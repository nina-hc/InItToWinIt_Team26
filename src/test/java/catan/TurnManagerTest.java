package catan;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {
	private Player[] players;
	private Board board;
	private Bank bank;
	private Randomizer randomizer;
	private PlacementValidator placementValidator;
	private DistributeResources  distributeResources;
	private TurnManager turnManager;

	private final InputStream originalIn = System.in;
	@BeforeEach
	void setUp() {
		board = new Board();
		bank = new Bank();
		randomizer = new Randomizer();
		placementValidator=new PlacementValidator(board);

		//1 human player
		players = new Player[4];
		for (int i = 0; i < 4; i++) {
			players[i] = new Player(i + 1);
		}

		distributeResources = new DistributeResources(bank,players,randomizer,board);
		turnManager=new TurnManager(players,board,distributeResources,randomizer,bank,placementValidator);
	}

	@AfterEach
	void resetStdIn() {
		System.setIn(originalIn);
	}






	@DisplayName("Test executeRounds when maxRounds is given 0")
	@Test
	void test_ExecuteRounds_when_maxRounds_is_zero() {
		Player test = turnManager.executeRounds(0);
		assertNull(test);
	}

	@DisplayName("Test executeRounds running out and no Winner, should return null")
	@Test
	void test_executeRounds_running_out_and_no_winner() {
		replaceHumanInput(humanTurn(1));
		Player test = turnManager.executeRounds(1);
		assertNull(test);

	}
	@DisplayName("Returns a player when they have 10 VP at the end of the max rounds")
	@Test
	void test_ExecuteRounds_VP_player_return(){
		replaceHumanInput(humanTurn(1));
		players[0].addVictoryPoints(10);
		Player winner = turnManager.executeRounds(5);
		assertEquals(1,winner.getPlayerID());
	}


	/*@DisplayName("executeRounds check longest road")
	@Test
	void test_executeRounds_longestRoadBonus() {
		replaceHumanInput(humanTurn(1));

		Player player2 = players[1];
		int vpBefore = player2.getVictoryPoints();
		//arbitrary length of roads

		int[][] roadChain = {{25,26},{26,27},{27,28},{28,29},{29,30}};
		for (int[] pair : roadChain) {
			Edge edge = board.getEdgeBetweenNodes(pair[0], pair[1]);
			Road road = new Road(player2.getPlayerID(), edge);
			edge.placeRoad(road);
			player2.playerAddRoad(road);
		}

		turnManager.executeRounds(1);

		// After updateLongestRoad() runs
		assertEquals(vpBefore+2,player2.getVictoryPoints());
	}*/


	/*-----Helpers-----*/

	/*to replace the human input*/
	void replaceHumanInput(String input) {
		System.setIn(new ByteArrayInputStream(input.getBytes()));
	}

	//simulate turn
	String humanTurn(int n){
		return "roll\ngo\n".repeat(n);
	}

}