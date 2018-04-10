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
    private ArrayList<T> src = new ArrayList<>();
    private ArrayList<T> tar = new ArrayList<>();
    private ArrayList<T> vert = new ArrayList<>();
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
        if (vert.contains(vertLabel)) {
            return;
        }

        vert.add(vertLabel);
        addtoMatrix();
        setMatrix();
    } // end of addVertex()

    public void addtoMatrix() {
        grapher = new String[vert.size() + 1][src.size() + 1];
        if (vert.size() > src.size()) {
            for (int i = 0; i < vert.size(); i++) {
                grapher[i + 1][0] = vert.get(i).toString();
                if (i < src.size()) {
                    grapher[0][i + 1] = src.get(i).toString() + " " + tar.get(i).toString();
                }
            }
        } else {
            for (int i = 0; i < src.size(); i++) {
                grapher[0][i + 1] = src.get(i).toString() + " " + tar.get(i).toString();
                if (i < vert.size()) {
                    grapher[i + 1][0] = vert.get(i).toString();
                }
            }
        }
    }

    public void addEdge(T srcLabel, T tarLabel) {
        // Implement me!
		if (src.contains(srcLabel) && tar.contains(tarLabel)) {
			if (src.indexOf(srcLabel) == tar.indexOf(tarLabel)) {
				return;
			}
		}
        /*row*/
        src.add(srcLabel);
        /*Column*/
        tar.add(tarLabel);
        addtoMatrix();
        setMatrix();
    } // end of addEdge()

    public void setMatrix() {
        grapher[0][0] = "";
        for (int j = 0; j < tar.size(); j++) {
            if (grapher[0][j + 1].contains(src.get(j).toString()) && grapher[0][j + 1].contains(tar.get(j).toString())) {
                for (int x = 0; x < vert.size(); x++) {
                    if (grapher[x + 1][0].contains(vert.get(x).toString()) && grapher[0][j + 1].contains(vert.get(x).toString())) {
                        grapher[x + 1][j + 1] = "1";
                    } else {
                        grapher[x + 1][j + 1] = "0";
                    }
                }
            }
        }
    }

    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();

        // Implement me!
        for (int i = 0; i < src.size(); i++) {
            if (src.get(i).equals(vertLabel)) {
                neighbours.add(tar.get(i));
            }
            if (tar.get(i).equals(vertLabel)) {
                neighbours.add(src.get(i));
            }
        }
        return neighbours;
    } // end of neighbours()


    public void removeVertex(T vertLabel) {
        // Implement me!
        boolean empty = false;
        if (grapher.length == 0) {
            empty = true;
            return;
        }
        if (vert.contains(vertLabel)) {
            vert.remove(vert.indexOf(vertLabel));
            for (int x = 0; x < src.size(); x++) {
                if (src.contains(vertLabel) || tar.contains(vertLabel)) {
                    if (src.get(x).equals(vertLabel) || tar.get(x).equals(vertLabel)) {
                        src.remove(x);
                        tar.remove(x);
                        /*rearrange*/
                        addtoMatrix();
                        setMatrix();
                    }
                } else {
                    return;
                }
            }
        }
    } // end of removeVertex()


    public void removeEdge(T srcLabel, T tarLabel) {
        // Implement me!
        for (int i = 0; i < src.size(); i++) {
            if (grapher[0][i + 1].contains(srcLabel.toString()) && grapher[0][i + 1].contains(tarLabel.toString())) {
                grapher[0][i + 1] = "0";
                src.remove(i);
                tar.remove(i);
                /*sort in order*/
                addtoMatrix();
                setMatrix();
                return;
            }
        }
        System.out.println("Failed to remove edge : " + srcLabel.toString() + " " + tarLabel.toString());
    } // end of removeEdges()


    public void printVertices(PrintWriter os) {
        // Implement me!
        for (int i = 0; i < vert.size(); i++) {
            os.print(grapher[i + 1][0].toString() + " ");
        }
        os.flush();

    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        for (int i = 0; i < src.size(); i++) {
            os.print(grapher[0][i + 1].toString() + " \n");
            os.print(tar.get(i) + " " + src.get(i) + "\n");
        }
        os.flush();
		/*TESTS FOR CHECKING MATRIX
		for (int i =0; i <vert.size()+1;i++) {
			
			for(int j =0; j <tar.size()+1;j++) {
				System.out.print(grapher[i][j]);
			}
			System.out.println();	
		}*/
    } // end of printEdges()


    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
        // Implement me!
        int distance = 0;
        int j = 0;
        boolean exists = false;
        String findtar = vertLabel2.toString();
        String findsrc = vertLabel1.toString();
        String[] visited = new String[src.size()];

        for (int i = 0; i < tar.size(); i++) {
            if (grapher[0][i + 1].contains(vertLabel2.toString())) {
                exists = true;
            }
            if (grapher[0][i + 1].contains(vertLabel2.toString()) && grapher[0][i + 1].contains(vertLabel1.toString())) {
                return 1;
            }
            visited[i] = "0";
        }
        if (exists != false) {
            while (true) {
                if (tar.get(j).toString().equals(findsrc)) {
                    findsrc = src.get(j).toString();
                    distance++;
                } else if (src.get(j).toString().equals(findsrc)) {
                    findsrc = tar.get(j).toString();
                    distance++;
                }
                if (grapher[0][j + 1].contains(vertLabel2.toString()) && grapher[0][j + 1].contains(findsrc)) {
                    return distance;
                }
                if (tar.get(j).toString().equals(findsrc) || src.get(j).toString().equals(findsrc)) {
                    if (visited[j] == "0") {
                        visited[j] = grapher[0][j + 1];
                    } else if (visited[j].equals(grapher[0][j + 1])) {
                        for (int p = 0; p < src.size(); p++) {
                            visited[p] = "0";
                        }
                        j = -1;
                        /*System.out.println("EMPTY");*/
                        distance = -1;
                    }
                }
                j++;
                if (j >= src.size()) {
                    j = 0;
                }
            }
        }
        // if we reach this point, source and target are disconnected
        return disconnectedDist;
    } // end of shortestPathDistance()

} // end of class IndMatrix