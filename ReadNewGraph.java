import java.io.*;
import java.util.*;


		class ColEdge
			{
			int u;
			int v;
			}
		
public class ReadNewGraph
		{
		
		public final static boolean DEBUG = true;
		
		public final static String COMMENT = "//";

		static ArrayList<Integer> indexChecked = new ArrayList<>();
		public static void main(String[] args) {

            String inputfile = "your file path"; //file path (change it to where the .txt files are on your computer with \\ instead of \)
            if(inputfile.length() < 1)
            {
            System.out.println("Error! No filename specified.");
            System.exit(0);
            }

				runner(inputfile); //method for running the code directrly through vscode instead of terminal

        }

        public static void runner( String inputfile) //this method was previously the main, we changed it into a seperate method in order to call it through the main containing the file
			{
			boolean seen[] = null;
			
			//! n is the number of vertices in the graph
			int n = -1;
			
			//! m is the number of edges in the graph
			int m = -1;
			
			//! e will contain the edges of the graph
			ColEdge e[] = null;
			
			try 	{ 
			    	FileReader fr = new FileReader(inputfile);
			        BufferedReader br = new BufferedReader(fr);

			        String record = new String();
					
					//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
					//! These comments are only allowed at the top of the file.
					
					//! -----------------------------------------
			        while ((record = br.readLine()) != null)
						{
						if( record.startsWith("//") ) continue;
						break; // Saw a line that did not start with a comment -- time to start reading the data in!
						}
	
					if( record.startsWith("VERTICES = ") )
						{
						n = Integer.parseInt( record.substring(11) );					
						if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
						}

					seen = new boolean[n+1];	
						
					record = br.readLine();
					
					if( record.startsWith("EDGES = ") )
						{
						m = Integer.parseInt( record.substring(8) );					
						if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
						}

					e = new ColEdge[m];	
				


					for( int d=0; d<m; d++)
						{
						if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
						record = br.readLine();
						String data[] = record.split(" ");
						if( data.length != 2 )
								{
								System.out.println("Error! Malformed edge line: "+record);
								System.exit(0);
								}
						e[d] = new ColEdge();
						
						e[d].u = Integer.parseInt(data[0]);
						e[d].v = Integer.parseInt(data[1]);



						seen[ e[d].u ] = true;
						seen[ e[d].v ] = true;
						
						if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);
				
						}
									
					String surplus = br.readLine();
					if( surplus != null )
						{
						if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");						
						}
					
					}
			catch (IOException ex)
				{ 
		        // catch possible io errors from readLine()
			    System.out.println("Error! Problem reading file "+inputfile);
				System.exit(0);
				}

			for( int x=1; x<=n; x++ )
				{
				if( seen[x] == false )
					{
					if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
					}
				}

			//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
			//! e[1] will be the second edge...
			//! (and so on)
			//! e[m-1] will be the last edge
			//! 
			//! there will be n vertices in the graph, numbered 1 to n

	
			//! INSERT YOUR CODE HERE!

			
			//adjacency Matrix that stores the connections for each node n, where the connections of n are stored in adjacencyMatrix[n-1]
			int[][] adjacencyMatrix  = new int [n][n]; 
			for(int i = 0; i < m; i++)
			{
				adjacencyMatrix[(e[i].u)-1][(e[i].v)-1] = 1;
				adjacencyMatrix[(e[i].v)-1][(e[i].u)-1] = 1;
			}

			if(n < 50){ //does not print adjacency Matrix if n is larger than 50 to save time and to make the output more appealing
			print2dArray(adjacencyMatrix);
			}


			//Calls the method getDegree, which returns the degrees of each node (degree = amount of adjacent nodes of one node) stored in an array.
			//In the array, degreeArray[n-1] stores the degree of n.
			System.out.println(" ");
			int[] degreeArray = getDegree(adjacencyMatrix);


			//An option for an upperbound is to take the number of highest degree for one node + 1. We add 1 in case the graph is a complete graph,
			//which means the chromatic number is (highest degree + 1)
			int upperbound = getIndexMax(degreeArray);
			System.out.println("The upper bound is: " + (degreeArray[upperbound] + 1));
	
	
			// The default colour is set to 1. Colour 0 means that the node has not been coloured yet
			int colour = 1;
			int[] coloursStored = new int[adjacencyMatrix.length]; //this array stores the colour for node n at coloursStored[n-1]


			//The method welshPowellRecursion goes through the order of degrees, starting at the highest degree node and progressing downward.
			//It colours the highest node in colour 1 and then proceeds to check if the lower degree nodes are adjacent. If they are not adjacent
			//it colours the next highest node in the lowest possible number
			welshPowellRecursion(adjacencyMatrix, degreeArray, coloursStored, colour);
	
			//System.out.println(Arrays.toString(coloursStored));

			//The chromatic number is the highest value stored in the coloursStored array, since it is the highest colour used
			int indexChromaticNumber = getIndexMax(coloursStored);
			System.out.println(" ");
	
			System.out.println("The chromatic number is: " + coloursStored[indexChromaticNumber]);


			//END OF "MAIN"
		}


		/**
		 * The method getDegree returns an array which holds the degree of each node (meaning how many edges reach each node).
		 * Here, storeDegree[n-1] array corresponds to adjacencyMatrix[n-1] for node n, which allows for further analysis.
		 * @param adjacencyMatrix
		 * @return
		 */
		public static int[] getDegree(int[][] adjacencyMatrix){
			int[] storeDegree = new int[adjacencyMatrix.length]; 
			int degree = 0;

			for(int i = 0; i < adjacencyMatrix.length; i++){
				degree = 0;
				for(int j = 0; j < adjacencyMatrix.length; j++){
				degree = degree + adjacencyMatrix[i][j];
				}
				storeDegree[i] = degree;
			}
			return storeDegree;
		}

		/**
		 * This is the welshPowellRecursion, which is responsible for colouring each node in an efficient way. It takes the highest degree node n
		 * and colours n in the default colour 1. It then searches the next higher-degree node, checks if m is adjacent to n, and if m is not adjacent, 
		 * also colours it in the default colour 1. If it is adjacent, it does not colour it and leaves m to be coloured later. Again, the next higher-degree node
		 * mNext is searched. The method then checks if mNext is adjacent to any of the nodes that were checked before. This is achieved by storing all
		 * checked values in a list and checking if the current maximum is adjacent to any of them. mNext is then given the lowest available number
		 * by the lowestAvailableNumber method.
		 * @param adjacencyMatrix
		 * @param degreeArray
		 * @param coloursStored
		 * @param colour
		 */
		public static void welshPowellRecursion(int[][]adjacencyMatrix, int[] degreeArray, int[] coloursStored, int colour){
			boolean coloursFilled = false;
			for (int i = 0; i < degreeArray.length; i++){ //checks whether the colours are all filled
				if (coloursStored[i] == 0){
					coloursFilled  = false;
				break;
				} else {
					coloursFilled  = true;
				}
			}
	
			if(coloursFilled  == true){ //if all colours are filled, then it returns
				return;
			}
	
			int n = getIndexMax(degreeArray); //calling method getMaxIndex gets the index (n-1) of the maximum degreeArray, corresponding to node n

			coloursStored[n] = lowestAvailableColour(coloursStored, adjacencyMatrix, n);  //stores default colour in first run at index n-1 for node n, current colour for recursion runs

			degreeArray[n] = 0; //deletes value in degree output, which allows for new maximum
	
			int [] tempDegreeArray = Arrays.copyOf(degreeArray, degreeArray.length); //a temporary copy of degreeArray is made to allow for multiple comparisons without changing the original array completely

			for(int i = 0; i < tempDegreeArray.length; i++) //do this operation as many times as degreeArray is long
			{
                int m = getIndexMax(tempDegreeArray); //get current maximum m after n has been put to 0

				if(tempDegreeArray[m] == 0){ //if the next maximum degree is 0, which means the next node has no edge, then the recursion stops, since there is no point in continuing
					return;
				}

                if (adjacencyMatrix[n][m] == 0) //checks if there is a connection between n and m
				{
					coloursStored[m]= lowestAvailableColour(coloursStored, adjacencyMatrix, m); //stores lowest possible colour for m at coloursStored[m-1] using lowestAvailableColour
					tempDegreeArray[m] = 0; //"deletes" the temporal maximum
                    for(int j : tempDegreeArray) //for each element in degree array that is not yet set to 0
					{
						int mNext= getIndexMax(tempDegreeArray); //gets the next highest maximum
						for (int k : indexChecked) //for each k that is added to the indexChecked
							{
							if (adjacencyMatrix[mNext][k] == 0) //if the next higher-degree maximun is not adjacent to any of the ones checked before
                    		{
                        		coloursStored[mNext] = lowestAvailableColour(coloursStored, adjacencyMatrix, mNext); //it then stores lowest possible colour for mNext coloursStored[mNext-1] using lowestAvailableColour
								indexChecked.add(mNext); //add the index of mNext as checked in indexChecked

								break;//breaks the inner for loop
                    		}	
						}
					}	
                }              
                indexChecked.add(m); //add index of mNext in indexChecked list
            }
            
            indexChecked.clear(); //clear list for the next recursion

			colour++;//increase the colour by one for the next recursion
            welshPowellRecursion(adjacencyMatrix, degreeArray, coloursStored, colour);
		}

		/**
		 * the method lowestAvailableColour
		 * @param coloursStored
		 * @param adjacencyMatrix
		 * @param m
		 * @return
		 */
		public static int lowestAvailableColour(int[] coloursStored, int[][] adjacencyMatrix, int passedNode){
			ArrayList<Integer> checkColour = new ArrayList<>(); //list that stores all theoretically possible colours (1,2,...,n)
			for(int i = 1; i < coloursStored.length; i++)
			{
				checkColour.add(i);
			}
			
			ArrayList<Integer> isAdjacent = new ArrayList<>(); //list that stores all nodes that are adjacent to the passed Node
			for(int j = 0; j < adjacencyMatrix.length; j++)
			{
				if(adjacencyMatrix[passedNode][j] == 1)
				{
					isAdjacent.add(j);
				}
			}

			for (int adjacentNode : isAdjacent) { //for each node in isAdjacent list, this loop removes all colours that are adjacent to passed Node
												//this eliminates the possibility of the passed node getting the same colour as an adjacent one
				int colour;
				colour = coloursStored[adjacentNode];
				checkColour.removeAll(Arrays.asList(colour));
			}

			int smallestColour = Integer.MAX_VALUE; //very large number to make sure it is bigger
			for (Integer storedColour : checkColour) { //gets the smallest value of the possible colours left in checkColour, which makes this efficient colouring
				if(storedColour < smallestColour)
				{
					smallestColour = storedColour;
				}
			}

			return smallestColour; //smallest possible number is then returned
		}


		public static int getIndexMax(int[] degreeArray){ //gives the index of the maximum value of an array and stores it in blacklist
			int index = 0;
			for(int i = 0; i < degreeArray.length; i++)
			{
				if(degreeArray[i] > degreeArray[index])
				{
					index = i;
				}
			}
			return index;
		}

		
		//2D Array printer
		public static void print2dArray(int[][] inputArray){
		
			System.out.println(" ");
	
			for(int i = 0; i < inputArray.length; i++)
			{
				for(int j = 0; j < inputArray[0].length; j++)
				{
					System.out.print(inputArray[i][j] + " ");
				}
				System.out.println();
			}
		}
		
		}
