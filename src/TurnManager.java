

public class TurnManager {

    private Player[] players;
    private Board board;
    private DistributeResources distributor;
    private Randomizer randomizer;

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
            //VictoryPointConditions vpCheck = new VictoryPointConditions(player, board);
            System.out.print("Player" + player.getPlayerID() + " = " + player.getVictoryPoints() + " | ");
        }
        System.out.println();
    }


}



