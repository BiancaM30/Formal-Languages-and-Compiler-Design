import jdk.jfr.consumer.RecordedThreadGroup;

import java.util.ArrayList;
import java.util.List;

public class Gramatica {
    private final List<String> lines;

    private final List<List<String>> reguli = new ArrayList<>();

    private final List<String> terminali = new ArrayList<>();

    private final List<String> neterminali = new ArrayList<>();

    public Gramatica(List<String> lines) {
        this.lines = lines;
        creezaReguli();
        creeazaTerminaliNeterminali();
    }

    private void creezaReguli() {
        for (int i = 0; i < lines.size(); i++) {
            // FORMA REGULA [S0, S, aSbS]
            // FORMA REGULA [S1, A, c]
            ArrayList<String> regula = new ArrayList<>();

            String leftElement = lines.get(i).split("->")[0];
            String rightElement = lines.get(i).split("->")[1];

            // Numar regula
            regula.add("S" + i);

            // Neterminal
            regula.add(leftElement);

            // Terminal
            regula.add(rightElement);

            reguli.add(regula);
        }
    }

    public void creeazaTerminaliNeterminali() {
        for (int i = 0; i < lines.size(); i++) {
            String leftElement = lines.get(i).split("->")[0];
            this.neterminali.add(leftElement);
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] rightElements = lines.get(i).split("->")[1].split("");
            for(String rightElement:rightElements) {
                if (!neterminali.contains(rightElement)) {
                    terminali.add(rightElement);
                }
            }
        }
    }

    public List<List<String>> getReguli() {
        return reguli;
    }

    public List<String> getTerminali() {
        return terminali;
    }

    public List<String> getNeterminali() {
        return neterminali;
    }

    @Override
    public String toString() {
        return "Gramatica{" +
                "reguli=" + reguli +
                ", terminali=" + terminali +
                ", neterminali=" + neterminali +
                '}';
    }
}
