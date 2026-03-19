package catan;

import java.util.ArrayList;
import java.util.List;

/**
 * Chooses the best available rule based on immediate value.
 * Breaks ties randomly.
 * @author Marva Hassan
 */
public class StrategyChooser {

    private final List<StrategyEvaluator> strategies;
    private final Randomizer randomizer;
    private double bestValue;

    public StrategyChooser(Randomizer randomizer) {
        this.randomizer = randomizer;
        this.strategies = new ArrayList<>();
        this.bestValue = bestValue;

        strategies.add(new PlaceCityStrategy());
        strategies.add(new PlaceSettlementStrategy());
        strategies.add(new PlaceRoadStrategy());
        strategies.add(new ReduceHandSizeStrategy());
    }

    public StrategyEvaluator chooseBestStrategy(Player player, Board board, Bank bank, PlacementValidator placementValidator) {

        bestValue = -1.0;
        List<StrategyEvaluator> bestStrategies = new ArrayList<>();

        for (StrategyEvaluator strategy : strategies) {

            double value = strategy.evaluate(player, board, randomizer, bank, placementValidator);

            if (value > bestValue) {
                bestValue = value;
                bestStrategies.clear();
                bestStrategies.add(strategy);
            }
            else if (value == bestValue) {
                bestStrategies.add(strategy);
            }
        }

        if (bestStrategies.isEmpty() || bestValue <= 0.0) {
            return null;
        }

        int chosenIndex = randomizer.randomSelection(0, bestStrategies.size() - 1);
        return bestStrategies.get(chosenIndex);
    }

    public boolean executeBestStrategy(Player player, Board board, Bank bank,
                                       PlacementValidator placementValidator) {

        StrategyEvaluator chosen = chooseBestStrategy(player, board, bank, placementValidator);

        if (chosen == null) {
            return false;
        }

        chosen.executeStrategy(player, board, randomizer, bank, placementValidator);
        return true;
    }

    public double getBestValue() {
        return bestValue;
    }
}