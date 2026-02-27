package main.java;

import java.util.*;

/**
 * The following is the Board class It's in charge of creating the starting
 * Board. This includes all the Tiles, their information along with all the
 * Nodes and their information It is also the Boards job to update the map
 * whenever a new Settlement/City/Road is placed
 *
 * @author Synthia Rosenberger
 * @version February 2026, McMaster University
 */
public class Board {
	/*board constants that are slightly more stable than magic numbers*/
	private static final int NUMBER_OF_NODES = 54;
	private static final int NUMBER_OF_TILES = 19;

	// ================================================================================
	// INSTANCE VARIABLES
	// ================================================================================

	/**
	 * Tiles array holds Tile objects
	 */
	private final Tile[] tiles;

	/**
	 * Nodes array holds Node objects
	 */
	private final Node[] nodes;

	private final Edge[] edges;


	// ================================================================================
	// METHODS
	// ================================================================================

	/**
	 * Board constructor, creates a Board object
	 */
	public Board() {
		nodes = new Node[NUMBER_OF_NODES]; // array size of 54 for 54 node objects
		tiles = new Tile[NUMBER_OF_TILES]; // array size of 19 for 19 tiles

		// create node objects and storing it in nodes array
		for (int i = 0; i < NUMBER_OF_NODES; i++) {
			nodes[i] = new Node(i);
		}

		constructTiles();

		//make the edges
		edges = constructEdges();

	}

	/**
	 * Method in charge of generating the starting map This includes assigning the
	 * Node's neighbors, and assigning Nodes to Tiles
	 */
	private void constructTiles() {



		// **************************************
		// FOR TILES
		// **************************************

		// create tile objects:
		// tiles[INDEX/ID] = new Tile(ID, ROLLNUM, RESOURCE)
		tiles[0] = new Tile(0, 10, ResourceType.LUMBER);
		tiles[1] = new Tile(1, 11, ResourceType.GRAIN);
		tiles[2] = new Tile(2, 8, ResourceType.BRICK);
		tiles[3] = new Tile(3, 3, ResourceType.ORE);
		tiles[4] = new Tile(4, 11, ResourceType.WOOL);
		tiles[5] = new Tile(5, 5, ResourceType.WOOL);
		tiles[6] = new Tile(6, 12, ResourceType.WOOL);
		tiles[7] = new Tile(7, 3, ResourceType.GRAIN);
		tiles[8] = new Tile(8, 6, ResourceType.ORE);
		tiles[9] = new Tile(9, 4, ResourceType.LUMBER);
		tiles[10] = new Tile(10, 6, ResourceType.ORE);
		tiles[11] = new Tile(11, 9, ResourceType.GRAIN);
		tiles[12] = new Tile(12, 5, ResourceType.LUMBER);
		tiles[13] = new Tile(13, 9, ResourceType.BRICK);
		tiles[14] = new Tile(14, 8, ResourceType.BRICK);
		tiles[15] = new Tile(15, 4, ResourceType.GRAIN);
		tiles[16] = new Tile(16, 7, ResourceType.DESERT);
		tiles[17] = new Tile(17, 2, ResourceType.LUMBER);
		tiles[18] = new Tile(18, 10, ResourceType.WOOL);

		// add vertices to the tile objects:
		// tiles[INDEX/ID].setNodes(new int[] {6 NODES ON TILE});
		tiles[0].setNodes(new int[] { 0, 1, 2, 3, 4, 5 });
		tiles[1].setNodes(new int[] { 1, 6, 7, 8, 9, 2 });
		tiles[2].setNodes(new int[] { 3, 2, 9, 10, 11, 12 });
		tiles[3].setNodes(new int[] { 15, 4, 3, 12, 13, 14 });
		tiles[4].setNodes(new int[] { 18, 16, 5, 4, 15, 17 });
		tiles[5].setNodes(new int[] { 21, 19, 20, 0, 5, 16 });
		tiles[6].setNodes(new int[] { 20, 22, 23, 6, 1, 0 });
		tiles[7].setNodes(new int[] { 7, 24, 25, 26, 27, 8 });
		tiles[8].setNodes(new int[] { 9, 8, 27, 28, 29, 10 });
		tiles[9].setNodes(new int[] { 11, 10, 29, 30, 31, 32 });
		tiles[10].setNodes(new int[] { 13, 12, 11, 32, 33, 34 });
		tiles[11].setNodes(new int[] { 37, 14, 13, 34, 35, 36 });
		tiles[12].setNodes(new int[] { 39, 17, 15, 14, 37, 38 });
		tiles[13].setNodes(new int[] { 42, 40, 18, 17, 39, 41 });
		tiles[14].setNodes(new int[] { 44, 43, 21, 16, 18, 40 });
		tiles[15].setNodes(new int[] { 45, 47, 46, 19, 21, 43 });
		tiles[16].setNodes(new int[] { 46, 48, 49, 22, 20, 19 });
		tiles[17].setNodes(new int[] { 49, 50, 51, 52, 23, 22 });
		tiles[18].setNodes(new int[] { 23, 52, 53, 24, 7, 6 });

	}

