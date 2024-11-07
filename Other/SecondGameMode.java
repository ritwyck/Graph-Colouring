import java.util.ArrayList;

import javax.swing.JOptionPane;

//import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.util.*;

/**
 * BitterEnd holds the method relevant for this game mode, with its own winning condition and scoring system. It also holds the hint text.
 */
public class SecondGameMode extends GameMode{

    private int kChromaticNumber;
    private ArrayList<Node> nodes;

    private static int hintCounter;

    private String hintMessage;

    public int gameTime;
    public int colorsUsed;



    public SecondGameMode(ArrayList<Node> nodes, int chromNumber) 
    {
        this.kChromaticNumber = chromNumber;

        this.nodes = nodes;

        hintCounter = 0;

        int timeForNode = 4;
        Timer timer = new Timer();
        gameTime = nodes.size()*timeForNode;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            gameTime =- 1;
            System.out.println("Hello");
            }
        }, 2*1000, 8*1000);
    }
    

    public boolean win(int usedColours)
    {
        colorsUsed = usedColours;
        if (allNodesColoured() == true && (kChromaticNumber >= usedColours || gameTime <= 0) )
        {
            JOptionPane winMessage = new JOptionPane();
            winMessage.showMessageDialog(winMessage.getParent(), "<html> Congratulations! <br> You found the best Colouring!</html>");
            

            return true; 
        }

        return false;
    }

    public int score()
    {
        return (int) (100 - 100*((colorsUsed - kChromaticNumber)/ nodes.size()));
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
