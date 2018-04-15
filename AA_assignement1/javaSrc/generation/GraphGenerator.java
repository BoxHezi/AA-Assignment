package generation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class GraphGenerator {
    private static final String BASE_GRAPH_FILE = "facebook_combined.txt";
    /*different density graphs to generate*/
    private static final String HD_TEST_GRAPH = "HD_base_graph.txt";
    private static final String MD_TEST_GRAPH = "MD_base_graph.txt";
    private static final String LD_TEST_GRAPH = "LD_base_graph.txt";
    /*different files required to generate for the different operations for the 3 density graphs*/
    private static final String AV_SET = "AV_SET.in";
    private static final String AE_SET = "AE_SET.in";
    private static final String AEmd_SET = "AEmd_SET.in";
    private static final String AEhd_SET = "AEhd_SET.in";
    private static final String RE_SET = "RE_SET.in";
    private static final String REmd_SET = "REmd_SET.in";
    private static final String REhd_SET = "REhd_SET.in";
    private static final String RV_SET = "RV_SET.in";
    private static final String RVmd_SET = "RVmd_SET.in";
    private static final String RVhd_SET = "RVhd_SET.in";
    private static final String N_SET = "N_SET.in";
    private static final String Nmd_SET = "Nmd_SET.in";
    private static final String Nhd_SET = "Nhd_SET.in";
    private static final String S_SET = "S_SET.in";
    private static final String Smd_SET = "Smd_SET.in";
    private static final String Shd_SET = "Shd_SET.in";
    /*used to generate the low density graph*/
    private static final int LD_INDICATOR = 3;
    /*declaring the size of the facebook graph we want in terms of vertices*/
    private static final int VERTEX_LIMIT = 500;
    /*declaring data size for each of the operation*/
    private static final int DATA_SIZE = 200;

    // store all 500 vertices in this list
    private ArrayList<String> vertexList = new ArrayList<>();
    /* stores all vertices and edges to be used to calculate appropriate operations*/
    private ArrayList<String> GlobalLD = new ArrayList<>();
    private ArrayList<String> GlobalLDvert = new ArrayList<>();
    private ArrayList<String> GlobalMD = new ArrayList<>();
    private ArrayList<String> GlobalMDvert = new ArrayList<>();
    private ArrayList<String> GlobalHD = new ArrayList<>();
    private ArrayList<String> GlobalHDvert = new ArrayList<>();

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

    /*reads the facebook graph and uses it as a base graph to gain the first few edges that contain 500 unique vertices*/
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

    /*generates the high density graph using the 500 vertices from reading the first 500 vertices*/
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
                GlobalHD.add(edge);
                token = edge.split("\\s");
                String vertex1 = token[0];
                String vertex2 = token[1];
                if (!tempVertexList.contains(vertex1)) {
                    tempVertexList.add(vertex1);
                    GlobalHDvert.add(vertex1);
                }
                if (!tempVertexList.contains(vertex2)) {
                    tempVertexList.add(vertex2);
                    GlobalHDvert.add(vertex2);
                }
            }
            System.out.println("HD BASE GRAPH GENERATE!");
            readFile.close();
        }
        printToFile(HD_TEST_GRAPH, tempEdgeList);
    }

    /*creates a medium density graph based off the base file*/
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
                    GlobalMD.add(edge);
                    token = edge.split("\\s");
                    String vertex1 = token[0];
                    String vertex2 = token[1];
                    if (!tempVertexList.contains(vertex1)) {
                        tempVertexList.add(vertex1);
                        GlobalMDvert.add(vertex1);
                    }
                    if (!tempVertexList.contains(vertex2)) {
                        tempVertexList.add(vertex2);
                        GlobalMDvert.add(vertex2);
                    }
                }
                addEdge = !addEdge;
            }
            System.out.println("MD BASE GRAPH GENERATE!");
            readFile.close();
        }
        printToFile(MD_TEST_GRAPH, tempEdgeList);
    }

    /*creates the low density graph based off the base graph but uses a limiter*/
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
                    GlobalLD.add(edge);
                    token = edge.split("\\s");
                    String vertex1 = token[0];
                    String vertex2 = token[1];
                    if (!tempVertexList.contains(vertex1)) {
                        tempVertexList.add(vertex1);
                        GlobalLDvert.add(vertex1);
                    }
                    if (!tempVertexList.contains(vertex2)) {
                        tempVertexList.add(vertex2);
                        GlobalLDvert.add(vertex2);
                    }
                }
                addEdge = !addEdge;
                addEdgeIndicator--;
                if (addEdgeIndicator <= 0) {
                    addEdgeIndicator = LD_INDICATOR;
                }
            }
            System.out.println("LD BASE GRAPH GENERATE!");
            readFile.close();
        }
        printToFile(LD_TEST_GRAPH, tempEdgeList);
    }

    /*prints the grpah data to their respective density file*/
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
    /*generating operations*/
    
    /*generating random numbers that exist in the base graphs to add new edges, the funciton creates
    the data for the three different density graphs*/
    private void generateRandAE() {
        ArrayList<String> edges = new ArrayList<>();
        ArrayList<String> edges2 = new ArrayList<>();
        ArrayList<String> edges3 = new ArrayList<>();
        Random rand = new Random();
        int counter = 0;
        while (counter < DATA_SIZE) {
            int n = rand.nextInt(GlobalLDvert.size());
            int m = rand.nextInt(GlobalLDvert.size());
            if (!GlobalLD.contains(GlobalLDvert.get(n) + " " + GlobalLDvert.get(m)) || !GlobalLD.contains(GlobalLDvert.get(m) + " " + GlobalLDvert.get(n))) {
                if (!edges.contains(GlobalLDvert.get(n) + " " + GlobalLDvert.get(m)) || !edges.contains(GlobalLDvert.get(m) + " " + GlobalLDvert.get(n))) {
                    edges.add(GlobalLDvert.get(n) + " " + GlobalLDvert.get(m));
                    counter++;
                }
            }
        }
        int counter2 = 0;
        while (counter2 < DATA_SIZE) {
            int n = rand.nextInt(GlobalMDvert.size());
            int m = rand.nextInt(GlobalMDvert.size());
            if (!GlobalMD.contains(GlobalMDvert.get(n) + " " + GlobalMDvert.get(m)) || !GlobalMD.contains(GlobalMDvert.get(m) + " " + GlobalMDvert.get(n))) {
                if (!edges2.contains(GlobalMDvert.get(n) + " " + GlobalMDvert.get(m)) || !edges2.contains(GlobalMDvert.get(m) + " " + GlobalMDvert.get(n))) {
                    edges2.add(GlobalMDvert.get(n) + " " + GlobalMDvert.get(m));
                    counter2++;
                }
            }
        }
        int counter3 = 0;
        while (counter3 < DATA_SIZE) {
            int n = rand.nextInt(GlobalHDvert.size());
            int m = rand.nextInt(GlobalHDvert.size());
            if (!GlobalHD.contains(GlobalHDvert.get(n) + " " + GlobalHDvert.get(m)) || !GlobalHD.contains(GlobalHDvert.get(m) + " " + GlobalHDvert.get(n))) {
                if (!edges3.contains(GlobalMDvert.get(n) + " " + GlobalMDvert.get(m)) || !edges3.contains(GlobalMDvert.get(m) + " " + GlobalMDvert.get(n))) {
                    edges3.add(GlobalHDvert.get(n) + " " + GlobalHDvert.get(m));
                    counter3++;
                }
            }
        }
        printToFileData(AE_SET, edges, "AE");
        printToFileData(AEmd_SET, edges2, "AE");
        printToFileData(AEhd_SET, edges3, "AE");
    }

    /*generating random numbers that exist in the base graphs to calculate the shortest path,
    the funciton creates the data for the three different density graphs*/
    private void generateS() {
        ArrayList<String> edges = new ArrayList<>();
        ArrayList<String> edges2 = new ArrayList<>();
        ArrayList<String> edges3 = new ArrayList<>();
        Random rand = new Random();
        int i = 0;
        int counter = 0;
        while (i < DATA_SIZE) {
            int n = rand.nextInt(GlobalLDvert.size());
            int m = rand.nextInt(GlobalLDvert.size());
            if (!edges.contains(GlobalLDvert.get(n) + " " + GlobalLDvert.get(m)) || !edges.contains(GlobalLDvert.get(m) + " " + GlobalLDvert.get(n)) && !edges.contains(GlobalMDvert.get(n) + " " + GlobalMDvert.get(m)) || !edges.contains(GlobalMDvert.get(m) + " " + GlobalMDvert.get(n))) {
                if (!edges.contains(GlobalLDvert.get(n) + " " + GlobalLDvert.get(m)) || !edges.contains(GlobalLDvert.get(m) + " " + GlobalLDvert.get(n))) {
                    edges.add(GlobalLDvert.get(n) + " " + GlobalLDvert.get(m));
                }
                if (!edges.contains(GlobalMDvert.get(n) + " " + GlobalMDvert.get(m)) || !edges.contains(GlobalMDvert.get(m) + " " + GlobalMDvert.get(n))) {
                    edges2.add(GlobalMDvert.get(n) + " " + GlobalMDvert.get(m));
                }
                if (!edges.contains(GlobalHDvert.get(n) + " " + GlobalHDvert.get(m)) || !edges.contains(GlobalHDvert.get(m) + " " + GlobalHDvert.get(n))) {
                    edges3.add(GlobalHDvert.get(n) + " " + GlobalHDvert.get(m));
                }
                i++;
            }
        }
        printToFileData(S_SET, edges, "S");
        printToFileData(Smd_SET, edges2, "S");
        printToFileData(Shd_SET, edges3, "S");
    }

    /*generating random numbers that exist in the base graphs to remove edges, the funciton creates
    the data for the three different density graphs*/
    private void generateRE() {
        ArrayList<String> edges = new ArrayList<>();
        ArrayList<String> edges2 = new ArrayList<>();
        ArrayList<String> edges3 = new ArrayList<>();
        Random rand = new Random();
        int counter = 0;
        while (counter < DATA_SIZE) {
            int n = rand.nextInt(GlobalLDvert.size());
            if (!edges.contains(GlobalLD.get(n))) {
                if (!edges.contains(GlobalLD.get(n))) {
                    edges.add(GlobalLD.get(n));
                    counter++;
                }
            }
        }
        int counter2 = 0;
        while (counter2 < DATA_SIZE) {
            int n = rand.nextInt(GlobalLDvert.size());
            if (!edges2.contains(GlobalMD.get(n))) {
                if (!edges2.contains(GlobalMD.get(n))) {
                    edges2.add(GlobalMD.get(n));
                    counter2++;
                }
            }
        }
        int counter3 = 0;
        while (counter3 < DATA_SIZE) {
            int n = rand.nextInt(GlobalHDvert.size());
            if (!edges3.contains(GlobalHD.get(n))) {
                if (!edges3.contains(GlobalHD.get(n))) {
                    edges3.add(GlobalHD.get(n));
                    counter3++;
                }
            }
        }
        printToFileData(RE_SET, edges, "RE");
        printToFileData(REmd_SET, edges2, "RE");
        printToFileData(REhd_SET, edges3, "RE");
    }

    /*generating random numbers that don't exist in the base graphs. It's using the
    base graph so then it doesn't need to create 3 files for each density*/
    private void generateRandAV() {
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

    /*generating random numbers that exist in the base graphs to remove vertices, the funciton creates
    the data for the three different density graphs*/
    private void generateRV() {
        ArrayList<String> vert = new ArrayList<>();
        ArrayList<String> vert2 = new ArrayList<>();
        ArrayList<String> vert3 = new ArrayList<>();
        Random rand = new Random();
        int i = 0;
        while (i < DATA_SIZE) {
            int n = rand.nextInt(10000);
            if (GlobalLDvert.contains(String.valueOf(n)) && !vert.contains(String.valueOf(n))) {
                if (GlobalLDvert.contains(String.valueOf(n))) {
                    vert.add(String.valueOf(n));
                    i++;
                }
            }
        }
        int j = 0;
        while (j < DATA_SIZE) {
            int n = rand.nextInt(10000);
            if (!vert2.contains(String.valueOf(n)) && GlobalMDvert.contains(String.valueOf(n))) {
                if (GlobalMDvert.contains(String.valueOf(n))) {
                    vert2.add(String.valueOf(n));
                    j++;
                }
            }
        }
        int z = 0;
        while (z < DATA_SIZE) {
            int n = rand.nextInt(10000);
            if (!vert3.contains(String.valueOf(n)) && GlobalHDvert.contains(String.valueOf(n))) {
                if (GlobalHDvert.contains(String.valueOf(n))) {
                    vert3.add(String.valueOf(n));
                    z++;
                }
            }
        }
        printToFileData(RV_SET, vert, "RV");
        printToFileData(RVmd_SET, vert2, "RV");
        printToFileData(RVhd_SET, vert3, "RV");
    }

    /*generating random numbers that exist in the base graph to find neighbours, the funciton creates
    the data for the three different density graphs*/
    private void generateN() {
        ArrayList<String> vert = new ArrayList<>();
        ArrayList<String> vert2 = new ArrayList<>();
        ArrayList<String> vert3 = new ArrayList<>();
        Random rand = new Random();
        int i = 0;
        while (i < DATA_SIZE) {
            int n = rand.nextInt(10000);
            if (GlobalLDvert.contains(String.valueOf(n)) && !vert.contains(String.valueOf(n))) {
                if (GlobalLDvert.contains(String.valueOf(n))) {
                    vert.add(String.valueOf(n));
                    i++;
                }
            }
        }
        int j = 0;
        while (j < DATA_SIZE) {
            int n = rand.nextInt(10000);
            if (!vert2.contains(String.valueOf(n)) && GlobalMDvert.contains(String.valueOf(n))) {
                if (GlobalMDvert.contains(String.valueOf(n))) {
                    vert2.add(String.valueOf(n));
                    j++;
                }
            }
        }
        int z = 0;
        while (z < DATA_SIZE) {
            int n = rand.nextInt(10000);
            if (!vert3.contains(String.valueOf(n)) && GlobalHDvert.contains(String.valueOf(n))) {
                if (GlobalHDvert.contains(String.valueOf(n))) {
                    vert3.add(String.valueOf(n));
                    z++;
                }
            }
        }
        printToFileData(N_SET, vert, "N");
        printToFileData(Nmd_SET, vert2, "N");
        printToFileData(Nhd_SET, vert3, "N");
    }

    /*prints the operation to a file*/
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
