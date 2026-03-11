//package catan;
//
//import main.java.ResourceType;
//import main.java.Tile;
//import org.junit.jupiter.api.Test;
//
//import static main.java.ResourceType.GRAIN;
//import static main.java.ResourceType.ORE;
//import static org.junit.jupiter.api.Assertions.*;
//
//class TileTest {
//
//    @Test
//    void testConstructTile() {
//        //create
//        Tile tile = new Tile(1, 11, GRAIN);
//        //test
//        int tileID = tile.getTileID();
//        int rollNum = tile.getTileRollNum();
//        ResourceType resource = tile.getResourceType();
//        boolean hasRobber = tile.getHasRobber();
//        //check
//        assertEquals(1, tileID);
//        assertEquals(11, rollNum);
//        assertEquals(GRAIN, resource);
//        assertFalse(hasRobber); //robber starts false
//    }
//
//
//    //=======================================================
//    @Test
//    void testSetNodes() {
//        //create
//        Tile tile = new Tile(1, 11, GRAIN);
//        //test
//        int[] nodes = { 1, 6, 7, 8, 9, 2 };
//        tile.setNodes(nodes);
//        int[] nodeIDs = tile.getNodeIDs();
//        //check
//        assertEquals(nodes, nodeIDs);
//    }
//
//    //=======================================================
//    @Test
//    void testSetRobber() {
//        //create
//        Tile tile = new Tile(1, 11, GRAIN);
//        //test
//        tile.setRobber(true);
//        //check
//        assertTrue(tile.getHasRobber());
//    }
//
//    //=======================================================
//    @Test
//    void testGetTileID() {
//        //create
//        Tile tile = new Tile(1, 11, GRAIN);
//        //test
//        int tileID = tile.getTileID();
//        //check
//        assertEquals(1, tileID);
//    }
//
//    //=======================================================
//    @Test
//    void testGetTileRollNum() {
//        //create
//        Tile tile = new Tile(1, 11, GRAIN);
//        //test
//        int rollNum = tile.getTileRollNum();
//        //check
//        assertEquals(11, rollNum);
//    }
//
//    //=======================================================
//    @Test
//    void testGetTileResourceType() {
//        //create
//        Tile tile = new Tile(1, 11, GRAIN);
//        //test
//        ResourceType resource = tile.getResourceType();
//        //check
//        assertEquals(GRAIN, resource);
//    }
//
//    //=======================================================
//    @Test
//    void testGetNodeIDs() {
//        //create
//        Tile tile = new Tile(1, 11, GRAIN);
//        //test
//        int[] nodes = {1, 6, 7, 8, 9, 2};
//        tile.setNodes(nodes);
//        int[] nodeIDs = tile.getNodeIDs();
//        //check
//        assertNotNull(nodeIDs);
//        assertArrayEquals(nodes, nodeIDs);
//        assertEquals(6, nodeIDs.length);
//    }
//
//    //=======================================================
//    @Test
//    void testGetHasRobber() {
//        //create
//        Tile tile = new Tile(1, 11, GRAIN);
//        //test
//        assertFalse(tile.setHasRobber); //should start false
//        tile.setRobber(true);
//        //check
//        assertTrue(tile.getHasRobber());  //should be true now
//    }
//
//}