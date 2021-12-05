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
    private JComponent[] inComponents = {inTitle, startingVertex, endingVertex, start};

    private File file;

    MainGUI(){

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
        // option 3 button
        // the user wanted to print the breadth first search of graph
        option3.setText("Perform Breadth First Traversal");
        option3.setVisible(true);
        option3.setBounds(10, 130, 280, 30);
        // option 4 button
        // the user wanted to print the shortest path from point a to b
        option4.setText("Show Shortest Path");
        option4.setVisible(true);
        option4.setBounds(10, 170, 280, 30);
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


        Border border = BorderFactory.createLineBorder(Color.gray);
        menuPanel.setVisible(true);
        menuPanel.setBorder(border);
        menuPanel.setBounds(0, 0, 300, 300);
        menuPanel.setLayout(null);
        // add components for menu
        for (var menu : menuComponents) menuPanel.add(menu);
    }

    private JPanel getMenuPanel(){
        return menuPanel;
    }

    private void graph(){

        frame.setTitle("Graph Team Pa-Rich");
        frame.setVisible(true);
        frame.setSize(new Dimension(700, 340));
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(getMenuPanel());
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
