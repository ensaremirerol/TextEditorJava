
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


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
    final private JTextArea textArea;
    public UndoInvoker(JTextArea textArea){
        this.textArea = textArea;
        undoMemento = new UndoCareTaker();
        redoMemento = new UndoCareTaker();
        orginator = new UndoOrginator(textArea.getText());
        undo = new UndoCommand(undoMemento, redoMemento, orginator);
        redo = new RedoCommand(undoMemento, redoMemento, orginator);
        
    }
    
    public void undo(){
        triggerListener = false;
        undo.execute();
        textArea.setText(orginator.saveMementos());
        triggerListener = true;
    }
    
    public void redo(){
        triggerListener = false;
        redo.execute();
        textArea.setText(orginator.saveMementos());
        triggerListener = true;
    }

    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        if(triggerListener){
            Document obj = (Document) e.getSource();
            try{
                undoMemento.addMementos(obj.getText(0, obj.getLength()));
            }
            catch(BadLocationException a){}
            orginator.setMementos(textArea.getText());
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
            String memento = undoMemento.getMementos();
            orginator.setMementos(memento);
            redoMemento.addMementos(memento);
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
            String memento = redoMemento.getMementos();
            orginator.setMementos(memento);
            undoMemento.addMementos(memento);
        } catch (MementosIsEmpty e) {
        }
    }
}