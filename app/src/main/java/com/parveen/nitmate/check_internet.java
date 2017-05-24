package com.parveen.nitmate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class check_internet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_internet);
        Intent intent = getIntent();
        int position = intent.getExtras().getInt("position");
        //Boolean  bs_netcheck=false;
        //bs_netcheck = netCheck();
        if(isOffline() == true)
        {
            displayAlert();
        }
        else
        {
            switch(position)
            {

                case 1: {
                    Intent in1 = new Intent(getApplicationContext(), placement.class);
                    startActivity(in1);
                    finish();
                    break;
                }
                case 2:
                {
                    Intent in2 = new Intent(getApplicationContext(), ImageListView.class);
                    startActivity(in2);
                    finish();
                    break;
                }
                case 3: {
                    Intent in3 = new Intent(getApplicationContext(), get_complain_server.class);
                    startActivity(in3);
                    finish();
                    break;
                }
                case 4: {
                    Intent in4 = new Intent(getApplicationContext(), lost_found_server.class);
                    startActivity(in4);
                    finish();
                    break;
                }
                case 5:
                {
                    Intent in5 = new Intent(getApplicationContext(), ImageListViewy.class);
                    startActivity(in5);
                    finish();
                    break;
                }
            }
        }

    }
    public  boolean isOffline() {
        Context context = getApplicationContext();
        ConnectivityManager conManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conManager.getActiveNetworkInfo();
        if ((i == null) || (!i.isConnected()) || (!i.isAvailable())) {

            return true;
        }
        return false;

    }
    public void displayAlert()
    {
        new AlertDialog.Builder(this).setMessage("Please Check Your Internet Connection and Try Again")
                .setTitle("Network Error")
                .setCancelable(false)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton){
                                dialog.cancel();
                                finish();
                            }
                        })
                .show();
    }
}
