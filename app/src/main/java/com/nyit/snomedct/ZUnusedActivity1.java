//package com.nyit.snomedct;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.widget.ExpandableListView;
//import android.widget.Toast;
//
//import com.nyit.snomedct.Atoms.Atoms;
//import com.nyit.snomedct.Atoms.Result;
//import com.nyit.snomedct.ConceptResults.AtomObject;
//import com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter;
//import com.nyit.snomedct.ConceptResults.ConceptObject;
//import com.nyit.snomedct.Relationship.Relations;
//import com.nyit.snomedct.UMLS.AsyncResponse;
//import com.nyit.snomedct.UMLS.GetTicket;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class ZUnusedActivity1 extends AppCompatActivity {
//    String uri,conceptName,id,baseURL;
//    GetTicket getTicket, getTicket2;
//    //HashMap<String,String> synonymsMap;
//    ConceptObject finalConceptObject;
//    List<AtomObject> synonymList;
//    ExpandableListView expandableListView;
//    List expandableListTitle;
//    HashMap<String,List<AtomObject>> expandableListDetail;
//    com.nyit.snomedct.ExpandableListAdapter expandableListAdapter;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.zunusedactivity1);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        Intent intentText= getIntent();
//        conceptName=intentText.getStringExtra(ConceptListRecyclerViewAdapter.CONCEPTNAMEKEY);
//        uri=intentText.getStringExtra(ConceptListRecyclerViewAdapter.CONCEPTURLKEY);
//        baseURL = uri+"/";
//        id=intentText.getStringExtra(ConceptListRecyclerViewAdapter.CONCEPTID);
//        String nameString= conceptName + " ("+id +")";
//        toolbar.setTitle(conceptName);
////        TextView conceptView = findViewById(R.id.concept_name_withID_textview);
////        conceptView.setText(nameString);
////        conceptView.setPaintFlags(conceptView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//
//
//
//        getTicket = new GetTicket(new AsyncResponse() {
//            @Override
//            public void processFinish(String ticket) {
//                Toast.makeText(ZUnusedActivity1.this, "ticket: " + ticket, Toast.LENGTH_SHORT).show();
//                getAtoms(ticket);
//
//
//
//
//            }
//        });
//        getTicket.execute("https://utslogin.nlm.nih.gov/cas/v1/api-key/");
//
//        getTicket2 = new GetTicket(new AsyncResponse() {
//            @Override
//            public void processFinish(String ticket) {
//                getRelations(ticket);
//            }
//        });
//        getTicket2.execute("https://utslogin.nlm.nih.gov/cas/v1/api-key/");
//
//
//
//
//    }
//    public void getAtoms(String ticket){
//
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseURL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        APICallsInterface apiCallsInterface = retrofit.create(APICallsInterface.class);
//        Call<Atoms> atomsCall = apiCallsInterface.getAtoms(ticket);
//        atomsCall.enqueue(new Callback<Atoms>() {
//            @Override
//            public void onResponse(Call<Atoms> call, Response<Atoms> response) {
//                Atoms atoms = response.body();
//                List<Result> atomList = atoms.getResult();
//                synonymList = new ArrayList<>();
//
//
//                for(int i =0; i< atomList.size();i++){
//                    Result atom = atomList.get(i);
//                    String states = atom.getObsolete();
//                    Boolean active= false;
//                    if (states.equals("false")){
//                        active = true;
//                    }
//                    synonymList.add(new AtomObject(atom.getUi(),atom.getName(),atom.getTermType(),active));
//
//                }
//
//                finalConceptObject = new ConceptObject(id,conceptName,uri,synonymList);
//
////                TextView textView = findViewById(R.id.concept_synonym_textview);
////
////                for(AtomObject atomObject : synonymList){
////
////                    names= names+ atomObject.getType() + " " + atomObject.getName() +" ";
////                }
////
////
////                names= names+ " size:" +synonymList.size() + " atom's size:"+atomList.size();
////
////                textView.setText(names);
//
//
//               //=======================
//
//                expandableListView = findViewById(R.id.synonym_expendableView);
//                expandableListDetail= ExpandableListDataPump.getData(synonymList);
//                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
//    //            expandableListTitle.add("Description");
//                expandableListAdapter = new ExpandableListAdapter(getApplicationContext(),expandableListTitle,expandableListDetail);
//
//                expandableListView.setAdapter(expandableListAdapter);
//
//
//
//                expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//
//                    @Override
//                    public void onGroupExpand(int i) {
//
//                    //    setListViewHeight(expandableListView, i);
//
//                        int height = 0;
//                        for (int j = 0; j < expandableListView.getChildCount(); j++) {
//                            height += expandableListView.getChildAt(j).getMeasuredHeight();
//                            height += expandableListView.getDividerHeight();
//                        }
//                        expandableListView.getLayoutParams().height = (height+60)*10;
//
//                        int size = expandableListView.getChildCount();
//                        Toast.makeText(ZUnusedActivity1.this, "open size is : " + size, Toast.LENGTH_LONG).show();
//
//                    }
//                });
//
//                expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//                    @Override
//                    public void onGroupCollapse(int i) {
//                    //    expandableListView.getLayoutParams().height = 10;
//
//
//                        int height = expandableListView.getHeight();
//                        expandableListView.getLayoutParams().height = height;
//
//                        Toast.makeText(ZUnusedActivity1.this, "close", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//                    @Override
//                    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                        Toast.makeText(ZUnusedActivity1.this, "childview toast" +
//                                "", Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                });
//
//
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<Atoms> call, Throwable t) {
//
//                Toast.makeText(ZUnusedActivity1.this, "fail to call", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//
//
//
//    }
//
//
//    public void getRelations(String ticket){
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseURL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        APICallsInterface apiCallsInterface = retrofit.create(APICallsInterface.class);
//        Call<Relations> relationsCall = apiCallsInterface.getRelations(ticket);
//        relationsCall.enqueue(new Callback<Relations>() {
//            @Override
//            public void onResponse(Call<Relations> call, Response<Relations> response) {
//                Relations relations = response.body();
//                List<com.nyit.snomedct.Relationship.Result> relationResults = relations.getResult();
//                relationResults.get(0);
//                String relationName = relationResults.get(0).getRelatedIdName();
//
//
//                Toast.makeText(ZUnusedActivity1.this, "RelationName:" +relationName, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<Relations> call, Throwable t) {
//
//            }
//        });
//
//
//    }
//
//
//
//
//
//
//}
