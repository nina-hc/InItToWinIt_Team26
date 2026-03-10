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
    private HumanTurn humanTurn = new HumanTurn(player, board, randomizer, bank, placementValidator, players);
    private boolean rolled = false;


    public void executeHumanTurn(Game game, Player player) {

        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();

        boolean turnActive = true;

        while (turnActive) {

            System.out.print("> ");
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
