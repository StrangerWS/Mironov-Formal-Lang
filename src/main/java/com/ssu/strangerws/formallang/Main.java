package com.ssu.strangerws.formallang;

import com.ssu.strangerws.formallang.automation.Automation;
import com.ssu.strangerws.formallang.automation.impl.DFA;
import com.ssu.strangerws.formallang.automation.impl.NFA;
import com.ssu.strangerws.formallang.utils.AutomationUtils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Automation automation = new DFA("src\\main\\resources\\txt\\automation.txt");
        Automation automation1 = new NFA("src\\main\\resources\\txt\\nfa.txt");
        String sentence = "be";
        String sentence1 = "vggvvggvg";

        try {
            System.out.println(AutomationUtils.testAutomation(automation, sentence));
            System.out.println(AutomationUtils.testAutomation(automation1, sentence1));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}