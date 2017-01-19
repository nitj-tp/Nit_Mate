package com.parveen.nitmate;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class getcomplains extends AppCompatActivity {
    ImageView back;
    GridView grid;
    /* int[] imagesid = {
             R.mipmap.ic_launcher,
             R.mipmap.ic_launcher,
             R.mipmap.ic_launcher,
             R.mipmap.ic_launcher
     };*/
    ArrayList<String> topic;
    ArrayList<String> description;
    ArrayList<String> to;
    ArrayList<String> name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getcomplains);
        Intent intent = getIntent();

        //###########
        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(in);
            }
        });
        //############
        topic =  intent.getStringArrayListExtra("topic");
        description = intent.getStringArrayListExtra("description");
        to = intent.getStringArrayListExtra("to");
        name = intent.getStringArrayListExtra("name");

        CustomGridc adapter = new CustomGridc(getApplicationContext(), topic);
        grid = (GridView) findViewById(R.id.gridc);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(getApplicationContext(), "You Clicked at " +position+"  "+ topic.get(position)+" "+description.get(position), Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext() , fullcomplain.class);
                intent1.putExtra("topic" ,topic.get(position) );
                intent1.putExtra("description" ,description.get(position) );
                intent1.putExtra("to" ,to.get(position) );
                intent1.putExtra("name" ,name.get(position) );
                startActivity(intent1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(in);
            }
        });
    }
}
