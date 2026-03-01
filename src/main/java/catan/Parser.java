package catan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses raw input strings into Command objects for game actions.
 * Supports commands: Roll, Go, List, Build (settlement, city, road).
 * Uses regular expressions to validate and extract parameters.
 *
 * @author Marva Hassan
 */
public class Parser {

    //Pattern for "roll" command
    private static final Pattern ROLL = Pattern.compile("^roll$");

    //Pattern for "go" command
    private static final Pattern GO = Pattern.compile("^go$");

    //Pattern for "list" command
    private static final Pattern LIST = Pattern.compile("^list$");

    //Pattern for "build settlement <nodeId>"
    private static final Pattern BUILD_SETTLEMENT = Pattern.compile("^build\\s+settlement\\s+(\\d+)$");

    //Pattern for "build city <nodeId>"
    private static final Pattern BUILD_CITY = Pattern.compile("^build\\s+city\\s+(\\d+)$");

    //Pattern for "build road <fromNodeId>, <toNodeId>"
    private static final Pattern BUILD_ROAD = Pattern.compile("^build\\s+road\\s+(\\d+)\\s*,\\s*(\\d+)$");


    /**
     * Parses an input string into a Command object.
     * Cleans the input, matches it against known patterns, and extracts parameters.
     *
     * @param input The raw input string from the player
     * @return A Command object representing the parsed action, or an invalid command if parsing fails
     */
    public Command parse(String input) {

        //Return invalid command if input is null
        if (input == null) {
            return Command.invalid();
        }

        //Clean input string
        input = InputCleaner.cleaner(input);

        Command cmd = new Command();

        //Handle Roll
        if (ROLL.matcher(input).matches()) {
            cmd.type = "Roll";
            cmd.valid = true;
            return cmd;
        }

        //Handle Go
        if (GO.matcher(input).matches()) {
            cmd.type = "Go";
            cmd.valid = true;
            return cmd;
        }

        //Handle List
        if (LIST.matcher(input).matches()) {
            cmd.type = "List";
            cmd.valid = true;
            return cmd;
        }

        //Handle Build Settlement
        Matcher settlement = BUILD_SETTLEMENT.matcher(input);
        if (settlement.matches()) {
            cmd.type = "Build";
            cmd.buildType = "settlement";
            cmd.nodeId = Integer.parseInt(settlement.group(1));
            cmd.valid = true;
            return cmd;
        }

        //Handle Build City
        Matcher city = BUILD_CITY.matcher(input);
        if (city.matches()) {
            cmd.type = "Build";
            cmd.buildType = "city";
            cmd.nodeId = Integer.parseInt(city.group(1));
            cmd.valid = true;
            return cmd;
        }

        //Handle Build Road
        Matcher road = BUILD_ROAD.matcher(input);
        if (road.matches()) {
            cmd.type = "Build";
            cmd.buildType = "road";
            cmd.fromNodeId = Integer.parseInt(road.group(1));
            cmd.toNodeId = Integer.parseInt(road.group(2));
            cmd.valid = true;
            return cmd;
        }

        //Return invalid command if no pattern matched
        return Command.invalid();
    }
}