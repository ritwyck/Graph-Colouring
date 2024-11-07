import java.awt.*;
import javax.swing.*;

public class ProgressBarKonrad extends JComponent {
    public int nodesHere;
    public int timeForNodeHere;

    JFrame frame = new JFrame();
    JProgressBar bar = new JProgressBar(0, 100);

    ProgressBarKonrad(int nodes, int timeForNode) {
        this.nodesHere = nodes;
        this.timeForNodeHere = timeForNode;

        bar.setValue(0);
        bar.setBounds(0, 0, 50, 20);
        bar.setStringPainted(true);
        bar.setFont(new Font("MV Boli", Font.BOLD, 15));
        bar.setForeground(Color.red);
        bar.setBackground(Color.black);

        frame.add(bar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setVisible(true);

        fill(nodesHere, timeForNodeHere);
    }

    public void fill(int nodes, int timeForNode) {
        int playTime = nodes * timeForNode ;

        while (playTime > 0) {

            bar.setValue(playTime);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playTime -= 1;
        }
        bar.setString("Time is up");
    }

    public static void main(String[] args) {
        ProgressBarKonrad PB = new ProgressBarKonrad(12, 4);
    }
}