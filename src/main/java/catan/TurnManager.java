package catan;

public class TurnManager {

    private Player[] players;
    private Board board;
    private DistributeResources distributor;
    private Randomizer randomizer;
    private Bank bank;
    private PlacementValidator placementValidator;

    public TurnManager(Player[] players, Board board, DistributeResources distributor, Randomizer randomizer, Bank bank, PlacementValidator placementValidator) {
        this.players = players;
        this.board = board;
        this.distributor = distributor;
        this.randomizer = randomizer;
        this.bank = bank;
        this.placementValidator = placementValidator;
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
                PlayerAction action = new PlayerAction(player, board, randomizer, bank, placementValidator);
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
            VictoryPointConditions vpCheck = new VictoryPointConditions(player, board);
            System.out.print("Player" + player.getPlayerID() + " = " + vpCheck.calculateVictoryPoints() + " | ");        }
        System.out.println();
    }


}



