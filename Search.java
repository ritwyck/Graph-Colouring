import java.util.*;

//import javax.xml.crypto.NodeSetData;

/**
 * The Search class is used for all search algorithms, finding the biggest clique and cycles using Bron-Kerbosch and depth first search.
 * It also removes single- and zero-degree nodes. 
 */
public class Search {

    
    ArrayList<Node> allNodes = new ArrayList<>();
    

    ArrayList<Subgraph> subgraphs = new ArrayList<>(); //stores all found subgraphs

    ArrayList<Node> currentNeighbors = new ArrayList<>(); //Konni

    ArrayList<Node> visitedNodes = new ArrayList<>(); //olivers
    ArrayList<Node> unvisitedNodes = new ArrayList<>(); //olivers

    ArrayList<Node> stack = new ArrayList<>();
    ArrayList<Integer> cliqueSizes = new ArrayList<>();

    ArrayList<Node> newNodes = new ArrayList<>(); //mateusz

    ArrayList<Node> finalCycle = new ArrayList<>();

    Node currentNode; 

    int n = 1;

    int biggestSizeGraph = 0;   

    public Search(ArrayList<Node> passedNodes){
        allNodes = passedNodes;
        unvisitedNodes.addAll(passedNodes);
        currentNode = allNodes.get(0);

        newNodes.addAll(passedNodes);
    }
    
    public ArrayList<Node> depthFirstSearch(Node rootNode) {
            
        Node currentNode = new Node(0);

        Stack<Node> nodeStack = new Stack<>();

        ArrayList<Node> depthFirstSearch = new ArrayList<>();

        nodeStack.push(rootNode);

        Node nextNode = new Node(0);
        Node currentParent = new Node(0);

        while (!nodeStack.empty()){
            currentNode = nodeStack.peek();
            nodeStack.pop();

            if (currentNode.isInStack() == false){
                int idToGet = depthFirstSearch.size()-1;

                if(idToGet > 0){
                    Node previouslyAddedNode = depthFirstSearch.get(depthFirstSearch.size()-1);

                    if(!previouslyAddedNode.getListAdjacentTo().contains(currentNode)){
                        depthFirstSearch.add(null); 
                        depthFirstSearch.add(currentParent);
                    }
                }
                depthFirstSearch.add(currentNode);
                currentNode.setInStack();
            }

            ArrayList<Node> cNodeAdjacencyList = currentNode.getListAdjacentTo();
            
            for (int i = 0; i < cNodeAdjacencyList.size(); i++){
                nextNode = cNodeAdjacencyList.get(i);

                if (nextNode.isInStack() == false){
                    nodeStack.push(nextNode);
                    currentParent = currentNode;
                }
            }

        }
        for (Node dfsNode : depthFirstSearch) {

            if (this.unvisitedNodes.contains(dfsNode)) {
                this.unvisitedNodes.remove(dfsNode);
                this.visitedNodes.add(dfsNode);
            }
        }
        return depthFirstSearch;
    }

    /**
     * Checks to see if graph is disconnected. Call the depthFirstSearch() method recursively until all nodes have been visited.
     * @return Returns an arraylist of disconnected subgraphs
     */
    public ArrayList<Subgraph> wholeGraphDFS(){

        ArrayList<Subgraph> allSubgraphs = new ArrayList<>();

        while(unvisitedNodes.size() != 0){
            allSubgraphs.add(new Subgraph(depthFirstSearch(unvisitedNodes.get(0)), GraphType.Disconnected));
        }
        return allSubgraphs;
    }

    /**
     * removes zero- and one-dergee nodes until all are removed
     */
    public void removeOrphansAndOnlyChildren()
    {
        ArrayList<Node> newNodes = new ArrayList<>(allNodes);

        while(optimal() == false)
        {
            for(Node node : newNodes)
            {
                if(node.getDegree() == 0 || node.getDegree() == 1)
                {
                    removeNodeInAdjacencyLists(node);
            
                    allNodes.remove(node);
                }
            } 

        }
        
    }

