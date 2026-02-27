package catan;

public interface Building {
	public int getOwnerID();
	int getResourceMultiplier();
	int getVictoryPointValue();
	public Node getNode();
}
