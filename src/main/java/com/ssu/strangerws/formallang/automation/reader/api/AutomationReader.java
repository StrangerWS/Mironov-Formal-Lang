package com.ssu.strangerws.formallang.automation.reader.api;

import java.util.List;
import java.util.Map;

/**
 * Created by DobryninAM on 03.10.2017.
 */
public interface AutomationReader<T> {

    List<String> readAlphabet();
    T readStartStates();
    List<String> readEndStates();
    Map<String, Map<String, T>> readTransitions();
}