	private Edge[] constructEdges(){
		// **************************************
		// NODE NEIGHBORS
		// **************************************

		// an array of node neighbors to help build the map:
		int[][] neighbors = {

				{ 1, 5, 20 }, // 0 : node number
				{ 0, 2, 6 }, // 1
				{ 1, 3, 9 }, // 2
				{ 2, 4, 12 }, // 3
				{ 3, 5, 15 }, // 4
				{ 0, 4, 16 }, // 5
				{ 1, 7, 23 }, // 6
				{ 6, 8, 24 }, // 7
				{ 7, 9, 27 }, // 8
				{ 2, 8, 10 }, // 9
				{ 9, 11, 29 }, // 10
				{ 10, 12, 32 }, // 11
				{ 3, 11, 13 }, // 12
				{ 12, 14, 34 }, // 13
				{ 13, 15, 37 }, // 14
				{ 4, 14, 17 }, // 15
				{ 5, 18, 21 }, // 16
				{ 15, 18, 39 }, // 17
				{ 16, 17, 40 }, // 18
				{ 20, 21, 46 }, // 19
				{ 0, 19, 22 }, // 20
				{ 16, 19, 43 }, // 21
				{ 20, 23, 49 }, // 22
				{ 6, 22, 52 }, // 23
				{ 7, 25, 53 }, // 24
				{ 24, 26 }, // 25
				{ 25, 27 }, // 26
				{ 26, 28 }, // 27
				{ 27, 29 }, // 28
				{ 28, 30 }, // 29
				{ 29, 31 }, // 30
				{ 30, 32 }, // 31
				{ 31, 33 }, // 32
				{ 32, 34 }, // 33
				{ 33, 35 }, // 34
				{ 34, 36 }, // 35
				{ 35, 37 }, // 36
				{ 36, 38 }, // 37
				{ 37, 39 }, // 38
				{ 38, 41 }, // 39
				{ 42, 44 }, // 40
				{ 39, 42 }, // 41
				{ 40, 41 }, // 42
				{ 44, 45 }, // 43
				{ 40, 43 }, // 44
				{ 43, 47 }, // 45
				{ 47, 48 }, // 46
				{ 45, 46 }, // 47
				{ 46, 49 }, // 48
				{ 48, 50 }, // 49
				{ 49, 51 }, // 50
				{ 50, 52 }, // 51
				{ 51, 53 }, // 52
				{ 53, 24 }, // 53
		};

		List<Edge> edgeList = new ArrayList<>();
		/*create custom ID for edges (essential is chosen only from the order they go in so not super meaningful)*/
		int edgeID=0;//again still could cut

		// connecting node to each neighbor
		for (int i = 0; i < neighbors.length; i++) {

			for (int neighbor : neighbors[i]) {
				if(i<neighbor){
					edgeList.add(new Edge(edgeID++,nodes[i],nodes[neighbor]));//maybe pull increment out so its not
					// doing too much
				}
			}
		}
		return edgeList.toArray(new Edge[0]);
	}

