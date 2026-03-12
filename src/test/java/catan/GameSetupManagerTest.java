package catan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameSetupManagerTest {
	private Board board;
	private Bank bank;
	private Player[] players;
	private Randomizer randomizer;
	private PlacementValidator placementValidator;
	private GameSetupManager gameSetupManager;

	@BeforeEach
	void setup() {
		board = new Board();
		bank = new Bank();
		randomizer = new Randomizer();
		placementValidator = new PlacementValidator(board);

		players = new Player[4];
		for (int i = 0; i < 4; i++) {
			players[i] = new Player(i + 1);
		}

		gameSetupManager = new GameSetupManager(board,bank,players,randomizer,placementValidator);

	}

	@DisplayName("Basic initial settlement placement, should not return a settlement that's not null")
	@Test
	void test_initialSettlementPlacement_returns_nonNull() {
		Settlement s = gameSetupManager.initialSettlementPlacement(players[0]);
		/*due to the randomization, we're not targeting a value or spot just that it's a settlement that's not null*/
		assertNotNull(s);
	}

	@DisplayName("Testing initial settlement placement player and settlement owner match")
	@Test
	void test_initialSettlement_owner_matches_player() {
		Settlement s = gameSetupManager.initialSettlementPlacement(players[0]);
		//defensive check first
		assertNotNull(s);
		assertEquals(players[0].getPlayerID(),s.getOwnerID());
	}

	@DisplayName("Test the initial settlement placements are tracked on the nodes")
	@Test
	void test_initialSettlementPlacements_are_tracked_on_nodes() {
		Settlement s = gameSetupManager.initialSettlementPlacement(players[0]);
		assertTrue(s.getNode().isOccupied());
	}

	@DisplayName("Test initial settlement placement increases player settlement list")
	@Test
	void test_initialSettlementPlacement_tracked_for_player() {
		int settlementsBefore = players[0].getPlayerSettlements().size();
		Settlement s = gameSetupManager.initialSettlementPlacement(players[0]);
		assertEquals(settlementsBefore+1,players[0].getPlayerSettlements().size());
	}

	@DisplayName("Test initial settlements follow the distance rule")
	@Test
	void test_initialSettlementPlacement_distance_rule() {
		Settlement[] placed = new Settlement[4];
		for (int i = 0; i < 4; i++) {
			placed[i] = gameSetupManager.initialSettlementPlacement(players[i]);
			//they should be valid as the board isn't full
			assertNotNull(placed[i]);
		}
		//check distance rule
		for(int j = 0; j < 4; j++){
			for(int k = j+1; k < 4; k++){
				int nodeA = placed[j].getNode().getNodeID();
				int nodeB = placed[k].getNode().getNodeID();
				assertFalse(board.isAdjacent(nodeA, nodeB));

			}
		}
	}

	/*inital road placements*/
	@DisplayName("Test that the initial road is added the the player's list")
	@Test
	void test_initialRoadPlacement_added_to_player() {
		Settlement s = gameSetupManager.initialSettlementPlacement(players[1]);
		int roadsBefore = players[1].getPlayerRoads().size();
		gameSetupManager.initialRoadPlacement(players[1],s);
		assertEquals(roadsBefore+1,players[1].getPlayerRoads().size());
	}

	@DisplayName("Test initial road is stored on edge")
	@Test
	void test_initialRoadPlacement_stored_in_edge() {
		Settlement s = gameSetupManager.initialSettlementPlacement(players[1]);
		gameSetupManager.initialRoadPlacement(players[1],s);
		Road road = players[1].getPlayerRoads().get(0);
		assertTrue(road.getEdge().hasRoad());
	}

	/*Test giving starting resources*/

	@Test
	void giveStartingResources() {
	}

	//should also do a full run through


	//helper method to add a settlement to a specific player
	void add_settlement(int nodeID, Player player) {
		Node node = board.getNode(nodeID);
		Settlement s = new Settlement(node, player.getPlayerID());
		player.playerAddSettlement(s);
	}
}