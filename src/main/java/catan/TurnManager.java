package catan;

/**
 * Manages the execution of turns for all players in a game.
 * Handles alternating human and AI player turns, rolling dice,
 * executing player actions, and printing the scoreboard.
 *
 * @author Marva Hassan
 */
public class TurnManager {

    private Player[] players;
    private Board board;
    private DistributeResources distributor;
    private Randomizer randomizer;
    private Bank bank;
    private PlacementValidator placementValidator;
    private Robber robber;


    //constructor
    public TurnManager(Player[] players, Board board, DistributeResources distributor, Randomizer randomizer, Bank bank, PlacementValidator placementValidator) {
        this.players = players;
        this.board = board;
        this.distributor = distributor;
        this.randomizer = randomizer;
        this.bank = bank;
        this.placementValidator = placementValidator;
        this.robber = new Robber(board.getTile(16));     //desert tile
    }

    /**
     * Executes a full set of rounds for all players up to a maximum number.
     * Alternates between human and AI players, executes their turns,
     * rolls dice, and prints the scoreboard after each round.
     *
     * @param maxRounds The maximum number of rounds to simulate
     * @return null (currently does not return a winner)
     */
    public Player executeRounds(int maxRounds) {
        boolean gameOver = false;
        int roundNumber = 0;

        while (!gameOver && roundNumber < maxRounds) {
            roundNumber++;

            for (Player player : players) {
                if (player.getPlayerID() == 1) { // use .equals for String comparison
                    HumanTurn humanTurn = new HumanTurn(player, board, randomizer, bank, placementValidator, players);
                    humanTurn.executeHumanTurn(); // run the human turn
                }

                else {

                    /*Dice roll */
                    int roll = distributor.executeDistribution();
                    System.out.println("[" + roundNumber + "] / [Player " + player.getPlayerID() + "]: Rolled " + roll);

                    if (roll == 7) {
                        robber.executeSevenRoll(board, bank, players, player);
                    }
                    /*Call player actions */
                    PlayerAction action = new PlayerAction(player, board, randomizer, bank, placementValidator);
                    action.executeTurn();
                }

                // Print Statement of Actions
                //System.out.println("[" + roundNumber + "] / Player " + player.getPlayerID() + ": ");

                VictoryPointConditions vpCheck = new VictoryPointConditions(player, board);

                if (vpCheck.checkWinConditions()) {
                    gameOver = true;
                    System.out.println("[Player " + player.getPlayerID() + "]: wins with " + vpCheck.calculateVictoryPoints() + " VPs!");
                    return player;
                }
            }

            // Print current score board after each round
            printScoreBoard(roundNumber);
        }

        System.out.println("Simulation ended after " + roundNumber + " rounds.");

        return null;

    }





    /**
     * Print current victory points for all players
     */
    public void printScoreBoard(int roundNumber) {
        System.out.print("[" + roundNumber + "] Scoreboard: ");
        for (Player player : players) {
            VictoryPointConditions vpCheck = new VictoryPointConditions(player, board);
            System.out.print("Player" + player.getPlayerID() + " = " + vpCheck.calculateVictoryPoints() + " | ");        }
        System.out.println();
    }


}



