package main.java;

/**
 * This is the Tile class It's in charge of creating Tile objects that will be
 * used to build the game Board
 *
 * @author Synthia Rosenberger
 * @version February 2026, McMaster University
 */
public class Tile {

	// ================================================================================
	// INSTANCE VARIABLES
	// ================================================================================

	/**
	 * Tile ID number from 0-18, assigned to each tile to help build to Board
	 */
	private int tileID;

	/**
	 * Roll number assigned to each Tile When the dice rolls the roll number,
	 * players on the tile can collect the corresponding resource from the Bank
	 */
	private int tileRollNum;

	/**
	 * Type of resource players on the tile will collect when the roll number is
	 * rolled
	 */
	private ResourceType resourceType;

	/**
	 * To know which Nodes are associated with the tile
	 */
	private int[] nodeIDs;

	// ================================================================================
	// METHODS
	// ================================================================================

	/**
	 * Tile constructor, to create Tile objects Each Tile will have a unique ID
	 * number, roll number, and resource type
	 *
	 * @param tileID       ID number used to reference Tile object
	 * @param tileRollNum  associated roll number on tile, determines when the
	 *                     resource is collected
	 * @param resourceType type of resource collected when roll number is rolled
	 */
	public Tile(int tileID, int tileRollNum, ResourceType resourceType) {
		this.tileID = tileID;
		this.tileRollNum = tileRollNum;
		this.resourceType = resourceType;
	}

	/**
	 * Method to set Node objects as Tile vertices
	 *
	 * @param nodeIDs To know which Nodes are associated with the tile
	 */
	public void setNodes(int[] nodeIDs) {
		this.nodeIDs = nodeIDs;
	}

	/**
	 * Getter to retrieve Tile ID number
	 *
	 * @return tileID
	 */
	public int getTileID() {
		return tileID;
	}

	/**
	 * Getter to retrieves the roll number for the Tile object
	 *
	 * @return tileRollNum
	 */
	public int getTileRollNum() {
		return tileRollNum;
	}

	/**
	 * Getter to retrieve the resource associated with the tile
	 *
	 * @return resourceType
	 */
	public ResourceType getResourceType() {
		return resourceType;
	}

	/**
	 * Getter to retrieve Nodes that are associated with Tile object
	 *
	 * @return nodeIDs
	 */
	public int[] getNodeIDs() {
		return nodeIDs;
	}
}