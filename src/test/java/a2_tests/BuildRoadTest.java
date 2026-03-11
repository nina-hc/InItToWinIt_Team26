package a2_tests;

import catan.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static catan.BuildRoad.*;
import static org.junit.jupiter.api.Assertions.*;

class BuildRoadTest {
	private Board board;
	private Player player;
	private Randomizer randomizer;
	private PlacementValidator placementValidator;
	private Bank bank;


	@BeforeEach
	void setUp() {
		board = new Board();
		player = new Player(1);
		randomizer = new Randomizer();
		placementValidator = new PlacementValidator(board);
		bank = new Bank();


	}

	@DisplayName("Testing if a player can buy a road but they can't")
	@Test
	void test_canPlayerBuy_false(){
		//assertFalse(new BuildRoad(player,board,randomizer,bank,placementValidator).canPlayerBuy());






	}

	@Test
	void test_canPlayerBuy_true() {

	}


	@Test
	void hasValidPlacement() {
	}

	@Test
	void generatePlacement() {
	}

	@Test
	void validatePlacement() {
	}

	@Test
	void doBuild() {
	}

	@Test
	void printAction() {
	}

	//Helper Methods
	/*Add a standard amount of resources to help with tests*/
	void add_starter_resources(){
		//arbitrary number that's enough to build more than one road
		player.getResourceHand().addResource(ResourceType.LUMBER,4);
		player.getResourceHand().addResource(ResourceType.BRICK,4);

	}
	/*add a settlement to help with the road tests*/
	void add_starter_settlement(){
		//sample node to place, arbitrary noded chosen
		Node testStartNode = board.getNode(0);
		//starting settlement to help with placing road tests if needed
		Settlement startSettlement = new Settlement(testStartNode,0);
		testStartNode.placeSettlement(startSettlement);
		player.playerAddSettlement(startSettlement);
	}
}