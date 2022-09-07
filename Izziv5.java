import java.util.*;

public class Izziv5 {

    public static void printSeznam(ArrayList<Integer> seznam, int start, int end, int max) {
        System.out.print("[");
        for(int i = start; i < end - 1; i++) 
            System.out.print(seznam.get(i) + ", ");
        System.out.println(seznam.get(end - 1) + "]: " + max);
    }

    public static int poisci(ArrayList<Integer> seznam, int start, int end) {
        if(end - start <= 1) {
            printSeznam(seznam, start, end, seznam.get(start));
            return seznam.get(start);
        }
        else {
            int pol = (start + end) / 2;
            if((start + end) % 2 == 1) pol += 1;
            int leva = poisci(seznam, start, pol);
            int desna = poisci(seznam, pol, end);
            int i = pol - 1;
            int j = pol;
            int vsota = seznam.get(i--) + seznam.get(j++);
            int max = vsota;
            while(i >= start && vsota >= 0) {
                vsota += seznam.get(i);
                if(vsota > max) max = vsota;
                i--;
            }
            vsota = max;
            while(j < end && vsota >= 0) {
                vsota += seznam.get(j);
                if(vsota > max) max = vsota;
                j++;
            }
            max = Integer.max(Integer.max(leva, desna), max);
            printSeznam(seznam, start, end, max);
            return max;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> seznam = new ArrayList<Integer>();
        while(sc.hasNext()) seznam.add(sc.nextInt());
        poisci(seznam, 0, seznam.size());
        sc.close();
//        System.out.println(rez);
    }
}