package catan;

/**
 * Rule: build a city if possible.
 * Immediate value = 1.0 because it earns a VP.
 * @author Marva Hassan
 */
public class PlaceCityStrategy extends AbstractStrategy implements StrategyEvaluator {
    @Override
    public double evaluate(Player player, Board board, Randomizer randomizer, Bank bank, PlacementValidator placementValidator) {

        if (canBuildCity(player)) {
            return 1.0;
        }
        return 0.0;
    }

    @Override
    public void executeStrategy(Player player, Board board, Randomizer randomizer, Bank bank, PlacementValidator placementValidator) {

        if (canBuildCity(player)) {
            BuildCity buildCity = new BuildCity(player, board, randomizer, bank, placementValidator);
            buildCity.execute();
        }
    }
}
