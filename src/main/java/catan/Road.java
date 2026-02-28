package catan;

/**
 * Class to represent a road
 * 
 * @author Nina Hay Cooper
 */
public class Road {
	/* Attributes for Road */
	private Node nodeA;
	private Node nodeB;
	private int ownerID;

	/**
	 * Constructor
	 * 
	 * @param nodeA   one node the road is between
	 * @param nodeB   second node the road is between
	 * @param ownerID playerID of the owner
	 */
	public Road(Node nodeA, Node nodeB, int ownerID) {
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.ownerID = ownerID;
	}

	/* Getters */
	public Node getNodeA() {
		return nodeA;
	}

	public Node getNodeB() {
		return nodeB;
	}

	public int getOwner() {
		return ownerID;
	}

}
