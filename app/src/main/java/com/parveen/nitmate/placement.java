package com.parveen.nitmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class placement extends AppCompatActivity implements View.OnClickListener {
    JSONArray result;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement);

       /* editTextId = (EditText) findViewById(R.id.editTextId);
        buttonGet = (Button) findViewById(R.id.buttonGet);
        textViewResult = (TextView) findViewById(R.id.textViewResult);*/
        getData();
        //buttonGet.setOnClickListener(this);
    }

    private void getData() {
       /* String id = editTextId.getText().toString().trim();
        if (id.equals("")) {
            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
            return;
        }*/
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = Config.DATA_URL;

        StringRequest stringRequest = new StringRequest(url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(placement.this,"Server Down",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        ArrayList<String> company =new ArrayList<>();
        ArrayList<String> post =new ArrayList<>();
        ArrayList<String> lpa = new ArrayList<>();
        ArrayList<String> branch = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            result = jsonObject.getJSONArray(Config.JSON_ARRAY);


            for (int i = 0; i < result.length(); i++) {
                JSONObject collegeData = result.getJSONObject(i);


                company.add(collegeData.getString(Config.KEY_COMPANY));
                post.add(collegeData.getString(Config.KEY_POST));
                lpa.add(collegeData.getString(Config.KEY_LPA));
                branch.add(collegeData.getString(Config.KEY_BRANCH));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent in=new Intent(getApplicationContext(),CardViewActivity.class);
        in.putStringArrayListExtra("company",company);
        in.putStringArrayListExtra("post",post);
        in.putStringArrayListExtra("lpa",lpa);
        in.putStringArrayListExtra("branch",branch);

        startActivity(in);

       this.finish();
    }

    @Override
    public void onClick(View v) {
        // getData();
    }
}
