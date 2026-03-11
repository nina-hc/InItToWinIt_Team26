package catan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import catan.*;



import static org.junit.jupiter.api.Assertions.*;



public class BuildSettlementTest {

    private Board board;
    private Randomizer randomizer;
    private Bank bank;
    private PlacementValidator validator;

    @BeforeEach
    void setup() {
        board = new Board();
        randomizer = new Randomizer();
        bank = new Bank();
        validator = new PlacementValidator(board);
    }

    @Test
    void testValidSettlementPlacement() {

        Player p = new Player(1);

        // Give player settlement resources
        p.getResourceHand().addResource(ResourceType.LUMBER, 1);
        p.getResourceHand().addResource(ResourceType.BRICK, 1);
        p.getResourceHand().addResource(ResourceType.WOOL, 1);
        p.getResourceHand().addResource(ResourceType.GRAIN, 1);

        BuildSettlement build = new BuildSettlement(p, board, randomizer, bank, validator);

        // Place a road first so settlement placement is valid
        board.placeRoad(0, 1, p.getPlayerID());

        // Now choose a node connected to that road
        Node node = board.getNode(0);

        // Validate placement
        assertTrue(build.validatePlacement(node));

        // Build settlement
        build.build(node);

        // Verify settlement placed
        assertTrue(node.isOccupied());
        assertEquals(1, p.getPlayerSettlements().size());
    }

    @Test
    void testCannotBuildWithoutResources() {

        Player p = new Player(1); // no resources
        BuildSettlement build = new BuildSettlement(p, board, randomizer, bank, validator);

        // Give the player a road so placement would normally be valid
        board.placeRoad(0, 1, p.getPlayerID());

        Node node = board.getNode(0);

        build.build(node);

        assertFalse(node.isOccupied());
        assertEquals(0, p.getPlayerSettlements().size());
    }


    @Test
    void testCannotBuildOnOccupiedNode() {
        Node node = board.getNode(5);
        // Occupy the node with another player's settlement
        node.placeSettlement(new Settlement(node, 2));

        Player p = new Player(1);
        p.getResourceHand().addResource(ResourceType.LUMBER, 1);
        p.getResourceHand().addResource(ResourceType.BRICK, 1);
        p.getResourceHand().addResource(ResourceType.WOOL, 1);
        p.getResourceHand().addResource(ResourceType.GRAIN, 1);

        BuildSettlement build = new BuildSettlement(p, board, randomizer, bank, validator);

        // Validation should fail
        assertFalse(build.validatePlacement(node));

        // Build attempt should not override existing settlement
        build.build(node);
        assertEquals(2, ((Settlement) node.getBuilding()).getOwnerID());
        assertEquals(0, p.getPlayerSettlements().size());
    }

    @Test
    void testDistanceRuleViolation() {
        // Neighboring node occupied
        Node neighbor = board.getNode(0);
        neighbor.placeSettlement(new Settlement(neighbor, 2));

        Node node = board.getNode(1);
        Player p = new Player(1);
        p.getResourceHand().addResource(ResourceType.LUMBER, 1);
        p.getResourceHand().addResource(ResourceType.BRICK, 1);
        p.getResourceHand().addResource(ResourceType.WOOL, 1);
        p.getResourceHand().addResource(ResourceType.GRAIN, 1);

        BuildSettlement build = new BuildSettlement(p, board, randomizer, bank, validator);

        // Placement should fail due to distance rule
        assertFalse(build.validatePlacement(node));
    }

    @Test
    void testCannotBuildWithoutRoadAfterSetup() {
        Player p = new Player(1);
        p.getResourceHand().addResource(ResourceType.LUMBER, 1);
        p.getResourceHand().addResource(ResourceType.BRICK, 1);
        p.getResourceHand().addResource(ResourceType.WOOL, 1);
        p.getResourceHand().addResource(ResourceType.GRAIN, 1);

        BuildSettlement build = new BuildSettlement(p, board, randomizer, bank, validator);

        Node node = board.getNode(8); // Assume not connected to any player road

        // Placement validation should fail because no connecting road
        assertFalse(build.validatePlacement(node));
    }

}