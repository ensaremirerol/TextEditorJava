/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ensar
 */
import java.util.Stack;

class MementosIsEmpty extends Exception{

    public MementosIsEmpty() {
        super();
    }
    
}

public class UndoCareTaker {
    final private Stack<UndoMemento> mementos = new Stack<>();

    public UndoCareTaker() {
    }
    
    public UndoMemento getMementos() throws MementosIsEmpty{
        if(mementos.isEmpty()) throw new MementosIsEmpty();
        return mementos.pop();
    }
    
    public void addMementos(UndoMemento undoableEdit){
        mementos.add(undoableEdit);    
    }
    
    public boolean isEmpty(){
        return mementos.isEmpty();
    }
    
    public void flush(){
        if (!mementos.isEmpty()) mementos.clear();
    }
}
