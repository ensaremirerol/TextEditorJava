
import javax.swing.undo.UndoableEdit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ensar
 */
public class UndoMemento {
    private final UndoableEdit memento;

    public UndoMemento(UndoableEdit memento) {
        this.memento = memento;
    }
    
    public UndoableEdit getMemento(){
        return memento;
    }
}
