package catan;

/**
 * Class to represent settlements in Catan. Settlement creates settlement objects that will be placed ont he board by the players.
 * Settlement objects have a playerID attached to them, victory point value and a resource multiplier
 *
 * @author Nina Hay Cooper
 * @version February 2026, McMaster University
 */
public class Settlement implements Building{

    /**
     * Node that the city is located on
     */
    private final Node node;

    /**
     * OwnerID of player that owns the city
     */
	private final int ownerID;

	/**
	 * Settlement constructor
	 * 
	 * @param node    node object for the settlement
	 * @param ownerID playerID who owns the settlement
	 */
	public Settlement(Node node, int ownerID) {
		this.node = node;
		this.ownerID = ownerID;
	}
	/* getters */

    /**
     * Getter to retrieve the owner of the settlement
     *
     * @return ownerID
     */
	@Override
	public int getOwnerID() {
		return ownerID;
	}

    /**
     * Getter to retrieve resource multiplier.
     * Players with settlement will receive one of the resource from the tile it's sitting on
     *
     * @return how many resources players receive
     */
	@Override
	public int getResourceMultiplier() {
		return 1;
	}

    /**
     * Getter to retrieve the victory point value for the building type
     * Settlements are worth 1 victory point
     *
     * @return 1, for 1 victory point
     */
	@Override
	public int getVictoryPointValue() {
		return 1;
	}

    /**
     * Getter to retrieve the node that the city is being placed on
     *
     * @return node
     */
	@Override
	public Node getNode() {
		return node;
	}

}
