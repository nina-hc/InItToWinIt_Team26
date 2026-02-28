package catan;

/**
 * Class to represent cities in Catan
 * 
 * @author Nina Hay Cooper, February 13th 2026
 */
public class City implements Building {
	private Node node;// where its located
	private int ownerID;// playerID that owns it

	/**
	 * Constructor
	 * 
	 * @param node    nodeID for the city
	 * @param ownerID playerID who owns the city
	 */
	public City(Node node, int ownerID) {
		this.node = node;
		this.ownerID = ownerID;
	}
	/* getters */
	@Override
	public int getOwnerID() {
		return ownerID;
	}

	@Override
	public int getResourceMultiplier() {
		return 2;//double the resources for
	}

	@Override
	public int getVictoryPointValue() {
		return 2;
	}

	@Override
	public Node getNode() {
		return node;
	}



}
