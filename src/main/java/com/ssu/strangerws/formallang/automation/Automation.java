package com.ssu.strangerws.formallang.automation;

import javafx.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by DobryninAM on 05.09.2017.
 */
public abstract class Automation<T> {

    protected List<String> alphabet;
    protected T startStates;
    protected List<String> endStates;
    protected Map<String, Pair<String, T>> transitions;
    protected T state;

    public T getState() {
        return state;
    }

    public abstract boolean changeState(String state);

    public abstract boolean checkEnd();

    public abstract void init(String fileName) throws IOException;
}
