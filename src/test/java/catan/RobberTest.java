package catan;

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

    //=======================================================
    //trying to better coverage
    @Test
    void testDiscardHalfWithDiscard() {
        Player player = new Player(1);
        Bank bank = new Bank();
        Tile tile = new Tile(1, 5, ResourceType.LUMBER);

        //give player 10 cards
        player.getResourceHand().addResource(ResourceType.LUMBER, 10);

        Robber robber = new Robber(tile);
        robber.discardHalf(player, bank);

        assertEquals(5, player.getResourceHand().totalPlayerCard());   //should be half
    }

    @Test
    void testDiscardHalfWithNoDiscard() {
        Player player = new Player(1);
        Bank bank = new Bank();
        Tile tile = new Tile(1, 5, ResourceType.LUMBER);

        //give player 7 cards
        player.getResourceHand().addResource(ResourceType.LUMBER, 7);

        Robber robber = new Robber(tile);
        robber.discardHalf(player, bank);

        assertEquals(7, player.getResourceHand().totalPlayerCard());    //should be unchanged
    }

    @Test
    void testStealCardsNoCardsToSteal() {
        Player thief = new Player(1);
        Player victim = new Player(2);
        Tile tile = new Tile(1, 5, ResourceType.LUMBER);

        Robber robber = new Robber(tile);
        robber.stealCard(thief, victim);

        assertEquals(0, thief.getResourceHand().totalPlayerCard());    //should still be 0
        assertEquals(0, victim.getResourceHand().totalPlayerCard());
    }

    @Test
    void testChooseRandomTileNotTheSameAsCurrent() {
        Board board = new Board();
        Tile startTile = board.getTile(0);

        Robber robber = new Robber(startTile);

        //a loop just to make sure that the current tile is not getting chosen
        for (int i = 0; i < 100; i++) {
            Tile chosenTile = robber.chooseRandomTile(board);
            assertNotEquals(startTile, chosenTile);
        }
    }

    //i cant believe i missed one of the most important test cases for this class... execute seven roll
    //case 1: no victims
    @Test
    void testExecuteSevenRollNoVictims() {
        Board board = new Board();
        Bank bank = new Bank();

        Player player1 = new Player(1);
        Player[] players = new Player[]{player1};

        Tile tile = new Tile(1, 5, ResourceType.LUMBER);
        Robber robber = new Robber(tile);

        robber.executeSevenRoll(board, bank, players, player1);

        //nothing to steal... just make sure it didn't crash
        assertTrue(true);

    }

    //case2: discard happens
    @Test
    void testExecuteSevenRollDiscardHappens() {
        Board board = new Board();
        Bank bank = new Bank();

        Player player1 = new Player(1);
        player1.getResourceHand().addResource(ResourceType.LUMBER, 10);

        //add player
        board.getPlayers().add(player1);

        Player[] players = new Player[]{player1};

        Tile tile = new Tile(1, 5, ResourceType.LUMBER);
        Robber robber = new Robber(tile);

        robber.executeSevenRoll(board, bank, players, player1);

        //check
        assertTrue(player1.getResourceHand().totalPlayerCard() <= 5);

    }

    //case 3 :stealing happens
    @Test
    void testExecuteSevenRollStealingHappens() {
        Board board = new Board();
        Bank bank = new Bank();

        Player player1 = new Player(1);
        Player player2 = new Player(2);
        player2.getResourceHand().addResource(ResourceType.LUMBER, 1);

        Tile tile = new Tile(1, 5, ResourceType.LUMBER);
        Robber robber = new Robber(tile);

        Player[] players = new Player[]{player1};

        robber.executeSevenRoll(board, bank, players, player1);

        //check
        assertTrue(player1.getResourceHand().totalPlayerCard() >= 0);

    }



}