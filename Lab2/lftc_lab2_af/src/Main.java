//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.FiniteAutomata;

public class Main {
    private static void showReadingChoices() {
        System.out.println("1. Citire din fisier");
        System.out.println("2. Citire de la tastatura");
    }

    private static void showMenu() {
        System.out.println("1. Multimea starilor");
        System.out.println("2. Alfabetul");
        System.out.println("3. Tranzitiile");
        System.out.println("4. Stari finale");
        System.out.println("5. Verifica daca secventa e acceptata de AF");
        System.out.println("6. Cel mai lung prefix acceptat de AF");
        System.out.println("0. Iesire");
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        FiniteAutomata FA = new FiniteAutomata("C:\\Facultate\\Cursuri facultate sem 5\\LFTC\\Laboratoare\\Teme\\Lab2\\lftc_lab2_af\\src\\fa.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        boolean read = false;
        while (!read) {
            showReadingChoices();
            System.out.println("Comanda: ");
            switch (reader.readLine()) {
                case "1":
                    FA.readFromFile();
                    read = true;
                    break;
                case "2":
                    read = true;
                    FA.readFromConsole();
                    break;
                default:
                    System.err.println("Comanda invalida!");
            }
        }

        while (true) {
            showMenu();
            System.out.println("Comanda: ");
            switch (reader.readLine()) {
                case "1":
                    System.out.println("Multimea starilor: ");
                    System.out.println(FA.getSetOfStates());
                    System.out.println("\n");
                    break;
                case "2":
                    System.out.println("Alfabet: ");
                    System.out.println(FA.getAlphabet());
                    System.out.println("\n");
                    break;
                case "3":
                    System.out.println("Tranzitii: ");
                    System.out.println(FA.getTransitionsList());
                    System.out.println("\n");
                    break;
                case "4":
                    System.out.println("Stari finale: ");
                    System.out.println(FA.getFinalStates());
                    System.out.println("\n");
                    break;
                case "5":
                    System.out.println("Introduceti secventa: ");
                    String sequence = reader.readLine();
                    if (FA.isSequenceAccepted(sequence)) {
                        System.out.println("Secventa e acceptata de AF");
                    } else System.out.println("Secventa nu e acceptata");
                    System.out.println("\n");
                    break;
                case "6":
                    System.out.println("Introduceti secventa: ");
                    String sequence2 = reader.readLine();
                    System.out.println("Cel mai lung prefix:" + FA.getLongestPrefix(sequence2));
                    System.out.println("\n");
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.err.println("Comanda invalida!");
            }
        }
    }
}

