import java.util.ArrayList;
import javax.swing.*;

public class Test extends JFrame{

    ImageIcon logo = new ImageIcon("umlogo1.png");
    String title = "PROJECT PHASE 2";

    Test() {

        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(logo.getImage());

        
        int width = 1100;
        int height = 700;

        RandomGraph graph = new RandomGraph(4, 5);

        NodeManager newManager = new NodeManager();
        ArrayList<Node> nodesList = newManager.makeNodesList(graph.getEdgeList(), graph.getNumberOfNodes(), graph.getNumberOfEdges());

        int upperBound = newManager.WelshPowell(nodesList);
        System.out.println("THE UPPER BOUND IS " + upperBound);

        BitterEnd bitterEnd = new BitterEnd(nodesList, upperBound);

        GraphPanel aGraph = new GraphPanel(nodesList, width, height, bitterEnd);
        frame.getContentPane().add(aGraph);       
        aGraph.setLocation(100, 0);

        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //these two are for making it fullscreen
        // this.setUndecorated(true);
        frame.setVisible(true);

        //int chromaticNumber = newManager.bruteForce(nodesList);
        
        //System.out.println("The chromatic number is: " + chromaticNumber);
    }

    public static void main(String[] args) {
    Test test = new Test();
    }
}
