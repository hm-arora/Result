package com.makroid.result;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

public class DisplayMessageActivity extends AppCompatActivity {
    TextView text;
    Toolbar toolbar;
    String Title;
   RelativeLayout rl;
    JSONObject jsonObject;
    private RecyclerView recView;
    private MyAdapter adapter;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        rl = (RelativeLayout) findViewById(R.id.relative);

        String message = getIntent().getExtras().getString("message"); // contains JsonObject
        try {
            jsonObject = new JSONObject(message);
            Title = (String) jsonObject.get("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("", message);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Title);
        Random r = new Random();
        int Low = 1;
        int High = 3;
        int Result = r.nextInt(High-Low) + Low;
        if(Result==1)
            anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        else
            anim = AnimationUtils.loadAnimation(this,R.anim.rotate);
        recView = (RecyclerView)findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));
        try {
            adapter = new MyAdapter(DeepData.getData(jsonObject),this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recView.setAdapter(adapter);
        SlideInRightAnimationAdapter alphaAdapter = new SlideInRightAnimationAdapter(adapter);
        recView.setAdapter(alphaAdapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
//        recView.setAdapter(alphaAdapter);
    }
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
            rl.startAnimation(anim);
    }
}
