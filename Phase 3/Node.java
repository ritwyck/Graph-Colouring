import java.util.ArrayList;

/**
 * Objects of the class Node are the base of our code. It stores all the information needed of one Node, including what its ID, what it is adjacent to and its colour. 
 * For the game it stores more parameters, such as its coordinates, selection status, and its fill and outline colour for drawing. 
 * @param id number of node
 * @param colourOfNode as a number
 * @param degree how many nodes a node is connected to
 * @param adjacentTo list of Nodes one Node is adjacent to 
 */
public class Node 
{
    public int id;
    public int colourOfNode;    
    public int degree;
    public boolean inStack;
    public boolean alreadyInDFS;
    
    public ArrayList<Node> adjacentTo = new ArrayList<>();
 
    public Node(int newID)
    {
        id = newID;

        colourOfNode = 0;

        inStack = false;

        alreadyInDFS = false;
    }

    public int getID()
    {
        return this.id;
    }

    public void setID(int a)
    {
        this.id = a;
    }

    public void setDegree()
    {
        degree = adjacentTo.size();
    }

    public int getDegree()
    {
        return this.adjacentTo.size();
    }
    
    public int getColour()
    {
        return colourOfNode;
    }

    public void setColour(int a)
    {
        this.colourOfNode = a;
    }

    public ArrayList<Node> getListAdjacentTo(){
        return adjacentTo;
    }

    public void setListAdjacentTo(ArrayList<Node> list){
        this.adjacentTo = list;
    }

    public void setInStack(){
        this.inStack = true;
    }

    public boolean isInStack(){
        return inStack;
    }

    public void removeNodeInAdjacentList(Node node)
    {
        this.adjacentTo.remove(node);

        this.setDegree();
    }

    public void setInDFS(){
        this.alreadyInDFS = true;
    }

    public boolean getInDFS(){
        return alreadyInDFS;
    }
}
