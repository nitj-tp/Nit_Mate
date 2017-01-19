package com.parveen.nitmate;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.Thing;

public class location extends Activity {

    private static final int RESULT_PICK_CONTACT = 1;
    private static final int PICK_CONTACT = 12;
    Button btnGPSShowLocation;
    Button btnShowAddress;
    TextView tvAddress,e1;
    String s1, s2;

    AppLocationService appLocationService;
    public SharedPreferences sharedpreferences;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        appLocationService = new AppLocationService(location.this);
        sharedpreferences = getSharedPreferences("Address", Context.MODE_PRIVATE);

        btnShowAddress = (Button) findViewById(R.id.btnShowAddress);
        btnShowAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                Location location = appLocationService.getLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude, getApplicationContext(), new GeocoderHandler());
                } else {
                    showSettingsAlert();
                }

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(location.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
   /* public Action getIndexApiAction() {
        Thing object = new Thing.Builder().setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]")).build();
        return new Action.Builder(Action.TYPE_VIEW).setObject(object).setActionStatus(Action.STATUS_TYPE_COMPLETED).build();
    }*/

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        // AppIndex.AppIndexApi.start(client, getIndexApiAction());
        //client.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //AppIndex.AppIndexApi.end(client, getIndexApiAction());
        //client.disconnect();
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            tvAddress.setText(locationAddress);
        }
    }

    public void pickContact(View v) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);

    }

    public void contact(View v1) {

        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
                case PICK_CONTACT:
                    contactPicked1(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }

    public void contactPicked1(Intent data) {
        try {
            Uri localUri = data.getData();
            Cursor localCursor = getContentResolver().query(localUri, null, null, null, null);
            localCursor.moveToFirst();
            int i = localCursor.getColumnIndex("data1");
            localCursor.getColumnIndex("display_name");
            s2 = localCursor.getString(i).toString();
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
            localBuilder.setMessage("You want to ask the location ?");
            localBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent localIntent = new Intent(location.this.getApplicationContext(), MainActivity.class);
                    PendingIntent localPendingIntent = PendingIntent.getActivity(location.this.getApplicationContext(), 0, localIntent, 0);
                    SmsManager localSmsManager = SmsManager.getDefault();
                    //String str =sharedpreferences.getString("Address", "test");
                    localSmsManager.sendTextMessage(s2, null, "Please send me your location ", localPendingIntent, null);
                    Toast.makeText(getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
                }
            });
            localBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    location.this.finish();
                }
            });
            localBuilder.create().show();
            return;
            //showSettingsAlert();

        } catch (Exception localException) {
            localException.printStackTrace();
        }

    }


    public void contactPicked(Intent data) {
        try {
            Location localLocation = this.appLocationService.getLocation("gps");
            if (localLocation != null) {
                double d1 = localLocation.getLatitude();
                double d2 = localLocation.getLongitude();
                new LocationAddress();
                LocationAddress.getAddressFromLocation(d1, d2, getApplicationContext(), new GeocoderHandler());
            }
            while (true) {
                Uri localUri = data.getData();
                Cursor localCursor = getContentResolver().query(localUri, null, null, null, null);
                localCursor.moveToFirst();
                int i = localCursor.getColumnIndex("data1");
                localCursor.getColumnIndex("display_name");
                s1 = localCursor.getString(i).toString();
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
                localBuilder.setMessage("You want to share your location ?");
                localBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent localIntent = new Intent(location.this.getApplicationContext(), location.class);
                        PendingIntent localPendingIntent = PendingIntent.getActivity(location.this.getApplicationContext(), 0, localIntent, 0);
                        SmsManager localSmsManager = SmsManager.getDefault();
                        String str =sharedpreferences.getString("Address", "test");
                        localSmsManager.sendTextMessage(s1, null, "I reach \n " + str, localPendingIntent, null);
                        Toast.makeText(getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
                    }
                });
                localBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        location.this.finish();
                    }
                });
                localBuilder.create().show();
                return;
                //showSettingsAlert();
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

}