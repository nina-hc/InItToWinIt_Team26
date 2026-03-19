package catan;

import java.util.List;

/**
 * BuildRoad extends off of the Build abstract class and represents the action of building a road in the game.
 * BuildRoad checks resources, checks for available edges, generates placement, validates placement,
 * preforms the build and prints the action that was completed.
 * 
 * @author Nina Hay Cooper
 * @version February 2026, McMaster University
 */
public class BuildRoad extends Build {

    /**
	 * Constructor for BuildRoad
	 *
	 * @param player             Player who is building the road
	 * @param board              the board game
	 * @param randomizer         randomizer object
	 * @param placementValidator placement generator
	 */
	public BuildRoad(Player player, Board board, Randomizer randomizer,Bank bank, PlacementValidator placementValidator) {
		super(player, board, randomizer, bank, placementValidator);

	}


    /**
     * Checking that the player has enough resources to buy a road
     * Also checks if there are enough roads left to be bought from
     *
     * @return true if a road can be bought, false otherwise
     */
	@Override
	protected boolean canPlayerBuy() {
		boolean hasResources = player.getResourceHand().canBuyRoad();
		boolean hasRoadsLeft = player.getPlayerRoadsLeft() > 0;

		return hasResources && hasRoadsLeft;
	}


    /**
     * Method used to generate a random placement at the beginning of the game
     *
     * @return road object
     */
	@Override
	protected Object generatePlacement() {
		/*use the placement validator to get the placements*/
		List<Edge> validEdges = placementValidator.getValidRoadEdges(player);
		/*if there are no valid places return null*/
		if(validEdges.isEmpty()){
			return null;
		}
		/*select a random index from the valid edges*/
		int index = randomizer.randomSelection(0, validEdges.size()-1);
		return validEdges.get(index);
	}


    /**
     * Check if a road can be built on the edge
     *
     * @param placement the target location for the placement
     * @return true if it can be built, false if not
     */
	@Override
	protected boolean validatePlacement(Object placement) {
		//type case edge to placement
		Edge edge = (Edge) placement;
		return placementValidator.canBuildRoad(edge, player);

	}


    /**
     * Method that preforms the building operation.
     * This includes paying for the build, updating the board, and updating player info
     *
     * @param placement the target location for the placement
     */
	@Override
	protected void doBuild(Object placement) {
		Edge edges = (Edge) placement;

		/* Pay for the build */
		player.getResourceHand().payForRoad(bank);

		/* Create road, add it to the player and the edge */
		Road road = new Road (player.getPlayerID(),edges);
		edges.placeRoad(road);
		player.playerAddRoad(road);

        //export to visualizer
        StateExporter.exportState(board);

	}


    /**
     * Method in charge of printing the build action that just ocured
     *
     * @param placement target placement
     */
	@Override
	public void printAction(Object placement) {
		Edge edges = (Edge) placement;

		System.out.printf("[Player " + player.getPlayerID() + "] : [Built a Road between %d and %d]\n",
				edges.getNodeA().getNodeID(),
				edges.getNodeB().getNodeID());
	}

}
