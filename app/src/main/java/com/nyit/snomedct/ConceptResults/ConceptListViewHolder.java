package com.nyit.snomedct.ConceptResults;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nyit.snomedct.R;

public class ConceptListViewHolder extends RecyclerView.ViewHolder {
    public TextView conceptNameView;
    public TextView conceptIDView;
    public View rootview;


    public ConceptListViewHolder(@NonNull View itemView) {
        super(itemView);
        rootview=itemView.findViewById(R.id.search_list_view);
        conceptNameView=itemView.findViewById(R.id.conceptName_recyclerview);
        conceptIDView=itemView.findViewById(R.id.conceptId_recyclerview);
    }
}
