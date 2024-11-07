import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.*;
import java.util.Timer;
import java.awt.event.*;

    /**
     * GraphPanel is where the nodes are displayed using objects of the class Node. Nodes are places randomly given certain criteria and coloured
     * using the ColourPanel, which is added to this component. GraphPanel also calls the GameMode's respective methods.
     * @param newNodes passed nodes list from NodeManager
     * @param panelWidth
     * @param panelHeight
     * @param givenGame either GameMode 1 or 2 
     */
public class GraphPanelKonrad extends JComponent 
{
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

    private ColourPanel colourPanel;

    private JLabel colourCounter = new JLabel();

    int milliseconds = 0;
    int seconds = 0;
    int minutes = 0;
    boolean started = false;
    String milliseconds_string = String.format("%02d", milliseconds);
    String seconds_string = String.format("%02d", seconds);
    String minutes_string = String.format("%02d", minutes);
    

    public GraphPanelKonrad(Graphics2D g2, ArrayList<Node> newNodes, int panelWidth, int panelHeight, GameMode givenGame) 
    {         
        this.currentGameMode = givenGame;                                                              
                                                                      
        for(Node node : newNodes)
        {
            if(node.degree != 0)
            {
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
        

        if(newNodes.size() != nodes.size())  //removes nodes with degree 0 so that no nodes are standing alone
        {
            JOptionPane.showMessageDialog(null, "Zero-degree-nodes have been removed", "Warning",JOptionPane.WARNING_MESSAGE);
            System.out.println("Zero-degree-nodes have been removed");
        }

        if(nodes.size() < 10)
        {
            node_distance = 40;
        }
        else if(nodes.size() < 15)
        {
            node_distance = 30;
        }
        else
        {
            node_distance = 20;
        }

        setNodeCoordinates();

        class MousePressListener implements MouseListener
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                int usedColours = colourPanel.getNumberOfColours();

                if(isErrorPresent() == false) //only calls win method if no errors are present. No cheating allowed
                {
                    currentGameMode.win(usedColours);
                }
            

                for(Node node : nodes)
                {
                    node.setOutlineColour(Color.BLACK);
                    node.isSelected(false);
                }

                for(Node node : nodes)
                {
                    if(x >= node.getXCoor() &&  x <= (node.getXCoor() + WIDTH) && y >= node.getYCoor() && y <= (node.getYCoor()+ HEIGHT))
                    {
                        node.isSelected(true);

                        node.setOutlineColour(COLOUR_SELECTED_NODE);

                        for(Node adjacentNode : node.adjacentTo)
                        {
                            adjacentNode.setOutlineColour(COLOUR_ADJACENT_SELECTED);
                        }

                        nodes.remove(node);
                        nodes.add(node);

                        break;
                    }
                }

                repaint();
            

            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        }

        MousePressListener listener = new MousePressListener();
        addMouseListener(listener);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(5, 200, 75, 100);

        JButton hint = new JButton("Hint"); //Hint button
        hint.setBackground(Color.LIGHT_GRAY);
        hint.setBorder(new LineBorder(Color.BLACK));
        hint.addActionListener(e -> getHint());
        hint.setPreferredSize(new Dimension(75, 30));
        hint.setEnabled(true);
        buttonPanel.add(hint);

        JButton restartButton = new JButton("Restart"); //restarts the graph if user wishes to start over
        restartButton.setBackground(Color.LIGHT_GRAY);
        restartButton.setBorder(new LineBorder(Color.BLACK));
        restartButton.addActionListener(e -> restart());
        restartButton.setPreferredSize(new Dimension(75, 30));
        restartButton.setEnabled(true);

        buttonPanel.add(restartButton);
        this.add(buttonPanel);

        JPanel timerPanel = new JPanel();

        
        timerPanel.setLocation(0, 0);
        timerPanel.setBounds(0, 0, 20, 30);
        timerPanel.add(timerOf2(nodes));
        this.add(timerPanel);

        colourCounter.setBounds(150,5, 100, 30);
        colourCounter.setBackground(Color.WHITE);
        colourCounter.setOpaque(false);
        this.add(colourCounter);
        
    }


    public JLabel timerOf2 (ArrayList<Node> nodesList) {
        int nodes = nodesList.size();
        JLabel timeLabel = new JLabel();
        int secForNode = 4;
        int playTime = secForNode * nodes * 1000;
        Timer timer = new Timer();
        int tenMilliseconds = 10;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                minutes = playTime / 60000;
                seconds = (playTime % 60000) / 1000;
                milliseconds = (playTime % 60000) % 1000;
                milliseconds_string = String.format("%03d", milliseconds);
                seconds_string = String.format("%02d", seconds);
                minutes_string = String.format("%02d", minutes);
                timeLabel.setText(minutes_string+":"+minutes_string+":"+seconds_string);
            }
        }, playTime, tenMilliseconds);
        return timeLabel;
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(3));

        for(Node node : nodes)
        {

            for(Node adjacentNode : node.adjacentTo)
            {
                g2.setColor(Color.BLACK);

                if(node.getOutlineColour().equals(COLOUR_SELECTED_NODE) && adjacentNode.getOutlineColour().equals(COLOUR_ADJACENT_SELECTED))
                {
                    g2.setColor(COLOUR_ADJACENT_SELECTED);
                }

                if(node.getNodeColor().equals(adjacentNode.getNodeColor()) && !node.getNodeColor().equals(Color.WHITE) && !adjacentNode.getNodeColor().equals(Color.WHITE))
                {
                    g2.setColor(Color.RED);

                    node.setHasAnError(true);
                }
                else
                {
                    node.setHasAnError(false);
                }
                
                g2.drawLine(node.xcoordinate +(WIDTH / 2), node.ycoordinate + (HEIGHT / 2), adjacentNode.xcoordinate + (WIDTH / 2), adjacentNode.ycoordinate + (HEIGHT/2));
            }
        }

        for(Node node : nodes)
        {
            g2.setColor(node.getOutlineColour());

            for(Node adjacentNode : node.adjacentTo)
            {
                if(node.getNodeColor().equals(adjacentNode.getNodeColor()) && !node.getNodeColor().equals(Color.WHITE) && !adjacentNode.getNodeColor().equals(Color.WHITE))
                {
                    g2.setColor(Color.RED);
                }
            }

            g2.setStroke(new BasicStroke(5));
            g2.drawOval(node.xcoordinate, node.ycoordinate, WIDTH, HEIGHT);

            g2.setColor(node.getNodeColor());
            g2.fillOval(node.xcoordinate, node.ycoordinate, WIDTH, HEIGHT);

            if(node.getHintNode() == true)
            {
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(7));

                g2.drawOval(node.xcoordinate - 6, node.ycoordinate -6, WIDTH +12, WIDTH+12);

                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(3));

                node.setHintNode(false);

            }
        }    

    }

    public void getHint(){
        hintCounter++;
        if(hintCounter == 1){
            JOptionPane.showMessageDialog(null, currentGameMode.hint());
            return;
        }
        if(currentGameMode.getClass().getName().equals("BitterEnd")){
            int evenOrOdd = (int) (Math.random()*100);
            int colouredNodes = 0;
            for(Node node: nodes){
                if(node.getNodeColor() != Color.white){
                    colouredNodes++;
                }
            }
            if(colouredNodes != nodes.size()){
                evenOrOdd = 1; //wont run lisas since graph not fully coloured
            }
            if(evenOrOdd % 2 == 0){         //lisa's hint should i work if all the adjacent nodes to the hinting node already has a colour
                int min = 1;
                int max = 3;
                int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);
                if(randomInt == 1){
                    ArrayList<Color> usedColours = colourPanel.getListOfUsedColours();
                    for(Node node : nodes)
                    {
                        Color colourOfCurrentNode = node.getNodeColor();

                        int positionOfColour = usedColours.indexOf(colourOfCurrentNode);
                        
                        for(int i = 0; i < usedColours.size(); i++)
                        {
                            if(i == positionOfColour)
                            {
                                continue;
                            }
                            if(isLegalColouring(node, usedColours.get(i)) == true);
                            {
                                node.setHintNode(true);

                                repaint();

                                JOptionPane.showMessageDialog(null, "The highlighted node could also use a different colour!");

                                return;
                            }
                        }
                    }
                } else if(randomInt == 2){
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
                } else {
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

            } else {
                //if graph isnt fully coloured, it picks randomly between these 2 hints
                int min = 1;
                int max = 2;
                int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);

                if(randomInt == 1){
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
                } else {
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
        
        if(currentGameMode.getClass().getName().equals("GameMode2")){ //hints for g2 right?

            //hint that tells the user how many times a colour for a certain node is used. may also indicate nodes that dont have colours yet which is fine since they have to take it into account then
            int evenOrOdd = (int) (Math.random()*100);
            int colouredNodes = 0;
            for(Node node: nodes){
                if(node.getNodeColor() != Color.white){
                    colouredNodes++;
                }
            }
            if(colouredNodes != nodes.size()){
                evenOrOdd = 1; //wont run lisas since graph not fully coloured
            }
            if(evenOrOdd % 2 == 0){         //lisa's hint should i work if all the adjacent nodes to the hinting node already has a colour
                int min = 1;
                int max = 3;
                int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);
                if(randomInt == 1){
                    ArrayList<Color> usedColours = colourPanel.getListOfUsedColours();
                    for(Node node : nodes)
                    {
                        Color colourOfCurrentNode = node.getNodeColor();

                        int positionOfColour = usedColours.indexOf(colourOfCurrentNode);
                        
                        for(int i = 0; i < usedColours.size(); i++)
                        {
                            if(i == positionOfColour)
                            {
                                continue;
                            }
                            if(isLegalColouring(node, usedColours.get(i)) == true);
                            {
                                node.setHintNode(true);

                                repaint();

                                JOptionPane.showMessageDialog(null, "The highlighted node could also use a different colour!");

                                return;
                            }
                        }
                    }
                } else if(randomInt == 2){
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
                } else {
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

            } else {
                //if graph isnt fully coloured, it picks randomly between these 2 hints
                int min = 1;
                int max = 2;
                int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);

                if(randomInt == 1){
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
                } else {
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

        if(currentGameMode.getClass().getName().equals("G3")){
            
            //EMRE make the hints only show the nodes that still have to be coloured because otherwise showing 2 nodes that should be coloured the same 
            //but already have been coloured and thus cannot be recoloured is a useless hint. 


            //if graph isnt fully coloured, it picks randomly between these 2 hints
            int min = 1;
            int max = 2;
            int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);

            if(randomInt == 1){
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
            } else {
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
    

    public void setColourCounter(String updatedCounter)
    {
        colourCounter.setText(updatedCounter);
    }

    /**
     * Randomly sets the coordinates of all nodes. Fixed coordinates for graphs of size 3 and 4. 
     */
    public void setNodeCoordinates()
    {
        if(nodes.size() == 3)
        {
            graphSize3();
            return;
        }

        if (nodes.size() == 4)
        {
            graphSize4();
            return;
        }

        for(Node node : nodes)
        {
            boolean check = false;

            while(check == false)
            {
                int x = (int) (Math.random()*PANEL_WIDTH);

                int y = (int) (Math.random()*PANEL_HEIGHT);

                node.xcoordinate = x;
                
                node.ycoordinate = y;

                check = enoughSpace(node);
            }
        }
    }

    
    public void restart()
    {
        for (Node node : nodes) {
            node.setNewColour(Color.WHITE);
        }

        this.repaint();
    }

    private void graphSize3()
    {
        nodes.get(0).setXCoor((PANEL_WIDTH/2) - (WIDTH/2));
        nodes.get(0).setYCoor((PANEL_HEIGHT/2) - node_distance);

        nodes.get(1).setXCoor(nodes.get(0).getXCoor() - 2*node_distance - WIDTH);
        nodes.get(1).setYCoor(nodes.get(0).getXCoor() + node_distance);

        nodes.get(2).setXCoor(nodes.get(0).getXCoor() + 2*node_distance + WIDTH);
        nodes.get(2).setYCoor(nodes.get(0).getXCoor() + node_distance);
    }

    private void graphSize4()
    {
        nodes.get(0).setXCoor((PANEL_WIDTH/2) - (node_distance*2));
        nodes.get(0).setYCoor((PANEL_HEIGHT/2) - (node_distance*2));

        nodes.get(1).setXCoor(nodes.get(0).getXCoor());
        nodes.get(1).setYCoor((PANEL_HEIGHT/2) + (node_distance*2));

        nodes.get(2).setXCoor((PANEL_WIDTH/2) + (node_distance*2));
        nodes.get(2).setYCoor(nodes.get(0).getYCoor());

        nodes.get(3).setXCoor(nodes.get(2).getXCoor());
        nodes.get(3).setYCoor(nodes.get(1).getYCoor());

    }

    public boolean enoughSpace(Node passedNode)
    {
        boolean safe = false;
        for(Node aNode : nodes)
        {
            if(aNode.equals(passedNode))
            {
                continue;
            }
            if (Math.abs(passedNode.xcoordinate - aNode.xcoordinate) > node_distance && Math.abs(passedNode.ycoordinate - aNode.ycoordinate) > node_distance && passedNode.xcoordinate > 100 && passedNode.ycoordinate > PANEL_DISTANCE && passedNode.xcoordinate < PANEL_WIDTH - PANEL_DISTANCE && passedNode.ycoordinate < PANEL_HEIGHT - PANEL_DISTANCE)
            {
                safe = true;
            }
            else
            {
                return false;
            }
        }
        return safe;
    }

    public static boolean aNodeIsSelected()
    {
        return aNodeIsSelected;
    }

    public boolean isErrorPresent()
    {
        for(Node node : nodes)
        {
            if(node.getHasAnError() == true)
            {
                return true;
            }
        }

        return false;
    }

    public boolean isLegalColouring(Node aNode, Color nodesColour)
    {
        for(Node adjacentNode : aNode.adjacentTo)
        {
            if(adjacentNode.getNodeColor().equals(nodesColour))
            {
                return false;
            }
        }
        return true;
    }

}
