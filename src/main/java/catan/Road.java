package catan;

/**
 * Class to represent a road
 * 
 * @author Nina Hay Cooper
 */
public class Road {
	/* Attributes for Road */
	private final int ownerID;
	private final Edge edge;


	public Road(int ownerID, Edge edge) {
		this.ownerID = ownerID;
		this.edge = edge;
	}

	/* Getters */
	public int getOwner() {
		return ownerID;
	}
	public Edge getEdge() {
		return edge;
	}

	//get them from edge as a shortcut? nah I don't think so
	public Node getNodeA() {
		return edge.getNodeA();
	}
	public Node getNodeB() {
		return edge.getNodeB();
	}

}
