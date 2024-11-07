import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

/**
 * GraphPanel is where the nodes are displayed using objects of the class Node.
 * Nodes are places randomly given certain criteria and coloured
 * using the ColourPanel, which is added to this component. GraphPanel also
 * calls the GameMode's respective methods.
 * 
 * @param newNodes    passed nodes list from NodeManager
 * @param panelWidth
 * @param panelHeight
 * @param givenGame   either GameMode 1 or 2
 */
public class GraphPanel extends JComponent {
    private ArrayList<Node> nodes = new ArrayList<>();

    private final int WIDTH = 26;
    private final int HEIGHT = 26;

    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;

    private int node_distance = 0;
    private final int PANEL_DISTANCE = 20;

    private final Color COLOUR_SELECTED_NODE = Color.ORANGE;
    private final Color COLOUR_ADJACENT_SELECTED = Color.CYAN;

    private static boolean aNodeIsSelected = false;
    private int hintCounter = 0;

    private GameMode currentGameMode = new GameMode();

    ColourPanel colourPanel;

    private JLabel colourCounter = new JLabel();

    public GameMode2 gameMode2;

    public GraphPanel(ArrayList<Node> newNodes, int panelWidth, int panelHeight, GameMode givenGame) {
        this.currentGameMode = givenGame;
        if(currentGameMode.getClass().getName().equals("GameMode2")) {
          this.gameMode2 = (GameMode2) givenGame;
          }

        for (Node node : newNodes) {
            if (node.degree != 0) {
                nodes.add(node);

            }
        }

        PANEL_WIDTH = panelWidth;
        PANEL_HEIGHT = panelHeight;

        this.setLayout(null);

        JPanel aPanel = new JPanel();
        aPanel.setSize(80, 375);
        aPanel.setBackground(Color.LIGHT_GRAY);

        colourPanel = new ColourPanel(nodes, this, currentGameMode);
        colourPanel.setPreferredSize(new Dimension(80, 350));
        aPanel.add(colourPanel);

        aPanel.setLocation(0, PANEL_HEIGHT - 400);

        this.add(aPanel);

        if (newNodes.size() != nodes.size()) // removes nodes with degree 0 so that no nodes are standing alone
        {
            JOptionPane.showMessageDialog(null, "Zero-degree-nodes have been removed", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            System.out.println("Zero-degree-nodes have been removed");
        }

        if (nodes.size() < 10) {
            node_distance = 40;
        } else if (nodes.size() < 15) {
            node_distance = 30;
        } else {
            node_distance = 20;
        }

        setNodeCoordinates();

        class MousePressListener implements MouseListener {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();


                for (Node node : nodes) {
                    node.setOutlineColour(Color.BLACK);
                    node.isSelected(false);
                }

                for (Node node : nodes) {
                    if (x >= node.getXCoor() && x <= (node.getXCoor() + WIDTH) && y >= node.getYCoor()
                            && y <= (node.getYCoor() + HEIGHT)) {
                        node.isSelected(true);

                        node.setOutlineColour(COLOUR_SELECTED_NODE);

                        for (Node adjacentNode : node.adjacentTo) {
                            adjacentNode.setOutlineColour(COLOUR_ADJACENT_SELECTED);
                        }

                        nodes.remove(node);
                        nodes.add(node);

                        break;
                    }
                }

                repaint();

            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        }

        MousePressListener listener = new MousePressListener();
        addMouseListener(listener);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(2, 170, 80,400);

        /*
         * if(currentGameMode.getClass().getName().equals("GameMode2"))
         * {
         * progressBar(nodes, buttonPanel); // input should be the number of nodes
         * }
         */

        JButton hint = new JButton("Hint"); // Hint button
        hint.setBackground(Color.LIGHT_GRAY);
        hint.setBounds(0, 50, 75, 30);
        hint.setBorder(new LineBorder(Color.BLACK));
        hint.addActionListener(e -> getHint());
        hint.setPreferredSize(new Dimension(75, 30));
        hint.setEnabled(true);
        buttonPanel.add(hint);

        JButton restartButton = new JButton("Restart"); // restarts the graph if user wishes to start over
        restartButton.setBackground(Color.LIGHT_GRAY);
        restartButton.setBounds(0, 80, 75, 30);
        restartButton.setBorder(new LineBorder(Color.BLACK));
        restartButton.addActionListener(e -> restart());
        restartButton.setPreferredSize(new Dimension(75, 30));
        restartButton.setEnabled(true);

        buttonPanel.add(restartButton);
        // this.add(buttonPanel);

        colourCounter.setBounds(150, 5, 100, 30);
        colourCounter.setBackground(Color.WHITE);
        colourCounter.setOpaque(false);
        this.add(colourCounter);
        
        JButton checkWin = new JButton("Finished?"); //restarts the graph if user wishes to start over
        checkWin.setBackground(Color.LIGHT_GRAY);
        checkWin.setBounds(0, 80,75, 30);
        checkWin.setBorder(new LineBorder(Color.BLACK));
        checkWin.addActionListener(e -> currentGameMode.win(colourPanel.getNumberOfColours()));
        checkWin.setPreferredSize(new Dimension(75, 30));
        checkWin.setEnabled(true);


        
        buttonPanel.add(checkWin);
        this.add(buttonPanel);


    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(3));

        for (Node node : nodes) {

            for (Node adjacentNode : node.adjacentTo) {
                g2.setColor(Color.BLACK);

                if (node.getOutlineColour().equals(COLOUR_SELECTED_NODE)
                        && adjacentNode.getOutlineColour().equals(COLOUR_ADJACENT_SELECTED)) {
                    g2.setColor(COLOUR_ADJACENT_SELECTED);
                }

                if (node.getNodeColor().equals(adjacentNode.getNodeColor()) && !node.getNodeColor().equals(Color.WHITE)
                        && !adjacentNode.getNodeColor().equals(Color.WHITE)) {
                    g2.setColor(Color.RED);

                    node.setHasAnError(true);
                } else {
                    node.setHasAnError(false);
                }

                g2.drawLine(node.xcoordinate + (WIDTH / 2), node.ycoordinate + (HEIGHT / 2),
                        adjacentNode.xcoordinate + (WIDTH / 2), adjacentNode.ycoordinate + (HEIGHT / 2));
            }
        }

        for (Node node : nodes) {
            g2.setColor(node.getOutlineColour());

            for (Node adjacentNode : node.adjacentTo) {
                if (node.getNodeColor().equals(adjacentNode.getNodeColor()) && !node.getNodeColor().equals(Color.WHITE)
                        && !adjacentNode.getNodeColor().equals(Color.WHITE)) {
                    g2.setColor(Color.RED);
                }
            }

            g2.setStroke(new BasicStroke(5));
            g2.drawOval(node.xcoordinate, node.ycoordinate, WIDTH, HEIGHT);

            g2.setColor(node.getNodeColor());
            g2.fillOval(node.xcoordinate, node.ycoordinate, WIDTH, HEIGHT);

            if (node.getHintNode() == true) {
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(7));

                g2.drawOval(node.xcoordinate - 6, node.ycoordinate - 6, WIDTH + 12, WIDTH + 12);

                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(3));

                node.setHintNode(false);

            }
        }

    }

    public int colorsUsed () {
        return colourPanel.getNumberOfColours();
    }

