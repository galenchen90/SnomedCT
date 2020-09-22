package com.nyit.snomedct.Tools;

import android.content.Context;
import android.os.AsyncTask;

import com.nyit.snomedct.APICallsInterface;
import com.nyit.snomedct.ConceptResults.AtomObject;
import com.nyit.snomedct.Relationship.Relations;
import com.nyit.snomedct.Relationship.Result;
import com.nyit.snomedct.UMLS.AsyncResponse;
import com.nyit.snomedct.UMLS.GetTicket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetParentTask extends AsyncTask<List<AtomObject>, Void,List<AtomObject>> {

    GetTicket getTicket;
    private AsyncResponseForParents listener;
    Context mContext;
    String url;
    List<AtomObject> atomObjectsForParents;

    public GetParentTask(AsyncResponseForParents listener, Context context, String url){
        this.listener=listener;
        mContext=context;
        this.url= url;
    }

    @Override
    protected List<AtomObject> doInBackground(List<AtomObject>... lists) {

      //  goOnline(url);

        getTicket = new GetTicket(new AsyncResponse() {
            @Override
            public void processFinish(String ticket) {


                String baseURL = url + "/";

                atomObjectsForParents = new ArrayList<>();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APICallsInterface apiCallsInterface = retrofit.create(APICallsInterface.class);
                Call<Relations> relationsCall = apiCallsInterface.getRelations(ticket);
                relationsCall.enqueue(new Callback<Relations>() {
                    @Override
                    public void onResponse(Call<Relations> call, Response<Relations> response) {

                        Relations relations = response.body();
                        List<Result> relationResults = relations.getResult();


                        for (com.nyit.snomedct.Relationship.Result result : relationResults) {

                            if (result.getAdditionalRelationLabel().equals("isa")) {
                                String conceptNameRelation = result.getRelatedIdName();
                                String relationContext = "is a";
                                String uri = result.getRelatedId();

                                URI theUri = null;
                                try {
                                    theUri = new URI(uri);
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                String[] segments = theUri.getPath().split("/");
                                String id = segments[segments.length - 1];

                                atomObjectsForParents.add(new AtomObject(id, conceptNameRelation, uri, relationContext));
                            }

                        }

                        listener.processFinish( atomObjectsForParents);

                 //       Toast.makeText(mContext, "it has " + atomObjectsForParents.size() + " parents", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Relations> call, Throwable t) {

                    }
                });

            }
        }, mContext);
        getTicket.execute("https://utslogin.nlm.nih.gov/cas/v1/api-key/");
        return  null;

    }

    @Override
    protected void onPostExecute(List<AtomObject> atomObjects) {
        super.onPostExecute(atomObjects);
//        listener.processFinish( atomObjects);

    }
}
