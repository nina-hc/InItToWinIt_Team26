package InItToWinIt_Team26;

public class Settlement {
    private Node node;//where its located
    private int ownerID;//playerID that owns it

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
