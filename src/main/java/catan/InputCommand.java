package catan;

/**
 * Represents a parsed player command in the game.
 * A command can be:
 *  - Roll
 *  - Go
 *  - List
 *  - Build (settlement, city, or road)
 *
 * @author Marva Hassan
 * @version March 2026, McMaster University
 */
public class InputCommand {

    /**
     * Move that the human player wants to take
     * Includes: Roll, Go, List, Build
     */
    public String type;

    /**
     * Types of building
     * Includes: Settlement, City, Road
     */
    public String buildType;

    /**
     * Node ID for building cities and settlements
     */
    public int nodeId;

    /**
     * First node for building roads
     */
    public int fromNodeId;

    /**
     * Second node for building roads
     */
    public int toNodeId;

    /**
     * True for a valid command, false otherwise
     */
    public boolean valid;


    /**
     * InputCommand constructor
     */
    public InputCommand() {
        this.valid = false;
    }

    /**
     * Returns a new invalid Command object.
     * Used when parsing fails or input is incorrect.
     */
    public static InputCommand invalid() {
        return new InputCommand();
    }


    /**
     * Returns a description of the command.
     */
    @Override
    public String toString() {

        if (!valid) {
            return "Invalid command";
        }

        //print out description according to type of build
        if ("Build".equals(type)) {
            if ("road".equals(buildType)) {
                return "Build road from " + fromNodeId + " to " + toNodeId;
            }
            return "Build " + buildType + " at " + nodeId;
        }

        return type;
    }

}
