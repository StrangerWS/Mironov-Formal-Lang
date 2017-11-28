package com.ssu.strangerws.formallang.utils;

import com.ssu.strangerws.formallang.automation.Automation;
import com.ssu.strangerws.formallang.automation.impl.NFA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by DobryninAM on 28.11.2017.
 */
public class AutomationGenerator {
    public static Automation createEmpty() {
        Set<String> state = new HashSet<>();
        state.add("1");
        Transition<String, String, Set<String>> transition = new Transition<>("q", "1", new HashSet<String>());

        Automation automation = new NFA();
        automation.setStartStates(state);
        automation.setEndStates(new ArrayList<>(state));
        automation.setState(new ArrayList<>(state));
        automation.setPriority(0);
        automation.setName("empty");
        automation.setAlphabet(new ArrayList<>());
        automation.setTransitions(new ArrayList<Transition<String, String, Set<String>>>());

        return automation;
    }

    public static Automation createSingle(String signal) {
        Set<String> startState = new HashSet<>();
        startState.add("1");

        List<String> endStates = new ArrayList<>();
        endStates.add("2");
        List<String> alphabet = new ArrayList<>();
        alphabet.add(signal);

        List<Transition<String, String, Set<String>>> transitions = new ArrayList<>();
        transitions.add(new Transition<>(signal, "1", new HashSet<>(endStates)));

        Automation automation = new NFA();
        automation.setStartStates(startState);
        automation.setEndStates(endStates);
        automation.setState(new ArrayList<>(startState));
        automation.setPriority(0);
        automation.setName("single");
        automation.setAlphabet(alphabet);
        automation.setTransitions(transitions);

        return automation;
    }

    public static Automation union(NFA a1, NFA a2) {
        Automation automation = new NFA();

        List<String> alphabet = new ArrayList<>();
        alphabet.addAll(a1.getAlphabet());
        alphabet.addAll(a2.getAlphabet());

        List<Transition> transitions = new ArrayList<>();
        transitions.addAll(a1.getTransitions());
        transitions.addAll(a2.getTransitions());

        Set<String> startStates = new HashSet<>();
        startStates.addAll(a1.getStartStates());
        startStates.addAll(a2.getStartStates());

        List<String> endStates = new ArrayList<>();
        endStates.addAll(a1.getEndStates());
        endStates.addAll(a2.getEndStates());

        automation.setTransitions(transitions);
        automation.setStartStates(startStates);
        automation.setEndStates(endStates);
        automation.setAlphabet(alphabet);

        return automation;
    }

    public static Automation concat(NFA a1, NFA a2) {
        Automation automation = new NFA();

        List<String> alphabet = new ArrayList<>();
        alphabet.addAll(a1.getAlphabet());
        alphabet.addAll(a2.getAlphabet());

        List<Transition> transitions = new ArrayList<>();
        transitions.addAll(a1.getTransitions());
        transitions.addAll(a2.getTransitions());

        Set<String> startStates = new HashSet<>();
        startStates.addAll(a1.getStartStates());

        List<String> endStates = new ArrayList<>();
        endStates.addAll(a2.getEndStates());

        //cycle by a1.endStates
        //cycle by a2.startStates
        //get a2.startState and a2.transition
        //add in transitions new transition a2.transition, a1.endStates, a2.startStates

        for (String a1State : a1.getEndStates()) {
            for (String a2State : a2.getStartStates()){
                for (String a2transitions : a2.getAlphabet()){
                    if (a2.getTransitionByNameAndState(a2transitions, a2State) != null){
                        Set<String> tmp = new HashSet<>();
                        tmp.add(a2State);
                        transitions.add(new Transition<String, String, Set<String>>(a2transitions, a1State, tmp));
                    }
                }
            }
        }

        for (String state : a2.getEndStates()){
            if (a2.getStartStates().contains(state)){
                endStates.addAll(a1.getEndStates());
            }
        }

        automation.setTransitions(transitions);
        automation.setStartStates(startStates);
        automation.setEndStates(endStates);
        automation.setAlphabet(alphabet);

        return automation;
    }

    public static NFA iter(NFA a1) {
        NFA automation = new NFA();

        automation.setTransitions(a1.getTransitions());
        automation.setStartStates(a1.getStartStates());
        automation.setEndStates(a1.getEndStates());
        automation.setAlphabet(a1.getAlphabet());

        automation.getStartStates().add("q");
        automation.getEndStates().add("q");

        return automation;
    }
}