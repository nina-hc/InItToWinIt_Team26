package test.java;

import main.java.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class BuildCityTest {


    @Test
    void testValidCityUpgrade() {
        Game game = new Game(10);
        Board board = new Board();
        Randomizer randomizer = new Randomizer();
        Player p = new Player(1);

        ResourceHand hand = p.getResourceHand();
        hand.addResource(ResourceType.ORE, 3);
        hand.addResource(ResourceType.GRAIN, 2);

        Node node = board.getNode(4);
        Settlement s = new Settlement(node, p.getPlayerID());

        p.playerAddSettlement(s);
        node.placeSettlement(s);

        BuildCity build = new BuildCity(p, board, randomizer);

        build.build(s); // if you add public wrapper

        // test passes if no exception
    }


    @Test
    void testCityFailsWithoutResources() {
        Game game = new Game(10);
        Board board = new Board();
        Randomizer randomizer = new Randomizer();
        Player p = new Player(1);

        Settlement s = new Settlement(board.getNode(4), p.getPlayerID());
        p.playerAddSettlement(s);

        BuildCity build = new BuildCity(p, board, randomizer);

        try {
            build.build(s);
            fail("Expected IllegalStateException");
        } catch (IllegalStateException ignored) {
            // test passes
        }
    }

    @Test
    void testCityFailsIfNotOwner() {
        Game game = new Game(10);
        Board board = new Board();
        Randomizer randomizer = new Randomizer();
        Player p = new Player(1);

        Node node = board.getNode(4);
        Settlement s = new Settlement(node, 2); // different owner
        node.placeSettlement(s);

        BuildCity build = new BuildCity(p, board, randomizer);

        try {
            build.build(s);
            fail("Should not allow upgrade of another player's settlement");
        } catch (IllegalStateException ignored) {

        }

    }


}
