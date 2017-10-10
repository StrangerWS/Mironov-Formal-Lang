package com.ssu.strangerws.formallang.automation;

import com.ssu.strangerws.formallang.automation.reader.api.AutomationReader;

import java.util.List;
import java.util.Map;

/**
 * Created by DobryninAM on 05.09.2017.
 */
public abstract class Automation<T> {

    protected List<String> alphabet;
    protected T startStates;
    protected List<String> endStates;
    protected Map<String, Map<String, T>> transitions;
    protected T state;
    protected AutomationReader<T> reader;

    public T getState() {
        return state;
    }

    public Automation(AutomationReader<T> reader) {
        this.reader = reader;
    }

    public void init() {
        alphabet = reader.readAlphabet();
        transitions = reader.readTransitions();
        startStates = reader.readStartStates();
        endStates = reader.readEndStates();
        state = startStates;
    }

    public abstract boolean changeState(String state);

    public abstract boolean checkEnd();

}
