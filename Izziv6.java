import java.util.*;

public class Izziv6 {
    public static int[][] dobiVhodnoMatriko(Scanner sc, int size) {
        int[][] mx = new int[size][size];
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                mx[i][j] = sc.nextInt();
        return mx;
    }

    public static void printMX(int[][] mx) {
        for(int i = 0; i < mx.length; i++) {
            for(int j = 0; j < mx.length; j++)
                System.out.print(mx[i][j] + " ");
            System.out.println();
        }
    }

    public static int[][] razdeliMatriko(int[][] startMX, int posX, int posY) {
        int size = startMX.length / 2;
        int[][] subMX = new int[size][size];
        posX = (posX * size);
        posY = (posY * size);

        int x = 0;
        int y = 0;
        for(int i = posX; i < (posX + (size)); i++) {
            for(int j = posY; j < (posY + (size)); j++) {
                subMX[y][x] = startMX[i][j];
                x++;
            }
            x = 0;
            y++;
        }
        return subMX;
    }

    public static int[][] sestejMatrike(int[][] a, int[][] b) {
        int[][] sumMX = new int[a.length][a.length];
        for(int i = 0; i < a.length; i++) 
            for(int j = 0; j < a.length; j++) 
                sumMX[i][j] = a[i][j] + b[i][j];
        return sumMX;
    }

    public static int[][] odstejMatrike(int[][] a, int[][] b) {
        int[][] sumMX = new int[a.length][a.length];
        for(int i = 0; i < a.length; i++) 
            for(int j = 0; j < a.length; j++) 
                sumMX[i][j] = a[i][j] - b[i][j];
        return sumMX;
    }

    public static int[][] normalnoMnozenje(int[][] a, int[][] b) {
        int[][] c = new int[a.length][a.length];
        for(int i = 0; i < a.length; i++)
            for(int j = 0; j < a.length; j++)
                for(int k = 0; k < a.length; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }

    public static int[][] zdruziPodmatrike(int[][] a11, int[][] a12, int[][] a21, int[][] a22) {
        int size = a11.length * 2;
        int[][] mx = new int[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(i < size / 2 && j < size / 2)
                    mx[i][j] = a11[i][j];
                else if(i < size / 2 && j >= size / 2)
                    mx[i][j] = a12[i][j - size / 2];
                else if(i >= size / 2 && j < size / 2)
                    mx[i][j] = a21[i - size / 2][j];
                else
                    mx[i][j] = a22[i - size / 2][j - size / 2];
            }
        }
        return mx;
    }

    public static int vsotaVsehElementov(int[][] mx) {
        int sum = 0;
        for(int i = 0; i < mx.length; i++)
            for(int j = 0; j < mx.length; j++)
                sum += mx[i][j];
        return sum;
    }

    public static int[][] strassen(int[][] a, int[][] b, int n, int depth) {
        if(n == depth) return normalnoMnozenje(a, b);
        
        int[][] a11 = razdeliMatriko(a, 0, 0);
        int[][] a12 = razdeliMatriko(a, 0, 1);
        int[][] a21 = razdeliMatriko(a, 1, 0);
        int[][] a22 = razdeliMatriko(a, 1, 1);
        int[][] b11 = razdeliMatriko(b, 0, 0);
        int[][] b12 = razdeliMatriko(b, 0, 1);
        int[][] b21 = razdeliMatriko(b, 1, 0);
        int[][] b22 = razdeliMatriko(b, 1, 1);

        int[][] m1 = strassen(sestejMatrike(a11, a22), sestejMatrike(b11, b22), n / 2, depth);
        int[][] m2 = strassen(sestejMatrike(a21, a22), b11, n / 2, depth);
        int[][] m3 = strassen(a11, odstejMatrike(b12, b22), n / 2, depth);
        int[][] m4 = strassen(a22, odstejMatrike(b21, b11), n / 2, depth);
        int[][] m5 = strassen(sestejMatrike(a11, a12), b22, n / 2, depth);
        int[][] m6 = strassen(odstejMatrike(a21, a11), sestejMatrike(b11, b12), n / 2, depth);
        int[][] m7 = strassen(odstejMatrike(a12, a22), sestejMatrike(b21, b22), n / 2, depth);
        
        System.out.println("m1: " + vsotaVsehElementov(m1));
        System.out.println("m2: " + vsotaVsehElementov(m2));
        System.out.println("m3: " + vsotaVsehElementov(m3));
        System.out.println("m4: " + vsotaVsehElementov(m4));
        System.out.println("m5: " + vsotaVsehElementov(m5));
        System.out.println("m6: " + vsotaVsehElementov(m6));
        System.out.println("m7: " + vsotaVsehElementov(m7));
        
        /**
        int m1 = (a11 + a22) * (b11 + b22);
        int m2 = (a21 + a22) * b11;
	    int m3 = a11 * (b12 - b22);
	    int m4 = a22 * (b21 - b11);
	    int m5 = (a11 + a12) * b22;
	    int m6 = (a21 - a11) * (b11 + b12);
	    int m7 = (a12 - a22) * (b21 + b22);
*/

        int[][] c11 = sestejMatrike(odstejMatrike(sestejMatrike(m1, m4), m5), m7);
        int[][] c12 = sestejMatrike(m3, m5);
        int[][] c21 = sestejMatrike(m2, m4);
        int[][] c22 = sestejMatrike(sestejMatrike(odstejMatrike(m1, m2), m3), m6);

        return zdruziPodmatrike(c11, c12, c21, c22);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int depth = sc.nextInt();
        int[][] a = dobiVhodnoMatriko(sc, n);
        int[][] b = dobiVhodnoMatriko(sc, n);
        printMX(strassen(a, b, n, depth));
        sc.close();
    }
}