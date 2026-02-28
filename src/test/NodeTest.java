package test;

import main.java.Board;
import main.java.City;
import main.java.Node;
import main.java.Settlement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void testIsOccupied() {
        //create
        Node node = new Node(1);
        //test
        Settlement settlement = new Settlement(node, 1);

        node.placeSettlement(settlement);
        boolean isOccupied = node.isOccupied();
        //check
        assertTrue(isOccupied);
    }

    @Test
    void testIsNotOccupied() {
        //create
        Node node = new Node(1);
        //test
        boolean isOccupied = node.isOccupied();
        //check
        assertFalse(isOccupied);
    }


    @Test
    void testIsOccupiedEdgeNodeDoesntExist() {
        //create
        Node node = new Node(-1);
        //test
        Settlement settlement = new Settlement(node, 1);

        node.placeSettlement(settlement);
        boolean isOccupied = node.isOccupied();
        //check
        assertFalse(isOccupied);
    }


    //=======================================================
    @Test
    void testPlaceSettlement() {
        //create
        Node node = new Node(1);
        //test
        Settlement settlement = new Settlement(node, 1);

        node.placeSettlement(settlement);
        boolean isOccupied = node.isOccupied();
        //check
        assertTrue(isOccupied);
    }


    //=======================================================
    @Test
    void testPlaceCityOnSettlement() {
        //create
        Node node = new Node(1);
        //test
        Settlement settlement = new Settlement(node, 1);
        City city = new City(node, 1);

        node.placeSettlement(settlement);
        node.placeCity(city);
        boolean isOccupied = node.isOccupied();
        //check
        assertTrue(isOccupied);
    }

    @Test
    void testPlaceCityOnEmptyNode() {
        //create
        Node node = new Node(1);
        //test
        City city = new City(node, 1);

        node.placeCity(city);
        boolean isOccupied = node.isOccupied();
        //check
        assertFalse(isOccupied);
    }

    @Test (expected = IllegalArgumentException.class)
    void testPlaceCityOnNoNode() {
        //create
        Node node = new Node(1);
        //test
        City city = new City(node, 1);

        node.placeCity(city);
        boolean isOccupied = node.isOccupied();
        //check
        //assertFalse(isOccupied);
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
        assertSame(1, nodeID);
    }
}