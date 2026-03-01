package a2_tests;

import catan.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
	private Player player;

	@BeforeEach
	void setUp() {
		/*creating a player with the ID 1*/
		player = new Player(1);
	}

	/*testing constructor*/
	@DisplayName("Test PlayerID from Constructor")
	@Test
	void test_constructor_player_id() {
		assertEquals(1,player.getPlayerID());
	}

	ResourceHand resourceHand = new ResourceHand();
	@DisplayName("Test ResourceHand from Constructor")
	@Test
	void test_constructor_resource_hand() {

		/*I could check them all individually in methods, but I'm not sure if that's excessive*/
		//checking that each resource type is initialized to empty
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
			resourceHand.getResource(ResourceType.DESERT);
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
	Board board = new Board();

	@DisplayName("Basic Settlement Being Added to Player Test")
	@Test
	void basic_playerAddSettlement() {
		Node node = board.getNode(5);
		Settlement s = new Settlement(node,5);
		player.playerAddSettlement(s);
		assertEquals(1,player.getPlayerSettlements().size());
		assertEquals(4,player.getPlayerSettlementsLeft());
	}
	/*@DisplayName("Testing the Player Building Inventory Decreases After Adding A Settlement")
	@Test
	void test_settlement_removed_from_player_inventory(){
		assertEquals(4,player.getPlayerSettlementsLeft());
	}*/

	@DisplayName("Basic Adding Road to Player's Inventory Test")
	@Test
	void basic_playerAddRoad() {
		Edge edge = board.getEdgeBetweenNodes(5,0);
		Road road = new Road(5,edge);
		player.playerAddRoad(road);
		assertEquals(1,player.getPlayerRoads().size());
		assertEquals(14,player.getPlayerRoadsLeft());

	}
	/*@DisplayName("Testing Road Removed from Player After Adding")
	@Test
	void road_removed_from_player_inventory(){
		assertEquals(14,player.getPlayerRoadsLeft());
	}*/


	/*@Test
	void playerUpgradeToCity() {
	}*/




	/*@Test
	void getPlayerRoads() {

	}*/

	/*@Test
	void getPlayerSettlements() {
	}

	@Test
	void getPlayerCities() {
	}

	@Test
	void getPlayerRoadsLeft() {
	}

	@Test
	void getPlayerSettlementsLeft() {
	}

	@Test
	void getPlayerCitiesLeft() {
	}

	@Test
	void getPlayerID() {
	}

	@Test
	void getResourceHand() {
	}*/




}