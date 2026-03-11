package catan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test cases for the PlayerAction Class
 *
 * @author Serene Abou Sharaf
 * March 5, 2026
 */
class PlayerActionTest {

    private Board board;
    private Player player;
    private Bank bank;
    private PlacementValidator validator;
    private PlayerAction action;

    @BeforeEach
    void setUp() {
        board = new Board();
        player = new Player(1);
        bank = new Bank(); // bank starts with 19 of each resource
        validator = new PlacementValidator(board);

        // deterministic "randomizer" that always chooses first build
        action = new PlayerAction(player, board, new Randomizer() {
            @Override
            public int randomSelection(int min, int max) {
                return 0; // always pick first available build
            }
        }, bank, validator);
    }

    @Test
    void testExecuteTurnBuildSettlement() {

        //give player a road so settlement is legal
        Road road = board.placeRoad(0, 1, player.getPlayerID());
        player.playerAddRoad(road);

        //give settlement resources
        ResourceHand hand = player.getResourceHand();

        bank.transferToPlayer(ResourceType.LUMBER, 1);
        bank.transferToPlayer(ResourceType.BRICK, 1);
        bank.transferToPlayer(ResourceType.WOOL, 1);
        bank.transferToPlayer(ResourceType.GRAIN, 1);

        hand.addResource(ResourceType.LUMBER, 1);
        hand.addResource(ResourceType.BRICK, 1);
        hand.addResource(ResourceType.WOOL, 1);
        hand.addResource(ResourceType.GRAIN, 1);

        int initialSettlements = player.getPlayerSettlements().size();

        action.executeTurn();

        assertTrue(player.getPlayerSettlements().size() > initialSettlements, "Player should have built a settlement");
    }


    @Test
    void testExecuteTurnBuildCity() {

        Node node = board.getNode(0);

        //create settlement
        Settlement settlement = new Settlement(node, player.getPlayerID());

        node.placeSettlement(settlement);
        player.playerAddSettlement(settlement);

        // give city resources
        ResourceHand hand = player.getResourceHand();

        bank.transferToPlayer(ResourceType.ORE, 3);
        bank.transferToPlayer(ResourceType.GRAIN, 2);

        hand.addResource(ResourceType.ORE, 3);
        hand.addResource(ResourceType.GRAIN, 2);

        int initialCities = player.getPlayerCities().size();

        action.executeTurn();

        assertTrue(player.getPlayerCities().size() > initialCities, "Player should have upgraded settlement to city");
    }



    @Test
    void testExecuteTurnBuildRoadAfterSettlement() {

        //Place a settlement so road has a connection
        Node node = board.getNode(0);
        Settlement settlement = new Settlement(node, player.getPlayerID());
        node.placeSettlement(settlement);
        player.playerAddSettlement(settlement);

        ResourceHand hand = player.getResourceHand();
        bank.transferToPlayer(ResourceType.LUMBER, 1);
        bank.transferToPlayer(ResourceType.BRICK, 1);
        hand.addResource(ResourceType.LUMBER, 1);
        hand.addResource(ResourceType.BRICK, 1);

        int initialRoads = player.getPlayerRoads().size();
        action.executeTurn();

        assertTrue(player.getPlayerRoads().size() > initialRoads, "Player should have built a road connected to the settlement");
    }

    @Test
    void testExecuteTurnUpgradeCity() {

        // Place a settlement first
        Node node = board.getNode(0);
        Settlement settlement = new Settlement(node, player.getPlayerID());
        node.placeSettlement(settlement);
        player.playerAddSettlement(settlement);

        ResourceHand hand = player.getResourceHand();
        bank.transferToPlayer(ResourceType.ORE, 3);
        bank.transferToPlayer(ResourceType.GRAIN, 2);
        hand.addResource(ResourceType.ORE, 3);
        hand.addResource(ResourceType.GRAIN, 2);

        int initialCities = player.getPlayerCities().size();
        int initialSettlements = player.getPlayerSettlements().size();

        action.executeTurn();

        assertTrue(player.getPlayerCities().size() > initialCities, "Player should have upgraded settlement to city");
        assertEquals(0, player.getPlayerSettlements().size(), "Settlement should be removed after upgrade to city");
    }

    @Test
    void testExecuteTurnBuildNothingWhenNoResources() {
        // Player has no resources

        int initialSettlements = player.getPlayerSettlements().size();
        int initialRoads = player.getPlayerRoads().size();
        int initialCities = player.getPlayerCities().size();

        action.executeTurn();

        assertEquals(initialSettlements, player.getPlayerSettlements().size());
        assertEquals(initialRoads, player.getPlayerRoads().size());
        assertEquals(initialCities, player.getPlayerCities().size());
    }




    @Test
    void testBuildRoadDirectly() {



        // Place a settlement to connect the road
        Node node = board.getNode(0);
        Settlement settlement = new Settlement(node, player.getPlayerID());
        node.placeSettlement(settlement);
        player.playerAddSettlement(settlement);

        // Get valid edges
        var edges = validator.getValidRoadEdges(player);
        assertFalse(edges.isEmpty(), "Player should have at least one valid road edge");

        Edge edge = edges.get(0);
        Road road = new Road(player.getPlayerID(), edge);
        edge.placeRoad(road);
        player.playerAddRoad(road);

        assertTrue(player.getPlayerRoads().contains(road));
    }

    @Test
    void testUpgradeCityDirectly() {
        // Place a settlement first
        Node node = board.getNode(0);
        Settlement settlement = new Settlement(node, player.getPlayerID());
        node.placeSettlement(settlement);
        player.playerAddSettlement(settlement);

        // Upgrade settlement to city
        City city = new City(node, player.getPlayerID());
        player.playerUpgradeToCity(node, city);

        assertTrue(player.getPlayerCities().contains(city));
        assertFalse(player.getPlayerSettlements().contains(settlement));
    }

    @Test
    void testExecuteTurnStopsWhenCannotBuildAnything() {
        // Player has no resources
        int initialVP = player.getVictoryPoints();
        action.executeTurn();



        // Player should not gain any builds or victory points
        assertEquals(0, player.getPlayerSettlements().size());
        assertEquals(0, player.getPlayerRoads().size());
        assertEquals(0, player.getPlayerCities().size());
        assertEquals(initialVP, player.getVictoryPoints());
    }

}