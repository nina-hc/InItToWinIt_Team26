package catan;

/**
 * Rule: build a settlement if possible.
 * Immediate value = 1.0 because it earns a VP.
 */
public class BuildSettlementEvaluator extends AbstractBuildStrategy implements StrategyEvaluator {
    @Override
    public double evaluate(Player player, Board board, Randomizer randomizer,
                           Bank bank, PlacementValidator placementValidator) {

        if (canBuildSettlement(player, placementValidator)) {
            return 1.0;
        }
        return 0.0;
    }

    @Override
    public void executeStrategy(Player player, Board board, Randomizer randomizer,
                                Bank bank, PlacementValidator placementValidator) {

        if (canBuildSettlement(player, placementValidator)) {
            BuildSettlement buildSettlement = new BuildSettlement(player, board, randomizer, bank, placementValidator);
            buildSettlement.execute();
        }
    }
}
