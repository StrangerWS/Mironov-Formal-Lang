package com.ssu.strangerws.formallang.automation.reader;

import com.ssu.strangerws.formallang.automation.reader.api.AutomationReader;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONDFAReader implements AutomationReader<String> {
    private JSONObject json;

    public JSONDFAReader(String filePath) {
        try {
            json = new JSONObject(FileUtils.readFileToString(new File(filePath), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<String> jsonArrayToList(JSONArray array) {
        List<Object> objects = array.toList();

        List<String> strings = new ArrayList<>();
        for (Object object : objects) {
            strings.add(object.toString());
        }

        return strings;
    }

    public List<String> readAlphabet() {
        JSONArray alphabet = json.getJSONArray("alphabet");
        return jsonArrayToList(alphabet);
    }

    public Map<String, Map<String, String>> readTransitions() {
        Map<String, Map<String, String>> resultMap = new HashMap<>();

        JSONObject transitions = json.getJSONObject("transitions");
        List<String> columns = jsonArrayToList(transitions.getJSONArray("columns"));
        JSONArray rows = transitions.getJSONArray("rows");
        for (int i = 0; i < rows.length(); i++) {
            JSONObject metaRow = rows.getJSONObject(i);
            String rowName = metaRow.getString("rowName");
            Map<String, String> map = new HashMap<>();
            resultMap.put(rowName, map);
            List<String> row = jsonArrayToList(metaRow.getJSONArray("transitions"));
            for (int j = 0; j < columns.size(); j++) {
                String element = row.get(j);
                if (element.equals("-")) {
                    element = null;
                }
                map.put(columns.get(j), element);
            }
        }


        return resultMap;
    }

    public String readStartStates() {
        JSONArray beginState = json.getJSONArray("startStates");
        return beginState.getString(0);
    }

    public List<String> readEndStates() {
        JSONArray endState = json.getJSONArray("endStates");
        return jsonArrayToList(endState);
    }
}
