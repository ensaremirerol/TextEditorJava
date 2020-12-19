/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import javax.swing.*; 
import java.awt.event.*; 
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.metal.*; 
import javax.swing.undo.UndoManager;
/**
 *
 * @author ensar
 */
public class Editor extends JFrame implements ActionListener {
    private static int editorCount = 0;
    
    private JTextArea textArea; // Text area
    
    private UndoManager undoManager = new UndoManager();
    
    private final JPopupMenu popMenu;
    
    private FileHandlerUI fileHandlerUI;
    
    private FindReplace findReplace;
    
    private static SpellChecker spellChecker;
    
    private final JMenuBar menuBar;
    
    private final JMenu menu1, menu2;
    
    private final JMenuItem menu1Item1, menu1Item2 , menu1Item3, menu1Item4,
            menu2Item1, menu2Item2, menu2Item3, menu2Item4, menu2Item5,
            menu2Item6, menu2Item7, popMenuItem1, popMenuItem2, popMenuItem3;
    
    private final JScrollPane scrollPane;
    
    Editor(){
        super("Text Editor"); 
        
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // Theme setting
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        }
        catch(ClassNotFoundException | IllegalAccessException |
                InstantiationException | UnsupportedLookAndFeelException e){           
        }
        
        textArea = new JTextArea();
        if(spellChecker == null){
            try{
                spellChecker = new SpellChecker("./words.txt");
            }
            catch (CannotInitilazeWordDictionary e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }        
        }
        
        fileHandlerUI = new FileHandlerUI(textArea, this);
        
