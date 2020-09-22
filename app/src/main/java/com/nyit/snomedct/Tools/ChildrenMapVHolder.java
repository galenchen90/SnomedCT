package com.nyit.snomedct.Tools;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nyit.snomedct.R;

public class ChildrenMapVHolder extends RecyclerView.ViewHolder {

    public TextView conceptView;


    public ChildrenMapVHolder(@NonNull View itemView) {
        super(itemView);
        conceptView=itemView.findViewById(R.id.hierarchy_concept_name);
    }
}
