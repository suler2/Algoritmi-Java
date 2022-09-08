import java.io.*;

class CycleListElement {
	int data;
	CycleListElement next;
	
	public CycleListElement(int data) {
		this.data = data;
		this.next = null;
	}
}

/**
	----------		----------		----------		----------
	|  head  | ---> |  curr  | ---> |        | ---> |  last  | ---
	----------		----------		----------		----------    |
						^
						|										  |
						 -----------------------------------------
*/

class CycleList {
	CycleListElement head;
	CycleListElement last;
	CycleListElement curr;
	
	public CycleList() {
		makeNull();
	}
	
	public void makeNull() {
		head = new CycleListElement(-1);
		last = null;
		curr = null;
	}
	
	public void add(int data) {
		CycleListElement e = new CycleListElement(data);
		if(last == null) {
			head.next = e;
			last = e;
			curr = e;
			e.next = e;
		}
		else {
			last.next = e;
			last = e;
			e.next = head.next;
		}
	}
	
	public void setStart() {
		curr = head.next;
	}
	
	public int step() {
		int data = curr.data;
		curr = curr.next;
		return data;
	}
}

class Stranka {
	int id;
	int potrpljenje;
	Stranka next;
	
	public Stranka(int id, int potrpljenje) {
		this.id = id;
		this.potrpljenje = potrpljenje;
		this.next = null;
	}
}

//podatkovna struktura ki simulira cakalnico (podobna PS vrsta)
//prva -> trenutno na frizerskem stolu
//zadnja -> trenutno zadnja v cakalni vrsti
/**
 * 		----------		----------		----------
 * 		| prva   | <--- |        | <--- | zadnja | <---
 *		----------		----------		----------
 */
class Cakalnica {
	Stranka prva;
	Stranka zadnja;
	int countStrank;
	int stStolov;
	int stZasedenih;
	int osnovniCasStrizenja;
	int trenutniCasStrizenja;
	int povecanjeCasaStrizenja;
	String izpisStrank;
	
	public Cakalnica(int stStolov, int osnovniCasStrizenja, int povecanjeCasaStrizenja) {
		this.prva = null;
		this.zadnja = null;
		this.countStrank = 0;
		this.stStolov = stStolov + 1;
		this.stZasedenih = 0;
		this.osnovniCasStrizenja = osnovniCasStrizenja;
		this.trenutniCasStrizenja = osnovniCasStrizenja;
		this.povecanjeCasaStrizenja = povecanjeCasaStrizenja;
		this.izpisStrank = "";
	}
	
	public boolean empty() {
		return (prva == null);
	}
	
	public void dequeue() {
		prva = prva.next;
	}
	
	//ce stranke ni (prva == null) se metoda takoj zakljuci, drugace
	//stranki, ki je na frizerskem stolu zmanjsa cas strizenja za 1
	//ce je cas strizenja enak 0 se strankin id zapise v izpisStrank,
	//stranka odide(dequeue) in nastavi se cas strizenja za naslednjo stranko
	public void opraviStrizenje() {
		if(prva == null) return;
		trenutniCasStrizenja--;
		if(trenutniCasStrizenja <= 0) {
			if(izpisStrank == "") izpisStrank = Integer.toString(prva.id);
			else izpisStrank += "," + Integer.toString(prva.id);
			dequeue();
			stZasedenih--;
			osnovniCasStrizenja += povecanjeCasaStrizenja;
			trenutniCasStrizenja = osnovniCasStrizenja;
		}
	}
	
	//poveca stevec strank za 1
	//ce je st zasedenih stolov manjse od skupnega st stolov, potem doda novo 
	//stranko na zadnji stol v vrsti in poveca st zasedenih stolov za 1
	public void dodajStranko(int potrpljenje) {
		countStrank++;
		if(stZasedenih < stStolov) {
			Stranka s = new Stranka(countStrank, potrpljenje);
			s.next = null;
			if(prva == null) prva = s;
			else zadnja.next = s;
			zadnja = s;
			stZasedenih++;
		}
	}
	
	//vsaki stranki na cakalnem stolu zmanjsa potrpljenje za 1
	//ce je potrpljenje trenutne stranke enako 0 potem stranka odide
	public void osveziPotrpljenje() {
		Stranka current = null;
		Stranka prev = null;
		if(prva != null) {
			prev = prva;
			current = prva.next;
		}
		
		while(current != null) {
			current.potrpljenje--;
			if(current.potrpljenje <= 0) {
				stZasedenih--;
				current = current.next;
				prev.next = current;
			}
			else {
				current = current.next;
				prev = prev.next;
			}
		}
	}
	
	public void izpisi(String out) throws IOException {
		PrintWriter p = new PrintWriter(new FileWriter(out));
		p.println(izpisStrank);
		p.close();
	}
}

public class Naloga4 {
	public static void main(String[] args) {
		try {
			//preberemo vse podatke
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			int t = Integer.parseInt(reader.readLine().trim());
			int n = Integer.parseInt(reader.readLine().trim());
			int s = Integer.parseInt(reader.readLine().trim());
			int k = Integer.parseInt(reader.readLine().trim());
			String la = reader.readLine();
			String lw = reader.readLine();
			reader.close();
			
			//ustvarimo cikclicni strukturi, ki hranita zamike in potrpljenja
			CycleList zamik = new CycleList();
			int start = 0;
			for (int i = 0; i < la.length(); i++) {
				if(la.charAt(i) == ',') {
					zamik.add(Integer.parseInt(la.substring(start, i).trim()));
					start = i + 1;
				}
			}
			zamik.add(Integer.parseInt(la.substring(start, la.length()).trim()));
			start = 0;
			CycleList potrpljenje = new CycleList();
			for (int i = 0; i < lw.length(); i++) {
				if(la.charAt(i) == ',') {
					potrpljenje.add(Integer.parseInt(lw.substring(start, i).trim()));
					start = i + 1;
				}
			}
			potrpljenje.add(Integer.parseInt(lw.substring(start, lw.length()).trim()));
			
			//ustvarimo cakalnico in startamo simulacijo
			//korakPrihodaStranke -> korak v katerem vstopi nova stranka
			Cakalnica cakalnica = new Cakalnica(n, s, k);
			int korakPrihodaStranke = zamik.step();
			for(int i = 1; i <= t; i++) {
				//metoda ki opravi strizenje
				cakalnica.opraviStrizenje();
				//doda stranko ce je to potrebno
				if(korakPrihodaStranke == i) {
					cakalnica.dodajStranko(potrpljenje.step());
					korakPrihodaStranke += zamik.step();
				}
				//osvezi potrpljenje vsem strankam
				cakalnica.osveziPotrpljenje();
			}
			
			cakalnica.izpisi(args[1]);
			//TODO metoda izpisi
		} catch(IOException e) {
			System.out.println("Problem pri vhodni datoteki!\n" + e.getMessage());
		}
	}
}
