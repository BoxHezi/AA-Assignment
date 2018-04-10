import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GraphGenerator {
    private static final String facebookFile = "facebook_combined.txt";
    private static final String avFile = "avCommand.in";
    private static final int vertexLimitation = 800;

    public static void main(String[] args) {
        scanFacebookFile();
        Scanner userInput = new Scanner(System.in);
    }

    private static void scanFacebookFile() {
        Scanner readFile = null;
        try {
            readFile = new Scanner(new File(facebookFile));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> vertexList = new ArrayList<>();
        String str;
        String token[];
        int count = 0;
        if (null != readFile) {
            while ((str = readFile.nextLine()) != null) {
                token = str.split(" ");
                String vertex = token[0];
                if (!vertexList.contains(vertex)) {
                    vertexList.add(vertex);
                    count++;
                }
                if (count >= vertexLimitation) {
                    break;
                }
            }
            System.out.println("Vertex Size: " + vertexList.size());
            readFile.close();
        }
        printToFile(avFile, vertexList, "AV");
    }

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
