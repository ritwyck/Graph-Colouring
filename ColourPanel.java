import java.util.ArrayList;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.event.*;

    /**
     * ColourPanel is visible on GraphPanel and enabled the player to colour the nodes. It uses objects of ClickColour.
     * @param passedNodes
     * @param graphPanel
     * @param currentGameMode
     */
public class ColourPanel extends JComponent
{
    ArrayList<Node> nodes = new ArrayList<>();

    final private int WIDTH = 100;
    final private int HEIGHT = 350;

    private static int c = 30; //circle width;

    private int border = 5;

    final private int COLOUR_DISTANCE = 35;

    ArrayList<ClickColour> allColours = new ArrayList<>();
    ArrayList<Color> usedColours = new ArrayList<>();

    GameMode currentGameMode = new GameMode();


    public ColourPanel(ArrayList<Node> passedNodes, GraphPanel graphPanel, GameMode currentGameMode)
    
    {
        nodes = passedNodes;

        this.setName("COLOUR PALLET");
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Color.WHITE);

        this.currentGameMode = currentGameMode;

        class MousePressListener implements MouseListener
        {

            @Override
            public void mouseClicked(MouseEvent e) 
            {
                int x = e.getX();
                int y = e.getY();

                for(ClickColour clicked : allColours)
                {
                    if(x >= clicked.getXCoorColor() && x <= (clicked.getXCoorColor() + c) && y >= clicked.getYCoorColor() && y <= clicked.getYCoorColor() + c)
                    {
                        
                    
                            for(Node node : nodes)
                            {
                                if(node.isSelected() == true)
                                {
                                    node.setNewColour(clicked.getColour());
                                    clicked.isUsed(true);
                                }
                            }

                        repaint();
                        graphPanel.repaint();
                    }
                }

                graphPanel.setColourCounter(getColourCount());

                for(ClickColour colour : allColours)
                {
                    colour.isColourUsed(nodes); //checks if a colour is used. if not, is un-highlght
                }
                
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
            
        }

        MousePressListener listener = new MousePressListener();
        addMouseListener(listener);

 
        ClickColour colour1 = new ClickColour(new Color(255,102,102),1);
        ClickColour colour2 = new ClickColour(new Color(102,255,102),2);
        ClickColour colour3 = new ClickColour(new Color(0,0,255),3);
        ClickColour colour4 = new ClickColour(new Color(255,0,255),4);
        ClickColour colour5 = new ClickColour(new Color(255,255,0),5);
        ClickColour colour6 = new ClickColour(new Color(255,128,0),6);
        ClickColour colour7 = new ClickColour(new Color(102,204,0),7);
        ClickColour colour8 = new ClickColour(new Color(255,0,0),8);
        ClickColour colour9 = new ClickColour(new Color(128,128,128),9);
        ClickColour colour10 = new ClickColour(new Color(71,64,46),10);
        ClickColour colour11 = new ClickColour(new Color(0,0,0),11);
        ClickColour colour12 = new ClickColour(new Color(169,131,007),12);
        ClickColour colour13 = new ClickColour(new Color(193,135,107),13);
        ClickColour colour14 = new ClickColour(new Color(134,115,161),14);
        ClickColour colour15 = new ClickColour(new Color(2,86,105),15);
        ClickColour colour16 = new ClickColour(new Color(47,69,56),16);
        ClickColour colour17 = new ClickColour(new Color(245,208,51),17);
        ClickColour colour18 = new ClickColour(new Color(59,131,189),18);
        ClickColour colour19 = new ClickColour(new Color(127,181,181),19);
        ClickColour colour20 = new ClickColour(new Color(204,0,0),20);

        allColours.add(colour1);
        allColours.add(colour2);
        allColours.add(colour3);
        allColours.add(colour4);
        allColours.add(colour5);
        allColours.add(colour6);
        allColours.add(colour7);
        allColours.add(colour8);
        allColours.add(colour9);
        allColours.add(colour10);
        allColours.add(colour11);
        allColours.add(colour12);
        allColours.add(colour13);
        allColours.add(colour14);
        allColours.add(colour15);
        allColours.add(colour16);
        allColours.add(colour17);
        allColours.add(colour18);
        allColours.add(colour19);
        allColours.add(colour20); 
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        for(int rows = 0; rows < 10; rows++)
        {
            for(int i = 0; i < 2; i++)
            {
                int colourIndex = i + rows*2;
                int xcoor = border + i*COLOUR_DISTANCE;
                int ycoor = border + rows*COLOUR_DISTANCE;

                g2.setColor(allColours.get(colourIndex).getColour());
                g2.fillOval(xcoor, ycoor, c, c);

                allColours.get(colourIndex).setXCoorColor(xcoor);
                allColours.get(colourIndex).setYCoorColor(ycoor);              
            }
        }


        for(ClickColour colour : allColours)
        {
            if(colour.isAColourUsed() == true)
            {
                g2.setStroke(new BasicStroke(2));
                g2.setColor(Color.BLACK);

                g2.drawOval(colour.getXCoorColor(), colour.getYCoorColor(), c, c);
            }
        }
    }

    public ArrayList<ClickColour> getAllColours()
    {
        return allColours;
    }

    /**
     * @return number of colours that are used by the player in the graph
     */
    public int getNumberOfColours()
    {
        int numberOfColoursUsed = 0;

        for(ClickColour colour : allColours)
        {
            if(colour.isAColourUsed() == true)
            {
                numberOfColoursUsed++;

                if(!usedColours.contains(colour.getColour()))
                {
                    usedColours.add(colour.getColour());
                }
            }
            else
            {
                usedColours.remove(colour.getColour());
            }
        }

        return numberOfColoursUsed;
    }

    public String getColourCount()
    {
        return "Colours used: " + getNumberOfColours();
    }

    public ArrayList<Color> getListOfUsedColours()
    {
        return usedColours;
    }
}
