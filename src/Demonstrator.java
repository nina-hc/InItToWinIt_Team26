
/**
 * The Demonstrator class serves as the main entry point of the Catan simulation
 * program.
 *
 * This class is responsible for launching and coordinating the execution of a
 * Catan game It creates a Game object, performs the initial placement phase,
 * and then starts the full simulation.
 *
 * This class does not contain any game logic itself. Instead, it acts as a
 * driver that triggers major game phases in the correct order.
 *
 * Game Initialization: When the Game object is created, the board is
 * constructed, the bank is initialized with resources, randomly simulated
 * agents are created, and all supporting systems (dice rolling, resource
 * distribution, etc.) are prepared.
 *
 * Initial Placement Phase: Each player places: - One settlement - One connected
 * road
 *
 * This occurs in forward order for the first round (Player 1 to Player 4), and
 * in reverse order for the second round (Player 4 to Player 1).
 *
 * Simulation Phase (Main Gameplay Loop): The game proceeds in turn-based order.
 * During each turn: - The player rolls the dice - Resources are distributed
 * based on the dice roll - The player may attempt to build roads, settlements,
 * or cities - Victory points are updated
 *
 * The simulation continues until: - A player reaches the required number of
 * victory points, OR - A predefined turn limit is reached
 *
 * @author Team 26
 * @version 1.0.0
 */
public class Demonstrator {

	public static void main(String[] args) {

		// create game object
		Game game = new Game(10);

		// initial placement phase
		game.initialPlacement();

		// start simulation
		game.startSimulation();

	}

}
