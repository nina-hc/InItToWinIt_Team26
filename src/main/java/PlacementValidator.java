package main.java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlacementValidator {
	private final Board board;

	public PlacementValidator(Board board) {
		this.board = board;
	}

	/*--------Rules for settlements-------*/
	public boolean canPlaceSettlement(Node node,Player player, boolean isInitialPlacement){
		//initial placement boolean allows for this to be used for the setup too

		/*checking that the node is empty*/
		if(node.isOccupied()){
			return false;
		}

		/*check the distance rule*/
		for (Edge edge : board.getAdjacentEdges(node)){
			if(edge.getOppositeNode(node).isOccupied()){
				return false;
			}
		}

		/*if it's not the initial placements and they don't have connecting roads*/
		if(!isInitialPlacement&& !hasConnectingRoads(node, player)){
			return false;
		}
		//otherwise true
		return true;

	}

	public List<Node> getValidSettlementPlacements(Player player, boolean initialPlacement){
		List<Node> validNodes = new ArrayList<>();
		for(Node node : board.getAllNodes()){
			//if they can place a settlement there add the node
			if(canPlaceSettlement(node, player, initialPlacement)){
				validNodes.add(node);
			}
		}
		return validNodes;
	}


	/*---------Rules for Cities-------*/
	public boolean canUpgradeToCity(Settlement settlement, Player player){
		return (settlement.getOwnerID() == player.getPlayerID());
	}

	/*------Rules for roads----*/

	public boolean canBuildRoad(Edge edge, Player player){
		/*check if the edge is empty*/
		if(edge.hasRoad()){
			return false;
		}
		/*Check that the node connects to something the player owns*/
		return ((isNodeStoringForPlayer(edge.getNodeA(), player))||(isNodeStoringForPlayer(edge.getNodeB(), player)));
	}

	public List<Edge> getValidRoadEdges(Player player){
		/*get all the players used nodes*/
		Set<Node> playerNodes = getNodesUsedByPlayer(player);
		List<Edge> validEdges = new ArrayList<>();

		/*looping through the players nodes*/
		for(Node node : playerNodes){
			/*narrowing it further by looking at the adjacent edges*/
			for (Edge edge: board.getAdjacentEdges(node)){
				//if the edge doesn't have a road and we haven't the edge already
				if(!edge.hasRoad() && !validEdges.contains(edge)){
					validEdges.add(edge);//add the edge to our valid list
				}
			}
		}
		return validEdges;
	}

	/*------Helper methods-------*/

	/*checks if the player has a road connecting to a node*/
	private boolean hasConnectingRoads(Node node,Player player){
		/*go through all the adjacent edges for the node*/
		for (Edge edge : board.getAdjacentEdges(node)){
			//check if there's a road and player that match up there
			if(edge.hasRoad() && (edge.getRoad().getOwner()==player.getPlayerID())){
				return true;
			}
		}
		//otherwise
		return false;
	}

	/**
	 * Checks if the player is already using the node/storing something there. Essentially checking if they have any
	 * building on that node.
	 * @param node the node that is being checked
	 * @param player the player that is being checked
	 * @return true if the node is storing something for the player, flase othewise
	 */
	private boolean isNodeStoringForPlayer(Node node, Player player){
		/*check for roads*/
		for(Road road: player.getPlayerRoads()){
			if ((road.getNodeA() == node) || (road.getNodeB() == node)){
				return true;
			}
		}
		/*check for settlements*/
		for(Settlement settlement: player.getPlayerSettlements()){
			if(settlement.getNode() == node){
				return true;
			}
		}

		/*check for cities*/
		for(City city: player.getPlayerCities()){
			if(city.getNode() == node){
				return true;
			}
		}
		//otherwise
		return false;
	}

	/**
	 * All the nodes that have items connected to the player
	 *  This helps for the road building to avoid going through all the nodes
	 * @param player that is being checked
	 * @return nodes that the player has builds in
	 */
	private Set<Node> getNodesUsedByPlayer(Player player){
		Set<Node> nodesUsed = new HashSet<>();
		/*nodes used by roads*/
		for(Road road: player.getPlayerRoads()){
			nodesUsed.add(road.getNodeA());
			nodesUsed.add(road.getNodeB());
		}
		/*nodes used by settlements*/
		for(Settlement settlement: player.getPlayerSettlements()){
			nodesUsed.add(settlement.getNode());
		}
		/*nodes used by cities*/
		for(City city: player.getPlayerCities()){
			nodesUsed.add(city.getNode());
		}
		return nodesUsed;
	}
}
