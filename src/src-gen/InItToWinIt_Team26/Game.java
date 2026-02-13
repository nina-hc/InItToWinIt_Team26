package InItToWinIt_Team26;

import java.util.Random;

/**
 * Manages the overall Catan game simulation
 * Fulfills Assignment 1 requirements (R1.1–R1.9)
 */
public class Game {

    private static final int WINNING_VP = 10;

    private Board board;
    private Bank bank;
    private Player[] players;
    private Randomizer randomizer;
    private DistributeResources distributor;
    private int maxRounds;

    /**
     * Initialize game with 4 players and default maxRounds
     */
    public Game(int maxRounds) {
        if (maxRounds < 1 || maxRounds > 8192) {
            throw new IllegalArgumentException("maxRounds must be 1–8192");
        }
        this.maxRounds = maxRounds;

        // Initialize board and bank
        board = new Board(); // Should use fixed map setup per R1.1
        bank = new Bank();
        randomizer = new Randomizer();

        // Create 4 players
        players = new Player[4];
        for (int i = 0; i < 4; i++) {
            players[i] = new Player(i + 1);
        }

        // Connect resource distributor
        distributor = new DistributeResources(bank, players, randomizer, board);
    }

    /**
     * Initial placement phase: each player places 2 settlements and 2 roads
     */
    public void initialPlacement() {
        for (int round = 0; round < 2; round++) {
            // Forward for first round, backward for second round
            int start = (round == 0) ? 0 : players.length - 1;
            int end = (round == 0) ? players.length : -1;
            int step = (round == 0) ? 1 : -1;

            for (int i = start; i != end; i += step) {
                Player player = players[i];
                // Place settlement (free during setup)
                Settlement settlement = initialSettlementPlacement(player);
                
                // Place road (free during setup)
                initialRoadPlacement(player);
                
                if (settlement != null) {
                    giveStartingResources(player, settlement);
                }
            }
        }
    }

    /**
     * Main simulation loop until a player wins or maxRounds reached
     */
    public void startSimulation() {
        boolean gameOver = false;
        int roundNumber = 0;

        while (!gameOver && roundNumber < maxRounds) {
            roundNumber++;

            System.out.println("=== Round " + roundNumber + " ===");

            for (Player player : players) {

                // Roll dice
                int roll = distributor.executeDistribution();
                System.out.println("[" + roundNumber + "] / Player " + player.getPlayerID() + ": Rolled " + roll);

                // AI builds (tries to spend cards if >7)
                PlayerAction action = new PlayerAction(player, board, randomizer);
                action.executeTurn();

                // Print player action summary
                System.out.println("[" + roundNumber + "] / Player " + player.getPlayerID() + ": Completed turn");

                // Check win conditions
                VictoryPointConditions vpCheck = new VictoryPointConditions(player, board);
                if (vpCheck.checkWinConditions()) {
                    gameOver = true;
                    System.out.println("Player " + player.getPlayerID() + " wins with " + vpCheck.calculateVictoryPoints() + " VPs!");
                    break;
                }

                System.out.println("Player " + player.getPlayerID() + " hand: "
                        + player.getResourceHand());


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
            //VictoryPointConditions vpCheck = new VictoryPointConditions(player, board);
            System.out.print("Player" + player.getPlayerID() + "=" + player.getVictoryPoints() + " ");
        }
        System.out.println();
    }

    /**
     * Attempt to place a settlement randomly (used in initial placement)
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
     * Attempt to place a road randomly (used in initial placement)
     */
    public void initialRoadPlacement(Player player) {
        int attempts = 0;
        int maxAttempts = 1000;
        
        while (attempts < maxAttempts) {
            // Find a settlement belonging to this player
            Settlement playerSettlement = null;
            for (Settlement s : player.getPlayerSettlements()) {
                playerSettlement = s;
                break; // Use first (most recent) settlement
            }
            
            if (playerSettlement == null) {
                return;
            }
            
            int settlementNodeID = playerSettlement.getNode().getNodeID();
            
            // Try to place road adjacent to settlement
            for (int neighborID : board.getNeighbors(settlementNodeID)) {
                if (!board.hasRoad(settlementNodeID, neighborID)) {
                    Road road = board.placeRoad(settlementNodeID, neighborID, player.getPlayerID());
                    player.playerAddRoad(road);
                    System.out.println("[Player " + player.getPlayerID() + "]: placed initial road between " + settlementNodeID + " and " + neighborID);
                    return;
                }
            }
            
            attempts++;
        }
        
        System.out.println("ERROR: Could not place road for Player " + player.getPlayerID());
    }

    private void giveStartingResources(Player player, Settlement settlement) {
        Node node = settlement.getNode();
        
        // Find tiles adjacent to this node
        for (int tileID = 0; tileID < 19; tileID++) {
            Tile tile = board.getTile(tileID);
            int[] nodeIDs = tile.getNodeIDs();
            
            // Check if this tile is adjacent to the settlement
            for (int id : nodeIDs) {
                if (id == node.getNodeID()) {
                    ResourceType resource = tile.getResourceType();
                    if (resource != ResourceType.DESERT) {
                        int received = bank.transferToPlayer(resource, 1);
                        if (received > 0) {
                            player.getResourceHand().addResource(resource, received);
                        }
                    }
                    break;
                }
            }
        }
        
        System.out.println("Player " + player.getPlayerID() + " received starting resources: " + player.getResourceHand().toString());
    }
    
}