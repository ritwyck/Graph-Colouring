import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class GUI extends JFrame implements ActionListener { 

    Border border = BorderFactory.createLineBorder(Color.black, 2);
    // JButton button;
    // JButton GameMode1;
    // JButton GameMode2;
    // JButton GameMode3;

    JFrame SelectGameMode;
    ImageIcon logo = new ImageIcon("umlogo1.png");
    String title = "PROJECT PHASE 2 - GAME: GRAPH COLORING";

    JPanel temp = new JPanel();

    GameMode currentGameMode = new GameMode();

    boolean hasOwnGraph = false;

    JProgressBar progressBar = new JProgressBar(0, 100);

    GUI() {

        this.setLayout(null);
        this.setSize(1200, 800);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(logo.getImage());
        this.getContentPane().setBackground(Color.white); // 0x124556

        JPanel panel = new JPanel();
        // panel.setBounds(1400,790,80,35);
        panel.setBounds(1000, 700, 200, 200);
        panel.setBackground(new Color(0x124556));

        // JButton button = new JButton();
        // button.setBounds(0, 0, 120, 100);
        // button.setText("NEXT");
        // button.setFocusable(false);
        // button.addActionListener(e -> secondScreen());


        JButton button = new JButton();
        button.setBounds(70, 100, 200, 120);
        button.setText("NEXT");
        button.setFont(new Font("Cambria", Font.CENTER_BASELINE, 22));
        button.setForeground(new Color(0x124556));
        button.setFocusable(false);
        button.addActionListener(e -> secondScreen());



        panel.add(button);
        this.add(panel);
        this.setResizable(false);
        // this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // this.setUndecorated(true);
        this.setVisible(true);
    }

    /**
     * Displays the selection of the game modes.
     */
    public void secondScreen() {

        this.dispose();
        SelectGameMode = new JFrame();
        SelectGameMode.setLayout(null);
        SelectGameMode.setSize(1200, 800);
        SelectGameMode.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SelectGameMode.setBackground(new Color(0x3EB489));
        SelectGameMode.setIconImage(logo.getImage());
        SelectGameMode.setTitle(title);

        // SelectGameMode.setResizable(false);

        JPanel GameModeSelection = new JPanel();
        GameModeSelection.setBackground(new Color(0xAAF0C1)); // 0xABB2B9
        GameModeSelection.setBounds(0, 0, 1200, 800);
        GameModeSelection.setBorder(border);
        GameModeSelection.setLayout(null);

        JLabel Selection = new JLabel();
        Selection.setText("PLEASE SELECT GAME MODE:");
        Selection.setForeground(Color.WHITE);
        Selection.setFont(new Font("Cambria", Font.BOLD, 36));
        Selection.setBounds(370, 200, 600, 50);
        GameModeSelection.add(Selection);

        JButton back = new JButton();
        back.setBounds(70, 100, 200, 60);
        back.setText("HOME PAGE");
        back.setForeground(new Color(0xAAF0C1));
        back.setFont(new Font("Cambria", Font.CENTER_BASELINE, 22));
        back.setFocusable(false);
        back.addActionListener(e -> MainMenu(SelectGameMode));
        GameModeSelection.add(back);

        JButton GameMode1 = new JButton();

        GameMode1.setBounds(200, 400, 200, 120); // 320 540
        GameMode1.setText("GAME MODE 1");
        GameMode1.setForeground(new Color(0xAAF0C1));
        GameMode1.setFont(new Font("Cambria", Font.CENTER_BASELINE, 22));
        GameMode1.setFocusable(false);
        GameMode1.addActionListener(e -> gamemode1());
        GameModeSelection.add(GameMode1);

        JButton GameMode2 = new JButton();
        GameMode2.setBounds(500, 400, 200, 120); // 320 540
        GameMode2.setText("GAME MODE 2");
        GameMode2.setForeground(new Color(0xAAF0C1));
        GameMode2.setFont(new Font("Cambria", Font.CENTER_BASELINE, 22));
        GameMode2.setFocusable(false);
        GameMode2.addActionListener(e -> gamemode2());
        GameModeSelection.add(GameMode2);

        JButton GameMode3 = new JButton();
        GameMode3.setBounds(800, 400, 200, 120); // 320 540
        GameMode3.setText("GAME MODE 3");
        GameMode3.setForeground(new Color(0xAAF0C1));
        GameMode3.setFont(new Font("Cambria", Font.CENTER_BASELINE, 22));
        GameMode3.setFocusable(false);
        GameMode3.addActionListener(e -> gamemode3());
        GameModeSelection.add(GameMode3);

        JButton ownGraph = new JButton("I have my own graph");
        ownGraph.setBounds(850, 650, 280, 50);
        ownGraph.setForeground(new Color(0xAAF0C1));
        ownGraph.setFont(new Font("Cambria", Font.CENTER_BASELINE, 22));
        ownGraph.setFocusable(false);
        ownGraph.addActionListener(e -> ownGraph(ownGraph));
        GameModeSelection.add(ownGraph);

        JLabel selectGraph = new JLabel();
        selectGraph.setText("Click to select:");
        selectGraph.setForeground(Color.WHITE);
        selectGraph.setFont(new Font("Cambria", Font.BOLD, 20));
        selectGraph.setBounds(850, 600, 200, 50);
        GameModeSelection.add(selectGraph);

        SelectGameMode.add(GameModeSelection);
        // SelectGameMode.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // SelectGameMode.setUndecorated(true);
        SelectGameMode.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
    }

    public void MainMenu(JFrame x) {
        x.dispose();
        SelectGameMode.dispose();
        this.setVisible(true);
    }

    public void GameModeSelection(JFrame x) {
        x.dispose();
        SelectGameMode.setVisible(true);
    }

    // INSTRUCTION FRAMES START ------------------------------

    /**
     * Displays the instructions for game mode 1. Triggers either a file path input
     * or an input of wanted nodes and edges when "next" button is clicked.
     */
    public void gamemode1() {
        this.dispose();
        SelectGameMode.dispose();

        JFrame intermission = new JFrame();
        intermission.setTitle(title);
        intermission.setIconImage(logo.getImage());
        intermission.setLayout(null);
        intermission.setSize(1200, 800);
        intermission.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        intermission.getContentPane().setBackground(new Color(0x124556));

            JLabel text = new JLabel();
                text.setText("<html>Welcome to the Bitter End game mode! <br> <br> In this game mode you will need to find a way of colouring the graph using the <br> least amount of colours, so that no adjacent nodes have the same colour. <br> <br> Click on a node to select it and then click on a colour to colour the node. <br> You only win when you reach our optimal colouring (or better). <br><br> The hint will tell you how many colours we used and give you some advice along the way. <br> <br> Good luck! <br> <br> </html>");
                text.setForeground(Color.white);
                text.setFont(new Font("Comic Sans MS",Font.ITALIC,28));
                text.setBounds(50,0,1100,700);
            

        JPanel temp = new JPanel();
        temp.setBackground(Color.pink);
        temp.setBounds(0, 0, 1200, 800);
        temp.setBorder(border);
        temp.setLayout(null);
        temp.add(text);

        JButton back = new JButton();
        back.setBounds(200, 600, 120, 60);
        back.setText("BACK");
        back.setFocusable(false);
        back.addActionListener(e -> GameModeSelection(intermission));
        temp.add(back);

        JButton continueButton = new JButton();
        continueButton.setBounds(920, 600, 120, 60);
        continueButton.setText("Continue");
        continueButton.setFocusable(false);
        continueButton.addActionListener(e -> start1(intermission));
        temp.add(continueButton);

        intermission.add(temp);
        intermission.setVisible(true);

    }

    /**
     * Displays the instructions for game mode 2. Triggers either a file path input
     * or an input of wanted nodes and edges when "next" button is clicked..
     */
    public void gamemode2() {
        this.dispose();
        SelectGameMode.dispose();

        JFrame frame = new JFrame();
        frame.setTitle("GAME MODE 2");
        frame.setIconImage(logo.getImage());
        frame.setLayout(null);
        frame.setBounds(0, 00, 1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0x124556));

        JLabel text = new JLabel();
        text.setText(
                "<html>Welcome! <br> <br>For this mode of the game, you have to determine the best upper bound in a fixed time frame. <br> <br>You will be given a fixed amount of time based on the number of Nodes and Game level, to find the best coloring using as few colors as possible.<html>");
        text.setForeground(Color.WHITE);
        text.setFont(new Font("Cambria", Font.BOLD, 24));
        text.setBounds(75, 00, 1000, 300);

        JPanel temp = new JPanel();
        temp.setBackground(new Color(0x82A3FF));
        temp.setBounds(0, 0, 1920, 1080);
        temp.setBorder(border);
        temp.setLayout(null);
        temp.add(text);

        JButton back = new JButton();
        back.setBounds(120, 550, 150, 90);
        back.setText("BACK");
        back.setForeground(new Color(0x82A3FF));
        back.setFont(new Font("Cambria", Font.CENTER_BASELINE, 22));
        back.setFocusable(false);
        back.addActionListener(e -> GameModeSelection(frame));
        temp.add(back);

        JLabel label = new JLabel();
        label.setText("Select game level below");
        label.setForeground(Color.RED);
        label.setFont(new Font("Cambria", Font.BOLD, 24));
        label.setBounds(75, 300, 1000, 30);

        JLabel instructions = new JLabel();
        instructions.setText(
                "<html>Easy level will give you more than enough time!<br>Medium level will give you just enough time!<br>Difficult level will keep you on your toes! *NOT FOR THE FAINT HEARTED*</html>");
        instructions.setForeground(Color.WHITE);
        instructions.setFont(new Font("Cambria", Font.CENTER_BASELINE, 24));
        instructions.setBounds(75, 300, 1200, 250);

        JButton easy = new JButton();
        easy.setBounds(380, 550, 150, 90);
        easy.setText("EASY");
        easy.setForeground(new Color(0x82A3FF));
        easy.setFont(new Font("Cambria", Font.CENTER_BASELINE, 22));
        easy.setFocusable(false);
        easy.addActionListener(e -> start2(frame));

        JButton medium = new JButton();
        medium.setBounds(660, 550, 150, 90);
        medium.setText("MEDIUM");
        medium.setForeground(new Color(0x82A3FF));
        medium.setFont(new Font("Cambria", Font.CENTER_BASELINE, 22));
        medium.setFocusable(false);
        medium.addActionListener(e -> start2(frame));

        JButton difficult = new JButton();
        difficult.setBounds(940, 550, 150, 90);
        difficult.setText("DIFFICULT");
        difficult.setForeground(new Color(0x82A3FF));
        difficult.setFont(new Font("Cambria", Font.BOLD, 22));
        difficult.setFocusable(false);
        difficult.addActionListener(e -> start2(frame));

        temp.add(label);
        temp.add(instructions);
        temp.add(easy);
        temp.add(medium);
        temp.add(difficult);

        frame.setVisible(true);

        frame.add(temp);
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // frame.setUndecorated(true);
        frame.setVisible(true);
    }

    // if conditions of graph creation
    // Test testGameMode1 = new Test();

    // frame.add(temp);
    // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    // frame.setUndecorated(true);
    // frame.setVisible(true);

    // implement the node and edges input in this section itself, and continue with
    // a direct graph

    /**
     * Displays the instructions for game mode 3. Triggers either a file path input
     * or an input of wanted nodes and edges when "next" button is clicked..
     */
    public void gamemode3() {
        this.dispose();
        SelectGameMode.dispose();
        JFrame frame = new JFrame();
        frame.setTitle("GAME MODE 3");
        frame.setIconImage(logo.getImage());
        frame.setLayout(null);
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0x124556));

        JLabel text = new JLabel();
        text.setText(
                "<html>Welcome to the Random Order game mode!<br><br> <br>In this gamemode the game selects a random vertex<br><br>Your mission is to colour this selected vertex <br><br>without having two vertices of the same colour directly connected! <br><br>Try to use as few colours as possible <br><br></html>");
        text.setFont(new Font("Cambria", Font.BOLD, 30));
        text.setBounds(75, 0, 1000, 600);

        JPanel temp = new JPanel();
        temp.setBackground(new Color(0x6EA67F));
        temp.setBounds(0, 0, 1920, 980);
        temp.setBorder(border);
        temp.setLayout(null);
        temp.add(text);

        JButton back = new JButton();
        back.setBounds(200, 600, 120, 60);
        back.setText("BACK");
        back.setFocusable(false);
        back.addActionListener(e -> GameModeSelection(frame));
        temp.add(back);

        JButton continueButton = new JButton();
        continueButton.setBounds(920, 600, 120, 60);
        continueButton.setText("CONTINUE");
        continueButton.setFocusable(false);
        continueButton.addActionListener(e -> start3(frame));
        temp.add(continueButton);

        frame.add(temp);
        frame.setVisible(true);
    }

    // INSTRUCTION FRAMES END ------------------------------

    // GAME FRAMES START -----------------------------------

    /**
     * Builds Game Mode 1 using all the needed components from other classes.
     * 
     * @param x previous frame
     */
    public void start1(JFrame x) {
        int nodes = 0;

        int edges = 0;

        NodeManager manager = new NodeManager();

        ArrayList<Node> nodesList = new ArrayList<>();

        if (hasOwnGraph == false) // Uses user input and generates a random graph
        {
            String nodesString = JOptionPane.showInputDialog("ENTER NUMBER OF NODES: \n Max. 20 for a complete graph");

            nodes = Integer.parseInt(nodesString.trim());

            if ((nodes == 0) || (nodesString.equals(null))) {
                JOptionPane.showMessageDialog(temp, "NODES CANNOT BE 0.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                String edgesString = JOptionPane.showInputDialog("ENTER NUMBER OF EDGES:");

                edges = Integer.parseInt(edgesString.trim());

                if ((edges == 0) || (edgesString.equals(null))) {
                    JOptionPane.showMessageDialog(temp, "Number can't be 0.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    this.dispose();
                    x.dispose();
                    SelectGameMode.dispose();
                }
            }

            RandomGraph graph = new RandomGraph(nodes, edges);

            nodesList = manager.makeNodesList(graph.getEdgeList(), graph.getNumberOfNodes(), graph.getNumberOfEdges());

        } 
        else if(hasOwnGraph == true)// Uses user's graph input file
        {
            String filePath = JOptionPane.showInputDialog("ENTER WANTED FILE PATH:");

            ReadInGraph graphReader = new ReadInGraph();
            graphReader.runner(filePath);

            nodesList = manager.generateNodesGivenGraph(graphReader.getColEdges(), graphReader.getNumberOfNodes(),
            graphReader.getNumberOfEdges());

        }
                JFrame frame = new JFrame("Game Mode 1");
                frame.setTitle(title);
                frame.setSize(1200, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setIconImage(logo.getImage());
    
                int bestColouring = 0;

                if(nodesList.size() < 10)
                {
                    bestColouring = manager.bruteForce(nodesList);
                }
                else
                {
                    bestColouring = manager.WelshPowell(nodesList);
                }
        
            BitterEnd bitterEnd = new BitterEnd(nodesList, bestColouring);
    
            GraphPanel aGraph = new GraphPanel(nodesList, 1100, 700, bitterEnd);
            frame.getContentPane().add(aGraph);       
            aGraph.setLocation(100, 0);
    
            frame.setVisible(true);
             
    }

    public void start2(JFrame x) {
        String nodesString = JOptionPane.showInputDialog("ENTER NUMBER OF NODES:");

        int nodes = Integer.parseInt(nodesString.trim());
        System.out.println(nodes);

        if ((nodes == 0) || (nodesString.equals(null))) {
            JOptionPane.showMessageDialog(temp, "NODES CANNOT BE 0.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            String edgesString = JOptionPane.showInputDialog("ENTER NUMBER OF EDGES:");

            int edges = Integer.parseInt(edgesString.trim());
            System.out.println(edges);

            if ((edges == 0) || (edgesString.equals(null))) {
                JOptionPane.showMessageDialog(temp, "Number can't be 0.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                this.dispose();
                x.dispose();
                SelectGameMode.dispose();

            }

            JFrame frame = new JFrame("Game Mode 2");
            frame.setTitle(title);
            frame.setSize(1200, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setIconImage(logo.getImage());

            RandomGraph graph = new RandomGraph(nodes, edges);

            NodeManager newManager = new NodeManager();
            ArrayList<Node> nodesList = newManager.makeNodesList(graph.getEdgeList(), graph.getNumberOfNodes(),
                    graph.getNumberOfEdges());
                    
            progressBar = new JProgressBar(0, 100);
            progressBar.setValue(100);
            progressBar.setBounds(150, 20, 1000, 40);
            progressBar.setString("TIME REMAINING:");
            progressBar.setStringPainted(true);
            progressBar.setFont(new Font("MV BOLI", Font.BOLD, 20));
            progressBar.setForeground(Color.black);
            progressBar.setBackground(Color.red);
            frame.add(progressBar);

            // show timer working and add conditions for the different levels
            /*for(int limit = 1000 * nodes; limit >= 0; limit--)
            {
                int milliseconds = limit;
                int minutes = milliseconds / 6000;
                int seconds = (milliseconds % 60000) / 1000;
                int millis = (milliseconds % 60000) % 1000;
    
                progressBar.setValue(limit);
                progressBar.setString(String.format("%d:%02d.%03d", minutes, seconds, millis));           
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/

            int upperBound = newManager.WelshPowell(nodesList);
            System.out.println("THE UPPER BOUND IS " + upperBound);

            GameMode2 G2 = new GameMode2(nodesList, upperBound);

            GraphPanel aGraph = new GraphPanel(nodesList, 1100, 700, G2);
            frame.getContentPane().add(aGraph);
            aGraph.setLocation(100, 0);

            frame.setVisible(true);
            fill(nodes, 3);}
        // if conditions of graph creation
    }
    public void fill(int nodes, int timeForNode) {
        int playTime = nodes * timeForNode;

        for(int limit = 1000 * nodes; limit >= 0; limit--)
            {
                int milliseconds = limit;
                int minutes = milliseconds / 6000;
                int seconds = (milliseconds % 60000) / 1000;
                int millis = (milliseconds % 60000) % 1000;
    
                progressBar.setValue(limit);
                progressBar.setString(String.format("%d:%02d.%03d", minutes, seconds, millis));           
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        /*while (playTime > 0) {

            progressBar.setValue(playTime);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playTime -= 1;
        } */
        progressBar.setString("Time is up");
    }

    public void start3(JFrame x) {

        String nodesString = JOptionPane.showInputDialog("ENTER NUMBER OF NODES:");

        int nodes = Integer.parseInt(nodesString.trim());
        System.out.println(nodes);

        if ((nodes == 0) || (nodesString.equals(null))) {
            JOptionPane.showMessageDialog(temp, "NODES CANNOT BE 0.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            String edgesString = JOptionPane.showInputDialog("ENTER NUMBER OF EDGES:");

            int edges = Integer.parseInt(edgesString.trim());
            System.out.println(edges);
            if ((edges == 0) || (edgesString.equals(null))) {
                JOptionPane.showMessageDialog(temp, "EDGES CANNOT BE 0.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                this.dispose();
                x.dispose();
                SelectGameMode.dispose();
                // GraphCreator PlayG = new GraphCreator(nodes,edges); //later will be
                // Test(nodes,edges);

                JFrame frame = new JFrame();
                frame.setTitle(title);
                frame.setSize(1200, 800);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setIconImage(logo.getImage());

                int width = 1100;
                int height = 700;

                RandomGraph graph = new RandomGraph(nodes, edges);

                NodeManager newManager = new NodeManager();
                ArrayList<Node> nodesList = newManager.makeNodesList(graph.getEdgeList(), graph.getNumberOfNodes(),
                        graph.getNumberOfEdges());

                // GraphPanel aGraph = new GraphPanel(nodesList, width, height, new GameMode());
                G3 aGraph = new G3(nodesList, width, height);

                frame.getContentPane().add(aGraph);
                aGraph.setLocation(100, 0);

                frame.setVisible(true);

            }
            // if conditions of graph creation
        }
    }

    public static void main(String[] args) {

        JLabel group = new JLabel();
        group.setText("<html>GROUP 29 <br> PROJECT PHASE 2  </html>");
        group.setForeground(Color.BLACK);
        group.setFont(new Font("Cambria", Font.BOLD, 30));
        group.setBounds(500, 00, 300, 100);
        Border border = BorderFactory.createLineBorder(Color.black, 2);
        // group.setBorder(border);

        JLabel UMBig = new JLabel();
        ImageIcon umlogo = new ImageIcon(
                new ImageIcon("umlogo1.png").getImage().getScaledInstance(270, 90, Image.SCALE_SMOOTH));
        UMBig.setIcon(umlogo);
        UMBig.setBounds(0, 0, 300, 100);
        UMBig.setBorder(border);

        JPanel GroupPanel = new JPanel();
        GroupPanel.setBackground(Color.white); // 0xABB2B9
        GroupPanel.setBounds(0, 0, 1200, 100);
        GroupPanel.setBorder(border);
        GroupPanel.setLayout(null);
        GroupPanel.add(group);

        JLabel graph = new JLabel();
        graph.setText("<html>WELCOME TO OUR GAME BASED ON GRAPH COLOURING! <br> CLICK ON NEXT TO CONTINUE </html>");
        graph.setForeground(Color.white);
        graph.setFont(new Font("Cambria", Font.BOLD, 26));
        graph.setBounds(320, 0, 1200, 100);

        JPanel temp = new JPanel();
        temp.setBackground(new Color(0x124556)); // 0x124556)
        temp.setBounds(00, 100, 1200, 672);
        temp.setLayout(null);
        temp.setBorder(border);
        temp.add(graph);

        GUI Program = new GUI();

        Program.add(UMBig);
        Program.add(GroupPanel);
        Program.add(temp);
    }

    public void ownGraph(JButton button) {
        hasOwnGraph = true;

        button.setBorder(new LineBorder(Color.CYAN, 3));
    }
}
