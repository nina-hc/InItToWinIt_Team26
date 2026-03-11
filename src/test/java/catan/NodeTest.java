package catan;

/*import main.java.Board;
import main.java.City;
import main.java.Node;
import main.java.Settlement;*/
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
    void testIsOccupiedAfterUpgrade() {
        //create
        Node node = new Node(1);
        //test
        Settlement settlement = new Settlement(node, 1);
        node.placeSettlement(settlement);

        City city = new City(node, 1);
        node.upgradeToCity(city);

        boolean isOccupied = node.isOccupied();
        //check
        assertTrue(isOccupied);
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
        assertEquals(settlement, node.getBuilding());
        assertEquals(1, node.getBuilding().getOwnerID());
    }

    @Test (expected = IllegalStatException.class)
    void testPlaceSettlementOnOccupiedNode() {
        //create
        Node node = new Node(1);
        //test
        Settlement settlement1 = new Settlement(node, 1);
        node.placeSettlement(settlement1);

        Settlement settlement2 = new Settlement(node, 2);
        //check
        node.placeSettlement(settlement2);  //should throw exception
    }

    //=======================================================
    @Test
    void testUpgradeToCity() {
        //create
        Node node = new Node(1);
        //test
        Settlement settlement = new Settlement(node, 1);
        City city = new City(node, 1);

        node.placeSettlement(settlement);
        node.upgradeToCity(city);
        boolean isOccupied = node.isOccupied();
        //check
        assertTrue(isOccupied);
        assertEquals(city, node.getBuilding());
        assertEquals(1, node.getBuilding().getOwnerID());
    }

    @Test (expected = IllegalStateException.class)
    void testUpgradeToCityOnEmptyNode() {
        //create
        Node node = new Node(1);
        //test
        City city = new City(node, 1);
        node.upgradeToCity(city);

    }

    @Test (expected = IllegalStateException.class)
    void testUpgradeToCityWithWrongOwner() {
        //create
        Node node = new Node(1);
        //test
        Settlement settlement = new Settlement(node, 1);
        node.placeSettlement(settlement);

        City city = new City(node, 2);
        node.upgradeToCity(city);
    }


    //=======================================================
    @Test
    void testGetNodeID() {
        //create
        Node node = new Node(41);
        //test
        int nodeID = node.getNodeID();
        //check
        assertSame(41, nodeID);
    }

    //=======================================================
    @Test
    void testGetBuilding() {
        //create
        Node node = new Node(41);
        //test
        assertNull(node,getBuilding());  //should start null
        Settlement settlement = new Settlement(node, 1);
        node.placeSettlement(settlement);

        //check
        assertNotNull(node.getBuilding());
        assertEquals(settlement, node.getBuilding());
    }



}