package com.nyit.snomedct.UMLS;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class GetTicket extends AsyncTask<String, Void,String>  {


    private AsyncResponse listener;
    Context mContext;

    public GetTicket(AsyncResponse listener, Context context){
        this.listener=listener;
        mContext=context;
    }

    @Override
    protected String doInBackground(String... strings) {


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String tgt = preferences.getString("TGT",null);

        String results = null;
        TGTSingleton tgtSingleton = TGTSingleton.getInstance();

    //    String tgt = tgtSingleton.getTGT();
        String url = strings[0] +tgt;

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("service", "http://umlsks.nlm.nih.gov")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try {
            Response response = client.newCall(request).execute();

            results = response.body().string();

            TicketSingleton ticketSingleton = TicketSingleton.getInstance();
            ticketSingleton.setTicket(results);
            Log.d(TAG, "doInBackground:  TGT? :" + tgt);
            Log.d(TAG, "doInBackground:  GETTICKET? :" + results);

        } catch (IOException e) {

        }


        return results;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.processFinish(s);

    }

}
