 package Code;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.filechooser.*;

public class Notepad implements ActionListener, ComponentListener{
    private JFrame frame;  //Container for everything
    private JScrollPane scroll; //Wraps textPane text
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu settingsMenu, changeColorMenu, changeCursor;
    private JMenuItem saveFile, openFile, exit;  //File items
    private JMenuItem cutItem, copyItem, pasteItem, selectAll, clear, undo, redo;  //Edit options
    private JMenuItem blue, yellow, green, pink, red, white, black, gray;  //Settings Menu Colors
    private JTextArea textArea;
    //Undo Manager
    private UndoManager manager;

    private final Color PASTEL_BLUE = new Color(209, 237, 255, 255);
    private final Color PASTEL_YELLOW = new Color(255, 254, 176, 255);
    private final Color PASTEL_GREEN = new Color(185, 217, 176, 255);
    private final Color PASTEL_PURPLE = new Color(255, 224, 249, 255);
    private final Color PASTEL_RED = new Color(255, 186, 186, 255);

    /*
       JFrame and its components declaration and initialization
       The components include a JMenuBar along with its JMenus and JMenuItems
       A JTextArea to allow for text editing
        */
    public Notepad(){
        //JFrame and its dimension
        frame = new JFrame("Untitled - Note");
        Dimension initialSize = new Dimension(500, 400);
        frame.setSize(initialSize);
        frame.setLayout(new BorderLayout());
        //JMenu and its items
        menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 500, 20);
        menuBar.setLocation(0, 0);
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        settingsMenu = new JMenu("Settings");
        changeColorMenu = new JMenu("Change Color");
        changeCursor = new JMenu("Change Cursor");
        saveFile = new JMenuItem("Save File");
        openFile = new JMenuItem("Open File...");
        exit = new JMenuItem("Exit");
        cutItem = new JMenuItem("Cut");
        copyItem = new JMenuItem("Copy");
        pasteItem = new JMenuItem("Paste");
        selectAll = new JMenuItem("Select All");
        clear = new JMenuItem("Clear");
        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        blue = new JMenuItem("Blue");
        yellow = new JMenuItem("Yellow");
        green = new JMenuItem("Green");
        pink = new JMenuItem("Purple");
        red = new JMenuItem("Red");
        white = new JMenuItem("White");
        black = new JMenuItem("Black");
        gray = new JMenuItem("Gray");

        //Undo Manager
        manager = new UndoManager();

