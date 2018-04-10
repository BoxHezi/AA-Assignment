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
	private static final String AE1_COMMAND_IN = "aeH.in";
	private static final String AE2_COMMAND_IN = "aeM.in";
	private static final String AE3_COMMAND_IN = "aeL.in";
    private static final int vertexLimitation = 800;

    public static void main(String[] args) {
        scanFacebookFile();
    }

    /**
     * Scan the first 800 vertices from the given facebook_combined.txt file
     */
    private static void scanFacebookFile() {
        Scanner readFile = null;
        try {
            readFile = new Scanner(new File(ORIGIN_DATE_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> vertexList = new ArrayList<>();
        String str;
        String token[];
        int count = 0;
//        int lineCounter = 0;
        if (null != readFile) {
            while ((str = readFile.nextLine()) != null) {
//                lineCounter++;
                token = str.split("\\s");
                String vertex = token[0];
                if (!vertexList.contains(vertex)) {
                    vertexList.add(vertex);
                    count++;
                }
                if (count >= vertexLimitation) {
                    break;
                }
            }
//            System.out.println("lineCounter: " + lineCounter);
            System.out.println("Vertex Size: " + vertexList.size());
            readFile.close();
        }
        // output a AV command file
        printToFile(AV_COMMAND_IN, vertexList, "AV");
		printToFileAE(AE1_COMMAND_IN, vertexList, "AE");
    }

    /**
     * @param fileName output file name
     * @param printElement list of command argument
     * @param command command AV, AE, RV, RE, etc
     */
    private static void printToFile(String fileName, List<String> printElement, String command) {
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
	
	/**
     * @param fileName output file name
     * @param printElement list of command argument
     * @param command command AV, AE, RV, RE, etc
	 *
     */
	private static void printToFileAE(String fileName, List<String> vert, String command) {
        PrintWriter printWriter = null;
		ArrayList<String> Edges = new ArrayList<>();
		
		try {
            printWriter = new PrintWriter(new FileOutputStream(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
		/*uses a randomizer to select random vertices to form edges then prints
		it to the file AE*/ 
		Random rand = new Random();
        int i = 0;
		/*being 12991x2 = 25,982 edges....*/
        while (i != 12991) {
        	int n = rand.nextInt(vert.size()) + 0;
			int m = rand.nextInt(vert.size()) + 0;
			if (!Edges.contains(vert.get(n)+" "+vert.get(m)) && !Edges.contains(vert.get(m)+" "+vert.get(n))) {
				Edges.add(vert.get(n)+" "+vert.get(m));
                printWriter.println(command + " " + Edges.get(i));
				printWriter.println(command + " " + vert.get(m)+" "+vert.get(n));
                i++;
			}
        }
		printWriter.close();
    }
	
	private static void printToFileRE(String fileName, List<String> Edges, String command) {
        //TODO
	}
}