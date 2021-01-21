
import javax.swing.undo.UndoManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ensar
 */
public class UndoInvoker{
    final private Command undo;
    final private Command redo;
    public UndoInvoker(UndoManager undoManager){
        undo = new UndoCommand(undoManager);
        redo = new RedoCommand(undoManager);
    }
    
    public void undo(){
        undo.execute();
    }
    
    public void redo(){
        redo.execute();
    }
    
}

class UndoCommand implements Command{
    final private UndoManager undoManager;

    public UndoCommand(UndoManager undoManager) {
        this.undoManager = undoManager;
    }
    
    @Override
    public void execute(){
        if(undoManager.canUndo()) undoManager.undo();
    }
}

class RedoCommand implements Command{
    final private UndoManager undoManager;

    public RedoCommand(UndoManager undoManager) {
        this.undoManager = undoManager;
    }
    
    @Override
    public void execute(){
        if(undoManager.canRedo()) undoManager.redo();
    }
}