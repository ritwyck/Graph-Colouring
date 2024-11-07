import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is used to hold all solving algorithms, including DSatur and whether a graph is bipartite
 */
public class Algorithms {

    private ArrayList<Node> allNodes = new ArrayList<>();
    // public  ArrayList<Node> allNodesBipartite = new ArrayList<>();

    private ArrayList<Node> nodesLeft = new ArrayList<>();

    private ArrayList<Node> uncolouredVertices = new ArrayList<>();

    private ArrayList<Node> tempNodesLeft = new ArrayList<>(); 
        
    private static int currentColour = 1;


    public Algorithms(ArrayList<Node> passedNodes)
    {
        this.allNodes = passedNodes;

        this.uncolouredVertices.addAll(passedNodes);

        this.nodesLeft.addAll(passedNodes);
    }
    
    /**
     * Bipartite method used to check if a given graph is bipartite. if a graph is bipartite, it will return a chromatic number of 2
     * @param passedNodes = depthFirstSearch(rootNode)
     * @return true or false. if it returns true, the graph is bipartite, however, if it returns false, it is not.
     */
    public boolean bipartite(ArrayList<Node> passedNodes)
    {
        resetColours(); 
        // resets all the colours, since if a node is already coloured, the results will not be accurate.

        for(Node node : passedNodes)
        {     
            if(Objects.nonNull(node)) 
            // makes sure a node is not null to prevent unwanted errors.
            {
                if(node.getColour() == 0)
                {
                    currentColour = 1;
                    colourNode(node);
                    // if a node's colour is zero, it will try colouring it with the colour 1.
                }
                
                if(node.getColour() == 0)
                {
                    currentColour = 2;
                    colourNode(node);
                    // however, if the node cannot be coloured 1 due to its adjacent nodes, it will try to be coloured with 2.
                }

                if(node.getColour() == 0)
                {
                    return false;
                    // if the node remains uncoloured after attempting to colour it with 1 and 2, it means that a third colour is needed.
                    // this works against the idea of what a bipartite graph is, so it will return false.
                }
            }
        }
        return true;
    }       
   /**
     * DSatur, gives an upperbound. For wheel, bipartite and cyclic graphs it even returns the exact chromatic number.
     * @param passedNodes
     * @return DSatur upperbound
     */
    public int dSatur(ArrayList<Node> passedNodes){
        resetColours();
        nodesLeft.addAll(passedNodes);

        Node firstNode = getMaxNode();
        firstNode.setColour(1);
        nodesLeft.remove(firstNode);

        while (allNodesColoured() == false){ //ends the while loop/dsatur
        // while (nodesLeft.size() != 0){ //ends the while loop/dsatur - Quicker 

            Node newMax = getHighestSaturationNode();

            newMax.setColour(lowestAvailableColour(newMax));

            nodesLeft.remove(newMax);
            
        }

        int highestColourUsed = -1;

        for(Node node : allNodes){

            if(node.getColour() > highestColourUsed){
                highestColourUsed = node.getColour();
            }
        }
        return highestColourUsed;
    }

