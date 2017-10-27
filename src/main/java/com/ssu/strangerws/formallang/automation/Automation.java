package com.ssu.strangerws.formallang.automation;

import com.ssu.strangerws.formallang.utils.Transition;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DobryninAM on 05.09.2017.
 */
public abstract class Automation<T> {

    protected List<String> alphabet = new ArrayList<>();
    protected T startStates;
    protected List<String> endStates = new ArrayList<>();
    protected List<Transition<String, String, T>> transitions = new ArrayList<>();
    protected T state;

    public T getState() {
        return state;
    }

    public abstract boolean changeState(String state);

    public abstract boolean checkEnd();

    public abstract void init(String fileName) throws IOException;
}
