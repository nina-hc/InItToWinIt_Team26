package InItToWinIt_Team26;

import java.util.*;

/**
 * The following is the Board class
 * It's in charge of creating the starting Board. This includes all the Tiles, their information along with all the Nodes and their information
 * It is also the Boards job to update the map whenever a new Settlement/City/Road is placed
 *
 * @author Synthia Rosenberger
 * @version February 2026, McMaster University
 */
public class Board {

    //================================================================================
    //INSTANCE VARIABLES
    //================================================================================

    /**
     * Tiles array holds Tile objects
     */
    private Tile[] tiles;

    /**
     * Nodes array holds Node objects
     */
    private Node[] nodes;

    /**
     * Static 2D array, for the board layout
     * Keeps track of all the Nodes and their neighbors, this never changes
     */
    private int[][] adjacentMatrix;

    /**
     * Dynamic 2D array, to keep track of Roads and the Nodes they connect to
     */
    private Road[][] roadMatrix;


    //================================================================================
    //METHODS
    //================================================================================

    /**
     * Board constructor, creates a Board object
     */
    public Board() {
        nodes = new Node[54];   //array size of 54 for 54 node objects
        tiles = new Tile[19];   //array size of 19 for 19 tiles

        adjacentMatrix = new int[54][54];   //fill all ints with 0s
        roadMatrix = new Road[54][54];

        originalMap();  //to construct the map
    }


    /**
     * Method in charge of generating the starting map
     * This includes assigning the Node's neighbors, and assigning Nodes to Tiles
     */
    private void originalMap() {

        //**************************************
        // FOR NODES
        //**************************************

        //create node objects and storing it in nodes array
        for (int i = 0; i < 54; i++) {
            nodes[i] = new Node(i);
        }


        //**************************************
        // FOR TILES
        //**************************************

        //create tile objects:
        //tiles[INDEX/ID] = new Tile(ID, ROLLNUM, RESOURCE)
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


        //add vertices to the tile objects:
        //tiles[INDEX/ID].setNodes(new int[] {6 NODES ON TILE});
        tiles[0].setNodes(new int[] {0, 1, 2, 3, 4, 5});
        tiles[1].setNodes(new int[] {1, 6, 7, 8, 9, 2});
        tiles[2].setNodes(new int[] {3, 2, 9, 10, 11, 12});
        tiles[3].setNodes(new int[] {15, 4, 3, 12, 13, 14});
        tiles[4].setNodes(new int[] {18, 16, 5, 4, 15, 17});
        tiles[5].setNodes(new int[] {21, 19, 20, 0, 5, 16});
        tiles[6].setNodes(new int[] {20, 22, 23, 6, 1, 0});
        tiles[7].setNodes(new int[] {7, 24, 25, 26, 27, 8});
        tiles[8].setNodes(new int[] {9, 8, 27, 28, 29, 10});
        tiles[9].setNodes(new int[] {11, 10, 29, 30, 31, 32});
        tiles[10].setNodes(new int[] {13, 12, 11, 32, 33, 34});
        tiles[11].setNodes(new int[] {37, 14, 13, 34, 35, 36});
        tiles[12].setNodes(new int[] {39, 17, 15, 14, 37, 38});
        tiles[13].setNodes(new int[] {42, 40, 18, 17, 39, 41});
        tiles[14].setNodes(new int[] {44, 43, 21, 16, 18, 40});
        tiles[15].setNodes(new int[] {45, 47, 46, 19, 21, 43});
        tiles[16].setNodes(new int[] {46, 48, 49, 22, 20, 19});
        tiles[17].setNodes(new int[] {49, 50, 51, 52, 23, 22});
        tiles[18].setNodes(new int[] {23, 52, 53, 24, 7, 6});


        //**************************************
        // NODE NEIGHBORS
        //**************************************

        //an array of node neighbors to help build the map:
        int[][] neighbors = {

                {1, 5, 20},     //0 : node number
                {0, 2, 6},     //1
                {1, 3, 9},     //2
                {2, 4, 12},     //3
                {3, 5, 15},     //4
                {0, 4, 16},     //5
                {1, 7, 23},     //6
                {6, 8, 24},     //7
                {7, 9, 27},     //8
                {2, 8, 10},     //9
                {9, 11, 29},     //10
                {10, 12, 32},     //11
                {3, 11, 13},     //12
                {12, 14, 34},     //13
                {13, 15, 37},     //14
                {4, 14, 17},     //15
                {5, 18, 21},     //16
                {15, 18, 39},     //17
                {16, 17, 40},     //18
                {20, 21, 46},     //19
                {0, 19, 22},     //20
                {16, 19, 43},     //21
                {20, 23, 49},     //22
                {6, 22, 52},     //23
                {7, 25, 53},     //24
                {24, 26},     //25
                {25, 27},     //26
                {26, 28},     //27
                {27, 29},     //28
                {28, 30},     //29
                {29, 31},     //30
                {30, 32},     //31
                {31, 33},     //32
                {32, 34},     //33
                {33, 35},     //34
                {34, 36},     //35
                {35, 37},     //36
                {36, 38},     //37
                {37, 39},     //38
                {38, 41},     //39
                {42, 44},     //40
                {39, 42},     //41
                {40, 41},     //42
                {44, 45},     //43
                {40, 43},     //44
                {43, 47},     //45
                {47, 48},     //46
                {45, 46},     //47
                {46, 49},     //48
                {48, 50},     //49
                {49, 51},     //50
                {50, 52},     //51
                {51, 53},     //52
                {53, 24},     //53
        };


        //connecting node to each neighbor
        for (int i = 0; i < neighbors.length; i++) {

            for (int neighbor : neighbors[i]) {
                connect(i, neighbor);
            }
        }

    }


