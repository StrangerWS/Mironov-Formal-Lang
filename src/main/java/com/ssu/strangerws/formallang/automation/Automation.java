package com.ssu.strangerws.formallang.automation;

import com.ssu.strangerws.formallang.utils.Transition;
import com.ssu.strangerws.formallang.utils.Type;

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
    protected int priority;
    protected String name;

    public String[] getMasks() {
        return masks;
    }

    protected String[] masks;

    public Automation() {
    }

    public Automation(String fileName, int priority, String name) {
        this.fileName = fileName;
        this.priority = priority;
        this.name = name;
    }
    public Automation(String fileName, int priority, String[] masks, String name) {
        this.fileName = fileName;
        this.priority = priority;
        this.masks = masks;
        this.name = name;
    }

    public Automation(String fileName, int priority, Type type){
        this.fileName = fileName;
        this.priority = priority;
        this.masks = type.masks;
        this.name = type.name;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    public T getStartStates() {
        return startStates;
    }

    public void setStartStates(T startStates) {
        this.startStates = startStates;
    }

    public List<String> getEndStates() {
        return endStates;
    }

    public void setEndStates(List<String> endStates) {
        this.endStates = endStates;
    }

    public List<Transition<String, String, T>> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition<String, String, T>> transitions) {
        this.transitions = transitions;
    }

    public void setState(T state) {
        this.state = state;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setMasks(String[] masks) {
        this.masks = masks;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getState() {
        return state;
    }

    public abstract boolean changeState(String state);

    public abstract boolean checkEnd();

    public abstract void init() throws IOException;
}
