import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.*;
import java.util.*;

/**
 GameMode2 holds the method relevant for this game mode, with its own winning condition and scoring system. It also holds the hint text.
 */
public class GameMode2 extends GameMode{

    private int kChromaticNumber;
    private ArrayList<Node> nodes;

    private static int hintCounter;

    private String hintMessage;

    public static int gameTime;
    public int colorsUsed;



    public GameMode2(ArrayList<Node> nodes, int chromNumber) 
    {
        this.kChromaticNumber = chromNumber;

        this.nodes = nodes;

        hintCounter = 0;

        int timeForNode = 4;
        //this is the time we give the user
        Timer timer = new Timer();
        this.gameTime = nodes.size()*timeForNode;
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
            gameTime -= 1;
            
            if(gameTime <= 0) {
                // if the timer is over it will check if the user won and the timer will be stopped 
                win(GUI.aGraph.colourPanel.getNumberOfColours());
                timer.cancel();
            }
            }
        }, 1*1000, 1*1000);
    }

    
    

    public boolean win(int usedColours)
    {
        colorsUsed = usedColours;
        if (allNodesColoured() == true && (kChromaticNumber <= usedColours) && (gameTime <= 0))
        {
            JOptionPane winMessage = new JOptionPane();
            winMessage.showMessageDialog(winMessage.getParent(), "Congratulations! \n You managed to color everything. \n Your score is " + score() + "% !");
            return true; 
        } 
        else if (allNodesColoured() == false && gameTime <= 0) 
        {
        JOptionPane looseMessage = new JOptionPane();
        looseMessage.showMessageDialog(looseMessage.getParent(), "Ayy Gringo. Your coloring does not belong here. \n Take less Siestas.");
        return false;
        }
        return false;
    }

    public int score()
    {
        return (int) (100 - 100*((GUI.aGraph.colourPanel.getNumberOfColours() - kChromaticNumber)/ nodes.size()));
    }

    public boolean allNodesColoured()
    {
        for(Node node : nodes)
        {
            if(node.getNodeColor().equals(Color.WHITE))
            {
                return false;
            }
        }

        return true;
    }

    public String hint()
    {
        hintCounter++;
        
        if(hintCounter == 1)
        {
            hintMessage = "We coloured this graph using " + kChromaticNumber + " colours.";
        }

        return hintMessage;
    }

}
