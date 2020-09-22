package com.nyit.snomedct;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nyit.snomedct.Database.DatabaseHelper;
import com.nyit.snomedct.UMLS.AsyncResponse;
import com.nyit.snomedct.UMLS.Authentication;

public class WelcomeActivity extends AppCompatActivity  {

    Authentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        TextView enterView = (TextView) findViewById( R.id.enter_button);

        // open the database and clear all the search keywords that has been last for more than seven days.

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        databaseHelper.updateHistoryTable();

        // get the authentication and TGT, then show the “Enter to Search” Button

        authentication = new Authentication(new AsyncResponse() {
            @Override
            public void processFinish(String ticket) {
                enterView.setVisibility(View.VISIBLE);
            }
        },getApplicationContext());


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            authentication.execute("https://utslogin.nlm.nih.gov/cas/v1/api-key");
            enterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent newIntent = new Intent(WelcomeActivity.this,TabsActivity.class);
                    startActivity(newIntent);

                }
            });


        } else {

            Toast.makeText(this, "no internet", Toast.LENGTH_SHORT).show();
            enterView.setText("No Internet connection");
        }


//        List<String> list = new ArrayList<>();
//        list.add("e");
//        list.add("3");
//        list.add("4");
//        list.add("5");
//
//        list.remove(list.size()-1);
//
//        Toast.makeText(this, "list: " + list.get(list.size()-1), Toast.LENGTH_SHORT).show();

    }


//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        mGoogleApiClient.connect();
//    }



}
