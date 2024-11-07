import java.awt.*;
import java.util.ArrayList;

/**
 * ClickColour objects are the elements withtin the ColourPanel. They store the position, the Color, and whether they are currently used or not. 
 * @param colour Color in RGB code
 * @param number of colour
 */
public class ClickColour 
{
    private int numberOfColour;

    private Color colourOfNode;

    private int xCoorColor, yCoorColor;

    private boolean used = false;

    static Color selectedColour;

    static ArrayList<ClickColour> allColours = new ArrayList<>();

    public ClickColour(Color colour, int number)
    {
        this.colourOfNode = colour;

        this.numberOfColour = number;

        allColours.add(this);
    }

    public int getNumberOfColour()
    {
        return numberOfColour;
    }

    public Color getColour()
    {
        return colourOfNode;
    }

    public void setXCoorColor(int value)
    {
        this.xCoorColor = value;
    }

    public void setYCoorColor(int value)
    {
        this.yCoorColor = value;
    }

    public int getXCoorColor()
    {
        return this.xCoorColor;
    }
    
    public int getYCoorColor()
    {
        return this.yCoorColor;
    }

    public void isUsed(boolean check)
    {
        used = check;
    }

    public boolean isAColourUsed()
    {
        return used;
    }

    public boolean isColourUsed(ArrayList<Node> nodes)
    {
        for(Node node : nodes)
        {
            if(node.getNodeColor().equals(this.getColour()))
            {
                return true;
            }
        }
        
        this.isUsed(false);

        return false;
    }

    public int getColourCounter()
    {
        int coloursUsed = 0;

        for(ClickColour colour : allColours)
        {
            if(colour.isAColourUsed() == true)
            {
                coloursUsed++;
            }
        }

        return coloursUsed;
    }
}

