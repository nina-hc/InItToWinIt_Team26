
package InItToWinIt_Team26;

//code needs to be editted... documents and comments

public class Node {         //node object

    //only knows what built on it... doesn't worry about neighbors

    //instance variables
    private int nodeID;
    private Build building;     //what type of building (settlement/city) is on this node



    //constructor:
    public Node(int nodeID) {   //allowing you to assign nodeIDs to the node object
        this.nodeID = nodeID;
        this.building = null;       //node begins empty
    }

    //nodeID getter:
    public int getNodeID() {
        return nodeID;
    }

    //is there something built on the node
    public boolean isOccupied() {
        if (building != null) {
            return true;
        } else {
            return false;
        }
    }

    //im thinking this can return true/false for build
    //update occupied status when a building is placed
    public void placeBuilding(Build building) {
        if (!isOccupied()) {    //placing a building on node
            this.building  = building;
        } else {
            System.out.println("Node is occupied");
        }

    }

    //building getter
    public Build getBuilding() {
        return building;            //if empty... returns null... if its full it will return the Build object
    }

}