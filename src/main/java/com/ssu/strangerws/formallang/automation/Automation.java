package com.ssu.strangerws.formallang.automation;

import javafx.util.Pair;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by DobryninAM on 05.09.2017.
 */
public class Automation {
    private Set<String> alphabet;
    private Set<String> startStates;
    private Set<String> endStates;
    private Map<String, Map<String, String>> transitions;
    private String state;

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Set<String> alphabet) {
        this.alphabet = alphabet;
    }

    public String getState() {
        return state;
    }

    public Set<String> getStartStates() {
        return startStates;
    }

    public void setStartStates(Set<String> startStates) {
        this.startStates = startStates;
    }

    public Set<String> getEndStates() {
        return endStates;
    }

    public void setEndStates(Set<String> endStates) {
        this.endStates = endStates;
    }

    public Map<String, Map<String, String>> getTransitions() {
        return transitions;
    }

    public void setTransitions(Map<String, Map<String, String>> transitions) {
        this.transitions = transitions;
    }

    private boolean checkEnd(){
        return endStates.contains(state);
    }
    private boolean doTransition(String command){
        Map<String, String> transition = transitions.get(command);
        if (transition.containsKey(state)){
            state = transition.get(state);
            return true;
        }
        else return false;
    }

    public boolean execute(String expression){
        for (int i = 0; i < expression.length(); i++) {
            doTransition(Character.toString(expression.charAt(i)));
            if (checkEnd()) return true;
        }
        return false;
    }

    public Automation(Map<String, Map<String, String>> transitions, Set<String> endStates, Set<String> startStates, Set<String> alphabet) {
        this.transitions = transitions;
        this.endStates = endStates;
        this.startStates = startStates;
        this.alphabet = alphabet;
    }
}
