package com.nyit.snomedct.Tools;

import android.content.Context;
import android.widget.Toast;

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

public class GetMap {

    GetTicket getTicket;
    Context mContext;
    String firstid, firstURL;
    AtomObject baseConcept;


    List<List<AtomObject>> listofLists;

    public GetMap(Context mContext, String firstid, String firstURL, AtomObject baseConcept) {
        this.mContext = mContext;
        this.firstid = firstid;
        this.firstURL = firstURL;
        this.baseConcept = baseConcept;
        listofLists = new ArrayList<>();
    }

    public List<List<AtomObject>> getMap() {
        //       List<AtomObject> firstParent = new ArrayList<>();

         goOnlineForFirstTime(firstURL);
//
//        List<AtomObject> records = new ArrayList<>();
//        records.add(baseConcept);
//
//        dfs(firstParent, records);
        return listofLists;

    }


    public void dfs(List<AtomObject> parentList, List<AtomObject> records) {

        if (parentList.size() == 0) {
            listofLists.add(records);
            return;
        }

        for (int i = 0; i < parentList.size(); i++) {

            records.add(parentList.get(i));
            String url = parentList.get(i).getUrl();

            parentList = goOnline(url);

            dfs(parentList, records);
            records.remove(records.get(records.size() - 1));

        }

    }


    public List<AtomObject> goOnline(String url) {

        final List<AtomObject>[] list = new List[]{new ArrayList<>()};

        getTicket = new GetTicket(new AsyncResponse() {
            @Override
            public void processFinish(String ticket) {
                list[0] = getParents(ticket, url);

            }
        }, mContext);
        getTicket.execute("https://utslogin.nlm.nih.gov/cas/v1/api-key/");

        return list[0];

    }




    public List<AtomObject> getParents(String ticket, String theUrl) {

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

                Toast.makeText(mContext, "it has " + atomObjectsForParents.size() + " parents", Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onFailure(Call<Relations> call, Throwable t) {

            }
        });


        return atomObjectsForParents;

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

                Toast.makeText(mContext, "it has " + firstParentList.size() + " parents", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<Relations> call, Throwable t) {

            }
        });

    }
















}
