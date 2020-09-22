package com.nyit.snomedct.UMLS;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class GetTickets {

    public  GetTickets() {}
    String ticket;



    public String GetIt(String string) {


        TGTSingleton tgtSingleton = TGTSingleton.getInstance();
        final String tgt = tgtSingleton.getTGT();
        String url = string + tgt;

        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("service", "http://umlsks.nlm.nih.gov")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                ticket = response.body().string();

                TicketSingleton ticketSingleton = TicketSingleton.getInstance();
                ticketSingleton.setTicket(ticket);
                Log.d(TAG, "onResponse: get ticket: " + ticket);

            }
        });



        return ticket;


    }
}