    /**
     * private, check whether any removable nodes are still present
     * @return optimal or not
     */
    private boolean optimal()
    {
        for(Node node: allNodes)
        {
            if(node.getDegree() == 0 || node.getDegree() == 1)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * removes a passed node in all adjacent node's adjacency lists to avoid null reference
     * @param node
     */
    public void removeNodeInAdjacencyLists(Node node)
    {
        for(Node aNode : allNodes)
        {
            aNode.getListAdjacentTo().remove(node);
        }
    }

    /**
     * starts with adding the first node to the possible cycle and runs until all edges have been visited
     */
    public void cycleFinder () {
        //cycleFinder only works for a connected graph, therefore the connectivity needs to be checked before
        //cycleFinder returns every possible cycle in a graph where every node only occurs one in the cycle
        //the search starts with the first node and add it to the current stack and to the already visited nodes
        stack.add(currentNode);
        while (n < NodeManager.EdgesList.size()) {;
            edgeIteratering();
            //does a depth-first search until every edge is visited
        }
    }

    /**
     * method that goes to to the next node or it goes back if all of the nodes edges  have been visited, if so it goes back to the parental node
     */
    public void edgeIteratering () {
        //creates an ArrayList edge which will be the edge that will be added to already visited edges
        ArrayList<Node> edge = new ArrayList<>();    
        for (int i = 0; i < NodeManager.EdgesList.size(); i++) {
            //with this for-loop we go through the edgesList and get the first edge which is not visited
            //from this edge we take the next node and make it our current node
            if (currentNode.equals(NodeManager.EdgesList.get(i).get(0)) && !NodeManager.visited.get(i)) {
                edge.add(currentNode);
                currentNode = NodeManager.EdgesList.get(i).get(1);
                edge.add(currentNode);
                NodeManager.ListOfVisitedEdges.add(edge);
                stack.add(currentNode);
                NodeManager.visited.set(i, true);
                n++;
                break;
            } else if (currentNode.equals(NodeManager.EdgesList.get(i).get(1)) && !NodeManager.visited.get(i)) {
                edge.add(currentNode);
                currentNode = NodeManager.EdgesList.get(i).get(0);
                edge.add(currentNode);
                NodeManager.visited.set(i, true);
                NodeManager.ListOfVisitedEdges.add(edge);
                stack.add(currentNode);
                n++;
                break;
            } else if (i == NodeManager.EdgesList.size() - 1 && !(NodeManager.EdgesList.get(i).contains(currentNode) || NodeManager.visited.get(i))) {
                //if all edges of the current node are visited then we will remove the node from the stack and go back to the last node in the stack
                if(stack.size() > 1) {
                    stack.remove(stack.size() -1);
                    currentNode = stack.get(stack.size() -1);
                } else if(stack.size() <= 1) {
                    n = NodeManager.EdgesList.size();
                }
            } 
        }
        if(stack.size() >= 3 && isInStack(currentNode)) {
            //if the stack is bigger then 3 which is minimal size for a cycle and the currentnode is already in the stack then we will have  a cycle
            gettingTheCycles(currentNode);
        }
    }

    /**
     * goes through the stack and checks if a node is already in the possible cycle
     * @param currentNode
     * @return answer if the node is in a possible cycle
     */
    public boolean isInStack (Node currentNode) {
        //this method checks if the currentNode is in stack by creating a new stack that doesnt include the last element to avoid false alarms in case of getting a cycle
        ArrayList<Node> modifiedStack = new ArrayList<>();
        modifiedStack.addAll(stack);
        modifiedStack.remove(stack.get(stack.size() - 1));
        for (Node node: modifiedStack) {
            if (node.equals(currentNode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * if there is a cycle, it returns the cycle, delete subcycles, check if the current cycle is the biggest, checks if a node is more than one time in a cycle and add cycle to the subgraph list
     * @param currentNode
     */
    public void gettingTheCycles (Node currentNode) {
        //this method returns cyccles
        //it returns every node in the stack until the current node is reached again
        ArrayList<Node> currentCyle = new ArrayList<>();
        int j = stack.size() -2;
        for (int i = j; i >= 0; i-- ) {
            //iterating starts at the next to last node 
            if (stack.get(i).equals(currentNode)) {
                currentCyle.add(stack.get(i));
                break;
        }
            else {
                currentCyle.add(stack.get(i));
            }
            }


        //some cycles contain cycles inside which makes the cycle unnecessary long and therefore the following part prunes the cycle
        for(int a = currentCyle.size() - 1; a >= 0; a--) {
            int c = 1;
            if (!finalCycle.isEmpty()) {
                c = finalCycle.size();
                        }
            for (int b = 0; b < c; b++) {
                if (!finalCycle.isEmpty() && currentCyle.get(a).equals(finalCycle.get(b))) {
                    finalCycle.subList(finalCycle.indexOf(finalCycle.get(b)), finalCycle.lastIndexOf(finalCycle.get(b))).clear();
                    c = finalCycle.size();
                } else if (b == c - 1 && (finalCycle.isEmpty() || !currentCyle.get(a).equals(finalCycle.get(b)))) {
                    finalCycle.add(currentCyle.get(a));
                }
                        }
                    }   
                            
        //following part checks if an error occurs while saving the cycle
        if (finalCycle.size() > NodeManager.allNodes.size()) {
            for (int i = 0; i < finalCycle.size(); i++) {
                if ( (Collections.frequency(stack, finalCycle.get(i))) > 1 ){
                    n = NodeManager.EdgesList.size();
                            break;
                        }
                    }
                        }

        //saves the length of the largest cycle so far
        if (biggestSizeGraph < finalCycle.size()) {
            biggestSizeGraph = finalCycle.size();
                            }
        Subgraph cycle = new Subgraph(finalCycle, GraphType.Cycle);
        subgraphs.add(cycle);
        //cycles will be add to the subgraph class
                        }

    /**
     * method answers if the whole graph is a cycle
     * @param nodesInGraph is the the graph you want to check
     * @return answer is the graph a cycle
     */
    public boolean gettingCycles (ArrayList<Node> nodesInGraph) {
        //this method only returns the largest cycle in the graph
        System.out.println("start");
        boolean[] alreadyVisitedNodes = new boolean[nodesInGraph.size()];
        //it uses a boolean array to keep track of the already visited nodes
        for (int i = 0; i < nodesInGraph.size(); i++) {
            //usage of a for loop to make the method also work for disconnected graphs
            if(!alreadyVisitedNodes[nodesInGraph.indexOf(nodesInGraph.get(i))]) {
                ArrayList<Node> possibleCycle = new ArrayList<>();
                dfsCycleGetter(nodesInGraph.get(i), -1, alreadyVisitedNodes, possibleCycle, nodesInGraph);
                //calls a method which does a breath-depth search until one of the already visited nodes is find in the adjacent node list
                //-1 shows that there is no parentnode
                if (possibleCycle.size() == nodesInGraph.size()) {
                    //returns true if the whole graph is a cycle
                    return true;
                } else if (possibleCycle.size() > biggestSizeGraph) {
                    biggestSizeGraph = possibleCycle.size();   
                    //keeps track of the biggest cycle in te graph
                }
            }
        }
        return false;
        //returns false if the whole graph is not a cycle
    }

    /**
     * method that uses depth first search until a cycle is found
     * @param ongoingNode the current node we have a look at
     * @param parentalNodeID the node which we had look before
     * @param alreadyVisitedNodes boolean array that answers if the node was already visited
     * @param possibleCycle 
     * @param nodesInGraph 
     */
    public static void dfsCycleGetter (Node ongoingNode, int parentalNodeID, boolean[] alreadyVisitedNodes, ArrayList<Node> possibleCycle,  ArrayList<Node> nodesInGraph) {
        //starts with setting the current node to true
        alreadyVisitedNodes[nodesInGraph.indexOf(ongoingNode)] = true;
        //then the node will be added to the possible cycle
        possibleCycle.add(ongoingNode);
        for (Node neighborNode : ongoingNode.adjacentTo) {
            if (!alreadyVisitedNodes[nodesInGraph.indexOf(neighborNode)]) {
                    //method will be called again if the node in the adjacency list is not visited yet
                    dfsCycleGetter(neighborNode, ongoingNode.id, alreadyVisitedNodes, possibleCycle, nodesInGraph);
            } else if (neighborNode.id != parentalNodeID) {
                //stops the iteration if the node in the adjacency list is already visited and is not a cycle
                return;
            }
        }
    }

    
    //recursion problem with finding small cycles when going back to previous node

//commented
    // public void findAllCycles(int node, int previousNode, boolean[] visited, ArrayList<Node> stack)
    // {
    //     if(visited[node])
    //     {
    //         System.out.println("found return node");

    //         return;
    //     }

    //     visited[node] = true;

    //     stack.add(allNodes.get(node));

    //     for (Node adjacentNode : allNodes.get(node).getListAdjacentTo()) {
    //         if (stack.contains(adjacentNode) && stack.size() >= 3 && allNodes.indexOf(adjacentNode) != previousNode) {
    //             subgraphs.add(new Subgraph(
    //                     (ArrayList) stack.subList(stack.indexOf(adjacentNode), stack.indexOf(allNodes.indexOf(node))),
    //                     GraphType.Cycle));

    //             return;
    //         }

    //         findAllCycles(allNodes.indexOf(adjacentNode), node, visited, stack);
    //     }

    //     System.out.println("worked");
    //     return;
    // }


    // private boolean allNodesVisited(boolean[] visited) {
    //     for(int i = 0; i < visited.length; i++)
    //     {
    //         if(visited[i] == false)
    //         {
    //             return false;
    //         }
    //     }

    //     return true;
    // }

    public ArrayList<Subgraph> getSubgraphList() {
        return subgraphs;
    }
    

/**
* Bron-Kerbosch Algorithm for finding the maximal clique and lower bound
*/
public void BronKerbosch(ArrayList<Node> clique, ArrayList<Node> possibleNodes,ArrayList<Node> excludedNodes){
    ArrayList<Node> maxClique;

    if(possibleNodes.isEmpty() & excludedNodes.isEmpty())   //clique is maximal
    {
        maxClique = clique;
        cliqueSizes.add(maxClique.size());  //comparing clique sizes
        
        for(Node node : maxClique)
        {
            newNodes.remove(node);
        }
            clique.clear();
            excludedNodes.clear();  

            if(newNodes.size()>0)
            {
                BronKerbosch(clique, newNodes, excludedNodes);  //checking different cliques
            }
            else if(newNodes.size()==0)
            {
                Collections.sort(cliqueSizes);
                int BKLowerBound = Collections.max(cliqueSizes);    //largest clique size - lower bound
                if(BKLowerBound<3)
                {
                    System.out.println("LOWER BOUND: " + BKLowerBound);
                }
                else System.out.println("LOWER BOUND: " + BKLowerBound);
            } 
        }
        else
        {   
            ArrayList<Node> neighbours = new ArrayList<>();

            for(int i=0;i<possibleNodes.size();i++)
            {
                Node currentNode = possibleNodes.get(i);
                clique.add(currentNode);
                neighbours.addAll(currentNode.getListAdjacentTo());

                possibleNodes.retainAll(neighbours); //intersection of possible nodes and nodes adjacent to the current node
                excludedNodes.retainAll(neighbours); //intersection of excluded nodes and nodes adjacent to the current node

                BronKerbosch(clique, possibleNodes, excludedNodes);    //recursive step
                    possibleNodes.remove(currentNode);
                    excludedNodes.add(currentNode);
            }
        }
}

public boolean gettingCycles(int size, ArrayList<Node> allNodes2) {
    return false;
}
}