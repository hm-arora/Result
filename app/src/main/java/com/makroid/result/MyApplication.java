package com.makroid.result;

import android.app.Application;

import org.json.JSONObject;

/**
 * Created by himanshu on 12/31/16.
 */

public class MyApplication extends Application {

    private JSONObject jsonObject;

    public JSONObject getSomeVariable() {
        return jsonObject;
    }

    public void setSomeVariable(JSONObject someVariable) {
        this.jsonObject = someVariable;
    }
}
