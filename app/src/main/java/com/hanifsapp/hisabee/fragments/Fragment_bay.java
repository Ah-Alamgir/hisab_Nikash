package com.hanifsapp.hisabee.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.R;

import java.util.ArrayList;
import java.util.Map;


public class Fragment_bay extends Fragment {

    private static Context context;
    private static RecyclerView recyclerView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment_bay.context = context;
    }

    public Fragment_bay() {
    }

    public static Fragment_bay newInstance() {
        Fragment_bay fragment = new Fragment_bay();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baki, container, false);


        recyclerView = view.findViewById(R.id.baki_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.setAdapter(new denapaonaAdapter(autoload.todayspend, context , "todaySpend"));
        return view;
    }

    public static void Update(ArrayList<Map<String, Object>> list){
//        recyclerView.setAdapter(new SoldhistoryAdapter(list, context, "todaySpend"));
    }

}




