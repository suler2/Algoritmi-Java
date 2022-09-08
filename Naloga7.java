import java.io.*;

class QueueElement {
	Object element;
	QueueElement next;
	
	public QueueElement(Object element) {
		this.element = element;
		this.next = null;
	}
}

class Queue {
	QueueElement first;
	QueueElement last;
	
	public Queue() {
		makenull();
	}
	
	public void makenull() {
		first = null;
		last = null;
	}
	
	public boolean empty() {
		return (first == null);
	}
	
	public Object front() {
		return first.element;
	}
	
	public void dequeue() {
		first = first.next;
		if(first == null) last = null;
	}
	
	public void enqueue(Object obj) {
		QueueElement nov = new QueueElement(obj);
		if(first == null) first = nov;
		else last.next = nov;
		last = nov;
	}
}


class PQElement {
	Postaja element;
	PQElement next;
	boolean priority;
	
	public PQElement(Postaja element, boolean priority) {
		this.element = element;
		this.next = null; 
		this.priority = priority;
	}
}

class PriorityQueue {
	PQElement first;
	PQElement last;
	
	public PriorityQueue() {
		makenull();
	}
	
	public void makenull() {
		first = null;
	}
	
	public void insert(Postaja obj, boolean priority) {
		//ce je ze v Q
		PQElement current = exists(obj);
		if(current != null) {
			if(priority && !current.priority) {
				current.priority = priority;
				if(current != first) {
					PQElement tmp = first;
					while(tmp.next != current) tmp = tmp.next;
					tmp.next = current.next;
					current.next = first;
					first = current;
					if(current == last) last = tmp;
				}
			}
			current.priority = priority;
		}
		//ce se ni v Q
		else {
			PQElement nov = new PQElement(obj, priority);
			if(first == null) {
				first = nov;
				last = nov;
			}
			else {
				if(priority) {
					nov.next = first;
					first = nov;
				}
				else {
					last.next = nov;
					last = last.next;
				}
			}
		}
	}
	
	public PQElement exists(Postaja obj) {
		PQElement current = first;
		while(current != null) {
			if(obj.id == current.element.id) {
				return current;
			}
			current = current.next;
		}
		return null;
	}
	
	public Object deleteMin() {
		//ce je prazno return null
		if(first == null) return null;
		//ce ni elementa s priority potem promotamo trenutne elemente
		//(deluje kot decrease key, key je lahko priority true or false)
		if(!first.priority) decreaseKey();
		//vrnemo prvi element
		Object tmp = first.element;
		if(first == last) last = null;
		first = first.next;
		return tmp;
	}
	
	private void decreaseKey() {
		PQElement current = first;
		while(current != null) {
			current.priority = true;
			Postaja currentP = (Postaja) current.element;
			currentP.stPrestopov++;
			current = current.next;
		}
	}
	
	public boolean empty() {
		return (first == null);
	}
}

//vertex
class Postaja {
	int id;
	Povezava firstPovezava;
	Postaja next;
	
	int razdaljaOdZacetka;
	boolean obiskana;
	int prejsnjaLinija;
	int stPrestopov;
	int razdaljaPrestopi;
	
	public Postaja(int id) {
		this.id = id;
		this.firstPovezava = null;
		this.next = null;
		this.razdaljaOdZacetka = 0;
		this.obiskana = false;
		this.prejsnjaLinija = 0;
		this.stPrestopov = 0;
		this.razdaljaPrestopi = 0;
	}
}

//edge
class Povezava {
	int linija;
	Postaja endPostaja;
	Povezava next;
	int stPrestopov;
	
	public Povezava(int linija, Postaja endPostaja, Povezava firstPovezava) {
		this.linija = linija;
		this.endPostaja = endPostaja;
		this.next = firstPovezava;
		this.stPrestopov = stPrestopov;
	}
}

class Graf {
	Postaja first;
	Postaja last;
	
	public Graf() {
		first = null;
		last = null;
	}
	
	public Postaja dobiPostajo(int id) {
		Postaja current = first;
		while(current != null) {
			if(current.id == id) return current;
			current = current.next;
		}
		return null;
	}
	
	public void dodajPostajo(Postaja nova) {
		if(first == null) {
			first = nova;
			last = nova;
		}
		else {
			Postaja current = first;
			while(current != null) {
				if(nova.id == current.id) return;
				current = current.next;
			}
			last.next = nova;
			last = last.next;
		}
	}
	
