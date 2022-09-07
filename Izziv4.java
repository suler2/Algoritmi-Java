import java.util.*;

class Stevilo {
    int stevilo;
    int stEnic;

    public Stevilo(int stevilo) {
        this.stevilo = stevilo;
        this.stEnic = pretvori(stevilo);
    }

    private int pretvori(int n) {
        int st = 0;
        while(n > 0) {
            if(n % 2 == 1) st++;
            n /= 2;
        }
        //POZOR zaradi stEnic
        return st - 1;
    }
}

public class Izziv4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Stevilo[] array = new Stevilo[n];
        int maxEnic = 0;
        for(int i = 0; i < n; i++) {
            array[i] = new Stevilo(sc.nextInt());
            if(maxEnic < array[i].stEnic) maxEnic = array[i].stEnic;
        }
        sc.close();
/**
        for(int i = 0; i < n; i++) {
            System.out.println(array[i].stevilo + ", " + array[i].stEnic + " | ");
        }
        System.out.println(maxEnic);
*/
        //POZOR pri maxEnic
        Stevilo[] sortiran = countSort(array, n, maxEnic + 1);

        for(int i = 0; i < n; i++)
            System.out.print(sortiran[i].stevilo + " ");
        System.out.println();
    }

    public static Stevilo[] countSort(Stevilo[] A, int n, int k) {
        Stevilo[] B = new Stevilo[n];
        for(int i = 0; i < n; i++)
            B[i] = new Stevilo(0);
        int[] C = new int[k];

        for(int i = 0; i < k; i++) {
            C[i] = 0;
        }

        for(int i = 0; i < n; i++)
            C[A[i].stEnic]++;
        
        for(int i = 1; i < k; i++)
            C[i] = C[i] + C[i - 1];
        
        for(int i = 0; i < k; i++)
        	C[i]--;
        
        for(int i = n - 1; i >= 0; i--) {
            B[C[A[i].stEnic]] = A[i];
            System.out.println("(" + A[i].stevilo + "," + C[A[i].stEnic] + ")");
            C[A[i].stEnic]--;
        }

        return B;
    }
}