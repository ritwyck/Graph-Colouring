import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

/**
 * BitterEnd holds the method relevant for this game mode, with its own winning condition and scoring system. It also holds the hint text.
 */
public class BitterEnd extends GameMode{

    
    private int kChromaticNumber;   //computers colouring number
    private ArrayList<Node> nodes;  //list of nodes

    private static int hintCounter; //counts the number of hints used

    private String hintMessage;     //stores the hint

    //constructor takes in a list of the nodes and and the computers chromatic number
    public BitterEnd(ArrayList<Node> nodes, int chromNumber) 
    {
        this.kChromaticNumber = chromNumber;

        this.nodes = nodes;
        
    }

    //called whenever the finished button is clicked
    //returns true if the users wins / coloured the graph with the computers colouring number and does so legally
    //returns false and a message if the computers colouring hasnt been reached yet
    public boolean win(int usedColours)
    {
        boolean noError = true;
        //goes through every node and check if it has an error. if there is one then it makes a checker variable = false
        for(Node node : nodes)
        {
            if(node.getHasAnError() == true)
            {
                noError = false; 
            }
        }
        //checks to see if graph is fully coloured, there are no errors (illegal colourings) and the users colouring is equal to
        //or better than the computers score. if this is all true, then the user has won and does the statements within the {} block
        if (allNodesColoured() == true && kChromaticNumber >= usedColours && noError == true)
        {
            //creates the player score = 10 - number of hints used
            int playerScore = 10 - hintCounter;

            //if more than 10 hints are used, the players score = 0
            if(hintCounter > 10){
                playerScore = 0;
            }
            //returns the 'win message' and states the players score 
            JOptionPane winMessage = new JOptionPane();
            winMessage.showMessageDialog(winMessage.getParent(), "Congratulations! \nYou found the best Colouring! \nYou received a total of " + playerScore + " points");
            
            return true; 
        } else {
            //if the user hasnt won, a  message stating the user has won is returned
            JOptionPane winMessage = new JOptionPane();
            winMessage.showMessageDialog(winMessage.getParent(), "<html> You are not there yet!</html>");

        }

        return false;
    }

    //checks if all nodes have been coloured. returns true if yes, false if no
    public boolean allNodesColoured()
    {
        //checks if any nodes are not coloured (coloured white)
        for(Node node : nodes)
        {
            if(node.getNodeColor().equals(Color.WHITE))
            {
                return false;
            }
        }

        return true;
    }

    //returns the first hint that indicates the computers colouring number
    public String hint()
    {
        //increases the hint counter by 1
        hintCounter++;
        //ensures the hintMessage hint is only returned once, otherwise hintMessage = empty string
        if(hintCounter == 1)
        {
            hintMessage = "We coloured this graph using " + kChromaticNumber + " colours.";
        }

        return hintMessage;
    }

}
