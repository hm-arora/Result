package com.makroid.result.model;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

// used to get List of rank in sorted paradigm (University or college rank)
public class Information {
    public static List<RankModel> getSortedData(String urlstring, String roll_number, String rank_system) {
        JSONObject jsonObject = null;
        String collegeName;
        try {
            jsonObject = new JSONObject(urlstring);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<RankModel> data = new ArrayList<>();
        try {
            // if college mention (for college rank) then get college rank data
            if (rank_system.equals("college")) {
                assert jsonObject != null;
                collegeName = jsonObject.getJSONObject(roll_number).getString("college");
                data = getData(jsonObject, collegeName);
            } else
                data = getData(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Sort data according to their credit percentage
        Collections.sort(data, new RankModel());
        return data;
    }

    // Used these method to getData of a particular college
    private static List<RankModel> getData(JSONObject jsonObject, String collegeName) throws JSONException {
        List<RankModel> data = new ArrayList<>();

        // Iterate over JsonObject to reterive information
        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            RankModel newData = new RankModel();
            String key = it.next(); // Used to reterive key of jsonObject
            String college = jsonObject.getJSONObject(key).getString("college");
            if (collegeName.equals(college)) {
                String name = jsonObject.getJSONObject(key).getString("name");
                String percentage = jsonObject.getJSONObject(key).getString("percentage");
                String creditPercentage = jsonObject.getJSONObject(key).getString("cPercentage");
                String total = jsonObject.getJSONObject(key).getString("total");
                String cRank = jsonObject.getJSONObject(key).getString("cRank");
                String uRank = jsonObject.getJSONObject(key).getString("uRank");

                // Setting
                newData.setRoll(key);
                newData.setName(name);
                newData.setCollege(college);
                newData.setCrank(cRank);
                newData.setPercentage(percentage);
                newData.setCreditPercentage(creditPercentage);
                newData.setTotalMarks(total);
                newData.setRank(uRank);

                // adding into arrayList
                data.add(newData);
            }
        }
        return data;
    }

    private static List<RankModel> getData(JSONObject jsonObject) throws JSONException {
        List<RankModel> data = new ArrayList<>();

        // Iterate over JsonObject to reterive information
        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            RankModel newData = new RankModel();
            String key = it.next(); // Used to reterive key of jsonObject
            String name = jsonObject.getJSONObject(key).getString("name");
            String college = jsonObject.getJSONObject(key).getString("college");
            String percentage = jsonObject.getJSONObject(key).getString("percentage");
            String creditPercentage = jsonObject.getJSONObject(key).getString("cPercentage");
            String total = jsonObject.getJSONObject(key).getString("total");
            String uRank = jsonObject.getJSONObject(key).getString("uRank");
            String cRank = jsonObject.getJSONObject(key).getString("cRank");

            // Setting data
            newData.setRoll(key);
            newData.setName(name);
            newData.setCollege(college);
            newData.setPercentage(percentage);
            newData.setCreditPercentage(creditPercentage);
            newData.setTotalMarks(total);
            newData.setCrank(cRank);
            newData.setRank(uRank);

            // add into arraylist
            data.add(newData);
        }
        return data;
    }
}
