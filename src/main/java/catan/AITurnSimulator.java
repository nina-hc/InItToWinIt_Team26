package catan;

import java.util.List;

/**
 * Executes one full AI turn for a player using rule-based strategies.
 * The player keeps applying the best available strategy until no
 * beneficial actions remain.
 *
 * @author Marva Hassan
 */
public class AITurnSimulator {

    private final Player player;
	private final Player[] players;
    private final Board board;
    private final Randomizer randomizer;
    private final Bank bank;
    private final PlacementValidator placementValidator;

    /**
     * Constructor initializes required game components.
     */
    public AITurnSimulator(Player player,Player[] players,Board board,
                           Randomizer randomizer, Bank bank,
                           PlacementValidator placementValidator) {

        this.player = player;
		this.players = players;
        this.board = board;
        this.randomizer = randomizer;
        this.bank = bank;
        this.placementValidator = placementValidator;
    }

    /**
     * Executes one full AI turn for the player.
     * Keeps applying the best strategy until none remain.
     */
    public void executeTurn() {


        StrategyChooser chooser = new StrategyChooser(randomizer,players);

        System.out.println("---- TESTING STRATEGY CHOOSER FOR PLAYER "
                + player.getPlayerID() + " ----");

        boolean actionTaken = false;

        if (handleConstraints()) {
            return; //stop normal strategy flow
        }

        while (true) {

            StrategyEvaluator bestStrategy =
                    chooser.chooseBestStrategy(player, board, bank, placementValidator);

            System.out.println("Best strategy value: " + chooser.getBestValue());

            if (bestStrategy == null) {
                if (!actionTaken) {
                    System.out.println("Player " + player.getPlayerID()
                            + " chose to take no action this turn.");
                }

                System.out.println("No more valid strategies for "
                        + player.getPlayerID());
                break;
            }

            bestStrategy.executeStrategy(player, board, randomizer, bank, placementValidator);
            actionTaken = true;
        }
    }


    private boolean handleConstraints() {

        //More than 7 cards then they must spend
        if (player.getResourceHand().totalPlayerCard() > 7) {
            System.out.println("Constraint: Too many cards → must spend");

            if (tryBuildRoad()) return true;
            if (tryBuildSettlement()) return true;
            if (tryBuildCity()) return true;

            return true; // even if nothing built, constraint handled
        }

        //Road segments within distance <= 2
        if (shouldConnectRoadSegments()) {
            System.out.println("Constraint: Connecting nearby road segments");

            if (tryBuildRoad()) return true;
        }

        //Protect longest road
        if (shouldProtectLongestRoad()) {
            System.out.println("Constraint: Protecting longest road");

            if (tryBuildRoad()) return true;
        }

        return false; // no constraint triggered
    }



    private boolean tryBuildRoad() {
        if (player.getResourceHand().canBuyRoad()
                && player.getPlayerRoadsLeft() > 0
                && !placementValidator.getValidRoadEdges(player).isEmpty()) {

            new BuildRoad(player, board, randomizer, bank, placementValidator).execute();
            return true;
        }
        return false;
    }

    private boolean tryBuildSettlement() {
        if (player.getResourceHand().canBuySettlement() && player.getPlayerSettlementsLeft() > 0 && !placementValidator.getValidSettlementPlacements(player, false).isEmpty()) {

            new BuildSettlement(player, board, randomizer, bank, placementValidator).execute();
            return true;
        }
        return false;
    }

    private boolean tryBuildCity() {
        if (player.getResourceHand().canBuyCity()
                && player.getPlayerCitiesLeft() > 0
                && !player.getPlayerSettlements().isEmpty()) {

            new BuildCity(player, board, randomizer, bank, placementValidator).execute();
            return true;
        }
        return false;
    }


    private boolean shouldProtectLongestRoad() {

        int myLongest = player.getPlayerRoads().size();

        for (Player p : board.getPlayers()) {
            if (p != player && p.getPlayerRoads().size() >= myLongest - 1) {
                return true;
            }
        }
        return false;
    }


    private boolean shouldConnectRoadSegments() {

        List<Road> roads = player.getPlayerRoads();

        // if player has at least 2 roads not sharing nodes then try connecting
        for (Road r1 : roads) {
            for (Road r2 : roads) {

                if (r1 == r2) continue;

                Node a1 = r1.getEdge().getNodeA();
                Node b1 = r1.getEdge().getNodeB();

                Node a2 = r2.getEdge().getNodeA();
                Node b2 = r2.getEdge().getNodeB();

                boolean connected =
                        a1 == a2 || a1 == b2 || b1 == a2 || b1 == b2;

                if (!connected) {
                    return true; // found disconnected segments
                }
            }
        }

        return false;
    }
}