import java.awt.Window;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class GUITimer {
    private static int seconds;
    private static Timer timer;
    private static JLabel timerLabel;

    public static void main(String[] args) {
        // Get the number of seconds for the timer from the user
        String input = JOptionPane.showInputDialog("Enter the number of seconds for the timer:");
        seconds = Integer.parseInt(input);

        // Create a new JFrame to hold the timer
        JFrame frame = new JFrame("Countdown Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JLabel to display the timer
        timerLabel = new JLabel(getTimeString(), SwingConstants.CENTER);
        frame.add(timerLabel);

        // Set the size of the JFrame and make it visible
        frame.setSize(250, 100);
        frame.setVisible(true);

        // Create a new Timer to update the time every 10 milliseconds
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                updateTime();
            }
        }, 1, 1000);
    }

    private static void updateTime() {
        // Decrement the number of seconds
        seconds--;

        // Update the time display on the JLabel
        timerLabel.setText(getTimeString());

        // If the timer has reached 0, stop the timer and dispose of the JFrame
        if (seconds <= 0) {
            timer.cancel();
        }
    }

    private static String getTimeString() {
        // Calculate the minutes, seconds, and milliseconds
        int minutes = (int) Math.floor(seconds / 60);
        int secs = (int) Math.floor(seconds % 60);

        // Return the time as a string in the format "MM:SS:MMM"
        return String.format("%02d:%02d", minutes, secs);
    }

}
