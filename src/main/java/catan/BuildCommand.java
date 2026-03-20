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
        action.doBuild(placement); // call your BuildX doBuild
        action.printAction(placement);
    }

    @Override
    public void undo() {
        action.undoBuild(placement); // call your BuildX undoBuild
    }

}
