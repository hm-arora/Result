package com.makroid.result;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {
    private static final String JSON = "JSONOBJECT";
    Toolbar toolbar;
    ListView listView;
    int position = 0;
    //        ArrayAdapter<String> adapter;
    private ProgressDialog pdia;

    private RecyclerView recyclerView;
    private RankAdapter rankAdapter;
    List<ListInformation> list;
    String urlstirng = "";
    String roll = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        SharedPreferences settings = getSharedPreferences(JSON, 0);
        String FILENAME = settings.getString("myFileName", "");
        if (!FILENAME.isEmpty()) {
            urlstirng = FILENAME;
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar_third);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Total Marks");
        roll = getIntent().getExtras().getString("test");
        listView = (ListView) findViewById(R.id.list_item);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        new ThirdAsyncTask().execute();
    }

    @Override
    public void onPause() {

        super.onPause();
        if (pdia != null)
            pdia.dismiss();
    }

    public class ThirdAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
//            stringArrayList = Information.getSortedData(urlstirng,roll);
//            list = (List<ListInformation>) stringArrayList.get(1);
            ListAgainInformation listAgainInformation = Information.getSortedData(urlstirng, roll);
            position = listAgainInformation.getPosition();
            list = listAgainInformation.getList();
            rankAdapter = new RankAdapter(list, getBaseContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (pdia != null)
                pdia.dismiss();
            super.onPostExecute(aVoid);
            recyclerView.setAdapter(rankAdapter);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThirdActivity.this);
            recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(ThirdActivity.this));
            recyclerView.getLayoutManager().scrollToPosition(position);
//            recyclerView.smoothScrollToPosition(position);
//            linearLayoutManager.scrollToPositionWithOffset(position, 20);
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
