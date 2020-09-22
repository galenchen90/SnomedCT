package com.nyit.snomedct.Tools;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nyit.snomedct.R;

public class ParentMapViewHolder extends RecyclerView.ViewHolder {

    public View rootview;
    public TextView titleView;
    public RecyclerView recycler_view_list;

    public ParentMapViewHolder(@NonNull View itemView) {
        super(itemView);
        rootview=itemView.findViewById(R.id.parent_root_view_map);
        titleView=itemView.findViewById(R.id.map_title_view);
        recycler_view_list=itemView.findViewById(R.id.childrent_map_recyclerview);


    }
}
