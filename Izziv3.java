import java.util.*;

public class Izziv3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] a = new int[n];
        int[] b = new int[m];
        for(int i = 0; i < n; i++)
            a[i] = sc.nextInt();
        for(int i = 0; i < m; i++)
            b[i] = sc.nextInt();
        sc.close();

        int[] tabela = new int[n + m];
        int i = 0; int j = 0;
        for(int k = 0; k < n + m; k++) {
            if(i >= n) {
                System.out.print("b");
                tabela[k] = b[j];
                j++;
            }
            else if(j >= m) {
                System.out.print("a");
                tabela[k] = a[i];
                i++;
            }
            else {
                if(b[j] < a[i]) {
                    System.out.print("b");
                    tabela[k] = b[j];
                    j++;
                }
                else if(a[i] <= b[j]) {
                    System.out.print("a");
                    tabela[k] = a[i];
                    i++;
                }
                else {}
            }
        }

        System.out.println();
        for(int k = 0; k < n + m; k++) 
            System.out.print(tabela[k] + " ");
        System.out.println();
    }
}