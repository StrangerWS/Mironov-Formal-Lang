package com.ssu.strangerws.formallang.automation.impl;

import com.ssu.strangerws.formallang.automation.Automation;
import com.ssu.strangerws.formallang.automation.reader.api.AutomationReader;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DobryninAM on 03.10.2017.
 */
public class NFA extends Automation<Set<String>> {
    public NFA(AutomationReader<Set<String>> reader) {
        super(reader);
    }

    @Override
    public boolean changeState(String state) {
        if (!alphabet.contains(state) || this.state.size() == 0) return false;

        Set<String> nextStates = new HashSet<>();

        for (String s : this.state) {
            Set<String> nextState = transitions.get(this.state).get(state);
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
}
