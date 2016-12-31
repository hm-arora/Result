package com.makroid.result;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FourthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        String name = getIntent().getExtras().getString("start");
        TextView txt = (TextView) findViewById(R.id.textView);
        txt.setText(name);
    }
}
