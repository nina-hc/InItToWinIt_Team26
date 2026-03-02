package test.java;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.*;

import static org.junit.jupiter.api.Assertions.*;



public class BuildSettlementTest {

    private Board board;
    private Randomizer randomizer;
    private Player player;
    Game game = new Game(10);

    /**
     *  This is to avoid test interference and to reset states
     */
    @BeforeEach
    void setup() {
        board = new Board();
        randomizer = new Randomizer();
    }


    @Test
    void testValidSettlementPlacement() {
        Player p = new Player(1);
        ResourceHand hand = p.getResourceHand();
        hand.addResource(ResourceType.LUMBER, 1);
        hand.addResource(ResourceType.BRICK, 1);
        hand.addResource(ResourceType.WOOL, 1);
        hand.addResource(ResourceType.GRAIN, 1);

        BuildSettlement build = new BuildSettlement(p, board, randomizer, game);
        Node node = board.getNode(5);

        build.build(node);

    }


    @Test
    void testCannotBuildWithoutResources() {
        Game game = new Game(10);
        Board board = new Board();
        Randomizer randomizer = new Randomizer();
        Player p = new Player(1);

        BuildSettlement build = new BuildSettlement(p, board, randomizer, game);
        Node node = board.getNode(5);

        try {
            build.build(node);
            fail("Expected IllegalStateException");
        } catch (IllegalStateException e) {
            // test passes
        }
    }

    @Test
    void testCannotBuildOnOccupiedNode() {
        Node node = board.getNode(5);
        node.placeSettlement(new Settlement(node, 2));

        BuildSettlement build = new BuildSettlement(player, board, randomizer,game);

        boolean result = build.validatePlacement(node);

        assertFalse(result);
    }


    @Test
    void testDistanceRuleViolation() {
        Node node = board.getNode(5);
        Node neighbor = board.getNode(6);

        neighbor.placeSettlement(new Settlement(neighbor, 2));

        BuildSettlement build = new BuildSettlement(player, board, randomizer, game);

        boolean result = build.validatePlacement(node);

        assertFalse(result);
    }


    @Test
    void testCannotBuildWithoutRoadAfterSetup() {
        Player p = new Player(1);
        ResourceHand hand = p.getResourceHand();
        hand.addResource(ResourceType.LUMBER, 1);
        hand.addResource(ResourceType.BRICK, 1);
        hand.addResource(ResourceType.WOOL, 1);
        hand.addResource(ResourceType.GRAIN, 1);

        BuildSettlement build = new BuildSettlement(p, board, randomizer, game);
        Node node = board.getNode(8);

        boolean result = build.validatePlacement(node);

        assertFalse(result);
    }


}
