package Code;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
//import javax.swing.filechooser.*;

public class Notepad {
    private final Color pastelBlue = new Color(209, 237, 255, 255);
    private final Color pastelYellow = new Color(255, 233, 176, 255);
    private final Color pastelGreen = new Color(185, 217, 176, 255);
    private final Color pastelPink = new Color(255, 224, 249, 255);
    private final Color pastelRed = new Color(255, 186, 186, 255);
    private final Color offWhite = new Color(255, 250, 241,255);

    public Notepad(){
        /*
        JFrame and its components declaration and initialization
        The components include a JMenuBar along with its JMenus and JMenuItems
        A JTextArea to allow for text editing
         */
        JFrame frame = new JFrame("Untitled - Note");
        JScrollPane scroll = new JScrollPane();
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu settingsMenu = new JMenu("Settings");
        JMenu changeColor = new JMenu("Change Color");
        JMenuItem saveFile = new JMenuItem("Save File");
        JMenuItem openFile = new JMenuItem("Open File");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste Item");
        JMenuItem selectAll = new JMenuItem("Select All");
        JMenuItem clear = new JMenuItem("Clear");
        Dimension initialSize = new Dimension(500, 400);
        JTextArea textArea = new JTextArea();

        //JFrame monitor location of appearing
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        Point middle = new Point( (width/2), (height/2));
        Point spawnLocation = new Point(middle.x - (int)initialSize.getWidth() , middle.y - (int)initialSize.getHeight());
        frame.setLocation(spawnLocation);

        //Menu bar drop-downs and contents


    }

}
