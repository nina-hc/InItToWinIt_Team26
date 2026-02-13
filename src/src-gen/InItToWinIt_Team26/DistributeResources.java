package InItToWinIt_Team26;

/**
 * This class is responsible for distributing resources
 * to players after a dice roll.
 *
 * It checks which tiles match the dice roll,
 * then gives resources to players who have buildings
 * on the nodes of those tiles.
 */
public class DistributeResources {

    private Board board;           // The game board (contains tiles and nodes)
    private Bank bank;             // The bank (stores available resources)
    private Player[] players;      // All players in the game
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
     * 1. Rolls the dice
     * 2. Checks all tiles
     * 3. Distributes resources if roll matches tile number
     *
     * @return the dice roll result
     */
    public int executeDistribution() {

        int roll = randomizer.rollDice();  //roll the dice

        //if 7 is rolled, robber rule ignored
        if (roll == 7) {
            return roll;
        }

        //Loop through all tiles on the board
        for (Tile tile : board.getTile(roll)) {

            // If tile number matches roll AND it's not desert
            if (tile.getTileRollNum() == roll
                    && tile.getResourceType() != ResourceType.DESERT) {

                distributeFromTile(tile);
            }
        }

        return roll;
    }



    /**
     * Distributes resources from one specific tile.
     *
     * It checks each node connected to the tile.
     * If a node has a building, the building's owner
     * receives resources from the bank.
     */
    private void distributeFromTile(Tile tile) {

        //get the type of resource this tile produces
        ResourceType resource = tile.getResourceType();

        // Get all node IDs connected to this tile
        int[] nodeIDs = tile.getNodeIDs();

        // Check each node
        for (int nodeID : nodeIDs) {

            Node node = board.getNode(nodeID);

            //if a building exists on the node
            if (node.isOccupied()) {

                Build building = node.getBuilding();
                Player owner = building.getPlayer();

                //Settlement = 1 resource
                //City = 2 resources
                int amount;
                if (building instanceof BuildCity){
                    amount = 2;
                }

                else {
                    amount = 1;
                }

                //ask the bank for the resources
                int receivedFromBank = bank.transferToPlayer(resource, amount);

                //add resources to the players hand
                //Only add if bank had resources available
                if (receivedFromBank > 0) {
                    owner.getResourcehand().addResource(resource, receivedFromBank);
                }
            }

        }
    }

}
