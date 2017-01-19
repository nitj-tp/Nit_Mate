package com.parveen.nitmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    login2 l2;

    //#######################################
    LinearLayout ll;
    public int currentimageindex=0;
    Timer timer;
    TimerTask task;
    ImageView slidingimage;

    private int[] IMAGE_IDS = {
            R.drawable.a, R.drawable.b
    };
    GridView grid;
    String[] web = {
            "Attendance",
            "Placements",
            "Students",
            "ComplainBox",
            "Teachers" ,
            "Video"
    } ;
    int[] imageId = {
            R.drawable.att,
            R.drawable.placement,
            R.drawable.student,
            R.drawable.complainbox,
            R.drawable.teacher,
            R.drawable.video
    };
    //#############################
  private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //##########################
        setTitle("Nit Mate");
        CustomGrid adapter = new CustomGrid(getApplicationContext(), web, imageId);
        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(position ==0)
                {
                        Intent in0 = new Intent(getApplicationContext(), ListDemo.class);
                        startActivity(in0);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), check_internet.class);
                    intent.putExtra("position", position);
                    Toast.makeText(getApplicationContext(), "You Clicked at " + position + "  " + web[+position], Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }

            }
        });


        setTitle("NIT Jalandhar");
        setTitleColor(Color.WHITE);
        //##############################
        //******************************************
        final Handler mHandler = new Handler();

        // Create runnable for posting
        final Runnable mUpdateResults = new Runnable() {
            public void run() {

                AnimateandSlideShow();

            }
        };

        int delay = 1000; // delay for 1 sec.

        int period = 8000; // repeat every 4 sec.

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {

                mHandler.post(mUpdateResults);

            }
        }, delay, period);

    //**********************************************
//        setSupportActionBar(toolbar);

     //Intent intent = getIntent();
       /* String sname = intent.getExtras().getString("name");
        String semail = intent.getExtras().getString("email");
        String imageurl  = intent.getExtras().getString("image");*/

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String sname = preferences.getString("shared_name", "");
        final String semail = preferences.getString("shared_email", "");
        final String imageurl = preferences.getString("image", "");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.name);
        TextView nav_email = (TextView)hView.findViewById(R.id.email);
        ImageView personimage = (ImageView)hView.findViewById(R.id.personImage);

        nav_user.setText(sname);
        nav_email.setText(semail);
        //Picasso.with(this).load(Uri.parse(imageurl)).into(personimage);

      //  ImageView cover = navigationView.getHeaderBackgroundView(); //get your ImageView
        if(imageurl.equals("empty")) {
            personimage.setImageResource(R.drawable.user);
        }
        else
        {
            Glide.with(getApplicationContext()).load(imageurl).into(personimage);

        }
      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    public void onClick(View v) {

        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * Helper method to start the animation on the splash screen
     */
    private void AnimateandSlideShow() {

        slidingimage = (ImageView)findViewById(R.id.imageView);
        slidingimage.setImageResource(IMAGE_IDS[currentimageindex%IMAGE_IDS.length]);

        currentimageindex++;

        Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.custom_anim);

        slidingimage.startAnimation(rotateimage);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_complain) {
            Intent intent = new Intent(getApplication() , complainbox.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.nav_location) {

            Intent intent = new Intent(getApplication() , location.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_support) {
            Toast.makeText(getApplicationContext(), "clicked  supoort", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplication() , support.class);
            startActivity(intent);

        } else if (id == R.id.nav_signout) {

         signOut();
        }
        else if (id == R.id.nav_help) {
            //finish();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),login2.class);
                        startActivity(i);
                    }
    });
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }
}
