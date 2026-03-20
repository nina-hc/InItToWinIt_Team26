package catan;

/**
 * Manages the overall Catan game simulation
 *  * Core responsibilities:
 *  - Setup game objects and players
 *  - Handle initial settlement and road placement
 *  - Distribute resources at the start and during the game
 *  - Execute player turns in order
 *  - Check for victory conditions
 *
 * @author Marva Hassan (Revisions done by Nina)
 * @version February 2026, McMaster University
 */
public class Game {

    /**
     * Game board, contains node, tiles and roads
     */
    private final Board board;

    /**
     * The bank, contains and manages remaining resource cards
     */
    private final Bank bank;

    /**
     * Player array holding all the players in the game
     */
    private final Player[] players;

    /**
     * The randomizer, handles random choices
     */
    private final Randomizer randomizer;

    /**
     * In charge of resource distribution after the dice is rolled
     */
    private final DistributeResources distributor;

    /**
     * The maximum number of simulation rounds
     */
    private final int maxRounds;

    /**
     * In charge of validating placements
     */
    private final PlacementValidator placementValidator;

    /**
     * To check if the game is still in the setup stage
     */
    private boolean setupPhase = true;

    /**
     * Initialize game with 4 players and default maxRounds
     */
    public Game(int maxRounds) {
        //R1.4
        if (maxRounds < 1 || maxRounds > 8192) {
            throw new IllegalArgumentException("maxRounds must be 1–8192");
        }
        this.maxRounds = maxRounds;

        // Initialize board and bank
        board = new Board(); //
        bank = new Bank();
        randomizer = new Randomizer();

        //create 4 players
        players = new Player[4];
        for (int i = 0; i < 4; i++) {
            players[i] = new Player(i + 1);
        }

        placementValidator = new PlacementValidator(board);

        //connect resource distributor
        distributor = new DistributeResources(bank, players, randomizer, board);
    }

    public void initialPlacement(){
        GameSetupManager setup = new GameSetupManager(board, bank, players, randomizer,placementValidator);
        setup.executeIntialPlacement();
        setupPhase = false;   // setup is now finished
    }

    /**
     * Method to check if the game is still in its setup stage
     *
     * @return true if it is still in the setup phase, false if not
     */
    public boolean isSetupPhase() {

        return setupPhase;
    }



    /**
     * Run the simulation for the number of defined rounds
     */
    public void startSimulation() {
        TurnManager manager = new TurnManager(players, board, distributor, randomizer, bank, placementValidator);

        Player winner = manager.executeRounds(maxRounds);

        //if no winner was determined in the executed rounds, state no winner
        if (winner == null) {
            System.out.println("Simulation ended without winner.");
        }
    }

}
