//package catan;
//
//import org.junit.jupiter.api.Test;
//import org.junit.platform.commons.support.*;
//
//import java.util.List;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BoardTest {
//
//    @Test
//    void testIsAdjacent() {
//        //create
//        Board board = new Board();
//        //test
////        Node node1 = board.getNode(0);
////        Node node2 = board.getNode(5);
//        boolean isAdjacent = board.isAdjacent(0, 5);
//        //check
//        assertTrue(isAdjacent);
//    }
//
//    @Test
//    void testIsNotAdjacent() {
//        //create
//        Board board = new Board();
//        //test
////        Node node1 = board.getNode(1);
////        Node node2 = board.getNode(24);
//        boolean isNotAdjacent = board.isAdjacent(1, 24);
//        //check
//        assertFalse(isNotAdjacent);
//    }
//
//    @Test (expected = ArrrayIndexOutOfBoundsException)
//    void testIsNotAdjacentEdgeOneNonExistingNode() {
//        //create
//        Board board = new Board();
//        //test
////        Node node1 = board.getNode(1);
////        Node node2 = board.getNode(67);
//        board.isAdjacent(1, 67);
//    }
//
//    @Test (expected = ArrrayIndexOutOfBoundsException)
//    void testIsNotAdjacentEdgeTwoNonExistingNode() {
//        //create
//        Board board = new Board();
//        //test
////        Node node1 = board.getNode(-1);
////        Node node2 = board.getNode(67);
//        board.isAdjacent(-1, 67);
//    }
//
//    @Test
//    void testIsNotAdjacentEdgeSameNode() {
//        //create
//        Board board = new Board();
//        //test
////        Node node1 = board.getNode(1);
////        Node node2 = board.getNode(1);
//        boolean isNotAdjacent = board.isAdjacent(1, 1);
//        //check
//        assertFalse(isNotAdjacent);
//    }
//
//
//    //=======================================================
//    @Test
//    void testPlaceSettlementOnMat() {
//        //create
//        Board board = new Board();
//        //test
//        Node node = board.getNode(1);
//        Settlement settlement = new Settlement(node, 1);
//        node.placeSettlement(settlement);
//        //check
//        assertTrue(node.isOccupied());
//    }
//
//
//    @Test
//    void testPlaceSettlementOnMatNodeDoesntExist() {
//        //create
//        Board board = new Board();
//        //test
//        Node node = new Node(-3);
//        boolean place = board.placeSettlementOnMat(-3, 1);
//        //check
//        assertFalse(place);
//    }
//
//    @Test
//    void testPlaceSettlementOnOccupiedNode() {
//        //create
//        Board board = new Board();
//        //test
//        Node node = new Node(14);
//        board.placeSettlementOnMat(14, 2);
//
//        boolean place = board.placeSettlementOnMat(14, 1);
//        //check
//        assertFalse(place);
//    }
//
//    //=======================================================
//    @Test
//    void testPlaceCityOnMat() {
//        //create
//        Board board = new Board();
//        //test
//        Node node = new Node(1);
//        boolean place = board.placeSettlementOnMat(1, 1);
//        boolean placeCity = board.placeCityOnMat(1, 1);
//        //check
//        assertTrue(placeCity);
//    }
//
//
//    @Test
//    void testPlaceCityOnMatNodeDoesntExist() {
//        //create
//        Board board = new Board();
//        //test
//        Node node = new Node(-3);
//        boolean place = board.placeCityOnMat(-3, 1);
//        //check
//        assertFalse(place);
//    }
//
//    @Test
//    void testPlaceCityOnOccupiedNode() {
//        //create
//        Board board = new Board();
//        //test
//        Node node = new Node(14);
//        board.placeSettlementOnMat(14, 2);
//
//        boolean place = board.placeCityOnMat(14, 1);
//        //check
//        assertFalse(place);
//    }
//
//    @Test
//    void testPlaceCityOnEmptyNode() {
//        //create
//        Board board = new Board();
//        //test
//        Node node = new Node(14);
//
//        boolean place = board.placeCityOnMat(14, 1);
//        //check
//        assertFalse(place);
//    }
//
//
//    //=======================================================
//    @Test
//    void testPlaceRoad() {
//        //create
//        Board board = new Board();
//        //test
//        Road road = board.placeRoad(0, 5, 2);
//        //check
//        assertNotNull(road);
//        assertEquals(2, road.getOwnerID());
//
//        //test and check road
//        Edge edge = board.getEdgeBetweenNodes(0, 5);
//        assertNotNull(edge);
//        assertTrue(edge.hasRoad());
//        assertEquals(road, edge.getRoad());
//
//    }
//
//    @Test (expected = IllegalArgumentException)
//    void testPlaceRoadNodeArentNeighbors() {
//        //create
//        Board board = new Board();
//        //test
//        Node node1 = board.getNode(1);
//        Node node2 = board.getNode(27);
//        board.placeRoad(1, 27, 2);
//    }
//
//    @Test (expected = IllegalArgumentException)
//    void testPlaceRoadOneNodeDontExist() {
//        //create
//        Board board = new Board();
//        //test
//        Node node1 = board.getNode(1);
//        Node node2 = board.getNode(67);
//        board.placeRoad(1, 67, 2);
//    }
//
//    @Test (expected = IllegalArgumentException)
//    void testPlaceRoadBothNodeDontExist() {
//        //create
//        Board board = new Board();
//        //test
//        Node node1 = board.getNode(-11);
//        Node node2 = board.getNode(67);
//        board.placeRoad(-11, 67, 2);
//    }
//
//    @Test (expected = IllegalArgumentException)
//    void testPlaceRoadSameNode() {
//        //create
//        Board board = new Board();
//        //test
//        Node node1 = board.getNode(1);
//        Node node2 = board.getNode(1);
//        board.placeRoad(1, 1, 2);
//    }
//
//
//    //=======================================================
//    @Test
//    void testGetEdgeBetweenNodes() {
//        //create
//        Board board = new Board();
//        //test
//        Edge edge = board.getEdgeBetweenNodes(0, 5);
//        //check
//        assertNotNull(edge);
//        assertEquals(0, edge.getNodeA(),getNodeID());
//        assertEquals(5, edge.getNodeB(),getNodeID());
//    }
//
//    @Test
//    void testGetEdgeBetweenNodesNotAdjacent() {
//        //create
//        Board board = new Board();
//        //test
//        Edge edge = board.getEdgeBetweenNodes(1, 28);
//        //check
//        assertNull(edge);
//    }
//
//    @Test
//    void testGetAdjacentEdges() {
//        //create
//        Board board = new Board();
//        Node node = board.getNode(1);
//        //test
//        List<Edge> adjacentEdges = board.getAdjacentEdges(node);
//        assertEquals(3, adjacentEdges.size());   //3 is number of nodes/adjacdent edges
//
//        //node 1 neighbors: 0, 2, 6
//        boolean found0 = false;
//        boolean found2 = false;
//        boolean found6 = false;
//
//        for (Edge edge : adjacentEdges) {
//            int nodeA = edge.getNodeA().getNodeID();
//            int nodeB = edge.getNodeB().getNodeID();
//
//            //check that the edges were actually found
//            if ( (nodeA == 1 && nodeB == 0) || (nodeA == 0 && nodeB == 1)) {
//                found0 = true;
//            }
//            if ( (nodeA == 1 && nodeB == 2) || (nodeA == 2 && nodeB == 1)) {
//                found2 = true;
//            }
//            if ( (nodeA == 1 && nodeB == 6) || (nodeA == 6 && nodeB == 1)) {
//                found6 = true;
//            }
//
//            //check
//            assertTrue(found0);
//            assertTrue(found2);
//            assertTrue(found6);
//        }
//    }
//
//    //=======================================================
//    @Test
//    void testGetTile() {
//        //create
//        Board board = new Board();
//        //test
//        Tile tile = board.getTile(2);
//        int tileID = tile.getTileID();
//        int rollNum = tile.getTileRollNum();
//        ResourceType resource = tile.getResourceType();
//        //check
//        assertEquals(2, tileID);
//        assertEquals(8, rollNum);
//        assertEquals(BRICK, resource);
//    }
//
//    @Test (expected = ArrayIndexOutOfBoundsException.class)
//    void testGetTileInvalidID() {
//        //create
//        Board board = new Board();
//        //test
//        board.getTile(26);
//    }
//
//    //=======================================================
//    @Test
//    void testGetNode() {
//        //create
//        Board board = new Board();
//        //test
//        Node node = board.getNode(1);
//        int nodeID = node.getNodeID();
//        //check
//        assertEquals(1, nodeID);
//    }
//
//    @Test (expected = ArrayIndexOutOfBoundsException.class)
//    void testGetNodeInvalidID() {
//        //create
//        Board board = new Board();
//        //test
//        board.getNode(67);
//    }
//
//
//
//    //=======================================================
//    //added cases after the refactor
//
//    @Test
//    void testGetAllEdges() {
//        //create
//        Board board = new Board();
//        //test
//        Edge[] edges = board.getAllEdges(); //the board should have 72 edges.. 54 nodes, 3 edge each, counted twice
//        assertNotNull(edge);
//        assertTrue(edges.length > 0);
//    }
//
//    @Test
//    void testGetAllTiles() {
//        //create
//        Board board = new Board();
//        //test
//        Tile[] tiles = board.getAllTiles();
//        //check
//        assertEquals(19, tiles.length);
//    }
//
//    @Test
//    void testGetAllNodes() {
//        //create
//        Board board = new Board();
//        //test
//        Node[] nodes = board.getAllNodes();
//        //check
//        assertEquals(54, nodes.length);
//    }
//
//    @Test
//    void testGetPlayersAdjacentToTile() {
//        //create
//        Board board = new Board();
//        Player player1 = new Player(1);
//        Player player2 = new Player(2);
//
//        //test
//        //place settlements on nodes adjacent to the tile 0 {0, 1, 2, 3, 4, 5}
//        board.placeSettlementOnMat(0, 1);
//        board.placeSettlementOnMat(3, 2);
//
//        Tile tile = board.getTile(0);
//
//        List<Player> adjacentPlayers = board.getPlayersAdjacentsToTile(tile);
//
//        //check
//        assertEquals(2, adjacentPlayers.size());
//    }
//
//
//}