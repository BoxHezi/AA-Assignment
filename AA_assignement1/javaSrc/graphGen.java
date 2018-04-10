import java.io.*;
import java.util.*;

public class graphGen
{
	/*calculate the density in a sheet then have a density of 1.0-high 0.5- medium  0.1-low*/
	public static void main(String args[]) {
    	generateAV();
		generateAE();
	}
	public static void generateAV() {
		ArrayList<String> unique = new ArrayList<>();
		
		try {
			BufferedReader in = new BufferedReader (new FileReader("facebook_combined.txt")); 
			BufferedWriter out = new BufferedWriter(new FileWriter("DataSetAV2.txt"));
			String[] token;
			String str;
			while ((str = in.readLine()) != null) {
				token = str.split(" ");
				/*System.out.println(token[0]);
				System.out.println(token[1]);*/
				/*make a randomizer function that doesn't overlap*/
				if (!unique.contains(token[0])) {
					unique.add(token[0]);
					out.write("AV "+token[0]+"\n");
				}
				if (!unique.contains(token[1])) {
					unique.add(token[1]);
					out.write("AV "+token[1]+"\n");
				}
			}
			System.out.println("vertex amount:"+unique.size());
			out.close();
			in.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}
	
	public static void generateAE() {
		try {
			BufferedReader in = new BufferedReader (new FileReader("facebook_combined.txt")); 
			BufferedWriter out = new BufferedWriter(new FileWriter("DataSetAE1.txt"));
			String str;
			while ((str = in.readLine()) != null) {
				/*System.out.println(token[0]);
				System.out.println(token[1]);*/
				out.write("AE "+str+"\n");
			}
			out.close();
			in.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}
}