import java.io.*;

//resi se z preslikovalno tabelo, hashi itd...
//s preslikovalno tabelo, key je trenutna situacija
//value je pa resitev trenutne situacije

public class Naloga5 {
	public static int igra(int k, int[] kupi, boolean prvi int poteza) {
		if() {
			
		}
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(args[0]));
		int k = Integer.parseInt(reader.readLine().trim());
		int[] kupi = new int[k];
		for (int i = 0; i < k; i++) {
			kupi[i] = Integer.parseInt(reader.readLine().trim());
		}
		System.out.println(igra(k, kupi, true, 0));
		reader.close();
	}
}
