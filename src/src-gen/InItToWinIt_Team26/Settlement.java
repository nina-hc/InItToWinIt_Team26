package InItToWinIt_Team26;

/**Class for settlements
 * @author Nina Hay Cooper, February 13th 2026
 */
public class Settlement {
    private Node node;//where its located
    private int ownerID;//playerID that owns it

    /**
     * Constructor
     * @param node nodeID for the settlement
     * @param ownerID playerID who owns the settlement
     */
    public Settlement(Node node, int ownerID){
        this.node=node;
        this.ownerID=ownerID;
    }

    /*getters */
    public Node getNode(){
        return node;
    }

    public int getOwner(){
        return ownerID;
    }

}
