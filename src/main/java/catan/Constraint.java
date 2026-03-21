package catan;

public interface Constraint {
	/*checks if the constraint applies*/
	boolean isApplicable(Player player, Board board, Bank bank, PlacementValidator placementValidator);

	/*action that is taken to resolve the constraint*/
	void resolveConstraint(Player player, Board board, Bank bank, Randomizer randomizer,
	                       PlacementValidator placementValidator);

}
