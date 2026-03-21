package catan;

/**
 * Base class for rule-based build strategies.
 * Provides shared helper methods for checking legal actions.
 * @author Marva Hassan
 */
public abstract class AbstractStrategy implements StrategyEvaluator {

    protected boolean canBuildCity(Player player) {
        return BuildCheckHelper.canBuildCity(player);
    }

    protected boolean canBuildSettlement(Player player, PlacementValidator placementValidator) {
        return BuildCheckHelper.canBuildSettlement(player, placementValidator);
    }

    protected boolean canBuildRoad(Player player, PlacementValidator placementValidator) {
        return BuildCheckHelper.canBuildRoad(player, placementValidator);
    }

    /**
     * Returns true if spending for this action leaves the player with fewer than 5 cards.
     */
    protected boolean leavesFewerThanFiveCards(Player player, int cardsSpent) {
        return player.getResourceHand().totalPlayerCard() - cardsSpent < 5;
    }

}
