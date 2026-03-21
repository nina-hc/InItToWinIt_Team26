package catan;
//not exactly an adapter but will help with the interaction with AITurnSimulator
public class ActionConstraint implements StrategyEvaluator{

	private final Constraint constraint;
	public ActionConstraint(Constraint constraint) {
		this.constraint = constraint;
	}

	@Override
	public double evaluate(Player player, Board board, Randomizer randomizer, Bank bank, PlacementValidator placementValidator) {
		//constraints do not have values attached to them, they shouldn't be included using the strategy ranking
		return -1.0;
	}

	@Override
	public void executeStrategy(Player player, Board board, Randomizer randomizer, Bank bank, PlacementValidator placementValidator) {
		constraint.resolveConstraint(player, , board, bank, randomizer, placementValidator);
	}
}
