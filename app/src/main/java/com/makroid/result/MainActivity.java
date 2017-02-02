package com.makroid.result;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ed;
    private Button btn;
    private String link = "https://raw.githubusercontent.com/Himanshuarora97/HelloWorld/master/1stSem.json";
    String message = "";
    final String JSON = "JSONOBJECT" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search");
        btn = (Button) findViewById(R.id.button);
        ed = (EditText) findViewById(R.id.EditText);
        ed.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
//                    Toast.makeText(MainActivity.this, ed.getText(), Toast.LENGTH_SHORT).show();
                    message = ed.getText().toString();
                    new SimpleAsyncTask().execute("");
                    return true;
                }
                return false;
            }
        });
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            message = ed.getText().toString();
            new SimpleAsyncTask().execute("");
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    link = "https://raw.githubusercontent.com/Himanshuarora97/HelloWorld/master/1stSem.json";
                break;
            case R.id.radio_ninjas:
                if (checked)
                    link = "https://raw.githubusercontent.com/Himanshuarora97/HelloWorld/master/2ndSem.json";
                break;
            case R.id.radio_bcom:
                if (checked)
                    link = "https://raw.githubusercontent.com/Himanshuarora97/HelloWorld/master/BCOM.json";
                break;
        }
    }

    public class SimpleAsyncTask extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pdia;

        @Override
        protected JSONObject doInBackground(String... strings) {
            String urlstring = "";
            try {
                urlstring = methodName(link);
                try {
                    JSONObject jsonObject = new JSONObject(urlstring);
                    SharedPreferences settings = getSharedPreferences(JSON, 0); // PREFS_NAME should be a static final String property
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("myFileName", String.valueOf(jsonObject)).commit();
                    JSONObject Object = jsonObject.getJSONObject(message); // getJson from key(Enrollment Number)
                    return Object;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            pdia.dismiss();

            if (jsonObject == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Enroll.Number not Found");
                builder.setMessage("Try Again");
                builder.setNegativeButton("OK", null);
                AlertDialog dialog = builder.create();
//                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                dialog.show();
            } else {
                Intent intent = new Intent(getBaseContext(), DisplayMessageActivity.class);
                intent.putExtra("message", message);
                startActivity(intent);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(MainActivity.this);
            pdia.setMessage("Fetching data");
            pdia.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pdia.show();
//
        }
    }

    private static String methodName(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.addRequestProperty("User-Agent", "Mozilla/4.76");
        HttpURLConnection connection = urlConnection;
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String urlString = "";
        String current;
        while ((current = in.readLine()) != null) {
            urlString += current;
        }
        return urlString;
    }


}
