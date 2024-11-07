import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Experiment {
    public static void main(String[] args) {
        int nodes = 0;
        int edges = 0;

        NodeManager manager = new NodeManager();
        ArrayList<Node> nodesList = new ArrayList<>();
        String nodesString = JOptionPane.showInputDialog("ENTER NUMBER OF NODES: \n Max. 20 for a complete graph");

        nodes = Integer.parseInt(nodesString.trim());

        if ((nodes == 0) || (nodesString.equals(null))) {
            JOptionPane.showMessageDialog(new JPanel(), "NODES CANNOT BE 0.", "Warning", JOptionPane.WARNING_MESSAGE);
        } 
        else 
        {
            String edgesString = JOptionPane.showInputDialog("ENTER NUMBER OF EDGES:");

            edges = Integer.parseInt(edgesString.trim());

            if ((edges == 0) || (edgesString.equals(null))) {
                JOptionPane.showMessageDialog(new JPanel(), "Number can't be 0.", "Warning", JOptionPane.WARNING_MESSAGE);
            } 
        }

        RandomGraph graph = new RandomGraph(nodes, edges);
        nodesList = manager.makeNodesList(graph.getEdgeList(), graph.getNumberOfNodes(), graph.getNumberOfEdges());
    
        JFrame frame = new JFrame("Game Mode 1");
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        int bestColouring = 0;

        BitterEnd bitterEnd = new BitterEnd(nodesList, bestColouring);
        GraphPanel aGraph = new GraphPanel(nodesList, 1100, 700, bitterEnd);


        frame.getContentPane().add(aGraph);       
        aGraph.setLocation(100, 0);
        frame.setVisible(true);
    }
 
    
}
