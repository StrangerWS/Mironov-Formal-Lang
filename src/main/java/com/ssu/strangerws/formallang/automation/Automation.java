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


}
