package com.makroid.result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static java.lang.StrictMath.round;

/**
 * Created by himanshu on 12/28/16.
 */
public class Information {
    public static ListAgainInformation getSortedData(String urlstring,String roll_number){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(urlstring);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<ListInformation> data = null;
        try {
            data = getData(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(data,new ListInformation());


        int pos = 0;
        boolean find  = true;
        for(int i=0;i<data.size();i++) {
            data.get(i).setRank(i + 1);
            if(roll_number.equals(data.get(i).getRoll()))
               find = false;
            if(find)
                pos++;
        }
        ListAgainInformation againInformation = new ListAgainInformation();
        againInformation.setList(data);
        againInformation.setPosition(pos);
        return againInformation;
    }

    public static List<ListInformation> getData(JSONObject jsonObject) throws JSONException {
//        int i = 0;
        List<ListInformation> data = new ArrayList<>();
        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            ListInformation listInformation = new ListInformation();
            String key = it.next();
            JSONObject object = jsonObject.getJSONObject(key).getJSONObject("Exams");
            Integer[] scheme = TotalMarks(object);
//            System.out.println(key);
            int credits = 27;
            int total_marks = scheme[0];
            int credit_marks = scheme[1];
            int total_subjects = scheme[2];
            double percentage = round(((double) total_marks / (double) total_subjects),2);
            double credit_percentage = round(((double)credit_marks / (double)credits),2);
            String name = jsonObject.getJSONObject(key).getString("name");
            listInformation.setName(name);
            listInformation.setRoll(key);
            listInformation.setTotalMarks(total_marks);
            listInformation.setCreditMarks(credit_marks);
            listInformation.setPercentage(percentage);
            listInformation.setCreditPercentage(credit_percentage);
            data.add(listInformation);
//            i++;
//            if(i==5)
//                break;
        }
        return data;
    }

    public static Integer[] TotalMarks(JSONObject value) throws JSONException {
        String regex = "\\(.+\\)";
        ArrayList<String> examList = new ArrayList<>();
        int Total_marks = 0,Credit_marks = 0;
        for (Iterator<String> it = value.keys(); it.hasNext(); ) {
            String key = it.next();
            examList.add(key);
        }
        Integer[] marking = new Integer[3];
        for(String key :examList) {
            int marks;
            try{
                marks = Integer.parseInt(((String) ((JSONArray) value.get(key)).get(1)).replaceAll(regex,""));
            }
            catch (NumberFormatException e){
                continue;
            }
            Total_marks += marks;
            if(marks >= 40)
            Credit_marks += marks * Integer.parseInt(((String) ((JSONArray) value.get(key)).get(2)));
        }
        marking[0] = Total_marks;
        marking[1] = Credit_marks;
        marking[2] = examList.size();
        return marking;
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
