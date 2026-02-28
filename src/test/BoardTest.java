package test;

import main.java.*;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.support.Resource;

import java.util.List;

import static main.java.ResourceType.BRICK;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testIsAdjacent() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(0);
        Node node2 = board.getNode(5);
        boolean isAdjacent = board.isAdjacent(0, 5);
        //check
        assertTrue(isAdjacent);
    }

    @Test
    void testIsNotAdjacent() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(1);
        Node node2 = board.getNode(24);
        boolean isNotAdjacent = board.isAdjacent(1, 24);
        //check
        assertFalse(isNotAdjacent);
    }

    @Test
    void testIsNotAdjacentEdgeOneNonExistingNode() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(1);
        Node node2 = board.getNode(67);
        boolean isNotAdjacent = board.isAdjacent(1, 67);
        //check
        assertFalse(isNotAdjacent);
    }

    @Test
    void testIsNotAdjacentEdgeTwoNonExistingNode() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(-1);
        Node node2 = board.getNode(67);
        boolean isNotAdjacent = board.isAdjacent(-1, 67);
        //check
        assertFalse(isNotAdjacent);
    }

    @Test
    void testIsNotAdjacentEdgeSameNode() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(1);
        Node node2 = board.getNode(1);
        boolean isNotAdjacent = board.isAdjacent(1, 1);
        //check
        assertFalse(isNotAdjacent);
    }


    //=======================================================
    @Test
    void testPlaceSettlementOnMat() {
        //create
        Board board = new Board();
        //test
        Node node = new Node(1);
        boolean place = board.placeSettlementOnMat(1, 1);
        //check
        assertTrue(place);
    }


    @Test
    void testPlaceSettlementOnMatNodeDoesntExist() {
        //create
        Board board = new Board();
        //test
        Node node = new Node(-3);
        boolean place = board.placeSettlementOnMat(-3, 1);
        //check
        assertFalse(place);
    }

    @Test
    void testPlaceSettlementOnOccupiedNode() {
        //create
        Board board = new Board();
        //test
        Node node = new Node(14);
        board.placeSettlementOnMat(14, 2);

        boolean place = board.placeSettlementOnMat(14, 1);
        //check
        assertFalse(place);
    }

    //=======================================================
    @Test
    void testPlaceCityOnMat() {
        //create
        Board board = new Board();
        //test
        Node node = new Node(1);
        boolean place = board.placeSettlementOnMat(1, 1);
        boolean placeCity = board.placeCityOnMat(1, 1);
        //check
        assertTrue(placeCity);
    }


    @Test
    void testPlaceCityOnMatNodeDoesntExist() {
        //create
        Board board = new Board();
        //test
        Node node = new Node(-3);
        boolean place = board.placeCityOnMat(-3, 1);
        //check
        assertFalse(place);
    }

    @Test
    void testPlaceCityOnOccupiedNode() {
        //create
        Board board = new Board();
        //test
        Node node = new Node(14);
        board.placeSettlementOnMat(14, 2);

        boolean place = board.placeCityOnMat(14, 1);
        //check
        assertFalse(place);
    }

    @Test
    void testPlaceCityOnEmptyNode() {
        //create
        Board board = new Board();
        //test
        Node node = new Node(14);

        boolean place = board.placeCityOnMat(14, 1);
        //check
        assertFalse(place);
    }


    //=======================================================
    @Test
    void testPlaceRoad() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(0);
        Node node2 = board.getNode(5);
        Road road = board.placeRoad(0, 5, 2);
        boolean hasRoad = board.hasRoad(0, 5);
        //check
        assertTrue(hasRoad);
    }

    @Test
    void testPlaceRoadNodeArentNeighbors() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(1);
        Node node2 = board.getNode(27);
        Road road = board.placeRoad(1, 27, 2);
        boolean hasRoad = board.hasRoad(1, 27);
        //check
        assertFalse(hasRoad);
    }

    @Test
    void testPlaceRoadOneNodeDontExist() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(1);
        Node node2 = board.getNode(67);
        Road road = board.placeRoad(1, 67, 2);
        boolean hasRoad = board.hasRoad(1, 67);
        //check
        assertFalse(hasRoad);
    }

    @Test
    void testPlaceRoadBothNodeDontExist() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(-11);
        Node node2 = board.getNode(67);
        Road road = board.placeRoad(-11, 67, 2);
        boolean hasRoad = board.hasRoad(-11, 67);
        //check
        assertFalse(hasRoad);
    }

    @Test
    void testPlaceRoadSameNode() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(1);
        Node node2 = board.getNode(1);
        Road road = board.placeRoad(1, 1, 2);
        boolean hasRoad = board.hasRoad(1, 1);
        //check
        assertFalse(hasRoad);
    }


    //=======================================================
    @Test
    void testHasRoad() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(0);
        Node node2 = board.getNode(5);
        Road road = board.placeRoad(0, 5, 2);
        boolean hasRoad = board.hasRoad(0, 5);
        //check
        assertTrue(hasRoad);
    }

    @Test
    void testHasNoRoad() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(1);
        Node node2 = board.getNode(5);
        boolean hasRoad = board.hasRoad(1, 5);
        //check
        assertFalse(hasRoad);
    }

    @Test
    void testHasNoRoadNodesArentEvenConnected() {
        //create
        Board board = new Board();
        //test
        Node node1 = board.getNode(1);
        Node node2 = board.getNode(21);
        boolean hasRoad = board.hasRoad(1, 21);
        //check
        assertFalse(hasRoad);
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


    //=======================================================
    @Test
    void testGetNode() {
        //create
        Board board = new Board();
        //test
        Node node = new Node(1);
        Node currentNode = board.getNode(1);
        //check
        assertSame(node, currentNode);
    }


    //=======================================================
    @Test
    void testGetNeighbors() {
        //create
        Board board = new Board();
        //test
        List<Integer> neighbors = board.getNeighbors(1);
        //check
        assertTrue(neighbors.contains(0));
        assertTrue(neighbors.contains(2));
        assertTrue(neighbors.contains(6));
    }


}