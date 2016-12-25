package com.makroid.result;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class DisplayMessageActivity extends AppCompatActivity {
    TextView text;
    Toolbar toolbar;
    String Title;
   CoordinatorLayout rl;
    JSONObject jsonObject;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        rl = (CoordinatorLayout) findViewById(R.id.main_content);
        String message = getIntent().getExtras().getString("message"); // contains JsonObject
        try {
            jsonObject = new JSONObject(message);
            Title = (String) jsonObject.get("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("", message);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Title);
        setSupportActionBar(toolbar);
        Random r = new Random();
        int Low = 1;
        int High = 3;
        int Result = r.nextInt(High-Low) + Low;
        if(Result==1)
            anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        else
            anim = AnimationUtils.loadAnimation(this,R.anim.rotate);
//        text = (TextView) findViewById(R.id.textView);
//        text.setText(message);
//        text.setTextSize(10);
    }
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
            rl.startAnimation(anim);
    }
}
