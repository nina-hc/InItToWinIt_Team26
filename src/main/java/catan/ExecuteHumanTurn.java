package catan;

import java.util.Scanner;


public class ExecuteHumanTurn {

    private Player player;
    private Board board;
    private Randomizer randomizer;
    private Bank bank;
    private PlacementValidator placementValidator;
    private Player[] players;
    private Parser parser;
    private HumanTurn humanTurn;
    private boolean rolled = false;

    public ExecuteHumanTurn(Player player, Board board, Randomizer randomizer, Bank bank, PlacementValidator validator, Player[] players) {

        this.player = player;
        this.board = board;
        this.randomizer = randomizer;
        this.bank = bank;
        this.placementValidator = validator;
        this.players = players;

        this.parser = new Parser();
        this.humanTurn = new HumanTurn(player, board, randomizer, bank, validator, players);
    }


    public void executeHumanTurn(Game game, Scanner scanner) {


        boolean turnActive = true;

        while (turnActive && scanner.hasNextLine()) {

            System.out.print("Type in command > ");
            String input = scanner.nextLine();

            Command cmd = parser.parse(input);

            if (!cmd.valid) {
                System.out.println("Invalid command.");
                continue;
            }

            turnActive = handleCommand(cmd, player);
        }


    }


    private boolean handleCommand(Command cmd, Player player) {
        //if parser marked command as invalid, notify user and continue turn
        if (!cmd.valid) {
            System.out.println("Invalid command.");
            return true; //keep turn active
        }

        //handle roll command
        if ("roll".equals(cmd.type)) {

            //prevent rolling more than once per turn
            if (rolled) {
                System.out.println("You already rolled this turn.");
            }
            else {
                humanTurn.handleRoll(); //execute roll logic
                rolled = true; //mark that player has rolled
            }
            return true;//continue turn

        }

        //handle list command
        else if ("list".equals(cmd.type)) {
            System.out.println(player.getResourceHand());
            return true;

        }
        //handle build command (delegate to HumanTurn logic)
        else if ("build".equals(cmd.type)) {

            humanTurn.handleBuild(cmd);// build settlement/city/road
            return true;

        }
        //handle go command
        else if ("go".equals(cmd.type)) {
            return false; // end turn

        }

        else {
            System.out.println("Unknown command.");
            return true;
        }
    }
}
