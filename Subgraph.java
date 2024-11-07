import java.util.ArrayList;

enum GraphType
{
    Clique, Bipartite, Planar, Wheel, Cycle, Disconnected //this is the same as giving each type a number, just nicer to use
}
public class Subgraph 
{
    ArrayList<Node> graphNodes = new ArrayList<>(); //nodes of specific graph

    GraphType graphType; 

    public Subgraph(ArrayList<Node> nodes, GraphType type)
    {
        graphNodes = nodes;

        graphType = type;
    }

    public ArrayList<Node> getSubgraphNodes()
    {
        return this.graphNodes;
    }

    //This prints the dfs node id order. it indicates disconnected graphs/new subgraph, as well as when backtracking happens
    public void graphDFSPrinter(ArrayList<Subgraph> subgraphsToBePrinted){
        for(int i = 0; i < subgraphsToBePrinted.size(); i++){

            ArrayList<Node> aDfsSubgraph = subgraphsToBePrinted.get(i).getSubgraphNodes();

            System.out.println(" ");
            System.out.println("Disconnected Graph");
            System.out.println(" ");

            for(int j = 0; j < aDfsSubgraph.size(); j++) {   

                if(aDfsSubgraph.get(j) != null){

                    System.out.println(aDfsSubgraph.get(j).getID());

                } else {

                    System.out.println("null");
                }
            } 
        }
    }
}
