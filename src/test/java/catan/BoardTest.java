package catan;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.support.*;

import java.util.List;


import static catan.ResourceType.BRICK;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testIsAdjacent() {
        //create
        Board board = new Board();
        //test
        boolean isAdjacent = board.isAdjacent(0, 5);
        //check
        assertTrue(isAdjacent);
    }

    @Test
    void testIsNotAdjacent() {
        //create
        Board board = new Board();
        //test
        boolean isNotAdjacent = board.isAdjacent(1, 24);
        //check
        assertFalse(isNotAdjacent);
    }

    @Test
    void testIsNotAdjacentEdgeOneNonExistingNode() {
        //create
        Board board = new Board();
        //test
        assertThrows (ArrayIndexOutOfBoundsException.class, () -> {
            board.isAdjacent(1, 67);
        });

    }

    @Test
    void testIsNotAdjacentEdgeTwoNonExistingNode() {
        //create
        Board board = new Board();
        //test
        assertThrows (ArrayIndexOutOfBoundsException.class, () -> {
            board.isAdjacent(-1, 67);
        });
    }

    @Test
    void testIsNotAdjacentEdgeSameNode() {
        //create
        Board board = new Board();
        //test
//        Node node1 = board.getNode(1);
//        Node node2 = board.getNode(1);
        boolean isNotAdjacent = board.isAdjacent(1, 1);
        //check
        assertFalse(isNotAdjacent);
    }


    //=======================================================
    @Test
    void testPlaceSettlement() {
        //create
        Board board = new Board();
        //test
        Node node = board.getNode(1);
        Settlement settlement = new Settlement(node, 1);
        node.placeSettlement(settlement);
        //check
        assertTrue(node.isOccupied());
    }


    @Test
    void testPlaceSettlementOnMatNodeDoesntExist() {
        //create
        Board board = new Board();
        //test... try to get a node that doesn't exist (negative ID or >=54)
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Node node = board.getNode(-3);  //negative
            node.placeSettlement(new Settlement(node, 1));
        });

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Node node = board.getNode(100); //way too large
            node.placeSettlement(new Settlement(node, 1));
        });

    }

    @Test
    void testPlaceSettlementOnOccupiedNode() {
        //create
        Board board = new Board();
        Node node = board.getNode(1);
        //test
        node.placeSettlement(new Settlement(node, 1));
        //check
        assertThrows(IllegalStateException.class, () -> {
            node.placeSettlement(new Settlement(node, 2));
        });
    }


    //=======================================================
    @Test
    void testUpgradeSettlementToCity() {
        //create
        Board board = new Board();
        Node node = board.getNode(2);

        //test
        Settlement settlement = new Settlement(node, 1);
        node.placeSettlement(settlement);

        City city = new City(node, 1);
        node.upgradeToCity(city);

        //check
        assertTrue(node.isOccupied());
        assertTrue(node.getBuilding() instanceof City);
        assertEquals(1, node.getBuilding().getOwnerID());
    }

    @Test
    void testUpgradeWithoutSettlement() {
        //create
        Board board = new Board();
        Node node = board.getNode(2);

        //test
        City city = new City(node, 1);

        assertThrows(IllegalStateException.class, () -> {
            node.upgradeToCity(city);
        });
    }

    @Test
    void testUpgradeAnotherPlayersSettlementToCity() {
        //create
        Board board = new Board();
        Node node = board.getNode(3);

        //test
        node.placeSettlement(new Settlement(node, 1));
        City city = new City(node, 2);

        assertThrows(IllegalStateException.class, () -> {
            node.upgradeToCity(city);
        });
    }


    //=======================================================
    @Test
    void testPlaceRoad() {
        //create
        Board board = new Board();
        //test
        Road road = board.placeRoad(0, 5, 2);
        //check
        assertNotNull(road);
        assertEquals(2, road.getOwner());

        //test and check road
        Edge edge = board.getEdgeBetweenNodes(0, 5);
        assertNotNull(edge);
        assertTrue(edge.hasRoad());
        assertEquals(road, edge.getRoad());

    }

    @Test
    void testPlaceRoadNodeArentNeighbors() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(1);
        Node node2 = board.getNode(27);

        assertThrows(IllegalArgumentException.class, () -> {
            board.placeRoad(1, 27, 2);
        });

    }

    @Test
    void testPlaceRoadOneNodeDoesntExist() {
        //create
        Board board = new Board();
        //test
        assertThrows(IllegalArgumentException.class, () -> {
            board.placeRoad(1, 67, 2);
        });

    }

    @Test
    void testPlaceRoadBothNodeDontExist() {
        //create
        Board board = new Board();
        //test
        assertThrows(IllegalArgumentException.class, () -> {
            board.placeRoad(-11, 67, 2);
        });
    }

    @Test
    void testPlaceRoadSameNode() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(1);
        Node node2 = board.getNode(1);

        assertThrows(IllegalArgumentException.class, () -> {
            board.placeRoad(1, 1, 2);
        });
    }


    //=======================================================
    @Test
    void testGetEdgeBetweenNodes() {
        //create
        Board board = new Board();
        //test
        Edge edge = board.getEdgeBetweenNodes(0, 5);
        //check
        assertNotNull(edge);
        assertEquals(0, edge.getNodeA().getNodeID());
        assertEquals(5, edge.getNodeB().getNodeID());
    }

    @Test
    void testGetEdgeBetweenNodesNotAdjacent() {
        //create
        Board board = new Board();
        //test
        Edge edge = board.getEdgeBetweenNodes(1, 28);
        //check
        assertNull(edge);
    }

    @Test
    void testGetAdjacentEdges() {
        //create
        Board board = new Board();
        Node node = board.getNode(1);
        //test
        List<Edge> adjacentEdges = board.getAdjacentEdges(node);
        assertEquals(3, adjacentEdges.size());   //3 is number of nodes/adjacdent edges

        //node 1 neighbors: 0, 2, 6
        boolean found0 = false;
        boolean found2 = false;
        boolean found6 = false;

        for (Edge edge : adjacentEdges) {
            int nodeA = edge.getNodeA().getNodeID();
            int nodeB = edge.getNodeB().getNodeID();

            //check that the edges were actually found
            if ( (nodeA == 1 && nodeB == 0) || (nodeA == 0 && nodeB == 1)) {
                found0 = true;
            }
            if ( (nodeA == 1 && nodeB == 2) || (nodeA == 2 && nodeB == 1)) {
                found2 = true;
            }
            if ( (nodeA == 1 && nodeB == 6) || (nodeA == 6 && nodeB == 1)) {
                found6 = true;
            }
        }

        //check
        assertTrue(found0);
        assertTrue(found2);
        assertTrue(found6);
    }

    //=======================================================
    @Test
    void testGetTile() {
        //create
        Board board = new Board();
        //test
        Tile tile = board.getTile(2);
        int tileID = tile.getTileID();
        int rollNum = tile.getTileRollNum();
        ResourceType resource = tile.getResourceType();
        //check
        assertEquals(2, tileID);
        assertEquals(8, rollNum);
        assertEquals(BRICK, resource);
    }

    @Test
    void testGetTileInvalidID() {
        //create
        Board board = new Board();
        //test
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            board.getTile(26);
        });

    }

    //=======================================================
    @Test
    void testGetNode() {
        //create
        Board board = new Board();
        //test
        Node node = board.getNode(1);
        int nodeID = node.getNodeID();
        //check
        assertEquals(1, nodeID);
    }

    @Test
    void testGetNodeInvalidID() {
        //create
        Board board = new Board();
        //test
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            board.getNode(67);
        });

    }



    //=======================================================
    //added cases after the refactor

    @Test
    void testGetAllEdges() {
        //create
        Board board = new Board();
        //test
        Edge[] edges = board.getAllEdges(); //the board should have 72 edges.. 54 nodes, 3 edge each, counted twice
        assertNotNull(edges);
        assertTrue(edges.length > 0);
    }

    @Test
    void testGetAllTiles() {
        //create
        Board board = new Board();
        //test
        Tile[] tiles = board.getAllTiles();
        //check
        assertEquals(19, tiles.length);
    }

    @Test
    void testGetAllNodes() {
        //create
        Board board = new Board();
        //test
        Node[] nodes = board.getAllNodes();
        //check
        assertEquals(54, nodes.length);
    }

    @Test
    void testGetPlayersAdjacentToTile() {
        //create
        Board board = new Board();

        //make players
        Player player1 = new Player(1);
        Player player2 = new Player(2);
        board.getPlayers().add(player1);
        board.getPlayers().add(player2);

        //get nodes adjacen to tile 0
        Tile tile = board.getTile(0);
        int[] nodeIDs = tile.getNodeIDs();

        //place settlements on two nodes
        Node node0 = board.getNode(nodeIDs[0]); //node 0
        node0.placeSettlement(new Settlement(node0, player1.getPlayerID()));

        Node node1 = board.getNode(nodeIDs[1]); //node 1
        node1.placeSettlement(new Settlement(node1, player2.getPlayerID()));

        //get players adjacent to the tile
        List<Player> adjacentPlayers = board.getPlayersAdjacentToTile(tile);

        //check
        assertEquals(2, adjacentPlayers.size());    //should be only 2 adjacent players
        assertTrue(adjacentPlayers.contains(player1));  //make sure player 1 is there
        assertTrue(adjacentPlayers.contains(player2));  //make sure that player 2 is there

    }
}