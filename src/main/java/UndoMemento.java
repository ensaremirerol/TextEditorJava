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
    private final String memento;

    public UndoMemento(String memento) {
        this.memento = memento;
    }
    
    public String getMemento(){
        return memento;
    }
}
