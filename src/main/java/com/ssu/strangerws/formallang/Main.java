package com.ssu.strangerws.formallang;

import com.ssu.strangerws.formallang.automation.Automation;
import com.ssu.strangerws.formallang.automation.impl.DFA;
import com.ssu.strangerws.formallang.automation.impl.NFA;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Automation automation = new DFA("C:\\Users\\StrangerWS\\IdeaProjects\\Mironov-Formal-Lang\\src\\main\\resources\\txt\\automation.txt");
        Automation automation1 = new NFA("C:\\Users\\StrangerWS\\IdeaProjects\\Mironov-Formal-Lang\\src\\main\\resources\\txt\\nfa.txt");
        String sentence = "bj";
        String sentence1 = "vggvvggvgv";

        try {
            automation.init();

            for (int i = 0; i < sentence.length(); i++) {
                if (!automation.changeState(String.valueOf(sentence.charAt(i)))) {
                    System.out.println("Invalid symbol!");
                }
            }

            if (automation.checkEnd()) {
                System.out.println("True");
            } else {
                System.out.println("False");
            }

            automation1.init();

            for (int i = 0; i < sentence1.length(); i++) {
                if (!automation1.changeState(String.valueOf(sentence1.charAt(i)))) {
                    System.out.println("Invalid symbol!");
                }
            }

            if (automation1.checkEnd()) {
                System.out.println("True");
            } else {
                System.out.println("False");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}