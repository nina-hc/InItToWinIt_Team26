package catan;

public class CardConstraint implements Constraint{
	private static final int CARD_LIMIT = 7;

	@Override
	public boolean isApplicable(Player player, Board board, Bank bank, PlacementValidator placementValidator) {
		boolean exceedsCardLimit = player.getResourceHand().totalPlayerCard() > CARD_LIMIT;

		boolean canSpendCards =
				BuildCheckHelper.canBuildCity(player) || BuildCheckHelper.canBuildSettlement(player,placementValidator)||BuildCheckHelper.canBuildRoad(player,
						placementValidator);
		//if they have more than 7 cards and can spend them
		return exceedsCardLimit&&canSpendCards;
	}

	@Override
	public void resolveConstraint(Player player, Board board, Bank bank, Randomizer randomizer, PlacementValidator placementValidator) {
		if(BuildCheckHelper.canBuildCity(player)){
			new BuildCity(player, board, randomizer, bank, placementValidator).execute();
			return;
		}
		if(BuildCheckHelper.canBuildSettlement(player,placementValidator)){
			new BuildSettlement(player, board, randomizer, bank, placementValidator).execute();
			return;
		}
		if(BuildCheckHelper.canBuildRoad(player,placementValidator)){
			new BuildRoad(player, board, randomizer, bank, placementValidator).execute();
		}
	}


}
