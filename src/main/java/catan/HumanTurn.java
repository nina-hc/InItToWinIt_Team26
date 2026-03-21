package catan;

import java.util.Scanner;

/**
 * Handles a human player's turn, including rolling, building, listing resources,
 * and ending the turn. Enforces the rule that a player must roll before ending their turn.
 *
 * @author Marva Hassan
 * @version March 2026, McMaster University
 *
 * implemented Command Design Pattern
 * @author Serene Abou Sharaf
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

	/*State machine for turn logic*/
	private TurnStateMachine stateMachine = new TurnStateMachine();

    private CommandManager commandManager = new CommandManager();
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
    public HumanTurn(Player player, Board board, Randomizer randomizer, Bank bank,
                     PlacementValidator placementValidator, Player[] players) {

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
		while(!stateMachine.isTurnDone()){
			System.out.print("[Player " + player.getPlayerID() + "]: Type in command > ");
			String input = scanner.nextLine();

			//Parse input into a Command object
			InputCommand cmd = parser.parse(input);

            if ("undo".equalsIgnoreCase(cmd.type)) {
                if (commandManager.canUndo()) {
                    commandManager.undo();
                    System.out.println("Undid last action.");
                } else {
                    System.out.println("Nothing to undo.");
                }
                continue;
            }

            if ("redo".equalsIgnoreCase(cmd.type)) {
                if (commandManager.canRedo()) {
                    commandManager.redo();
                    System.out.println("Redid last undone action.");
                } else {
                    System.out.println("Nothing to redo.");
                }
                continue;
            }

			if(!cmd.valid){
				System.out.println("Invalid command");
				continue;
			}

			if(stateMachine.isValidOption(cmd.type)){
				//use execute helper method
				execute(cmd);
				//go to next state if applicable
				stateMachine.goToNextState(cmd.type);
			}

		}
    }


	private void execute(InputCommand cmd) {
		switch (cmd.type) {
			case "Roll":
				handleRoll();
				break;
			case "List":
				System.out.println(player.getResourceHand());
				break;
			case "Build":
				handleBuild(cmd);
				break;
			case "Go":
				break;
			default:
				System.out.println("Unknown command.");
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
    public void handleBuild(InputCommand cmd) {


        //Handle settlement build
        if ("settlement".equalsIgnoreCase(cmd.buildType)) {

            Node node = board.getNode(cmd.nodeId);

            if (node == null) {
                System.out.println("Invalid node.");
                return;
            }

            Build action = new BuildSettlement(player, board, randomizer, bank, placementValidator);
            BuildCommand cmdBuild = new BuildCommand(action, node);

            try {
                commandManager.executeCommand(cmdBuild);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            // print confirmation
            System.out.println("[Player " + player.getPlayerID() + "] built settlement at node " + cmd.nodeId);
        }

        //Handle city build
        if ("city".equalsIgnoreCase(cmd.buildType)) {

            Node node = board.getNode(cmd.nodeId);

            if (node == null) {
                System.out.println("Invalid node.");
                return;
            }

            if (!(node.getBuilding() instanceof Settlement)) {
                System.out.println("Must upgrade a settlement.");
                return;
            }

            Settlement settlement = (Settlement) node.getBuilding();

            Build action = new BuildCity(player, board, randomizer, bank, placementValidator);
            BuildCommand cmdBuild = new BuildCommand(action, settlement);

            try {
                commandManager.executeCommand(cmdBuild);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // print confirmation
            System.out.println("[Player " + player.getPlayerID() + "] upgraded settlement to city at node " + cmd.nodeId);
        }

        //Handle road build
        if ("road".equalsIgnoreCase(cmd.buildType)) {

            Edge edge = board.getEdgeBetweenNodes(cmd.fromNodeId, cmd.toNodeId);

            if (edge == null) {
                System.out.println("Invalid road.");
                return;
            }

            Build action = new BuildRoad(player, board, randomizer, bank, placementValidator);
            BuildCommand cmdBuild = new BuildCommand(action, edge);

            try {
                commandManager.executeCommand(cmdBuild);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            // print confirmation
            System.out.println("[Player " + player.getPlayerID() + "] built road between " + cmd.fromNodeId + " and " + cmd.toNodeId);
        }

    }

}