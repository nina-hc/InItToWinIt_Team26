package catan;

public class Command {
    public String type;        // Roll, Go, List, Build
    public String buildType;   // settlement, city, road
    public int nodeId;
    public int fromNodeId;
    public int toNodeId;
    public boolean valid;

    public Command() {
        this.valid = false;
    }

    public static Command invalid() {
        return new Command();
    }

    @Override
    public String toString() {
        if (!valid) return "Invalid command";

        if ("Build".equals(type)) {
            if ("road".equals(buildType)) {
                return "Build road from " + fromNodeId + " to " + toNodeId;
            }
            return "Build " + buildType + " at " + nodeId;
        }

        return type;
    }

}
