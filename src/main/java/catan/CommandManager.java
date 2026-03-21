package catan;

import java.util.Stack;

/**
 * Manages execution, undo, and redo of commands using stacks.
 * Also implements the Command pattern manager to keep track of executed actions.
 *
 * @author Serene Abou Sharaf
 * March 20, 2026
 */
public class CommandManager {

    /** Stack storing executed commands for undo operations */
    private Stack<Command> undoStack = new Stack<>();

    /** Stack storing undone commands for redo operations */
    private Stack<Command> redoStack = new Stack<>();

    /**
     * Executes a new command, pushes it onto the undo stack,
     * and clears the redo stack
     *
     * @param command the command to execute
     */
    public void executeCommand(Command command) {
        command.execute(); // run the command
        undoStack.push(command); // store in undo stack
        redoStack.clear(); // clear redo stack when a new action is done
    }

    /**
     * Undoes the last executed command.
     * Moves the command from the undo stack to the redo stack.
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo(); // reverse the action
            redoStack.push(cmd); // push it to redo stack
        }
        else {
        System.out.println("Nothing to undo.");
        }
    }

    /**
     * Redoes the last undone command.
     * Moves the command from the redo stack back to the undo stack.
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute(); // re-execute the command
            undoStack.push(cmd); // put it back on undo stack
        }
        else {
            System.out.println("Nothing to redo.");
        }
    }

    /** Returns true if there are commands that can be undone */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /** Returns true if there are commands that can be redone */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}
