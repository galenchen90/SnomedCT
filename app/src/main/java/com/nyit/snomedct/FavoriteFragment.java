package com.nyit.snomedct;

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
import com.nyit.snomedct.Database.FavoriteListRecyclerViewAdapter;
import com.nyit.snomedct.Database.FavoriteObject;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
     RecyclerView mRecyclerView;
     List<FavoriteObject> favoriteObjects;
     FavoriteListRecyclerViewAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment,container,false );

        mRecyclerView = view.findViewById(R.id.recycler_view_for_favorite);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        favoriteObjects= new ArrayList<>();

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getContext());
        favoriteObjects= databaseHelper.getFavorite();

        adapter =new FavoriteListRecyclerViewAdapter(favoriteObjects, getContext());
        mRecyclerView.setAdapter(adapter);

        return view;
    }
}
