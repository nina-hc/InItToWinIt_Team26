package catan;

/**
 * Represents a parsed player command in the game.
 * A command can be:
 *  - Roll
 *  - Go
 *  - List
 *  - Build (settlement, city, or road)
 * @author Marva Hassan
 */
public class Command {

    public String type;        // Roll, Go, List, Build
    public String buildType;   // settlement, city, road
    public int nodeId;
    public int fromNodeId;
    public int toNodeId;
    public boolean valid;

    //constructor
    public Command() {
        this.valid = false;
    }

    /**
     * Returns a new invalid Command object.
     * Used when parsing fails or input is incorrect.
     */
    public static Command invalid() {
        return new Command();
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
