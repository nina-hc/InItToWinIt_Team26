package test;

import main.java.ResourceType;
import main.java.Tile;
import org.junit.jupiter.api.Test;

import static main.java.ResourceType.GRAIN;
import static main.java.ResourceType.ORE;
import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    void testConstructTile() {
        //create
        Tile tile = new Tile(1, 11, GRAIN);
        //test
        int tileID = tile.getTileID();
        int rollNum = tile.getTileRollNum();
        ResourceType resource = tile.getResourceType();
        //check
        assertEquals(1, tileID);
        assertEquals(11, rollNum);
        assertEquals(GRAIN, resource);
    }


    //=======================================================
    @Test
    void testSetNodes() {
        //create
        Tile tile = new Tile(1, 11, GRAIN);
        //test
        int[] nodes = { 1, 6, 7, 8, 9, 2 };
        tile.setNodes(nodes);
        int[] nodeIDs = tile.getNodeIDs();
        //check
        assertEquals(nodes, nodeIDs);
    }
}