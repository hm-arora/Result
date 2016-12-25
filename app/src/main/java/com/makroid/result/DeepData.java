package com.makroid.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by himanshu on 12/25/16.
 */

public class DeepData {
    private static final String[] countries = {"India","Pakistan","China","Itlay"};
    private static final String[] capitals = {"New Delhi","Islamabad","Beijing","Rome"};
    public static List<ListItem> getData(){
        List<ListItem> data = new ArrayList<>();
        for(int x = 0;x<4;x++){
            for(int i=0;i<countries.length;i++){
                ListItem item = new ListItem();
                item.setCapital(capitals[i]);
                item.setCountry(countries[i]);
                data.add(item);
            }
        }
        return data;
    }
}
