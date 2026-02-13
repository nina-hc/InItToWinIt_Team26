
package InItToWinIt_Team26;

import java.util.*;

public class Board {

    private Tile[] tiles;       //tiles array holds Tile objects
    private Node[] nodes;       //nodes array holds Node objects

    //static 2D array, for our map layout
    private int[][] adjacentMatrix; //never changes

    //dynamic (changing) arrays to keep track of occupied nodes
    //private int[] settlementMatrix;   //for settlements and cities... index = nodeID, value = playerID same for roads GETTING RID OF THIS CUZ WANT NODE TO STORE ITS OWN STUFF
    private Road[][] roadMatrix;     //for roads


    //-----------------------------------------------------


    //constructor
    public Board() {
        nodes = new Node[54];   //array size of 54 for 54 node objects
        tiles = new Tile[19];   //array size of 19 for 19 tiles

        adjacentMatrix = new int[54][54];   //fill all ints with 0s
        //settlementMatrix = new int[54];   //NODES WILL SELF CARE
        roadMatrix = new Road[54][54];

        originalMap();  //build map bro
    }


    //method that creates and stores the original map
    private void originalMap() {

        //create node objects and storing it in nodes array
        for (int i = 0; i < 54; i++) {
            nodes[i] = new Node(i);

        }


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

        //more tile stuff...
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




        //=========================================
        //make an array of node neighbors to help build the map:
        int[][] neighbors = {

                {1, 5, 20},     //0
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


        //connect loop
        for (int i = 0; i < neighbors.length; i++) {

            for (int neighbor : neighbors[i]) {
                connect(i, neighbor);       //connecting node to each neighbor
            }
        }

    }

    //connect method to help in creating the map
    private void connect(int i, int j) {
        adjacentMatrix[i][j] = 1;   //sets both directions, for an undirected graph (same both ways... is 1 connects to 2, then 2 connects to 1)
        adjacentMatrix[j][i] = 1;
    }


    //**************************************
    //ADJACENCY

    //adjacency matrix!!
    //adjacent[i][j] = 1 : i is connected to j
    //adjacent[i][j] = 0 : they are not connected
    //for an undirected board... adj[i][j] == adj[j][i]
    //never changes, static 2D array, represents the map
    public boolean isAdjacent(int i, int j) {
        if (adjacentMatrix[i][j] == 1) {    //are adjacent
            return true;

        } else {
            return false;
        }

    }

    //**************************************




//    public boolean isOccupied(int nodeID) {
//        if (settlementMatrix[nodeID] == 0) {    //not occupied
//            return false;
//
//        } else {
//            return true;
//        }
//    }



    //**************************************
    //BUILDINGS

    //this is to update the occupancy matrix
    //SETTLEMENT
    public boolean placeSettlementOnMat(int nodeID, int playerID) {
        //settlementMatrix[nodeID] = playerID;    //this will be marked with player id so its known whos settlement is on the node
        Node node = nodes[nodeID];

        if (!node.isOccupied()) {   //check node object if it's occupied
            Settlement settlement = new Settlement(node, playerID); //make object
            node.placeSettlement(settlement);   //place object (NOTE TO SELF, I DO THIS FOR ALL THE BUILDINGS)
            return true;
        }

        return false;
    }

    //this is to update the occupancy matrix
    //CITY
    public boolean placeCityOnMat(int nodeID, int playerID) {
        //settlementMatrix[nodeID] = playerID;    //this will be marked with player id so its known whos settlement is on the node
        Node node = nodes[nodeID];

        if (!node.isOccupied()) {   //check node object if it's occupied
            City city = new City(node, playerID); //make object
            node.placeCity(city);   //place object (NOTE TO SELF, I DO THIS FOR ALL THE BUILDINGS)
            return true;
        }

        return false;
    }
    //**************************************


    //**************************************
    //ROADS

    public boolean placeRoad(int nodeOneID, int nodeTwoID, int playerID) {

        //roadMatrix[i][j] = 0 : no road
        //roadMatrix[i][j] = playerID : player that owns road

        //check is nodes are adjacent
        if(!isAdjacent(nodeOneID, nodeTwoID)) {
            System.out.println("ERROR: Nodes are not adjacent");
            return false;
        }

        if (hasRoad(nodeOneID, nodeTwoID)) {
            System.out.println("ERROR: Road already exsists");
            return false;
        }

        //retrieve node objects
        Node nodeOne = nodes[nodeOneID];
        Node nodeTwo = nodes[nodeTwoID];

        //making road object
        Road road = new Road(nodeOne, nodeTwo, playerID);

        roadMatrix[nodeOneID][nodeTwoID] = road;
        roadMatrix[nodeTwoID][nodeOneID] = road;

        return true;


    }

    //method to check if a road exists
    public boolean hasRoad(int nodeOne, int nodeTwo) {
        if (roadMatrix[nodeOne][nodeTwo] != null) {
            return true;
        } else {
            return false;
        }
    }
    //**************************************


    //********************************************************

    //GETTERS:
    public Node getNode(int nodeID) {
        return nodes[nodeID];
    }

    public Tile getTile(int tileID) {
        return tiles[tileID];
    }
    
    public List<Integer> getNeighbors(int nodeID) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 54; i++) {
            if (adjacentMatrix[nodeID][i] == 1) {
                list.add(i);
            }
        }

        return list;
    }
    


    //********************************************************

}