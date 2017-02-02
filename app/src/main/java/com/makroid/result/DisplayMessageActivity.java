package com.makroid.result;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RelativeLayout;

import com.makroid.result.InformationClass.DeepData;
import com.makroid.result.InformationClass.ListItem;
import com.makroid.result.adapters.ResultAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DisplayMessageActivity extends AppCompatActivity {
    private static final String JSON = "JSONOBJECT";
    Toolbar toolbar;
    String Title;
    RelativeLayout rl;
    List<ListItem> data = new ArrayList<>();
    JSONObject jsonObject;
    private RecyclerView recView;
    private ResultAdapter adapter;
    Animation anim;
    String message="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        rl = (RelativeLayout) findViewById(R.id.relative);

        // This is used to get JsonObject

        String name="null";
        SharedPreferences settings = getSharedPreferences(JSON, 0);
        String FILENAME = settings.getString("myFileName", "");
        if (!FILENAME.isEmpty()) {
            name = FILENAME;
        }

        // This is used to get JsonObject

        message = getIntent().getExtras().getString("message"); // contains JsonObject
        try {
            jsonObject = new JSONObject(name);
            jsonObject = jsonObject.getJSONObject(message);
            Title = (String) jsonObject.get("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("", message);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        recView = (RecyclerView)findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));
        try {
            data = DeepData.getData(jsonObject); // used to get the data
            adapter = new ResultAdapter(data,this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recView.setAdapter(adapter);
//        SlideInRightAnimationAdapter alphaAdapter = new SlideInRightAnimationAdapter(adapter);
//        recView.setAdapter(alphaAdapter);
//        alphaAdapter.setDuration(1200);
//        alphaAdapter.setInterpolator(new OvershootInterpolator(5));
//        recView.setScrollBarFadeDuration(4000);

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
                intent.putExtra("test", message);
                startActivity(intent);
                finish(); // therefore it never come after this from back button
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
