package com.nyit.snomedct.ConceptResults;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nyit.snomedct.APICallsInterface;
import com.nyit.snomedct.AllInformation.AllInfo;
import com.nyit.snomedct.ConceptActivity;
import com.nyit.snomedct.R;
import com.nyit.snomedct.Tools.ViewDialog;
import com.nyit.snomedct.UMLS.AsyncResponse;
import com.nyit.snomedct.UMLS.GetTicket;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConceptListRecyclerViewAdapter extends RecyclerView.Adapter<ConceptListViewHolder> {
    public static final String CONCEPTURLKEY="concepturlkey";
    public static final String CONCEPTNAMEKEY="conceptnamekey";
    public static final String CONCEPTID="conceptIDKEY";
    public static final String CONCEPTSTUTES="conceptIDstatues";
    GetTicket getTicket;
    ViewDialog viewDialog;



    List<ConceptObject> conceptObjectList;
    Context context;

    public ConceptListRecyclerViewAdapter(List<ConceptObject> conceptObjectList, Context context) {
        this.conceptObjectList = conceptObjectList;
        this.context=context;
    }

    @NonNull
    @Override
    public ConceptListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View parentView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_recyclerview, viewGroup,false);
        ConceptListViewHolder viewHolder = new ConceptListViewHolder(parentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConceptListViewHolder viewHolder, int i) {
        viewHolder.conceptNameView.setText(conceptObjectList.get(i).getConceptName());
        viewHolder.conceptIDView.setText(conceptObjectList.get(i).getId());
        ConceptObject conceptObject = conceptObjectList.get(i);

        viewHolder.rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //show progress bar/view

                viewDialog = new ViewDialog((Activity)context);
                viewDialog.showDialog();

                String name = conceptObject.getConceptName();
                String uri = conceptObject.getUri();
                String baseUrl = "https://uts-ws.nlm.nih.gov/rest/content/2019AB/source/SNOMEDCT_US/";
                String id = conceptObject.getId();
      //          Toast.makeText(view.getContext(), "uri: "+ uri, Toast.LENGTH_SHORT).show();
                // final Boolean isActive;

                //Add a api call to get active states~

                getTicket = new GetTicket(new AsyncResponse() {
                    @Override
                    public void processFinish(String ticket) {



                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        APICallsInterface getConceptsAPI = retrofit.create(APICallsInterface.class);

                        Call<AllInfo> allInfoCall= getConceptsAPI.getAllInfo(id, ticket);
                        allInfoCall.enqueue(new Callback<AllInfo>() {
                            @Override
                            public void onResponse(Call<AllInfo> call, Response<AllInfo> response) {
                                AllInfo allInfo = response.body();
                                Boolean isObsolete = allInfo.getResult().getObsolete();
                                String status = "Active";
                                if(isObsolete) status ="Retried";

                                Intent intent = new Intent(context, ConceptActivity.class);
                                intent.putExtra(CONCEPTNAMEKEY, name);
                                intent.putExtra(CONCEPTURLKEY, uri);
                                intent.putExtra(CONCEPTID, id);
                                intent.putExtra(CONCEPTSTUTES,status );
                                context.startActivity(intent);
                                viewDialog.hideDialog();
                             //   ((Activity)context).finish();



                            }

                            @Override
                            public void onFailure(Call<AllInfo> call, Throwable t) {

                            }
                        });


                  //      Toast.makeText(context, "query:"+query+" ticket: " + ticket, Toast.LENGTH_SHORT).show();
                        //     Toast.makeText(getActivity(), "yeah, we pass the ticket here" + ticket, Toast.LENGTH_SHORT).show();
                    }
                },context);
                getTicket.execute("https://utslogin.nlm.nih.gov/cas/v1/api-key/");




//
//                Intent intent = new Intent(context, ConceptActivity.class);
//                intent.putExtra(CONCEPTNAMEKEY, name);
//                intent.putExtra(CONCEPTURLKEY, uri);
//                intent.putExtra(CONCEPTID, id);
//                context.startActivity(intent);


            }
        });



    }

    @Override
    public int getItemCount() {
        return conceptObjectList.size();
    }
}
