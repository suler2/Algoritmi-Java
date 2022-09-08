import java.io.*;

class ClenStrukture {
	Object[] elementi;
	int stPolnih;
	int size;
	ClenStrukture next;
	
	public ClenStrukture(int N) {
		this.elementi = new Object[N];
		this.size = N;
		this.stPolnih = 0;
		this.next = null;
	}
	
	public void dodajElement(int v, int p) {
		if(elementi[p] != null) 
			premakniDesno(p);
		elementi[p] = v;
		stPolnih++;
	}
	
	private void premakniDesno(int p) {
		for (int i = size - 1; i > p; i--) {
			elementi[i] = elementi[i - 1];
		}
	}
	
	/**
	//TODO zdruzi dodajElement in premakniDesno
	public void dodajElement(int v, int p) {
		if(stPolnih < size) {
			elementi[p] = v;
			stPolnih++;
		}
		else System.out.println("Prevec elementov!");
	}
	
	public void premakniDesno(int pos) {
		for (int i = size - 1; i > pos; i--) {
			elementi[i] = elementi[i - 1];
		}
	}
	*/
	
	public void izbrisiElement(int p) {
		premakniLevo(p);
		stPolnih--;
	}
	
	private void premakniLevo(int p) {
		for(int i = p; i < size - 1; i++) 
			elementi[i] = elementi[i + 1];
		elementi[size - 1] = null;
	} 
	
	/**
	//TODO optimiziraj (brez elementi[pos] = null...
	public void izbrisiElement(int pos) {
		elementi[pos] = null;
		if(elementi[pos + 1] != null) {
			while(pos <= size - 2) {
				elementi[pos] = elementi[pos + 1];
				pos++;
			}
			elementi[pos] = null;
		}
		stPolnih--;
	}
	*/
	public boolean jePoln() {
		return (stPolnih == size);
	}
	
	public Object vrniElement(int index) {
		return elementi[index];
	}
	
	public int vrniStElementov() {
		return stPolnih;
	}
}

class Struktura {
	ClenStrukture head;
	int stClenov;
	int size;
	
	public Struktura() {
		init(5);
	}
	
	public void init(int N) {
		this.size = N;
		head = new ClenStrukture(N);
		stClenov = 1;
	}
	
	public boolean insert(int v, int p) {
		ClenStrukture trenutni = head;
		
		//Gremo cez vse clene, da ugotovimo kam (ce sploh) sodi nov el.
		while(trenutni != null) {
			//ce ima clen ustrezno st. elementov (if) potem preverimo, kam ga vstaviti
			//ce clen nima ustrezno st. elementov (else) potem gremo na naslednji el.
			if(p == 0 || (p < size && trenutni.vrniElement(p - 1) != null)) {
				//ce je pozicija p prazna potem preprosto vstavimo (if)
				//ce je pozicija p zapolnjena potem (else)
				if(trenutni.elementi[p] == null) {
					trenutni.dodajElement(v, p);
				}
				else {
					//ce je v trenutnem clenu se dovolj prostora, potem zamaknemo
					//elemente od p naprej za 1 v desno in dodamo v na pozicijo p (if)
					//ce v trenutnem clenu ni prostora, potem klicemo metodo razdeli (else)
					if(trenutni.vrniStElementov() < size) {
//						trenutni.premakniDesno(p);
						trenutni.dodajElement(v, p);
					}
					else {
						razdeli(trenutni, v, p);
					}
				}
				//element smo uspesno vstavili, vrnemo true
				return true;
			}
			else {
				p -= trenutni.vrniStElementov();
				trenutni = trenutni.next;
			}
		}
		
		//ce smo prisli cez vse clene in se vedno nimamo dovolj elementov
		//da bi lahko vstavili v, potem vrnemo false
		return false;
	}
	
	//ustvari nov clen, razdeli elemente na pol (zaokrozeno navzdol)
	//nato doda element na ustrezno mesto
	
