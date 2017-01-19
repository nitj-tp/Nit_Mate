package com.parveen.nitmate;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class email_services extends Activity {
    EditText editTextTo, editTextSubject, editTextMessage;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_services);
        Intent in=getIntent();
        String email=in.getExtras().getString("email");

        editTextTo = (EditText) findViewById(R.id.editText1);
        editTextTo.setText(email);
        editTextSubject = (EditText) findViewById(R.id.editText2);
        editTextMessage = (EditText) findViewById(R.id.editText3);

        send = (Button) findViewById(R.id.button1);

        send.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String to = editTextTo.getText().toString();
                String subject = editTextSubject.getText().toString();
                String message = editTextMessage.getText().toString();

//String mail[]={id.getText().toString(),"ashutosh89685@gmail.com"};
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }

        });
    }
}