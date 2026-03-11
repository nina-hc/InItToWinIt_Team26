package catan;

import catan.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test cases for the VictoryPointConditions Class
 *
 * @author Serene Abou Sharaf
 * March 5, 2026
 */
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
    void testLongestRoadVictoryPoints() {
        Board board = new Board();
        Player player = new Player(1);
        VictoryPointConditions vp = new VictoryPointConditions(player, board);

        // Create 5 connected roads for the longest road
        board.placeRoad(0, 1, player.getPlayerID());
        board.placeRoad(1, 2, player.getPlayerID());
        board.placeRoad(2, 3, player.getPlayerID());
        board.placeRoad(3, 4, player.getPlayerID());
        board.placeRoad(4, 5, player.getPlayerID());

        // Add roads to player's inventory
        for (Edge edge : board.getAllEdges()) {
            if (edge.getRoad() != null && edge.getRoad().getOwner() == player.getPlayerID()) {
                player.playerAddRoad(edge.getRoad());
            }
        }

        assertEquals(2, vp.calculateVictoryPoints());
    }




    @Test
    void testCalculateTotalVictoryPoints() {
        Board board = new Board();
        Player player = new Player(1);
        VictoryPointConditions vp = new VictoryPointConditions(player, board);

        // 2 settlements
        Node n1 = board.getNode(0);
        Node n2 = board.getNode(1);
        Settlement s1 = new Settlement(n1, player.getPlayerID());
        Settlement s2 = new Settlement(n2, player.getPlayerID());
        n1.placeSettlement(s1);
        n2.placeSettlement(s2);
        player.playerAddSettlement(s1);
        player.playerAddSettlement(s2);

        // 1 settlement that will be upgraded to a city
        Node cityNode = board.getNode(2);
        Settlement citySettlement = new Settlement(cityNode, player.getPlayerID());
        cityNode.placeSettlement(citySettlement);
        player.playerAddSettlement(citySettlement);

        City city = new City(cityNode, player.getPlayerID());
        cityNode.upgradeToCity(city);
        player.playerUpgradeToCity(cityNode, city);

        // 5-road chain for longest road
        board.placeRoad(0, 1, player.getPlayerID());
        board.placeRoad(1, 6, player.getPlayerID());
        board.placeRoad(6, 7, player.getPlayerID());
        board.placeRoad(7, 8, player.getPlayerID());
        board.placeRoad(8, 9, player.getPlayerID());

        for (Edge edge : board.getAllEdges()) {
            if (edge.getRoad() != null && edge.getRoad().getOwner() == player.getPlayerID()) {
                player.playerAddRoad(edge.getRoad());
            }
        }

        // VP: settlements(2vp) + city(2vp) + longest road(5 road means 2vp) = 6vp
        assertEquals(6, vp.calculateVictoryPoints());
        assertFalse(vp.checkWinConditions());
    }

    @Test
    void testWinConditionWithMixedVP() {
        Board board = new Board();
        Player player = new Player(1);
        VictoryPointConditions vp = new VictoryPointConditions(player, board);

        // 4 settlements
        for (int i = 0; i < 4; i++) {
            Node node = board.getNode(i);
            Settlement s = new Settlement(node, player.getPlayerID());
            node.placeSettlement(s);
            player.playerAddSettlement(s);
        }

        // 3 settlements upgraded to cities
        for (int i = 4; i < 7; i++) {
            Node node = board.getNode(i);
            Settlement s = new Settlement(node, player.getPlayerID());
            node.placeSettlement(s);
            player.playerAddSettlement(s);

            City c = new City(node, player.getPlayerID());
            node.upgradeToCity(c);
            player.playerUpgradeToCity(node, c);
        }

        // 2 road chain means not enough for longest road VP
        board.placeRoad(10, 11, player.getPlayerID());
        board.placeRoad(11, 12, player.getPlayerID());
        for (Edge edge : board.getAllEdges()) {
            if (edge.getRoad() != null && edge.getRoad().getOwner() == player.getPlayerID()) {
                player.playerAddRoad(edge.getRoad());
            }
        }

        //VP: settlements(4) +cities(3*2=6) +roads(0) =10 , win
        assertTrue(vp.checkWinConditions());
    }



    @Test
    void testNotWinning() {
        Board board = new Board();
        Player player = new Player(1);

        // Place settlements for the player (1 VP each)
        Node node1 = board.getNode(0);
        Node node2 = board.getNode(1);
        Settlement s1 = new Settlement(node1, player.getPlayerID());
        Settlement s2 = new Settlement(node2, player.getPlayerID());
        node1.placeSettlement(s1);
        node2.placeSettlement(s2);
        player.playerAddSettlement(s1);
        player.playerAddSettlement(s2);

        //No cities or longest road, so total VP =2

        VictoryPointConditions vp = new VictoryPointConditions(player, board);

        assertEquals(2, vp.calculateVictoryPoints()); // total VP based on settlements
        assertFalse(vp.checkWinConditions()); // < 10, so not winning
    }





}