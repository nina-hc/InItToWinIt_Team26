package main.java;

public class TurnManager {

    private Player[] players;
    private Board board;
    private DistributeResources distributor;
    private Randomizer randomizer;
    private Player longestRoadHolder = null;
    private int longestRoadLength = 0;


    public TurnManager(Player[] players, Board board, DistributeResources distributor, Randomizer randomizer) {
        this.players = players;
        this.board = board;
        this.distributor = distributor;
        this.randomizer = randomizer;
    }


    public Player executeRounds(int maxRounds) {
        boolean gameOver = false;
        int roundNumber = 0;

        while (!gameOver && roundNumber < maxRounds) {
            roundNumber++;

            for (Player player : players) {

                /*Dice roll */
                int roll = distributor.executeDistribution();
                System.out.println("[" + roundNumber + "] / [Player " + player.getPlayerID() + "]: Rolled " + roll);

                /*Call player actions */
                PlayerAction action = new PlayerAction(player, board, randomizer);
                action.executeTurn();

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
            //VictoryPointConditions vpCheck = new VictoryPointConditions(player, board);
            System.out.print("Player" + player.getPlayerID() + " = " + player.getVictoryPoints() + " | ");
        }
        System.out.println();
    }





}



