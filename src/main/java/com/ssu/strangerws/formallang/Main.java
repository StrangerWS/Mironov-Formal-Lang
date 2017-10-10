package com.ssu.strangerws.formallang;

import com.ssu.strangerws.formallang.automation.Automation;
import com.ssu.strangerws.formallang.automation.impl.DFA;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Automation automation = new DFA();
        String sentence = "abbca";

        try {
            automation.init("C:\\Users\\DobryninAM\\IdeaProjects\\Mironov-Formal-Lang\\src\\main\\resources\\txt\\automation.txt");

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}