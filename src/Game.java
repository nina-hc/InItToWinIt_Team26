
import java.util.Random;

/**
 * Manages the overall Catan game simulation * Core responsibilities: - Setup
 * game objects and players - Handle initial settlement and road placement -
 * Distribute resources at the start and during the game - Execute player turns
 * in order - Check for victory conditions
 * 
 * @author Marva Hassan (Revisions done by Nina)
 */
public class Game {

	private Board board; // The Catan board containing nodes, tiles, and roads
	private Bank bank; // Bank managing remaining resource cards
	private Player[] players; // Array holding all 4 AI players
	private Randomizer randomizer; // handles random choices
	private DistributeResources distributor; // handles resource distribution after dice rolls
	private int maxRounds; // maximum number of simulation rounds

	/**
	 * Initialize game with 4 players and default maxRounds
	 */
	public Game(int maxRounds) {
		// R1.4
		if (maxRounds < 1 || maxRounds > 8192) {
			throw new IllegalArgumentException("maxRounds must be 1–8192");
		}
		this.maxRounds = maxRounds;

		// Initialize board and bank
		board = new Board(); //
		bank = new Bank();
		randomizer = new Randomizer();

		// create 4 players
		players = new Player[4];
		for (int i = 0; i < 4; i++) {
			players[i] = new Player(i + 1);
		}

		// connect resource distributor
		distributor = new DistributeResources(bank, players, randomizer, board);
	}

	/**
	 * Start of game has builds place for free per player
	 */
	public void initialPlacement() {
		for (int round = 0; round < 2; round++) {
			int start;
			int end;
			int step;

			// determine loop direction based on round
			if (round == 0) {
				start = 0; // Player 1 to 4
				end = players.length;
				step = 1;
			} else {
				start = players.length - 1; // Player 4 to 1
				end = -1;
				step = -1;
			}

			for (int i = start; i != end; i += step) {
				Player player = players[i];

				// settlement placement
				Settlement settlement = initialSettlementPlacement(player);

				// road placement
				initialRoadPlacement(player);

				// Give starting resources if settlement exists
				if (settlement != null) {
					giveStartingResources(player, settlement);
				}
			}
		}
	}

	/**
	 * Run the simulation for the number of defined rounds
	 */
	public void startSimulation() {
		boolean gameOver = false;
		int roundNumber = 0;

		while (!gameOver && roundNumber < maxRounds) {
			roundNumber++;

			for (Player player : players) {

				/* Dice roll */
				int roll = distributor.executeDistribution();
				System.out.println("[" + roundNumber + "] / [Player " + player.getPlayerID() + "]: Rolled " + roll);

				/* Call player actions */
				PlayerAction action = new PlayerAction(player, board, randomizer);
				action.executeTurn();

				// Print Statement of Actions
				System.out.println("[" + roundNumber + "] / Player " + player.getPlayerID() + ": ");

				// Check win conditions
				VictoryPointConditions vpCheck = new VictoryPointConditions(player, board);
				if (vpCheck.checkWinConditions()) {
					gameOver = true;
					System.out.println("[Player " + player.getPlayerID() + "]: wins with "
							+ vpCheck.calculateVictoryPoints() + " VPs!");
					break;
				}

			}

			// Print current score board after each round
			printScoreBoard(roundNumber);
		}

		System.out.println("Simulation ended after " + roundNumber + " rounds.");
	}

	/**
	 * Print current victory points for all players
	 */
	public void printScoreBoard(int roundNumber) {
		System.out.print("[" + roundNumber + "] Scoreboard: ");
		for (Player player : players) {
			// VictoryPointConditions vpCheck = new VictoryPointConditions(player, board);
			System.out.print("Player" + player.getPlayerID() + "=" + player.getVictoryPoints() + " ");
		}
		System.out.println();
	}

	/**
	 * Place a starting settlement for a player.
	 *
	 * Ensures settlement placement respects adjacency rules.
	 *
	 * @param player the player placing a settlement
	 * @return the placed Settlement, or null if placement fails
	 */
	public Settlement initialSettlementPlacement(Player player) {
		int attempts = 0;
		int maxAttempts = 1000;

		while (attempts < maxAttempts) {
			int nodeID = randomizer.randomSelection(0, 53);
			Node node = board.getNode(nodeID);

			// Check if node is empty
			if (node.isOccupied()) {
				attempts++;
				continue;
			}

			// Check distance rule (no adjacent buildings)
			boolean validDistance = true;
			for (int i = 0; i < 54; i++) {
				if (board.isAdjacent(nodeID, i)) {
					Node neighbor = board.getNode(i);
					if (neighbor.isOccupied()) {
						validDistance = false;
						break;
					}
				}
			}

			if (validDistance) {
				Settlement s = new Settlement(node, player.getPlayerID());
				node.placeSettlement(s);
				player.playerAddSettlement(s);
				System.out.println("[Player " + player.getPlayerID() + "]: placed initial settlement at " + nodeID);
				return s;
			}

			attempts++;
		}

		System.out.println("ERROR: Could not place settlement for Player " + player.getPlayerID());
		return null;
	}

	/**
	 * Place a starting road connected to the player's settlement. Tries each
	 * adjacent node until a valid road is placed.
	 */
	public void initialRoadPlacement(Player player) {
		int attempts = 0;
		int maxAttempts = 1000;

		while (attempts < maxAttempts) {
			// find a settlement belonging to this player
			Settlement playerSettlement = null;
			for (Settlement s : player.getPlayerSettlements()) {
				playerSettlement = s;
				break; // use first (most recent) settlement
			}

			if (playerSettlement == null) {
				return;
			}

			int settlementNodeID = playerSettlement.getNode().getNodeID();

			// Try to place road adjacent to settlement
			for (int neighborID : board.getNeighbors(settlementNodeID)) {
				if (!board.hasRoad(settlementNodeID, neighborID)) {
					// place road between settlement and neighbor node
					Road road = board.placeRoad(settlementNodeID, neighborID, player.getPlayerID());
					player.playerAddRoad(road); // track road for player
					System.out.println("[Player " + player.getPlayerID() + "]: placed initial road between "
							+ settlementNodeID + " and " + neighborID);
					return;
				}
			}

			attempts++;
		}

		System.out.println("ERROR: Could not place road for Player " + player.getPlayerID());
	}

	/**
	 * Give starting resources to a player based on settlement location.
	 *
	 * @param player     the player receiving resources
	 * @param settlement the settlement to check adjacent tiles
	 */
	public void giveStartingResources(Player player, Settlement settlement) {
		Node node = settlement.getNode();

		// Find tiles adjacent to this node
		for (int tileID = 0; tileID < 19; tileID++) {
			Tile tile = board.getTile(tileID);
			int[] nodeIDs = tile.getNodeIDs();

			// Check if this tile is adjacent to the settlement
			for (int id : nodeIDs) {
				if (id == node.getNodeID()) {
					ResourceType resource = tile.getResourceType();
					// All tiles produce resources except desert
					if (resource != ResourceType.DESERT) {
						int received = bank.transferToPlayer(resource, 1); // Take resource from bank
						if (received > 0) {
							player.getResourceHand().addResource(resource, received); // add to player's hand
						}
					}
					break; // stop checking nodes for this tile
				}
			}
		}

		System.out.println("Player " + player.getPlayerID() + " received starting resources: "
				+ player.getResourceHand().toString());
	}

}