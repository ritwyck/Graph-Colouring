**Graph Colouring Game - Group 29**

Graph Colouring is based on a group of nodes connected via edges. The idea is that no connected nodes may have the same colour. 

We were tasked to make a game based on Graph Colouring with three distinct game modes.

1) _Bitter End_ 
    The player has to find the optimal colouring (using the least amount of colours so no nodes with the same colour are adjacent) to win the game. 

2) _Best Upper Bound_
    The player has to find the best upper bound of colouring the graph within a given time frame. Once the time is up, the game is over.

3)_Random Order_ 
    The computer generates a random order of nodes to be coloured. The player has to assess the best colour for each node. 

The three game modes were integrated into one User-Interface, where the player can select the game modes and what kind of graph to use: a randomly generated one where the user can choose the amount of nodes and edges or a read in graph from a text file. 

    Our incentive as a group was to make the game a fun and playable experience, while keeping things simple for the player. This has been rather successful but comes with certain limitations for the display of graphs and finding the best colouring algorithmically. Our graphs are displayed randomly, which makes the game a lot more interesting because no two graphs are alike. This limits how many nodes we can display at a time within a certain space, as no nodes are allowed to overlap. However, our take is that graphs over 20 nodes do not make for a very enjoyable experience, which therefore fits our incentive.

_How to run our code_
The main method of our code is in the file **GUI.java**. When this file is run, the game will appear. All required files are in the zip provided. 

If you would like to implement your own graph into the game, click the  "I have my own graph" button on the second screen. This will automatically open an input field where you can input the filepath of your wanted graph once you have selected a game mode. 

It may happen that there are unconnected nodes in a graph, meaning single nodes without any connections. We have chosen to remove these nodes since it does not add to the game and their colouring is irrelevant. The player will be notified about this.

_How our code works_
We store all the information one node has in an object of the class Node. This includes: the node's ID, its degree (how many nodes it is adjacent to), a list of which nodes it is adjacent to and their respective information, a numerical colour used by the algorithms, its coordinates on the graph, its outline colour, whether it is selected by the player, the colour the player has given to it, and whether there is an error on that node.

Almost every other class makes use of the node objects. 

**NodeManager** is responsible for creating a list of nodes, storing the given information in objects of the class Node, and performing our colouring algortihms: **brute force** and **Welsh Powell.**
Brute force ensures that we get the optimal way of colouring by going through all possible ways of colouring and retrieving the one with the minimum colours used. This algorithm is used for graphs up to 10 nodes. For more nodes the calculation would take too long and would distrupt the flow of the game. For graphs of higher node count we use the Welsh Powell algorithm, which colours the nodes based on degree, highest to lowest. This is really efficient and gives a good upper bound for the graph colouring. 

The **RandomGraph generator** uses the amount of nodes and edges and creates all possible edges of the graph, which creates a fully connected graph. Then, based on the user input of edges, the difference between all edges and the user edges are removed randomly. Say the user puts in a graph of 5 nodes and 7 edges. A fully connected graph would have 10 edges, so we randomly remove 3 edges so the wanted edge count is reached. This creates a random graph very efficiently. The list of edges is then passed to NodeManager to create the list of Node objects. 

**GraphPanel** is the main part of the game, as it displays the actual nodes and also a **ColourPanel** that allows the colouring of the nodes, as well as some other components.
First, the coordinates of the nodes are set in such a way that they do not overlap and are not too close to the frame or the ColourPanel. The lines of the connected nodes are drawn, and then the nodes as circles on top, with default outline black and fill colour white. 
GraphPanel uses its inner class **MousePressListener** to listen to the players actions. If the player clicks into a node, the node is marked as selected, and its outline, the adjacent nodes' outline and their connections become highlighted. If the user then clicks into a colour in the ColourPanel(which also uses a MousePressListener class), the selected colour is stored in the node object and the GraphPanel is redrawn with the new coloured node. This way, the player can colour all the nodes while seeing clearly what is adjacent. If two adjacent nodes have the same colour, their outlines and their connection are redrawn as red, which indicates the error to the user, and the error is stored in the respective nodes. This is removed after correction. 

**G3** is the equivalent of GraphPanel, but modified in order to work with the third game mode. Game Mode 3 has additional buttons and the player can only go through randomly selected nodes using the next button. After colouring, they cannot go back. **G3ColourPanel** is the equivalent of **ColourPanel**, but again modified for the needs of game mode 3.

Once the whole graph is coloured, with every click in GraphPanel, the current game mode's winning condition is checked. This depends on the respective game mode.
**BitterEnd** uses the algorithms best colouring and compares it to the player's. If the player's number of colours used is the same or smaller, the player wins, and a win message is displayed.

All the components come together in the **GUI** which depending on which game mode is chosen calls the right methods. It also includes a neat interface that contains short instructions.


This work was done by Group 29: Lisa Theis, Mateusz Zboś, Oliver van Sonsbeeck, Konrad Retzlaff and Ritwik Srivastava (@ritwyck)
For more accurate work distribution, refer to: https://github.com/UM-KEN1300/graph-colouring-group29_2022
