package com.makroid.result;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private EditText ed;
    private final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<String> arrayList;
    private String rollNumber = "";
    private RequestQueue requestQueue; // for Volley
    private SharedPreferences settings;
    boolean fetch_data = false;
    private SharedPreferences.Editor editor;
    private Toolbar toolbar;
    private Button btn;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences(getString(R.string.prefs_key), 0);
        setContentView(R.layout.activity_main);
        /* call this if call from third activity */
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("roll")) {
            rollNumber = getIntent().getExtras().getString("roll");

            // Load Url using this function
            loadArrayList();
            Log.e(TAG, "onCreate: This create " + getIntent().getExtras().getString("roll") +"   " + arrayList.size());
            Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
            intent.putStringArrayListExtra(getString(R.string.intent_key), arrayList);
            startActivity(intent);
        }


        // Initialize vies
        initViews();
        // Set up toolbar
        setToolbar();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout relativeLayout;
                relativeLayout = (RelativeLayout) findViewById(R.id.relative);
            /* Used to hide keyboard after pressing submit button */
                InputMethodManager IMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                IMM.hideSoftInputFromWindow(relativeLayout.getWindowToken(), 0);

                rollNumber = ed.getText().toString();
                if (rollNumber.length() == 11) {
                    loadArrayList();
                    if (arrayList.size() > 1) {
                        mProgressDialog = new ProgressDialog(MainActivity.this);
                        mProgressDialog.setMessage("Fetching result.....");
                        mProgressDialog.show();
                        FetchUrl(arrayList, 0);
                    }
                } else
                    showError();
            }
        });
        // For submit button via keyBoard
        ed.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //this is used to detect via keyboard
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    rollNumber = ed.getText().toString();
                    if (rollNumber.length() == 11) {
                        loadArrayList();
                        FetchUrl(arrayList,0);
                    } else
                        showError();
                    return true;
                }
                return false;
            }
        });

        // Volley
        requestQueue = Volley.newRequestQueue(this);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn = (Button) findViewById(R.id.button);
        ed = (EditText) findViewById(R.id.EditText);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Search");
    }

    private void loadArrayList() {

        // ArrayList to store links and last index is used to store roll_number
        arrayList = new ArrayList<>();
        arrayList.add("https://raw.githubusercontent.com/Himanshuarora97/Result/master/jsonFiles/1Sem.json");
        arrayList.add("https://raw.githubusercontent.com/Himanshuarora97/Result/master/jsonFiles/2Sem.json");
        arrayList.add("https://raw.githubusercontent.com/Himanshuarora97/Result/master/jsonFiles/3Sem.json");
        arrayList.add("https://raw.githubusercontent.com/Himanshuarora97/Result/master/jsonFiles/3Sem.json");
        arrayList.add("https://raw.githubusercontent.com/Himanshuarora97/Result/master/jsonFiles/3Sem.json");
        arrayList.add("https://raw.githubusercontent.com/Himanshuarora97/Result/master/jsonFiles/3Sem.json");
        arrayList.add("https://raw.githubusercontent.com/Himanshuarora97/Result/master/jsonFiles/3Sem.json");
        arrayList.add(rollNumber);
    }

    private void FetchUrl(final ArrayList<String> arrayList, final int index) {
        String link = "";
        if (arrayList.size() > 1 && index < arrayList.size() - 1)
            link = arrayList.get(index);
        else {
            mProgressDialog.dismiss();
            if (fetch_data) {
                Log.e(TAG, "Second Activity starts here");
                Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                intent.putStringArrayListExtra(getString(R.string.intent_key), arrayList);
                startActivity(intent);
            } else
                Toast.makeText(MainActivity.this, "Roll number not found", Toast.LENGTH_SHORT).show();
            return;
        }
        // if SharedPreference does not contain link then fetch ..
        if (!settings.contains(link)) {
            final String finalLink = link;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // if (String.valueOf(response).contains(rollNumber))
                            if (response.has(rollNumber))
                                fetch_data = true;
                            editor = settings.edit();
                            editor.putString(finalLink, String.valueOf(response)).apply();
                            FetchUrl(arrayList, index + 1);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mProgressDialog.dismiss();
                        }
                    });
            requestQueue.add(jsonObjectRequest);
        } else {
            String response = settings.getString(link, "");
            if (response.contains(rollNumber))
                fetch_data = true;
            FetchUrl(arrayList, index + 1);
        }
    }



    void showError() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Length of Roll Number should be 11")
                .setMessage("Try Again with correct one")
                .setPositiveButton(android.R.string.yes, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
