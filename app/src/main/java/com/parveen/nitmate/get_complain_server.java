package com.parveen.nitmate;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class get_complain_server extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_complain_server);
        getData();
    }

    private void getData() {

        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = "http://nitmate.esy.es/getcomplains.php";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

        ArrayList<String> topic  = new ArrayList<>();
        ArrayList<String> description  = new ArrayList<>();
        ArrayList<String> to  = new ArrayList<>();
        ArrayList<String> name  = new ArrayList<>();

        JSONArray result = null;
        try {
            JSONObject jsonObject = new JSONObject(response);
            result = jsonObject.getJSONArray("result");

            for(int i =0;i<result.length();i++) {
                JSONObject collegeData = result.getJSONObject(i);

                topic.add( collegeData.getString("topic"));
                description.add(collegeData.getString("description"));
                to.add(collegeData.getString("to"));
                name.add(collegeData.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // StringBuilder sb = new StringBuilder();
        Intent in  = new Intent(getApplicationContext() ,getcomplains.class);
        in.putStringArrayListExtra("topic", topic);
        in.putStringArrayListExtra("description", description);
        in.putStringArrayListExtra("to", to);
        in.putStringArrayListExtra("name", name);
        startActivity(in);
        finish();
    }

    @Override
    public void onClick(View v) {
        getData();
    }
}
