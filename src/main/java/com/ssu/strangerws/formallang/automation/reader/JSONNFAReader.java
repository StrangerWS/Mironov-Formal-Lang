package com.ssu.strangerws.formallang.automation.reader;

import com.ssu.strangerws.formallang.automation.reader.api.AutomationReader;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by DobryninAM on 03.10.2017.
 */
public class JSONNFAReader implements AutomationReader<Set<String>> {

    private JSONObject json;

    public JSONNFAReader(String filePath) {
        try {
            json = new JSONObject(FileUtils.readFileToString(new File(filePath), "UTF-8"));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public List<String> readAlphabet() {
        JSONArray alphabet = json.getJSONArray("alphabet");
        return jsonArrayToList(alphabet);
    }

    public Map<String, Map<String, Set<String>>> readTransitions() {
        Map<String, Map<String, Set<String>>> resultMap = new HashMap<>();

        JSONObject table = json.getJSONObject("table");
        List<String> columns = jsonArrayToList(table.getJSONArray("columns"));
        JSONArray rows = table.getJSONArray("rows");
        for (int i = 0; i < rows.length(); i++) {
            JSONObject metaRow = rows.getJSONObject(i);
            String rowName = metaRow.getString("rowName");
            Map<String, Set<String>> map = new HashMap<>();
            resultMap.put(rowName, map);

            JSONArray transitions = metaRow.getJSONArray("transitions");

            for (int j = 0; j < columns.size(); j++) {
                Set<String> row = new HashSet<>(jsonArrayToList(transitions.getJSONArray(j)));
                Set<String> newRow = new HashSet<>();
                for (String element : row) {
                    if (element.equals("-")) {
                        newRow.add(null);
                    } else {
                        newRow.add(element);
                    }
                }


                map.put(columns.get(j), newRow);
            }
        }


        return resultMap;
    }

    public Set<String> readStartStates() {
        JSONArray beginState = json.getJSONArray("beginState");
        return jsonArrayToSet(beginState);
    }

    public List<String> readEndStates() {
        JSONArray endState = json.getJSONArray("endState");
        return jsonArrayToList(endState);
    }

    private List<String> jsonArrayToList(JSONArray array) {
        List<Object> objects = array.toList();

        List<String> strings = new ArrayList<>();
        for (Object object : objects) {
            strings.add(object.toString());
        }

        return strings;
    }

    private Set<String> jsonArrayToSet(JSONArray array) {
        return new HashSet<>(jsonArrayToList(array));
    }
}


