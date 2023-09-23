package com.hanifsapp.hisabee;

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
import java.util.Collection;
import java.util.Collections;
import java.util.Map;


public class bikri extends Fragment {

    private static Context context;
    private static RecyclerView recyclerView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bikri.context = context;
    }

    public bikri() {
    }

    public static bikri newInstance() {
        bikri fragment = new bikri();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bikri, container, false);


        recyclerView = view.findViewById(R.id.baki_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new denapaonaAdapter(autoload.todaysell , context, "todaySell"));
        return view;
    }

    static void Update(ArrayList<Map<String, Object>> list){
        recyclerView.setAdapter(new denapaonaAdapter(list, context, "todaySell"));
    }

}