    /**
     * Welsh Powell, although not used in this program, gives a really efficient but less accurate upper bound which was used for experiments
     * @param passedNodes
     * @return upper bound Welsh Powell
     */
    public int WelshPowell(ArrayList<Node> passedNodes){
        tempNodesLeft.addAll(passedNodes);
        nodesLeft.addAll(passedNodes);
        resetColours();

        Node firstMax = getMaxNode();

        colourNode(firstMax);

        boolean check = false;
        
        while (check == false) {

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

    public Node getNode(int nodeID) // returns node based on the parameter ID
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

    /**
     * @return Node with the highest degree in allNodes
     */
    public Node getMaxNode()
    {
        int max = -1;
        Node maxNode = null;

        for (Node node : nodesLeft) {
            if (node.getDegree() > max)
            {
                max = node.getDegree();
                maxNode = node;
            }
        }

        return maxNode;
    }

    /**
     * @return Node with highest degree in tempNodesLeft
     */
    public Node getTempMaxNode(){
        int max = -1;

        int tempID = 0;

        for (Node node : tempNodesLeft) {
            if (node.getDegree() > max)
            {
                max = node.getDegree();

                tempID = node.getID();
            }
        }

        tempNodesLeft.remove(getNode(tempID));

        return getNode(tempID);
    }
    
    /**
     * HighestSaturationNode, returns the node to be coloured in the DSatur algorithm
     * @return highestColouredNode
     */
    public Node getHighestSaturationNode(){ 

        int max = -1;
        ArrayList<Node> sameHighestSaturationColour = new ArrayList<>(); // empty array in which we later store all nodes with the highest saturation degree

        for (Node node : nodesLeft) { // iterates through all the nodes that are not yet deleted
            int nodeSaturationNumber = getSaturationNumber(node); //gets saturation numbers
            if (nodeSaturationNumber > max) //if the saturation number is bigger than the previous highest saturation degree
            {
                max = nodeSaturationNumber; //then max is now equal to that node's saturation number
            }//(This way max will equal the biggest saturation number out of all nodes)
        }

        for (Node node : nodesLeft) // iterates through all the nodes that are not yet deleted
        {
            if (getSaturationNumber(node) == max) //for all nodes with the saturation number being equal to the biggest saturation number
            {
                sameHighestSaturationColour.add(node); //store them in the sameHighestSaturationColour array.
            }
        }

        Node highestColouredNode = null;

        if(sameHighestSaturationColour.size() == 1){ //if there is only one node with the highest saturation degree
            highestColouredNode = sameHighestSaturationColour.get(0); // then that node will be stored in highestColouredNode
        } 
        else // if there are more than one nodes with the highest saturatin degree (ties) 
        {
            int maxx = -1;

            for(int i = 0; i < sameHighestSaturationColour.size(); i++){ //for however many nodes have the same highest saturation degree
                int degreeOfNodeToUncolouredNodes = getDegreeToUncolouredNodes(sameHighestSaturationColour.get(i)); //get the degree they have to uncoloured nodes
                if(degreeOfNodeToUncolouredNodes > maxx){ //if the uncoloured degree of the node id bigger than the last highest uncoloured degree
                    maxx = degreeOfNodeToUncolouredNodes; // maxx will then equal the new highest uncoloured degree of that node
                    highestColouredNode = sameHighestSaturationColour.get(i); //makes the highestcolourNode = the node with the highest degree to uncoloured nodes
                }
            }
        }
        return highestColouredNode; // this will be returned by the method
    }
    /**
     * Works out the colour saturation of a node(number of different colours adjacent to a node). 
     * @param node The node we want to find the saturation number of
     * @return Returns the saturation number
     */
    public int getSaturationNumber(Node node){ //returns the number of different colour nodes a node is connected to

        ArrayList<Integer> differentAdjacentColours = new ArrayList<>();

        for(Node a : node.getListAdjacentTo()){
            if(!differentAdjacentColours.contains(a.getColour()) && a.getColour() != 0){
                differentAdjacentColours.add(a.getColour());
            }
        }
        return differentAdjacentColours.size();
    }

    /**
     * Works ou the degree of a node to only uncoloured nodes
     * @param node The node whos uncoloured degree we want to find
     * @return Returns the degree to uncoloured nodes
     */
    public int getDegreeToUncolouredNodes(Node node){ // returns how many uncoloured nodes are connected to a node
        int count = 0;

        for(Node a : node.getListAdjacentTo()){
            if(a.getColour() == 0){
                count ++;
            }
        }
        return count;
    }

    public void colourNode(Node node) // colours a node if none of its adjacent nodes share the same colour
    {
        for (Node adjacentNode : node.getListAdjacentTo()) 
        {
            if(adjacentNode.getColour() == currentColour)
            {
                return;
            }
        }  
        
        node.setColour(currentColour);

        nodesLeft.remove(node); 
        tempNodesLeft.remove(node);
    }


    
    public boolean allNodesColoured() // check if all the nodes have been coloured
    {
        for (Node node : allNodes){
            if(node.getColour() == 0){
                return false;
            }
        }

        return true;
    }

    public int lowestAvailableColour(Node node) // checks what the lowest colour available is  
    {
        ArrayList<Integer> checkColour = new ArrayList<>(); 

        for(int i = 1; i < allNodes.size(); i++)
        {
            checkColour.add(i);
        }

        for (Node adjacentNode : node.getListAdjacentTo()) { 

            int colour = adjacentNode.getColour();

            checkColour.remove(Integer.valueOf(colour));
        }

        int smallestColour = Integer.MAX_VALUE; 
        
        for (Integer storedColour : checkColour) { 
            if(storedColour < smallestColour)
            {
                smallestColour = storedColour;
            }
        }
        return smallestColour;
    }

    public void resetColours (){ // resets all colours to 0 (uncoloured)
        for(Node node : allNodes){
            node.setColour(0);
        }
    }
}