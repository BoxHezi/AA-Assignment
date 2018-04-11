import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class GraphGenerator {
    private static final String BASE_GRAPH_FILE = "facebook_combined.txt";
    private static final String HD_TEST_GRAPH = "HD_base_graph.txt";
    private static final String MD_TEST_GRAPH = "MD_base_graph.txt";
    private static final String LD_TEST_GRAPH = "LD_base_graph.txt";
    private static final String AE_SET = "AE_SET.in";
    private static final String AV_SET = "AV_SET.in";
    private static final String RE_SET = "RE_SET.in";
    private static final String RV_SET = "RV_SET.in";
    private static final String N_SET = "N_SET.in";
    private static final String S_SET = "S_SET.in";
    private static final int LD_INDICATOR = 3;
    private static final int VERTEX_LIMIT = 500;
    private static final int DATA_SIZE = 200;

    // store all 500 vertices in this list
    private ArrayList<String> vertexList = new ArrayList<>();
    // need to clean list after generate different density graph
    private ArrayList<String> tempVertexList = new ArrayList<>();
    private ArrayList<String> tempEdgeList = new ArrayList<>();

    public static void main(String[] args) {
        GraphGenerator graphGenerator = new GraphGenerator();
        graphGenerator.getFiveHundredVertex();
        graphGenerator.createGraphHD();
        graphGenerator.createGraphMD();
        graphGenerator.createGraphLD();
        graphGenerator.generateRandAE();
        graphGenerator.generateRandAV();
        graphGenerator.generateRE();
        graphGenerator.generateRV();
        graphGenerator.generateN();
        graphGenerator.generateS();
    }

    private void getFiveHundredVertex() {
        Scanner readFile = null;
        try {
            readFile = new Scanner(new File(BASE_GRAPH_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != readFile) {
            String str;
            String token[];
            while ((str = readFile.nextLine()) != null) {
                if (vertexList.size() >= VERTEX_LIMIT) {
                    break;
                }
                token = str.split("\\s");
                String vertex1 = token[0];
                String vertex2 = token[1];
                if (!vertexList.contains(vertex1)) {
                    vertexList.add(vertex1);
                }
                if (!vertexList.contains(vertex2)) {
                    vertexList.add(vertex2);
                }
            }
            readFile.close();
        }
    }

    private void createGraphHD() {
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
                if (tempVertexList.size() >= VERTEX_LIMIT) {
                    break;
                }
                tempEdgeList.add(edge);
                token = edge.split("\\s");
                String vertex1 = token[0];
                String vertex2 = token[1];
                if (!tempVertexList.contains(vertex1)) {
                    tempVertexList.add(vertex1);
                }
                if (!tempVertexList.contains(vertex2)) {
                    tempVertexList.add(vertex2);
                }
            }
            System.out.println("HD BASE GRAPH GENERATE!");
            readFile.close();
        }
        printToFile(HD_TEST_GRAPH, tempEdgeList);
    }

    private void createGraphMD() {
        Scanner readFile = null;
        try {
            readFile = new Scanner(new File(BASE_GRAPH_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != readFile) {
            String edge;
            String token[];
            boolean addEdge = true;
            while ((edge = readFile.nextLine()) != null) {
                if (tempVertexList.size() >= VERTEX_LIMIT) {
                    break;
                }
                if (addEdge) {
                    tempEdgeList.add(edge);
                }
                addEdge = !addEdge;
                token = edge.split("\\s");
                String vertex1 = token[0];
                String vertex2 = token[1];
                if (!tempVertexList.contains(vertex1)) {
                    tempVertexList.add(vertex1);
                }
                if (!tempVertexList.contains(vertex2)) {
                    tempVertexList.add(vertex2);
                }
            }
            System.out.println("MD BASE GRAPH GENERATE!");
            System.out.println(tempVertexList.size());
            readFile.close();
        }
        printToFile(MD_TEST_GRAPH, tempEdgeList);
    }

    private void createGraphLD() {
        Scanner readFile = null;
        try {
            readFile = new Scanner(new File(BASE_GRAPH_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != readFile) {
            String edge;
            String token[];
            boolean addEdge = true;
            int addEdgeIndicator = LD_INDICATOR;
            while ((edge = readFile.nextLine()) != null) {
                if (tempVertexList.size() >= VERTEX_LIMIT) {
                    break;
                }
                if (addEdge && addEdgeIndicator == LD_INDICATOR) {
                    tempEdgeList.add(edge);
                }
                addEdge = !addEdge;
                addEdgeIndicator--;
                if (addEdgeIndicator <= 0) {
                    addEdgeIndicator = LD_INDICATOR;
                }
                token = edge.split("\\s");
                String vertex1 = token[0];
                String vertex2 = token[1];
                if (!tempVertexList.contains(vertex1)) {
                    tempVertexList.add(vertex1);
                }
                if (!tempVertexList.contains(vertex2)) {
                    tempVertexList.add(vertex2);
                }
            }
            System.out.println("LD BASE GRAPH GENERATE!");
            readFile.close();
        }
        printToFile(LD_TEST_GRAPH, tempEdgeList);
    }

    private void printToFile(String filename, List<String> printElement) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileOutputStream(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != printWriter) {
            for (String e : printElement) {
                printWriter.println(e);
            }
            printWriter.close();
        }
        tempEdgeList = new ArrayList<>();
        tempVertexList = new ArrayList<>();
    }

    /*************************************************************************************/
    private void generateRandAE() {
        /*this doesn't go in generateEdges becasue we want random ones to add*/
        ArrayList<String> edges = new ArrayList<>();
        Random rand = new Random();
        int i = 0;
        while (i <= DATA_SIZE) {
            int n = rand.nextInt(vertexList.size());
            int m = rand.nextInt(vertexList.size());
            if (!vertexList.contains(String.valueOf(n) + " " + String.valueOf(m)) || !vertexList.contains(String.valueOf(m) + " " + String.valueOf(n))) {
                if (!edges.contains(String.valueOf(n) + " " + String.valueOf(m)) || !edges.contains(String.valueOf(m) + " " + String.valueOf(n))) {
                    edges.add(n + " " + m);
                    i++;
                }
            }
        }
        printToFileData(AE_SET, edges, "AE");
    }

    private void generateEdges(List<String> edges) {
        Random rand = new Random();
        int i = 0;
        while (i <= DATA_SIZE) {
            int n = rand.nextInt(vertexList.size());
            int m = rand.nextInt(vertexList.size());
            if (!edges.contains(vertexList.get(n) + " " + vertexList.get(m)) || !edges.contains(vertexList.get(m) + " " + vertexList.get(n))) {
                edges.add(vertexList.get(n) + " " + vertexList.get(m));
                i++;
            }
        }
    }

    private void generateS() {
        ArrayList<String> edges = new ArrayList<>();
        generateEdges(edges);
        printToFileData(S_SET, edges, "S");
    }

    private void generateRE() {
        ArrayList<String> edges = new ArrayList<>();
        generateEdges(edges);
        printToFileData(RE_SET, edges, "RE");
    }

    private void generateRandAV() {
        /*this doesn't go in generateVert becasue we want random ones to add*/
        ArrayList<String> vert = new ArrayList<>();
        Random rand = new Random();
        int i = 0;
        while (i <= DATA_SIZE) {
            int n = rand.nextInt(10000);
            if (!vert.contains(String.valueOf(n)) && !vertexList.contains(String.valueOf(n))) {
                vert.add(String.valueOf(n));
                i++;
            }
        }
        printToFileData(AV_SET, vert, "AV");
    }

    private void generateVert(List<String> vert) {
        Random rand = new Random();
        int i = 0;
        while (i <= DATA_SIZE) {
            int n = rand.nextInt(10000);
            if (vertexList.contains(String.valueOf(n)) && !vert.contains(String.valueOf(n))) {
                vert.add(String.valueOf(n));
                i++;
            }
        }
    }

    private void generateRV() {
        ArrayList<String> vert = new ArrayList<>();
        generateVert(vert);
        printToFileData(RV_SET, vert, "RV");
    }

    private void generateN() {
        ArrayList<String> vert = new ArrayList<>();
        generateVert(vert);
        printToFileData(N_SET, vert, "N");
    }

    private void printToFileData(String filename, List<String> printElement, String command) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileOutputStream(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != printWriter) {
            for (String e : printElement) {
                printWriter.println(command + " " + e);
            }
            System.out.println(command + " file generated!");
            printWriter.close();
        }
    }

}
