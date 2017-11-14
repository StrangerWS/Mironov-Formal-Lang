package com.ssu.strangerws.formallang;

import com.ssu.strangerws.formallang.automation.Automation;
import com.ssu.strangerws.formallang.automation.impl.DFA;
import com.ssu.strangerws.formallang.automation.impl.NFA;
import com.ssu.strangerws.formallang.utils.AutomationUtils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//      Automation automation = new DFA("src\\main\\resources\\txt\\dfa.txt", 0);
//      Automation automation1 = new NFA("src\\main\\resources\\txt\\nfa.txt", 0);
        Automation automation2 = new DFA("src\\main\\resources\\txt\\real.txt", 0);
        Automation keyWord = new DFA("src\\main\\resources\\txt\\real.txt", 0, true);
//      String sentence = "be";
//      String sentence1 = "vggvvggvg";
        String sentence2 = "-13.3e-7erl.ltp+5.3egaf0.dsere.043e+12";

        try {
//          System.out.println(AutomationUtils.testAutomation(automation, sentence));
//          System.out.println(AutomationUtils.testAutomation(automation1, sentence1));
//          System.out.println(AutomationUtils.findAllExpressions(automation2, sentence2));
            System.out.println(AutomationUtils.testAutomation(keyWord, sentence2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}