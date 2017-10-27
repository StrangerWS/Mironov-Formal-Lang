package com.ssu.strangerws.formallang.automation.impl;

import com.ssu.strangerws.formallang.automation.Automation;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
        List<String> lines = new ArrayList<>();
        Files.lines(Paths.get(fileName)).forEach(lines::add);
        startStates = new HashSet<>();
        startStates.addAll(Arrays.asList(lines.get(0).split(" ")));
        endStates.addAll(Arrays.asList(lines.get(1).split(" ")));
        alphabet.addAll(Arrays.asList(lines.get(2).split(" ")));
        for (int i = 3; i < lines.size(); i++) {
            String[] arr = lines.get(i).split(" ");
            Set<String> tmpSet = new HashSet<>();
            for (int j = 2; j < arr.length; j++) {
                tmpSet.add(arr[j]);
            }
            Pair<String, Set<String>> tmp = new Pair<>(arr[1], tmpSet);
            transitions.put(arr[0], tmp);
        }

        state = startStates;
    }
}
