package catan;

public class BuildCheckHelper {
	//do not allow instantiation
	private BuildCheckHelper() {
		throw new UnsupportedOperationException("This is a Helper Class and cannot be instantiated");
	}

	public static boolean canBuildCity(Player player) {
		return player.getResourceHand().canBuyCity()
				&& player.getPlayerCitiesLeft() > 0
				&& !player.getPlayerSettlements().isEmpty();
	}

	public static boolean canBuildSettlement(Player player, PlacementValidator placementValidator) {
		return player.getResourceHand().canBuySettlement()
				&& player.getPlayerSettlementsLeft() > 0
				&& !placementValidator.getValidSettlementPlacements(player, false).isEmpty();
	}

	public static boolean canBuildRoad(Player player, PlacementValidator placementValidator) {
		return player.getResourceHand().canBuyRoad()
				&& player.getPlayerRoadsLeft() > 0
				&& !placementValidator.getValidRoadEdges(player).isEmpty();
	}

}
