package com.makroid.result.informationclass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Model {
    public static List<ListItem> getData(String jsonObjectString,String roll) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonObjectString).getJSONObject(roll);
        JSONObject value = (JSONObject) jsonObject.get("exams");
        List<String> examList = new ArrayList<>();
        for (Iterator<String> it = value.keys(); it.hasNext(); ) {
            String key = it.next();
            examList.add(key);
        }

        ArrayList<String> marks = new ArrayList<>();
        ArrayList<String> internal = new ArrayList<>();
        ArrayList<String> external = new ArrayList<>();
        for(String key :examList) {
            marks.add((String) ((JSONArray) value.get(key)).get(1));
            internal.add(((String) ((JSONArray) value.get(key)).get(0)).split(" ")[0]);
            external.add(((String) ((JSONArray) value.get(key)).get(0)).split(" ")[2]);
        }

            List<ListItem> data = new ArrayList<>();
            for(int i=0;i<marks.size();i++){
                ListItem item = new ListItem();
                item.setexam(examList.get(i));
                item.setmarks("Marks : " + marks.get(i));
                item.setinternal("Internal : "+internal.get(i));
                item.setexternal("External : "+external.get(i));
                data.add(item);
            }
        return data;
    }

}
