package com.ssu.strangerws.formallang;

import com.ssu.strangerws.formallang.automation.AutomationReader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * Created by DobryninAM on 05.09.2017.
 */
public class Main {
    public static void main(String[] args) {
        AutomationReader reader = new AutomationReader("C:\\Users\\DobryninAM\\IdeaProjects\\Mironov-Formal-Lang\\src\\main\\resources\\json\\automation.json");
    }
}
