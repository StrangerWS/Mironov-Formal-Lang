package com.ssu.strangerws.formallang.automation.impl;

import com.ssu.strangerws.formallang.automation.Automation;
import com.ssu.strangerws.formallang.utils.Transition;
import com.ssu.strangerws.formallang.utils.Type;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by DobryninAM on 03.10.2017.
 */
public class NFA extends Automation<Set<String>> {

    public NFA() {
    }
    public NFA(String fileName, int priority, String name) {
        super(fileName, priority, name);
    }
    public NFA(String fileName, int priority, String[] masks, String name) {super(fileName, priority, masks, name);}
    public NFA(String fileName, int priority, Type type, String name) {super(fileName, priority, type);}


    public Transition<String, String, Set<String>> getTransitionByNameAndState(String name, String state) {
        for (Transition<String, String, Set<String>> t : transitions) {
            if (t.getTransition().equals(name) && t.getCurrent().equals(state)) {
                return t;
            }
        }
        return null;

    }

    @Override
    public boolean changeState(String transition) {
        if (!alphabet.contains(transition) || state.size() == 0) return false;

        Set<String> nextStates = new HashSet<>();

        for (String s : state) {
            Transition<String, String, Set<String>> tmp = getTransitionByNameAndState(transition, s);
            if (tmp != null) {
                nextStates.addAll(tmp.getNext());
            }
        }

        state = nextStates;
        return true;
    }

    @Override
    public boolean checkEnd() {
        System.out.println(state.toString());
        for (String nextState : state) {
            if (endStates.contains(nextState)) return true;
        }
        return false;
    }

    @Override
    public void init() throws IOException {
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
            transitions.add(new Transition<String, String, Set<String>>(arr[0], arr[1], tmpSet));
        }

        state = startStates;
    }
}
