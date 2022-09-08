import java.io.*;

public class Naloga1 {
	private static int velikost;
	
	private static int[][] vhod(String imeVhodneDatoteke) throws IOException {
		String tmp;
		BufferedReader reader = new BufferedReader(new FileReader(imeVhodneDatoteke));		
		tmp = reader.readLine();
		velikost = Integer.valueOf(tmp);
		int[][] plantaza = new int[velikost][velikost];
		for (int i = 0; i < velikost; i++) {
			tmp = reader.readLine();
			String[] a = tmp.split(",");
			for(int j = 0; j < velikost; j++) {
				plantaza[i][j] = Integer.parseInt(a[j].trim());
			}
		}
		reader.close();
		return plantaza;
	}
	
	private static int najdiPot(int[][] plantaza, boolean[][] path,  int x, int y, int prev) {

		//ce je pozicija izven mape se vrnemo za 1
		if(x < 0 || y < 0 || x == velikost || y == velikost) return Integer.MIN_VALUE;
		//ce je dvig previsok oz. spust preglobok se vrnemo za 1
		if(prev < plantaza[y][x] && plantaza[y][x] - prev > 20) return Integer.MIN_VALUE;
		if(prev > plantaza[y][x] && plantaza[y][x] - prev < -30) return Integer.MIN_VALUE;
		
		//ce smo prispeli na cilj vrnemo ciljni element in koncamo rekurzijo
		if(x == velikost - 1 && y == velikost - 1) {
			if(plantaza[y][x] - prev > 0) return plantaza[y][x] - prev;
			else return 0;
		}
		
		//ce smo tu ze bili se vrnemo za 1
		if(path[y][x]) return Integer.MIN_VALUE;
		
		
		//ce smo prisli do sem je pozicija pravilna in gremo naprej
		path[y][x] = true;
		int current = 0;
		current = najdiPot(plantaza, path, x + 1, y, plantaza[y][x]);
		if(current >= 0) {
			if(plantaza[y][x] - prev >= 0) return current + plantaza[y][x] - prev;
			else return current;
		}
		current = najdiPot(plantaza, path, x - 1, y, plantaza[y][x]);
		if(current >= 0) {
			if(plantaza[y][x] - prev >= 0) return current + plantaza[y][x] - prev;
			else return current;
		}
		current = najdiPot(plantaza, path, x, y + 1, plantaza[y][x]);
		if(current >= 0) {
			if(plantaza[y][x] - prev >= 0) return current + plantaza[y][x] - prev;
			else return current;
		}
		current = najdiPot(plantaza, path, x, y - 1, plantaza[y][x]);
		if(current >= 0) {
			if(plantaza[y][x] - prev >= 0) return current + plantaza[y][x] - prev;
			else return current;
		}
		return Integer.MIN_VALUE;
	}
	
	private static void izpisi(int rezultat, String imeIzhodneDatoteke) throws IOException {
		PrintWriter p = new PrintWriter(new FileWriter(imeIzhodneDatoteke));
		p.println(rezultat);
		p.close();
	}
	
	public static void main(String[] args) throws IOException {
		int[][] plantaza = vhod(args[0]);
		boolean[][] path = new boolean[velikost][velikost];
		for(int i = 0; i < velikost; i++)
			for (int j = 0; j < velikost; j++)
				path[i][j] = false;
		
		int dvig = 0;
		dvig = najdiPot(plantaza, path, 0, 0, 0);
		System.out.println(dvig);
		izpisi(dvig, args[1]);
	}
}
