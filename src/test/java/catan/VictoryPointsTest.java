package catan;

import catan.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VictoryPointsTest {


    @Test
    void testSettlementVictoryPoints() {
        Board board = new Board();
        Player player = new Player(1);

        Node node = board.getNode(5);
        Settlement settlement = new Settlement(node, player.getPlayerID());
        node.placeSettlement(settlement);
        player.playerAddSettlement(settlement);

        VictoryPointConditions vp = new VictoryPointConditions(player, board);
        assertEquals(1, vp.calculateSettlementVP());
    }

    @Test
    void testCityVictoryPoints() {
        Board board = new Board();
        Player player = new Player(1);

        Node node = board.getNode(6);
        Settlement settlement = new Settlement(node, player.getPlayerID());
        node.placeSettlement(settlement);
        player.playerAddSettlement(settlement);

        City city = new City(node, player.getPlayerID());
        node.upgradeToCity(city);
        player.playerUpgradeToCity(node, city);

        VictoryPointConditions vp = new VictoryPointConditions(player, board);
        assertEquals(2, vp.calculateCityVP());
    }

    @Test
    void testWinCondition() {
        Board board = new Board();
        Player player = new Player(1);

        player.addVictoryPoints(10);
        VictoryPointConditions vp = new VictoryPointConditions(player, board);

        assertTrue(vp.checkWinConditions());
    }

    @Test
    void testNotWinning() {
        Board board = new Board();
        Player player = new Player(1);

        player.addVictoryPoints(8);
        VictoryPointConditions vp = new VictoryPointConditions(player, board);

        assertFalse(vp.checkWinConditions());
    }
//    @Test
//    void testSettlementVictoryPoint() {
//        Game game = new Game(10);
//        Board board = new Board();
//        Randomizer randomizer = new Randomizer();
//        Player p = new Player(1);
//
//        ResourceHand hand = p.getResourceHand();
//        hand.addResource(ResourceType.LUMBER, 1);
//        hand.addResource(ResourceType.BRICK, 1);
//        hand.addResource(ResourceType.WOOL, 1);
//        hand.addResource(ResourceType.GRAIN, 1);
//
//        BuildSettlement build = new BuildSettlement(p, board, randomizer, game);
//        build.build(board.getNode(3));
//
//        if (vp.calculateSettlementVP() != 1) {
//            fail("Expected 1 victory point");
//        }
//    }
//
//
//    @Test
//    void testCityVictoryPointUpgrade() {
//        Game game = new Game(10);
//        Board board = new Board();
//        Randomizer randomizer = new Randomizer();
//        Player p = new Player(1);
//
//        ResourceHand hand = p.getResourceHand();
//        hand.addResource(ResourceType.LUMBER, 1);
//        hand.addResource(ResourceType.BRICK, 1);
//        hand.addResource(ResourceType.WOOL, 1);
//        hand.addResource(ResourceType.GRAIN, 1);
//
//        Node node = board.getNode(3);
//
//        BuildSettlement bs = new BuildSettlement(p, board, randomizer, game);
//        bs.build(node);
//
//        hand.addResource(ResourceType.ORE, 3);
//        hand.addResource(ResourceType.GRAIN, 2);
//
//        BuildCity bc = new BuildCity(p, board, randomizer);
//        bc.build(p.getPlayerSettlements().get(0));
//
//        if (p.getVictoryPoints() != 2) {
//            fail("Expected 2 victory points after upgrade");
//        }
//    }
//
//
//    @Test
//    void testWinAtTenVictoryPoints() {
//        Player p = new Player(1);
//        p.addVictoryPoints(10);
//
//        if (p.getVictoryPoints() >= 10) {
//            // win condition
//        } else {
//
//            fail("Player should win at 10 points");
//        }
//   }
}