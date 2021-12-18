package finlab;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.List;

public class MainGUI {

    private JFrame frame = new JFrame();

    // different panels for every section in the gui
    private JPanel menuPanel = new JPanel();
    private JPanel outputPanel = new JPanel();
    private JPanel inputPanel = new JPanel();

    // title for menu panel
    private JLabel menuTitle = new JLabel();
    // option buttons in the menu
    private JFileChooser fileChooser = new JFileChooser("res");
    private JButton option1 = new JButton();
    private JButton option2 = new JButton();
    private JButton option3 = new JButton();
    private JButton option4 = new JButton();
    private JButton option5 = new JButton();

    // title for output panel
    private JLabel outTitle = new JLabel();
    private JLabel content = new JLabel();
    // text area where output will be displayed
    private JTextArea outDisplay = new JTextArea();
    private JTextArea contentDisplay = new JTextArea();

    // title for input panel
    private JLabel inTitle = new JLabel();
    private JLabel startTitle = new JLabel();         // title of text area for starting vertex
    private JLabel endTitle = new JLabel();           // title of text area for ending vertex (opt 4 only)
    private JLabel typeTitle = new JLabel();
    // text area to put the input starting vertex for options 2 - 4
    private JTextArea startingVertex = new JTextArea();     // for opt 2 & 3
    private JTextArea endingVertex = new JTextArea();       // for option 4
    private JTextArea graphType = new JTextArea();          // display the type of graph of the loaded file
    // button to start analyze and print the result and output
    private JButton result1 = new JButton(); // result button for depth first search
    private JButton result2 = new JButton(); // result button for breadth first search
    private JButton result3 = new JButton(); // result button for shortest path

    private JComponent[] menuComponents = {option1, option2, option3, option4, option5, menuTitle};
    private JComponent[] outComponents = {outTitle, outDisplay, content, contentDisplay};
    private JComponent[] inComponents = {inTitle, startTitle, endTitle, typeTitle, startingVertex, endingVertex, graphType, result1, result2, result3};

    private File file;

    private Border border = BorderFactory.createLineBorder(Color.gray);

    MainGUI(){
        menuPanelMethod();
    }

