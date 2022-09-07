import java.util.Scanner;

public class Izziv11 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int w = sc.nextInt();
        int n = sc.nextInt();
        int[][] tabela = new int[n][2];
        for(int i = 0; i < n; i++) {
            tabela[i][0] = sc.nextInt();
            tabela[i][1] = sc.nextInt();
        }
        sc.close();
        
    }
}