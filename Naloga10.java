import java.io.*;

//dijunktne podmnozice
class Podmnozica {
	Tocka tocka;
	Podmnozica parent;
	int stTock;
	Podmnozica next;
	
	public Podmnozica(Tocka tocka) {
		this.tocka = tocka;
		this.parent = null;
		this.stTock = 1;
		this.next = null;
	}
}

class DisjunktnaMnozica {
	Podmnozica first;
	
	public DisjunktnaMnozica() {
		makenull();
	}
	
	public void makenull() {
		first = null;
	}
	
	public void makeSet(Tocka t) {
		Podmnozica nova = new Podmnozica(t);
		nova.parent = nova;
		t.podmnozica = nova;
		if(first == null) first = nova;
		else {
			nova.next = first;
			first = nova;
		}
	}
	
	public void union(Podmnozica a, Podmnozica b) {
		Podmnozica p1 = find(a);
		Podmnozica p2 = find(b);
		if(p1.tocka.id < p2.tocka.id) {
			p2.parent = p1;
			p1.stTock += p2.stTock;
		}
		else {
			p1.parent = p2;
			p2.stTock += p1.stTock;
		}
	}
	
	public Podmnozica find(Podmnozica x) {
		if(x == x.parent) return x;
		else {
			x.parent = find(x.parent);
			return x.parent;
		}
	}
}

class Heap {
	int size;
	Razdalja[] tabela;
	int stZadnjega;
	
	public Heap(int size) {
		this.size = size;
		this.tabela = new Razdalja[this.size];
		this.stZadnjega = 0;
	}

	public boolean empty() {
		return (stZadnjega == 0);
	}
	
	public void insert(Razdalja nova) {
		int ix = stZadnjega;
		tabela[ix] = nova;
		stZadnjega++;
		while(ix != 0 && tabela[(ix - 1) / 2].razdalja > tabela[ix].razdalja) {
			Razdalja tmp = tabela[ix];
			tabela[ix] = tabela[(ix - 1) / 2];
			tabela[(ix - 1) / 2] = tmp;
			ix = (ix - 1) / 2;
		}
	}
	
	public Razdalja deleteMin() {
		if(tabela[0] == null) return null;
		Razdalja ret = tabela[0];
		tabela[0] = tabela[stZadnjega - 1];
		tabela[stZadnjega - 1] = null;
		stZadnjega--;
		int ix = 0;
		while(true) {
			if(tabela[2 * ix + 1] == null) break;
			else if(tabela[ix].razdalja < tabela[2 * ix + 1].razdalja &&
					tabela[2 * ix + 2] == null) break;
			else if(tabela[ix].razdalja < tabela[2 * ix + 1].razdalja &&
					tabela[ix].razdalja < tabela[2 * ix + 2].razdalja) break;
			else {
				if((tabela[2 * ix + 2] == null ||
					tabela[2 * ix + 1].razdalja <= tabela[2 * ix + 2].razdalja) &&
					tabela[ix].razdalja > tabela[2 * ix + 1].razdalja) {
					Razdalja tmp = tabela[2 * ix + 1];
					tabela[2 * ix + 1] = tabela[ix];
					tabela[ix] = tmp;
					ix = 2 * ix + 1;
				}
				else if(tabela[2 * ix + 2].razdalja < tabela[2 * ix + 1].razdalja &&
						tabela[ix].razdalja > tabela[2 * ix + 2].razdalja) {
					Razdalja tmp = tabela[2 * ix + 2];
					tabela[2 * ix + 2] = tabela[ix];
					tabela[ix] = tmp;
					ix = 2 * ix + 2;
				}
			}
		}
		return ret;
	}
}

//vertex
class Tocka {
	int id;
	double x;
	double y;
	Tocka next;
	
	Podmnozica podmnozica;
	
	boolean izpisana;
	
	public Tocka(int id, double x, double y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.next = null;
		this.podmnozica = null;
		this.izpisana = false;
	}
}

//edge
class Razdalja {
	Tocka t1;
	Tocka t2;
	double razdalja;
	Razdalja next;
	
	boolean porabljena;
	
	public Razdalja(Tocka t1, Tocka t2, double razdalja) {
		this.t1 = t1;
		this.t2 = t2;
		this.razdalja = razdalja;
		this.next = null;
		this.porabljena = false;
	}
}

class Graf {
	Tocka firstT;
	Tocka lastT;
	
	Razdalja firstR;
	
	Heap heap;
	
	public Graf(int heapsize) {
		makenull(heapsize);
	}
	
	public void makenull(int heapsize) {
		firstT = null;
		lastT = null;
		firstR = null;
		heap = new Heap(heapsize);
	}
	
	public void insertTocka(Tocka nova) {
		if(firstT == null) {
			firstT = nova;
			lastT = nova;
		}
		else {
			lastT.next = nova;
			lastT = lastT.next;
			
			Tocka current = firstT;
			while(current != lastT) {
				heap.insert(new Razdalja(current, nova, razdalja(current, nova)));
				current = current.next;
			}
		}
	}
	
	public double razdalja(Tocka a, Tocka b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}
}

public class Naloga10 {
	
	public static void main(String[] args) throws IOException {
		//in
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		int n = Integer.parseInt(br.readLine());
		
		Graf graf = new Graf(n * (n - 1));
		DisjunktnaMnozica skupine = new DisjunktnaMnozica();
		
		for(int i = 0; i < n; i++) {
			String[] in = br.readLine().split(",");
			Tocka current = new Tocka(i + 1, Double.parseDouble(in[0]), Double.parseDouble(in[1]));
			graf.insertTocka(current);
			skupine.makeSet(current);
		}
		int res = Integer.parseInt(br.readLine());
		br.close();
		
		//main
		Razdalja najmanjsaR = graf.heap.deleteMin();
		while(najmanjsaR != null && res < n) {
			Tocka t1 = najmanjsaR.t1;
			Tocka t2 = najmanjsaR.t2;
			
			Podmnozica p1 = skupine.find(t1.podmnozica);
			Podmnozica p2 = skupine.find(t2.podmnozica);
			if(p1 != p2) {
				skupine.union(p1, p2);
				najmanjsaR.porabljena = true;
				res++;
			}
			najmanjsaR = graf.heap.deleteMin();
		}
		
		//out
		PrintWriter p = new PrintWriter(new FileWriter(args[1]));
		Tocka outer = graf.firstT;
		while(outer != null) {
			if(outer.izpisana) {
				outer = outer.next;
				continue;
			}
			String line = "";
			line += outer.id + ",";
			Tocka inner = outer.next;
			while(inner != null) {
				if(skupine.find(inner.podmnozica) == skupine.find(outer.podmnozica)) {
					inner.izpisana = true;
					line += inner.id + ",";
				}
				inner = inner.next;
			}
			p.println(line.substring(0, line.length() - 1));
			outer = outer.next;
		}
		p.close();
	}
}