package catan;

/**
 * Base class for rule-based build strategies.
 * Provides shared helper methods for checking legal actions.
 * @author Marva Hassan
 */
public abstract class AbstractStrategy implements StrategyEvaluator {

    protected boolean canBuildCity(Player player) {
        return player.getResourceHand().canBuyCity()
                && player.getPlayerCitiesLeft() > 0
                && !player.getPlayerSettlements().isEmpty();
    }

    protected boolean canBuildSettlement(Player player, PlacementValidator placementValidator) {
        return player.getResourceHand().canBuySettlement()
                && player.getPlayerSettlementsLeft() > 0
                && !placementValidator.getValidSettlementPlacements(player, false).isEmpty();
    }

    protected boolean canBuildRoad(Player player, PlacementValidator placementValidator) {
        return player.getResourceHand().canBuyRoad()
                && player.getPlayerRoadsLeft() > 0
                && !placementValidator.getValidRoadEdges(player).isEmpty();
    }

    /**
     * Returns true if spending for this action leaves the player with fewer than 5 cards.
     */
    protected boolean leavesFewerThanFiveCards(Player player, int cardsSpent) {
        return player.getResourceHand().totalPlayerCard() - cardsSpent < 5;
    }

}
