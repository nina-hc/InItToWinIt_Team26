package InItToWinIt_Team26;

import java.util.Random;

public class Game {

    //the fixed catan board (contains tiles, nodes, roads)
    private Board board;

    //bank stores remaining resource cards
    private Bank bank;

    //the 4 players
    private Player[] players;

    //responsible for rolling dice
    private Randomizer randomizer;

    //handles resource production after dice rolls
    private DistributeResources distributor;

    //used for random decision-making
    private Random random;

    //victory points required to win
    private static final int WINNING_VP = 10;

    /*
     * constructor initializes entire game state
     */
    public Game() {

        //create fixed map
        board = new Board();

        //create bank (starts with 19 of each resource)
        bank = new Bank();

        //dice roll object
        randomizer = new Randomizer();

        //create 4 agents
        players = new Player[4];
        for (int i = 0; i < 4; i = i + 1) {
            players[i] = new Player(i + 1); //playerID 1-4
        }

        //connect distributor to board, bank, players
        distributor = new DistributeResources(bank, players, randomizer, board);

        //random object for generating random choices
        random = new Random();

        //perform initial placement phase
        initialPlacement();
    }

    /*
     * initial placement phase:
     * each player places 2 settlements and 2 roads
     * first round: p1 to p4
     * second round: p4 to p1
     */
    public void initialPlacement() {
        int round = 0;
        while (round < 2) {

            int start = 0;
            int end = players.length;
            int step = 1;

            //second round goes in reverse
            if (round == 1) {
                start = players.length - 1;
                end = -1;
                step = -1;
            }

            int i = start;
            while (i != end) {
                Player player = players[i];

                //place settlement randomly
                randomSettlementPlacement(player);

                //place road randomly
                randomRoadPlacement(player);

                i = i + step;
            }

            round = round + 1;
        }
    }

    /*
     * main simulation loop
     * stops when:
     *   - a player reaches 10 vp
     */
    public void startSimulation() {

        boolean gameOver = false;

        while (!gameOver) {


            int playerIndex = 0;
            while (playerIndex < players.length) {

                Player player = players[playerIndex];

                //roll dice and distribute resources
                int roll = distributor.executeDistribution();
                System.out.println("player " + player.getPlayerID() + " roll " + roll);

                //create a player action handler
                PlayerAction action = new PlayerAction(player, board);

                //execute one full turn for this player
                action.executeTurn();

                //check if player reached winning points
                if (player.getVictoryPoints() >= WINNING_VP) {
                    gameOver = true;
                    break;
                }

                playerIndex = playerIndex + 1;
            }

            //print vp summary at end of round
            printScoreBoard();

        }

        System.out.println("simulation ended");
    }

    /*
     * print current victory points for all players
     */
    public void printScoreBoard() {
        System.out.print("score ");
        int i = 0;
        while (i < players.length) {
            System.out.print("player" + players[i].getPlayerID() + "=" + players[i].getVictoryPoints() + " ");
            i = i + 1;
        }
        System.out.println();
    }

    /*
     * attempt to place a settlement randomly
     */
    public void randomSettlementPlacement(Player player) {

        BuildSettlement settlement = new BuildSettlement(player, board, randomizer);

        //execute the build (placement and resource deduction handled internally)
        settlement.execute();
    }

    /*
     * attempt to place road randomly
     */
    public void randomRoadPlacement(Player player) {

        BuildRoad road = new BuildRoad(player, board, randomizer);

        //execute the build
        road.execute();
    }


}
