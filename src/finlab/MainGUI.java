package finlab;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class MainGUI {

    private JFrame frame = new JFrame();

    // different panels for every section in the gui
    private JPanel menuPanel = new JPanel();
    private JPanel outputPanel = new JPanel();
    private JPanel inputPanel = new JPanel();

    // title for menu panel
    private JLabel menuTitle = new JLabel();
    // option buttons in the menu
    private JFileChooser fileChooser = new JFileChooser();
    private JButton option1 = new JButton();
    private JButton option2 = new JButton();
    private JButton option3 = new JButton();
    private JButton option4 = new JButton();
    private JButton option5 = new JButton();

    // title for output panel
    private JLabel outTitle = new JLabel();
    private JLabel startTitle = new JLabel();         // title of text area for starting vertex
    private JLabel endTitle = new JLabel();           // title of text area for ending vertex (opt 4 only)
    // text area where output will be displayed
    private JTextArea outDisplay = new JTextArea();

    // title for input panel
    private JLabel inTitle = new JLabel();
    // text are to put the input starting vertex for options 2 - 4
    private JTextArea startingVertex = new JTextArea();     // for opt 2 & 3
    private JTextArea endingVertex = new JTextArea();       // for option 4
    // button to start analyze and print the output
    private JButton start = new JButton();

    private JComponent[] menuComponents = {option1, option2, option3, option4, option5, menuTitle};
    private JComponent[] outComponents = {outTitle, outDisplay};
    private JComponent[] inComponents = {inTitle, startTitle, endTitle, startingVertex, endingVertex, start};

    private File file;

    private Border border = BorderFactory.createLineBorder(Color.gray);

    MainGUI(){
        menuPanelMethod();
    }

    private void menuPanelMethod(){

        // menu section
        // title for menu
        menuTitle.setText("MENU");
        menuTitle.setBounds(10, 10, 50, 30);
        // option 1 button
        // the user select CSV file for creating graph
        option1.setText("Select File");
        option1.setVisible(true);
        option1.setBounds(10, 50, 280, 30);
        option1.addActionListener(btn1 -> {
            // filter the input file to only csv file
            javax.swing.filechooser.FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().endsWith(".csv");
                }

                @Override
                public String getDescription() {
                    return "CSV Comma-delimited Value (.csv)";
                }
            };
            fileChooser.setFileFilter(fileFilter);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION){
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println(file);
            }
        });
        // option 2 button
        // the user wanted to print the depth first search of graph
        option2.setText("Perform Depth First Traversal");
        option2.setVisible(true);
        option2.setBounds(10, 90, 280, 30);
        option2.addActionListener(btn2 -> {
                opt23InComponents();
        });

        // option 3 button
        // the user wanted to print the breadth first search of graph
        option3.setText("Perform Breadth First Traversal");
        option3.setVisible(true);
        option3.setBounds(10, 130, 280, 30);
        option3.addActionListener(btn3 -> {
            opt23InComponents();
        });

        // option 4 button
        // the user wanted to print the shortest path from point a to b
        option4.setText("Show Shortest Path");
        option4.setVisible(true);
        option4.setBounds(10, 170, 280, 30);
        option4.addActionListener(btn4 -> {
            opt4InComponents();
        });
        // option 5 button
        // the user wanted to print the depth first search of graph
        option5.setText("Exit");
        option5.setVisible(true);
        option5.setBounds(10, 210, 280, 30);
        option5.addActionListener(btn5 ->{
            // display message for the user
            JOptionPane.showMessageDialog(null,
                    "Thank you for using our application",
                    "Message",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);

        });
        menuPanel.setVisible(true);
        menuPanel.setBorder(border);
        menuPanel.setBounds(0, 0, 300, 300);
        menuPanel.setLayout(null);

        //title for input panel
        inTitle.setText("INPUT");
        inTitle.setBounds(10, 10, 50, 30);

        inputPanel.setVisible(true);
        inputPanel.setBorder(border);
        inputPanel.setBounds(300, 0, 600, 100);
        inputPanel.setLayout(null);

        // add components for input panel
        for(var components : inComponents) inputPanel.add(components);

        // add components for menu panel
        for (var menu : menuComponents) menuPanel.add(menu);
    }

    private void opt23InComponents(){
        // label for starting vertex text area
        startTitle.setText("Starting Vertex:");
        startTitle.setBounds(10, 40, 90, 30);
        // text area to put the starting vertex
        // of depth first and breadth first search
        startingVertex.setBounds(105, 40, 100, 20);
        startingVertex.setVisible(true);
        // button to start analyzing and displaying the results/output
        start.setVisible(true);
        start.setText("Start");
        start.setBounds(220, 40, 90, 30);

        endTitle.setVisible(false);
        endingVertex.setVisible(false);
    }

    private void opt4InComponents(){
        // label for starting vertex text area
        startTitle.setText("Starting Vertex:");
        startTitle.setBounds(10, 40, 90, 30);
        // text area to put the starting vertex
        // of depth first and breadth first search
        startingVertex.setBounds(105, 40, 100, 20);
        startingVertex.setVisible(true);
        // label for ending vertex area
        endTitle.setText("Ending Vertex:");
        endTitle.setBounds(220, 40, 90, 30);
        endTitle.setVisible(true);
        // text area to put the ending vertex
        // for Dijkstra's shortest path
        endingVertex.setBounds(315, 40, 100, 20);
        endingVertex.setVisible(true);
        // button to start analyzing and displaying the results/output
        start.setVisible(true);
        start.setText("Start");
        start.setBounds(425, 40, 90, 30);
    }

    private JPanel getMenuPanel(){
        return menuPanel;
    }
    private JPanel getInputPanel(){return inputPanel;}

    private void graph(){
        frame.setTitle("Graph Team Pa-Rich");
        frame.setVisible(true);
        frame.setBounds(100, 100, 900, 340);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(getMenuPanel());
        frame.getContentPane().add(getInputPanel());
    }

    public static void main(String[] args) {
        MainGUI gui = new MainGUI();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.graph();
            }
        });
    }

}