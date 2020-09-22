package com.nyit.snomedct;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nyit.snomedct.Database.DatabaseHelper;
import com.nyit.snomedct.Database.HistoryListRecyclerViewAdapter;
import com.nyit.snomedct.Database.SearchObjects;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    public RecyclerView mRecyclerView;
    List<SearchObjects> searchObjectsList;
    HistoryListRecyclerViewAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment,container,false );



        mRecyclerView = view.findViewById(R.id.recycler_view_for_history);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        searchObjectsList= new ArrayList<>();

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getContext());
        searchObjectsList= databaseHelper.getHistory();



        adapter =new HistoryListRecyclerViewAdapter(searchObjectsList, getContext(),getActivity());
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return view;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        adapter.notifyDataSetChanged();
    }
}
