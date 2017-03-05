package com.makroid.result.InformationClass;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static java.lang.StrictMath.round;

public class Information {
    public static List<RankModel> getSortedData(String urlstring, String roll_number, String rank_system){
        JSONObject jsonObject = null;
        String collegeName = "null";
        try {
            jsonObject = new JSONObject(urlstring);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<RankModel> data = new ArrayList<>();
        try {
            if(rank_system.equals("college")) {
                collegeName = jsonObject.getJSONObject(roll_number).getString("college");
                data = getData(jsonObject,collegeName);
            }
            else
                data = getData(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(data,new RankModel());
        return data;
    }

    private static List<RankModel> getData(JSONObject jsonObject, String collegeName) throws JSONException {
        List<RankModel> data = new ArrayList<>();
        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            RankModel newData = new RankModel();
            String key = it.next();
            String college = jsonObject.getJSONObject(key).getString("college");
            if (collegeName.equals(college)) {
                String name = jsonObject.getJSONObject(key).getString("name");
                String percentage = jsonObject.getJSONObject(key).getString("percentage");
                String creditPercentage = jsonObject.getJSONObject(key).getString("cPercentage");
                String total = jsonObject.getJSONObject(key).getString("total");
                String uRank = jsonObject.getJSONObject(key).getString("uRank");
                newData.setName(name);
                newData.setCollege(college);
                newData.setPercentage(percentage);
                newData.setCreditPercentage(creditPercentage);
                newData.setTotalMarks(total);
                newData.setRank(uRank);
            }
            data.add(newData);
        }
        return data;
    }

    public static List<RankModel> getData(JSONObject jsonObject) throws JSONException {
        List<RankModel> data = new ArrayList<>();
        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            RankModel newData = new RankModel();
            String key = it.next();
            String name = jsonObject.getJSONObject(key).getString("name");
            String college = jsonObject.getJSONObject(key).getString("college");
            String percentage = jsonObject.getJSONObject(key).getString("percentage");
            String creditPercentage = jsonObject.getJSONObject(key).getString("cPercentage");
            String total = jsonObject.getJSONObject(key).getString("total");
            String uRank = jsonObject.getJSONObject(key).getString("uRank");
            newData.setRoll(key);
            newData.setName(name);
            newData.setCollege(college);
            newData.setPercentage(percentage);
            newData.setCreditPercentage(creditPercentage);
            newData.setTotalMarks(total);
            newData.setRank(uRank);
            data.add(newData);
        }
        return data;
    }
}
