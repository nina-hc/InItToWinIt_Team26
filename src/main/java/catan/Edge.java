package catan;

public class Edge {
	private final int edgeID; //maybe?
	/* Pair of nodes */
	private final Node nodeA;// starting node
	private final Node nodeB;// ending node
	private Road road;//the road stored on the edge or null if empty


	public Edge(int edgeID, Node nodeA, Node nodeB) {
		this.edgeID = edgeID;//again idk about this it would be us assigning so maybe not worth it
		this.nodeA = nodeA;
		this.nodeB = nodeB;

	}

	public Node getNodeA() {
		return nodeA;
	}

	public Node getNodeB() {
		return nodeB;
	}

	public int getEdgeID() {
		return edgeID;
	}

	public Road getRoad() {
		return road;
	}

	public boolean hasRoad(){
		return road != null;
	}

	public void placeRoad(Road road){
		if(this.road != null){
			throw new IllegalStateException("Edge already has a Road");
		}
		//otherwise place
		this.road=road;
	}

	//helpers for checking adjacency

	/*given one node, use the edge to get the node at the other end*/
	public Node getOppositeNode(Node node){//useful for settlement building?
		if(node == nodeA){
			return nodeB;
		}
		if(node == nodeB){
			return nodeA;
		}
		//if we get here somethings wrong
		throw new IllegalArgumentException("Error: Node "+node.getNodeID()+" does not correspond to an edge.");
	}

	/**
	 * Checks if the edge shares a node with a provided node.
	 * @param node the node being checked
	 * @return true if the edge and node do share a common node, false otherwise
	 */
	public boolean sharesNode(Node node){
		return (node == nodeA || node == nodeB);
	}

	/**
	 * Checks if two edges are adjacent using the nodes at either end.
	 * @param otherEdge the edge being compared to
	 * @return true if they are adjacent, false otherwise.
	 */
	public boolean isAdjacentTo(Edge otherEdge){
		return (sharesNode(otherEdge.getNodeA()) || sharesNode(otherEdge.getNodeB()));
	}
}
