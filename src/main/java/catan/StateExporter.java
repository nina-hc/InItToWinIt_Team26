package catan;

import java.io.FileWriter;
import java.io.IOException;

public class StateExporter {

    private static String getColor(int playerID) {
        switch (playerID) {
            case 1: return "BLUE";
            case 2: return "RED";
            case 3: return "ORANGE";
            case 4: return "WHITE";
            default: return "BLUE";
        }
    }

    public static void exportState(Board board) {
        try {
            FileWriter writer = new FileWriter("visualizer/state.json");
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
            writer.close();
            System.out.println("State exported successfully to visualizer/state.json");

        } catch (IOException e) {
            System.out.println("ERROR: state export failed");
            e.printStackTrace();
        }
    }
}























//import java.io.FileWriter;
//import java.io.IOException;
//
//public class StateExporter {
//
//    public static void exportState(Board board) {
//
//        try {
//            FileWriter writer = new FileWriter("visualizer/state.json");
//
//            writer.write("{\n");
//
//            // Roads
//            writer.write("  \"roads\": [\n");
//
//            boolean first = true;
//            for (Road r : board.getRoads()) {
//
//                if (!first) writer.write(",\n");
//
//                writer.write("    { \"a\": " + r.getNodeA() +
//                        ", \"b\": " + r.getNodeB() +
//                        ", \"owner\": \"" + r.getOwner() + "\" }");
//
//                first = false;
//            }
//
//            writer.write("\n  ],\n");
//
//            // Buildings
//            writer.write("  \"buildings\": [\n");
//
//            first = true;
//            for (Building b : board.getBuildings()) {
//
//                if (!first) writer.write(",\n");
//
//                String type = b.isCity() ? "CITY" : "SETTLEMENT";
//
//                writer.write("    { \"node\": " + b.getNode() +
//                        ", \"owner\": \"" + b.getOwner() +
//                        "\", \"type\": \"" + type + "\" }");
//
//                first = false;
//            }
//
//            writer.write("\n  ]\n}");
//
//            writer.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}