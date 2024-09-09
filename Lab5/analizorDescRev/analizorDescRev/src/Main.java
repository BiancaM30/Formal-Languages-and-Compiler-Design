import utils.Reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        Reader reader = new utils.Reader("analizorDescRev/input/gramatica.txt");
        ArrayList<String> lines = reader.read();
        Gramatica grammar = new Gramatica(lines);

        System.out.println(grammar);

        System.out.println("Introduceti secventa pentru verificare: ");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String secventa = consoleReader.readLine();

        Analizor analizor=new Analizor(grammar, secventa);
        System.out.println(analizor.descendentCuReveniri());
    }
}