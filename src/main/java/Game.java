package main.java;

/**
 * Manages the overall Catan game simulation
 *  * Core responsibilities:
 *  - Setup game objects and players
 *  - Handle initial settlement and road placement
 *  - Distribute resources at the start and during the game
 *  - Execute player turns in order
 *  - Check for victory conditions
 * @author Marva Hassan (Revisions done by Nina)
 */
public class Game {

    private Board board; //The Catan board containing nodes, tiles, and roads
    private Bank bank; //Bank managing remaining resource cards
    private Player[] players;  // Array holding all 4 AI players
    private Randomizer randomizer; //handles random choices
    private DistributeResources distributor; //handles resource distribution after dice rolls
    private int maxRounds; //maximum number of simulation rounds

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

        //connect resource distributor
        distributor = new DistributeResources(bank, players, randomizer, board);
    }

    public void initialPlacement(){
        GameSetupManager setup = new GameSetupManager(board, bank, players, randomizer);
        setup.executeIntialPlacement();
    }



    /**
     * Run the simulation for the number of defined rounds
     */
    public void startSimulation() {
        TurnManager manager = new TurnManager(players, board, distributor, randomizer);

        Player winner = manager.executeRounds(maxRounds);

        //if no winner was determined in the executed rounds, state no winner
        if (winner == null) {
            System.out.println("Simulation ended without winner.");
        }
    }




}
