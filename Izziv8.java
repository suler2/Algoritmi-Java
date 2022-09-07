import java.util.*;

class Complex{
	double re;
	double im;

    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    public String toString() {
    	double tRe = (double)Math.round(re * 100000) / 100000;
    	double tIm = (double)Math.round(im * 100000) / 100000;
        if (tIm == 0) return tRe + "";
        if (tRe == 0) return tIm + "i";
        if (tIm <  0) return tRe + "-" + (-tIm) + "i";
        return tRe + "+" + tIm + "i";
    }

	public Complex conj() {
		return new Complex(re, -im);
	}

    // sestevanje
    public Complex plus(Complex b) {
        Complex a = this;
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new Complex(real, imag);
    }

    // odstevanje
    public Complex minus(Complex b) {
        Complex a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;
        return new Complex(real, imag);
    }

    // mnozenje z drugim kompleksnim stevilo
    public Complex times(Complex b) {
        Complex a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new Complex(real, imag);
    }

    // mnozenje z realnim stevilom
    public Complex times(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }

    // reciprocna vrednost kompleksnega stevila
    public Complex reciprocal() {
        double scale = re*re + im*im;
        return new Complex(re / scale, -im / scale);
    }

    // deljenje
    public Complex divides(Complex b) {
        Complex a = this;
        return a.times(b.reciprocal());
    }

    // e^this
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }


    //potenca komplesnega stevila
    public Complex pow(int k) {

    	Complex c = new Complex(1,0);
    	for (int i = 0; i <k ; i++) {
			c = c.times(this);
		}
    	return c;
    }
}

public class Izziv8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int sq2 = 1;
        while(sq2 < 2 * n) sq2 *= 2;
        double[] p1 = readP(sc, sq2, n);
        double[] p2 = readP(sc, sq2, n);
        sc.close();
        double[] result = zmnoziPolinoma(p1, p2, sq2, n);

    }

    public static double[] readP(Scanner sc, int sq2, int n) {
        double[] p = new double[sq2];
        for(int i = 0; i < n; i++)
            p[i] = sc.nextDouble();
        for(int i = sq2; i < sq2 - n; i++)
            p[i] = 0.0;
        return p;
    }

/**
    public static double[] readP(Scanner sc, int sq2, int n) {
        int over = sq2 - n;
        double[] p = new double[sq2];
        for(int i = 0; i < sq2; i++) {
            if(over > 0) {
                p[i] = 0;
                over--;
            }
            else p[i] = sc.nextDouble();
        }
        return p;
    }
*/
    public static double[] zmnoziPolinoma(double[] p1, double[] p2, int sq2, int n) {
        double[] result = new double[sq2];
        Complex[] pc1 = realToComplexVector(p1, sq2);
//        printVector(pc1, sq2);
        Complex[] pc2 = realToComplexVector(p2, sq2);
        Complex[] trans1 = fft(pc1, sq2);
        Complex[] trans2 = fft(pc2, sq2);
        for(int i = 0; i < sq2; i++) {
            trans1[i] = trans1[i].times(trans2[i]);
        }
        trans1 = ifft(trans1, sq2);

        for(int i = 0; i < sq2; i++) {
            double div = 1.0 / sq2;
            trans1[i].re = trans1[i].re * div;
            System.out.print(trans1[i].toString() + " ");
        }
        System.out.println();
//        printVector(trans1, sq2);
//        printVector(trans1, sq2);
//        printVector(trans2, sq2);
        return result;
    }

    public static Complex[] realToComplexVector(double[] vector, int n) {
        Complex[] result = new Complex[n];
        for(int i = 0; i < n; i++) {
            result[i] = new Complex(vector[i], 0);
        }
        return result;
    }

    public static void printVector(Complex[] vector, int n) {
        for(int i = 0; i < n; i++) {
            String current = vector[i].toString();
            System.out.print(current + " ");
        }
        System.out.println();
    }

    public static Complex[] fft(Complex[] polinom, int size) {
        if(size == 1) {
//            Complex[] result = new Complex[1];
//            result[0] = polinom[0];
//            return result;
            return polinom;
        }
        else {
            Complex[] polinomS = razdeliPolinom(polinom, size / 2, 0);
            Complex[] polinomL = razdeliPolinom(polinom, size / 2, 1);
            

            Complex[] resultS = fft(polinomS, size / 2);
            Complex[] resultL = fft(polinomL, size / 2);
            Complex[] result = new Complex[size];

            for(int k = 0; k < size; k++) {
                Complex tmp = new Complex(0, k * 2 * Math.PI / size);
                Complex omega = tmp.exp();
//                System.out.println(tmp);
                result[k] = resultS[k % (size/2)].plus(resultL[k % (size/2)].times(omega));
            }
            printVector(result, size);
            return result;
        }
    }

    public static Complex[] ifft(Complex[] polinom, int size) {
        if(size == 1) {
//            Complex[] result = new Complex[1];
//            result[0] = polinom[0];
//            return result;
            return polinom;
        }
        else {
            Complex[] polinomS = razdeliPolinom(polinom, size / 2, 0);
            Complex[] polinomL = razdeliPolinom(polinom, size / 2, 1);
            

            Complex[] resultS = ifft(polinomS, size / 2);
            Complex[] resultL = ifft(polinomL, size / 2);
            Complex[] result = new Complex[size];

            for(int k = 0; k < size; k++) {
                Complex tmp = new Complex(0, - k * 2 * Math.PI / size);
                Complex omega = tmp.exp();
//                System.out.println(tmp);
                result[k] = resultS[k % (size/2)].plus(resultL[k % (size/2)].times(omega));
//                result[k] = result[k].times(1 / size);
            }
            printVector(result, size);
            return result;
        }
    }

/**
    public static Complex[] ifft(Complex[] polinom, int size) {
        Complex[] result = new Complex[size];
        for(int i = 0; i < size; i++)
            result[i] = polinom[i].conj();
        result = fft(result, size);
//        result = fft(polinom, size);

        for (int i = 0; i < size; i++) 
            result[i] = result[i].conj();

        for (int i = 0; i < size; i++) 
            result[i] = result[i].divides(new Complex(size, 0));
        printVector(result, size);

        return result;
    }
*/
    public static Complex[] razdeliPolinom(Complex[] polinom, int size, int sl) {
        Complex[] result = new Complex[size];
        for(int i = 0; i < size; i++) {
            if(sl % 2 == 0) 
                result[i] = polinom[2 * i];
            else 
                result[i] = polinom[2 * i + 1];
        }
        return result;
    }
}