package com.parveen.nitmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class support extends AppCompatActivity implements View.OnClickListener {

    private static final String REGISTER_URL = "http://www.nitmate.esy.es/suggestion.php";
    ImageView back;
    public static final String KEY_NAME = "name";
    public static final String KEY_SUGGEST = "suggest";
    public static final String KEY_EMAIL = "email";
    private EditText editTextSuggest;

    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        editTextSuggest = (EditText) findViewById(R.id.editTextSuggest);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);
        back= (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(in);
            }
        });
    }

    private void registerUser(){
        final String suggest = editTextSuggest.getText().toString().trim();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String name = preferences.getString("shared_name", "");
        final String email = preferences.getString("shared_email", "");


        //Toast.makeText(getApplicationContext() , "name = "+name , Toast.LENGTH_LONG).show();
        //final String name="Tajinder";
        //final String email="tajinder@gmail.com";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(support.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(support.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_NAME,name);
                params.put(KEY_SUGGEST,suggest);
                params.put(KEY_EMAIL, email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }
}
