package catan;

/**
 * This is the Node class It's in charge of creating Node objects that will be
 * used to build the game Board
 *
 * @author Synthia Rosenberger
 * @version February 2026, McMaster University
 */
public class Node {

	// ================================================================================
	// INSTANCE VARIABLES
	// ================================================================================

	/**
	 * Node identification number from 0-53, that will be used to build settlements,
	 * cities and roads Allows for reference to specific Node objects
	 */
	private final int nodeID; //the nodeID shouldn't change

	private Building building;

	// ================================================================================
	// METHODS
	// ================================================================================

	/**
	 * Node constructor, creates Node objects Each will have a unique ID number
	 *
	 * @param nodeID ID number used to reference Node object
	 */
	public Node(int nodeID) {
		this.nodeID = nodeID;
		this.building = null;//initializes as empty
	}

	/**
	 * Getter to retrieve Node ID number
	 *
	 * @return nodeID
	 */
	public int getNodeID() {
		return nodeID;
	}

	/**
	 * Method to check if a Node currently has a building
	 *
	 * @return true if Node is occupied, false if not
	 */
	public boolean isOccupied() {
		return (building != null);
	}

	/**
	 * Gets the building currently on the node
	 * @return the building on the node or null if there is no building
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * Method to place a Settlement on a Node
	 *
	 * @param settlement object that's being placed on the Node
	 */
	public void placeSettlement(Settlement settlement) {
		if (isOccupied()) {
			throw new IllegalStateException("Error: Node "+nodeID+" is already occupied.");
		}
		//Otherwise place settlement in node
		this.building = settlement;

	}

	/**
	 * Method to place a City on a Node, must be a Settlement previously in order to
	 * become a City
	 *
	 * @param city object that's being placed on the Node
	 */
	public void upgradeToCity(City city) {
		if (!(building instanceof Settlement)) {
			throw new IllegalStateException("Error: Node "+ nodeID+ " does contain a settlement. Cities must " +
					"upgrade settlements.");
		}
		this.building = city;

	}


}