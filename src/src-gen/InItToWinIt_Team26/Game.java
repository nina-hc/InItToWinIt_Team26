package InItToWinIt_Team26;

import java.util.Random;

/**
 * Manages the overall Catan game simulation
 * Fulfills Assignment 1 requirements (R1.1–R1.9)
 */
public class Game {

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
     * Start of game has builds place for free
     */
    public void initialPlacement() {
        for (int round = 0; round < 2; round++) {
            // Forward for first round, backward for second round
            int start = (round == 0) ? 0 : players.length - 1;
            int end = (round == 0) ? players.length : -1;
            int step = (round == 0) ? 1 : -1;

            for (int i = start; i != end; i += step) {
                Player player = players[i];
                // settlement for set up
                Settlement settlement = initialSettlementPlacement(player);
                
                // The roads for set up
                initialRoadPlacement(player);
                
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

                /*Dice roll */
                int roll = distributor.executeDistribution();
                System.out.println("[" + roundNumber + "] / [Player " + player.getPlayerID() + "]: Rolled " + roll);

                /*Call player actions */
                PlayerAction action = new PlayerAction(player, board, randomizer);
                action.executeTurn();

                // Print Statement of Actions
                System.out.println("[" + roundNumber + "] / Player " + player.getPlayerID() + ": ");

                // Check win conditions
                VictoryPointConditions vpCheck = new VictoryPointConditions(player, board);
                if (vpCheck.checkWinConditions()) {
                    gameOver = true;
                    System.out.println("[Player " + player.getPlayerID() + "]: wins with " + vpCheck.calculateVictoryPoints() + " VPs!");
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
            //VictoryPointConditions vpCheck = new VictoryPointConditions(player, board);
            System.out.print("Player" + player.getPlayerID() + "=" + player.getVictoryPoints() + " ");
        }
        System.out.println();
    }

    /**
     * Place Settlements for start of game.
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
     * Place roads for start of game
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