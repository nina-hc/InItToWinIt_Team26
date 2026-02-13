
package InItToWinIt_Team26;


public class Tile {

    //instance variables:
    private int tileID;
    private int tileRollNum;
    private ResourceType resourceType;
    private int[] nodeIDs;  //to know associated nodes with each tile



    //constructor:
    public Tile(int tileID, int tileRollNum, ResourceType resourceType) {
        this.tileID = tileID;
        this.tileRollNum = tileRollNum;
        this.resourceType = resourceType;
    }



    //setter for nodes:
    public void setNodes(int[] nodeIDs) {
        this.nodeIDs = nodeIDs;
    }

    //tileID getter:
    public int getTileID() {
        return tileID;
    }

    //tileRollNum getter
    public int getTileRollNum() {
        return tileRollNum;
    }

    //resourceType getter
    public ResourceType getResourceType() {
        return resourceType;
    }

    //nodeIDs getter
    public int[] getNodeIDs() {
        return nodeIDs;
    }
}