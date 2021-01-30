
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ensar
 */
public class UndoOrginator {
    private UndoMemento memento;

    public UndoOrginator() {}
    
    public UndoMemento saveMementos(){
        return memento;
    }
    
    public void setMementos(UndoMemento mementos){
        this.memento = mementos;
    }
}
