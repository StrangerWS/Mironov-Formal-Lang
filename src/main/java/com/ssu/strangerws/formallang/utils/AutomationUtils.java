package com.ssu.strangerws.formallang.utils;

import com.ssu.strangerws.formallang.automation.Automation;
import javafx.util.Pair;

import java.io.IOException;

public class AutomationUtils {
    private Pair<Boolean, Integer> findMaxLengthExpression(Automation automation, String expression, int k) throws IOException {
        boolean over = false;
        int globalCnt = 0;
        int localCnt = 0;

        automation.init();

        for (int i = k; i < expression.length() || !automation.changeState(Character.toString(expression.charAt(i))); i++) {
            localCnt++;
            over = automation.checkEnd();
            if (over){
                globalCnt += localCnt;
                localCnt = 0;
            }
        }

        return new Pair<>(over, globalCnt);
    }
}
