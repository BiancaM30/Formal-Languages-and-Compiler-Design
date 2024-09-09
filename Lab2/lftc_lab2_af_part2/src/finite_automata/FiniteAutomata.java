package finite_automata;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FiniteAutomata {
    private String fileName;
    private List<String> setOfStates;
    private List<String> alphabet;
    private List<model.Transition> transitionsList;
    private List<String> finalStates;
    private String initialState;

    public FiniteAutomata(String fileName) {
        this.fileName = fileName;
        this.setOfStates = new ArrayList<String>();
        this.alphabet = new ArrayList<String>();
        this.transitionsList = new ArrayList<model.Transition>();
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
            model.Transition transition = new model.Transition(transitionElems.get(0), transitionElems.get(2), transitionElems.get(1));
            this.transitionsList.add(transition);
        }

        String finalStates = scanner.nextLine();
        this.finalStates = Arrays.asList(finalStates.split(","));

        scanner.nextLine();
        this.initialState = scanner.nextLine();

        scanner.close();
    }


    public List<String> getSetOfStates() {
        return setOfStates;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public List<model.Transition> getTransitionsList() {
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
            //System.out.println(currentState + " " + character + " " + nextState);

            if (nextState.equals("invalid")) return false;
            currentState = nextState;
        }
        //System.out.println(currentState);
        return this.finalStates.contains(currentState);
    }

    private String nextState(String startState, String value) {
        for (model.Transition transition : transitionsList) {
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