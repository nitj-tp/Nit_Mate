package com.parveen.nitmate;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class complainbox extends AppCompatActivity implements View.OnClickListener {
ImageView back;
    private static final String REGISTER_URL = "http://www.nitmate.esy.es/complianbox.php";

    public static final String KEY_TOPIC = "topic";
    public static final String KEY_DES = "description";
    public static final String KEY_TO = "to";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    private EditText topic , description , to;

    private Button complain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complainbox);
        back = (ImageView)findViewById(R.id.back);
        topic = (EditText) findViewById(R.id.topic);
        description = (EditText) findViewById(R.id.description);
        to = (EditText) findViewById(R.id.to);


        complain = (Button) findViewById(R.id.complain);

        complain.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void complainsend(){
        final String stopic = topic.getText().toString().trim();
        final String sdescription = description.getText().toString().trim();
        final String sto = to.getText().toString().trim();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String name = preferences.getString("shared_name", "");
        final String email = preferences.getString("shared_email", "");

        // final String name="Tajinder";
        // final String email="tajinder@gmail.com";

        Toast.makeText(getApplicationContext() , stopic+sdescription+sto+name+email , Toast.LENGTH_LONG).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(complainbox.this,"  this is respoms "+response+"good",Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(complainbox.this,error.toString()+"bad",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_TOPIC,stopic);
                params.put(KEY_DES,sdescription);
                params.put(KEY_TO, sto);
                params.put(KEY_NAME ,name );
                params.put(KEY_EMAIL ,email);

                return params;
            }
        };
        Toast.makeText(getApplicationContext() , " "+stopic+"  "+sdescription+" "+sto,Toast.LENGTH_LONG).show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.complain){
            complainsend();
        }
        if(v.getId()==R.id.back)
        {

                    Intent in = new Intent(getApplicationContext() , MainActivity.class);
                    startActivity(in);
        }
    }
}