        //JTextArea and JScrollPane
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setSize(initialSize);
        textArea.getDocument().addUndoableEditListener(manager);
        scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        //JFrame monitor location of appearing
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        Point middle = new Point( (width/2), (height/2));
        Point spawnLocation = new Point(middle.x - (int)initialSize.getWidth() , middle.y - (int)initialSize.getHeight());
        frame.setLocation(spawnLocation);

    }

    /*
    Adds everything to the JFrame
     */
    public void run(){

        //File menu items and action listeners
        fileMenu.add(saveFile);
        fileMenu.add(openFile);
        fileMenu.add(exit);
        saveFile.addActionListener(this);
        openFile.addActionListener(this);
        exit.addActionListener(this);
        //Edit menu items and action listeners
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(selectAll);
        editMenu.add(clear);
        editMenu.add(undo);
        editMenu.add(redo);
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK)); //Shortcut for undo
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));//Shortcut for redo
        cutItem.addActionListener(this);
        copyItem.addActionListener(this);
        pasteItem.addActionListener(this);
        selectAll.addActionListener(this);
        clear.addActionListener(this);
        undo.addActionListener(this);
        redo.addActionListener(this);
        //Settings menu items and action listeners
        settingsMenu.add(changeColorMenu); //Adds submenu  - Change Color to settings menu
        settingsMenu.add(changeCursor); //Adds submenu - Change Cursor to settings menu
        changeColorMenu.add(blue);
        changeColorMenu.add(yellow);
        changeColorMenu.add(green);
        changeColorMenu.add(pink);
        changeColorMenu.add(red);
        changeColorMenu.add(white);
        changeColorMenu.add(black);
        changeColorMenu.add(gray);
        blue.addActionListener(this);
        yellow.addActionListener(this);
        green.addActionListener(this);
        pink.addActionListener(this);
        red.addActionListener(this);
        white.addActionListener(this);
        black.addActionListener(this);
        gray.addActionListener(this);
        //Adds all the menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(settingsMenu);
        menuBar.setBackground(Color.WHITE);

        //Adding menu and JScrollPane to the frame
        frame.add(menuBar, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);
        //JFrame visibility and closing operation set
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * Invoked when an action occurs.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //File menu options
        if(e.getSource() == openFile){
            JFileChooser opener = new JFileChooser();
            //Prevents all file types to be accepted
            opener.setAcceptAllFileFilterUsed(false);
            //Filter to only accept .txt files
            FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Only .txt files", "txt");
            opener.addChoosableFileFilter(txtFilter);

            int result = opener.showOpenDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                File openFile = opener.getSelectedFile();
                try{
                    //Reading the file
                    FileReader fr = new FileReader(openFile);
                    BufferedReader br = new BufferedReader(fr);
                    textArea.read(br, null);
                    //Closing the file after reading is done and clearing memory
                    br.close();
                    textArea.requestFocus();
                }catch(Exception ex){
                    System.out.print(ex);
                }
            }
        }
        else if(e.getSource() == saveFile){
            JFileChooser saveAs = new JFileChooser();
            //Sets up the file chooser
            saveAs.setApproveButtonText("Save");
            int save = saveAs.showOpenDialog(frame);
            if(save != JFileChooser.APPROVE_OPTION){
                return;
            }
            File saveFile = new File(saveAs.getSelectedFile() + ".txt");
            BufferedWriter output = null;
            try{
                output = new BufferedWriter(new FileWriter(saveFile));
                textArea.write(output);
            }catch(IOException except){
                except.printStackTrace();
            }
        }
        if(e.getSource() == exit){
            manager.discardAllEdits();
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }

        //Edit menu options
        if(e.getSource() == cutItem){
            textArea.cut();
        }
        else if(e.getSource() == copyItem){
            textArea.copy();
        }
        else if(e.getSource() == pasteItem){
            textArea.paste();
        }
        else if(e.getSource() == selectAll){
            textArea.selectAll();
        }
        else if(e.getSource() == clear){
            textArea.setText("");
        }
        else if(e.getSource() == undo){
            try {
                if (manager.canUndo()) {
                    manager.undo();
                }
            }catch(CannotUndoException exception){
            }

        }
        else if(e.getSource() == redo){
            try{
                if(manager.canRedo()){
                    manager.redo();
                }
            }catch(CannotRedoException exception){

            }
        }

        //Settings menu options
        if(e.getSource() == blue){
            textArea.setBackground(PASTEL_BLUE);
            textArea.setCaretColor(Color.BLACK);
        }
        else if(e.getSource() == yellow){
            textArea.setBackground(PASTEL_YELLOW);
            textArea.setCaretColor(Color.BLACK);
        }
        else if(e.getSource() == green){
            textArea.setBackground(PASTEL_GREEN);
            textArea.setCaretColor(Color.BLACK);
        }
        else if(e.getSource() == pink){
            textArea.setBackground(PASTEL_PURPLE);
            textArea.setCaretColor(Color.BLACK);
        }
        else if(e.getSource() == red){
            textArea.setBackground(PASTEL_RED);
            textArea.setCaretColor(Color.BLACK);
        }
        else if(e.getSource() == white){
            textArea.setBackground(Color.WHITE);
            textArea.setCaretColor(Color.BLACK);
        }
        else if(e.getSource() == black){
            textArea.setBackground(Color.BLACK);
            textArea.setCaretColor(Color.WHITE);
        }
        else if(e.getSource() == gray){
            textArea.setBackground(Color.lightGray);
            textArea.setCaretColor(Color.RED);
        }
    }

    /**
     * Invoked when the component's size changes.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentResized(ComponentEvent e) {

    }

    /**
     * Invoked when the component's position changes.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentMoved(ComponentEvent e) {

    }

    /**
     * Invoked when the component has been made visible.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentShown(ComponentEvent e) {

    }

    /**
     * Invoked when the component has been made invisible.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
