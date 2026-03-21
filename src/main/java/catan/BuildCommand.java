package catan;

/**
 *
 * @author Serene Abou Sharaf
 * March 20, 2026
 */
public class BuildCommand implements Command{

    private Build action;
    private Object placement;

    public BuildCommand(Build action, Object placement) {
        this.action = action;
        this.placement = placement;
    }

    @Override
    public void execute() {
        action.executeWithPlacement(placement);
    }

    @Override
    public void undo() {
        action.undoBuild(placement);
    }

}