    /**
     * Connect method to help connect neighboring Nodes when generating the Board
     * Sets both directions, for an undirected graph
     * Same both ways is 1 connects to 2, then 2 connects to 1
     *
     * @param i first node
     * @param j second node
     */
    private void connect(int i, int j) {
        adjacentMatrix[i][j] = 1;
        adjacentMatrix[j][i] = 1;
    }


    //**************************************
    //ADJACENCY
    //**************************************

    /**
     * Node adjacency matrix
     * adjacent[i][j] = 1 : i is connected to j
     * adjacent[i][j] = 0 : they are not connected
     * Never changes, this is a static 2D array used to represent the Board
     *
     * @param i first node
     * @param j second node
     * @return true if nodes are adjacent, false if they are not
     */
    public boolean isAdjacent(int i, int j) {
        if (adjacentMatrix[i][j] == 1) {    //are adjacent
            return true;

        } else {
            return false;
        }

    }


    //**************************************
    //BUILDINGS
    //**************************************

    /**
     * Method used to update the occupancy matrix when a Settlement is placed
     *
     * @param nodeID ID number ot reference Node objects
     * @param playerID  ID number used to represent Player objects
     * @return true if the place is successful
     */
    public boolean placeSettlementOnMat(int nodeID, int playerID) {
        Node node = nodes[nodeID];

        if (!node.isOccupied()) {   //check node object if it's occupied
            Settlement settlement = new Settlement(node, playerID); //make object
            node.placeSettlement(settlement);   //place object
            return true;
        }

        return false;
    }

    /**
     * Method used to update the occupancy matrix when a City is placed
     * Cities can only be placed on top of Settlements owned by the player
     *
     * @param nodeID ID number to reference Node object
     * @param playerID ID number to reference player object
     * @return true if the place is successful
     */
    public boolean placeCityOnMat(int nodeID, int playerID) {
        Node node = nodes[nodeID];

        if (!node.isOccupied()) {   //check node object if it's occupied
            City city = new City(node, playerID); //make object
            node.placeCity(city);   //place object
            return true;
        }

        return false;
    }


    //**************************************
    //ROADS
    //**************************************

    /**
     * Method used to update the occupancy matrix when a Road is placed
     *
     * @param nodeOneID
     * @param nodeTwoID ID number to reference second Node object
     * @param playerID ID number to reference player object
     * @return road object that is placed
     */
    public Road placeRoad(int nodeOneID, int nodeTwoID, int playerID) {//NINA changed it to road

        //retrieve node objects
        Node nodeOne = nodes[nodeOneID];
        Node nodeTwo = nodes[nodeTwoID];

        //making road object
        Road road = new Road(nodeOne, nodeTwo, playerID);

        roadMatrix[nodeOneID][nodeTwoID] = road;    //mark road in both directions
        roadMatrix[nodeTwoID][nodeOneID] = road;
        
        return road;
    }


    /**
     * Method to check if Road exists
     *
     * @param nodeOne first node checking
     * @param nodeTwo second node checking
     * @return true if the edge contains a road, false if not
     */
    public boolean hasRoad(int nodeOne, int nodeTwo) {
        if (roadMatrix[nodeOne][nodeTwo] != null) { //edge is not empty
            return true;
        } else {
            return false;   //edge is empty
        }
    }


    //**************************************
    //OTHERS
    //**************************************

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

    /**
     * To retrieve Node neighbors
     * 
     * @param nodeID ID number used to reference Node objects
     * @return Node neighbors
     */
    public List<Integer> getNeighbors(int nodeID) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 54; i++) {
            if (adjacentMatrix[nodeID][i] == 1) {
                list.add(i);
            }
        }

        return list;
    }

}