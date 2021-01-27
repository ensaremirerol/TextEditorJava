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
/**
 *
 * @author ensar
 */
public class Editor extends JFrame implements ActionListener {
    // Toplam açık olan editor pencersinin sayısnı tutar
    private static int editorCount = 0;
    
    // Metin bloğu
    private JTextArea textArea; // Text area
    
    private UndoInvoker undoInvoker;
    
    private WindowFactory windowFactory;
    
    // Sağ tıklama menüsü
    private final JPopupMenu popMenu;
    
    // Dosya okuma/yazma işlemleri için arayüz
    private FileHandlerUI fileHandlerUI;
    
    // Pencere menü barı
    private final JMenuBar menuBar;
    
    // Pencere bar menüleri
    private final JMenu menu1, menu2;
    
    // Pencere bar menülerinin itemları
    private final JMenuItem menu1Item1, menu1Item2 , menu1Item3, menu1Item4,
            menu2Item1, menu2Item2, menu2Item3, menu2Item4, menu2Item5,
            menu2Item6, menu2Item7, popMenuItem1, popMenuItem2, popMenuItem3;
    
    // Tüm ekarnın kaydırlabilir olmasını sağlamaktdadır
    //      textArea bunun içine eklenmektedir
    private final JScrollPane scrollPane;
    
    Editor(){
        // Pencere adı
        super("Text Editor"); 
        
        // Tema ayarı
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // Theme setting
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        }
        catch(ClassNotFoundException | IllegalAccessException |
                InstantiationException | UnsupportedLookAndFeelException e){           
        }
        
        textArea = new JTextArea();
        
        undoInvoker = new UndoInvoker(textArea);
        
        // SpellChecker ın tanımlanması
        // Olası hata nedeni: words.txt bulunamadı
        if(SpellChecker.instance == null){
            try{
                SpellChecker.initialize("./words.txt");
            }
            catch (CannotInitilazeWordDictionary e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }        
        }
        
        fileHandlerUI = new FileHandlerUI(textArea, this);
        
        // undoManager textArea içindeki document objesinie bağlanır
        textArea.getDocument().addUndoableEditListener(undoInvoker); // Adding undoManager
        
        windowFactory = new WindowFactory(this, textArea);
        
        // Bu custom DocumentListener dosya değiştiğinde haber verir
        // Dosyanın kayıt edilmesine gerek olup olmadığı bu listenr ile belirlenir
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
        
        // Pop menu için dinleyici eklenir
        textArea.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseReleased(MouseEvent me) {
            showPopMenu(me);
         }
         }) ;
        
        // JScrollPane e JTextArea eklenerek pencerinin kaydırılması sağlanır
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
        
        // WindowFactory init
        setJMenuBar(menuBar);
        add(scrollPane);
        setSize(600,600);
        // Pencere kapama eylemleri değiştirilir
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
    
    // Factory method
    public static void createWindow(){
        new Editor();
    }
 
    // Popmenu yü farenin bulunduğu noktada açar
    private void showPopMenu(MouseEvent me){
        if(me.isPopupTrigger())
         popMenu.show(me.getComponent(), me.getX(), me.getY());
    }
    
    // Pencereden çıkılmak istenirse bu metoda girlir
    private void onExit(){
        // Dosya kaydedilmedi ise uyarı penceresi çıakrtır
        // Veilen cevaba göre:
        //  - Kaydetmeden çıkılabilir
        //  - Kaydedilerek çıkılablir
        //  - Çıkma işlemi iptal edilebilir
        if(!fileHandlerUI.isIsSaved()){
            switch(saveWarning()){
                case JOptionPane.YES_OPTION:
                    if(fileHandlerUI.saveFunction() == JFileChooser.APPROVE_OPTION){
                        dispose();
                    }
                    break;
                     
                case JOptionPane.NO_OPTION:
                    dispose();
                    break;
                     
                case JOptionPane.CANCEL_OPTION:
                    break;
            }
        }
        else{
            dispose();
        }
    }
    
    // Kaydetme uyarı penceresi
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
    
    @Override
    public void dispose(){
        editorCount -= 1;
        if (editorCount <=0){
            super.dispose();
            System.exit(editorCount);
        }
        super.dispose();
    }
    
    // Menu barındaki işlemler
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
                undoInvoker.undo();
                break;
            case "Redo":
                undoInvoker.redo();
                break;
            case "Find":
                windowFactory.createWindow("find");
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
                windowFactory.createWindow("editor");
                break;
            case "Spell check":
                windowFactory.createWindow("spellcheck");
                break;
            default:
                break;
        }
    } 
}
