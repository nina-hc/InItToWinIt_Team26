package catan;

public interface StrategyEvaluator {

    double evaluate(Player player, Board board, Randomizer randomizer, Bank bank, PlacementValidator placementValidator);
    void executeStrategy(Player player, Board board, Randomizer randomizer, Bank bank, PlacementValidator placementValidator);

}
