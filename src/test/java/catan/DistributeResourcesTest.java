package catan;

import catan.*;
import org.junit.jupiter.api.Test;

import static catan.ResourceType.LUMBER;
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

        //make sure tile 0 has its nodes
        board.getTile(0).setNodes(new int[]{0, 1, 2, 3, 4, 5});

        //place settlement on node 2
        Node node2 = board.getNode(2);
        Settlement settlement = new Settlement(node2, 1);
        node2.placeSettlement(settlement);

        int before = players[0].getResourceHand().getResource(LUMBER);
        distributeResources.executeDistribution();
        int after = players[0].getResourceHand().getResource(LUMBER);

        //check
        assertEquals(before + 1, after);
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

        //make sure tile 0 has its nodes
        board.getTile(0).setNodes(new int[]{0, 1, 2, 3, 4, 5});

        //place settlement on node 2
        Node node2 = board.getNode(2);
        Settlement settlement = new Settlement(node2, 1);
        node2.placeSettlement(settlement);

        //upgrade to a city
        City city = new City(node2, 1);
        node2.upgradeToCity(city);

        int before = players[0].getResourceHand().getResource(LUMBER);
        distributeResources.executeDistribution();
        int after = players[0].getResourceHand().getResource(LUMBER);

        //check
        assertEquals(before + 2, after);
    }


    //========================================================
    //tests added after refactors

    @Test
    void testExecuteDistributionWithRobberOnTile() {
        //create
        Board board = new Board();
        Bank bank = new Bank();
        Randomizer randomizer = new FixedRandomizer(7); //can choose a roll value! its 7 for robber
        Player[] players = new Player[1];
        players[0] = new Player(1);

        DistributeResources distributeResources = new DistributeResources(bank, players, randomizer, board);

        //make sure tile 0 has its nodes
        board.getTile(0).setNodes(new int[]{0, 1, 2, 3, 4, 5});

        //place settlement on node 2
        Node node2 = board.getNode(2);
        Settlement settlement = new Settlement(node2, 1);
        node2.placeSettlement(settlement);

        //place robber
        board.getTile(0).setRobber(true);

        int before = players[0].getResourceHand().getResource(LUMBER);
        distributeResources.executeDistribution();
        int after = players[0].getResourceHand().getResource(LUMBER);

        //check
        assertEquals(before, after);    //should be the same because robber stopped them from collecting

    }

    @Test
    void testExecuteDistributionWitNoOneOnTile() {
        //create
        Board board = new Board();
        Bank bank = new Bank();
        Randomizer randomizer = new FixedRandomizer(7); //can choose a roll value!

        Player[] players = new Player[1];
        players[0] = new Player(1);

        DistributeResources distributeResources = new DistributeResources(bank, players, randomizer, board);

        //test
        int resourcesBefore = players[0].getResourceHand().totalPlayerCard();
        distributeResources.executeDistribution();
        int resourcesAfter = players[0].getResourceHand().totalPlayerCard();

        //check
        assertEquals(resourcesBefore, resourcesAfter);
    }
}