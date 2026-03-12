package catan;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test cases for the BuildCity Class
 *
 * @author Serene Abou Sharaf
 * March 5, 2026
 */
public class BuildCityTest {

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
    void testValidCityUpgrade() {
        Player p = new Player(1);

        // Give resources for initial settlement
        p.getResourceHand().addResource(ResourceType.LUMBER, bank.transferToPlayer(ResourceType.LUMBER, 1));
        p.getResourceHand().addResource(ResourceType.BRICK, bank.transferToPlayer(ResourceType.BRICK, 1));
        p.getResourceHand().addResource(ResourceType.WOOL, bank.transferToPlayer(ResourceType.WOOL, 1));
        p.getResourceHand().addResource(ResourceType.GRAIN, bank.transferToPlayer(ResourceType.GRAIN, 1));

        BuildSettlement buildSettlement = new BuildSettlement(p, board, randomizer, bank, validator);
        board.placeRoad(0, 1, p.getPlayerID());
        Node node = board.getNode(0);
        buildSettlement.build(node);

        // Give resources for city (2 grain + 3 ore)
        p.getResourceHand().addResource(ResourceType.GRAIN, bank.transferToPlayer(ResourceType.GRAIN, 2));
        p.getResourceHand().addResource(ResourceType.ORE, bank.transferToPlayer(ResourceType.ORE, 3));

        BuildCity buildCity = new BuildCity(p, board, randomizer, bank, validator);

        // Validate and upgrade
        assertTrue(buildCity.validatePlacement(p.getPlayerSettlements().get(0)));

        try {
            buildCity.build(p.getPlayerSettlements().get(0));
        } catch (IllegalStateException e) {
            fail("Build should succeed but threw exception: " + e.getMessage());
        }

        // Verify city is placed
        assertTrue(node.isOccupied());
        assertTrue(node.getBuilding() instanceof City);
        assertEquals(p.getPlayerID(), node.getBuilding().getOwnerID());

        assertEquals(0, p.getPlayerSettlements().size());
        assertEquals(1, p.getPlayerCities().size());
    }


    @Test
    void testCannotBuildCityWithoutSettlement() {
        Player p = new Player(1);

        // Give enough resources to avoid resource failure
        p.getResourceHand().addResource(ResourceType.GRAIN, bank.transferToPlayer(ResourceType.GRAIN, 2));
        p.getResourceHand().addResource(ResourceType.ORE, bank.transferToPlayer(ResourceType.ORE, 3));

        BuildCity buildCity = new BuildCity(p, board, randomizer, bank, validator);

        try {
            buildCity.build(null); // No settlements to upgrade
            fail("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Verify the exception message
            assertEquals("Invalid placement", e.getMessage());
        } catch (Exception e) {
            // Any other exception fails the test
            fail("Unexpected exception type: " + e);
        }
    }


    @Test
    void testCannotBuildCityWithoutResources() {
        Player p = new Player(1);

        // Place settlement
        Node node = board.getNode(0);
        node.placeSettlement(new Settlement(node, p.getPlayerID()));
        p.getPlayerSettlements().add((Settlement) node.getBuilding());

        BuildCity buildCity = new BuildCity(p, board, randomizer, bank, validator);

        try {
            buildCity.build(node);
            fail("Expected IllegalStateException because player has no resources");
        } catch (IllegalStateException e) {
            // Expected exception
            assertEquals("Not enough resources", e.getMessage());
        }
    }

    @Test
    void testCannotBuildCityOnOtherPlayersSettlement() {
        Player p = new Player(1);

        // Give enough resources to avoid resource failure
        p.getResourceHand().addResource(ResourceType.GRAIN, bank.transferToPlayer(ResourceType.GRAIN, 2));
        p.getResourceHand().addResource(ResourceType.ORE, bank.transferToPlayer(ResourceType.ORE, 3));

        // Create a settlement owned by another player
        Node node = board.getNode(0);
        Settlement otherSettlement = new Settlement(node, 2); // other player
        node.placeSettlement(otherSettlement);

        BuildCity buildCity = new BuildCity(p, board, randomizer, bank, validator);

        try {
            buildCity.build(otherSettlement); // attempt to build on another player's settlement
            fail("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // check that the exception message is correct
            assertEquals("Invalid placement", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception type: " + e);
        }
    }

}
