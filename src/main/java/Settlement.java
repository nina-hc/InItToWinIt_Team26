package main.java;

/**
 * Class for settlements
 * 
 * @author Nina Hay Cooper, February 13th 2026
 */
public class Settlement implements Building{
	private Node node;// where its located
	private int ownerID;// playerID that owns it

	/**
	 * Constructor
	 * 
	 * @param node    nodeID for the settlement
	 * @param ownerID playerID who owns the settlement
	 */
	public Settlement(Node node, int ownerID) {
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
		return 1;
	}

	@Override
	public int getVictoryPointValue() {
		return 1;
	}


	@Override
	public Node getNode() {
		return node;
	}

}