	public void dodajPovezavo(int linija, Postaja start, Postaja end) {
		Povezava nova = new Povezava(linija, end, start.firstPovezava);
		start.firstPovezava = nova;
	}
}

public class Naloga7 {
	public static void main(String[] args) throws IOException {
		long start = System.nanoTime();
		
		Graf graf = new Graf();
		
		//in
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		int n = Integer.parseInt(br.readLine());
		
		for(int i = 0; i < n; i++) {
			String[] line = br.readLine().split(",");
			Postaja[] postaje = new Postaja[line.length];
			for(int j = 0; j < line.length; j++) {
				postaje[j] = new Postaja(Integer.parseInt(line[j]));
				graf.dodajPostajo(postaje[j]);
				if(j != 0) {
					graf.dodajPovezavo(i + 1, graf.dobiPostajo(postaje[j - 1].id), graf.dobiPostajo(postaje[j].id));
					graf.dodajPovezavo(i + 1, graf.dobiPostajo(postaje[j].id), graf.dobiPostajo(postaje[j - 1].id));
				}
			}
		}
		
		int a = 0; int b = 0;
		String[] lastLine = br.readLine().split(",");
		a = Integer.parseInt(lastLine[0]);
		b = Integer.parseInt(lastLine[1]);
		br.close();
		
		//main
		Postaja zacetna = graf.dobiPostajo(a);
		Postaja koncna = graf.dobiPostajo(b);
		Queue queue = new Queue();
		queue.enqueue(zacetna);
		
		while(!queue.empty()) {
			Postaja currentPostaja = (Postaja) queue.front();
			queue.dequeue();
			Povezava currentPovezava = currentPostaja.firstPovezava;
			while(currentPovezava != null) {
				if(!currentPovezava.endPostaja.obiskana) {
					Postaja endPostaja = currentPovezava.endPostaja;
					endPostaja.razdaljaOdZacetka = currentPostaja.razdaljaOdZacetka + 1;
					endPostaja.obiskana = true;
					queue.enqueue(endPostaja);
				}
				currentPovezava = currentPovezava.next;
			}
		}
		
		Postaja reset = graf.first;
		while(reset != null) {
			reset.obiskana = false;
			reset = reset.next;
		}

		PriorityQueue pq = new PriorityQueue();
		pq.insert(zacetna, true);
		
		while(!pq.empty()) {
			Postaja currentPostaja = (Postaja) pq.deleteMin();
			if(currentPostaja == koncna) break;
			if(currentPostaja.obiskana) continue;
			Povezava currentPovezava = currentPostaja.firstPovezava;
			while(currentPovezava != null) {
				Postaja endPostaja = currentPovezava.endPostaja;
//				endPostaja.razdaljaPrestopi = currentPostaja.razdaljaPrestopi + 1;
				if(currentPostaja.id == zacetna.id ||
				   	currentPostaja.prejsnjaLinija == currentPovezava.linija) {
					endPostaja.prejsnjaLinija = currentPovezava.linija;
					endPostaja.stPrestopov = currentPostaja.stPrestopov;
					pq.insert(endPostaja, true);
				}
				else {
					if(pq.exists(endPostaja) == null) {
						endPostaja.prejsnjaLinija = currentPovezava.linija;
						endPostaja.stPrestopov = currentPostaja.stPrestopov;
						pq.insert(endPostaja, false);
					}
				}
				currentPovezava = currentPovezava.next;
			}
			currentPostaja.obiskana = true;
		}
		
		
		if(koncna.razdaljaOdZacetka == 0) System.out.println("-1");
		else System.out.println(koncna.stPrestopov);
		if(koncna.razdaljaOdZacetka == 0) System.out.println("-1");
		else System.out.println(koncna.razdaljaOdZacetka);
		
		//out
		PrintWriter p = new PrintWriter(args[1]);
		if(koncna.razdaljaOdZacetka == 0) {
			p.println("-1");
			p.println("-1");
			p.println("-1");
		}
		else {
			p.println(koncna.stPrestopov);
			p.println(koncna.razdaljaOdZacetka);
			if(koncna.razdaljaOdZacetka == koncna.razdaljaPrestopi)
				p.println("1");
			else p.println("0");
		}
		p.close();
		
		long end = System.nanoTime();
		
		System.out.println("Time: " + (end - start) / 1000000);
	}
}