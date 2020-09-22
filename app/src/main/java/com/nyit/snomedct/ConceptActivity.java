package com.nyit.snomedct;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nyit.snomedct.Atoms.Atoms;
import com.nyit.snomedct.Atoms.Result;
import com.nyit.snomedct.ConceptResults.AtomObject;
import com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter;
import com.nyit.snomedct.ConceptResults.ConceptObject;
import com.nyit.snomedct.ConceptResults.RelationConceptObject;
import com.nyit.snomedct.Database.DatabaseHelper;
import com.nyit.snomedct.Relationship.Relations;
import com.nyit.snomedct.Tools.AsyncResponseForMap;
import com.nyit.snomedct.Tools.GetHierarchies;
import com.nyit.snomedct.Tools.ViewDialog;
import com.nyit.snomedct.UMLS.AsyncResponse;
import com.nyit.snomedct.UMLS.GetTicket;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ConceptActivity extends AppCompatActivity {
    String uri, conceptName, id, baseURL,status;
    GetTicket getTicket, getTicket2;
    //HashMap<String,String> synonymsMap;
    ConceptObject finalConceptObject;
    List<AtomObject> synonymList;
    ExpandableListView expandableListView;
    List expandableListTitle;
    LinkedHashMap<String, List<AtomObject>> expandableListDetail;
    com.nyit.snomedct.ExpandableListAdapter expandableListAdapter;
    ImageView addFavoriateBt, shareBt;
    ViewDialog viewDialog;
    List<RelationConceptObject> relationResultList;
    List<AtomObject> atomObjectsForRelations;
    List<List<AtomObject>> mapLists;
    GetHierarchies map;


    static String[] tableTitle = {"ID","Concept Status"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intentText = getIntent();
        conceptName = intentText.getStringExtra(ConceptListRecyclerViewAdapter.CONCEPTNAMEKEY);
        uri = intentText.getStringExtra(ConceptListRecyclerViewAdapter.CONCEPTURLKEY);
        baseURL = uri + "/";
        id = intentText.getStringExtra(ConceptListRecyclerViewAdapter.CONCEPTID);
        String nameString = conceptName + " (" + id + ")";
        status = intentText.getStringExtra(ConceptListRecyclerViewAdapter.CONCEPTSTUTES);
        String [][] tableValue = {{id,status} };

        TextView titleView = findViewById(R.id.title_in_toolbar);

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(getApplicationContext(),TabsActivity.class);
                startActivity(newIntent);
                finish();
            }
        });




        // starting the dfs as soon as possible

        AtomObject baseConcept = new AtomObject(id,conceptName,uri,"");
        map = new GetHierarchies(new AsyncResponseForMap() {
            @Override
            public void processFinish(List<List<AtomObject>> listofLists) {


                mapLists = listofLists;
                Toast.makeText(ConceptActivity.this, "Map is ready", Toast.LENGTH_LONG).show();


            }
        },getApplicationContext(),id,baseURL,baseConcept);
        map.execute();




        TextView conceptView = findViewById(R.id.concept_name_withID_textview);
        conceptView.setText(conceptName);
        conceptView.setPaintFlags(conceptView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        viewDialog = new ViewDialog(ConceptActivity.this);
        viewDialog.showDialog();

        shareBt = findViewById(R.id.share_button);
        addFavoriateBt = findViewById(R.id.add_tofavoriate_button);
        shareBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","", null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Snomed CT Concept");
                intent.putExtra(Intent.EXTRA_TEXT, conceptName + "  ID: " + id);
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));



            }
        });

        addFavoriateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
                databaseHelper.insertFavorite(id, conceptName, uri, status);



                Toast.makeText(ConceptActivity.this, "add to favorite", Toast.LENGTH_SHORT).show();

            }
        });


        if (status.equals("Active")) conceptView.setTextColor(Color.argb(255,64,205 ,212 ));


        TableView<String[]> tableView = findViewById(R.id.table_view);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this,tableTitle));
        tableView.setColumnCount(2);
        tableView.setDataAdapter(new SimpleTableDataAdapter(this,tableValue));



        getTicket = new GetTicket(new AsyncResponse() {
            @Override
            public void processFinish(String ticket) {
           //     Toast.makeText(ConceptActivity.this, "ticket: " + ticket, Toast.LENGTH_SHORT).show();
                getAtoms(ticket);


            }
        },getApplicationContext());
        getTicket.execute("https://utslogin.nlm.nih.gov/cas/v1/api-key/");


    }

    public void getAtoms(String ticket) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APICallsInterface apiCallsInterface = retrofit.create(APICallsInterface.class);
        Call<Atoms> atomsCall = apiCallsInterface.getAtoms(ticket);
        atomsCall.enqueue(new Callback<Atoms>() {
            @Override
            public void onResponse(Call<Atoms> call, Response<Atoms> response) {
                Atoms atoms = response.body();
                Log.d("last activity~~~~~", "onResponse: "+ baseURL + ticket);
                List<Result> atomList = atoms.getResult();
                synonymList = new ArrayList<>();

                for (int i = 0; i < atomList.size(); i++) {
                    Result atom = atomList.get(i);
                    String states = atom.getObsolete();
                    Boolean active = false;
                    if (states.equals("false")) {
                        active = true;
                    }

                    if ( atom.getTermType().equals("FN")){
                        atom.setTermType("Fully specified name");
                    }else { atom.setTermType("Synonym");}



                    synonymList.add(new AtomObject(atom.getUi(), atom.getName(), atom.getTermType(), active));


                }

                finalConceptObject = new ConceptObject(id, conceptName, uri, synonymList);


                getTicket2 = new GetTicket(new AsyncResponse() {
                    @Override
                    public void processFinish(String ticket) {
                        getRelations(ticket);

                    }
                },getApplicationContext());
                getTicket2.execute("https://utslogin.nlm.nih.gov/cas/v1/api-key/");

            }

            @Override
            public void onFailure(Call<Atoms> call, Throwable t) {

                Toast.makeText(ConceptActivity.this, "fail to call", Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void getRelations(String ticket)  {
        relationResultList = new ArrayList<>();
        atomObjectsForRelations = new ArrayList<>();

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
                List<com.nyit.snomedct.Relationship.Result> relationResults = relations.getResult();


                for (com.nyit.snomedct.Relationship.Result result : relationResults){

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
                        String id = segments[segments.length-1];

                        relationResultList.add(new RelationConceptObject(id,conceptNameRelation,uri,relationContext));
                        atomObjectsForRelations.add(new AtomObject(id,conceptNameRelation,uri,relationContext));
                    }

                    if (result.getAdditionalRelationLabel().equals("has_finding_site")) {
                        String conceptNameRelation = result.getRelatedIdName();
                        String relationContext = "Finding site";
                        String uri = result.getRelatedId();

                        URI theUri = null;
                        try {
                            theUri = new URI(uri);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        String[] segments = theUri.getPath().split("/");
                        String id = segments[segments.length-1];

                        relationResultList.add(new RelationConceptObject(id,conceptNameRelation,uri,relationContext));
                        atomObjectsForRelations.add(new AtomObject(id,conceptNameRelation,uri,relationContext));

                    }

                    if (result.getAdditionalRelationLabel().equals("has_pathological_process")) {
                        String conceptNameRelation = result.getRelatedIdName();
                        String relationContext = "Pathological process";
                        String uri = result.getRelatedId();

                        URI theUri = null;
                        try {
                            theUri = new URI(uri);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        String[] segments = theUri.getPath().split("/");
                        String id = segments[segments.length-1];

                        relationResultList.add(new RelationConceptObject(id,conceptNameRelation,uri,relationContext));
                        atomObjectsForRelations.add(new AtomObject(id,conceptNameRelation,uri,relationContext));


                    }

                    if (result.getAdditionalRelationLabel().equals("has_causative_agent")) {
                        String conceptNameRelation = result.getRelatedIdName();
                        String relationContext = "Causative agent";
                        String uri = result.getRelatedId();

                        URI theUri = null;
                        try {
                            theUri = new URI(uri);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        String[] segments = theUri.getPath().split("/");
                        String id = segments[segments.length-1];

                        relationResultList.add(new RelationConceptObject(id,conceptNameRelation,uri,relationContext));
                        atomObjectsForRelations.add(new AtomObject(id,conceptNameRelation,uri,relationContext));

                    }

                }

                finalConceptObject = new ConceptObject(id, conceptName, uri, synonymList,relationResultList);

                expandableListView = findViewById(R.id.synonym_expendableView_tryindescri);
                expandableListDetail = ExpandableListDataPump.getData(synonymList,atomObjectsForRelations);
                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                expandableListAdapter = new ExpandableListAdapter(getApplicationContext(), expandableListTitle, expandableListDetail);







                expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int i) {
                        if(i ==2) {

                            try {

                                if (mapLists.size() == 0) {
                                    Toast.makeText(ConceptActivity.this, "Sorry please wait", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            catch (Exception e) {
                                Toast.makeText(ConceptActivity.this, "Sorry it is the top level", Toast.LENGTH_SHORT).show();

                                return;
                            }


                            Intent newIntent = new Intent(getApplicationContext(),HierarchyActivity.class);
                            newIntent.putExtra("LISTS", (Serializable) mapLists);
                            startActivity(newIntent);

//                            Toast.makeText(ConceptActivity.this, "map map~~~", Toast.LENGTH_SHORT).show();

//                            AtomObject baseConcept = new AtomObject(id,conceptName,uri,"");
//                            map = new GetHierarchies(new AsyncResponseForMap() {
//                                @Override
//                                public void processFinish(List<List<AtomObject>> listofLists) {
//
//                                    Toast.makeText(ConceptActivity.this, "listoflist size "+ listofLists.size(), Toast.LENGTH_LONG).show();
//
//
//                                }
//                            },getApplicationContext(),id,baseURL,baseConcept);
//                            map.execute();

                        }
                    }
                });





                expandableListView.setAdapter(expandableListAdapter);
                viewDialog.hideDialog();

            //    Toast.makeText(ConceptActivity.this, "RelationSize:" + atomObjectsForRelations.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Relations> call, Throwable t) {

            }
        });


    }


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//        Intent intent = new Intent(getApplicationContext(), TabsActivity.class);
//        startActivity(intent);
//    }
}