package com.parveen.nitmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewFullImage extends AppCompatActivity implements View.OnClickListener{
    private ImageView imageView;
    Intent in;
    int i;
    calls call_ser;
    TextView tn,td,te,tc;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        in = getIntent();
        i = in.getExtras().getInt("name");
        setTitle(""+GetAlImages.names[i]);
        setContentView(R.layout.activity_view_full_image);
        imageView = (ImageView) findViewById(R.id.imageViewFull);
        imageView.setImageBitmap(GetAlImages.bitmaps[i]);

        tn=(TextView)findViewById(R.id.tn);
        td= (TextView)findViewById(R.id.td);
        te=(TextView)findViewById(R.id.te);
        tc=(TextView)findViewById(R.id.tc);

        tn.setText(GetAlImages.names[i]);
        td.setText(GetAlImages.descriptions[i]);
        te.setText(GetAlImages.emails[i]);
        tc.setText(GetAlImages.contacts[i]);

        te.setOnClickListener(this);
        tc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.te)
        {
            Intent in=new Intent(getApplicationContext(),email_services.class);
            in.putExtra("email",GetAlImages.emails[i]);
            startActivity(in);
        }
        if(v.getId()==R.id.tc)
        {
            call_ser=new calls(GetAlImages.contacts[i].toString(),this);
            call_ser.call();
        }
    }
}