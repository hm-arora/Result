package com.makroid.result;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.PersistableBundle;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ed;
    private Button btn;
    private final String TAG = MainActivity.class.getName();
    int count = 0;
    private ArrayList<String> arrayList;
    String message = "";
    final String JSON = "JSONOBJECT";
    RequestQueue requestQueue;
    SharedPreferences settings;
    boolean fetch_data = false;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"OnView Created");
         settings = getSharedPreferences(JSON, 0);
        editor = settings.edit();
        setContentView(R.layout.activity_main);

        /* call this if call from third activity */
        if(getIntent().getExtras()!=null && getIntent().getExtras().containsKey("roll")){
            message = getIntent().getExtras().getString("roll");
            loadArrayList();
            Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
            intent.putStringArrayListExtra("message", arrayList);
            startActivity(intent);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search");
        btn = (Button) findViewById(R.id.button);
        ed = (EditText) findViewById(R.id.EditText);
        btn.setOnClickListener(this);
        // For submit button via keyBoard
        ed.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                          message = ed.getText().toString();
                    if(message.length() == 11) {
                        loadArrayList();
                        new taskExecute().execute();
                    }
                    else
                    showError();
                    return true;
                }
                return false;
            }
        });

        // Used for Volley
        requestQueue = Volley.newRequestQueue(this);
    }

    private void loadArrayList() {
        arrayList = new ArrayList<>();
        arrayList.add("https://raw.githubusercontent.com/Himanshuarora97/IPU-Result/master/jsonFiles/Sem.json");
        arrayList.add(message);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            RelativeLayout relativeLayout;
            relativeLayout = (RelativeLayout) findViewById(R.id.relative);
            /* Used to hide keyboard after pressing submit button */
            InputMethodManager IMM = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            IMM.hideSoftInputFromWindow(relativeLayout.getWindowToken(),0);

            message = ed.getText().toString();
            if(message.length() == 11) {
                loadArrayList();
                new taskExecute().execute();
            }
            else
                showError();
        }
    }

    private void TaskMethod(final String link) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(!settings.contains(link))
                                editor.putString(link, String.valueOf(response)).commit();
                                if (response.has(message)) {
                                    JSONObject Object = response.getJSONObject(message);
                                } else
                                    Toast.makeText(MainActivity.this, "Roll number not found", Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "DataBase Not found ", Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
    }

    class taskExecute extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < arrayList.size()-1; i++) {
                // if it already present in SharedPreferences
                if(!settings.contains(arrayList.get(i))) {
                    try {
//                        HttpURLConnection.setFollowRedirects(false);
//                        HttpURLConnection con =
//                                (HttpURLConnection) new URL(arrayList.get(i)).openConnection();
//                        con.setRequestMethod("HEAD");
//                        // check if url exist or not
//                        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                            Log.e(TAG,"Ok or not");
                            TaskMethod(arrayList.get(i));
//                        }
//                        else
//                            break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
                Log.e(TAG,"Value " + i);
            }
            if(arrayList.size() > 1){
                String resposne = settings.getString(arrayList.get(0),"");
                if(resposne.contains(message))
                    fetch_data = true;
            }
            Log.e(TAG,"fetch data " + fetch_data);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Log.e(TAG, "Post Execute fetch_data  " + String.valueOf(fetch_data));
                if (fetch_data) {
                    Log.e(TAG, "Second Activity starts here");
                    Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                    intent.putStringArrayListExtra("message", arrayList);
                    startActivity(intent);
                }


                else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Try Again")
                            .setMessage("Roll number not found in Database")
                            .setPositiveButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Fetching The File....");
            progressDialog.show();
        }
    }
    void showError(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Roll number length should be 11")
                .setMessage("Try Again with correct one")
                .setPositiveButton(android.R.string.yes, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
