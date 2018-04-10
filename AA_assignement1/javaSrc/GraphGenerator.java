import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GraphGenerator {
    private static final String ORIGIN_DATE_FILE = "facebook_combined.txt";
    private static final String AV_COMMAND_IN = "av.in";
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
}
