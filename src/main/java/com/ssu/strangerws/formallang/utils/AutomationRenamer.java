package com.ssu.strangerws.formallang.utils;

import com.ssu.strangerws.formallang.automation.impl.NFA;

import java.util.*;

public class AutomationRenamer {
    public static List<Transition<String, String, Set<String>>> renameTransitionsForPair(NFA a1, NFA a2) {
        List<Transition<String, String, Set<String>>> transitions = new ArrayList<>();
        Set<Integer> names1 = new HashSet<>();
        Set<Integer> names2 = new HashSet<>();
        int maxElem;

        for (Transition<String, String, Set<String>> t1 : a1.getTransitions()) {
            names1.add(Integer.parseInt(t1.getCurrent()));
            t1.getNext().forEach(s -> {
                names1.add(Integer.parseInt(s));
            });
        }

        for (Transition<String, String, Set<String>> t2 : a2.getTransitions()) {
            names2.add(Integer.parseInt(t2.getCurrent()));
            t2.getNext().forEach(s -> {
                names2.add(Integer.parseInt(s));
            });
        }

        if (!names2.isEmpty()) {
            if (!names1.isEmpty()) {
                maxElem = (Collections.max(names1) > Collections.max(names2)) ? Collections.max(names1) : Collections.max(names2);
            } else {
                maxElem = Collections.max(names2);
            }


            names2.retainAll(names1);

            if (!names2.isEmpty()) {
                List<Integer> names2old = new ArrayList<>(names2);

                for (int i = 0; i < names2old.size(); i++) {
                    for (Transition<String, String, Set<String>> transition : a2.getTransitions()) {
                        if (transition.getCurrent().equals(names2old.get(i).toString())) {
                            transition.setCurrent(Integer.toString(maxElem + i + 1));
                        }
                        if (transition.getNext().contains(names2old.get(i).toString())) {
                            transition.getNext().remove(names2old.get(i).toString());
                            transition.getNext().add(Integer.toString(maxElem + i + 1));
                        }
                    }

                    if (a2.getStartStates().contains(names2old.get(i).toString())) {
                        a2.getStartStates().remove(names2old.get(i).toString());
                        a2.getStartStates().add(Integer.toString(maxElem + i + 1));
                    }

                    if (a2.getEndStates().contains(names2old.get(i).toString())) {
                        a2.getEndStates().remove(names2old.get(i).toString());
                        a2.getEndStates().add(Integer.toString(maxElem + i + 1));
                    }
                }
            }
        }

        transitions.addAll(a1.getTransitions());
        transitions.addAll(a2.getTransitions());

        return transitions;
    }
}
