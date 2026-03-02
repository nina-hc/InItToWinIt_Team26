package catan;

public class BuildPlacement {
	private  Board board;
	private Bank bank;
	private PlacementValidator placementValidator;

	public BuildPlacement(Board board, Bank bank, PlacementValidator placementValidator) {
		this.board = board;
		this.bank = bank;
		this.placementValidator = placementValidator;
	}


	public Road placeRoad(int nodeOneID, int nodeTwoID, int playerID) {
		/*get the edge that the road will be placed on*/
		Edge edge = board.getEdgeBetweenNodes(nodeOneID, nodeTwoID);

		//if it's not a valid edge
		if(edge==null){
			throw new IllegalArgumentException("Error: A road cannot be placed between node "+nodeOneID+" and node "+nodeTwoID);
		}

		// making road object
		Road road = new Road(playerID,edge);
		//store the road in the edge
		edge.placeRoad(road);
		return road;
	}

	public void placeSettlement(int nodeID, Player player) {
		/*Node object where the settlement is being placed*/
		Node placementNode = board.getNode(nodeID);

		/*validate placement*/
		if(placementValidator.canPlaceSettlement(placementNode,player,false)){
			Settlement settlement = new Settlement(placementNode,player.getPlayerID());

			///Think about later
		}
	}
}
