package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FiniteAutomata {
    private String fileName;
    private List<String> setOfStates;
    private List<String> alphabet;
    private List<Transition> transitionsList;
    private List<String> finalStates;
    private String initialState;

    public FiniteAutomata(String fileName) {
        this.fileName = fileName;
        this.setOfStates = new ArrayList<String>();
        this.alphabet = new ArrayList<String>();
        this.transitionsList = new ArrayList<Transition>();
        this.finalStates = new ArrayList<String>();
        this.initialState = "";
    }

    public void readFromFile() throws FileNotFoundException {
        File file = new File(this.fileName);
        Scanner scanner = new Scanner(file);

        scanner.nextLine();
        String setOfStates = scanner.nextLine();
        this.setOfStates = Arrays.asList(setOfStates.split(","));

        scanner.nextLine();
        String alphabet = scanner.nextLine();
        this.alphabet = Arrays.asList(alphabet.split(","));

        scanner.nextLine();
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("stari finale")) {
                break;
            }
            List<String> transitionElems = Arrays.asList(line.split(","));
            Transition transition = new Transition(transitionElems.get(0), transitionElems.get(2), transitionElems.get(1));
            this.transitionsList.add(transition);
        }

        String finalStates = scanner.nextLine();
        this.finalStates = Arrays.asList(finalStates.split(","));

        scanner.nextLine();
        this.initialState = scanner.nextLine();

        scanner.close();
    }


    public void readFromConsole() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Introduceti multimea starilor (delimitate cu virgula): ");
        String setOfStates = reader.readLine();
        this.setOfStates = Arrays.asList(setOfStates.split(","));

        System.out.println("Introduceti alfabetul (elemente delimitate cu virgula): ");
        String alphabet = reader.readLine();
        this.alphabet = Arrays.asList(alphabet.split(","));

        System.out.println("Introduceti tranzitiile, fiecare pe cate o linie separata (Format: stare_initiala," +
                "input,stare_finala). La final, scrieti 'stop'");
        while (true) {
            String line = reader.readLine();
            if (line.equals("stop")) {
                break;
            }
            List<String> transitionElems = Arrays.asList(line.split(","));
            Transition transition = new Transition(transitionElems.get(0), transitionElems.get(2), transitionElems.get(1));
            this.transitionsList.add(transition);
        }

        System.out.println("Introduceti starile finale (delimitate cu virgula): ");
        String finalStates = reader.readLine();
        this.finalStates = Arrays.asList(finalStates.split(","));

        System.out.println("Introduceti starile initiale (delimitate cu virgula): ");
        this.initialState = reader.readLine();
    }

    public List<String> getSetOfStates() {
        return setOfStates;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public List<Transition> getTransitionsList() {
        return transitionsList;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public String getInitialState() {
        return initialState;
    }

    public boolean isSequenceAccepted(String sequence) {
        String currentState = this.initialState;
        String[] letters = sequence.split("");
        for (String character : letters) {
            String nextState = nextState(currentState, character);
            System.out.println(currentState + " " + character + " " + nextState);

            if (nextState.equals("invalid")) return false;
            currentState = nextState;
        }
        return this.finalStates.contains(currentState);
    }

    private String nextState(String startState, String value) {
        for (Transition transition : transitionsList) {
            if (transition.getStartState().equals(startState) && transition.getInput().equals(value))
                return transition.getEndState();
        }
        return "invalid";
    }

    public String getLongestPrefix(String sequence) {
        String currentState = this.initialState;
        String prefix = "";
        String[] letters = sequence.split("");
        for (String character : letters) {
            String nextState = nextState(currentState, character);
            // System.out.println(currentState + " " + character + " " + nextState);
            if (nextState.equals("invalid")) return prefix;
            prefix += character;
            currentState = nextState;
        }
        //return this.finalStates.contains(currentState);
        return prefix;
    }

}
