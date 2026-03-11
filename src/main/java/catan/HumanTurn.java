package catan;

import java.util.Scanner;

/**
 * Handles a human player's turn, including rolling, building, listing resources,
 * and ending the turn. Enforces the rule that a player must roll before ending their turn.
 *
 * @author Marva Hassan
 */
public class HumanTurn {

    //The current player
    private Player player;

    //The game board
    private Board board;

    //Randomizer for dice rolls
    private Randomizer randomizer;

    //Bank reference for resource distribution
    private Bank bank;

    //Validator for placement rules
    private PlacementValidator placementValidator;

    //All players in the game
    private Player[] players;

    //Parser for converting input into commands
    private Parser parser;

    //Scanner for reading user input
    private Scanner scanner;

    /**
     * Constructs a HumanTurn instance with all required game components.
     *
     * @param player The player taking the turn
     * @param board The game board
     * @param randomizer Randomizer for dice rolls
     * @param bank The bank for resources
     * @param placementValidator Validates placement of settlements/cities/roads
     * @param players All players in the game
     */
    public HumanTurn(Player player, Board board, Randomizer randomizer, Bank bank, PlacementValidator placementValidator, Player[] players) {

        this.player = player;
        this.board = board;
        this.randomizer = randomizer;
        this.bank = bank;
        this.placementValidator = placementValidator;
        this.players = players;
        this.parser = new Parser();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Executes the main human turn loop.
     * Continuously prompts for input until the player ends the turn with "Go".
     * Enforces that the player must roll before ending the turn.
     */
    public void executeHumanTurn() {

        boolean turnActive = true;
        boolean rolled = false; //tracks if player rolled this turn

        while (turnActive) {

            System.out.print("[Player " + player.getPlayerID() + "]: Type in command > ");
            String input = scanner.nextLine();

            //Parse input into a Command object
            Command cmd = parser.parse(input);

            //If the command is invalid, notify player
            if (!cmd.valid) {
                System.out.println("Invalid command.");
                continue;
            }

            //Handle Roll
            if ("roll".equalsIgnoreCase(cmd.type)) {

                if (rolled) {
                    System.out.println("You already rolled this turn.");
                } else {
                    handleRoll(); //Execute roll logic
                    rolled = true;
                }
            }

            //Handle List
            else if ("list".equalsIgnoreCase(cmd.type)) {
                System.out.println(player.getResourceHand());
            }

            //Handle Build
            else if ("build".equalsIgnoreCase(cmd.type)) {
                if (!rolled) {
                    System.out.println("You must roll before building.");
                } else {
                    handleBuild(cmd);
                }
            }

            //Handle Go (end turn)
            else if ("go".equalsIgnoreCase(cmd.type)) {
                if (!rolled) {
                    System.out.println("You must roll before ending your turn.");
                } else {
                    turnActive = false; //End turn
                }
            }

            //Unknown command fallback
            else {
                System.out.println("Unknown command.");
            }
        }
    }

    /**
     * Handles dice rolling for the current player.
     * Distributes resources to players based on the roll.
     */
    public void handleRoll() {

        //Create a resource distributor
        DistributeResources distribute = new DistributeResources(bank, players, randomizer, board);

        //Execute distribution and get roll value
        int roll = distribute.executeDistribution();

        //Display the roll result
        System.out.println("Rolled: " + roll);
    }

    /**
     * Handles build actions (settlement, city, road) for the current player.
     *
     * @param cmd The command containing build type and placement info
     */
    public void handleBuild(Command cmd) {


        //Handle settlement build
        if ("settlement".equalsIgnoreCase(cmd.buildType)) {

            //Check resources first
            if (!player.getResourceHand().canBuySettlement()) {
                System.out.println("Not enough resources to build a settlement.");
                return;
            }

            Node node = board.getNode(cmd.nodeId);

            if (node == null) {
                System.out.println("Invalid node.");
                return;
            }

            if (node.isOccupied()) {
                System.out.println("Cannot build settlement: node already occupied.");
                return;
            }

            if (!placementValidator.canPlaceSettlement(node, player, false)) {

                //Check distance rule
                for (Edge edge : board.getAdjacentEdges(node)) {
                    if (edge.getOppositeNode(node).isOccupied()) {
                        System.out.println("Cannot build settlement: distance rule violated.");
                        return;
                    }
                }

                System.out.println("Cannot build settlement: must connect to your road.");
                return;
            }

            Build action = new BuildSettlement(player, board, randomizer, bank, placementValidator);
            action.executeWithPlacement(node);

            System.out.println("[Player " + player.getPlayerID() + "] built settlement at node " + cmd.nodeId);
        }

        //Handle city build
        if ("city".equalsIgnoreCase(cmd.buildType)) {

            if (!player.getResourceHand().canBuyCity()) {
                System.out.println("Not enough resources to build a city.");
                return;
            }

            Node node = board.getNode(cmd.nodeId);

            if (node == null) {
                System.out.println("Invalid node.");
                return;
            }

            if (!node.isOccupied()) {
                System.out.println("Cannot build city: no settlement exists here.");
                return;
            }

            Building building = node.getBuilding();

            if (!(building instanceof Settlement)) {
                System.out.println("Cannot build city: must upgrade a settlement.");
                return;
            }


            Build action = new BuildCity(player, board, randomizer, bank, placementValidator);
            action.executeWithPlacement(node);

            System.out.println("[Player " + player.getPlayerID() + "] upgraded settlement to city at node " + cmd.nodeId);
        }

        //Handle road build
        if ("road".equalsIgnoreCase(cmd.buildType)) {

            if (!player.getResourceHand().canBuyRoad()) {
                System.out.println("Not enough resources to build a road.");
                return;
            }

            Edge edge = board.getEdgeBetweenNodes(cmd.fromNodeId, cmd.toNodeId);

            if (edge == null) {
                System.out.println("Invalid road: nodes are not connected.");
                return;
            }

            if (edge.hasRoad()) {
                System.out.println("Cannot build road: edge already occupied.");
                return;
            }

            if (!placementValidator.canBuildRoad(edge, player)) {
                System.out.println("Cannot build road: must connect to your road, settlement, or city.");
                return;
            }

            Build action = new BuildRoad(player, board, randomizer, bank, placementValidator);
            action.executeWithPlacement(edge);

            System.out.println("[Player " + player.getPlayerID() + "] built road between "
                    + cmd.fromNodeId + " and " + cmd.toNodeId);
        }

    }

}