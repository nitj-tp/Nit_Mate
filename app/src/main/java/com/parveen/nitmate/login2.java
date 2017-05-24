package com.parveen.nitmate;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.Serializable;

public class login2 extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    public    String personName;
    public  String email;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private  GoogleSignInAccount acct;
    private SignInButton btnSignIn;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);

        btnSignIn.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()                 // it is used for the configuration of GoogleAPI (entry point of google sign in)
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)        //GoogleApiClient is used for integration of google play services in app
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);  // getting intent from GoogleSignInAPI by
        // passing our googleAPICLient
        startActivityForResult(signInIntent, RC_SIGN_IN);       //the activity is called in background and result is handled here
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

             personName = acct.getDisplayName();
            email = acct.getEmail();
            String personPhotoUrl;
            try {
                 personPhotoUrl = acct.getPhotoUrl().toString();
            }
            catch(Exception e)
            {
                personPhotoUrl="empty";
            }
           //setProfilePic(imgProfilePic);
//            txtName.setText(personName);
         //   txtEmail.setText(email);
            Intent intent = new Intent(getApplicationContext() , MainActivity.class);
         /*   intent.putExtra("name" , personName);
            intent.putExtra("email" ,email);
            intent.putExtra("image" , personPhotoUrl);*/

            preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("shared_name",personName);
            editor.putString("shared_email",email);
            editor.putString("image" , personPhotoUrl);
            editor.apply();

           // intent.putExtra("api" , (Serializable) mGoogleApiClient);
            startActivity(intent);
            finish();
           // updateUI(true);

        } else {
            // Signed out, show unauthenticated UI.
           // updateUI(false);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
                signIn();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {  // it gets reuest code which was passed by startActivity for result
        super.onActivityResult(requestCode, resultCode, data);                     // receive resultcode and data passed

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data); //GoogleSignInResult stores result of sign in request sent
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
       /* Intent intent_login = new Intent(getApplicationContext() , login_internet.class);
        startActivity(intent_login);*/
        Toast.makeText(getApplicationContext() , "Please Check your internet connection",Toast.LENGTH_LONG).show();
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}