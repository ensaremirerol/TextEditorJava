
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
    private String text;

    public UndoOrginator(String text) {
        this.text = text;
    }
    
    public String saveMementos(){
        return text;
    }
    
    public void setMementos(String mementos){
        this.text = mementos;
    }
}
