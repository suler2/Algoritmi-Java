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
	QueueElement first = null;
	QueueElement last = null;
	
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

class Povezava {
	Mesto start;
	Mesto end;
	Povezava next;
	int value;
	
	public Povezava(Mesto start, Mesto end, Povezava firstP) {
		this.start = start;
		this.end = end;
		this.next = firstP;
		this.value = 0;
	}
}

class Mesto {
	int id;
	Mesto next;
	Povezava firstP;
	
	int distance;
	int prevId;
	Povezava from;
	
	public Mesto(int id) {
		this.id = id;
		this.distance = Integer.MAX_VALUE;
		this.next = null;
		this.firstP = null;
		this.prevId = 0;
		this.from = null;
	}
}

class Graf {
	Mesto firstM;
	
	
	public Graf() {
		makenull();
	}
	
	public void makenull() {
		firstM = null;
	}
	
	public void insert(Mesto m1, Mesto m2) {
		m1 = insertMesto(m1);
		m2 = insertMesto(m2);
		insertPovezava(m1, m2);
		insertPovezava(m2, m1);
	}
	
	private Mesto insertMesto(Mesto m) {
		Mesto current = firstM;
		while(current != null) {
			if(m.id == current.id) return current;
			current = current.next;
		}
		m.next = firstM;
		firstM = m;
		return m;
	}
	
	private void insertPovezava(Mesto m1, Mesto m2) {
		Povezava nova = new Povezava(m1, m2, m1.firstP);
		m1.firstP = nova;
	}
	
	public Mesto findMesto(int id) {
		Mesto current = firstM;
		while(current != null) {
			if(current.id == id) return current;
			current = current.next;
		}
		return null;
	}
	
	public void cleanDistance() {
		Mesto current = firstM;
		while(current != null) {
			current.distance = Integer.MAX_VALUE;
			current.prevId = 0;
			current.from = null;
			current = current.next;
		}
	}
}

public class Naloga9 {
	public static void main(String[] args) throws IOException {
		
		Graf graf = new Graf();
		
		//in
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		String[] l1 = br.readLine().split(",");
		int n = Integer.parseInt(l1[0]);
		int m = Integer.parseInt(l1[1]);
		for(int i = 0; i < n; i++) {
			l1 = br.readLine().split(",");
			graf.insert(new Mesto(Integer.parseInt(l1[0])), new Mesto(Integer.parseInt(l1[1])));
		}
		//main
		for(int i = 0; i < m; i++) {
			String[] l2 = br.readLine().split(",");
			Mesto end = graf.findMesto(Integer.parseInt(l2[0]));
			Mesto start = graf.findMesto(Integer.parseInt(l2[1]));
			int value = Integer.parseInt(l2[2]);
			
			start.distance = 0;
			Queue queue = new Queue();
			queue.enqueue(start);
			
			Mesto currentM;
			Povezava currentP;
			
			while(!queue.empty()) {
				currentM = (Mesto) queue.front();
				queue.dequeue();
				currentP = currentM.firstP;
				while(currentP != null) {
					Mesto startMesto = currentM;
					Mesto endMesto = currentP.end;
					if(endMesto.distance - startMesto.distance == 1) {
						if(endMesto.prevId > startMesto.id) {
							endMesto.from = currentP;
							endMesto.prevId = currentM.id;
						}
					}
					else if(endMesto.distance == Integer.MAX_VALUE) {
							endMesto.distance = startMesto.distance + 1;
							endMesto.from = currentP;
							endMesto.prevId = currentM.id;
							queue.enqueue(endMesto);
					}
					else {}
					
					currentP = currentP.next;
				}
				if(currentM.distance >= end.distance) break;
			}
			
			currentM = end;			
			do {
				currentP = currentM.from;
				
				if(currentM.id < currentM.prevId) {
					currentP.value += value;
					Povezava np = currentM.firstP;
					while(np.end.id != currentM.prevId) np = np.next;
					np.value += value;
				}
				else {
					currentP.value += value;
				}
				currentM = currentP.start;
			} while (currentM != start);
						
			graf.cleanDistance();
		}
		br.close();
		
		Povezava[] max = new Povezava[1000];
		
		max[0] = graf.firstM.firstP;
		Mesto currentM = graf.firstM;
		while(currentM != null) {
			Povezava currentP = currentM.firstP;
			while(currentP != null) {
				if(currentP.value > max[0].value) {
					for(int i = 0; i < max.length; i++) 
						max[i] = null;
					max[0] = currentP;
				}
				else if(currentP.value == max[0].value) {
					add(currentP, max, 0);
				}
				currentP = currentP.next;
			}
			currentM = currentM.next;
		}
		
		//out
		PrintWriter p = new PrintWriter(new FileWriter(args[1]));
		int ix = 0;
		while(max[ix] != null) {
			if(max[ix].start.id < max[ix].end.id)
				p.println(max[ix].start.id + "," + max[ix].end.id);
			ix++;
		}
		p.close();
	}
	
	public static void add(Povezava currentP, Povezava[] max, int ix) {
		if(currentP != null && max[ix] == null) {
			max[ix] = currentP;
		}
		else {
			int id1 = Math.min(currentP.start.id, currentP.end.id);
			int id2 = Math.min(max[ix].start.id, max[ix].end.id);
			if(id1 < id2) {
				Povezava tmp = max[ix];
				max[ix] = currentP;
				add(tmp, max, ix + 1);
			}
			else if(id1 == id2) {
				int id3 = Math.max(currentP.start.id, currentP.end.id);
				int id4 = Math.max(max[0].start.id, max[0].end.id);
				if(id3 > id4) {
					Povezava tmp = max[ix];
					max[ix] = currentP;
					add(tmp, max, ix + 1);
				}
				else {
					add(currentP, max, ix + 1);
				}
			}
			else {
				add(currentP, max, ix + 1);
			}
		}
	}
}