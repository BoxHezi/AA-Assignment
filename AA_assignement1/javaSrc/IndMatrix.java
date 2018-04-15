import java.io.*;
import java.util.*;


/**
 * Incidence matrix implementation for the FriendshipGraph interface.
 * <p>
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class IndMatrix<T extends Object> implements FriendshipGraph<T> {

	/*Compile:
	javac -cp .:jopt-simple-5.02.jar:sample.jar *.java
	
	Run:
	java -cp .:jopt-simple-5.02.jar:sample.jar GraphTester <implementation>
	python assign1TestScript.py -v javaSrc indmat tests/test1.in
	
	
	*/

    /**
     * Contructs empty graph.
     */
	/*uses two array lists , an edges arraylist to store the edges in the graph and a vertex arraylist
	to store the vertices, the vertex is of object T so then it can have the correct return type*/
    private ArrayList<String> edges = new ArrayList<>();
    private ArrayList<T> vert = new ArrayList<>();
	/*grapher hold the values for the matrix*/
    private String[][] grapher;

    public IndMatrix() {
        // Implement me!
        grapher = new String[3][3];
        for (int i = 0; i < grapher.length; i++) {
            for (int j = 0; j < grapher.length; j++) {
                grapher[i][j] = "0";
            }
        }
        /*graph should be empty*/
    } // end of IndMatrix()


    public void addVertex(T vertLabel) {
        // Implement me!
		for (int i =0; i<vert.size();i++ ) {
			if (vert.get(i).equals(vertLabel)) {
				System.err.println("Vertex already exists");
				return;
			}
		}
        vert.add(vertLabel);
        addtoMatrix();
        setMatrix();
    } // end of addVertex()

    public void addtoMatrix() {
		/*adds the edges and values to a new matrix it uses an if statement and a size comparison so then it can 
		efficiently only use one for loop to use two lists*/
        grapher = new String[vert.size() + 1][edges.size() + 1];
        if (vert.size() > edges.size()) {
            for (int i = 0; i < vert.size(); i++) {
                grapher[i + 1][0] = vert.get(i).toString();
                if (i < edges.size()) {
                    grapher[0][i + 1] = edges.get(i);
                }
            }
        } else {
            for (int i = 0; i < edges.size(); i++) {
                grapher[0][i + 1] = edges.get(i);
                if (i < vert.size()) {
                    grapher[i + 1][0] = vert.get(i).toString();
                }
            }
        }
    }

    public void addEdge(T srcLabel, T tarLabel) {
        // Implement me!
		/*checks wheather the specified vertices exist and gets the largest list size to perform the loop to remove them
		if edge already exists then it gives a system error.*/
		boolean found1=false;
		boolean found2=false;
		String token[];
		if (edges.size() > vert.size()) {
			for (int i =0; i <edges.size() ; i++) {
				if (i < vert.size()) {
					if (vert.get(i).equals(srcLabel)) {
						found1 = true;
					}
					if (vert.get(i).equals(tarLabel)) {
						found2=true;
					}
				}
				token = edges.get(i).split(" ");
				/*we must account for both combinations*/
				if (token[0].equals(srcLabel.toString()) && token[1].equals(tarLabel.toString()) || token[1].equals(srcLabel.toString()) && token[0].equals(tarLabel.toString()) ) {
					System.err.println("Edge already exists");
					return;
				}
			}
		} else {
			for (int i =0; i <vert.size() ; i++) {
				if (vert.get(i).equals(srcLabel)) {
					found1 = true;
				}
				if (vert.get(i).equals(tarLabel)) {
					found2=true;
				}
				if (i < edges.size()) {
					token = grapher[0][i+1].split(" ");
					if (token[0].equals(srcLabel.toString()) && token[1].equals(tarLabel.toString()) || token[1].equals(srcLabel.toString()) && token[0].equals(tarLabel.toString()) ) {
						System.err.println("Edge already exists");
						return;
					}
				}
			}
		}
		
		if (found1==false || found2==false) {
			System.err.println("Vertices not found");
			return;
		}
		/*if successful the edges are added to the arraylist and then an update is called to construct the new graph/matrix*/
		edges.add(srcLabel+" "+tarLabel);
        addtoMatrix();
        setMatrix();
    } // end of addEdge()

    public void setMatrix() {
		/*sets the matrix up with 0s and 1s to show a connection in the matrix*/
        grapher[0][0] = "";
        for (int j = 0; j < edges.size(); j++) {
 	       for (int x = 0; x < vert.size(); x++) {
           		if (grapher[x + 1][0].equals(vert.get(x).toString()) && grapher[0][j + 1].contains(vert.get(x).toString())) {
           			grapher[x + 1][j + 1] = "1";
                } else {
                	grapher[x + 1][j + 1] = "0";
   	    		}
    	   }
        }
    }

    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();
        // Implement me!
		/*searches the edges for the vertex, if it contians a vertex then it'll add them to it's neighbours*/
		boolean exists = false;		
		String token[];
		for (int j = 0; j < edges.size(); j++) {
			token = grapher[0][j+1].split(" ");
			if ((token[0].equals(vertLabel.toString()) || token[1].equals(vertLabel.toString())) && grapher[vert.indexOf(vertLabel)+1][j+1] == "1") {
				if (!token[0].equals(vertLabel.toString()) && !neighbours.contains(token[0])) {
					neighbours.add(vert.get(vert.indexOf(token[0])));
				} else if (!token[1].equals(vertLabel.toString()) && !neighbours.contains(token[1])) {
					neighbours.add(vert.get(vert.indexOf(token[1])));
				}
				exists =true;
			}
		}
		/*if the vertex doesn't exist it returns neighbours and an error message*/
		if (exists == false) {
			System.out.print("no vertex found: ");
		}
        return neighbours;
    } // end of neighbours()


    public void removeVertex(T vertLabel) {
        // Implement me!
		/*removes the vertex and the edges it's associated with.*/
		boolean removed = false;
		String token[];
		if (vert.size() > edges.size()) {
			for (int x = 0; x < vert.size(); x++) {
				if (vert.get(x).equals(vertLabel)) {
					vert.remove(x);
					removed = true;
				}
				if (x<edges.size()) {
					token = edges.get(x).split(" ");
					if (token[0].equals(vertLabel.toString()) || token[1].equals(vertLabel.toString())) {
						edges.remove(x);
					}
				}
			}
		} else {
			for (int x = 0; x < edges.size(); x++) {
				if (edges.get(x).contains(vertLabel.toString())) {
						edges.remove(x);
					}
				if (x<vert.size()) {
					if (vert.get(x).equals(vertLabel)) {
					vert.remove(x);
					removed =true;
				}
				}
			}
		}
		if (removed == true) {
			/*When removed it rearranges the matrix by creating a new one*/
			addtoMatrix();
			setMatrix();
		} else {
			System.err.println("failed to remove: vertLabel");	
		}
    } // end of removeVertex()


    public void removeEdge(T srcLabel, T tarLabel) {
        // Implement me!
		boolean removed = false;
		/**goes through the loop, if it finds the edge in the vertex it'll remove it, if not an error will show*/
        for (int i = 0; i < edges.size(); i++) {
            if (grapher[0][i + 1].equals(tarLabel.toString()+" "+srcLabel.toString()) || grapher[0][i + 1].equals(srcLabel.toString()+" "+tarLabel.toString())) {
                grapher[0][i + 1] = "0";
                edges.remove(i);
				removed = true;
            }
        }
		 /*sort in order after edge is removed to a new matrix*/
		if (removed == true) {
         	addtoMatrix();
         	setMatrix();
		} else {
			System.err.println("Edge does not exists");	
		}
    } // end of removeEdges()


    public void printVertices(PrintWriter os) {
        // Implement me!
        for (int i = 0; i < vert.size(); i++) {
            os.print(grapher[i + 1][0].toString() + " ");
        }
        os.flush();

    } // end of printVertices()


    public void printEdges(PrintWriter os) {
		String token[];
        for (int i = 0; i < edges.size(); i++) {
			token = grapher[0][i + 1].split(" ");
			os.print(token[1] + " " + token[0] + " \n");
			os.print(token[0] + " " + token[1] + " \n");
        }
        os.flush();
		/*TESTS FOR CHECKING MATRIX
		for (int i =0; i <vert.size()+1;i++) {
			
			for(int j =0; j <edges.size()+1;j++) {
				System.out.print(grapher[i][j]);
			}
			System.out.println();	
		}*/
    } // end of printEdges()


    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
        // Implement me!
        int distance = 0;
        int x = 0;
        boolean exists1 = false;
		boolean exists2 = false;
        String findtar = vertLabel2.toString();
        String findsrc = vertLabel1.toString();
		String token[];
		/*checks if vertices exist if not it'll skip the while loop and return a -1 distance*/
        for (int i = 0; i < edges.size(); i++) {
			token = grapher[0][i + 1].split(" ");
            if (token[1].equals(vertLabel2.toString()) || token[0].equals(vertLabel2.toString())) {
                exists1 = true;
            }
			if (token[1].equals(vertLabel2.toString()) || token[0].equals(vertLabel1.toString())) {
				exists2 = true;
			}
			/*if the combination already exists as a 1 to 1 relationship it returns 1*/
            if (token[1].equals(vertLabel2.toString()) && token[0].equals(vertLabel1.toString()) || token[0].equals(vertLabel2.toString()) && token[1].equals(vertLabel1.toString())) {
                return 1;
            }
        }
		/*the idea is to find a node in the matrix by searching for the vertex then see if it has a value of 1 then it'll obtain the next
		vertex it is currently neighbours with and continue to search until it finds a match*/
		if (exists1 && exists2) {
			for (int i=0; i<vert.size();i++) {
				for (int j=0; j<edges.size();j++) {
					if (grapher[i+1][j+1] == "1" && grapher[0][j+1].contains(findsrc)) {
						distance++;
						token = grapher[0][j+1].split(" ");
						if (!token[1].equals(findsrc)) {
							findsrc = token[1]; 
						} else {
							findsrc = token[0]; 
						}
					}
					if (grapher[0][j+1].equals(findsrc+" "+findtar) || findsrc.equals(findtar)) {
						return distance;
					}
				}
			}
		}
		if (exists1 == false || exists2 ==false) {
			System.err.println("vertices don't exist: ");
		}
        // if we reach this point, source and target are disconnected
        return disconnectedDist;
    } // end of shortestPathDistance()
} // end of class IndMatrix