
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ensar
 */
public class UndoInvoker implements UndoableEditListener{
    private boolean triggerListener = true;
    final private Command undo, redo;
    final private UndoCareTaker undoMemento, redoMemento;
    final private UndoOrginator orginator;
    public UndoInvoker(){
        undoMemento = new UndoCareTaker();
        redoMemento = new UndoCareTaker();
        orginator = new UndoOrginator(null);
        undo = new UndoCommand(undoMemento, redoMemento, orginator);
        redo = new RedoCommand(undoMemento, redoMemento, orginator);
        
    }
    
    public void undo(){
        triggerListener = false;
        undo.execute();
        triggerListener = true;
    }
    
    public void redo(){
        triggerListener = false;
        redo.execute();
        triggerListener = true;
    }

    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        if(triggerListener){
            undoMemento.addMementos(orginator.saveMementos());
            orginator.setMementos(new UndoMemento(e.getEdit()));
            redoMemento.flush();
        }
        
    }
    
}

class UndoCommand implements Command{
    final private UndoCareTaker undoMemento, redoMemento;
    final private UndoOrginator orginator;

    public UndoCommand(UndoCareTaker undoMementos, UndoCareTaker redoMementos, UndoOrginator orginator) {
        this.undoMemento = undoMementos;
        this.redoMemento = redoMementos;
        this.orginator = orginator;
    }
    
    @Override
    public void execute(){
        try {
            UndoMemento newMemento, oldMemento;
            oldMemento = orginator.saveMementos();
            newMemento = undoMemento.getMementos();
            if(oldMemento.getMemento().canUndo()){
                oldMemento.getMemento().undo();
                orginator.setMementos(newMemento);
                redoMemento.addMementos(oldMemento);
            }
        } catch (MementosIsEmpty e) {
        }
    }
}

class RedoCommand implements Command{
    final private UndoCareTaker undoMemento, redoMemento;
    final private UndoOrginator orginator;

    public RedoCommand(UndoCareTaker undoMementos, UndoCareTaker redoMementos, UndoOrginator orginator) {
        this.undoMemento = undoMementos;
        this.redoMemento = redoMementos;
        this.orginator = orginator;
    }
    
    @Override
    public void execute(){
        try {
            UndoMemento newMemento, oldMemento;
            oldMemento = orginator.saveMementos();
            newMemento = redoMemento.getMementos();
            if(oldMemento == null && newMemento.getMemento().canRedo()){
                newMemento.getMemento().redo();
            }
            else if(newMemento.getMemento().canRedo()){
                newMemento.getMemento().redo();      
            }
            orginator.setMementos(newMemento);
            undoMemento.addMementos(oldMemento);
        } catch (MementosIsEmpty e) {
        }
    }
}
