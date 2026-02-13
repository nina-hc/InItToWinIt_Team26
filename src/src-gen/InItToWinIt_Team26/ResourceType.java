package InItToWinIt_Team26;

public enum ResourceType {
	LUMBER("Lumber"),
    BRICK("Brick"),
    WOOL("Wool"),
    GRAIN("Grain"),
    ORE("Ore"),
    DESERT("Desert");

    private final String resourceName;

    ResourceType(String resourceName){
        this.resourceName=resourceName;
    }

    public String getResourceName(){
        return resourceName;
    }

}
