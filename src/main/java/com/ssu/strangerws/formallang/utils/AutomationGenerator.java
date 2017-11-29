package com.ssu.strangerws.formallang.utils;

import com.ssu.strangerws.formallang.automation.impl.NFA;

import java.util.*;

/**
 * Created by DobryninAM on 28.11.2017.
 */
public class AutomationGenerator {
    public static NFA createEmpty() {
        Set<String> state = new HashSet<>();
        state.add("1");
        Transition<String, String, Set<String>> transition = new Transition<>("q", "1", new HashSet<String>());

        NFA automation = new NFA();
        automation.setStartStates(state);
        automation.setEndStates(new ArrayList<>(state));
        automation.setState(state);
        automation.setPriority(0);
        automation.setName("empty");
        automation.setAlphabet(new ArrayList<>());
        automation.setTransitions(new ArrayList<>());

        return automation;
    }

    public static NFA createSingle(String signal) {
        Set<String> startState = new HashSet<>();
        startState.add("1");

        List<String> endStates = new ArrayList<>();
        endStates.add("2");
        List<String> alphabet = new ArrayList<>();
        alphabet.add(signal);

        List<Transition<String, String, Set<String>>> transitions = new ArrayList<>();
        transitions.add(new Transition<>(signal, "1", new HashSet<>(endStates)));

        NFA automation = new NFA();
        automation.setStartStates(startState);
        automation.setEndStates(endStates);
        automation.setState(startState);
        automation.setPriority(0);
        automation.setName("single");
        automation.setAlphabet(alphabet);
        automation.setTransitions(transitions);

        return automation;
    }

    public static NFA union(NFA a1, NFA a2) {
        NFA automation = new NFA();

        Set<String> alphabet = new TreeSet<>();
        alphabet.addAll(a1.getAlphabet());
        alphabet.addAll(a2.getAlphabet());

        List<Transition<String, String, Set<String>>> transitions = AutomationRenamer.renameTransitionsForPair(a1, a2);

        Set<String> startStates = new HashSet<>();
        startStates.addAll(a1.getStartStates());
        startStates.addAll(a2.getStartStates());

        List<String> endStates = new ArrayList<>();
        endStates.addAll(a1.getEndStates());
        endStates.addAll(a2.getEndStates());

        Set<String> masks = new TreeSet<>();
        if (a1.getMasks() != null) masks.addAll(Arrays.asList(a1.getMasks()));
        if (a2.getMasks() != null) masks.addAll(Arrays.asList(a2.getMasks()));

        automation.setTransitions(transitions);
        automation.setStartStates(startStates);
        automation.setEndStates(endStates);
        automation.setAlphabet(new ArrayList<>(alphabet));
        automation.setState(startStates);
        if (!masks.isEmpty()) automation.setMasks(masks.toArray(new String[masks.size()]));

        return automation;
    }

    public static NFA concat(NFA a1, NFA a2) {
        NFA automation = new NFA();

        Set<String> alphabet = new TreeSet<>();
        alphabet.addAll(a1.getAlphabet());
        alphabet.addAll(a2.getAlphabet());

        List<Transition<String, String, Set<String>>> transitions = AutomationRenamer.renameTransitionsForPair(a1, a2);

        //cycle by a1.endStates
        //cycle by a2.startStates
        //get a2.startState and a2.transition
        //add in transitions new transition a2.transition, a1.endStates, a2.startStates

        for (String a1State : a1.getEndStates()) {
            for (String a2State : a2.getStartStates()) {
                for (String a2transitions : a2.getAlphabet()) {
                    if (a2.getTransitionByNameAndState(a2transitions, a2State) != null) {
                        transitions.remove(a2.getTransitionByNameAndState(a2transitions, a2State));
                        transitions.add(new Transition<>(a2transitions, a1State, a2.getTransitionByNameAndState(a2transitions, a2State).getNext()));
                    }
                }
            }
        }

        Set<String> startStates = new HashSet<>();
        startStates.addAll(a1.getStartStates());

        List<String> endStates = new ArrayList<>();
        endStates.addAll(a2.getEndStates());

        for (String state : a2.getEndStates()) {
            if (a2.getStartStates().contains(state)) {
                endStates.addAll(a1.getEndStates());
            }
        }

        Set<String> masks = new TreeSet<>();
        if (a1.getMasks() != null) masks.addAll(Arrays.asList(a1.getMasks()));
        if (a2.getMasks() != null) masks.addAll(Arrays.asList(a2.getMasks()));

        automation.setTransitions(transitions);
        automation.setStartStates(startStates);
        automation.setEndStates(endStates);
        automation.setAlphabet(new ArrayList<>(alphabet));
        automation.setState(a1.getState());
        if (!masks.isEmpty()) automation.setMasks(masks.toArray(new String[masks.size()]));

        return automation;
    }

    public static NFA iter(NFA a1) {
        NFA automation = new NFA();

        automation.setTransitions(a1.getTransitions());
        automation.setStartStates(a1.getStartStates());
        automation.setEndStates(a1.getEndStates());
        automation.setAlphabet(a1.getAlphabet());
        if(a1.getMasks() != null) automation.setMasks(a1.getMasks());

        for (String endState : a1.getEndStates()) {
            for (String startState : a1.getStartStates()) {
                for (String transition : a1.getAlphabet()) {
                    if (a1.getTransitionByNameAndState(transition, startState) != null) {
                        automation.getTransitions().add(new Transition<>(transition, endState, a1.getTransitionByNameAndState(transition, startState).getNext()));
                    }
                }
            }
        }

        automation.getStartStates().add("0");
        automation.getEndStates().add("0");

        automation.setState(automation.getStartStates());

        return automation;
    }
}