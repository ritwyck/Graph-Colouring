import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class Timer2 {
    public boolean isTimeLeft;
    private int timeRemaining;
    JLabel label;

    JFrame frame;

    public Timer2 (int numOfVertices) {
        this.frame = new JFrame();
        this.label = new JLabel();
        //frame.add(label);
        this.timeRemaining = numOfVertices*1000;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                
            System.out.println("Hello");
            }
        }, numOfVertices*1000, 10);
        timer.cancel();
        isTimeLeft = false;
    }
    public boolean timeOver() {
        return isTimeLeft;
    }

    public static void main(String[] args) {
        Timer2 timer = new Timer2(1);
    }
} 