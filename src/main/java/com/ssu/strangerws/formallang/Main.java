package com.ssu.strangerws.formallang;

import com.ssu.strangerws.formallang.automation.Automation;
import com.ssu.strangerws.formallang.automation.impl.DFA;
import com.ssu.strangerws.formallang.automation.impl.NFA;
import com.ssu.strangerws.formallang.utils.AutomationUtils;
import com.ssu.strangerws.formallang.utils.Type;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        Automation identifier = new DFA("src\\main\\resources\\txt\\identifier.txt", 1, Type.identifier);
        Automation real = new DFA("src\\main\\resources\\txt\\real.txt", 2, Type.real);
        Automation integer = new DFA("src\\main\\resources\\txt\\int.txt", 3, Type.integer);
        Automation closeBracket = new DFA("src\\main\\resources\\txt\\closeBkt.txt", 4, Type.closeBracket);
        Automation openBracket = new DFA("src\\main\\resources\\txt\\openBkt.txt", 5, Type.openBracket);
        Automation space = new DFA("src\\main\\resources\\txt\\space.txt", 6, Type.space);
        Automation keyWord = new DFA("src\\main\\resources\\txt\\keyWord.txt", 7, Type.keyWord);

        identifier.setName("ID");
        real.setName("REAL");
        integer.setName("INT");
        closeBracket.setName("CB");
        openBracket.setName("OB");
        space.setName("SP");
        keyWord.setName("KW");

        String sentence2 = "define -13.3e-7terl.ltp+5.3 (lambda (egaf) - 0).dsere 0.43";

        try {
            System.out.println(AutomationUtils.analyzeCode(new Automation[]{identifier,
                                                                            real,
                                                                            integer,
                                                                            closeBracket,
                                                                            openBracket,
                                                                            space,
                                                                            keyWord},
                                                                            sentence2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}