	public void razdeli(ClenStrukture prvi, int v, int p) {
		//ustvarimo element in ga pravilno vkljucimo
		ClenStrukture tretji = prvi.next;
		ClenStrukture drugi = new ClenStrukture(size);
		prvi.next = drugi;
		drugi.next = tretji;
		
		//prenesemo odvecne elemente iz clena 1 v clen 2
		int ix = size/2;
		int j = 0;
//		for (int i = (size / 2); i < size; i++) {
		while(prvi.elementi[ix] != null) {
			drugi.dodajElement((int)prvi.elementi[ix], j);
			j++;
			prvi.izbrisiElement(ix);
		}
		
		//damo nov element na ustrezno mesto
		//postopek je podoben kot pri vstavljanju elementa v metodi insert
		ClenStrukture trenutni = prvi;
		for (int i = 0; i < 2; i++) {
			if(p == 0 || (p < size && trenutni.elementi[p - 1] != null)) {
				if(trenutni.elementi[p] == null) {
					trenutni.dodajElement(v, p);
					break;
				}
				else {
//					trenutni.premakniDesno(p);
					trenutni.dodajElement(v, p);
					break;
				}
			}
			else {
				p -= trenutni.vrniStElementov();
				trenutni = trenutni.next;
			}
		}
		stClenov++;
	}

	//TODO ..
	
	public boolean remove(int p) {
		ClenStrukture trenutni = head;
		
		while(trenutni != null) {
			//ce je p ustrezen in je pozicija polna potem (if)
			//drugace gremo v naslednji clen (else)
			if(p < size && trenutni.elementi[p] != null) {
				//izbrisemo element, ce je st elementov v novem clenu
				//manjse od size/2, zdruzimo clena v metodi zdruzi()
				trenutni.izbrisiElement(p);
				if(trenutni.vrniStElementov() < (size / 2)) {
					zdruzi(trenutni);
				}
				return true;
			}
			else {
				p -= trenutni.vrniStElementov();
				trenutni = trenutni.next;
			}
		}
		return false;
	}
	
	public void zdruzi(ClenStrukture prvi) {
		ClenStrukture drugi = prvi.next;
		if(drugi == null) return;
		else {
			prvi.dodajElement((int)drugi.vrniElement(0), prvi.vrniStElementov());
			drugi.izbrisiElement(0);
			if(drugi.vrniStElementov() < (size / 2)) {
				
				int i = prvi.vrniStElementov();
				while(drugi.elementi[0] != null) {
					prvi.dodajElement((int)drugi.elementi[0], i);
					drugi.izbrisiElement(0);
					i++;
//					System.out.println("HEREQ");
				}
				prvi.next = drugi.next;
				stClenov--;
			}	
		}
	}
	
	//TODO ...
	public void izpisi(String dir) throws IOException {
		
		ClenStrukture trenutni = head;
		PrintWriter p = new PrintWriter(new FileWriter(dir));
		p.println(stClenov);
		while(trenutni != null) {
			for (int i = 0; i < size; i++) {
				if(trenutni.vrniElement(i) != null) 
					p.print(trenutni.vrniElement(i));
				else p.print("NULL");
				if(i < size - 1) p.print(",");
			}
			p.println();
			trenutni = trenutni.next;
		}
		p.close();
	}
	
	/**
	public void izpisi() {
		ClenStrukture trenutni = head;
		int ct = 0;
		while(trenutni != null) {
			ct++;
			trenutni = trenutni.next;
		}
		System.out.println(ct);
		trenutni = head;
		while(trenutni != null) {
			for (int i = 0; i < size; i++) {
				if(trenutni.elementi[i] != null) System.out.print(trenutni.elementi[i]); 
				else System.out.print("NULL");
				if(i < size - 1) System.out.print(",");
			}
			System.out.println();
			trenutni = trenutni.next;
		}
	}
	*/
}

public class Naloga3 {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(args[0]));
		int k = Integer.parseInt(reader.readLine().trim());
		Struktura struktura = new Struktura();
		for (int i = 0; i < k; i++) {
			String vrstica = reader.readLine();
			if(vrstica.charAt(0) == 's') {
				struktura.init(Integer.parseInt(vrstica.substring(2)));
			}
			else if(vrstica.charAt(0) == 'i') {
				int j = 3;
				while(vrstica.charAt(j) != ',') j++;
				struktura.insert(Integer.parseInt(vrstica.substring(2, j)), 
								 Integer.parseInt(vrstica.substring(j + 1)));
			}
			else if(vrstica.charAt(0) == 'r') {
				struktura.remove(Integer.parseInt(vrstica.substring(2)));
			}
			else System.out.println("NAPAKA PRI BRANJU!");
		}
		reader.close();
			
		//TODO read metoda
		struktura.izpisi(args[1]);
	}
}
