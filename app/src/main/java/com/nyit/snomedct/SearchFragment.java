package com.nyit.snomedct;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter;
import com.nyit.snomedct.ConceptResults.ConceptObject;
import com.nyit.snomedct.ConceptResults.Concepts;
import com.nyit.snomedct.ConceptResults.Result;
import com.nyit.snomedct.ConceptResults.Result_;
import com.nyit.snomedct.Database.DatabaseHelper;
import com.nyit.snomedct.UMLS.AsyncResponse;
import com.nyit.snomedct.UMLS.GetTicket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class SearchFragment extends Fragment {
    SearchView searchView;
    View view;
    GetTicket getTicket;
    List<ConceptObject> conceptObjectList;
    RecyclerView mRecyclerView;
    ConceptListRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment,container,false );
        searchViewCode();

        mRecyclerView = view.findViewById(R.id.recycler_view_for_search);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        return view;

    }



    // if the user go to search activity from searching history
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Log.d(TAG, "setUserVisibleHint: can see?");




            try {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String keyword = preferences.getString("KEYWORD",null);

                if (!keyword.equals(null)) {

                    Log.d(TAG, "keyword : " + keyword);


                    getTicket = new GetTicket(new AsyncResponse() {
                        @Override
                        public void processFinish(String ticket) {
                            searchByTerm(keyword,ticket);
                            //     Toast.makeText(getActivity(), "yeah, we pass the ticket here" + ticket, Toast.LENGTH_SHORT).show();
                        }
                    },getContext());
                    getTicket.execute("https://utslogin.nlm.nih.gov/cas/v1/api-key/");



                    long currenttime= System.currentTimeMillis() ;
                    String time = Long.toString(currenttime);

                    DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getContext());
                    databaseHelper.insertKeyword(time,keyword);


                    // set the keyword as null again for next time use.

                    SharedPreferences preferencestwo = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor2 = preferencestwo.edit();
                    editor2.putString("KEYWORD",null);
                    editor2.apply();

                }
            }
            catch (NullPointerException e) {
                Log.d(TAG, "onCreateView: not data");
            }



        }
    }

    public void searchViewCode() {

        searchView = view.findViewById(R.id.search_view);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                long currenttime= System.currentTimeMillis() ;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa");
                Date currentDateD = new Date(currenttime);
                String currentDate = simpleDateFormat.format(currentDateD);
                String time = Long.toString(currenttime);

                DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getContext());
                databaseHelper.insertKeyword(time,s);

                searchView.setQuery("", false);
                searchView.clearFocus();



                getTicket = new GetTicket(new AsyncResponse() {
                    @Override
                    public void processFinish(String ticket) {
                        searchByTerm(s,ticket);
                   //     Toast.makeText(getActivity(), "yeah, we pass the ticket here" + ticket, Toast.LENGTH_SHORT).show();
                    }
                },getContext());
                getTicket.execute("https://utslogin.nlm.nih.gov/cas/v1/api-key/");

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });



    }


    public void searchByTerm( String query,String ticket){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uts-ws.nlm.nih.gov/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        APICallsInterface getConceptsAPI = retrofit.create(APICallsInterface.class);
        Call<Concepts> conceptsCall= getConceptsAPI.getConcepts(query, ticket);
      //  Toast.makeText(getActivity(), "query:"+query+" ticket: " + ticket, Toast.LENGTH_SHORT).show();

        conceptsCall.enqueue(new Callback<Concepts>() {
            @Override
            public void onResponse(Call<Concepts> call, Response<Concepts> response) {
                Concepts concepts = response.body();

                if(concepts == null) {
                    Toast.makeText(getActivity(), "no data returned", Toast.LENGTH_SHORT).show();
                }else{

                    Result result = concepts.getResult();

                    List<Result_> resultList = result.getResults();
                    conceptObjectList = new ArrayList<>();


                    for (Result_ result_object : resultList){
                        ConceptObject conceptObject = new ConceptObject(result_object.getUi(),
                                result_object.getName(),
                                result_object.getUri());
                        conceptObjectList.add(conceptObject);
                    }

                    int size = conceptObjectList.size();


                    adapter =new ConceptListRecyclerViewAdapter(conceptObjectList, getContext());
                    mRecyclerView.setAdapter(adapter);



                }
            }

            @Override
            public void onFailure(Call<Concepts> call, Throwable t) {

            }
        });


    }



}
