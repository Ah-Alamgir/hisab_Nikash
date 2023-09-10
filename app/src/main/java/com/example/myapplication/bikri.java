package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class bikri extends Fragment {

    public bikri() {
        // Required empty public constructor
    }

    public static bikri newInstance() {
        bikri fragment = new bikri();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bikri, container, false);



    }
}