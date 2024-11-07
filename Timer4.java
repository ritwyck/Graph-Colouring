import java.util.*;
public class Timer4 {
    public static void main(String[] args) {
        boolean isTimeLeft = true;
        int numOfVertices = 3;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                
            System.out.println("Hello");
            }
        }, 2*1000, 2*1000);
        isTimeLeft = false;
    }
}
