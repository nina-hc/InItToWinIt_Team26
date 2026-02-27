package catan;

/**
 * This class is responsible for distributing resources to players after a dice
 * roll.
 *
 * It checks which tiles match the dice roll, then gives resources to players
 * who have buildings on the nodes of those tiles.
 * 
 * @author Marva Hassan
 */
public class DistributeResources {

	private Board board; // The game board (contains tiles and nodes)
	private Bank bank; // The bank (stores available resources)
	private Player[] players; // All players in the game
	private Randomizer randomizer; // Used to roll the dice

	/**
	 * Constructor initializes required game components
	 */
	public DistributeResources(Bank bank, Player[] players, Randomizer randomizer, Board board) {
		this.bank = bank;
		this.players = players;
		this.randomizer = randomizer;
		this.board = board;
	}

	/**
	 * Executes a full resource distribution round.
	 *
	 * 1. Rolls the dice 2. Checks all tiles 3. Distributes resources if roll
	 * matches tile number
	 *
	 * @return the dice roll result
	 */
	public int executeDistribution() {

		int roll = randomizer.rollDice(); // roll the dice

		// if 7 is rolled, robber rule ignored
		if (roll == 7) {
			return roll;
		}

		// Loop through all tiles on the board
		for (int i = 0; i < 19; i++) { // 19 tiles
			Tile tile = board.getTile(i); // get tile by tileID
			if (tile.getTileRollNum() == roll && tile.getResourceType() != ResourceType.DESERT) {
				distributeFromTile(tile);
			}
		}

		return roll;
	}

	/**
	 * Distributes resources from one specific tile.
	 *
	 * It checks each node connected to the tile. If a node has a building, the
	 * building's owner receives resources from the bank.
	 */
	public void distributeFromTile(Tile tile) {

		ResourceType resource = tile.getResourceType();
		int[] nodeIDs = tile.getNodeIDs();

		// loop through each node
		for (int nodeID : nodeIDs) {
			Node node = board.getNode(nodeID);
			Building building = node.getBuilding();

			if (building != null) {
				int ownerID = building.getOwnerID();
				Player owner = players[ownerID - 1]; // convert IDs 1-4 to array (0-3)

				int receivedFromBank = building.getResourceMultiplier();
				if(receivedFromBank >0){
					owner.getResourceHand().addResource(resource, receivedFromBank);

				}
			}
		}
	}

}