	// **************************************
	// BUILDINGS
	// **************************************

	//nodes know where the buildings are and the nodes are on the board

	// **************************************
	// ROADS
	// **************************************

	/**
	 * Method used to update the occupancy matrix when a Road is placed
	 *
	 * @param nodeOneID
	 * @param nodeTwoID ID number to reference second Node object
	 * @param playerID  ID number to reference player object
	 * @return road object that is placed
	 */
	public Road placeRoad(int nodeOneID, int nodeTwoID, int playerID) {
		/*get the edge that the road will be placed on*/
		Edge edge = getEdgeBetweenNodes(nodeOneID, nodeTwoID);

		//if it's not a valid edge
		if(edge==null){
			throw new IllegalArgumentException("Error: A road cannot be placed between node "+nodeOneID+" and node "+nodeTwoID);
		}

		// making road object
		Road road = new Road(nodeOneID, nodeTwoID, playerID);
		//store the road in the edge
		edge.placeRoad(road);
		return road;
	}
	// **************************************
	// OTHERS
	// **************************************

	/**
	 * Getter for Node objects
	 *
	 * @param nodeID ID number to reference Node objects
	 * @return nodeID
	 */
	public Node getNode(int nodeID) {
		return nodes[nodeID];
	}

	/**
	 * To retrieve a Tile object
	 *
	 * @param tileID ID number used to reference Tile objects
	 * @return tile
	 */
	public Tile getTile(int tileID) {
		return tiles[tileID];
	}

	public Edge[] getAllEdges(){
		return edges;
	}

	public Tile[] getAllTiles(){
		return tiles;
	}

	public Node[] getAllNodes(){
		return nodes;
	}
	/**
	 * Gets the adjacent edges, utilizes the edge class to essentially check the shared end points which is what the
	 * matrix did
	 * @param node the node you are checking adjacency for
	 * @return the edges that are adjacent
	 */
	public List<Edge> getAdjacentEdges(Node node) {
		List<Edge> adjacentEdges = new ArrayList<>();
		for(Edge edge : edges){
			//if the edge shares a node its adjacent
			if(edge.sharesNode(node)){
				adjacentEdges.add(edge);
			}
		}
		return adjacentEdges;
	}


	/**
	 * Gets the edge between two nodes
	 * @param nodeAID the first node
	 * @param nodeBID the second node
	 * @return the edge if there is one, or null if there is not
	 */
	public Edge getEdgeBetweenNodes(int nodeAID, int nodeBID) {
		/*going through the edges to check for a match*/
		for (Edge edge : edges) {
			/*getting the two IDs stored in the actual edges*/
			int nodeOneID = edge.getNodeA().getNodeID();
			int nodeTwoID = edge.getNodeB().getNodeID();

			/*if the nodeIDs match up to an edge, return the edge*/
			//one way to match
			if((nodeOneID == nodeAID)&&(nodeTwoID == nodeBID)){
				return edge;

			}
			//second way
			if((nodeOneID == nodeBID)&&(nodeTwoID == nodeAID)){
				return edge;
			}
		}
		//otherwise return null
		return null;

	}

	/**
	 * Check if two nodes are adjacent. This is the same functionality as the adjacency matrix
	 * @param nodeAID one of the nodes being checked
	 * @param nodeBID the second node being checked
	 * @return true if they are adjacent, false otherwise
	 */
	public boolean isAdjacent(int nodeAID,int nodeBID) {
		return getEdgeBetweenNodes(nodeAID,nodeBID) != null;
	}

}