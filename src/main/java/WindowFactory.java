
import javax.swing.JFrame;
import javax.swing.JTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ensar
 */
public class WindowFactory extends AbstractWindowCreator {
    private final JFrame window;
    private final JTextArea textArea;
    public WindowFactory(JFrame window, JTextArea textArea){
        this.window = window;
        this.textArea = textArea;
    }
    @Override
    public void createWindow(String windowName){
        switch(windowName){
            case "find":
                FindReplace.createWindow(window, textArea);
                break;
            case "spellcheck":
                SpellCheckerUI.createWindow(window, textArea);
                break;
            case "editor":
                Editor.createWindow();
            default:
                break;
        }
    }
}
