package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fragment_bay extends Fragment {

    public Fragment_bay() {
        // Required empty public constructor
    }

    public static Fragment_bay newInstance() {
        Fragment_bay fragment = new Fragment_bay();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bay, container, false);
    }

}