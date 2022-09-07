import java.util.*;

public class Izziv2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] vhod = new int[n];
        for(int i = 0; i < n; i++)
            vhod[i] = sc.nextInt();
        int[] kopica = new int[n + 10];
        zgradiKopico(kopica, vhod, n);
        izpisi(kopica, n);

        while(n > 0) {
            n--;
            kopica[0] = kopica[n];
            pogrezni(kopica, 0, n);
            izpisi(kopica, n);
        }
    }

    public static void zgradiKopico(int[] kopica, int[] vhod, int n) {
        int pol = n / 2;
        if(n % 2 != 0) pol++;
        for(int i = pol; i < n; i++)
            kopica[i] = vhod[i];
        for(int i = pol - 1; i >= 0; i--) {
            kopica[i] = vhod[i];
            pogrezni(kopica, i, n);
        }
    }

    public static void pogrezni(int a[], int i, int dolzKopice) {
        if(i >= dolzKopice / 2)
            return;
        else if(a[i] > a[2 * i + 1] &&
                a[i] > a[2 * i + 2])
            return;
        else {
            int tmp = a[i];
            if(a[2 * i + 1] >= a[2 * i + 2]) {
                a[i] = a[2 * i + 1];
                a[2 * i + 1] = tmp;
                pogrezni(a, 2 * i + 1, dolzKopice);
            }
            else if(a[2 * i + 1] < a[2 * i + 2]){
                a[i] = a[2 * i + 2];
                a[2 * i + 2] = tmp;
                pogrezni(a, 2 * i + 2, dolzKopice);
            }
            else {}
        }
    }

    public static void izpisi(int[] kopica, int n) {
        int lim = 1;
        int ct = 1;
        for(int i = 0; i < n; i++) {
            if(i == ct) {
                System.out.print("| ");
                lim *= 2;
                ct += lim;
            }
            System.out.print(kopica[i] + " ");
        }
        System.out.println();
    }
}
