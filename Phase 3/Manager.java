import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class Manager {
        
    public static void main(String[] args) {


        //----------Setup---------- insert filepath here:

        ArrayList<Node> allNodes = setup("Phase 3\\phase3_2022_graph01.txt");
        Search search = new Search(allNodes);
        Algorithms algorithms = new Algorithms(allNodes);

        // ----------Optimization--------- find graph types here:
        search.removeOrphansAndOnlyChildren();


        if (algorithms.bipartite(search.depthFirstSearch(allNodes.get(0)))) 
        {
            System.out.println("EXACT: 2");
            return;
        }

        if(search.gettingCycles(allNodes.size(), allNodes))
        {
            if(allNodes.size()%2 == 0)
            {
                System.out.println("EXACT: 2");
                return;
            }
            else
            {
                System.out.println("EXACT: 3");
                return;
            }
        }


        // -----------Solving-----------
        search.BronKerbosch(new ArrayList<>(), new ArrayList<>(allNodes), new ArrayList<>());
       
        int estimate = algorithms.dSatur(allNodes);
            System.out.println("UPPER BOUND: " + estimate);

        //----------------------------

    }

    private static ArrayList<Node> setup(String filePath)
    {
        ReadInGraph reader = new ReadInGraph();
        reader.runner(filePath);

        NodeManager manager = new NodeManager();
        ArrayList<Node> allNodes = manager.generateNodesGivenGraph(reader.getColEdges(), reader.getNumberOfNodes(), reader.getNumberOfEdges());

        return allNodes;
        
    }
}
