package com.example.hisabee;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hisabee.recyclerView.denapaonaAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Fragment_bay extends Fragment {

    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
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


        RecyclerView recyclerView = view.findViewById(R.id.baki_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new denapaonaAdapter(denaPawna.filterItemsByWeek("todaySpend"), context , "todaySpend"));
        return view;
    }


}




