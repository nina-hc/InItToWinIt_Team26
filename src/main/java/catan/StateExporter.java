package catan;

import java.io.FileWriter;
import java.io.IOException;

public class StateExporter {

    /**
     * This helper method converts players to a colour
     * This is necessary for the visualizer
     *
     * @param playerID the player ID
     * @return colour associated with the playerID
     */
    private static String getColor(int playerID) {
        switch (playerID) {
            case 1: return "BLUE";
            case 2: return "RED";
            case 3: return "ORANGE";
            case 4: return "WHITE";
            default: return "BLUE";
        }
    }

    /**
     * method to export the state of the board to the visualizer
     * it updates the state.json file everything something is built
     *
     * @param board the board
     */
    public static void exportState(Board board) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("visualizer/state.json");
            writer.write("{\n");

            // =====================
            // ROADS
            // =====================
            writer.write("  \"roads\": [\n");

            boolean first = true;
            for (Edge edge : board.getAllEdges()) {
                Road road = edge.getRoad();
                if (road != null) {
                    if (!first) writer.write(",\n");

                    writer.write("    { \"a\": " + edge.getNodeA().getNodeID() +
                            ", \"b\": " + edge.getNodeB().getNodeID() +
                            ", \"owner\": \"" + getColor(road.getOwner()) + "\" }");
                    first = false;
                }
            }

            writer.write("\n  ],\n");

            // =====================
            // BUILDINGS
            // =====================
            writer.write("  \"buildings\": [\n");
            first = true;
            for (Node node : board.getAllNodes()) {
                if (node.isOccupied()) {
                    Building building = node.getBuilding();
                    String type = building instanceof City ? "CITY" : "SETTLEMENT";

                    if (!first) writer.write(",\n");

                    writer.write("    { \"node\": " + node.getNodeID() +
                            ", \"owner\": \"" + getColor(building.getOwnerID()) +
                            "\", \"type\": \"" + type + "\" }");

                    first = false;
                }
            }

            writer.write("\n  ]\n}");
            //this is for debugging
            System.out.println("State exported successfully to visualizer/state.json");
            writer.close();

        } catch (IOException e) {
            System.out.println("ERROR: state export failed");
            //e.printStackTrace();      this should be uncommented for debugging purposes only
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    //e.printStackTrace();    this should be uncommented for debugging purposes only
                }
            }
        }
    }
}

