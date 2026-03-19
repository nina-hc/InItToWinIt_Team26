package catan;


/**
 * Rule: spend cards in a way that leaves fewer than 5 cards in hand.
 * This has lower priority than VP gain or building without VP.
 *
 * This version tries city, then settlement, then road, but only assigns 0.5
 * when the action leaves fewer than 5 total cards.
 */
public class ReduceHandSizeEvaluator extends AbstractBuildStrategy implements StrategyEvaluator {
    @Override
    public double evaluate(Player player, Board board, Randomizer randomizer,
                           Bank bank, PlacementValidator placementValidator) {

        if (canBuildCity(player) && leavesFewerThanFiveCards(player, 5)) {
            return 0.5;
        }

        if (canBuildSettlement(player, placementValidator) && leavesFewerThanFiveCards(player, 4)) {
            return 0.5;
        }

        if (canBuildRoad(player, placementValidator) && leavesFewerThanFiveCards(player, 2)) {
            return 0.5;
        }

        return 0.0;
    }

    @Override
    public void executeStrategy(Player player, Board board, Randomizer randomizer,
                                Bank bank, PlacementValidator placementValidator) {

        if (canBuildCity(player) && leavesFewerThanFiveCards(player, 5)) {
            BuildCity buildCity = new BuildCity(player, board, randomizer, bank, placementValidator);
            buildCity.execute();
            return;
        }

        if (canBuildSettlement(player, placementValidator) && leavesFewerThanFiveCards(player, 4)) {
            BuildSettlement buildSettlement = new BuildSettlement(player, board, randomizer, bank, placementValidator);
            buildSettlement.execute();
            return;
        }

        if (canBuildRoad(player, placementValidator) && leavesFewerThanFiveCards(player, 2)) {
            BuildRoad buildRoad = new BuildRoad(player, board, randomizer, bank, placementValidator);
            buildRoad.execute();
        }
    }
}
