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
    private Set<String> startStates;
    private Set<String> endStates;
    private Map<String, Pair<String, String>> transitions;

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
                Pair tmp = new Pair(arr[1], arr[2]);
                transitions.put(arr[0], tmp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transitions;
    }

    private boolean checkInputValidity(List<String> input){
        boolean flag = true;

        for (String s : startStates) {
            for (Map.Entry entry : transitions.entrySet()) {
                if (!entry.getKey().equals(s)) {
                    flag = false;
                    System.err.println("INVALID INPUT");
                    break;
                }
            }
        }

        return flag;
    }

    
}
