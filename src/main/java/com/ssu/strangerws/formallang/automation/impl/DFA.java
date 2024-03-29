package com.ssu.strangerws.formallang.automation.impl;

import com.ssu.strangerws.formallang.automation.Automation;
import com.ssu.strangerws.formallang.utils.Transition;
import com.ssu.strangerws.formallang.utils.Type;
import javafx.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DobryninAM on 03.10.2017.
 */
public class DFA extends Automation<String> {

    public DFA() {
    }
    public DFA(String fileName, int priority, String name) {
        super(fileName, priority, name);
    }
    public DFA(String fileName, int priority, String[] masks, String name) {super(fileName, priority, masks, name);}
    public DFA(String fileName, int priority, Type type) {super(fileName, priority, type);}

    private Transition<String, String, String> getTransitionByName(String name) {
            for (Transition<String, String, String> t: transitions) {
                if (t.getTransition().equals(name) && t.getCurrent().equals(state)) {
                    return t;
                }
            }
            return null;
    }

    @Override
    public boolean changeState(String transition)  {
        Transition<String, String, String> tmp = getTransitionByName(transition);
        if (!alphabet.contains(transition) || tmp == null) return false;
        state = tmp.getNext();
        return true;
    }

    @Override
    public boolean checkEnd() {
        return endStates.contains(state);
    }

    @Override
    public void init() throws IOException {
        List<String> lines = new ArrayList<>();
        Files.lines(Paths.get(fileName)).forEach(lines::add);
        startStates = lines.get(0);
        endStates.addAll(Arrays.asList(lines.get(1).split(" ")));
        alphabet.addAll(Arrays.asList(lines.get(2).split(" ")));
        for (int i = 3; i < lines.size(); i++) {
            String[] arr = lines.get(i).split(" ");
            transitions.add(new Transition<String, String, String>(arr[0], arr[1], arr[2]));
        }
        state = startStates;
    }

}
