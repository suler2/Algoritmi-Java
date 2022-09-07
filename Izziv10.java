import java.util.Scanner;

public class Izziv10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        n++;
        int k = sc.nextInt();
        sc.close();

        int[][] tabela = initTabela(n, k); 
        metanjeJajc(n, k, tabela);
//        izpisi(n, k, tabela);
        System.out.println(tabela[n - 1][k - 1]);
    }

    public static int[][] initTabela(int n, int k) {
        int[][] tabela = new int[n][k];
        for(int i = 0; i < k; i++) {
            tabela[0][i] = 0;
            tabela[1][i] = 1;
        }
        for(int i = 0; i < n; i++)
            tabela[i][0] = i;
        return tabela;
    }

    //se ni popolnoma optimizirano...
    public static void metanjeJajc(int n, int k, int[][] tabela) {
        for(int i = 2; i < n; i++) {
            for(int j = 1; j < k; j++) {
//                if(j <= (int)Math.floor(Math.log(i) / Math.log(2)))
                    tabela[i][j] = enacba(i, j, tabela);
//                else tabela[i][j] = (int)Math.ceil(Math.log(i) / Math.log(2));
            }
        }
    }


    public static int enacba(int y, int x, int[][] tabela) {
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < y; i++) {
            int current = Math.max(tabela[i][x - 1], tabela[y - 1 - i][x]);
            if(current < min) min = current;
        }
        return 1 + min; 
    }

    public static void izpisi(int n, int k, int[][] tabela) {
        System.out.printf("    ");
        for(int i = 0; i < k; i++) {
            System.out.printf("%4d", i + 1);
        }
        System.out.printf("\n");
        for(int i = 0; i < n; i++) {
            System.out.printf("%4d", i);
            for(int j = 0; j < k; j++) {
                System.out.printf("%4d", tabela[i][j]);
            }
            System.out.printf("\n");
        }
    }
}