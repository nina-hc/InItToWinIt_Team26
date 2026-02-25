
/**
 * Class to represent cities in Catan
 * 
 * @author Nina Hay Cooper, February 13th 2026
 */
public class City {
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
	public Node getNode() {
		return node;
	}

	public int getOwner() {
		return ownerID;
	}

}
