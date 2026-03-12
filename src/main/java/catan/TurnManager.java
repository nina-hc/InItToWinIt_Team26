package catan;

public class TurnManager {

    private Player[] players;
    private Board board;
    private DistributeResources distributor;
    private Randomizer randomizer;
    private Bank bank;
    private PlacementValidator placementValidator;
    private Player longestRoadHolder = null;
    private int longestRoadLength = 0;

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

                if (player.getPlayerID() == 1) { //PLAYER 1 is Human player, Players 2,3,4 are ai simulated
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

                //Update Longest Road after player builds
                updateLongestRoad();

                //Now check win condition
                if (player.getVictoryPoints() >= 10) {
                    gameOver = true;
                    System.out.println("[Player " + player.getPlayerID() + "]: wins with " + player.getVictoryPoints() + " VPs!");
                    return player;
                }

                // Print Statement of Actions
                //System.out.println("[" + roundNumber + "] / Player " + player.getPlayerID() + ": ");

//                VictoryPointConditions vpCheck = new VictoryPointConditions(player, board);
//
//                if (vpCheck.checkWinConditions()) {
//                    gameOver = true;
//                    System.out.println("[Player " + player.getPlayerID() + "]: wins with " + vpCheck.calculateVictoryPoints() + " VPs!");
//                    return player;
//                }

                //player.getVictoryPoints();
            }

            // Print current score board after each round
            printScoreBoard(roundNumber);
        }

        System.out.println("Simulation ended after " + roundNumber + " rounds.");

        return null;

    }


    private void updateLongestRoad() {

        Player newHolder = null;
        int maxLength = 0;

        //Finds the player with the longest road of >=5
        for (Player player : players) {
            VictoryPointConditions vp = new VictoryPointConditions(player, board);
            int length = vp.getLongestRoad();

            if (length >= 5 && length > maxLength) {
                maxLength = length;
                newHolder = player;
            }
        }

        //If someone beats the current holder
        if (newHolder != null && newHolder != longestRoadHolder) {

            //Remove 2 VP from previous holder
            if (longestRoadHolder != null) {
                longestRoadHolder.addVictoryPoints(-2);
            }

            //sets the new holder
            longestRoadHolder = newHolder;
            longestRoadLength = maxLength;

            //Award 2 VP to new holder
            newHolder.addVictoryPoints(2);
        }
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



