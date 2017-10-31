package com.ssu.strangerws.formallang;

import com.ssu.strangerws.formallang.automation.Automation;
import com.ssu.strangerws.formallang.automation.impl.DFA;
import com.ssu.strangerws.formallang.automation.impl.NFA;
import com.ssu.strangerws.formallang.utils.AutomationUtils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Automation automation = new DFA("C:\\Users\\StrangerWS\\IdeaProjects\\Mironov-Formal-Lang\\src\\main\\resources\\txt\\automation.txt");
        Automation automation1 = new NFA("C:\\Users\\StrangerWS\\IdeaProjects\\Mironov-Formal-Lang\\src\\main\\resources\\txt\\nfa.txt");
        String sentence = "bj";
        String sentence1 = "vggvvggvgv";

        try {
            AutomationUtils.testAutomation(automation, sentence);
            AutomationUtils.testAutomation(automation1, sentence1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}