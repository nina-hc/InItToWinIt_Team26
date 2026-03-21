package catan;

/**
 * Represents a generic command in the Command design pattern.
 * Provides methods to execute and undo an action.
 *
 * @author Serene Abou Sharaf
 * March 20, 2026
 */
public interface Command {

    void execute();
    void undo();
}
