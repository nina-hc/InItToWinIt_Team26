package InItToWinIt_Team26;

import java.util.Random;

/**
 * Manages the overall Catan game simulation
 * Fulfills Assignment 1 requirements (R1.1–R1.9)
 */
public class Game {

    private static final int WINNING_VP = 10;

    private Board board;
    private Bank bank;
    private Player[] players;
    private Randomizer randomizer;
    private DistributeResources distributor;
    private int maxRounds;

    /**
     * Initialize game with 4 players and default maxRounds
     */
    public Game(int maxRounds) {
        if (maxRounds < 1 || maxRounds > 8192) {
            throw new IllegalArgumentException("maxRounds must be 1–8192");
        }
        this.maxRounds = maxRounds;

        // Initialize board and bank
        board = new Board(); // Should use fixed map setup per R1.1
        bank = new Bank();
        randomizer = new Randomizer();

        // Create 4 players
        players = new Player[4];
        for (int i = 0; i < 4; i++) {
            players[i] = new Player(i + 1);
        }

        // Connect resource distributor
        distributor = new DistributeResources(bank, players, randomizer, board);
    }

    /**
     * Initial placement phase: each player places 2 settlements and 2 roads
     */
    public void initialPlacement() {
        for (int round = 0; round < 2; round++) {
            // Forward for first round, backward for second round
            int start = (round == 0) ? 0 : players.length - 1;
            int end = (round == 0) ? players.length : -1;
            int step = (round == 0) ? 1 : -1;

            for (int i = start; i != end; i += step) {
                Player player = players[i];
                randomSettlementPlacement(player);
                randomRoadPlacement(player);
            }
        }
    }

    /**
     * Main simulation loop until a player wins or maxRounds reached
     */
    public void startSimulation() {
        boolean gameOver = false;
        int roundNumber = 0;

        while (!gameOver && roundNumber < maxRounds) {
            roundNumber++;

            System.out.println("=== Round " + roundNumber + " ===");

            for (Player player : players) {

                // Roll dice
                int roll = distributor.executeDistribution();
                System.out.println("[" + roundNumber + "] / Player " + player.getPlayerID() + ": Rolled " + roll);

                // AI builds (tries to spend cards if >7)
                PlayerAction action = new PlayerAction(player, board, randomizer);
                action.executeTurn();

                // Print player action summary
                System.out.println("[" + roundNumber + "] / Player " + player.getPlayerID() + ": Completed turn");

                // Check win conditions
                VictoryPointConditions vpCheck = new VictoryPointConditions(player, board);
                if (vpCheck.checkWinConditions()) {
                    gameOver = true;
                    System.out.println("Player " + player.getPlayerID() + " wins with " + vpCheck.calculateVictoryPoints() + " VPs!");
                    break;
                }

                System.out.println("Player " + player.getPlayerID() + " hand: "
                        + player.getResourceHand().toString());


            }

            // Print current score board after each round
            printScoreBoard(roundNumber);
        }


        System.out.println("Simulation ended after " + roundNumber + " rounds.");
    }

    /**
     * Print current victory points for all players
     */
    public void printScoreBoard(int roundNumber) {
        System.out.print("[" + roundNumber + "] Scoreboard: ");
        for (Player player : players) {
            VictoryPointConditions vpCheck = new VictoryPointConditions(player, board);
            System.out.print("Player" + player.getPlayerID() + "=" + vpCheck.calculateVictoryPoints() + " ");
        }
        System.out.println();
    }

    /**
     * Attempt to place a settlement randomly (used in initial placement)
     */
    public void randomSettlementPlacement(Player player) {
        BuildSettlement settlement = new BuildSettlement(player, board, randomizer);
        settlement.execute();
    }

    /**
     * Attempt to place a road randomly (used in initial placement)
     */
    public void randomRoadPlacement(Player player) {
        BuildRoad road = new BuildRoad(player, board, randomizer);
        road.execute();
    }
}
