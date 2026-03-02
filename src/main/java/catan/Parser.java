package catan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    // Patterns
    private static final Pattern ROLL =
            Pattern.compile("^roll$", Pattern.CASE_INSENSITIVE);

    private static final Pattern GO =
            Pattern.compile("^go$", Pattern.CASE_INSENSITIVE);

    private static final Pattern LIST =
            Pattern.compile("^list$", Pattern.CASE_INSENSITIVE);

    private static final Pattern BUILD_SETTLEMENT =
            Pattern.compile("^build\\s+settlement\\s+(\\d+)$", Pattern.CASE_INSENSITIVE);

    private static final Pattern BUILD_CITY =
            Pattern.compile("^build\\s+city\\s+(\\d+)$", Pattern.CASE_INSENSITIVE);

    private static final Pattern BUILD_ROAD =
            Pattern.compile("^build\\s+road\\s+(\\d+)\\s*,\\s*(\\d+)$", Pattern.CASE_INSENSITIVE);

    public Command parse(String input) {

        if (input == null) {
            return Command.invalid();
        }

        input = InputCleaner.clean(input);

        Command cmd = new Command();

        // Roll
        if (ROLL.matcher(input).matches()) {
            cmd.type = "Roll";
            cmd.valid = true;
            return cmd;
        }

        // Go
        if (GO.matcher(input).matches()) {
            cmd.type = "Go";
            cmd.valid = true;
            return cmd;
        }

        // List
        if (LIST.matcher(input).matches()) {
            cmd.type = "List";
            cmd.valid = true;
            return cmd;
        }

        // Build settlement
        Matcher settlement = BUILD_SETTLEMENT.matcher(input);
        if (settlement.matches()) {
            cmd.type = "Build";
            cmd.buildType = "settlement";
            cmd.nodeId = Integer.parseInt(settlement.group(1));
            cmd.valid = true;
            return cmd;
        }

        // Build city
        Matcher city = BUILD_CITY.matcher(input);
        if (city.matches()) {
            cmd.type = "Build";
            cmd.buildType = "city";
            cmd.nodeId = Integer.parseInt(city.group(1));
            cmd.valid = true;
            return cmd;
        }

        // Build road
        Matcher road = BUILD_ROAD.matcher(input);
        if (road.matches()) {
            cmd.type = "Build";
            cmd.buildType = "road";
            cmd.fromNodeId = Integer.parseInt(road.group(1));
            cmd.toNodeId = Integer.parseInt(road.group(2));
            cmd.valid = true;
            return cmd;
        }

        return Command.invalid();
    }
}
