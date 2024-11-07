import java.util.ArrayList;
import javax.swing.*;

public class GraphCreator extends JFrame{

    ImageIcon logo = new ImageIcon("umlogo1.png");
    String title = "PROJECT PHASE 2";


    GraphCreator(int nodes,int edges) {

        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setIconImage(logo.getImage());
        //frame.getContentPane().setLayout(null);

        int width = 1100;
        int height = 700;

        RandomGraph graph = new RandomGraph(nodes,edges);
        //graph.generateNewGraph(nodes, edges);

        NodeManager newManager = new NodeManager();
        ArrayList<Node> nodesList = newManager.makeNodesList(graph.getEdgeList(), graph.getNumberOfNodes(), graph.getNumberOfEdges());

        int upperBound = newManager.WelshPowell(nodesList);
        System.out.println("THE UPPER BOUND IS " + upperBound);

        int graph_xcoor = 300;
        int graph_ycoor = 200;
        G3 aGraph = new G3(nodesList, width, height);
        
        frame.add(aGraph);       
        aGraph.setLocation(100, 100);

        
        //JButton Colours = new JButton();
        // JPanel panel = new JPanel();
        // panel.setBounds(0,0,100,100);
        // panel.add(Colours);
        //Colours.setBounds(0,0,50,30);

        //frame.add(Colours);

        frame.setVisible(true);

        //int chromaticNumber = newManager.bruteForce(nodesList);

        //System.out.println("The chromatic number is: " + chromaticNumber);
    }
    public static void main(String[] args) {
        GraphCreator test = new GraphCreator(5, 10);
    }

}
