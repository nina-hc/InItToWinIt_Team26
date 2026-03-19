package catan;

/**
 * Class to represent cities in Catan. City creates city objects that will be placed ont he board by the players.
 * City objects have a playerID attached to them, victory point multiplier and a resource multiplier
 * 
 * @author Nina Hay Cooper
 * @version February 2026, McMaster University
 */
public class City implements Building {

    /**
     * Node that the city is located on
     */
    private Node node;

    /**
     * OwnerID of player that owns the city
     */
    private int ownerID;

	/**
	 * City constructor
	 * 
	 * @param node    nodeID for the city
	 * @param ownerID playerID who owns the city
	 */
	public City(Node node, int ownerID) {
		this.node = node;
		this.ownerID = ownerID;
	}

	/* getters */

    /**
     * Getter to retrieve the owner of the city
     *
     * @return ownerID
     */
	@Override
	public int getOwnerID() {
		return ownerID;
	}

    /**
     * Getter to retrieve resource multiplier.
     * Players with cities will receive two of the resource from the tile it's sitting on
     *
     * @return how many resources players receive
     */
	@Override
	public int getResourceMultiplier() { return 2;	}

	@Override
	public int getVictoryPointValue() {
		return 2;
	}

	@Override
	public Node getNode() {
		return node;
	}



}
