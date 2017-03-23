package com.makroid.result;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.eftimoff.viewpagertransformers.RotateDownTransformer;
import com.makroid.result.informationclass.Information;

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
    ArrayList<Fragment> fragmentActivities = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading please wait..... ");
        progressDialog.show();
        Calculate();
        initToolbar();
        viewPager.setPageTransformer(true, new RotateDownTransformer());
        if(arrayList.size()<=3)
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
            new simpleTask().execute();
    }

    class simpleTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            InformationFragment fragment = new InformationFragment().newInstance(Title,college,message);
            fragmentActivities.add(fragment);
            for (int i=0;i<=arrayList.size()-1;i++) {
                Log.e(TAG,"working or not " + i);
                FragmentActivity fragmentActivity = new FragmentActivity().newInstance(message,arrayList.get(i));
                fragmentActivities.add(fragmentActivity);
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewPager.setOffscreenPageLimit(fragmentActivities.size());
            adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentActivities);
            viewPager.setAdapter(adapter);
            Log.e(TAG, "onPostExecute: " + "end here");
            progressDialog.dismiss();
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            String name;
            try {
                name = Title.charAt(0) + Title.substring(1).toLowerCase();
            }
            catch (NullPointerException e){
                name = "null";
            }
            getSupportActionBar().setTitle(name);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void Calculate() {
        settings   = getSharedPreferences(JSON, 0);
        arrayList = getIntent().getExtras().getStringArrayList("message");
        if(arrayList!=null) {
            message = arrayList.get(arrayList.size() - 1);
            String FILENAME = settings.getString(arrayList.get(2), "");
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }
}
