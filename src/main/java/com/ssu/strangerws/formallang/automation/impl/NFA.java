package com.ssu.strangerws.formallang.automation.impl;

import com.ssu.strangerws.formallang.automation.Automation;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by DobryninAM on 03.10.2017.
 */
public class NFA extends Automation<Set<String>> {
    @Override
    public boolean changeState(String transition) {
        if (!alphabet.contains(transition) || this.state.size() == 0) return false;

        Set<String> nextStates = new HashSet<>();

        for (String s : this.state) {
            Set<String> nextState = transitions.get(transition).getValue();
            if (!nextState.isEmpty()) {
                nextStates.addAll(nextState);
            }
        }

        this.state = nextStates;
        return true;
    }

    @Override
    public boolean checkEnd() {
        for (String nextState : state) {
            if (endStates.contains(nextState)) return true;
        }
        return false;
    }

    @Override
    public void init(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String[] lines = (String[]) reader.lines().toArray();
        startStates.addAll(Arrays.asList(lines[0].split(" ")));
        endStates.addAll(Arrays.asList(lines[1].split(" ")));
        alphabet.addAll(Arrays.asList(lines[2].split(" ")));
        for (int i = 3; i < lines.length; i++) {
            String[] arr = lines[i].split(" ");
            Set<String> tmpSet = new HashSet<>();
            for (int j = 2; j < arr.length; j++) {
                tmpSet.add(arr[i]);
            }
            Pair<String, Set<String>> tmp = new Pair<>(arr[1], tmpSet);
            transitions.put(arr[0], tmp);
        }

        state = startStates;
    }
}
