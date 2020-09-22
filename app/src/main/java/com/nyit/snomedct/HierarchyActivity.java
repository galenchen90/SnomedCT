package com.nyit.snomedct;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nyit.snomedct.ConceptResults.AtomObject;
import com.nyit.snomedct.Tools.HierarchyListObject;

import java.util.ArrayList;
import java.util.List;

public class HierarchyActivity extends AppCompatActivity {

    List<List<AtomObject>> mapLists;
    List<HierarchyListObject> mapSectionLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hierarchy);

        Intent intent = getIntent();
        mapLists = (List<List<AtomObject>>) intent.getSerializableExtra("LISTS");

        mapSectionLists= new ArrayList<>();
        int size = mapLists.size();

        for (int i = 0; i <size ; i++) {
            String number =i +1 + " ";
            mapSectionLists.add(new HierarchyListObject("Hierarchy map "+ number, mapLists.get(i)));

        }


        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.parent_map_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        MapParentRVadapter adapter = new MapParentRVadapter( mapSectionLists,this);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        my_recycler_view.setAdapter(adapter);




    //    Toast.makeText(this, "Map is ready " , Toast.LENGTH_LONG).show();

      //  Toast.makeText(this, "subsize: is " + mapLists.get(0).size(), Toast.LENGTH_SHORT).show();





    }
}
