package main.java;

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
	private int nodeID;

	/**
	 * Settlement object that's placed on a Node Null if no Settlement has been
	 * placed
	 */
	private Settlement settlement;

	/**
	 * City object that's place on a Node Null if no City has been placed
	 */
	private City city;

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
		this.settlement = null; // node begins empty
		this.city = null;
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
	 * Method to check if a Node currently has a Settlement or City built on it
	 *
	 * @return true if Node is occupied, false if not
	 */
	public boolean isOccupied() {
		if (settlement != null || city != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to place a Settlement on a Node
	 *
	 * @param settlement object that's being placed on the Node
	 */
	public void placeSettlement(Settlement settlement) {
		if (!isOccupied()) { // proceed if Node is not occupied
			this.settlement = settlement;
		} else {
			System.out.println("Node is occupied");
		}

	}

	/**
	 * Method to place a City on a Node, must be a Settlement previously in order to
	 * become a City
	 *
	 * @param city object that's being placed on the Node
	 */
	public void placeCity(City city) {
		if (this.settlement == null) {
			throw new IllegalArgumentException("Error: No settlement here to replace");
		}
		this.settlement = null; // remove settlement
		this.city = city; // place city

	}

	/**
	 * Settlement getter
	 *
	 * @return returns empty if null, returns settlement if full
	 */
	public Settlement getSettlement() {
		return settlement;
	}

	public City getCity() {
		return city; // if empty... returns null... if its full it will return the Build object
	}

}