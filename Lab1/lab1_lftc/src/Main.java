import java.util.Scanner;

public class Main {
    //1. perimetru si aria cerc
    public static void program1() {
        double p, a, r;
        System.out.println("Introduceti raza: ");
        Scanner in = new Scanner(System.in);
        r = in.nextDouble();
        p = 2 * r * 3.14;
        a = r * r * 3.14;
        System.out.print("Perimetru: ");
        System.out.println(p);
        System.out.print("Arie: ");
        System.out.println(a);
    }

    //2.  determina cmmdc a 2 nr naturale
    public static void program2() {
        int a, b;
        System.out.println("Introduceti cele 2 numere: ");
        Scanner in = new Scanner(System.in);
        a = in.nextInt();
        b = in.nextInt();
        while (a != b) {
            if (a > b) {
                a = a - b;
            } else {
                b = b - a;
            }
        }
        System.out.print("CMMDC: ");
        System.out.println(a);

    }

    //3. calculeaza suma a n numere citite de la tastatura
    public static void program3() {
        int n, sum, contor;
        System.out.println("Introduceti n:  ");
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        sum = 0;
        contor = 0;
        System.out.println("Introduceti numerele: ");
        while (contor < n) {
            int nr;
            nr = in.nextInt();
            sum = sum + nr;
            contor = contor + 1;
        }
        System.out.println("Suma este:");
        System.out.println(sum);
    }

    public static void program_erori() {
        int a, b;
        System.out.println("Introduceti numerele: ");
        Scanner in = new Scanner(System.in);
        a = in.nextInt();
        b = in.nextInt();
        if (a > b) {
            b++;
            System.out.println(b);
        }
        b *= 2;
        System.out.println(b);
    }

    public static void main(String[] args) {
        System.out.println("Program1");
        program1();
        System.out.println("\nProgram2");
        program2();
        System.out.println("\nProgram3");
        program3();
        System.out.println("\nProgram_erori");
        program_erori();
    }

}