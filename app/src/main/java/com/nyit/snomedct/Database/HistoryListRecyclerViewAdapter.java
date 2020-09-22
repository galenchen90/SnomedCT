package com.nyit.snomedct.Database;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nyit.snomedct.ConceptResults.ConceptListViewHolder;
import com.nyit.snomedct.R;
import com.nyit.snomedct.TabsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryListRecyclerViewAdapter extends RecyclerView.Adapter<ConceptListViewHolder> {
    public static final String SENDINGKEWYWORD = "SENDINGKEYWORDS";

    List<SearchObjects> searchObjectsList;
    Context mContext;
    Activity activity;

    public HistoryListRecyclerViewAdapter(List<SearchObjects> searchObjectsList, Context mContext, Activity activity) {
        this.searchObjectsList = searchObjectsList;
        this.mContext = mContext;
        this.activity=activity;
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

        viewHolder.conceptNameView.setText(searchObjectsList.get(i).getKeyword());

        Long timelong= Long. parseLong(searchObjectsList.get(i).getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss, dd/MM/yyyy");
        Date currentDateD = new Date(timelong);
        String currentDate = simpleDateFormat.format(currentDateD);





        viewHolder.conceptIDView.setText(currentDate);

        viewHolder.rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((TabsActivity)activity).selectTab( searchObjectsList.get(i).getKeyword());


//                Intent intent = new Intent(mContext, TabsActivity.class);
//                intent.putExtra(SENDINGKEWYWORD,searchObjectsList.get(i).getKeyword() );
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return searchObjectsList.size();
    }

    public void updateDatabase(){
        notifyDataSetChanged();

    }
}