    private void menuPanelMethod(){
        Graph graph = new Graph();

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
            try {

                BufferedReader bfr;
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
                }
                bfr = new BufferedReader(new FileReader(file));
                String line  = bfr.readLine();
                if (line.contains("UNWEIGHTED")){
                    graph.readUnWghtGraphCSV(bfr);
                    graph.setGraphType(line.split(",")[0]);
                    contentDisplay.setText(graph.contentUnWght());
                } else if (line.contains("WEIGHTED")){
                    graph.readWghtGraphCSV(bfr);
                    graph.setGraphType(line.split(",")[0]);
                    contentDisplay.setText(graph.contentWght());
                }
                typeTitle.setText("Type of Graph:");
                typeTitle.setBounds(110, 10, 90, 30);
                graphType.setBounds(210, 10, 200, 20);
                graphType.setEditable(false);
                graphType.setText(graph.getGraphType());
            } catch (Exception e){
                e.printStackTrace();
            }

        });
        // option 2 button
        // the user wanted to print the depth first search of graph
        option2.setText("Perform Depth First Traversal");
        option2.setVisible(true);
        option2.setBounds(10, 90, 280, 30);
        option2.addActionListener( btn2 -> {
            checkFile();
            // set the position of input components
            opt23InComponents(result1);
            outDisplay.setText("");
            result1.addActionListener(res -> {
                try {
                    inputLimit(startingVertex.getText().length());
                    Character start = startingVertex.getText().charAt(0);
                    String result = graph.depthFS(start);
                    outDisplay.setText(result);
                } catch (InputMismatchException e){
                    // display message for the user
                    JOptionPane.showMessageDialog(null,
                            "Wrong input (vertex)",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                } catch (IndexOutOfBoundsException e){
                    // display message for the user
                    JOptionPane.showMessageDialog(null,
                            "Input should only be one character",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            });
        });


        // option 3 button
        // the user wanted to print the breadth first search of graph
        option3.setText("Perform Breadth First Traversal");
        option3.setVisible(true);
        option3.setBounds(10, 130, 280, 30);
        option3.addActionListener(btn3 ->{
            checkFile();
            // set the position of input components
            opt23InComponents(result2);
            outDisplay.setText("");
            result2.addActionListener(res -> {
                try {
                    inputLimit(startingVertex.getText().length());
                    Character start = startingVertex.getText().charAt(0);
                    String result = graph.breadthFirstSearch(start);
                    outDisplay.setText(result);
                } catch (InputMismatchException e){
                    // display message for the user
                    JOptionPane.showMessageDialog(null,
                            "Wrong input (vertex)",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                } catch (QueueNullException e1){
                    // display message for the user
                    JOptionPane.showMessageDialog(null,
                            "Queue is null",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                } catch (IndexOutOfBoundsException e){
                    // display message for the user
                    JOptionPane.showMessageDialog(null,
                            "Input should only be one character",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            });
        });

        // option 4 button
        // the user wanted to print the shortest path from point a to b
        option4.setText("Show Shortest Path");
        option4.setVisible(true);
        option4.setBounds(10, 170, 280, 30);
        option4.addActionListener(btn4 -> {
            checkFile();
            // set the position of input components
            opt4InComponents();
            result3.addActionListener(res ->{
                try {
                    inputLimit(startingVertex.getText().length());
                    inputLimit(endingVertex.getText().length());
                    Character start = startingVertex.getText().charAt(0);
                    Character end = endingVertex.getText().charAt(0);
                    List<String> result = graph.shortestPath(start, end);
                    String path = result.get(0);
                    int cost = Integer.parseInt(result.get(1));
                    outDisplay.setText(String.format("The path from %c to %c is %s\n The cost of traversal is %d", start, end, path, cost));
                } catch (InputMismatchException e){ // one or both the input is not a vertex
                    // display message for the user
                    JOptionPane.showMessageDialog(null,
                            "The input is not a vertex",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                } catch (NullPointerException e){ // if there is no possible path for the given input
                    // display message for the user
                    JOptionPane.showMessageDialog(null,
                            "There is no path from " + startingVertex.getText().charAt(0) + " to " + endingVertex.getText().charAt(0),
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                } catch (IndexOutOfBoundsException e){
                    // display message for the user
                    JOptionPane.showMessageDialog(null,
                            "Input should only be one character",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }

            });
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

        // title for output panel
        outTitle.setText("OUTPUT");
        outTitle.setBounds(10, 10, 50, 30);

        outDisplay.setBounds(10, 50, 250, 200);
        outDisplay.setEditable(false);

        // title for content
        content.setText("CONTENT");
        content.setBounds(270, 10, 100, 30);
        contentDisplay.setBounds(270, 50, 300, 200);
        contentDisplay.setEditable(false);

        outputPanel.setVisible(true);
        outputPanel.setBorder(border);
        outputPanel.setBounds(300, 100, 600, 240);
        outputPanel.setLayout(null);

        // add components for input panel
        for(var components : inComponents) inputPanel.add(components);

        // add components for menu panel
        for (var menu : menuComponents) menuPanel.add(menu);

        // add components for output panel
        for (var components : outComponents) outputPanel.add(components);
    }

    private void opt23InComponents(JButton result){
        // label for starting vertex text area
        startTitle.setText("Starting Vertex:");
        startTitle.setBounds(10, 40, 90, 30);
        // text area to put the starting vertex
        // of depth first and breadth first search
        startingVertex.setBounds(105, 40, 100, 20);
        startingVertex.setVisible(true);
        startingVertex.setText("");
        // button to start analyzing and displaying the results/output
        result.setVisible(true);
        result.setText("Result");
        result.setBounds(220, 40, 90, 30);
        result3.setVisible(false);

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
        result3.setVisible(true);
        result3.setText("Result");
        result3.setBounds(425, 40, 90, 30);
        result1.setVisible(false);
        result2.setVisible(false);
    }

    private void checkFile(){
        if (file == null){
            // display message for the user
            JOptionPane.showMessageDialog(null,
                    "There is no file loaded",
                    "No File",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void inputLimit(int i){
        if (i > 1) throw new IndexOutOfBoundsException();
    }

    private JPanel getMenuPanel(){
        return menuPanel;
    }
    private JPanel getInputPanel(){return inputPanel;}
    private JPanel getOutputPanel(){
        return outputPanel;
    }

    private void graph(){
        frame.setTitle("Graph Team Pa-Rich");
        frame.setVisible(true);
        frame.setBounds(100, 100, 900, 340);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(getMenuPanel());
        frame.getContentPane().add(getInputPanel());
        frame.getContentPane().add(getOutputPanel());
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
