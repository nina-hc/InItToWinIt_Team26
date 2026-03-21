package catan;

import java.util.*;

/**
 * This class is for when 2 of the player's disconnected road segments are within
 * 2 edges of each other, it should attempt to connect them by building a road.
 *
 *
 * @author Serene Abou Sharaf
 * March 20, 2026
 */
public class RoadDistanceConstraint implements Constraint{


    /**
     * Checks if the player is able to build a road, has at least 2 disconnected road components
     * and those components must be within 2 edges of each other
     */
    @Override
    public boolean isApplicable(Player player, Player[] players, Board board, Bank bank, PlacementValidator placementValidator) {

        // Must be able to build a road
        if (!BuildCheckHelper.canBuildRoad(player, placementValidator)) {
            return false;
        }

        List<Set<Node>> components = getConnectedComponents(player);

        // Need at least 2 disconnected groups
        if (components.size() < 2) {
            return false;
        }

        return areComponentsClose(components, board);
    }

    /**
     * Resolves the constraint by building a road.
     * Attempts to build a road that connects two components.
     * Falls back to any valid road if a direct connection isn't found.
     */
    @Override
    public void resolveConstraint(Player player, Player[] players, Board board, Bank bank, Randomizer randomizer, PlacementValidator placementValidator) {

        List<Edge> validEdges = placementValidator.getValidRoadEdges(player);
        List<Set<Node>> components = getConnectedComponents(player);

        Edge bridgingEdge = findBridgingEdge(validEdges, components);

        if (bridgingEdge != null) {

            new BuildRoad(player, board, randomizer, bank, placementValidator).executeWithPlacement(bridgingEdge);
        } else {

            //build any valid road
            new BuildRoad(player, board, randomizer, bank, placementValidator).execute();
        }
    }



    /**
     * Builds connected components of the player's roads using BFS.
     * Each component represents a group of connected road nodes.
     */
    private List<Set<Node>> getConnectedComponents(Player player) {

        Map<Node, Set<Node>> graph = new HashMap<>();

        // Build adjacency graph from player's roads
        for (Road road : player.getPlayerRoads()) {
            Node a = road.getNodeA();
            Node b = road.getNodeB();

            graph.computeIfAbsent(a, k -> new HashSet<>()).add(b);
            graph.computeIfAbsent(b, k -> new HashSet<>()).add(a);
        }

        Set<Node> visited = new HashSet<>();
        List<Set<Node>> components = new ArrayList<>();

        // BFS to find connected components
        for (Node start : graph.keySet()) {

            if (visited.contains(start)) continue;

            Set<Node> component = new HashSet<>();
            Queue<Node> queue = new LinkedList<>();

            queue.add(start);
            visited.add(start);

            while (!queue.isEmpty()) {
                Node current = queue.poll();
                component.add(current);

                for (Node neighbor : graph.getOrDefault(current, Collections.emptySet())) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }

            components.add(component);
        }

        return components;
    }

    /**
     * Checks if any two components are within 2 edges of each other.
     */
    private boolean areComponentsClose(List<Set<Node>> components, Board board) {

        for (int i = 0; i < components.size(); i++) {

            Set<Node> expanded = expandByTwoEdges(components.get(i), board);

            for (int j = i + 1; j < components.size(); j++) {
                for (Node node : components.get(j)) {
                    if (expanded.contains(node)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Expands a set of nodes outward by 2 edges.
     */
    private Set<Node> expandByTwoEdges(Set<Node> componentNodes, Board board) {

        Set<Node> expanded = new HashSet<>(componentNodes);

        //Step 1: nodes 1 edge away
        Set<Node> oneAway = new HashSet<>();
        for (Node node : componentNodes) {
            for (Edge edge : board.getAdjacentEdges(node)) {
                oneAway.add(edge.getOppositeNode(node));
            }
        }
        expanded.addAll(oneAway);

        //Step 2: nodes 2 edges away
        for (Node node : oneAway) {
            for (Edge edge : board.getAdjacentEdges(node)) {
                expanded.add(edge.getOppositeNode(node));
            }
        }

        return expanded;
    }

    /**
     * Finds an edge that connects two different components.
     */
    private Edge findBridgingEdge(List<Edge> validEdges, List<Set<Node>> components) {

        for (Edge edge : validEdges) {

            int compA = findComponent(edge.getNodeA(), components);
            int compB = findComponent(edge.getNodeB(), components);

            if (compA != -1 && compB != -1 && compA != compB) {
                return edge;
            }
        }

        return null;
    }

    /**
     * Returns the index of the component containing the node.
     */
    private int findComponent(Node node, List<Set<Node>> components) {

        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).contains(node)) {
                return i;
            }
        }

        return -1;
    }
}
