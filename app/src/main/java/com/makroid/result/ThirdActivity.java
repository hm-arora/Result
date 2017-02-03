package com.makroid.result;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.makroid.result.InformationClass.Information;
import com.makroid.result.InformationClass.ListAgainInformation;
import com.makroid.result.InformationClass.ListInformation;
import com.makroid.result.adapters.RankAdapter;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String JSON = "JSONOBJECT";
    Toolbar toolbar;
    ListView listView;
    int position = 0;
    private ProgressDialog pdia;

    private RecyclerView recyclerView;
    private RankAdapter rankAdapter;
    List<ListInformation> list;
    String urlstirng = "";
    String roll = "";
    String rank_system="";
    ArrayList<String> arrayList = new ArrayList<>();

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
        getSupportActionBar().setTitle("Rank");
        arrayList = getIntent().getStringArrayListExtra("test");
        roll = arrayList.get(0);
        rank_system = arrayList.get(1);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<ListInformation> filteredModelList = filter(list, newText);
        rankAdapter.setFilter(filteredModelList);
        return false;
    }

    public class ThirdAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            // Used to get Sorted object
            ListAgainInformation listAgainInformation = Information.getSortedData(urlstirng, roll,rank_system);
            position = listAgainInformation.getPosition();
            list = listAgainInformation.getList();
            rankAdapter = new RankAdapter(list, getBaseContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pdia != null)
                pdia.dismiss();
            recyclerView.setAdapter(rankAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(ThirdActivity.this));
            recyclerView.getLayoutManager().scrollToPosition(position);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(ThirdActivity.this);
            pdia.setMessage("Fetching data");
            pdia.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pdia.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_third, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        TextView textView = (TextView) searchView.findViewById(R.id.search_src_text);
        textView.setTextColor(Color.WHITE);
        searchView.setOnQueryTextListener(this);
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
    private List<ListInformation> filter(List<ListInformation> models, String query) {
        query = query.toLowerCase();final List<ListInformation> filteredModelList = new ArrayList<>();
        for (ListInformation model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
