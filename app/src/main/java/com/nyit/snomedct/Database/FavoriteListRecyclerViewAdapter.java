package com.nyit.snomedct.Database;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nyit.snomedct.ConceptActivity;
import com.nyit.snomedct.ConceptResults.ConceptListViewHolder;
import com.nyit.snomedct.R;

import java.util.List;

import static com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter.CONCEPTID;
import static com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter.CONCEPTNAMEKEY;
import static com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter.CONCEPTSTUTES;
import static com.nyit.snomedct.ConceptResults.ConceptListRecyclerViewAdapter.CONCEPTURLKEY;

public class FavoriteListRecyclerViewAdapter extends RecyclerView.Adapter<ConceptListViewHolder> {

    List<FavoriteObject> favoriteObjects;
    Context mContext;

    public FavoriteListRecyclerViewAdapter(List<FavoriteObject> favoriteObjects, Context mContext) {
        this.favoriteObjects = favoriteObjects;
        this.mContext = mContext;
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

        viewHolder.conceptNameView.setText(favoriteObjects.get(i).getName());
        viewHolder.conceptIDView.setText(favoriteObjects.get(i).getId());

        FavoriteObject atomObject = favoriteObjects.get(i);

        viewHolder.rootview.setOnClickListener(new View.OnClickListener() {
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
            }
        });

    }

    @Override
    public int getItemCount() {
        return favoriteObjects.size();
    }

    public void updateDatabase(){
        notifyDataSetChanged();
    }
}
