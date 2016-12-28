package com.makroid.result;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        toolbar = (Toolbar) findViewById(R.id.toolbar_third);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Total Marks");
        ArrayList<Integer> test = getIntent().getIntegerArrayListExtra("message");
        text = (TextView) findViewById(R.id.text_view);
        text.setText(test.toString());
    }
}
