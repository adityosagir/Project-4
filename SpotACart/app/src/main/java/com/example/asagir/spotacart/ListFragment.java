package com.example.asagir.spotacart;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;


public class ListFragment extends android.app.Fragment {

    ArrayList arrayList;

    // Data
    ArrayList<String> mFoodCarts = new ArrayList<>();
    CartListRecyclerAdapter mAdapter;

    // UI
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Firebase mRef = new Firebase("https://brilliant-fire-1347.firebaseio.com/");

        View v = inflater.inflate(R.layout.activity_list, container, false);
        // Can put in recycler view

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        mAdapter = new CartListRecyclerAdapter(mRef, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        return v;


    }
}
