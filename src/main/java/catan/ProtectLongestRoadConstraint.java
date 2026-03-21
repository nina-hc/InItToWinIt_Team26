package catan;

public class ProtectLongestRoadConstraint implements Constraint{

	@Override
	public boolean isApplicable(Player player,Player[] players, Board board, Bank bank,
	                            PlacementValidator placementValidator) {
		//can't build a road
		if(!BuildCheckHelper.canBuildRoad(player,placementValidator)){
			return false;
		}
		return isLongestRoadThreatened(player,players,board);

	}

	@Override
	public void resolveConstraint(Player player, Player[] players, Board board, Bank bank, Randomizer randomizer, PlacementValidator placementValidator) {
		new BuildRoad(player, board, randomizer, bank, placementValidator).execute();
	}

	private boolean isLongestRoadThreatened(Player player, Player[] players, Board board){
		int currentPlayersRoadLength = new VictoryPointConditions(player,board).getPlayerRoadLength();
		//opponent can't have a shorter than 0
		if(currentPlayersRoadLength == 0){
			return false;
		}

		for(Player opponents: players){
			if(opponents.getPlayerID() == player.getPlayerID()){
				//skip the current player
				continue;
			}
			int opponentPlayerRoadLength = new VictoryPointConditions(opponents,board).getPlayerRoadLength();
			//checking for the other players being one away
			if(opponentPlayerRoadLength >= (currentPlayersRoadLength - 1 )){
				return true;
			}
		}
		//otherwise
		return false;
	}
}
