package a2_tests;

import catan.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
	private Player player;
	private Board board;

	@BeforeEach
	void setUp() {
		/*creating a player with the ID 1 and a board to share across tests*/
		player = new Player(1);
		board = new Board();
	}
	//STILL need to add timeout for tests

	/*testing constructor*/
	@DisplayName("Test PlayerID from Constructor")
	@Test
	void test_constructor_player_id() {
		assertEquals(1,player.getPlayerID());
	}


	@DisplayName("Test ResourceHand is Initialized to Empty from Constructor")
	@Test
	void test_constructor_resource_hand() {
		ResourceHand resourceHand = new ResourceHand();
		/*I could check them all individually in methods, but I'm not sure if that's excessive*/
		assertEquals(0,resourceHand.getResource(ResourceType.BRICK));
		assertEquals(0,resourceHand.getResource(ResourceType.LUMBER));
		assertEquals(0,resourceHand.getResource(ResourceType.WOOL));
		assertEquals(0,resourceHand.getResource(ResourceType.GRAIN));
		assertEquals(0,resourceHand.getResource(ResourceType.ORE));
	}

	@DisplayName("Error if desert is ever included")
	@Test
	void getResource_desert_error(){
		assertThrows(IllegalArgumentException.class, () -> {
			player.getResourceHand().getResource(ResourceType.DESERT);
		});
	}


	@DisplayName("Testing the Building Lists are Initialized to Empty")
	@Test
	void constructor_initializes_roads_empty(){
		assertTrue(player.getPlayerRoads().isEmpty());
		assertTrue(player.getPlayerSettlements().isEmpty());
		assertTrue(player.getPlayerCities().isEmpty());
	}

	@DisplayName("Testing the Players Start with Pre-Defined Number of Buildings as Per the Rules")
	@Test
	void player_building_inventory_at_start(){
		assertEquals(15, player.getPlayerRoadsLeft());
		assertEquals(5, player.getPlayerSettlementsLeft());
		assertEquals(4, player.getPlayerCitiesLeft());
	}

	/*Basic Adding Building Tests*/

	/*Settlement Tests*/
	@DisplayName("Basic Placing a Settlement Test: the player's settlements should increase by one and their " +
			"settlements should decrease by one")
	@Test
	void basic_playerAddSettlement() {
		Node node = board.getNode(5);
		Settlement s = new Settlement(node, player.getPlayerID());
		player.playerAddSettlement(s);
		assertEquals(1,player.getPlayerSettlements().size());
		assertEquals(4,player.getPlayerSettlementsLeft());
	}

	@DisplayName("Boundary Test: Place a Settlement When the Player has None Left")
	@Test
	void boundaryTest_addSettlement_with_none_left(){
		//adding max amount of settlements first
		int [] nodeIDs = {15,6,0,10,19};
		for (int nodeID : nodeIDs){
			addSettlementHelper(nodeID);
		}
		//no more to place
		Node node = board.getNode(41);
		assertThrows(IllegalStateException.class,
				() -> {player.playerAddSettlement(new Settlement(node, player.getPlayerID()));});

	}


	/*Road Building*/

	@DisplayName("Basic Adding Road to Player's Inventory Test")
	@Test
	void basic_playerAddRoad() {
		Edge edge = board.getEdgeBetweenNodes(5,0);
		Road road = new Road(5,edge);
		player.playerAddRoad(road);
		assertEquals(1,player.getPlayerRoads().size());
		assertEquals(14,player.getPlayerRoadsLeft());

	}


	@DisplayName("Boundary Test: Place a Road When None are Left")
	@Test
	void boundaryTest_addRoad_with_none_left() {
		//sample road pairs
		addRoadHelper(34,13);
		addRoadHelper(12,3);
		addRoadHelper(4,5);
		addRoadHelper(0,20);
		addRoadHelper(2,1);
		addRoadHelper(10,9);
		addRoadHelper(24,53);
		addRoadHelper(22,49);
		addRoadHelper(17,18);
		addRoadHelper(16,21);
		addRoadHelper(37,38);
		addRoadHelper(51,50);
		addRoadHelper(26,27);
		addRoadHelper(15,4);
		addRoadHelper(43,45);
		//this would've been better with getting all edges but unfortunately I don't think that would be wise without
		// having it tested first
		/*Ideally the structural test suites go first so I can use it*/

		//No more roads left, trying to add a 16th
		Edge edge = board.getEdgeBetweenNodes(41,39);
		Road road = new Road(player.getPlayerID(),edge);
		assertThrows(IllegalStateException.class, () -> {player.playerAddRoad(road);});

	}

	@DisplayName("Basic Player Upgrading a to a City Test")
	@Test
	void playerUpgradeToCity() {
		Node node = board.getNode(0);
		addSettlementHelper(0);
		City city = new City(node,1);
		player.playerUpgradeToCity(node,city);

		assertEquals(1,player.getPlayerCities().size());
		assertEquals(0,player.getPlayerSettlements().size());
		assertEquals(3,player.getPlayerCitiesLeft());
	}

	@DisplayName("Upgrading a City Where There is No Settlement")
	@Test
	void city_upgrade_without_settlement(){
		Node node = board.getNode(2);
		assertThrows(IllegalArgumentException.class, () -> {player.playerUpgradeToCity(node,new City(node,1));});
	}

	@DisplayName("Boundary Test: Placing a City When There Are None Left")
	@Test
	void boundaryTest_addCity_with_none_left(){
		//sample safe nodes
		int[] nodeIDs = {0,8,11,14};
		//use helper to make 4 settlements which are upgraded to cities
		for(int i : nodeIDs){
			addSettlementForAddCityHelper(i);
		}
		//out of cities
		Node node = board.getNode(16);
		addSettlementHelper(16);
		assertThrows(IllegalStateException.class, () -> player.playerUpgradeToCity(node,new City(node,1)));
	}
	/*Helpers*/
	private void addSettlementHelper(int nodeID){
		Node node = board.getNode(nodeID);
		Settlement s = new Settlement(node, player.getPlayerID());
		player.playerAddSettlement(s);
	}

	private void addRoadHelper(int nodeAID, int nodeBID){
		Edge edge = board.getEdgeBetweenNodes(nodeAID,nodeBID);
		Road road = new Road(player.getPlayerID(), edge);
		player.playerAddRoad(road);
	}

	private void addSettlementForAddCityHelper(int nodeID){
		Node node = board.getNode(nodeID);
		//is it bad or better encapsulation to use the other helper for adding settlements...?
		Settlement s = new Settlement(node, player.getPlayerID());
		player.playerAddSettlement(s);
		player.playerUpgradeToCity(node,new City(node, player.getPlayerID()));
	}

}