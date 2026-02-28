package test;

import main.java.*;
import org.junit.jupiter.api.Test;

import static main.java.ResourceType.LUMBER;
import static org.junit.jupiter.api.Assertions.*;

class DistributeResourcesTest {

    //creating a fake randomizer for the sake of testing (can choose the roll instead of a random one each time)
    class FixedRandomizer extends Randomizer {
        private int fixedValue;

        //constructor
        public FixedRandomizer(int desiredValue) {
            this.fixedValue = desiredValue;
        }

        @Override
        public int rollDice() {
            return fixedValue;
        }
    }


    //======================================================
    @Test
    void testExecuteDistributionRollSeven() {
        //create
        Board board = new Board();
        Bank bank = new Bank();
        Randomizer randomizer = new FixedRandomizer(7); //can choose a roll value!

        Player[] players = new Player[1];
        players[0] = new Player(1);

        DistributeResources distributeResources = new DistributeResources(bank, players, randomizer, board);

        //test
        int roll = distributeResources.executeDistribution();

        //check
        assertEquals(7, roll);
    }

    @Test
    void testExecuteDistributionSettlementGetsOneResource() {
        //create
        Board board = new Board();
        Bank bank = new Bank();
        Randomizer randomizer = new FixedRandomizer(10); //can choose a roll value!
        //tile id: 0, roll num: 10, resource: lumber, attached nodes: { 0, 1, 2, 3, 4, 5 }

        Player[] players = new Player[1];
        players[0] = new Player(1);

        DistributeResources distributeResources = new DistributeResources(bank, players, randomizer, board);

        //test
        board.placeSettlementOnMat(2, 1);    //placed on node 2 for player 1
        int resourcesBefore = players[0].getResourceHand().getResource(LUMBER);

        distributeResources.executeDistribution();
        int resourcesAfter = players[0].getResourceHand().getResource(LUMBER);

        //check: before + 1 should = after
        assertEquals(resourcesBefore + 1, resourcesAfter);
    }

    @Test
    void testExecuteDistributionCityGetsTwoResources() {
        //create
        Board board = new Board();
        Bank bank = new Bank();
        Randomizer randomizer = new FixedRandomizer(10); //can choose a roll value!
        //tile id: 0, roll num: 10, resource: lumber, attached nodes: { 0, 1, 2, 3, 4, 5 }

        Player[] players = new Player[1];
        players[0] = new Player(1);

        DistributeResources distributeResources = new DistributeResources(bank, players, randomizer, board);

        //test
        board.placeSettlementOnMat(0, 1);
        board.placeCityOnMat(0, 1);

        int resourcesBefore = players[0].getResourceHand().getResource(LUMBER);

        distributeResources.executeDistribution();
        int resourcesAfter = players[0].getResourceHand().getResource(LUMBER);

        //check: before + 2 should = after
        assertEquals(resourcesBefore + 2, resourcesAfter);
    }


    //=======================================================
    @Test
    void testDistributeFromTile() {
        //create
        Board board = new Board();
        Bank bank = new Bank();
        Randomizer randomizer = new FixedRandomizer(10); //can choose a roll value!
        //tile id: 0, roll num: 10, resource: lumber, attached nodes: { 0, 1, 2, 3, 4, 5 }

        Player[] players = new Player[1];
        players[0] = new Player(1);

        DistributeResources distributeResources = new DistributeResources(bank, players, randomizer, board);

        //test
        board.placeSettlementOnMat(2, 1);    //placed on node 2 for player 1
        int resourcesBefore = players[0].getResourceHand().getResource(LUMBER);

        distributeResources.executeDistribution();
        int resourcesAfter = players[0].getResourceHand().getResource(LUMBER);

        //check: before + 1 should = after
        assertEquals(resourcesBefore + 1, resourcesAfter);
    }
}