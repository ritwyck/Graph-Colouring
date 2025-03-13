
# Graph Colouring Game - Group 29

## Overview

Graph Colouring is a challenging puzzle game based on the mathematical concept of graph theory. The objective is to color nodes in a graph such that no adjacent nodes share the same color. This implementation offers three distinct game modes, providing an engaging and educational experience for players.

## Game Modes

### 1. Bitter End
Find the optimal coloring using the least number of colors. The game continues until the player achieves the best possible solution.

### 2. Best Upper Bound
Color the graph within a given time frame, aiming to use as few colors as possible. The game ends when time runs out.

### 3. Random Order
Nodes are presented in a random sequence. The player must choose the best color for each node as it appears.

## Features

- **Graph Generation**: Choose between randomly generated graphs or custom graphs from text files.
- **Customizable Complexity**: For random graphs, specify the number of nodes and edges.
- **User-Friendly Interface**: Intuitive UI for game mode selection and graph customization.
- **Dynamic Visualization**: Graphs are displayed randomly, ensuring a unique experience each time.
- **Error Highlighting**: Adjacent nodes with the same color are highlighted in red.

## How to Run

1. Execute the main method in `GUI.java`.
2. Select a game mode and graph type (random or custom).
3. For custom graphs, input the file path when prompted.

## Technical Details

- **Node Representation**: The `Node` class encapsulates all node-related information.
- **Graph Management**: `NodeManager` handles node creation and implements coloring algorithms.
- **Algorithms**: 
  - Brute Force (optimal solution for graphs up to 10 nodes)
  - Welsh Powell (efficient upper bound for larger graphs)
- **Random Graph Generation**: Efficiently creates graphs based on user-specified parameters.
- **Game Logic**: Implemented in `GraphPanel` and `G3` for different game modes.
- **User Interaction**: Utilizes `MousePressListener` for node selection and coloring.

## Limitations and Design Choices

- Graph display is limited to about 20 nodes for optimal playability.
- Unconnected nodes are removed to streamline the game experience.
- Randomized graph layouts enhance replayability but limit the maximum displayable nodes.

## Contributors

Group 29: Lisa Theis, Mateusz Zboś, Oliver van Sonsbeeck, Konrad Retzlaff, and Ritwik Srivastava (@ritwyck)

For detailed work distribution, visit our [GitHub repository](https://github.com/UM-KEN1300/graph-colouring-group29_2022).

## Acknowledgments

This project was developed as part of a group assignment, combining theoretical graph concepts with practical game development skills[1][3].

Citations:
[1] https://jdh.hamkins.org/math-for-seven-year-olds-graph-coloring-chromatic-numbers-eulerian-paths/
[2] https://www.youtube.com/watch?v=y4RAYQjKb5Y
[3] https://en.wikipedia.org/wiki/Graph_coloring
[4] https://www.youtube.com/watch?v=G-dkYSoluys
[5] https://www.sciencedirect.com/science/article/pii/S0195669811002162
[6] https://mathweb.ucsd.edu/~stochnet/talks/Chung16.pdf
[7] https://www.researchgate.net/publication/339893748_A_Connected_Version_of_the_Graph_Coloring_Game
[8] https://projecteuclid.org/journals/internet-mathematics/volume-8/issue-4/Strategic-Coloring-of-a-Graph/im/1354809992.pdf
