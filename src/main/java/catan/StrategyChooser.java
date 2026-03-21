package catan;

import java.util.ArrayList;
import java.util.List;

/**
 * Chooses the best available rule based on immediate value.
 * Breaks ties randomly.
 * @author Marva Hassan
 * Constraint Implementation done by,
 * @author Nina Hay Cooper
 */
public class StrategyChooser {

	private final List<Constraint> constraints;
	private final Player[] players;
    private final List<StrategyEvaluator> strategies;
    private final Randomizer randomizer;
    private double bestValue;

    public StrategyChooser(Randomizer randomizer, Player[] players) {
        this.randomizer = randomizer;
		this.players = players;
        this.bestValue = -1.0;//initialized to a invalid value that's a safe starting point

	    //constraints
	    constraints = new ArrayList<>();
		constraints.add(new CardConstraint());
		constraints.add(new ProtectLongestRoadConstraint());

	    //benefit value strategies
	    this.strategies = new ArrayList<>();
        strategies.add(new PlaceCityStrategy());
        strategies.add(new PlaceSettlementStrategy());
        strategies.add(new PlaceRoadStrategy());
        strategies.add(new ReduceHandSizeStrategy());
    }

    public StrategyEvaluator chooseBestStrategy(Player player, Board board, Bank bank, PlacementValidator placementValidator) {
		/*need to check the constraints first*/
	    List<Constraint> applicableConstraints = getApplicableConstraints(player,board,bank,placementValidator);

		/*Based on the wording, I believe you exshust the constraints if they apply before going to the strategies.
		It's not just doing one constraint
		 */
	    while(!applicableConstraints.isEmpty()) {
			int index = randomizer.randomSelection(0, applicableConstraints.size()-1);
			Constraint choosenConstraint = applicableConstraints.get(index);
			choosenConstraint.resolveConstraint(player,players,board,bank,randomizer,placementValidator);

			//continue
		    applicableConstraints = getApplicableConstraints(player,board,bank,placementValidator);

	    }

		//then assess strategies
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

	private List<Constraint> getApplicableConstraints(Player player, Board board, Bank bank, PlacementValidator placementValidator) {
		List<Constraint> applicableConstraints = new ArrayList<>();
		for(Constraint constraint : constraints) {
			if(constraint.isApplicable(player,players,board,bank,placementValidator)){
				applicableConstraints.add(constraint);
			}
		}
		return applicableConstraints;
	}
}