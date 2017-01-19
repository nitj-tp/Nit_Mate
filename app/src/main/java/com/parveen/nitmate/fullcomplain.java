package com.parveen.nitmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class fullcomplain extends AppCompatActivity {
    TextView t1 , t2 , t3 , t4;
    String s1 , s2 , s3 , s4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullcomplain);
        t1 = (TextView)findViewById(R.id.fulltopic);
        t2 = (TextView)findViewById(R.id.fulldescription);
        t3 = (TextView)findViewById(R.id.fullto);
        t4 = (TextView)findViewById(R.id.fullname);

        Intent intent = getIntent();
        s1 = intent.getExtras().getString("topic");
        s2 = intent.getExtras().getString("description");
        s3 = intent.getExtras().getString("to");
        s4 = intent.getExtras().getString("name");

        t1.setText(s1);
        t2.setText(s2);
        t3.setText(s3);
        t4.setText(s4);
    }
}

