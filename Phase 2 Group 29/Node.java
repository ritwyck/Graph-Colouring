import java.util.ArrayList;
import java.awt.Color;


/**
 * Objects of the class Node are the base of our code. It stores all the information needed of one Node, including what its ID, what it is adjacent to and its colour. 
 * For the game it stores more parameters, such as its coordinates, selection status, and its fill and outline colour for drawing. 
 * @param id number of node
 * @param colourOfNode as a number
 * @param degree how many nodes a node is connected to
 * @param adjacentTo list of Nodes one Node is adjacent to 
 * @param select selection status
 * @param xcoordinate for drawing
 * @param ycoordinate for drawing
 * @param outlineColour Color of outline
 * @param visibleColour Color given by ColourPanel
 */
public class Node 
{
    public int id;
    public int colourOfNode;    
    public int degree;
    public int colourFromWPowell;

    private boolean selected = false;
    private boolean errorIsPresent = false;
    private boolean notOptimalColouring = false;

    public int xcoordinate;
    public int ycoordinate;
    
    public Color outlineColour;
    public Color visibleColour;

    public ArrayList<Node> adjacentTo = new ArrayList<>();
    

 
    public Node(int newID)
    {
        id = newID;

        colourOfNode = 0;

        outlineColour = Color.BLACK;

        visibleColour = Color.WHITE;
    }

    public int getID()
    {
        return this.id;
    }

    public void getDegree()
    {
        degree = adjacentTo.size();
    }

    public int getXCoor()
    {
        return xcoordinate;
    }

    public void setXCoor(int x)
    {
        this.xcoordinate = x;
    }

    public int getYCoor()
    {
        return ycoordinate;
    }

    public void setYCoor(int y)
    {
        this.ycoordinate = y;
    }

    public void setOutlineColour(Color c)
    {
        this.outlineColour = c;
    }

    public Color getOutlineColour()
    {
        return this.outlineColour;
    }

    public void setNewColour(Color c){
        this.visibleColour = c;
    }

    public int getColour(){
        return colourOfNode;
    }

    public Color getNodeColor(){
        return this.visibleColour;
    }
    
    public int getColour(Node node)
    {
        return node.colourOfNode;
    }

    public void isSelected(boolean check)
    {
        selected = check;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public boolean maybeValidColour(int colour, ArrayList<Node> adjacentTo){
        for(Node node : adjacentTo){
            if(colour == node.getColour()){
                return false;
            }
        }
        return true;
    }

    public void sameColourFinder(Node nodeChecked, int wantedColour, ArrayList<Node> allNodes, ArrayList<Integer> sameColour){
        for (Node node : allNodes){
            if(node.getColour() ==  wantedColour){
                sameColour.add(node.getID());
            }
        }
    }
    

    public void setHasAnError(boolean isThereAnError)
    {
        errorIsPresent = isThereAnError;
    }

    public boolean getHasAnError()
    {
        return errorIsPresent;
    }

    public void setHintNode(boolean bool)
    {
        notOptimalColouring = bool;
    }

    public boolean getHintNode()
    {
        return notOptimalColouring;
    }
}