/* public void progressBar(ArrayList<Node> nodes, JPanel buttonPanel) 
    {    
        /*JProgressBar progressBar;
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(100);
        progressBar.setBounds(150, 30, 100, 40);
        progressBar.setString("TIME  _ REMAINING:");
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("MV BOLI", Font.BOLD, 20));
        progressBar.setForeground(Color.black);
        progressBar.setBackground(Color.red);
        buttonPanel.add(progressBar);
            

        for(int limit = 1000 * nodes.size(); limit >= 0; limit--)
        {
            int milliseconds = limit;
            int minutes = milliseconds / 6000;
            int seconds = (milliseconds % 60000) / 1000;
            int millis = (milliseconds % 60000) % 1000;

           // progressBar.setValue(limit);
            // progressBar.setString(String.format("%d:%02d.%03d", minutes, seconds, millis));           
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(buttonPanel, "TIME UP", "Warning", JOptionPane.WARNING_MESSAGE);
        // progress.setVisible(true);
    
    }*/ //test
    

    public void getHint() {
        //increments hint counter every time this is called
        hintCounter++;
        //checks if the hintcounter = 1. if so then it calls the game specific hint found in their respective classes
        if (hintCounter == 1) {
            JOptionPane.showMessageDialog(null, currentGameMode.hint());
            return;
        }

        //calls the game specific hint which has its own hint count checker. this ensures the hintCounter increases every time
        currentGameMode.hint();

        //ensures that all nodes have a node colour 0 at the begin
        for(Node node: nodes)
        {
            node.colourOfNode = 0;
        }

        NodeManager manager = new NodeManager();
        
        //runs the WP method that either does WP or brute force. this gives all nodes a colour
        manager.WelshPowell(nodes);

        //checks if the gamemode is game 1
        if(currentGameMode.getClass().getName().equals("BitterEnd"))
        {
            int evenOrOdd = (int) (Math.random()*100);
            //counts how many nodes are coloured
            int colouredNodes = 0;
            for (Node node : nodes) {
                if (node.getNodeColor() != Color.white) {
                    colouredNodes++;
                }
            }
            //if not all the nodes are coloured, the broadest conditional will be defined by this condition. 
            //This is because 1 hint type is only good for fully coloured graphs
            if (colouredNodes != nodes.size()) {
                evenOrOdd = 1; 
            }
            if (evenOrOdd % 2 == 0) { //this conditional block will run if graph is fully coloured      LISA CAN YOU EXPLAIN your hint
                int min = 1;
                int max = 3;
                int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);
                if (randomInt == 1) { //hint 1: possibly runs everytime getHint() is called for bitterend
                    ArrayList<Color> usedColours = colourPanel.getListOfUsedColours();

                    for (Node node : nodes) 
                    {
                        Color colourOfCurrentNode = node.getNodeColor();

                        int positionOfColour = usedColours.indexOf(colourOfCurrentNode);

                        for (int i = 0; i < usedColours.size(); i++) //checks the colours used in the order that they were first used by the user
                        {
                            if (i == positionOfColour) 
                            {
                                continue;
                            }

                            if (isLegalColouring(node, usedColours.get(i)) == true) // if a previously used colour would also be legal, it sets the node as a hint
                            {
                                //highlights the hint node
                                node.setHintNode(true);

                                repaint();
                                //displays the hint message
                                JOptionPane.showMessageDialog(null,
                                        "The highlighted node could also use a different colour!");

                                return;
                            }
                        }
                    }
                } else if (randomInt == 2) { //hint 2: possibly runs everytime getHint() is called for bitterend
                    int randomIndex = (int) (Math.random() * (nodes.size()));
                    int firstNodePowellColour = nodes.get(randomIndex).colourFromWPowell;
                    //highlights a random node - the node will be used in a hint
                    nodes.get(randomIndex).setHintNode(true);
                    //goes through all nodes that have the same colour as the node above. It ensures the node isnt the same node. 
                    //the first node (random since nodes are generated randomly) with the same colour is highlighted aswell and the hint 
                    //message is returned. The getHint method then ends due to the return;
                    for (int i = 0; i < nodes.size(); i++) {
                        if (randomIndex != i && nodes.get(i).colourFromWPowell == firstNodePowellColour) {
                            nodes.get(i).setHintNode(true);

                            repaint();

                            JOptionPane.showMessageDialog(null, "We coloured these highlighted nodes the same colour!");

                            return;
                        }
                    }
                    //if not other nodes have same colour, getHint method doesnt end and the hint below is returned
                    repaint();
                    JOptionPane.showMessageDialog(null,
                            "We coloured this highlighted node with a colour only used once!");
                } else { //hint 3: possibly runs everytime getHint() is called for bitterend
                    int randomIndex = (int) (Math.random() * (nodes.size()));
                    int currentColourFromWPowell = nodes.get(randomIndex).colourFromWPowell;
                    int numberOfNodesWithCurrentColour = 0;

                    //counts the number of nodes with the same colour as the node at randomindex in nodes list
                    for (int i = 0; i < nodes.size(); i++) {
                        if (nodes.get(i).colourFromWPowell == currentColourFromWPowell) {
                            numberOfNodesWithCurrentColour++;
                        }
                    }

                    //highlights the node at randomindex
                    nodes.get(randomIndex).setHintNode(true);
                    //checks if the number of nodes with the colour is 1, if so then returns the hint below
                    if (numberOfNodesWithCurrentColour == 1) {
                        repaint();
                        JOptionPane.showMessageDialog(null,
                                "We coloured this highlighted node with a colour only used once!");
                        return;
                    }
                    //if there are more then one node with the nodes colour, the hint below is returned
                    repaint();
                    JOptionPane.showMessageDialog(null, "We used the colour of the highlighted node to colour "
                            + numberOfNodesWithCurrentColour + " nodes");
                }

            } else { // if graph isnt fully coloured, it picks randomly between these 2 hints. Exact same hints as the last 2 above
                int min = 1;
                int max = 2;
                int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);

                if (randomInt == 1) {
                    int randomIndex = (int) (Math.random() * (nodes.size()));
                    int firstNodePowellColour = nodes.get(randomIndex).colourFromWPowell;

                    nodes.get(randomIndex).setHintNode(true);

                    for (int i = 0; i < nodes.size(); i++) {
                        if (randomIndex != i && nodes.get(i).colourFromWPowell == firstNodePowellColour) {
                            nodes.get(i).setHintNode(true);

                            repaint();

                            JOptionPane.showMessageDialog(null, "We coloured these highlighted nodes the same colour!");

                            return;
                        }
                    }

                    repaint();
                    JOptionPane.showMessageDialog(null,
                            "We coloured this highlighted node with a colour only used once!");
                } else {
                    int randomIndex = (int) (Math.random() * (nodes.size()));
                    int currentColourFromWPowell = nodes.get(randomIndex).colourFromWPowell;
                    int numberOfNodesWithCurrentColour = 0;

                    for (int i = 0; i < nodes.size(); i++) {
                        if (nodes.get(i).colourFromWPowell == currentColourFromWPowell) {
                            numberOfNodesWithCurrentColour++;
                        }
                    }

                    nodes.get(randomIndex).setHintNode(true);
                    if (numberOfNodesWithCurrentColour == 1) {
                        repaint();
                        JOptionPane.showMessageDialog(null,
                                "We coloured this highlighted node with a colour only used once!");
                        return;
                    }

                    repaint();
                    JOptionPane.showMessageDialog(null, "We used the colour of the highlighted node to colour "
                            + numberOfNodesWithCurrentColour + " nodes");
                }
            }
        }

        if (currentGameMode.getClass().getName().equals("GameMode2")) { //checks if the gamemode is game 2

            /*
            exact same structure as the getHint() for game mode 1 "bitter end"
            copy and pasted :)
            */


            
            int evenOrOdd = (int) (Math.random() * 100);
            int colouredNodes = 0;
            for (Node node : nodes) {
                if (node.getNodeColor() != Color.white) {
                    colouredNodes++;
                }
            }
            if (colouredNodes != nodes.size()) {
                evenOrOdd = 1; 
            }
            if (evenOrOdd % 2 == 0) { 

                int min = 1;
                int max = 3;
                int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);


                if (randomInt == 1) 
                {
                    ArrayList<Color> usedColours = colourPanel.getListOfUsedColours();

                    for (Node node : nodes) 
                    {
                        Color colourOfCurrentNode = node.getNodeColor();

                        int positionOfColour = usedColours.indexOf(colourOfCurrentNode);

                        for (int i = 0; i < usedColours.size(); i++) 
                        {
                            if (i == positionOfColour) 
                            {
                                continue;
                            }

                            if (isLegalColouring(node, usedColours.get(i)) == true)
                            {
                                node.setHintNode(true);

                                repaint();

                                JOptionPane.showMessageDialog(null,"The highlighted node could also use a different colour!");

                                return;
                            }
                        }
                    }
                } 
                else if (randomInt == 2) {
                    int randomIndex = (int) (Math.random() * (nodes.size()));
                    int firstNodePowellColour = nodes.get(randomIndex).colourFromWPowell;

                    nodes.get(randomIndex).setHintNode(true);

                    for (int i = 0; i < nodes.size(); i++) {
                        if (randomIndex != i && nodes.get(i).colourFromWPowell == firstNodePowellColour) {
                            nodes.get(i).setHintNode(true);
                            repaint();
                            JOptionPane.showMessageDialog(null, "We coloured these highlighted nodes the same colour!");
                            return;
                        }
                    }
                    repaint();
                    JOptionPane.showMessageDialog(null,
                            "We coloured this highlighted node with a colour only used once!");
                } else {
                    int randomIndex = (int) (Math.random() * (nodes.size()));
                    int currentColourFromWPowell = nodes.get(randomIndex).colourFromWPowell;
                    int numberOfNodesWithCurrentColour = 0;

                    for (int i = 0; i < nodes.size(); i++) {
                        if (nodes.get(i).colourFromWPowell == currentColourFromWPowell) {
                            numberOfNodesWithCurrentColour++;
                        }
                    }
                    nodes.get(randomIndex).setHintNode(true);
                    if (numberOfNodesWithCurrentColour == 1) {
                        repaint();
                        JOptionPane.showMessageDialog(null,
                                "We coloured this highlighted node with a colour only used once!");
                        return;
                    }
                    repaint();
                    JOptionPane.showMessageDialog(null, "We used the colour of the highlighted node to colour "
                            + numberOfNodesWithCurrentColour + " nodes");
                }

            } else {
                int min = 1;
                int max = 2;
                int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);

                if (randomInt == 1) {
                    int randomIndex = (int) (Math.random() * (nodes.size()));
                    int firstNodePowellColour = nodes.get(randomIndex).colourFromWPowell;

                    nodes.get(randomIndex).setHintNode(true);

                    for (int i = 0; i < nodes.size(); i++) {
                        if (randomIndex != i && nodes.get(i).colourFromWPowell == firstNodePowellColour) {
                            nodes.get(i).setHintNode(true);
                            repaint();
                            JOptionPane.showMessageDialog(null, "We coloured these highlighted nodes the same colour!");
                            return;
                        }
                    }
                    repaint();
                    JOptionPane.showMessageDialog(null,
                            "We coloured this highlighted node with a colour only used once!");
                } else {
                    int randomIndex = (int) (Math.random() * (nodes.size()));
                    int currentColourFromWPowell = nodes.get(randomIndex).colourFromWPowell;
                    int numberOfNodesWithCurrentColour = 0;

                    for (int i = 0; i < nodes.size(); i++) {
                        if (nodes.get(i).colourFromWPowell == currentColourFromWPowell) {
                            numberOfNodesWithCurrentColour++;
                        }
                    }
                    nodes.get(randomIndex).setHintNode(true);
                    if (numberOfNodesWithCurrentColour == 1) {
                        repaint();
                        JOptionPane.showMessageDialog(null,
                                "We coloured this highlighted node with a colour only used once!");
                        return;
                    }
                    repaint();
                    JOptionPane.showMessageDialog(null, "We used the colour of the highlighted node to colour "
                            + numberOfNodesWithCurrentColour + " nodes");
                }
            }           
        }

        if(currentGameMode.getClass().getName().equals("G3")){//checks if the gamemode is game 3
            
            //exact same hints as the other game modes, except now theres only the hint 2 and 3 since hint 1 is not used in this
            //gamemode. therefore it has not been included and the if else conditionals are randomly chosen, therefore randomly picking 
            //hint 2 or hint 3
           
            int min = 1;
            int max = 2;
            int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);

            if(randomInt == 1){ //if yes, then does hint 2
                int randomIndex = (int) (Math.random()*(nodes.size()));
                int firstNodePowellColour = nodes.get(randomIndex).colourFromWPowell;            

                nodes.get(randomIndex).setHintNode(true);

                for(int i = 0; i < nodes.size(); i++){
                    if(randomIndex != i && nodes.get(i).colourFromWPowell == firstNodePowellColour){
                        nodes.get(i).setHintNode(true);
                        repaint();
                        JOptionPane.showMessageDialog(null, "We coloured these highlighted nodes the same colour!");
                        return;
                    }
                }
                repaint();
                JOptionPane.showMessageDialog(null, "We coloured this highlighted node with a colour only used once!");
            } else { //does hint 3 if hint 2 is not done
                int randomIndex = (int) (Math.random()*(nodes.size()));
                int currentColourFromWPowell = nodes.get(randomIndex).colourFromWPowell;     
                int numberOfNodesWithCurrentColour = 0;
                
                for(int i = 0; i < nodes.size(); i++){
                    if(nodes.get(i).colourFromWPowell == currentColourFromWPowell){
                        numberOfNodesWithCurrentColour++;
                    }
                }
                nodes.get(randomIndex).setHintNode(true);
                if(numberOfNodesWithCurrentColour == 1){
                    repaint();
                    JOptionPane.showMessageDialog(null, "We coloured this highlighted node with a colour only used once!");
                    return;
                }
                repaint();
                JOptionPane.showMessageDialog(null, "We used the colour of the highlighted node to colour " + numberOfNodesWithCurrentColour + " nodes");
            }
        }   
    }

    public void setColourCounter(String updatedCounter) {
        colourCounter.setText(updatedCounter);
    }

    /**
     * Randomly sets the coordinates of all nodes. Fixed coordinates for graphs of
     * size 3 and 4.
     */
    public void setNodeCoordinates() {
        if (nodes.size() == 3) {
            graphSize3();
            return;
        }

        if (nodes.size() == 4) {
            graphSize4();
            return;
        }

        for (Node node : nodes) {
            boolean check = false;

            while (check == false) {
                int x = (int) (Math.random() * PANEL_WIDTH);

                int y = (int) (Math.random() * PANEL_HEIGHT);

                node.xcoordinate = x;

                node.ycoordinate = y;

                check = enoughSpace(node);
            }
        }
    }
    
    /**
     * Restarts the gameplay
     */
    public void restart() {
        for (Node node : nodes) {
            node.setNewColour(Color.WHITE);
        }

        setColourCounter(colourPanel.getColourCount());

        this.repaint();
    }

    private void graphSize3() {
        nodes.get(0).setXCoor((PANEL_WIDTH / 2) - (WIDTH / 2));
        nodes.get(0).setYCoor((PANEL_HEIGHT / 2) - node_distance);

        nodes.get(1).setXCoor(nodes.get(0).getXCoor() - 2 * node_distance - WIDTH);
        nodes.get(1).setYCoor(nodes.get(0).getXCoor() + node_distance);

        nodes.get(2).setXCoor(nodes.get(0).getXCoor() + 2 * node_distance + WIDTH);
        nodes.get(2).setYCoor(nodes.get(0).getXCoor() + node_distance);
    }

    private void graphSize4() {
        nodes.get(0).setXCoor((PANEL_WIDTH / 2) - (node_distance * 2));
        nodes.get(0).setYCoor((PANEL_HEIGHT / 2) - (node_distance * 2));

        nodes.get(1).setXCoor(nodes.get(0).getXCoor());
        nodes.get(1).setYCoor((PANEL_HEIGHT / 2) + (node_distance * 2));

        nodes.get(2).setXCoor((PANEL_WIDTH / 2) + (node_distance * 2));
        nodes.get(2).setYCoor(nodes.get(0).getYCoor());

        nodes.get(3).setXCoor(nodes.get(2).getXCoor());
        nodes.get(3).setYCoor(nodes.get(1).getYCoor());

    }

    public boolean enoughSpace(Node passedNode) {
        boolean safe = false;
        for (Node aNode : nodes) {
            if (aNode.equals(passedNode)) {
                continue;
            }
            if (Math.abs(passedNode.xcoordinate - aNode.xcoordinate) > node_distance
                    && Math.abs(passedNode.ycoordinate - aNode.ycoordinate) > node_distance
                    && passedNode.xcoordinate > 100 && passedNode.ycoordinate > PANEL_DISTANCE
                    && passedNode.xcoordinate < PANEL_WIDTH - PANEL_DISTANCE
                    && passedNode.ycoordinate < PANEL_HEIGHT - PANEL_DISTANCE) {
                safe = true;
            } else {
                return false;
            }
        }
        return safe;
    }

    public static boolean aNodeIsSelected() {
        return aNodeIsSelected;
    }

    public boolean isErrorPresent() {
        for (Node node : nodes) {
            if (node.getHasAnError() == true) {
                return true;
            }
        }

        return false;
    }

    public boolean isLegalColouring(Node aNode, Color nodesColour) {
        for (Node adjacentNode : aNode.adjacentTo) {
            if (adjacentNode.getNodeColor().equals(nodesColour)) {
                return false;
            }
        }
        return true;
    }

}
