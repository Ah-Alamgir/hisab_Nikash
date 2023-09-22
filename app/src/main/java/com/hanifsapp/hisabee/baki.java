package com.hanifsapp.hisabee;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.recyclerView.denapaonaAdapter;

import java.util.ArrayList;
import java.util.Map;


public class baki extends Fragment {
    public static RecyclerView recyclerView;
    private static Context context;




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        baki.context = context;

    }

    public baki() {
    }

    public static baki newInstance() {
        baki fragment = new baki();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baki, container, false);


        recyclerView = view.findViewById(R.id.baki_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new denapaonaAdapter(autoload.todaydue, context, "todayDue"));

        return view;
    }


    static void Update(ArrayList<Map<String, Object>> list ){
        recyclerView.setAdapter(new denapaonaAdapter(list, context, "todayDue"));
    }



















}




