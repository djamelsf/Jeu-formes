/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command;

import java.util.Stack;

/**
 *
 * @author mac
 */
public class CommandHandler {

    private Stack<Action> stackUndo;
    private Stack<Action> stackRedo;

    public CommandHandler() {
        this.stackUndo = new Stack<>();
        this.stackRedo = new Stack<>();
    }

    public void handle(Action action) {
        stackUndo.push(action);
        action.operate();
        stackRedo.clear();
    }

    public void undo() {
        Action op = stackUndo.pop();
        op.compensate();
        stackRedo.push(op);
    }

    public void redo() {
        Action op = stackRedo.pop();
        op.operate();
        stackUndo.push(op);
    }

    public Stack<Action> getStackUndo() {
        return stackUndo;
    }

    public void setStackUndo(Stack<Action> stackUndo) {
        this.stackUndo = stackUndo;
    }

    public Stack<Action> getStackRedo() {
        return stackRedo;
    }

    public void setStackRedo(Stack<Action> stackRedo) {
        this.stackRedo = stackRedo;
    }
    
    

}
