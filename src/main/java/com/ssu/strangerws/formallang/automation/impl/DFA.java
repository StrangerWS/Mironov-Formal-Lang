package com.ssu.strangerws.formallang.automation.impl;

import com.ssu.strangerws.formallang.automation.Automation;
import javafx.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.IntFunction;

/**
 * Created by DobryninAM on 03.10.2017.
 */
public class DFA extends Automation<String> {

    @Override
    public boolean changeState(String state)  {
        if (!alphabet.contains(state) || transitions.get(state) == null) return false;

        String newState = transitions.get(state).getValue();
        if (newState == null) return false;

        this.state = newState;
        return true;
    }

    @Override
    public boolean checkEnd() {
        return endStates.contains(state);
    }

    @Override
    public void init(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        List<String> lines = new ArrayList<>();
        Files.lines(Paths.get(fileName)).forEach(lines::add);
        startStates = lines.get(0);
        endStates.addAll(Arrays.asList(lines.get(1).split(" ")));
        alphabet.addAll(Arrays.asList(lines.get(2).split(" ")));
        for (int i = 3; i < lines.size(); i++) {
            String[] arr = lines.get(i).split(" ");
            Pair<String, String> tmp = new Pair<>(arr[1], arr[2]);
            transitions.put(arr[0], tmp);
        }
    }

}
