/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
/**
 *
 * @author ensar
 */

// Dosya okuma yazma işlemleri için arayüzü sağlar FileHandler inhert eder
public class FileHandlerUI extends FileHandler {
    
    final private JTextArea textArea;
    final private JFrame window;
    private JFileChooser fileChooser;
    
    private boolean isSaved;
    private String filePath;
    
    public FileHandlerUI(JTextArea textArea, JFrame window){
        this.textArea = textArea;
        this.window = window;
        filePath = "";
    }
    
    // Hızlı kaydetme işlemini gerçekleştirir
    public int saveFunction(){
        if(!filePath.equals("")){
            try{
                save(filePath, textArea.getText());
                isSaved = true;
            }
            catch(CannotSaveException e){
                JOptionPane.showMessageDialog(window, "Something went wrong while saving file\n" +
                        e.getMessage());
            }          
        }
        else{
            return saveAsWindow();
        }
        return 0;
    }
    
    // Farklı kaydet işlevini gerçekleştirir
    public int saveAsWindow(){
        fileChooser = new JFileChooser("f:");
        int saveDialogResult = fileChooser.showSaveDialog(window);
        
        if(saveDialogResult == JFileChooser.APPROVE_OPTION){
            filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try{
                save(filePath, textArea.getText());
                isSaved = true;
            }
            catch(CannotSaveException e){
                JOptionPane.showMessageDialog(window, "Something went wrong while saving file\n" +
                        e.getMessage());
            }
        }
        return saveDialogResult;
    }
    
    // Açma işlevini gerçekleştirir
    public void openWindow(){
        fileChooser = new JFileChooser("f:");
        int openDialogResult = fileChooser.showSaveDialog(window);
        
        if (openDialogResult == JFileChooser.APPROVE_OPTION){
            filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try{
                textArea.setText(open(filePath));
                isSaved = true;
            }
            catch(CannotOpenException e){
               JOptionPane.showMessageDialog(window, "Something went wrong while opening file\n" +
                        e.getMessage()); 
            }
        }
    }
    
    public void fileChanged(){
        isSaved = false;
    }

    /**
     * @return the isSaved
     */
    public boolean isIsSaved() {
        return isSaved;
    }
}
