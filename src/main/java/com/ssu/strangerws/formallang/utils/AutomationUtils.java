package com.ssu.strangerws.formallang.utils;

import com.ssu.strangerws.formallang.automation.Automation;
import javafx.util.Pair;

import java.io.IOException;

public class AutomationUtils {

    public static boolean testAutomation(Automation automation, String sentence) throws IOException {
        automation.init();

        for (int i = 0; i < sentence.length(); i++) {
            if (!automation.changeState(String.valueOf(sentence.charAt(i)))) {
                System.out.println("Invalid symbol: " + sentence.charAt(i));
                return false;
            }
        }

        return automation.checkEnd();
    }

    public Pair<Boolean, Integer> findMaxLengthExpression(Automation automation, String expression, int k) throws IOException {
        boolean over = false;
        int globalCnt = 0;
        int localCnt = 0;

        automation.init();

        for (int i = k; i < expression.length() || !automation.changeState(Character.toString(expression.charAt(i))); i++) {
            localCnt++;
            over = automation.checkEnd();
            if (over) {
                globalCnt += localCnt;
                localCnt = 0;
            }
        }

        return new Pair<>(over, globalCnt);
    }
}
