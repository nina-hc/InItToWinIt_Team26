package a2_tests;

import catan.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobberTest {

    //=======================================================
    @Test
    void testMoveRobber() {
        Tile tile1 = new Tile(1, 11, ResourceType.GRAIN);
        Tile tile2 = new Tile(2, 8, ResourceType.BRICK);
        //idk if i have to set the nodes for these tiles

        Robber robber = new Robber(tile1);
        robber.moveRobber(tile2);

        assertFalse(tile1.getHasRobber());
        assertTrue(tile2.getHasRobber());
    }

    @Test
    void testRobberStartsOnDesert() {
        Tile tile = new Tile(16, 7, ResourceType.DESERT);
        Robber robber= new Robber(tile);

        assertTrue(tile.getHasRobber());
    }

    //=======================================================
    @Test
    void testStealCard() {
        Player player1 = new Player(1);
        Player player2 = new Player(2);

        player2.getResourceHand().addResource(ResourceType.ORE, 1);
        Tile tile = new Tile(1, 11, ResourceType.GRAIN);

        Robber robber = new Robber(tile);
        robber.stealCard(player1, player2);

        assertEquals(1, player1.getResourceHand().totalPlayerCard()); //should have 1 stolen ORE
        assertEquals(0, player2.getResourceHand().totalPlayerCard()); //should have none
    }

    //=======================================================
    @Test
    void testChooseRandomTile() {
        Tile tile1 = new Tile(1, 11, ResourceType.GRAIN);
        Tile tile2 = new Tile(2, 8, ResourceType.BRICK);
        Tile tile3 = new Tile(3, 3, ResourceType.ORE);

        Board board = new Board();
//        board.getTile(tile1);
//        board.getTile(tile2);
//        board.getTile(tile3);

        Robber robber = new Robber(tile1);
        Tile chosenTile = robber.chooseRandomTile(board);

        assertNotNull(chosenTile);
        assertNotEquals(tile1, chosenTile);
    }

    //=======================================================
    @Test
    void testExecuteSevenRoll() {
        //tiles
        Tile tile1 = new Tile(1, 11, ResourceType.GRAIN);
        Tile tile2 = new Tile(2, 8, ResourceType.BRICK);

        //board
        Board board = new Board();

        //players
        Player player1 = new Player(1);
        Player player2 = new Player(2);

        Player[] players = new Player[]{player1, player2};

        //set up buildings
        Node node = new Node(3); //placing on a node on tile 2
        Settlement settlement = new Settlement(node, 2);
        node.placeSettlement(settlement);

        //bank
        Bank bank = new Bank();
        Robber robber = new Robber(tile1);

        //execute
        robber.executeSevenRoll(board, bank, players, player1);

        //check
        assertFalse(tile1.getHasRobber());
    }
}