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
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Map extends AsyncTask<List<List<AtomObject>>,Void,List<List<AtomObject>>> {

    GetTicket getTicket;
    Context mContext;
    String firstid, firstURL;
    AtomObject baseConcept;
    private AsyncResponseForMap listener;


    List<List<AtomObject>> listofLists;

    public Map(AsyncResponseForMap listener, Context mContext, String firstid, String firstURL, AtomObject baseConcept) {
        this.mContext = mContext;
        this.firstid = firstid;
        this.firstURL = firstURL;
        this.baseConcept = baseConcept;
        this.listener=listener;
        listofLists = new ArrayList<>();
    }



    @Override
    protected List<List<AtomObject>> doInBackground(List<List<AtomObject>>... lists) {

      //  getMap();

        goOnlineForFirstTime(firstURL);


        return listofLists;
    }


    @Override
    protected void onPostExecute(List<List<AtomObject>> lists) {
        super.onPostExecute(lists);
        listener.processFinish(lists);

    }
//
//    public List<List<AtomObject>> getMap() {
//        //       List<AtomObject> firstParent = new ArrayList<>();
//
//        goOnlineForFirstTime(firstURL);
////
////        List<AtomObject> records = new ArrayList<>();
////        records.add(baseConcept);
////
////        dfs(firstParent, records);
//        return listofLists;
//
//    }


    public void dfs(List<AtomObject> parentList, List<AtomObject> records) {

        if (parentList.size() == 0) {
            listofLists.add(records);
            return;
        }

        for (int i = 0; i < parentList.size(); i++) {

            String url = parentList.get(i).getUrl();

            int finalI = i;

            GetParentTask getParentTask = new GetParentTask(new AsyncResponseForParents() {
                 @Override
                 public void processFinish(List<AtomObject> objectList) {


                     records.add(parentList.get(finalI));
                     List<AtomObject> nextRecords = new ArrayList<>();

                     Iterator<AtomObject> iterator = records.iterator();
                     while (iterator.hasNext()){
                         nextRecords.add((AtomObject) iterator.next());
                     }


                     dfs(objectList, nextRecords);

                     records.remove(records.size() - 1);


                 }
             },mContext,url);
             getParentTask.execute();

       //     records.remove(records.get(records.size() - 1));

        }

    }


    public void goOnline(String url) {

        final List<AtomObject>[] list = new List[]{new ArrayList<>()};

        getTicket = new GetTicket(new AsyncResponse() {
            @Override
            public void processFinish(String ticket) {
            //    getParents(ticket, url);

            }
        }, mContext);
        getTicket.execute("https://utslogin.nlm.nih.gov/cas/v1/api-key/");


    }




    public void getParents(String ticket, String theUrl) {

        String baseURL = theUrl + "/";

        List<AtomObject> atomObjectsForParents = new ArrayList<>();


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

           //     Toast.makeText(mContext, "it has " + atomObjectsForParents.size() + " parents", Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onFailure(Call<Relations> call, Throwable t) {

            }
        });


    }


    public void goOnlineForFirstTime(String url) {


        getTicket = new GetTicket(new AsyncResponse() {
            @Override
            public void processFinish(String ticket) {
                getParentsForFirstTime(ticket, url);

            }
        }, mContext);
        getTicket.execute("https://utslogin.nlm.nih.gov/cas/v1/api-key/");


    }


    public void getParentsForFirstTime(String ticket, String theUrl) {

        String baseURL = theUrl + "/";

        List<AtomObject> firstParentList = new ArrayList<>();


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

                        firstParentList.add(new AtomObject(id, conceptNameRelation, uri, relationContext));
                    }

                }


                List<AtomObject> records = new ArrayList<>();
                records.add(baseConcept);
                dfs(firstParentList, records);

          //      Toast.makeText(mContext, "first parent it has " + firstParentList.size() + " parents", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<Relations> call, Throwable t) {

            }
        });

    }



}
