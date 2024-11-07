import java.util.ArrayList;

public class RandomGraph 
{
    private int edges;
    private int vertices;
    private ArrayList<ArrayList<Integer>> newEdges = new ArrayList<>();

    public boolean completeGraph = false;

    /**
     * Generates random edges for a new graph
     * @return
     */
    public RandomGraph(int numberOfNodes, int numberOfEdges)
    {

        edges = numberOfEdges;

        vertices = numberOfNodes;

        int maximumEdges = 0;

        for (int i = 0; i < vertices; i++) //maximum number of edges is sum of all terms up to (vertices -1) "vertices choose 2"
        {
            maximumEdges += i;
        } 
        
        if (edges > maximumEdges)
        {
            System.out.println("Invalid Graph." + "Maximum edges = " + maximumEdges);
            return;
        }
        
        

        for(Integer i = 1; i < vertices + 1; i++)
        {
            for(Integer j = 1; j < vertices + 1; j++)
            {
                if(i == j) continue;

                ArrayList<Integer> anEdge = new ArrayList<>();

                anEdge.add(i);
                anEdge.add(j);

                if(isNewEdge(newEdges, anEdge))
                {
                    newEdges.add(anEdge);
                }

                anEdge.clear();

                anEdge.add(j);
                anEdge.add(i);

                if(isNewEdge(newEdges, anEdge))
                {
                    newEdges.add(anEdge);
                }
            }

        }

        int edgesToRemove = maximumEdges - edges;

        for(int i = 0; i < edgesToRemove; i++) //instead of making the code look for new random edges, we remove edges from a completed graph until the wanted amount of edges is reached.
        //this is a lot more efficient for bigger graphs with more than 100 edges
        {
            int randomEdge = (int) (Math.random()*newEdges.size());

            newEdges.remove(randomEdge);
        }

/*
        boolean differentEdge = false;
        
        for(int i = 0; i < edges; i++) //creates a list of random edges stored as a 2d array
        {                
            int randomEdge1 = (int) (Math.random()*vertices + 1);
            int randomEdge2;

            while (differentEdge == false)
            {
                ArrayList<Integer> tempList = new ArrayList<>();

                randomEdge2 = (int) (Math.random()*vertices + 1);  

                if(randomEdge1 == randomEdge2) 
                {
                    continue;
                }

                tempList.add(randomEdge1);
                tempList.add(randomEdge2);

                if (isNewEdge(newEdges, tempList) == true)
                {
                    newEdges.add(tempList);
                    differentEdge = true;
                }
            }

            differentEdge = false;


        }
        */

    }

    /**
     * Once the edges are created, this method returns the 2d array list.
     * This is used by the NodeManager to create a list of Nodes
     * @return 2D Arraylist storing edges in the format (int, int)
     */
    public ArrayList<ArrayList<Integer>> getEdgeList()
    {
        return newEdges;
    }

    public int getNumberOfNodes()
    {
        return vertices;
    }

    public int getNumberOfEdges()
    {
        return edges;
    }

    private static boolean isNewEdge(ArrayList<ArrayList<Integer>> madeEdges, ArrayList<Integer> newEdge)
    {
        if(madeEdges.contains(newEdge))
        return false;

        int a = newEdge.get(0);

        newEdge.remove(0);

        newEdge.add(a);

        if(madeEdges.contains(newEdge))
            return false;

        else
            return true;
    }



    public int[][] getAdjacencyMatrix()
    {
        int[][] adjacencyMatrix = new int[vertices][vertices];

        for (int i = 0; i < vertices; i++)
        {
            adjacencyMatrix[newEdges.get(i).get(0) -1][newEdges.get(i).get(1) -1] = 1;
            adjacencyMatrix[newEdges.get(i).get(1) -1][newEdges.get(i).get(0) -1] = 1;
        }

        return adjacencyMatrix;
    }
    
  
}