        textArea.getDocument().addUndoableEditListener(undoManager); // Adding undoManager
        
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fileHandlerUI.fileChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fileHandlerUI.fileChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fileHandlerUI.fileChanged();
            }
        });
        
        popMenu = new JPopupMenu();
        
        // Adding mouse listener for popMenu
        textArea.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseReleased(MouseEvent me) {
            showPopMenu(me);
         }
         }) ;
        
        // Adding JTextArea to JScrollPane for ability of scrolling
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
             
        // Menu Bar init
        menuBar = new JMenuBar();
        
        
        // File menu init
        menu1 = new JMenu("File");
        
        menu1Item1 = new JMenuItem("New");
        menu1Item1.setAccelerator(KeyStroke.getKeyStroke('N',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        menu1Item2 = new JMenuItem("Open");
        menu1Item2.setAccelerator(KeyStroke.getKeyStroke('O',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        menu1Item3 = new JMenuItem("Save"); 
        menu1Item3.setAccelerator(KeyStroke.getKeyStroke('S',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        menu1Item4 = new JMenuItem("Save As"); 
        menu1Item4.setAccelerator(KeyStroke.getKeyStroke('S',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() |
                        KeyEvent.SHIFT_DOWN_MASK));
        
        menu1Item1.addActionListener(this);
        menu1Item2.addActionListener(this);
        menu1Item3.addActionListener(this);
        menu1Item4.addActionListener(this);
        
        menu1.add(menu1Item1);
        menu1.add(menu1Item2);
        menu1.add(menu1Item3);
        menu1.add(menu1Item4);
        
        
       // Edit Menu init 
        menu2 = new JMenu("Edit");
        
        menu2Item1 = new JMenuItem("Cut"); 
        menu2Item1.setAccelerator(KeyStroke.getKeyStroke('X',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        menu2Item2 = new JMenuItem("Copy");
        menu2Item2.setAccelerator(KeyStroke.getKeyStroke('C',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        menu2Item3 = new JMenuItem("Paste");
        menu2Item3.setAccelerator(KeyStroke.getKeyStroke('V',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        menu2Item4 = new JMenuItem("Undo");
        menu2Item4.setAccelerator(KeyStroke.getKeyStroke('Z',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        menu2Item5 = new JMenuItem("Redo");
        menu2Item5.setAccelerator(KeyStroke.getKeyStroke('Z',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() | KeyEvent.SHIFT_DOWN_MASK));
        menu2Item6 = new JMenuItem("Find"); 
        menu2Item6.setAccelerator(KeyStroke.getKeyStroke('F',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        menu2Item7 = new JMenuItem("Spell check"); 
        
        menu2Item1.addActionListener(this);
        menu2Item2.addActionListener(this);
        menu2Item3.addActionListener(this);
        menu2Item4.addActionListener(this);
        menu2Item5.addActionListener(this);
        menu2Item6.addActionListener(this);
        menu2Item7.addActionListener(this);
       
        menu2.add(menu2Item1);
        menu2.add(menu2Item2);
        menu2.add(menu2Item3);
        menu2.add(menu2Item4);
        menu2.add(menu2Item5);
        menu2.add(menu2Item6);
        menu2.add(menu2Item7);
        
        // PopMenu menu items init
        
        popMenuItem1 = new JMenuItem("Cut"); 
        popMenuItem2 = new JMenuItem("Copy");
        popMenuItem3 = new JMenuItem("Paste");

        
        popMenuItem1.addActionListener(this);
        popMenuItem2.addActionListener(this);
        popMenuItem3.addActionListener(this);
        
        popMenu.add(popMenuItem1);
        popMenu.add(popMenuItem2);
        popMenu.add(popMenuItem3);
        
        menuBar.add(menu1);
        menuBar.add(menu2);
        
        // Window init
        setJMenuBar(menuBar);
        add(scrollPane);
        setSize(600,600);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        
        editorCount += 1;
    }
    
    public static Editor createWindow(){
        return new Editor();
    }
 
    // Shows popup menu at position of mouse
    private void showPopMenu(MouseEvent me){
        if(me.isPopupTrigger())
         popMenu.show(me.getComponent(), me.getX(), me.getY());
    }
    
    private void onExit(){
        if(!fileHandlerUI.isIsSaved()){
            switch(saveWarning()){
                case JOptionPane.YES_OPTION:
                    if(fileHandlerUI.saveFunction() == JFileChooser.APPROVE_OPTION){
                        Dispose();
                    }
                    break;
                     
                case JOptionPane.NO_OPTION:
                    Dispose();
                    break;
                     
                case JOptionPane.CANCEL_OPTION:
                    break;
            }
        }
        else{
            Dispose();
        }
    }
    
    private int saveWarning(){
        String[] buttonLabels = new String[] {"Yes", "No", "Cancel"};
        String defaultOption = buttonLabels[0];
        Icon icon = null;
        return JOptionPane.showOptionDialog(this,
                "There's still something unsaved.\n" +
                "Do you want to save before exiting?",
                "Warning",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                icon,
                buttonLabels,
                defaultOption);     
    }
    
    private void Dispose(){
        if (findReplace != null){
            findReplace.dispose();
        }
        dispose();
        editorCount -= 1;
        if (editorCount <=0){
            System.exit(editorCount);
        }
    }
    
    // MenuBar Actions
    @Override
    public void actionPerformed(ActionEvent e) 
    { 
        String actionString = e.getActionCommand(); 
  
        switch (actionString) {
            case "Cut":
                textArea.cut();
                break;
            case "Copy":
                textArea.copy();
                break;
            case "Paste":
                textArea.paste();
                break;
            case "Undo":
                if(undoManager.canUndo())undoManager.undo();
                break;
            case "Redo":
                if(undoManager.canRedo())undoManager.redo();
                break;
            case "Find":
                findReplace = new FindReplace(this, textArea);
                break;
            case "Save":
                fileHandlerUI.saveFunction();
                break;
            case "Save As":
                fileHandlerUI.saveAsWindow();
                break;
            case "Open":
                fileHandlerUI.openWindow();
                break;
            case "New": 
                createWindow();
                break;
            case "Spell check":
                SpellCheckerUI.createWindow(this, textArea, spellChecker);
                break;
            default:
                break;
        }
    } 
}
