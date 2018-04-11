import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

public class GraphGenerator {
    private static final String ORIGIN_DATE_FILE = "facebook_combined.txt";
    private static final String AV_COMMAND_IN = "av.in";
    /*
     *AE1 has a density of 0.0406 and an edge count of 12991x2 = 25,982 edges
     */
    private static final String AE_HD_COMMAND_IN = "aeH.in";
    private static final String AE_MD_COMMAND_IN = "aeM.in";
    private static final String AE_LD_COMMAND_IN = "aeL.in";
    private static final String RE_HD_COMMAND_IN = "reH.in";
    private static final String RE_MD_COMMAND_IN = "reM.in";
    private static final String RE_LD_COMMAND_IN = "reL.in";
    private static final String S1_COMMAND_IN = "sH.in";
    private static final String S2_COMMAND_IN = "sM.in";
    private static final String S3_COMMAND_IN = "sL.in";
    private static final int VERTEX_LIMIT = 800;
    private static final int HD_LIMIT = 12991;
    private static final int MD_LIMIT = 6496;
    private static final int LD_LIMIT = 3248;

    private ArrayList<String> vertexList = new ArrayList<>();

    public static void main(String[] args) {
        GraphGenerator graphGenerator = new GraphGenerator();
        graphGenerator.generateAV();
        graphGenerator.generateRV();
        graphGenerator.generateAE();
        graphGenerator.generateRE();
        graphGenerator.generateS();
        graphGenerator.generateN();
    }

    private void generateRV() {

    }

    private void generateN() {

    }

    /**
     * Scan the first 800 vertices from the given facebook_combined.txt file
     */
    private void generateAV() {
        Scanner readFile = null;
        try {
            readFile = new Scanner(new File(ORIGIN_DATE_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != readFile) {
            String str;
            String[] token;
            int count = 0;
            while ((str = readFile.nextLine()) != null) {
                token = str.split("\\s");
                String vertex = token[0];
                if (!vertexList.contains(vertex)) {
                    vertexList.add(vertex);
                    count++;
                }
                if (count >= VERTEX_LIMIT) {
                    break;
                }
            }
            System.out.println("Vertex Size: " + vertexList.size());
            readFile.close();
        }
        // output a AV command file
        printToFile(AV_COMMAND_IN, vertexList, "AV");

    }

    private void generateAE() {
        ArrayList<String> edgeList = new ArrayList<>();
        String AE_COMMAND = "AE";

		/*uses a randomizer to select random vertices to form edges then prints
		it to the file AE*/
        Random rand = new Random();
        int i = 0;
        /*being 12991x2 = 25,982 edges....*/
        while (i <= 12991) {
            int n = rand.nextInt(vertexList.size());
            int m = rand.nextInt(vertexList.size());
            if (!edgeList.contains(vertexList.get(n) + " " + vertexList.get(m)) && !edgeList.contains(vertexList.get(m) + " " + vertexList.get(n))) {
                edgeList.add(vertexList.get(n) + " " + vertexList.get(m));
                i++;
                if (i == HD_LIMIT) {
                    printToFile(AE_HD_COMMAND_IN, edgeList, AE_COMMAND);
                }
                if (i == MD_LIMIT) {
                    printToFile(AE_MD_COMMAND_IN, edgeList, AE_COMMAND);
                }
                if (i == LD_LIMIT) {
                    printToFile(AE_LD_COMMAND_IN, edgeList, AE_COMMAND);
                }
            }
        }
    }

    private void generateRE() {
        Scanner readFile = null;

        ArrayList<String> REdge1 = new ArrayList<>();
        ArrayList<String> REdge2 = new ArrayList<>();
        ArrayList<String> REdge3 = new ArrayList<>();

        try {
            readFile = new Scanner(new File(AE_HD_COMMAND_IN));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str;
        String token[];
        int count = 0;
        if (null != readFile) {
            while (readFile.hasNextLine()) {
                str = readFile.nextLine();
                token = str.split("\\s");
                if (count <= HD_LIMIT) {
                    REdge1.add(token[1] + " " + token[2]);
                }
                if (count <= MD_LIMIT) {
                    REdge2.add(token[1] + " " + token[2]);
                }
                if (count <= LD_LIMIT) {
                    REdge3.add(token[1] + " " + token[2]);
                }
                count++;
            }
            readFile.close();
        }
        printToFile(RE_HD_COMMAND_IN, REdge1, "AE");
        printToFile(RE_MD_COMMAND_IN, REdge2, "AE");
        printToFile(RE_LD_COMMAND_IN, REdge3, "AE");
    }

    /**
     * @param fileName     output file name
     * @param printElement list of command argument
     * @param command      command AV, AE, RV, RE, etc
     */
    private void printToFile(String fileName, List<String> printElement, String command) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileOutputStream(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != printWriter) {
            for (String element : printElement) {
                printWriter.println(command + " " + element);
            }
            printWriter.close();
        }
    }

    private void generateS() {
        Scanner readFile = null;
        /*error it doesn't get random vertices to use S*/
        ArrayList<String> vert = new ArrayList<>();
        ArrayList<String> Sedge1 = new ArrayList<>();
        ArrayList<String> Sedge2 = new ArrayList<>();
        ArrayList<String> Sedge3 = new ArrayList<>();
        try {
            readFile = new Scanner(new File(AV_COMMAND_IN));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str;
        String token[];
        //        int lineCounter = 0;
        if (null != readFile) {
            while (readFile.hasNextLine()) {
                str = readFile.nextLine();
                //                lineCounter++;
                token = str.split("\\s");
                vert.add(token[1]);
            }
            readFile.close();
        }

        Random rand = new Random();
        int i = 0;
        /*being 12991x2 = 25,982 edges....*/
        while (i != 12991) {
            int n = rand.nextInt(vert.size());
            int m = rand.nextInt(vert.size());
            if (!Sedge1.contains(vert.get(n) + " " + vert.get(m)) && !Sedge1.contains(vert.get(m) + " " + vert.get(n))) {
                if (i <= HD_LIMIT) {
                    Sedge1.add(vert.get(n) + " " + vert.get(m));
                }
                if (i <= MD_LIMIT) {
                    Sedge2.add(vert.get(n) + " " + vert.get(m));
                }
                if (i <= LD_LIMIT) {
                    Sedge3.add(vert.get(n) + " " + vert.get(m));
                }
                i++;
            }
        }
        printToFile(S1_COMMAND_IN, Sedge1, "S");
        printToFile(S2_COMMAND_IN, Sedge2, "S");
        printToFile(S3_COMMAND_IN, Sedge3, "S");
    }
}











