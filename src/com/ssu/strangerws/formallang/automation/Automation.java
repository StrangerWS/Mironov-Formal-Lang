package com.ssu.strangerws.formallang.automation;

import javafx.util.Pair;

import java.io.*;
import java.util.*;

/**
 * Created by DobryninAM on 05.09.2017.
 */
public class Automation {
    private Set<String> startStates;
    private Set<String> endStates;
    private Map<String, Pair<String, String>> transitions;
    private String state;

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

    public Map<String, Pair<String, String>> getTransitions() {
        return transitions;
    }

    public void setTransitions(Map<String, Pair<String, String>> transitions) {
        this.transitions = transitions;
    }

    public Automation(Set<String> startStates, Set<String> endStates, Map<String, Pair<String, String>> transitions) {
        boolean flag = true;

        for (String s : startStates) {
            for (Map.Entry entry : transitions.entrySet()) {
                if (!entry.getKey().equals(s)) {
                    flag = false;
                    System.err.println("INVALID START STATES");
                    break;
                }
            }
        }

        if (flag) {
            for (String s : endStates) {
                for (Map.Entry entry : transitions.entrySet()) {
                    if (!entry.getKey().equals(s)) {
                        flag = false;
                        System.err.println("INVALID END STATES");
                        break;
                    }
                }
            }
        }

        if (flag) {
            this.startStates = startStates;
            this.endStates = endStates;
            this.transitions = transitions;
        }
    }

    public static Map<String, Pair<String, String>> getTransitionsFromData(String fileName) {
        Map<String, Pair<String, String>> transitions = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(" ");
                Pair<String, String> tmp = new Pair(arr[1], arr[2]);
                transitions.put(arr[0], tmp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transitions;
    }

    public void getStatesFromData(String fileName){
        Set<String> startStates = new HashSet<String>();
        Set<String> endStates = new HashSet<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.contains(">")){
                    startStates.add(line.substring(1));
                }
                if(line.contains("\\")){
                    endStates.add(line.substring(1));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean execute(String expression){
        String[] array = expression.split(" ");
        for (int i = 0; i < array.length; i++) {
            if(stateChange(array[i])){
                return true;
            }
        }
        return false;
    }

    private boolean stateChange(String cmd){
        Pair<String, String> transition = transitions.get(cmd);
        if (state.equals(transition.getKey()) && endStates.contains(transition.getValue())){
            state = transition.getValue();
            return true;
        } else return false;
    }
}
