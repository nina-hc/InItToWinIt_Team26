package catan;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

	/*---Constructor Tests---*/
	@DisplayName("Test Game Constructor Below Lower Bound for Rounds")
	@Test
	void test_game_constructor_below_lower_bound_for_rounds() {
		assertThrows(IllegalArgumentException.class, () -> new Game(0));

	}

	@DisplayName("Testing Negative Input for Game Rounds")
	@Test
	void test_game_constructor_negative_input_for_rounds() {
		assertThrows(IllegalArgumentException.class, () -> new Game(-3));
	}

	@DisplayName("Test Above Upper Bound for Game Rounds")
	@Test
	void test_game_above_upper_bound_for_rounds() {
		assertThrows(IllegalArgumentException.class, () -> new Game(8193));
	}

	@DisplayName("Test at lower bound for game rounds, rounds = 1")
	@Test
	void test_at_lower_bound_for_rounds() {
		assertDoesNotThrow(() -> new Game(1));
	}

	@DisplayName("Test at upper bound for game rounds, rounds = 8192")
	@Test
	void test_at_upper_bound_for_rounds() {
		assertDoesNotThrow(() -> new Game(8192));
	}

	@DisplayName("Basic acceptable round number test")
	@Test
	void test_basic_accept_round_number() {
		assertDoesNotThrow(() -> new Game(50));
	}

	//need to test for non-int value given

	@DisplayName("Testing the set up phase flag returns true until initial placements are done")
	@Test
	void test_set_up_flag_before_initial_placements() {
		Game game = new Game(12);
		assertTrue(game.isSetupPhase());
	}
	@DisplayName("Test Set Up Flag after initial placements, should be false")
	@Test
	void test_set_up_flag_after_initial_placements() {
		Game game = new Game(12);
		game.initialPlacement();
		assertFalse(game.isSetupPhase());
	}

	/*--initial placements--*/
	@DisplayName("Testing that initial placements complete successfully, there should be no errors thrown")//the class
	// doesn't account for throwing any, so checking this is important to debug
	@Test
	void test_initialPlacement_completes_successfully() {
		Game game = new Game(10);
		assertDoesNotThrow(() -> game.initialPlacement());
	}

	@DisplayName("Testing program behaviour if you try to call initial placement twice")
	@Test
	void test_calling_initialPlacement_twice() {
		Game game = new Game(10);
		game.initialPlacement();
		//current code does not have any errors or restrictions around this
		//there will be the automata in A2 which will safeguard more, so I'll just make sure it doesn't set up phase
		// again
		assertDoesNotThrow(()-> game.isSetupPhase());//verify with marva
	}

}