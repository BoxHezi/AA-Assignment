import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GraphGenerator {
    private static final String BASE_GRAPH_FILE = "facebook_combined.txt";
    private static final String BASE_TEST_GRAPH = "bash_graph.txt";
    private static final int HD_INDICATOR = 1;
    private static final int MD_INDICATOR = 2;
    private static final int LD_INDICATOR = 3;
    private static final int VERTEX_LIMIT = 500;

    private ArrayList<String> vertexList = new ArrayList<>();
    private ArrayList<String> edgeList = new ArrayList<>();

    public static void main(String[] args) {
        GraphGenerator graphGenerator = new GraphGenerator();
        graphGenerator.generateBaseGraph();
    }

    private void generateBaseGraph() {
        Scanner readFile = null;
        try {
            readFile = new Scanner(new File(BASE_GRAPH_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != readFile) {
            String edge;
            String[] token;
            while ((edge = readFile.nextLine()) != null) {
                if (vertexList.size() >= VERTEX_LIMIT) {
                    break;
                }
                edgeList.add(edge);
                token = edge.split("\\s");
                String vertex = token[0];
                if (!vertexList.contains(vertex)) {
                    vertexList.add(vertex);
                }
            }
            System.out.println("BASE GRAPH GENERATE");
            readFile.close();
        }
        printToFile(BASE_TEST_GRAPH, edgeList);
    }

    private void createGraphHD() {

    }

    private void printToFile(String filename, List<String> printElement) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileOutputStream(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != printWriter) {
            for(String e : printElement) {
                printWriter.println(e);
            }
        }
    }
}
