package catan;

import java.util.Stack;

/**
 *
 * @author Serene Abou Sharaf
 * March 20, 2026
 */
public class CommandManager {

    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    //Execute a new command
    public void executeCommand(Command command) {
        command.execute(); // run the command
        undoStack.push(command); // store in undo stack
        redoStack.clear(); // clear redo stack when a new action is done
    }

    //Undo the last command
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

    // Redo the last undone command
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

    //helper methods
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}
