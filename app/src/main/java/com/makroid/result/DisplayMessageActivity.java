package com.makroid.result;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.eftimoff.viewpagertransformers.RotateDownTransformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DisplayMessageActivity extends AppCompatActivity {
    private static final String JSON = "JSONOBJECT";
    private final String TAG = DisplayMessageActivity.class.getName();
    Toolbar toolbar;
    String Title;
    JSONObject jsonObject;
    ArrayList<String> arrayList;
    String message = "";
    SharedPreferences settings;
    String name = "";
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    String college;
    ProgressDialog progressDialog;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

        }
    };
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing Result ...");
        progressDialog.show();
        Calculate();
        initToolbar();
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(arrayList.size()-1);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new RotateDownTransformer());
        if(arrayList.size()<=3)
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doHere();
            }
        },2000);
    }

    private void doHere() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i=1;i<=arrayList.size();i++) {
                    Log.e(TAG,"working or not " + i);
                    FragmentActivity fragmentActivity = new FragmentActivity().newInstance(message,arrayList.get(0));
                    adapter.addFragment(fragmentActivity,(0+i)+" Semester");
                }
                adapter.notifyDataSetChanged();
            }
        });
        progressDialog.dismiss();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Title);
        getSupportActionBar().setSubtitle("( "+message+" )");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void Calculate() {
        settings   = getSharedPreferences(JSON, 0);
        arrayList = getIntent().getExtras().getStringArrayList("message");
        if(arrayList!=null) {
            message = arrayList.get(arrayList.size() - 1);
            String FILENAME = settings.getString(arrayList.get(0), "");
            if (!FILENAME.isEmpty()) {
                name = FILENAME;
            }
            arrayList.remove(arrayList.size() - 1);
        }
        try {
            jsonObject = new JSONObject(name);
            jsonObject = jsonObject.getJSONObject(message);
            Title = (String) jsonObject.get("name");
            college = (String) jsonObject.get("college");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
