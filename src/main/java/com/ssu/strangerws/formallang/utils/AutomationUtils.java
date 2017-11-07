package com.ssu.strangerws.formallang.utils;

import com.ssu.strangerws.formallang.automation.Automation;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AutomationUtils {

    public static boolean testAutomation(Automation automation, String sentence) throws IOException {
        automation.init();

        for (int i = 0; i < sentence.length(); i++) {
            String signal;
            if (Character.isDigit(sentence.charAt(i))) {
                i++;
                signal = "\\d";
            } else {
                signal = String.valueOf(sentence.charAt(i));
            }
            if (!automation.changeState(signal)) {
                System.out.println("Invalid symbol: " + sentence.charAt(i));
                return false;
            }
        }

        return automation.checkEnd();
    }

    public static Pair<Boolean, Integer> findMaxLengthExpression(Automation automation, String expression, int k) throws IOException {
        boolean over = false;
        int globalCnt = 0;
        int localCnt = 0;

        automation.init();

        for (int i = k; i < expression.length(); i++) {
            String signal;
            if (Character.isDigit(expression.charAt(i))) {
                i++;
                signal = "\\d";
            } else {
                signal = String.valueOf(expression.charAt(i));
            }
            localCnt++;

            if (automation.checkEnd()){
                globalCnt+=localCnt;
                localCnt = 0;
                over = true;
            }
            if (!automation.changeState(signal)) {
                return new Pair<>(over, globalCnt);
            }
        }

        return new Pair<>(over, globalCnt);
    }

    public static List<String> findAllExpressions(Automation automation, String expression) throws IOException {
        List<String> numbers = new ArrayList<>();
        int i = 0;
        int k;

        while (i < expression.length()) {
            Pair<Boolean, Integer> tmp = findMaxLengthExpression(automation, expression, i);
            if (!tmp.getKey()) {
                i++;
            } else {
                numbers.add(expression.substring(i, i + tmp.getValue()));
                i += tmp.getValue();
            }
        }

        return numbers;
    }
}
