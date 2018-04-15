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
    private ArrayList<T> vertex;
    private Integer[][] matrix;

    /**
     * Contructs empty graph.
     */
    public AdjMatrix() {
        // Implement me!
        vertex = new ArrayList<>();
        matrix = new Integer[vertex.size()][vertex.size()];
        for (int i = 0; i < vertex.size(); i++) {
            for (int j = 0; j < vertex.size(); j++) {
                matrix[i][j] = 0;
            }
        }
    } // end of AdjMatrix()

    public void addVertex(T vertLabel) {
        // Implement me!
        if (vertex.contains(vertLabel)) {
            System.err.println("Vertex existed!");
            return;
        }
        System.out.println("Add vertex: " + vertLabel);
        vertex.add(vertLabel);

        addToMatrix();
    } // end of addVertex()

    private void addToMatrix() {
        int newMatrixSize = matrix.length + 1;
        Integer[][] tempMatrix = new Integer[newMatrixSize][newMatrixSize];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                tempMatrix[i][j] = matrix[i][j];
            }
        }
        tempMatrix = setDefaultMatrixValue(tempMatrix);
        matrix = tempMatrix;
    }

    private Integer[][] setDefaultMatrixValue(Integer[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (null == matrix[i][j]) {
                    matrix[i][j] = 0;
                }
            }
        }
        return matrix;
    }

    public void addEdge(T srcLabel, T tarLabel) {
        // Implement me!
        if (!existVertex(srcLabel) || !existVertex(tarLabel)) {
            System.err.println("One of the vertex doesn't existed!");
            return;
        }
        int srcIndex = findVertexIndex(srcLabel);
        int tarIndex = findVertexIndex(tarLabel);

        // set both direction in the matrix graph
        matrix[srcIndex][tarIndex] = 1;
        matrix[tarIndex][srcIndex] = 1;
    } // end of addEdge()

    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<>();
        // Implement me!
        if (!existVertex(vertLabel)) {
            System.err.println("Vertex " + vertLabel + " doesn't existed!");
            return neighbours;
        }
        int vertIndex = findVertexIndex(vertLabel);
        for (int i = 0; i < vertex.size(); i++) {
            if (matrix[vertIndex][i] == 1) {
                neighbours.add(vertex.get(i));
            }
        }

        return neighbours;
    } // end of neighbours()

    public void removeVertex(T vertLabel) {
        // Implement me!
        if (!existVertex(vertLabel)) {
            System.err.println("Vertex doesn't existed!");
            return;
        }

        int removeVertexIndex = findVertexIndex(vertLabel);
        Integer[][] newMatrix = new Integer[vertex.size() - 1][vertex.size() - 1];

        // move the vertex need to be removed at the end of every array
        // therefore, when declare new array, just ignore the last item of each array
        for (int i = 0; i < matrix.length; i++) {
            if (i >= removeVertexIndex) {
                if (i >= matrix.length - 1) {
                    break;
                }
                Integer[] tempArray = matrix[i];
                matrix[i] = matrix[i + 1];
                matrix[i + 1] = tempArray;
            }
            for (int j = 0; j < matrix[i].length; j++) {
                if (j >= removeVertexIndex) {
                    if (j >= matrix[i].length - 1) {
                        break;
                    }
                    Integer tempInt = matrix[i][j];
                    matrix[i][j] = matrix[i][j + 1];
                    matrix[i][j + 1] = tempInt;
                }
            }
        }
        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[i].length; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }

        matrix = newMatrix;
        vertex.remove(vertLabel);
    } // end of removeVertex()

    public void removeEdge(T srcLabel, T tarLabel) {
        // Implement me!
        if (!existVertex(srcLabel) || !existVertex(tarLabel)) {
            System.err.println("One of the vertex doesn't existed!");
            return;
        }
        int srcIndex = findVertexIndex(srcLabel);
        int tarIndex = findVertexIndex(tarLabel);

        // set both direction in the matrix graph
        matrix[srcIndex][tarIndex] = 0;
        matrix[tarIndex][srcIndex] = 0;
    } // end of removeEdges()

    public void printVertices(PrintWriter os) {
        // Implement me!
        for (T currVertex : vertex) {
            os.print(currVertex + " ");
        }
        os.println();
        os.flush();
    } // end of printVertices()

    public void printEdges(PrintWriter os) {
        // Implement me!
        for (int i = 0; i < matrix.length; i++) {
            ArrayList<Integer> printList = new ArrayList<>();
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    printList.add(j);
                }
            }
            // skip if the vertex doesn't have any edges
            if (printList.size() <= 0) {
                continue;
            }
            os.print(vertex.get(i));
            for (Integer printIndex : printList) {
                os.print(" " + vertex.get(printIndex));
            }
            os.println();
        }
        os.flush();
    } // end of printEdges()

    public int shortestPathDistance(T vertLabel1, T vertLabel2) {
        // Implement me!
        if (!existVertex(vertLabel1) || !existVertex(vertLabel2)) {
            System.err.println("One of the vertex doesn't existed!");
            return disconnectedDist;
        }

        boolean found = false;
        int shortestPath = 0;

        ArrayList<T> visitedList = new ArrayList<>();
        ArrayList<T> queue = new ArrayList<>();
        ArrayList<T> nextQueue = new ArrayList<>();
        int currVertIndex = findVertexIndex(vertLabel1);

        // get the fisrt queue by using vertLabel1 passed in
        for (int i = 0; i < matrix[currVertIndex].length; i++) {
            if (matrix[currVertIndex][i] == 1) {
                queue.add(vertex.get(i));
            }
        }
        while (!found) {
            shortestPath++;
            if (queue.size() == 0) {
                break;
            } else {
                if (queue.contains(vertLabel2)) {
                    found = true;
                } else {
                    for (int j = 0; j < queue.size() && j < matrix.length; j++) {
                        if (!visitedList.contains(queue.get(j))) {
                            visitedList.add(queue.get(j));
                        }
                        for (int x = 0; x < matrix[j].length; x++) {
                            if (matrix[j][x] == 1 && !visitedList.contains(vertex.get(x))) {
                                nextQueue.add(vertex.get(x));
                            }
                        }
                    }
                    // reset queue and add new queue into it
                    queue = clearQueue();
                    queue.addAll(nextQueue);
                    nextQueue = clearQueue();
                }
            }
        }

        if (found) {
            return shortestPath;
        }
        // if we reach this point, source and target are disconnected
        return disconnectedDist;
    } // end of shortestPathDistance()

    private int findVertexIndex(T tarVertex) {
        for (int i = 0; i < vertex.size(); i++) {
            if (vertex.get(i).equals(tarVertex)) {
                return i;
            }
        }
        // return -1 if vertex is not existed
        // it is not likely to return -1 as existence will be check before this method is called
        return -1;
    }

    // check if a vertex existed or not
    private boolean existVertex(T vertLabel) {
        return vertex.contains(vertLabel);
    }

    private ArrayList<T> clearQueue() {
        return new ArrayList<>();
    }
} // end of class AdjMatrix
