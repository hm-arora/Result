package com.makroid.result;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class ThirdActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    //    ArrayAdapter<String> adapter;
    private RecyclerView recyclerView;
    private RankAdapter rankAdapter;
    List<ListInformation> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        toolbar = (Toolbar) findViewById(R.id.toolbar_third);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Total Marks");
        ArrayList<Integer> test = getIntent().getIntegerArrayListExtra("message");
        listView = (ListView) findViewById(R.id.list_item);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        new ThirdAsyncTask().execute();
    }

    public class ThirdAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pdia;

        @Override
        protected Void doInBackground(Void... voids) {
//            adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.single_row,R.id.singleText,Information.getSortedData());
            list = Information.getSortedData();
            rankAdapter = new RankAdapter(list, getBaseContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pdia.dismiss();
            super.onPostExecute(aVoid);
            recyclerView.setAdapter(rankAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(ThirdActivity.this));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(ThirdActivity.this);
            pdia.setMessage("Fetching data");
            pdia.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pdia.show();
//
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_third, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
