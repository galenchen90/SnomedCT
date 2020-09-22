package com.nyit.snomedct.UMLS;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class Authentication extends AsyncTask<String, Void,String> {


    public static final String BASE_URL = "https://utslogin.nlm.nih.gov/cas/v1/api-key";
    private static final String API = "b58cce13-f8c4-49b7-8497-5f8715060308";
    Context mConext;
    String tgt;

    private AsyncResponse listener;

    public Authentication(AsyncResponse listener, Context context){
        this.listener=listener;
        mConext=context;
    }

    @Override
    protected String doInBackground(String... strings) {

        // use okhttpclient to get the tgt string.

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("apikey", "b58cce13-f8c4-49b7-8497-5f8715060308")
                .build();
        Request request = new Request.Builder()
                .url(strings[0])
                .post(formBody)
                .build();

        Log.d(TAG, "doInBackground: string[0] is: "+ strings[0]);

        try {
            Response response = client.newCall(request).execute();

            String results = response.body().string();

            Log.d(TAG, "doInBackground:  result is :" + results);

            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(results);
            m.find();
            m.find();
            Log.d(TAG, "doInBackground:  TGTHttps is :" + m.group(1));
            String tgtHttps= m.group(1);
            tgt = tgtHttps.substring(tgtHttps.indexOf("y")+2);

            TGTSingleton tgtSingleton = TGTSingleton.getInstance();
            tgtSingleton.setTGT(tgt);


            // to store the TGT

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mConext);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("TGT",tgt);
            editor.apply();

            Log.d(TAG, "doInBackground:  TGT is :" + tgt);


        } catch (IOException e) {
             // Toast.makeText(mConext.getApplicationContext(), "faill to post", Toast.LENGTH_SHORT).show();
        }

        return tgt;


    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.processFinish(s);

    }



}
