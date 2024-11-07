import java.awt.*;
import javax.swing.*;

public class ProgressBarPB {

    JFrame frame = new JFrame();
    JProgressBar progressBar = new JProgressBar(0, 100);
    JPanel panel = new JPanel();

    ProgressBarPB() {

        progressBar.setValue(0);
        progressBar.setBounds(0, 0, 800, 100);
        progressBar.setString("TIME REMAINING:");
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("MV BOLI", Font.BOLD, 35));
        progressBar.setForeground(Color.black);
        progressBar.setBackground(Color.red);
        frame.add(progressBar);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 300);
        frame.setLayout(null);
        frame.setVisible(true);

        // run(limit); method parameter can be the number of nodes/vertices inputted by the user, which then will be multiplied by 50
    }

// main function was here
ProgressBarPB pb = new ProgressBarPB();

}

  