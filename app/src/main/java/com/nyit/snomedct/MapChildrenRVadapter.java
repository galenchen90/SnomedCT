package com.nyit.snomedct;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nyit.snomedct.ConceptResults.AtomObject;
import com.nyit.snomedct.Tools.ChildrenMapVHolder;

import java.util.List;

import static com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter.CONCEPTID;
import static com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter.CONCEPTNAMEKEY;
import static com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter.CONCEPTSTUTES;
import static com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter.CONCEPTURLKEY;

public class MapChildrenRVadapter extends RecyclerView.Adapter<ChildrenMapVHolder> {

    List<AtomObject> atomObjects;
    Context mContext;

    public MapChildrenRVadapter(List<AtomObject> atomObjects, Context mContext) {
        this.atomObjects = atomObjects;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ChildrenMapVHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View parentView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.children_map_recyclverview, viewGroup,false);
        ChildrenMapVHolder viewHolder = new ChildrenMapVHolder(parentView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChildrenMapVHolder childrenMapVHolder, int i) {

        AtomObject atomObject = atomObjects.get(i);
        childrenMapVHolder.conceptView.setText(atomObject.getName());

        childrenMapVHolder.conceptView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ConceptActivity.class);
                intent.putExtra(CONCEPTNAMEKEY, atomObject.getName());
                intent.putExtra(CONCEPTURLKEY, atomObject.getUrl());
                intent.putExtra(CONCEPTID, atomObject.getId());
                intent.putExtra(CONCEPTSTUTES,"Active" );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //   | Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                mContext.startActivity(intent);




                Toast.makeText(mContext, "urlis: " + atomObject.getUrl()
                        +"id is : + " + atomObject.getId()
                        +"name is : " + atomObject.getName(), Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return atomObjects.size();
    }
}
