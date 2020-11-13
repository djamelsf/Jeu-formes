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

    public CommandHandler(Stack<Action> stackUndo, Stack<Action> stackRedo) {
        this.stackUndo = stackUndo;
        this.stackRedo = stackRedo;
    }
    
    public void handle(Action action){
        //
    }
    
    public void undo(){
        //
    }
    
    public void redo(){
       //
    }
    
}
