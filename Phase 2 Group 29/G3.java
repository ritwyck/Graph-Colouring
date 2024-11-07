
import java.util.ArrayList;
import java.util.Collections;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.Highlighter.Highlight;

public class G3 extends JComponent implements ActionListener {
    private ArrayList<Node> nodes = new ArrayList<>();
    private int hintCounter = 0;
    private final int WIDTH = 26;
    private final int HEIGHT = 26;
    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;
    private final int NODE_DISTANCE = 30;
    private final int PANEL_DISTANCE = 30;

    private final Color COLOUR_SELECTED_NODE = Color.ORANGE;
    private final Color COLOUR_ADJACENT_SELECTED = Color.CYAN;

    private static boolean aNodeIsSelected = false;
    public int kChromaticNumber;
    private G3ColourPanel G3ColourPanel;
    private JLabel colourCounter = new JLabel();
    private static int counter = 0;

    /**
     * G3 is where the nodes are displayed using objects of the class Node.
     * Nodes are places randomly given certain criteria and coloured
     * using the G3ColourPanel, which is added to this component.
     * 
     * @param newNodes    passed nodes list from NodeManager
     * @param panelWidth
     * @param panelHeight
     * @param chromNumber
     */
    public G3(ArrayList<Node> newNodes, int panelWidth, int panelHeight, int chromNumber) {
        this.kChromaticNumber = chromNumber;
        PANEL_WIDTH = panelWidth;
        PANEL_HEIGHT = panelHeight;

        for (Node node : newNodes) {
            if (node.degree != 0) {
                nodes.add(node);

            }
        }

        this.setLayout(null);

        JPanel aPanel = new JPanel();
        aPanel.setSize(80, 375);
        aPanel.setBackground(Color.LIGHT_GRAY);

        G3ColourPanel = new G3ColourPanel(nodes, this);
        G3ColourPanel.setPreferredSize(new Dimension(80, 350));
        aPanel.add(G3ColourPanel);
        aPanel.setLocation(0, PANEL_HEIGHT - 400);

        this.add(aPanel);

        if (newNodes.size() != nodes.size()) // removes nodes with degree 0 so that no nodes are standing alone
        {
            JOptionPane.showMessageDialog(null, "Zero-degree-nodes have been removed", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            System.out.println("Zero-degree-nodes have been removed");
        }
        setNodeCoordinates();

        JLabel nodeLabel = new JLabel();
        nodeLabel.setBorder(BorderFactory.createLineBorder(Color.RED));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(5, 100, 75, 200);

        JButton hint = new JButton("Hint");
        hint.setBackground(Color.LIGHT_GRAY);
        hint.setBorder(new LineBorder(Color.BLACK));
        hint.addActionListener(e -> getHint());
        hint.setPreferredSize(new Dimension(75, 30));
        hint.setEnabled(true);

        JButton restartButton = new JButton("Restart");
        restartButton.setBackground(Color.LIGHT_GRAY);
        restartButton.setBorder(new LineBorder(Color.BLACK));
        restartButton.addActionListener(e -> restart());
        restartButton.setPreferredSize(new Dimension(75, 30));
        restartButton.setEnabled(true);

        JButton selectButton = new JButton("Start");
        selectButton.setBackground(Color.LIGHT_GRAY);
        selectButton.setBorder(new LineBorder(Color.BLACK));
        selectButton.addActionListener(e -> Highlight(counter));
        selectButton.setPreferredSize(new Dimension(75, 30));
        selectButton.setEnabled(true);

        JButton nextButton = new JButton("Next Node");
        nextButton.setBackground(Color.LIGHT_GRAY);
        nextButton.setBorder(new LineBorder(Color.BLACK));
        nextButton.addActionListener(e -> NextNode());
        nextButton.setPreferredSize(new Dimension(75, 30));
        nextButton.setEnabled(true);

        JButton finishButton = new JButton("Finish");
        finishButton.setBackground(Color.LIGHT_GRAY);
        finishButton.setBorder(new LineBorder(Color.BLACK));
        finishButton.addActionListener(e -> score(kChromaticNumber, G3ColourPanel.getNumberOfColours()));
        finishButton.setPreferredSize(new Dimension(75, 30));
        finishButton.setEnabled(true);

        buttonPanel.add(selectButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(hint);
        buttonPanel.add(restartButton);
        buttonPanel.add(finishButton);
        this.add(buttonPanel);

        colourCounter.setBounds(150, 5, 100, 30);
        colourCounter.setBackground(Color.WHITE);
        colourCounter.setOpaque(false);
    }

    /**
     * Ends the game
     */
    private void GameFinished() {
        ImageIcon icon = new ImageIcon("smile.jpeg");

        JButton returnButton = new JButton("CLOSE"); // i made it turn off for now
        returnButton.addActionListener(e -> System.exit(0));

        int result = JOptionPane.showOptionDialog(null, "Thank you for playing!", "The game has finished",
                JOptionPane.CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon,
                new Object[] { returnButton }, 0);
        return;
    }

    /**
     * Checks if all nodes are coloured
     */
    public boolean allNodesColoured() {
        for (Node node : nodes) {
            if (node.getNodeColor().equals(Color.WHITE)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Continues to the next node
     */
    private void NextNode() {
        if (counter == nodes.size() - 1) {
            score(kChromaticNumber, G3ColourPanel.getNumberOfColours());
        } else {
            for (Node node : nodes) {
                node.isSelected(false);
            }
            counter++;
            Highlight(counter);
        }
    }

    /**
     * Highlights edges of a selected node
     */
    public void Highlight(int counter) {
        int nodeNumber = counter + 1;

        for (Node node : nodes) {
            node.setOutlineColour(Color.BLACK);
        }

        for (Node node : nodes) {

            if (nodes.contains(node) && (node.getID() == nodeNumber) && node.degree != 0) {
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
        }

    }

    /**
     * Calculates the score and ends the game
     */
    public void score(int kChromaticNumber, int colouredNodes) {
        colouredNodes = G3ColourPanel.getNumberOfColours();

        double a = 0;
        int score = 0;
        a = colouredNodes - kChromaticNumber;
        if (a == 0) {
            JOptionPane.showMessageDialog(null, "Congratulations! You found the best colouring!", "",
                    JOptionPane.INFORMATION_MESSAGE);
            GameFinished();

        } else if (colouredNodes == 0) {
            JOptionPane.showMessageDialog(null, "Try harder next time!", "", JOptionPane.INFORMATION_MESSAGE);
            GameFinished();
        } else {
            score = (int) kChromaticNumber * (100 / colouredNodes);
            JOptionPane.showMessageDialog(null, "Congratulations! Your score is: " + score + "%", "",
                    JOptionPane.INFORMATION_MESSAGE);
            GameFinished();
        }
    }

    /**
     * Gives the player a hint
     */
    public void getHint() {

        hintCounter++;
        String hintMessage = "";
        if (hintCounter == 1) {
            JOptionPane.showMessageDialog(null, "We coloured this graph using " + kChromaticNumber + " colours.");
            return;
        }
    }
    // int evenOrOdd = (int) (Math.random() * 100);
    // int colouredNodes = 0;
    // for (Node node : nodes) {
    // if (node.getNodeColor() != Color.white) {
    // colouredNodes++;
    // }
    // }
    // if (colouredNodes != nodes.size()) {
    // evenOrOdd = 1;
    // }
    // if (evenOrOdd % 2 == 0) {

    // int min = 1;
    // int max = 3;
    // int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);
    // if (randomInt == 1) {
    // ArrayList<Color> usedColours = G3ColourPanel.getListOfUsedColours();
    // for (Node node : nodes) {
    // Color colourOfCurrentNode = node.getNodeColor();

    // int positionOfColour = usedColours.indexOf(colourOfCurrentNode);

    // for (int i = 0; i < usedColours.size(); i++) {
    // if (i == positionOfColour) {
    // continue;
    // }
    // if (isLegalColouring(node, usedColours.get(i)) == true)
    // ;
    // {
    // node.setHintNode(true);

    // repaint();

    // JOptionPane.showMessageDialog(null,
    // "The highlighted node could also use a different colour!");

    // return;
    // }
    // }
    // }
    // } else if (randomInt == 2) {
    // int randomIndex = (int) (Math.random() * (nodes.size()));
    // int firstNodePowellColour = nodes.get(randomIndex).colourFromWPowell;

    // nodes.get(randomIndex).setHintNode(true);

    // for (int i = 0; i < nodes.size(); i++) {
    // if (randomIndex != i && nodes.get(i).colourFromWPowell ==
    // firstNodePowellColour) {
    // nodes.get(i).setHintNode(true);
    // repaint();
    // JOptionPane.showMessageDialog(null, "We coloured these highlighted nodes the
    // same colour!");
    // return;
    // }
    // }
    // repaint();
    // JOptionPane.showMessageDialog(null,
    // "We coloured this highlighted node with a colour only used once!");
    // } else {
    // int randomIndex = (int) (Math.random() * (nodes.size()));
    // int currentColourFromWPowell = nodes.get(randomIndex).colourFromWPowell;
    // int numberOfNodesWithCurrentColour = 0;

    // for (int i = 0; i < nodes.size(); i++) {
    // if (nodes.get(i).colourFromWPowell == currentColourFromWPowell) {
    // numberOfNodesWithCurrentColour++;
    // }
    // }
    // nodes.get(randomIndex).setHintNode(true);
    // if (numberOfNodesWithCurrentColour == 1) {
    // repaint();
    // JOptionPane.showMessageDialog(null,
    // "We coloured this highlighted node with a colour only used once!");
    // return;
    // }
    // repaint();
    // JOptionPane.showMessageDialog(null, "We used the colour of the highlighted
    // node to colour "
    // + numberOfNodesWithCurrentColour + " nodes");
    // }

    // } else {
    // // if graph isnt fully coloured, it picks randomly between these 2 hints
    // int min = 1;
    // int max = 2;
    // int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);

    // if (randomInt == 1) {
    // int randomIndex = (int) (Math.random() * (nodes.size()));
    // int firstNodePowellColour = nodes.get(randomIndex).colourFromWPowell;

    // nodes.get(randomIndex).setHintNode(true);

    // for (int i = 0; i < nodes.size(); i++) {
    // if (randomIndex != i && nodes.get(i).colourFromWPowell ==
    // firstNodePowellColour) {
    // nodes.get(i).setHintNode(true);
    // repaint();
    // JOptionPane.showMessageDialog(null, "We coloured these highlighted nodes the
    // same colour!");
    // return;
    // }
    // }
    // repaint();
    // JOptionPane.showMessageDialog(null,
    // "We coloured this highlighted node with a colour only used once!");
    // } else {
    // int randomIndex = (int) (Math.random() * (nodes.size()));
    // int currentColourFromWPowell = nodes.get(randomIndex).colourFromWPowell;
    // int numberOfNodesWithCurrentColour = 0;

    // for (int i = 0; i < nodes.size(); i++) {
    // if (nodes.get(i).colourFromWPowell == currentColourFromWPowell) {
    // numberOfNodesWithCurrentColour++;
    // }
    // }
    // nodes.get(randomIndex).setHintNode(true);
    // if (numberOfNodesWithCurrentColour == 1) {
    // repaint();
    // JOptionPane.showMessageDialog(null,
    // "We coloured this highlighted node with a colour only used once!");
    // return;
    // }
    // repaint();
    // JOptionPane.showMessageDialog(null, "We used the colour of the highlighted
    // node to colour "
    // + numberOfNodesWithCurrentColour + " nodes");
    // }
    // }
    // }

    private boolean isLegalColouring(Node aNode, Color nodesColour) {
        for (Node adjacentNode : aNode.adjacentTo) {
            if (adjacentNode.getNodeColor().equals(nodesColour)) {
                return false;
            }
        }
        return true;
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
        for (Node node : nodes) {
            node.setOutlineColour(Color.BLACK);
        }
        counter = 0;

        this.repaint();
    }

    private void graphSize3() {
        nodes.get(0).setXCoor((PANEL_WIDTH / 2) - (WIDTH / 2));
        nodes.get(0).setYCoor((PANEL_HEIGHT / 2) - NODE_DISTANCE);

        nodes.get(1).setXCoor(nodes.get(0).getXCoor() - 2 * NODE_DISTANCE - WIDTH);
        nodes.get(1).setYCoor(nodes.get(0).getXCoor() + NODE_DISTANCE);

        nodes.get(2).setXCoor(nodes.get(0).getXCoor() + 2 * NODE_DISTANCE + WIDTH);
        nodes.get(2).setYCoor(nodes.get(0).getXCoor() + NODE_DISTANCE);
    }

    private void graphSize4() {
        nodes.get(0).setXCoor((PANEL_WIDTH / 2) - (NODE_DISTANCE * 2));
        nodes.get(0).setYCoor((PANEL_HEIGHT / 2) - (NODE_DISTANCE * 2));

        nodes.get(1).setXCoor(nodes.get(0).getXCoor());
        nodes.get(1).setYCoor((PANEL_HEIGHT / 2) + (NODE_DISTANCE * 2));

        nodes.get(2).setXCoor((PANEL_WIDTH / 2) + (NODE_DISTANCE * 2));
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
            if (Math.abs(passedNode.xcoordinate - aNode.xcoordinate) > NODE_DISTANCE
                    && Math.abs(passedNode.ycoordinate - aNode.ycoordinate) > NODE_DISTANCE
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

    public void actionPerformed(ActionEvent e) {
    }

}
