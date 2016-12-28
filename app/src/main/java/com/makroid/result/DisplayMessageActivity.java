package com.makroid.result;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

public class DisplayMessageActivity extends AppCompatActivity {
    TextView text;
    Toolbar toolbar;
    String Title;
   RelativeLayout rl;
    List<ListItem> data = new ArrayList<>();
    ArrayList<Integer> totalMarks = new ArrayList<>();
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
//        Typeface face = Typeface.createFromAsset(getAssets(),"fonts.ttf");
//        TextView yourTextView = null;
//        try {
//            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
//            f.setAccessible(true);
//            yourTextView = (TextView) f.get(toolbar);
//            yourTextView.setTypeface(face);
//        } catch (NoSuchFieldException e) {
//        } catch (IllegalAccessException e) {
//        }
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
            data = DeepData.getData(jsonObject);
            adapter = new MyAdapter(data,this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recView.setAdapter(adapter);
        SlideInRightAnimationAdapter alphaAdapter = new SlideInRightAnimationAdapter(adapter);
        recView.setAdapter(alphaAdapter);
        alphaAdapter.setDuration(1200);
        alphaAdapter.setInterpolator(new OvershootInterpolator(5));


        int sum = 0,a;
        for(ListItem item:data){
            a = Integer.parseInt(item.getmarks().replaceAll("[\\D]", ""));
            sum+=a;
            totalMarks.add(a);
        }
        totalMarks.add(sum);
        try {
            totalMarks.add(Integer.parseInt((String) jsonObject.get("Total credits")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recView.setScrollBarFadeDuration(4000);

//        recView.setAdapter(alphaAdapter);
    }
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
            rl.startAnimation(anim);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rank:
                Intent intent = new Intent(getBaseContext(), ThirdActivity.class);
                intent.putIntegerArrayListExtra("message", totalMarks);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
