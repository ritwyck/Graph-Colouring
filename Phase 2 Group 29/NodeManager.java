import java.util.ArrayList;
import java.util.Arrays;

//This class does the analysis of the nodes, including making an arrayList o nodes, brute force and upper bound
/**
 * NodeManager handles all the algortihms in our code, including Brute Force and Welsh Powell. It does this by creating objects of the class Node. It is also used to create a 
 * list of Nodes which can be used further.
 */
public class NodeManager {
    
    public ArrayList<Node> allNodes = new ArrayList<>();

    public ArrayList<Node> nodesLeft = new ArrayList<>(); 

    public ArrayList<Node> tempNodesLeft = new ArrayList<>(); 

    public ArrayList<Node[]> allOrders = new ArrayList<>();

    public ArrayList<Node> sameColour = new ArrayList<>();
        
    public static int currentColour = 1;

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
                if(node.id == e[i].u)
                {
                    node.adjacentTo.add(getNode(e[i].v));
                }

                else if(node.id == e[i].v)
                {
                    node.adjacentTo.add(getNode(e[i].u));
                }
            }       
        }

        nodesLeft.addAll(allNodes);

        for (Node node: allNodes)
        {
            node.getDegree();
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
                if(node.id == edgesList.get(i).get(0))
                {
                    node.adjacentTo.add(getNode(edgesList.get(i).get(1)));
                }

                else if(node.id == edgesList.get(i).get(1))
                {
                    node.adjacentTo.add(getNode(edgesList.get(i).get(0)));
                }
            }       
        }

        for(Node node : allNodes)
        {
            node.getDegree();
        }

        return nodesList;

    }


    public int WelshPowell(ArrayList<Node> passedNodes)
    {
        allNodes.addAll(passedNodes);
        nodesLeft.addAll(passedNodes);

        tempNodesLeft.addAll(passedNodes);

        Node firstMax = getMaxNode();

        colourNode(firstMax);

        boolean check = false;
        
        while (check == false) 
        {
            Node newMax = getMaxNode();

            colourNode(newMax);

            int size = nodesLeft.size();

            for (int i = 0; i < size; i++)
            {
                Node otherMax = getTempMaxNode();

                colourNode(otherMax);
            }

            if(allNodesColoured() == true)
                break;
                
            currentColour++;

            tempNodesLeft.addAll(nodesLeft);

        }

        return currentColour;
    }

    public int bruteForce(ArrayList<Node> passedNodes)
    {     
        allNodes.clear();
        allNodes.addAll(passedNodes);
        nodesLeft.addAll(passedNodes);
        
        boolean completeGraph = false;

        for(Node node : allNodes)
        {
            if(node.degree == passedNodes.size()-1)
            {
                completeGraph = true;
            }
            else
            {
                completeGraph = false;
                break;
            }
        }

        if(completeGraph == true)
        {
            return passedNodes.size();
        }



        permutations(nodesLeft.toArray(new Node[0]), 0);

        ArrayList<Integer> usedColours = new ArrayList<>();

        for (int i = 0; i < allOrders.size(); i++)
        {
            int max = 0;
            for (int j = 0; j < nodesLeft.size(); j++)
            {
                allOrders.get(i)[j].colourOfNode = lowestAvailableColour(allOrders.get(i)[j]);

                if(allOrders.get(i)[j].colourOfNode > max)
                {
                    max = allOrders.get(i)[j].colourOfNode;
                }
            }
            usedColours.add(max);

            for(Node node : allOrders.get(i))
            {
                node.colourOfNode = 0;
            }
        }


        usedColours.sort(null);

        return usedColours.get(0);
    }


    public Node getNode(int nodeID)
    {
        for (Node node : allNodes) 
        {
            if(nodeID == node.id)
            {
                return node;
            }
        }
        return null;
    }

    public Node getMaxNode()
    {
        int max = -1;

        int tempID = 0;

        for (Node node : nodesLeft) {
            if (node.degree > max)
            {
                max = node.degree;

                tempID = node.id;
            }
        }

        return getNode(tempID);
    }

    public Node getTempMaxNode()
    {
        int max = -1;

        int tempID = 0;

        for (Node node : tempNodesLeft) {
            if (node.degree > max)
            {
                max = node.degree;

                tempID = node.id;
            }
        }

        tempNodesLeft.remove(getNode(tempID));

        return getNode(tempID);
    }

    public void colourNode(Node node)
    {
        for (Node adjacentNode : node.adjacentTo) 
        {
            if(adjacentNode.colourOfNode == currentColour)
            {
                return;
            }
        }             
        //oliver added something here for hints
        node.colourOfNode = currentColour;

        nodesLeft.remove(node); //removes node when coloured
        tempNodesLeft.remove(node);
    }

    
    public boolean allNodesColoured()
    {
        
        for (Node node : allNodes) 
        {
            if(node.colourOfNode == 0)
            {
                return false;
            }
        }
        

        return true;
    }


    public void permutations(Node[] order, int index)
    {
        if(index >= order.length -1)
        {
            allOrders.add(order);
            return;
        }
        else
        {

            for(int i = index; i < order.length; i++)
            {
                Node node = order[index];

                order[index] = order[i];
                
                order[i] = node;

                permutations(order.clone(), index + 1);
            }
        }
    }

    public int lowestAvailableColour(Node node)
    {
        ArrayList<Integer> checkColour = new ArrayList<>(); // all theoretically possible colours (1,2,...,n)

        for(int i = 1; i < allNodes.size(); i++)
        {
            checkColour.add(i);
        }

        for (Node adjacentNode : node.adjacentTo) { 

            int colour = adjacentNode.colourOfNode;

            checkColour.removeAll(Arrays.asList(colour));
        }

        int smallestColour = Integer.MAX_VALUE; 
        for (Integer storedColour : checkColour) { //lowest possible colour
            if(storedColour < smallestColour)
            {
                smallestColour = storedColour;
            }
        }

        return smallestColour;
    }

    public int lowerBound() //needs to sclude the nodes that are outside the clique... will only work on perfect graph
    {
        int lowerBound = 0;

        int max = 0;

        for (Node node : nodesLeft) 
        {
            ArrayList<Node> set = new ArrayList<>();

            set.addAll(node.adjacentTo);

            set.add(node);

            boolean clique = false;

            for (Node aNode : node.adjacentTo)
            {
                ArrayList<Node> aSet = new ArrayList<>();

                aSet.addAll(aNode.adjacentTo);
    
                aSet.add(aNode);

                if(set.containsAll(aSet))
                {
                   clique = true;
                }
                else
                {
                    clique = false;
                    break;
                } 
            }

            if(clique == true)
            {
                max = set.size();
            }
        }

        lowerBound = max;

        return lowerBound;
    }     
    public ArrayList<Node> getSameColourArray(int colourOfNode){
        for(Node node: allNodes){
            if(node.getColour() == colourOfNode){
                sameColour.add(node);
            }   
        }
        return sameColour;
    }
}

