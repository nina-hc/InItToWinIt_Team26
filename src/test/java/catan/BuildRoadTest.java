package catan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class BuildRoadTest {
	private Board board;
	private Player player;
	private Randomizer randomizer;
	private PlacementValidator placementValidator;
	private Bank bank;
	private BuildRoad buildRoad;


	@BeforeEach
	void setUp() {
		board = new Board();
		player = new Player(1);
		randomizer = new Randomizer();
		placementValidator = new PlacementValidator(board);
		bank = new Bank();
		buildRoad = new BuildRoad(player,board,randomizer,bank,placementValidator);


	}

	/*-----canPlayerBuy-----*/
	@DisplayName("Testing if a player can buy a road but they don't have any of the resources")
	@Test
	void test_canPlayerBuy_when_they_have_none_of_the_resources(){
		assertFalse(new BuildRoad(player,board,randomizer,bank,placementValidator).canPlayerBuy());
	}

	@DisplayName("Testing if a player can buy a road but they're missing a brick")
	@Test
	void test_canPlayerBuy_when_missing_brick(){
		player.getResourceHand().addResource(ResourceType.LUMBER,1);
		assertFalse(buildRoad.canPlayerBuy());

	}

	@DisplayName("Testing if a player can buy a road but they're missing wood")
	@Test
	void test_canPlayerBuy_when_missing_wood(){
		player.getResourceHand().addResource(ResourceType.BRICK,1);
		assertFalse(buildRoad.canPlayerBuy());
	}

	@DisplayName("Testing if a player can buy a road when they are able to")
	@Test
	void test_canPlayerBuy_true() {
		add_starter_resources();
		assertTrue(buildRoad.canPlayerBuy());
	}

	@DisplayName("Test trying to buy a road when the player has none left")
	@Test
	void test_canPlayerBuy_no_roads_left(){
		//add enough to buy more than 15 roads
		add_starter_resources();
		add_starter_resources();
		add_starter_resources();
		add_starter_resources();

		//adding 15 roads to the map, this isn't dependent on settlements
		int [][] edgeNodePairs = {{0,5},{0,20},{0,1},{13,14},{34,13},{13,12},{8,9},{8,27},{8,7},{11,10},{10,9},{10,
				29},{39,17},{17,18},{17,15}};

		for(int[] nodePair : edgeNodePairs){
			Edge edge = board.getEdgeBetweenNodes(nodePair[0],nodePair[1]);
			Road road = new Road (player.getPlayerID(),edge);
			edge.placeRoad(road);
			player.playerAddRoad(road);
		}


		assertFalse(buildRoad.canPlayerBuy());

	}

	/*-----generatePlacement-----*/

	@DisplayName("Test generate placement when they have no valid placements, aka no roads or settlements to build " +
			"off of")
	@Test
	void test_generatePlacement_when_no_valid_places() {
		assertNull(buildRoad.generatePlacement());
	}

	@DisplayName("Test generate placement when the player has a settlement")
	@Test
	void basic_test_generatePlacement() {
		/*here in practice it depends on how many settlements they have as it will randomize what it gives but the
		idea is that it shouldn't be null, it'll give an edge*/
		add_starter_settlement();
		Object placement =  buildRoad.generatePlacement();
		assertNotNull(placement,"As it can be randomized the exact edge can vary. However, it shouldn't be null");
		assertInstanceOf(Edge.class,placement,"A valid edge is expected");
	}

	@DisplayName("Testing that generate placements returns an edge that is in the valid edge lists")
	@Test
	void test_generatePlacement_returns_valid_edge_list() {
		add_starter_settlement();
		List<Edge> validEdges = placementValidator.getValidRoadEdges(player);
		Object placement = buildRoad.generatePlacement();

		assertTrue(validEdges.contains(placement));
	}

	//I like this concept but want to make it better if I have time
	@DisplayName("Testing that generatePlacement doesn't return an edge that's occupied")
	@Test
	void test_generatePlacement_never_return_occupied_edge() {
		//when the player already has a road on all adjacent edges, BuildRoad should understand that this also counts
		// as no more valid edges even though they belong to the player

		/*settlement at nodeID 0*/
		add_starter_settlement();
		Node node = board.getNode(0);
		//trying to make it more extendable for the future if it's more than just one settlement in a suite
		for(Edge edge: board.getAdjacentEdges(node)){
			//rather than relying on validator manually checking it has no roads to reduce dependency for debugging
			if(!edge.hasRoad()){
				Road road = new Road (player.getPlayerID(),edge);
				edge.placeRoad(road);
				player.playerAddRoad(road);
			}

		}
		Object placement = buildRoad.generatePlacement();

		/*I'm not sure if I implemented this right, I wanted it to be if they did have other settlements making sure
		it didn't return one that had a road, but currently I think it would just be null as it's only one settlement*/
		if(placement != null){
			assertFalse(((Edge)placement).hasRoad());
		}

	}

	/*-----validatePlacement-----*/

	@DisplayName("Test vaild placements when there are none")
	@Test
	void test_ValidPlacement_when_false() {
		assertFalse(buildRoad.validatePlacement(board.getEdgeBetweenNodes(0,5)));

	}

	@DisplayName("Testing valid placements when true")
	@Test
	void test_ValidPlacement_when_true() {
		add_starter_resources();
		add_starter_settlement();
		assertTrue(buildRoad.validatePlacement(board.getEdgeBetweenNodes(0,5)));

	}

	@DisplayName("Test validatePlacements when trying to place on an edge that already has a road")
	@Test
	void test_validatePlacement_when_edge_occupied() {
		add_starter_settlement();
		Edge edge = board.getEdgeBetweenNodes(0,1);
		//placing a road
		Road occupyingRoad = new Road (2,edge);
		edge.placeRoad(occupyingRoad);
		//trying to place on the occupied spot
		assertFalse(buildRoad.validatePlacement(edge));

	}

	@DisplayName("Testing validPlacements when it's connecting to a road only")
	@Test
	void test_validPlacement_connecting_roads() {
		add_starter_settlement();

		Edge edgeA = board.getEdgeBetweenNodes(0,1);
		Road roadA = new Road (player.getPlayerID(), edgeA);
		edgeA.placeRoad(roadA);
		player.playerAddRoad(roadA);

		//place a road connecting to a valid road
		assertTrue(buildRoad.validatePlacement(board.getEdgeBetweenNodes(1,2)));

	}

	/*----doBuild---*/
	@DisplayName("Basic doBuild Test, making sure it takes the correct resources from the player")
	@Test
	void basic_doBuild_test() {
		add_starter_resources();
		add_starter_settlement();

		Edge edge = board.getEdgeBetweenNodes(0,1);
		buildRoad.doBuild(edge);

		assertEquals(4,player.getResourceHand().getResource(ResourceType.LUMBER));
		assertEquals(4,player.getResourceHand().getResource(ResourceType.BRICK));

	}

	//include human testing for A2

	@DisplayName("Testing that doBuild adds the road to the player")
	@Test
	void test_doBuild_adds_the_road_to_player() {
		add_starter_resources();
		add_starter_settlement();

		int numberOfRoadsBefore = player.getPlayerRoads().size();

		Edge edge = board.getEdgeBetweenNodes(0,1);
		buildRoad.doBuild(edge);

		assertEquals(numberOfRoadsBefore+1,player.getPlayerRoads().size(),"This should increase the number of roads " +
				"by 1");

	}

	@DisplayName("Testing that doBuild places the road on the edge correctly")
	@Test
	void test_doBuild_places_the_road_on_the_edge() {
		add_starter_resources();
		add_starter_settlement();
		Edge edge = board.getEdgeBetweenNodes(0,1);
		assertFalse(edge.hasRoad());//false before
		buildRoad.doBuild(edge);
		assertTrue(edge.hasRoad());//true after

	}

	@DisplayName("Testing doBuild road owner is the current player")
	@Test
	void test_doBuild_road_owner_is_the_current_player() {
		add_starter_resources();
		add_starter_settlement();
		Edge edge = board.getEdgeBetweenNodes(0,1);
		buildRoad.doBuild(edge);
		assertEquals(player.getPlayerID(),edge.getRoad().getOwner());
	}



	@DisplayName("Testing that doBuild gives the resources to the bank")
	@Test
	void test_doBuild_gives_the_resources_to_bank() {
		add_starter_resources();
		add_starter_settlement();

		int initialBankLumber = bank.getResourceAmount(ResourceType.LUMBER);
		int initialBankBrick = bank.getResourceAmount(ResourceType.BRICK);
		buildRoad.doBuild(board.getEdgeBetweenNodes(0,1));

		assertEquals(initialBankLumber+1,bank.getResourceAmount(ResourceType.LUMBER));
		assertEquals(initialBankBrick+1,bank.getResourceAmount(ResourceType.BRICK));
	}

	/*---execute---*/
	@DisplayName("Test execute when player cannot afford")
	@Test
	void test_execute_when_player_cannot_afford() {
		add_starter_settlement();
		assertFalse(buildRoad.execute());

	}

	@DisplayName("Test execute when the player has no valid placements")
	@Test
	void test_execute_when_player_has_no_valid_placements() {
		add_starter_resources();
		assertFalse(buildRoad.execute());
	}

	@DisplayName("Test execute when player can afford and has a valid spot")
	@Test
	void test_execute_true(){
		add_starter_resources();
		add_starter_settlement();
		assertTrue(buildRoad.execute());

	}

	/*-----Helper Methods-----*/
	/*Add a standard amount of resources to help with tests*/
	void add_starter_resources(){
		//arbitrary number that's enough to build more than one road
		player.getResourceHand().addResource(ResourceType.LUMBER, bank.transferToPlayer(ResourceType.LUMBER,5));
		player.getResourceHand().addResource(ResourceType.BRICK,bank.transferToPlayer(ResourceType.BRICK,5));

	}
	/*add a settlement to help with the road tests*/
	void add_starter_settlement(){
		//sample node to place, arbitrary noded chosen
		Node testStartNode = board.getNode(0);
		//starting settlement to help with placing road tests if needed
		Settlement startSettlement = new Settlement(testStartNode,1);
		testStartNode.placeSettlement(startSettlement);
		player.playerAddSettlement(startSettlement);
	}

	void add_settlement_helper(int NodeID){
		Node node = board.getNode(NodeID);
		Settlement settlement = new Settlement(node,1);
		node.placeSettlement(settlement);
		player.playerAddSettlement(settlement);
	}
}