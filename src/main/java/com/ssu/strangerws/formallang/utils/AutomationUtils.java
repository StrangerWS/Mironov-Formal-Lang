package com.ssu.strangerws.formallang.utils;

import com.ssu.strangerws.formallang.automation.Automation;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutomationUtils {

    public static String readFile(String fileName) throws IOException {
        StringBuilder file = new StringBuilder();
        Files.lines(Paths.get(fileName)).forEach(item -> {
            file.append(item);
            file.append("\n");
        });
        return file.toString();
    }

    public static boolean testAutomation(Automation automation, String expression) throws IOException {
        if (!automation.getFileName().equals("generated")) automation.init();

        for (int i = 0; i < expression.length(); i++) {
            String signal = null;
            if (automation.getMasks() != null) {
                String[] masks = automation.getMasks();
                for (int j = 0; j < masks.length; j++) {
                    Matcher m = Pattern.compile(automation.getMasks()[j]).matcher(String.valueOf(expression.charAt(i)));
                    if (m.matches()) {
                        signal = masks[j];
                    }
                }
            }
            if (signal == null) {
                signal = String.valueOf(expression.charAt(i));
            }
            if (!automation.changeState(signal)) {
                System.out.println("Invalid symbol: " + expression.charAt(i));
                return false;
            }
        }

        return automation.checkEnd();
    }

    private static Pair<Boolean, Integer> findMaxLengthExpression(Automation automation, String expression, int k) throws IOException {
        boolean over = false;
        int globalCnt = 0;
        int localCnt = 0;

        if (!automation.getFileName().equals("generated")) automation.init();
        else automation.setState(automation.getStartStates());

        for (int i = k; i < expression.length(); i++) {
            String signal = null;
            if (automation.getMasks() != null) {
                String[] masks = automation.getMasks();
                for (int j = 0; j < masks.length; j++) {
                    Matcher m = Pattern.compile(automation.getMasks()[j]).matcher(String.valueOf(expression.charAt(i)));
                    if (m.matches()) {
                        signal = masks[j];
                    }
                }
            }
            if (signal == null) {
                signal = String.valueOf(expression.charAt(i));
            }
            localCnt++;

            if (!automation.changeState(signal)) {
                return new Pair<>(over, globalCnt);
            }

            if (automation.checkEnd()) {
                globalCnt += localCnt;
                localCnt = 0;
                over = true;
            }
        }

        return new Pair<>(over, globalCnt);
    }

    public static List<String> findAllExpressions(Automation automation, String expression) throws IOException {
        List<String> numbers = new ArrayList<>();
        int i = 0;

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

    public static List<Pair<String, String>> analyzeCode(Automation[] automations, String expression) throws IOException {
        List<Pair<String, String>> lexemes = new ArrayList<>();
        int i = 0;
        int maxLength = 0;
        int automationIndex = -1;
        int falseCnt = 0;

        while (i < expression.length()) {
            for (int j = 0; j < automations.length; j++) {
                Pair<Boolean, Integer> tmp = findMaxLengthExpression(automations[j], expression, i);
                if (!tmp.getKey()) {
                    falseCnt++;
                }
                if (maxLength < tmp.getValue()) {
                    maxLength = tmp.getValue();
                    automationIndex = j;
                } else if (maxLength == tmp.getValue()) {
                    if (automationIndex == -1 || automations[j].getPriority() > automations[automationIndex].getPriority()) {
                        maxLength = tmp.getValue();
                        automationIndex = j;
                    }
                }
            }

            if (falseCnt == automations.length) {
                System.out.println("No automations to read the symbol: " + expression.charAt(i) + " at position " + i);
                return lexemes;
            }

            falseCnt = 0;

            lexemes.add(new Pair<>(automations[automationIndex].getName(), expression.substring(i, i + maxLength)));
            i += maxLength;

            maxLength = 0;
            automationIndex = -1;
        }

        return lexemes;
    }
}
