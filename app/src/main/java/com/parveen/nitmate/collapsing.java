package com.parveen.nitmate;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class collapsing extends AppCompatActivity implements View.OnClickListener{
    Intent in;
    int i;
    calls call_ser;
    TextView tn,td,te,tc;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing);

        imageView=(ImageView)findViewById(R.id.bgheader);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        in = getIntent();
        i = in.getExtras().getInt("namee");

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setTitle(""+GetAlImages.names[i]);
        collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.colorAccent));
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar);

        imageView.setImageBitmap(GetAlImages.bitmaps[i]);

        tn=(TextView)findViewById(R.id.tnn);
        td= (TextView)findViewById(R.id.tdd);
        te=(TextView)findViewById(R.id.tee);
        tc=(TextView)findViewById(R.id.tcc);

        tn.setText(GetAlImages.names[i]);
        td.setText(GetAlImages.descriptions[i]);
        te.setText(GetAlImages.emails[i]);
        tc.setText(GetAlImages.contacts[i]);

        te.setOnClickListener(this);
        tc.setOnClickListener(this);
    }


    public void onClick(View v) {
        if(v.getId()==R.id.tee)
        {
            Intent in=new Intent(getApplicationContext(),email_services.class);
            in.putExtra("email",GetAlImages.emails[i]);
            startActivity(in);
        }
        if(v.getId()==R.id.tcc)
        {
            call_ser=new calls(GetAlImages.contacts[i].toString(),this);
            call_ser.call();
        }
    }

}
