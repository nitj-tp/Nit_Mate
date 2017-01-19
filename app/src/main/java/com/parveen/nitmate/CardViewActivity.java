package com.parveen.nitmate;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class CardViewActivity extends ActionBarActivity {
ImageView back;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
ArrayList<String> c=new ArrayList<>();
    ArrayList<String> p=new ArrayList<>();
    ArrayList<String> l=new ArrayList<>();
    ArrayList<String> b=new ArrayList<>();
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        back =(ImageView)findViewById(R.id.back);
Intent in=getIntent();

       c= in.getStringArrayListExtra("company");
        p= in.getStringArrayListExtra("post");
        l= in.getStringArrayListExtra("lpa");
        b= in.getStringArrayListExtra("branch");
        count=c.size();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        // Code to Add an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).addItem(obj, index);


        // Code to remove an item with default animation
        //((MyRecyclerViewAdapter) mAdapter).deleteItem(index);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(in);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }

    public ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        for (int i= 0; i < count; i++) {
            DataObject obj = new DataObject("Company : " + c.get(i),
                    "Post : " + p.get(i),"LPA : "+l.get(i),"Name : "+b.get(i));
            results.add(i, obj);
        }
        return results;
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        Toast.makeText(getApplicationContext() ,"back_press pressed" , Toast.LENGTH_LONG).show();
       // Intent inback = new Intent(getApplicationContext() , MainActivity.class);
        //startActivity(inback);

        finish();

    }
}