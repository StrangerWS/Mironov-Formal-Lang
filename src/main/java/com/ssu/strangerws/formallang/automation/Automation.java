package com.ssu.strangerws.formallang.automation;

import com.ssu.strangerws.formallang.utils.Transition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DobryninAM on 05.09.2017.
 */
public abstract class Automation<T> {

    protected List<String> alphabet = new ArrayList<>();
    protected T startStates;
    protected List<String> endStates = new ArrayList<>();
    protected List<Transition<String, String, T>> transitions = new ArrayList<>();
    protected T state;
    protected String fileName;

    public Automation(String fileName) {
        this.fileName = fileName;
    }

    public T getState() {
        return state;
    }

    public abstract boolean changeState(String state);

    public abstract boolean checkEnd();

    public abstract void init() throws IOException;
}
