import java.io.*;
import java.util.*;

/**
 * Adjacency matrix implementation for the FriendshipGraph interface.
 * <p>
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class AdjMatrix<T extends Object> implements FriendshipGraph<T> {
    private ArrayList<T> vertexList;
    private HashMap<T, ArrayList<T>> edgeMap;

    private int[][] matrix;

    /**
     * Contructs empty graph.
     */
    public AdjMatrix() {
        // Implement me!
        vertexList = new ArrayList<>();
        edgeMap = new HashMap<>();

        matrix = new int[0][0];
    } // end of AdjMatrix()


    public void addVertex(T vertLabel) {
        // Implement me!
        if (vertexList.contains(vertLabel)) {
            System.err.println("Vertex existed!");
            return;
        }
        vertexList.add(vertLabel);
        edgeMap.put(vertLabel, new ArrayList<>());

        /*addToMatrix();
        setMatrix();
        printMatrix();*/
    } // end of addVertex()

    public void addEdge(T srcLabel, T tarLabel) {
        // Implement me!
        if (!existedVertex(srcLabel) || !existedVertex(tarLabel)) {
            System.err.println("One of the vertex is not existed!");
            return;
        }
        ArrayList<T> tempEdgeList = edgeMap.get(srcLabel);
        tempEdgeList.add(tarLabel);
        edgeMap.put(srcLabel, tempEdgeList);

        tempEdgeList = edgeMap.get(tarLabel);
        tempEdgeList.add(srcLabel);
        edgeMap.put(tarLabel, tempEdgeList);
    } // end of addEdge()


    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();
        // Implement me!
        neighbours = edgeMap.get(vertLabel);
        return neighbours;
    } // end of neighbours()

    public void removeVertex(T vertLabel) {
        // Implement me!
        if (!existedVertex(vertLabel)) {
            System.err.println("Vertex doesn't existed!");
            return;
        }

        // remove edge if there is other vertex connect to this vertex
        for (T currentVertex : vertexList) {
            ArrayList<T> tempEdgeList = edgeMap.get(currentVertex);
            if (tempEdgeList.contains(vertLabel)) {
                tempEdgeList.remove(vertLabel);
                edgeMap.put(currentVertex, tempEdgeList);
            }
        }

        vertexList.remove(vertLabel);
        edgeMap.remove(vertLabel);
    } // end of removeVertex()

    public void removeEdge(T srcLabel, T tarLabel) {
        // Implement me!
        if (!existedVertex(srcLabel) || !existedVertex(tarLabel)) {
            System.err.println("One of the vertex doesn't existed!");
            return;
        }

        ArrayList<T> edgeList = edgeMap.get(srcLabel);
        edgeList.remove(tarLabel);
        edgeMap.put(srcLabel, edgeList);

        edgeList = edgeMap.get(tarLabel);
        edgeList.remove(srcLabel);
        edgeMap.put(tarLabel, edgeList);
    } // end of removeEdges()

    public void printVertices(PrintWriter os) {
        // Implement me!
        for (T vertex : vertexList) {
            os.print(vertex);
            os.print(" ");
        }
        os.close();
    } // end of printVertices()

    public void printEdges(PrintWriter os) {
        // Implement me!
        for (int i = 0; i < vertexList.size(); i++) {
            T currentVertex = vertexList.get(i);
            ArrayList<T> tempEdgeList = edgeMap.get(currentVertex);
            if (tempEdgeList.size() <= 0) {
                continue;
            }
            os.print(currentVertex);
            for (int j = 0; j < tempEdgeList.size(); j++) {
                os.print(" " + tempEdgeList.get(j));
            }
            os.println();
        }
        os.close();
    } // end of printEdges()

    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
        // Implement me!
        if (!existedVertex(vertLabel1) || !existedVertex(vertLabel2)) {
            System.err.println("One of the vertex doesn't existed!");
            return disconnectedDist;
        }
        if (isolateVertex(vertLabel1) || isolateVertex(vertLabel2)) {
            return disconnectedDist;
        }

        int shortestPath = 0;
        boolean found = false;
        ArrayList<T> queue = edgeMap.get(vertLabel1);
        //get queue size
        int queueSize = queue.size();
        while (!found) {
            shortestPath++;
            if (queue.size() == 0) {
                break;
            }
            if (queue.contains(vertLabel2)) {
                found = true;
            } else {
                ArrayList<T> subQueue = new ArrayList<>();
                for (T nextVisit : queue) {
                    ArrayList<T> tempQueue = edgeMap.get(nextVisit);
                    subQueue.addAll(tempQueue);
                }
                for (T nextQueueVertex : subQueue) {
                    if (!queue.contains(nextQueueVertex)) {
                        queue.add(nextQueueVertex);
                    }
                }
                /*System.out.println(queueSize);
                System.out.println(queue.size());
                System.out.println("vertex1: " + vertLabel1 + " vertex2: " + vertLabel2 + " " + queue);*/
            }
        }

        // remove edgeInfo for vertex as the list is changed when processing BFS
        for (int i = queue.size() - 1; i >= queueSize; i--) {
            queue.remove(i);
        }

        if (found) {
            return shortestPath;
        }
        // if we reach this point, source and target are disconnected
        return disconnectedDist;
    } // end of shortestPathDistance()

    private boolean existedVertex(T vertLabel) {
        return vertexList.contains(vertLabel);
    }

    private boolean isolateVertex(T vertLabel) {
        ArrayList<T> edgeList = edgeMap.get(vertLabel);
        return edgeList.size() == 0;
    }

    private void addToMatrix() {
        int size = matrix.length;
        matrix = new int[size + 1][size + 1];
    }

    private void setMatrix() {
        int size = matrix.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    private void printMatrix() {
        for (int[] vertex : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(vertex[j]);
            }
            System.out.println();
        }
        System.out.println();
    }

} // end of class AdjMatrix