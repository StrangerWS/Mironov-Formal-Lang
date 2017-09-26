package com.ssu.strangerws.formallang.automation;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by DobryninAM on 26.09.2017.
 */
public class AutomationReader {
    private JSONObject automation;

    public AutomationReader(String filePath) {
        try {
            automation = new JSONObject(FileUtils.readFileToString(new File(filePath), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> readAlphabet(){
        automation.get("transitions");
        return null;
    }
}
