import java.util.*;
import java.math.*;

public class Izziv7 {
    private static int pk = 0;

    public static boolean checkPrastevilo(int current) {
        for(int i = 2; i <= Math.ceil(Math.sqrt(current)); i++) 
            if(current % i == 0) return false;
        return true;
    }

    public static int poisciPrastevilo(int current) {
        while(!checkPrastevilo(current)) current++;
        return current;
    }

    public static boolean poisciPrimitivneKorene(int prastevilo, int n) {
        for(int i = 2; i < n; i++) {
            boolean currentPK = true;
            for(int j = 1; j < n; j++) {
                if((int)Math.pow(i, j) % prastevilo == 1) currentPK = false;
            }
            if(currentPK) {
                pk = i;
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int prastevilo = n;
        do {
            prastevilo = poisciPrastevilo(prastevilo + 1);
        } while(!poisciPrimitivneKorene(prastevilo, n));

        izpisi(n, prastevilo);
        sc.close();
    }

    public static void izpisi(int n, int prastevilo) {
        boolean first = true;
        int vandermonde = 0;
        System.out.print(prastevilo + ": ");
        for(int i = 2; i < prastevilo; i++) {
            boolean currentPK = true;
            for(int j = 1; j < n; j++) {
                if((int)Math.pow(i, j) % prastevilo == 1) currentPK = false;
            }
            if(currentPK) {
                System.out.print(i + " ");
                if(first) {
                    first = false;
                    vandermonde = i;
                }
            }
        }
        System.out.println();
        mx(n, prastevilo);
    }
    
    public static void mx(int n, int prastevilo) {
        for(int i = 1; i <= n; i++) {
            System.out.print(" ");
            for(int j = 1; j <= n; j++) {
                int st = (int) Math.pow(i, j - 1) % prastevilo;
                System.out.print(st + "  ");
            }
            System.out.println();
        }
    }
}