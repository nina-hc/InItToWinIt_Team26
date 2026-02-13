
package InItToWinIt_Team26;

//code needs to be editted... documents and comments

public class Node {         //node object

    //only knows what built on it... doesn't worry about neighbors

    //instance variables
    private int nodeID;

    //private Build building;     //what type of building (settlement/city) is on this node
    private Settlement settlement;
    private City city;


    //constructor:
    public Node(int nodeID) {   //allowing you to assign nodeIDs to the node object
        this.nodeID = nodeID;
        //this.building = null;       //node begins empty
        this.settlement = null;
        this.city = null;
    }

    //nodeID getter:
    public int getNodeID() {
        return nodeID;
    }

    //is there something built on the node
    public boolean isOccupied() {
        if (settlement != null || city != null) {
            return true;
        } else {
            return false;
        }
    }

    //im thinking this can return true/false for build
    //update occupied status when a building is placed


    //SETTLEMENT
    public void placeSettlement(Settlement settlement) {
        if (!isOccupied()) {    //placing a building on node
            this.settlement  = settlement;
        } else {
            System.out.println("Node is occupied");
        }

    }

    //CITY
    public void placeCity(City city) {
        if(this.settlement==null){
            throw new IllegalArgumentException("Error: No settlement here to replace");
        }
        this.settlement=null;
        this.city  = city;
        
        
        

    }

    //building getter
    public Settlement getSettlement() {
        return settlement;            //if empty... returns null... if its full it will return the Build object
    }

    public City getCity() {
        return city;            //if empty... returns null... if its full it will return the Build object
    }

}