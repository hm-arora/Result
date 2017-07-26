package com.makroid.result;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.makroid.result.model.Model;
import com.makroid.result.model.ListItem;
import com.makroid.result.adapters.ResultAdapter;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FragmentActivity extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean isLoaded = false;
    private List<ListItem> data;
    private RecyclerView recView;
    private ImageView imageView;
    private ProgressBar progressBar;
    private ResultAdapter adapter;
    String message = ";";
    SharedPreferences settings;
    LinearLayoutManager llm;
    boolean isTrue = false;
    JSONObject jsonObject;
    String JsonObjectString = "";
    FragmentManager fragmentManager;
    View view;

    public FragmentActivity newInstance(String param1, String param2) {
        FragmentActivity fragment = new FragmentActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_display, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        recView = (RecyclerView) view.findViewById(R.id.rec_list);
        imageView = (ImageView) view.findViewById(R.id.image);
        return view;
    }


    // Used to load fragment only once
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(true);
        if (this.isVisible() && isVisibleToUser && !isLoaded) {
            new loadData().execute();
            isLoaded = true; // fragment loaded
        }
    }

    private class loadData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Log.e(TAG, "doInBackground: Starts here ");
            try {
                settings = getActivity().getSharedPreferences(getString(R.string.prefs_key), 0);
                message = getArguments().getString(ARG_PARAM1);
                JsonObjectString = settings.getString(getArguments().getString(ARG_PARAM2), "");
                try {
                    jsonObject = new JSONObject(JsonObjectString).getJSONObject(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                data = Model.getData(JsonObjectString, message); // used to get the data
                if (data.size() > 0)
                    isTrue = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            if (isTrue)
                adapter = new ResultAdapter(data, getActivity());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.INVISIBLE);
            if (isTrue) {
                recView.setAdapter(adapter);
                recView.setLayoutManager(llm);
                recView.setHasFixedSize(true);
                recView.setVisibility(View.VISIBLE);
            } else
                imageView.setVisibility(View.VISIBLE);
            Log.e(TAG, "onPostExecute: FragmentActivity");
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.context, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.college:
                ArrayList<String> arrayList = new ArrayList<>();
                Intent intent = new Intent(getActivity(), ThirdActivity.class);
                arrayList.add(getArguments().getString(ARG_PARAM2));
                arrayList.add(message);
                arrayList.add("college");
                intent.putStringArrayListExtra("test", arrayList);
                startActivity(intent);
                break;
            case R.id.university:
                arrayList = new ArrayList<>();
                intent = new Intent(getActivity(), ThirdActivity.class);
                arrayList.add(getArguments().getString(ARG_PARAM2));
                arrayList.add(message);
                arrayList.add("overall");
                intent.putStringArrayListExtra("test", arrayList);
                startActivity(intent);
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
