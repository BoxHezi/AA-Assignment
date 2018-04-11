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
    private static final String RE1_COMMAND_IN = "reH.in";
	private static final String RE2_COMMAND_IN = "reM.in";
	private static final String RE3_COMMAND_IN = "reL.in";
    private static final String S1_COMMAND_IN = "sH.in";
	private static final String S2_COMMAND_IN = "sM.in";
	private static final String S3_COMMAND_IN = "sL.in";
    private static final int vertexLimitation = 800;
    private static final int E1Limitation = 12991;
    private static final int E2Limitation = 6496;
    private static final int E3Limitation = 3248;

    public static void main(String[] args) {
        scanFacebookFile();
        generateAE();
        generateRE();
        generateS();
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
		
    }
	
	public static void generateAE() {
		Scanner readFile = null;

		ArrayList<String> Edges1 = new ArrayList<>();
        ArrayList<String> Edges2 = new ArrayList<>();
        ArrayList<String> Edges3 = new ArrayList<>();
		ArrayList<String> vert = new ArrayList<>();
		
		try {
		    readFile = new Scanner(new File(ORIGIN_DATE_FILE));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		String str;
		String token[];
		int count = 0;
	//        int lineCounter = 0;
		if (null != readFile) {
		    while ((str = readFile.nextLine()) != null) {
	//                lineCounter++;
                token = str.split("\\s");
                String vertex = token[0];
                if (!vert.contains(vertex)) {
                    vert.add(vertex);
                    count++;
                }
                if (count >= vertexLimitation) {
                    break;
                }
		    }
		}
		readFile.close();
		/*uses a randomizer to select random vertices to form edges then prints
		it to the file AE*/ 
		Random rand = new Random();
		int i = 0;
			/*being 12991x2 = 25,982 edges....*/
		while (i != 12991) {
			int n = rand.nextInt(vert.size()) + 0;
			int m = rand.nextInt(vert.size()) + 0;
			if (!Edges1.contains(vert.get(n)+" "+vert.get(m)) && !Edges1.contains(vert.get(m)+" "+vert.get(n))) {
                if (i <= E1Limitation) {
                    Edges1.add(vert.get(n)+" "+vert.get(m));
                }
                if (i <= E2Limitation) {
                    Edges2.add(vert.get(n)+" "+vert.get(m));
                }
                if (i <= E3Limitation) {
                    Edges3.add(vert.get(n)+" "+vert.get(m));
                }
				i++;
			}
		}
		printToFile(AE1_COMMAND_IN,Edges1, "AE");
        printToFile(AE2_COMMAND_IN,Edges2, "AE");
        printToFile(AE3_COMMAND_IN,Edges3, "AE");
	}
    
    
    public static void generateRE() {
        Scanner readFile = null;

		ArrayList<String> REdge1 = new ArrayList<>();
        ArrayList<String> REdge2 = new ArrayList<>();
        ArrayList<String> REdge3 = new ArrayList<>();
		
		try {
		    readFile = new Scanner(new File(AE1_COMMAND_IN));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		String str;
		String token[];
		int count = 0;
	//        int lineCounter = 0;
		if (null != readFile) {
		    while (readFile.hasNextLine()) {
                str = readFile.nextLine();
	//                lineCounter++;
                token = str.split("\\s");
                if (count <= E1Limitation) {
                    REdge1.add(token[1]+" "+token[2]);
                }
                if (count <= E2Limitation) {
                    REdge2.add(token[1]+" "+token[2]);
                }
                if (count <= E3Limitation) {
                    REdge3.add(token[1]+" "+token[2]);
                }
                count++;
		    }
		}
		readFile.close();
        printToFile(RE1_COMMAND_IN,REdge1,"AE");
        printToFile(RE2_COMMAND_IN,REdge2,"AE");
        printToFile(RE3_COMMAND_IN,REdge3,"AE");
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
    private static void generateS() {
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
		}
		readFile.close();
        
        Random rand = new Random();
		int i = 0;
			/*being 12991x2 = 25,982 edges....*/
		while (i != 12991) {
			int n = rand.nextInt(vert.size()) + 0;
			int m = rand.nextInt(vert.size()) + 0;
			if (!Sedge1.contains(vert.get(n)+" "+vert.get(m)) && !Sedge1.contains(vert.get(m)+" "+vert.get(n))) {
                if (i <= E1Limitation) {
                    Sedge1.add(vert.get(n)+" "+vert.get(m));
                }
                if (i <= E2Limitation) {
                    Sedge2.add(vert.get(n)+" "+vert.get(m));
                }
                if (i <= E3Limitation) {
                    Sedge3.add(vert.get(n)+" "+vert.get(m));
                }
				i++;
			}
		}
        printToFile(S1_COMMAND_IN,Sedge1,"S");
        printToFile(S2_COMMAND_IN,Sedge2,"S");
        printToFile(S3_COMMAND_IN,Sedge3,"S");
        
    }
}











