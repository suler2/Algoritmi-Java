import java.util.*;

public class Izziv1 {
    public static int[] generateTable(int n) {
        int[] table = new int[n];
        for(int i = 0; i < n; i++)
            table[i] = i + 1;
        return table;
    }
    
    public static int findLinear(int[] a, int v) {
        int ix = 0;
        while(a[ix] != v) ix++;
        return ix;
    }

    public static int findBinary(int[] a, int l, int r, int v) {
        int ix = l + (r - l) / 2;
        if(a[ix] == v) return ix;
        else if(a[ix] > v) findBinary(a, l, ix - 1, v);
        else findBinary(a, ix + 1, r, v);
        return -1;

    }

    public static long timeLinear(int n) {
        int[] tabela = generateTable(n);
        long startTime = System.nanoTime();
        for(int i = 0; i < 1000; i++) {
            int r = (int)(Math.random() * n + 1);
            int dump = findLinear(tabela, r);
        }
        long executionTime = System.nanoTime() - startTime;
        return executionTime / 1000;
    }

    public static long timeBinary(int n) {
        int[] tabela = generateTable(n);
        long startTime = System.nanoTime();
        for(int i = 0; i < 1000; i++) {
            int r = (int)(Math.random() * n + 1);
            int dump = findBinary(tabela, 0, n, r);
        }
        long executionTime = System.nanoTime() - startTime;
        return executionTime / 1000;
    }

    public static void main(String[] args) {
        System.out.println("   n   |linearno|dvojisko");
        System.out.println("--------------------------");
        for(int i = 1000; i <= 100000; i += 1000) {
            System.out.println(i + " | " + timeLinear(i) + " | " + timeBinary(i));
        }
    }
}