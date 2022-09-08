import java.io.*;

//Sklad
class StackElement {
	Object element;
	StackElement next;
	
	public StackElement() {
		element = null;
		next = null;
	}
}

class Stack {
	private StackElement top;
	
	public Stack() {
		makenull();
	}
	
	public void makenull() {
		top = null;
	}
	
	public boolean empty() {
		return (top == null);
	}
	
	public Object top() {
		return top.element;
	}
	
	public void push(Object obj) {
		StackElement e = new StackElement();
		e.element = obj;
		e.next = top;
		top = e;
	}
	
	public void pop() {
		top = top.next;
	}
}

//Vrsta
class QueueElement {
	Object element;
	QueueElement next;

	QueueElement() {
		element = null;
		next = null;
	}
}

class Queue {
	private QueueElement front;
	private QueueElement rear;
	
	public Queue() {
		makenull();
	}
	
	public void makenull() {
		front = null;
		rear = null;
	}
	
	public boolean empty() {
		return (front == null);
	}
	
	public Object front() {
		return front.element;
	}
	
	public void enqueue(Object obj) {
		QueueElement e = new QueueElement();
		e.element = obj;
		if(front == null) {
			front = e;
		}
		else rear.next = e;
		rear = e;
	}
	
	public void swap() {
		QueueElement e = front;	
		while(e != null && e.next != null) {
			Object data = e.element;
			e.element = e.next.element;
			e.next.element = data;
			e = e.next.next;
		}
	}
	
	public void dequeue() {
		front = front.next;
	}
}


public class Naloga2 {
/**
*	S to metodo beremo odstavke (vsak readLine predstavlja en odstavek).
*	Odstavke sproti urejamo, nato jih damo na sklad ce so lihi oz. v vrsto
*	ce so sodi. Ko preberemo in uredimo vse odstavke jih zlozimo v pravilen
* 	vrstni red po principu QueueEl -> StackEl -> QueueEl -> StackEl -> ....
*	Dobljeno besedilo predstavlja rezultat, ki ga potem outputamo.
*/
	public static String zamenjajOdstavke(String in) throws IOException {
		String odstavek = "";
		Queue sodi = new Queue();
		Stack lihi = new Stack();
		int stOdstavkov = 1;
		
		BufferedReader reader = new BufferedReader(new FileReader(in));
		while((odstavek = reader.readLine()) != null) {
			odstavek = urediOdstavek(odstavek);
			if(stOdstavkov % 2 != 0) lihi.push(odstavek);
			else sodi.enqueue(odstavek);
			stOdstavkov++;
		}
		reader.close();
		
		//zdruzi lihe in sode odstavke
		int count = 1;
		String besedilo = "";
		while(count < stOdstavkov) {
			if(count % 2 != 0) {
				besedilo += (String)lihi.top();
				lihi.pop();
			}
			else {
				besedilo += (String)sodi.front();
				sodi.dequeue();
			}
			if(count < stOdstavkov - 1) besedilo += '\n';
			count++;
		}
		return besedilo;
	}
	
/**
*	Besedi zamenjamo tako, da najprej zlozimo odstavek besedo po besedo
*	na stack. Potem jemljemo dve besedi naenkrat in jih swap-amo, nato jih
*	damo se enkrat na sklad. Pri tem pazimo na izjemo, ko se poved zacne in
*	konca!. Zacetek/konec povedi ureja metoda urediKonecPovedi(). To je edini 
*	nacin, s katerim sem uspel pravilno swapat besede, tudi ce je *morda* 
*	nepotrebno dolg. Besede vzamemo iz sklada in jih zlozimo v pravilno urejene 
*	povedi. Na koncu vsako poved damo na sklad in jih nato jemljemo s sklada in 
*	s tem obrnemo njihov vrstni red.
*/
	public static String urediOdstavek(String odstavek) {
		//obrne crke v posamezni besedi s pomocjo sklada
		String beseda = "";
		int start = 0;
		Stack besedeS = new Stack();
		for (int i = 0; i < odstavek.length(); i++) {
			if(odstavek.charAt(i) == ' ') {
				beseda = obrniBesedo(odstavek.substring(start, i)) + " ";
				i++;
				start = i;
				//ze obrnjene besede damo v stack
				besedeS.push(beseda);
			}
		}
		beseda = obrniBesedo(odstavek.substring(start, odstavek.length())) + " ";
		besedeS.push(beseda);
		
		boolean konecPovedi = true;
		Stack swapped = new Stack();
		while(!besedeS.empty()) {
			if(konecPovedi) {
				swapped = urediKonecPovedi(swapped, besedeS);
				konecPovedi = false;
			}
			else {
				String b1 = (String)besedeS.top();
				besedeS.pop();
				String b2 = (String)besedeS.top();
				besedeS.pop();
				if(Character.isUpperCase(b1.charAt(0)) ||
				   Character.isUpperCase(b2.charAt(0))) {
					konecPovedi = true;
				}
				swapped.push(b2);
				swapped.push(b1);
			}
		}
		
		String poved = "";
		Stack povediS = new Stack();
		while(!swapped.empty()) {
			beseda = (String)swapped.top();
			poved += beseda;
			swapped.pop();
			if(imaPiko(beseda)) {
				povediS.push(poved);
				poved = "";
			}
		}
		
		String koncanOdstavek = "";
		while(!povediS.empty()) {
			koncanOdstavek += (String)povediS.top();
			povediS.pop();
		}
		
		return koncanOdstavek.trim();
	}
	
	public static Stack urediKonecPovedi(Stack swapped, Stack besedeS) {
		String b1 = (String)besedeS.top();
		besedeS.pop();
		if(imaPiko(b1) || besedeS.empty()) {
			swapped.push(b1);
			return swapped;
		}
		else {
			String b2 = (String)besedeS.top();
			besedeS.pop();
			swapped.push(b2);
			swapped.push(b1);
			return swapped;
		}
	}
	
/**
*	Z uporabo Sklada obrnemo besedo char by char
*/
	public static String obrniBesedo(String beseda) {
		Stack crkeS = new Stack();
		for (int i = 0; i < beseda.length(); i++) {
			crkeS.push(beseda.charAt(i));
		}
		
		String obrnjena = "";
		while(!crkeS.empty()) {
			obrnjena += crkeS.top();
			crkeS.pop();
		}
		return obrnjena;
	}
	
	public static boolean imaPiko(String beseda) {
		return (beseda.charAt(beseda.length() - 2) == '.');
	}
	
	public static void izpisiBesedilo(String besedilo, String dir) throws IOException {
		PrintWriter p = new PrintWriter(new FileWriter(dir));
		p.print(besedilo);
		p.close();
	}
	
	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		String besedilo = zamenjajOdstavke(args[0]);
		izpisiBesedilo(besedilo, args[1]);
		
		long stopTime = System.nanoTime();
		System.out.println((stopTime - startTime) / 1000000);
		
	}	
}