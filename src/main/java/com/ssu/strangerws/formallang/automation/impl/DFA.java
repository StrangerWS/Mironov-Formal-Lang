package com.ssu.strangerws.formallang.automation.impl;

import com.ssu.strangerws.formallang.automation.Automation;
import com.ssu.strangerws.formallang.automation.reader.api.AutomationReader;

/**
 * Created by DobryninAM on 03.10.2017.
 */
public class DFA extends Automation<String> {
    public DFA(AutomationReader<String> reader) {
        super(reader);
    }

    @Override
    public boolean changeState(String state)  {
        if (!alphabet.contains(state) || transitions.get(this.state) == null) return false;

        String newState = transitions.get(this.state).get(state);
        if (newState == null) return false;

        this.state = newState;
        return true;
    }

    @Override
    public boolean checkEnd() {
        return endStates.contains(state);
    }
}
