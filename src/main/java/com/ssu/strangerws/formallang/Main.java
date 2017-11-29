package com.ssu.strangerws.formallang;

import com.ssu.strangerws.formallang.automation.Automation;
import com.ssu.strangerws.formallang.automation.impl.DFA;
import com.ssu.strangerws.formallang.automation.impl.NFA;
import com.ssu.strangerws.formallang.utils.AutomationUtils;
import com.ssu.strangerws.formallang.utils.Parser;
import com.ssu.strangerws.formallang.utils.Type;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Automation identifier =     new DFA("src\\main\\resources\\txt\\identifier.txt",    1, Type.identifier);
        Automation real =           new DFA("src\\main\\resources\\txt\\real.txt",          2, Type.real);
        Automation integer =        new DFA("src\\main\\resources\\txt\\int.txt",           3, Type.integer);
        Automation closeBracket =   new DFA("src\\main\\resources\\txt\\closeBkt.txt",      4, Type.closeBracket);
        Automation openBracket =    new DFA("src\\main\\resources\\txt\\openBkt.txt",       5, Type.openBracket);
        Automation space =          new DFA("src\\main\\resources\\txt\\space.txt",         6, Type.space);
        Automation keyWord =        new DFA("src\\main\\resources\\txt\\keyWord.txt",       7, Type.keyWord);

        try {
            System.out.println(AutomationUtils.analyzeCode(new Automation[]{identifier, real, integer, closeBracket, openBracket, space, keyWord},
                                                                            AutomationUtils.readFile("src\\main\\resources\\code.txt")));
            System.out.println(AutomationUtils.analyzeCode( Parser.createAnalyzer(Parser.readLexemes("src\\main\\resources\\automations.txt")),
                                                            AutomationUtils.readFile("src\\main\\resources\\code.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}