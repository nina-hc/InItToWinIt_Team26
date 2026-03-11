package a2_tests;

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

    @Test
    void testHasResourcesWithZero() {
        //create
        ResourceHand hand = new ResourceHand();

        //test
        boolean hasZero = hand.hasResource(ResourceType.ORE, 0);
        //check
        assertTrue(hasZero);
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
        Bank bank = new Bank();
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.BRICK, 3);
        hand.addResource(ResourceType.LUMBER, 2);

        hand.payForRoad(bank);

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
        Bank bank = new Bank();
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.BRICK, 1);
        hand.addResource(ResourceType.LUMBER, 1);
        hand.addResource(ResourceType.WOOL, 1);
        hand.addResource(ResourceType.GRAIN, 1);

        hand.payForSettlement(bank);

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
        Bank bank = new Bank();
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.ORE, 3);
        hand.addResource(ResourceType.GRAIN, 2);

        hand.payForCity(bank);

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

    @Test
    void testTotalPlayerCardEmpty() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        assertEquals(0, hand.totalPlayerCard());
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
        assertTrue(result.contains("Lumber:0"));
        assertTrue(result.contains("Brick:2"));
        assertTrue(result.contains("Wool:1"));
        assertTrue(result.contains("Grain:0"));
        assertTrue(result.contains("Ore:0"));
    }

    //======================================================
    //added test cases after refactor
    @Test (expected = IllegalArgumentException.class)
    void testAddNegativeResources() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.BRICK, -1);
    }

    @Test (expected = IllegalArgumentException.class)
    void testAddDesertResource() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.DESERT, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    void testGetDesertResource() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.getResource(ResourceType.DESERT);
    }

    @Test (expected = IllegalArgumentException.class)
    void testHasDesertResource() {
        //create
        ResourceHand hand = new ResourceHand();
        //test
        hand.hasResource(ResourceType.DESERT, 1);
    }

    @Test (expected = IllegalArgumentException.class)
    void testPayForRoadNotEnoughResources() {
        //create
        Bank bank = new Bank();
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.BRICK, 1);    //not enough lumber
        //check
        hand.payForRoad(bank);
    }

    @Test (expected = IllegalArgumentException.class)
    void testPayForSettlementNotEnoughResources() {
        //create
        Bank bank = new Bank();
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.BRICK, 1);
        hand.addResource(ResourceType.LUMBER, 1);
        hand.addResource(ResourceType.WOOL, 1);    //not enough grain
        //check
        hand.payForSettlement(bank);
    }

    @Test (expected = IllegalArgumentException.class)
    void testPayForCityNotEnoughResources() {
        //create
        Bank bank = new Bank();
        ResourceHand hand = new ResourceHand();
        //test
        hand.addResource(ResourceType.ORE, 3);
        hand.addResource(ResourceType.GRAIN, 1);    //only 1 grain... not enough
        //check
        hand.payForCity(bank);
    }

    @Test
    void testRemoveRandomResource() {
        //create
        ResourceHand hand = new ResourceHand();

        //test
        hand.addResource(ResourceType.BRICK, 3);
        hand.addResource(ResourceType.LUMBER, 3);
        hand.addResource(ResourceType.WOOL, 3);

        int totatlBefore = hand.totalPlayerCard();
        List<ResourceType> removed = hand.removeRandomResource(2);

        //check
        assertEqual(2, removed.size()); //check that amount removed is correct amount
        assertEquals(totalBefore - 2, hand.totalPlayerCard());  //check that remaing cards in playes had is less than what it was before
    }

    @Test
    void testDiscardHalfForSevenEvenNumber() {
        //create
        ResourceHand hand = new ResourceHand();

        //test
        hand.addResource(ResourceType.BRICK, 2);
        hand.addResource(ResourceType.LUMBER, 2);
        hand.addResource(ResourceType.WOOL, 2); //6 cards total

        int totatlBefore = hand.totalPlayerCard();
        List<ResourceType> discarded = hand.discardHalfForSeven();

        //check
        assertEquals(3, discarded.size());
        assertEquals(totalBefore - 3, hand.totalPlayerCard());
    }

    @Test
    void testDiscardHalfForSevenOddNumber() {
        //create
        ResourceHand hand = new ResourceHand();

        //test
        hand.addResource(ResourceType.BRICK, 3);
        hand.addResource(ResourceType.LUMBER, 2);
        hand.addResource(ResourceType.WOOL, 2); //7 cards total

        int totatlBefore = hand.totalPlayerCard();
        List<ResourceType> discarded = hand.discardHalfForSeven();

        //check
        assertEqual(3, discarded.size());
        assertEquals(totalBefore - 3, hand.totalPlayerCard());
    }
}