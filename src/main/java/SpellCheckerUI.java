
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
public class SpellCheckerUI extends JFrame implements ActionListener{
    final private JTextArea textArea;
    
    final private JFrame window;
    
    final private JTextField typoText, correctText;
    
    final private JButton findNext, replace, replaceAll, cancel;
    
    final private WordYielder wordYielder;
    
    final private SpellChecker spellChecker;
    
    public SpellCheckerUI(JFrame window, JTextArea textArea, SpellChecker spellChecker){
        this.window = window;
        this.textArea = textArea;
        this.spellChecker = spellChecker;
        wordYielder = new WordYielder(textArea.getText());
        JLabel label1 = new JLabel("Typo");
        JLabel label2 = new JLabel("Correct Word");
        
        typoText = new JTextField();
        typoText.setEditable(false);
        
        correctText = new JTextField();
        
        findNext = new JButton("Find Next Typo");
        replace = new JButton("Correct Typo");
        replaceAll = new JButton("Correct All Typos");
        cancel = new JButton("Cancel");
        
        setLayout(null);
        
        int labelWidth = 70;
        int labelHeight = 20;
        
        label1.setBounds(10,10, labelWidth, labelHeight);
        add(label1);
        typoText.setBounds(10+labelWidth, 10, 120, 20);
        add(typoText);
        label2.setBounds(10, 10+labelHeight+10, labelWidth, labelHeight);
        add(label2);
        correctText.setBounds(10+labelWidth, 10+labelHeight+10, 120, 20);
        add(correctText);
        
        findNext.setBounds(225, 6, 115, 20);
        add(findNext);
        findNext.addActionListener(this);

        replace.setBounds(225, 28, 115, 20);
        add(replace);
        replace.addActionListener(this);

        replaceAll.setBounds(225, 50, 115, 20);
        add(replaceAll);
        replaceAll.addActionListener(this);

        cancel.setBounds(225, 72, 115, 20);
        add(cancel);
        cancel.addActionListener(this);
        
        setSize(360,160);
        setResizable(false);
        
        setLocationRelativeTo(textArea);
        setVisible(true);
        toFront();
        requestFocus();
        window.setEnabled(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }
    
    public static SpellCheckerUI createWindow(JFrame window, JTextArea textArea,
            SpellChecker spellChecker){
        return new SpellCheckerUI(window, textArea, spellChecker);
    }
    
    public int findNext(){
        Word wordObject = wordYielder.next();
        if (wordObject != null){
            String word = wordObject.getWord();
            String resultString = spellChecker.checkWord(word);
            if (resultString.equals(word) && !word.equals("")) return findNext();
            textArea.select(wordObject.getStartIndex(),
                    wordObject.getEndIndex());
            
            typoText.setText(word);
            
            if(resultString.equals("")){
                correctText.setText(word);
            }
            else{
                correctText.setText(resultString);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Reached end of file");
            return -1;
        }
        return 0;
    }
    
    public int replace(){
        try {
            textArea.replaceSelection(correctText.getText());
            return findNext();
        } catch (NullPointerException | IndexOutOfBoundsException e) {
        }
        return -1;
    }
    
    public void replaceAll(){
        while(replace() != -1){}
    }
    
    @Override
    public void dispose(){
        window.setEnabled(true);
        super.dispose();
    }
      
    @Override
    public void actionPerformed(ActionEvent e){
        String actionString = e.getActionCommand();
        switch(actionString){
            case "Find Next Typo":
                findNext();
                break;
            case "Correct Typo":
                replace();
                break;
            case "Correct All Typos":
                replace();
                break;
            case "Cancel":
                dispose();
        }
    }
    
}
