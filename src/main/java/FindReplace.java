
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ensar
 */
public class FindReplace extends JFrame implements ActionListener{
    
    final private JTextArea textArea;
    final private JFrame window;
    private int currIndex = 0;
    private int selectionStart = -1;
    
    final private JTextField findText, replaceText;
    
    final private JButton find, findNext, replace, replaceAll, cancel;
    
    public FindReplace (JFrame window, JTextArea textArea){
        super("Find and Replace");
        this.window = window;
        this.textArea = textArea;
        
        JLabel label1 = new JLabel("Find");
        JLabel label2 = new JLabel("Replace");
        
        findText = new JTextField();
        replaceText = new JTextField();
        
        find = new JButton("Find");
        findNext = new JButton("Find Next");
        replace = new JButton("Replace");
        replaceAll = new JButton("Replace All");
        cancel = new JButton("Cancel");
        
        setLayout(null);
        
        int labelWidth = 70;
        int labelHeight = 20;
        
        label1.setBounds(10,10, labelWidth, labelHeight);
        add(label1);
        findText.setBounds(10+labelWidth, 10, 120, 20);
        add(findText);
        label2.setBounds(10, 10+labelHeight+10, labelWidth, labelHeight);
        add(label2);
        replaceText.setBounds(10+labelWidth, 10+labelHeight+10, 120, 20);
        add(replaceText);
        
        find.setBounds(225, 6, 115, 20);
        add(find);
        find.addActionListener(this);

        findNext.setBounds(225, 28, 115, 20);
        add(findNext);
        findNext.addActionListener(this);

        replace.setBounds(225, 50, 115, 20);
        add(replace);
        replace.addActionListener(this);

        replaceAll.setBounds(225, 72, 115, 20);
        add(replaceAll);
        replaceAll.addActionListener(this);

        cancel.setBounds(225, 94, 115, 20);
        add(cancel);
        cancel.addActionListener(this);
        
        setSize(360,160);
        setResizable(false);
        
        setLocationRelativeTo(textArea);
        setVisible(true);
        window.setEnabled(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
    }
    
    // Factory metod
    public static FindReplace createWindow(JFrame window, JTextArea textArea){
        return new FindReplace(window, textArea);
    }
    
    // Bulma işlevi gerçekleştirilir
    private int find(boolean showErrors){
        currIndex = 0;
        // Verilen metinin başlangıc indexi aranır
        selectionStart = textArea.getText().toLowerCase().
                indexOf(findText.getText().toLowerCase());
        // Verilen metin yok ise -1 dönderir
        if (selectionStart == -1 && showErrors){
            JOptionPane.showMessageDialog(this,
                    "Could not find \"" + findText.getText() + "\"");
            return -1;
        }
        // Seçimin son index i belirlenir
        int selectionEnd = selectionStart + findText.getText().length();
        currIndex = selectionEnd +1;
        // Editörde seçilir
        textArea.select(selectionStart, selectionEnd);
        return 1;
    }
    
    // Bir sonrakini bulma işlevini gerçekleştirir
    private void findNext(){
        
        // Null check
        String selectedString = textArea.getSelectedText().toLowerCase();
        try{
            selectedString.equals("");
        }
        catch (NullPointerException e){
            selectedString = findText.getText().toLowerCase();
            try{
                selectedString.equals("");
            }
            catch (NullPointerException e2){
                JOptionPane.showMessageDialog(this, e2);
            }
        }
        try{
            // Mevcut indexden sonraki karşılaşmayı arar
            selectionStart = textArea.getText().toLowerCase().
                    indexOf(selectedString, currIndex);
            if (selectionStart == -1){
            currIndex = 0;
            JOptionPane.showMessageDialog(this,
                    "Could not find \"" + findText.getText() + "\"");
            return;
            }
            int selectionEnd = selectionStart + selectedString.length();
            textArea.select(selectionStart, selectionEnd);
            currIndex = selectionEnd + 1;
        }
        catch (NullPointerException e){
            JOptionPane.showMessageDialog(this, e);
        }       
    }
    
    // Bulunan metini istenen metinle yer değiştirir
    private int replace(boolean showErrors){
        try{
            find(showErrors);
            if (selectionStart != -1){
               textArea.replaceSelection(replaceText.getText());
               return 1;
            }
            return -1;
            
        }
        catch (NullPointerException e){
            JOptionPane.showMessageDialog(this, "Null Pointer Exception:" + e);
            return -1;
        }
    }
    
    // Hepsini değiştir işlevi
    private void replaceAll(){
        // replace -1 döndürene kadar devam eder
        while(replace(false) == 1){}
    }
    
    @Override
    public void dispose(){
        window.setEnabled(true);
        super.dispose();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionString = e.getActionCommand();
        switch (actionString) {
            case "Find":
                find(true);
                break;
            case "Find Next":
                findNext();
                break;
            case "Replace":
                replace(true);
                break;
            case "Replace All":
                replaceAll();
                break;
            case "Cancel":
                dispose();
                break;
            default:
                break;
        }
   }
    
    
}
