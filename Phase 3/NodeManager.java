import java.util.ArrayList;

//This class does the analysis of the nodes, including making an arrayList o nodes, brute force and upper bound
/**
 * NodeManager handles all the algortihms in our code, including Brute Force and Welsh Powell. It does this by creating objects of the class Node. It is also used to create a 
 * list of Nodes which can be used further.
 */
public class NodeManager {
    
    public static ArrayList<Node> allNodes = new ArrayList<>();

    public ArrayList<Node> nodesLeft = new ArrayList<>(); 

    public ArrayList<Node> tempNodesLeft = new ArrayList<>(); 

    public static ArrayList<ArrayList<Node>> EdgesList = new ArrayList<>(); //from Konrado bene 

    public static ArrayList<ArrayList<Node>> ListOfVisitedEdges = new ArrayList<>();

    public static ArrayList<Boolean> visited = new ArrayList<>();

    public ArrayList<Node> generateNodesGivenGraph(ColEdge[] e, int n, int m)
    {
        for (int i = 1; i < n+1; i++)
        {
            Node newNode = new Node(i);
            allNodes.add(newNode);
        }


        for (int i = 0; i < m; i++)
        {
            for (Node node: allNodes) 
            {
                if(node.getID() == e[i].u){

                    node.getListAdjacentTo().add(getNode(e[i].v));

                    //Another Konrado Code
                    ArrayList<Node> edge = new ArrayList<>();
                    edge.add(node);
                    edge.add(getNode(e[i].v));
                    EdgesList.add(edge);
                    visited.add(false);
                }

                else if(node.getID() == e[i].v)
                {
                    node.getListAdjacentTo().add(getNode(e[i].u));
                }
            }       
        }

        nodesLeft.addAll(allNodes);

        for (Node node: allNodes)
        {
            node.setDegree();
        } 

        tempNodesLeft.addAll(nodesLeft);

        return allNodes;

    }

    public ArrayList<Node> makeNodesList(ArrayList<ArrayList<Integer>> edgesList, int n, int m)
    {
        ArrayList<Node> nodesList = new ArrayList<>();

        for (int i = 1; i < n+1; i++)
        {
            Node newNode = new Node(i);
            allNodes.add(newNode);
            nodesList.add(newNode);
        }

        for (int i = 0; i < m; i++)
        {
            for (Node node: allNodes) 
            {
                if(node.getID() == edgesList.get(i).get(0))
                {
                    node.getListAdjacentTo().add(getNode(edgesList.get(i).get(1)));
                }

                else if(node.getID() == edgesList.get(i).get(1))
                {
                    node.getListAdjacentTo().add(getNode(edgesList.get(i).get(0)));
                }
            }       
        }

        for(Node node : allNodes)
        {
            node.setDegree();
        }

        return nodesList;

    }
    
    public Node getNode(int nodeID)
    {
        for (Node node : allNodes) 
        {
            if(nodeID == node.getID())
            {
                return node;
            }
        }
        return null;
    }
}
