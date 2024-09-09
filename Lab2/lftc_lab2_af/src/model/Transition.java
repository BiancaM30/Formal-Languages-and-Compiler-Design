package model;

import java.util.List;

public class Transition {
    private String startState;
    private String endState;
    private String input;

    public Transition(String startState, String endState, String input) {
        this.startState = startState;
        this.endState = endState;
        this.input = input;
    }

    public Transition() {
    }

    public String getStartState() {
        return startState;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public String getEndState() {
        return endState;
    }

    public void setEndState(String endState) {
        this.endState = endState;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public String toString() {

        return 'd' +"(" + startState + "," + input + ")=" + endState;
    }
}
