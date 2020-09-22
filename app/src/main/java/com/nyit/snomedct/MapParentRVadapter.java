package com.nyit.snomedct;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nyit.snomedct.ConceptResults.AtomObject;
import com.nyit.snomedct.Tools.HierarchyListObject;
import com.nyit.snomedct.Tools.ParentMapViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MapParentRVadapter extends RecyclerView.Adapter<ParentMapViewHolder>  {

    List<HierarchyListObject> hierarchyListObjects;
    Context mContext;

    public MapParentRVadapter(List<HierarchyListObject> hierarchyListObjects, Context mContext) {
        this.hierarchyListObjects = hierarchyListObjects;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ParentMapViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    //    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parent_map_recyvlerview, null);
        View parentView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parent_map_recyvlerview, viewGroup,false);
        ParentMapViewHolder viewHolder = new ParentMapViewHolder(parentView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ParentMapViewHolder parentMapViewHolder, int i) {

        String sectionName = hierarchyListObjects.get(i).getMapTitle();
        parentMapViewHolder.titleView.setText(sectionName);

        List<AtomObject> atomObjectList = hierarchyListObjects.get(i).getAtomObjectList();
        int size = atomObjectList.size();

        List<AtomObject> topDownHierarchyList = new ArrayList<>();

        for (int j = size-1; j >=0 ; j--) {
            topDownHierarchyList.add(atomObjectList.get(j));
        }




        MapChildrenRVadapter mapChildrenRVadapter = new MapChildrenRVadapter(topDownHierarchyList,mContext);
        parentMapViewHolder.recycler_view_list.setHasFixedSize(true);
        parentMapViewHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        parentMapViewHolder.recycler_view_list.setAdapter(mapChildrenRVadapter);




    }

    @Override
    public int getItemCount() {
        return hierarchyListObjects.size();
    }
}
