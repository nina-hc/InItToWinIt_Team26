package a2_tests;

import catan.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuildCityTest {

    @Test
    void testUpgradeSettlementToCity() {
        Board board = new Board();
        Player player = new Player(1);

        Randomizer randomizer = new Randomizer();
        Bank bank = new Bank();
        PlacementValidator validator = new PlacementValidator(board);

        Node node = board.getNode(3);

        Settlement settlement = new Settlement(node, player.getPlayerID());
        node.placeSettlement(settlement);
        player.playerAddSettlement(settlement);

        BuildCity buildCity = new BuildCity(player, board, randomizer, bank, validator);

        player.getResourceHand().addResource(ResourceType.ORE,3);
        player.getResourceHand().addResource(ResourceType.GRAIN,2);

        buildCity.build(settlement);

        assertTrue(node.getBuilding() instanceof City);
        assertEquals(1, player.getPlayerCities().size());
        assertEquals(0, player.getPlayerSettlements().size());

        // Check resources reduced
        assertEquals(0, player.getResourceHand().getResource(ResourceType.ORE));
        assertEquals(0, player.getResourceHand().getResource(ResourceType.GRAIN));
    }

    @Test
    void testCityWithoutSettlementFails() {
        Board board = new Board();
        Player player = new Player(1);

        Randomizer randomizer = new Randomizer();
        Bank bank = new Bank();
        PlacementValidator validator = new PlacementValidator(board);

        BuildCity buildCity = new BuildCity(player, board, randomizer, bank, validator);

        Node node = board.getNode(4);

        player.getResourceHand().addResource(ResourceType.ORE,3);
        player.getResourceHand().addResource(ResourceType.GRAIN,2);

        buildCity.build(node);

        assertFalse(node.isOccupied());
        assertEquals(0, player.getPlayerCities().size());
    }
//
//    @Test
//    void testValidCityUpgrade() {
//        Game game = new Game(10);
//        Board board = new Board();
//        Randomizer randomizer = new Randomizer();
//        Player p = new Player(1);
//
//        ResourceHand hand = p.getResourceHand();
//        hand.addResource(ResourceType.ORE, 3);
//        hand.addResource(ResourceType.GRAIN, 2);
//
//        Node node = board.getNode(4);
//        Settlement s = new Settlement(node, p.getPlayerID());
//
//        p.playerAddSettlement(s);
//        node.placeSettlement(s);
//
//        BuildCity build = new BuildCity(p, board, randomizer);
//
//        build.build(s); // if you add public wrapper
//
//        // test passes if no exception
//    }
//
//
//    @Test
//    void testCityFailsWithoutResources() {
//        Game game = new Game(10);
//        Board board = new Board();
//        Randomizer randomizer = new Randomizer();
//        Player p = new Player(1);
//
//        Settlement s = new Settlement(board.getNode(4), p.getPlayerID());
//        p.playerAddSettlement(s);
//
//        BuildCity build = new BuildCity(p, board, randomizer);
//
//        try {
//            build.build(s);
//            fail("Expected IllegalStateException");
//        } catch (IllegalStateException ignored) {
//            // test passes
//        }
//    }
//
//    @Test
//    void testCityFailsIfNotOwner() {
//        Game game = new Game(10);
//        Board board = new Board();
//        Randomizer randomizer = new Randomizer();
//        Player p = new Player(1);
//
//        Node node = board.getNode(4);
//        Settlement s = new Settlement(node, 2); // different owner
//        node.placeSettlement(s);
//
//        BuildCity build = new BuildCity(p, board, randomizer);
//
//        try {
//            build.build(s);
//            fail("Should not allow upgrade of another player's settlement");
//        } catch (IllegalStateException ignored) {
//
//        }
//
//    }
}
