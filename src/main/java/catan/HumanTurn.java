package catan;

import java.util.Scanner;

/**
 * HumanTurn handles all logic related to a human player's turn.
 * Responsibilities:
 * - Reading user input
 * - Parsing commands
 * - Executing roll, list, build, and end-turn actions
 *
 */
public class HumanTurn {

    private Player player;
    private Board board;
    private Randomizer randomizer;
    private Bank bank;
    private PlacementValidator placementValidator;
    private Player[] players;

    private Parser parser;

    public HumanTurn(Player player,
                     Board board,
                     Randomizer randomizer,
                     Bank bank,
                     PlacementValidator placementValidator,
                     Player[] players) {

        this.player = player;
        this.board = board;
        this.randomizer = randomizer;
        this.bank = bank;
        this.placementValidator = placementValidator;
        this.players = players;

        this.parser = new Parser();
    }

    // --------------------------------------------
    // Main Turn Loop
    // --------------------------------------------
    public void executeTurn() {

        Scanner scanner = new Scanner(System.in);

        boolean turnActive = true;
        boolean rolled = false;

        while (turnActive) {

            System.out.print("> ");
            String input = scanner.nextLine();

            Command cmd = parser.parse(input);

            if (!cmd.valid) {
                System.out.println("Invalid command.");
                continue;
            }

            // Handle Roll
            if (cmd.type.equals("Roll")) {

                if (rolled) {
                    System.out.println("You already rolled this turn.");
                } else {
                    handleRoll();
                    rolled = true;
                }
            }

            // Handle List
            else if (cmd.type.equals("List")) {
                System.out.println(player.getResourceHand());
            }

            // Handle Build
            else if (cmd.type.equals("Build")) {
                handleBuild(cmd);
            }

            // End Turn
            else if (cmd.type.equals("Go")) {
                turnActive = false;
            }
        }
    }

    // --------------------------------------------
    // Roll Logic
    // --------------------------------------------
    public void handleRoll() {

        DistributeResources distribute = new DistributeResources(bank, players, randomizer, board);

        int roll = distribute.executeDistribution();

        System.out.println("Rolled: " + roll);



    }

    // --------------------------------------------
    // Build Logic
    // --------------------------------------------
    public void handleBuild(Command cmd) {

        Build action = null;

        // Create correct build action
        if (cmd.buildType.equals("settlement")) {

            action = new BuildSettlement(
                    player, board, randomizer, bank, placementValidator);

            Node node = board.getNode(cmd.nodeId);

            if (!action.executeWithPlacement(node)) {
                System.out.println("Invalid settlement placement.");
            }
        }

        else if (cmd.buildType.equals("city")) {

            action = new BuildCity(
                    player, board, randomizer, bank, placementValidator);

            Node node = board.getNode(cmd.nodeId);

            if (!action.executeWithPlacement(node)) {
                System.out.println("Invalid city placement.");
            }
        }

        else if (cmd.buildType.equals("road")) {

            action = new BuildRoad(
                    player, board, randomizer, bank, placementValidator);

            Edge edge =
                    board.getEdgeBetweenNodes(cmd.fromNodeId, cmd.toNodeId);

            if (!action.executeWithPlacement(edge)) {
                System.out.println("Invalid road placement.");
            }
        }

        else {
            System.out.println("Unknown build type.");
        }
    }

}