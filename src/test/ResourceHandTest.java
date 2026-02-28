package test;

import main.java.ResourceHand;
import main.java.ResourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceHandTest {

    @Test
    void testAddResource() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.BRICK, 1);
        int numResource = hand.getResource(ResourceType.BRICK);
        //check
        assertEquals(1, numResource);
    }


    //=======================================================
    @Test
    void testGetResource() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.ORE, 2);
        int numResource = hand.getResource(ResourceType.ORE);
        //check
        assertEquals(2, numResource);
    }

    //could maybe add some tests that throw exceptions for tying get -num or more than 19 cards


    //=======================================================
    @Test
    void testHasResource() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.GRAIN, 2);
        boolean hasEnough = hand.hasResource(ResourceType.GRAIN, 2);
        //check
        assertTrue(hasEnough);
    }

    @Test
    void testHasResourceNotEnough() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.GRAIN, 2);
        boolean notEnough = hand.hasResource(ResourceType.GRAIN, 3);
        //check
        assertFalse(notEnough);
    }


    //=======================================================
    @Test
    void testCanBuyRoad() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.BRICK, 1);
        hand.addResource(ResourceType.LUMBER, 1);
        boolean canBuy = hand.canBuyRoad();
        //check
        assertTrue(canBuy);
    }

    @Test
    void testCantBuyRoad() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.BRICK, 1);    //should have 0 lumber
        boolean canBuy = hand.canBuyRoad();
        //check
        assertFalse(canBuy);
    }

    //=======================================================
    @Test
    void testCanBuySettlement() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.BRICK, 1);
        hand.addResource(ResourceType.LUMBER, 1);
        hand.addResource(ResourceType.WOOL, 1);
        hand.addResource(ResourceType.GRAIN, 1);

        boolean canBuy = hand.canBuySettlement();
        //check
        assertTrue(canBuy);
    }

    @Test
    void testCantBuySettlement() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.BRICK, 1);
        hand.addResource(ResourceType.LUMBER, 1);
        hand.addResource(ResourceType.WOOL, 1);
        hand.addResource(ResourceType.GRAIN, 1);

        boolean canBuy = hand.canBuySettlement();
        //check
        assertFalse(canBuy);
    }


    //=======================================================
    @Test
    void testCanBuyCity() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.ORE, 3);
        hand.addResource(ResourceType.GRAIN, 2);

        boolean canBuy = hand.canBuyCity();
        //check
        assertTrue(canBuy);
    }

    @Test
    void testCantBuyCity() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.ORE, 3);
        hand.addResource(ResourceType.GRAIN, 1);

        boolean canBuy = hand.canBuyCity();
        //check
        assertFalse(canBuy);
    }

    //=======================================================
    @Test
    void testPayForRoad() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.BRICK, 3);
        hand.addResource(ResourceType.LUMBER, 2);

        hand.payForRoad();

        int numBrick = hand.getResource(ResourceType.BRICK);
        int numLumber = hand.getResource(ResourceType.LUMBER);

        //check
        assertEquals(2, numBrick);
        assertEquals(1, numLumber);
    }


    //=======================================================
    @Test
    void testPayForSettlement() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.BRICK, 1);
        hand.addResource(ResourceType.LUMBER, 1);
        hand.addResource(ResourceType.WOOL, 1);
        hand.addResource(ResourceType.GRAIN, 1);

        hand.payForSettlement();

        int numBrick = hand.getResource(ResourceType.BRICK);
        int numLumber = hand.getResource(ResourceType.LUMBER);
        int numWool = hand.getResource(ResourceType.WOOL);
        int numGrain = hand.getResource(ResourceType.GRAIN);

        //check
        assertEquals(0, numBrick);
        assertEquals(0, numLumber);
        assertEquals(0, numWool);
        assertEquals(0, numGrain);
    }


    //=======================================================
    @Test
    void testPayForCity() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.ORE, 3);
        hand.addResource(ResourceType.GRAIN, 2);

        hand.payForCity();

        int numOre = hand.getResource(ResourceType.ORE);
        int numGrain = hand.getResource(ResourceType.GRAIN);

        //check
        assertEquals(0, numOre);
        assertEquals(0, numGrain);
    }


    //=======================================================
    @Test
    void testTotalPlayerCard() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.BRICK, 2);
        hand.addResource(ResourceType.WOOL, 1);
        hand.addResource(ResourceType.GRAIN, 2);

        int total = hand.totalPlayerCard();
        //check
        assertEquals(5, total);
    }


    //=======================================================
    @Test
    void testToString() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.BRICK, 2);
        hand.addResource(ResourceType.WOOL, 1);

        String result = hand.toString();
        //check
        assertTrue(result.contains("Brick:2"));
        assertTrue(result.contains("Wool:1"));
    }
}