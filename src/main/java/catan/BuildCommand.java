package catan;

/**
 * Implements the Command pattern for build actions in the game.
 * Ecnapsulates a build action and their placement information which allows the
 * action to be executed or undone without the user needing to know the details of the build.
 *
 *
 * @author Serene Abou Sharaf
 * March 20, 2026
 */
public class BuildCommand implements Command{

    /**
     * The build action to be preformed for road, settlement or city
     */
    private Build action;

    /**
     * The placement information for the build
     */
    private Object placement;

    /**
     * Constructor for BuildCommand
     * @param action the Build action to encapsulate
     * @param placement the placement/location information for the build
     */
    public BuildCommand(Build action, Object placement) {
        this.action = action;
        this.placement = placement;
    }

    /**
     * Executes the build action at the specified placement.
     * Delegates the actual build logic to the Build object.
     */
    @Override
    public void execute() {
        action.executeWithPlacement(placement);
    }

    /**
     * Undoes the previously executed build action.
     * Delegates the undo logic to the Build object.
     */
    @Override
    public void undo() {
        action.undoBuild(placement);
    }

}
