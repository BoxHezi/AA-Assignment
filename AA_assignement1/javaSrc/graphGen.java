import java.io.*;
import java.util.*;

public class graphGen
{
	/*calculate the density in a sheet then have a density of 1.0-high 0.5- medium  0.1-low*/
	public static void main(String args[]) {
		generatefacebook();
    	generateAV();
		generateAE();
	}
	
	public static void generatefacebook() {
		ArrayList<String> unique = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader (new FileReader("facebook_combined.txt")); 
			BufferedWriter out = new BufferedWriter(new FileWriter("facebook_combined_20k1.txt"));
			String str;
			String token[];
			int i = 0;
			while ((str = in.readLine()) != null) {
				/*System.out.println(token[0]);
				System.out.println(token[1]);*/
				/*make a randomizer function that doesn't overlap*/
				token = str.split(" ");
				/*System.out.println(token[0]);
				System.out.println(token[1]);*/
				/*make a randomizer function that doesn't overlap*/
				if (!unique.contains(token[0])) {
					unique.add(token[0]);
				}
				if (!unique.contains(token[1])) {
					unique.add(token[1]);
				}
				out.write(str+"\n");
				out.write(token[1]+" "+token[0]+"\n");
				/*large*/ 
				if (i == 20000) {
					break;
				}
				i++;
			}
			
			System.out.print("facebook size: "+unique.size());
			/*this will produce the random numbers needed to amke the added vertices*/
			out.close();
			in.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		
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
				}
				if (!unique.contains(token[1])) {
					unique.add(token[1]);
				}
			}
			/*this will produce the random numbers needed to amke the added vertices*/
			Random rand = new Random();
			int i=0;
			while(i != 400) {
				int  n = rand.nextInt(10000)+4000;
				if (!unique.contains(String.valueOf(n))) {
					unique.add(String.valueOf(n));
					out.write("AV "+String.valueOf(n)+"\n");
					i++;
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
		ArrayList<String> aeList = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader (new FileReader("facebook_combined.txt")); 
			BufferedWriter out = new BufferedWriter(new FileWriter("DataSetAE1.txt"));
			String str;
			String token[];
			while ((str = in.readLine()) != null) {
				/*System.out.println(token[0]);
				System.out.println(token[1]);*/
				token = str.split(" ");
				
				if (!aeList.contains(token[0]+" "+token[1]) && !aeList.contains(token[1]+" "+token[0])) {
					aeList.add(token[0]+" "+token[1]);
					aeList.add(token[1]+" "+token[0]);
					out.write("AE "+str+"\n");
				}
			}
			System.out.println("AE size: "+aeList.size());
			out.close();
			in.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}
}