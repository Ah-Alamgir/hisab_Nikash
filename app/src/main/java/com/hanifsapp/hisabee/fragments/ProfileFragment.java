package com.hanifsapp.hisabee.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.hanifsapp.hisabee.databinding.DialogueInfoInputBinding;
import com.hanifsapp.hisabee.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    FirebaseAuth auth;
    String DB_NAME = "NAME";
    String DB_ADDRESS = "ADDRESS";
    String DB_NUMBER = "NUMBER";
    String DB_CURRENCY = "CURRENCY";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        setInfos();
        FirebaseAuth.getInstance();
        binding.btnEdit.setOnClickListener(v -> showAddCustomerDialog());

        return binding.getRoot();
    }


    private void setInfos() {

        SharedPreferences prefs = getActivity().getSharedPreferences("Submit", Context.MODE_PRIVATE);


        binding.textViewName.setText(prefs.getString(DB_NAME, "Add Your Name"));
        binding.textViewPhoneNumber.setText(prefs.getString(DB_NUMBER, "Add Phone Number"));
        binding.textViewAddress.setText(prefs.getString(DB_ADDRESS, "Add Address"));
        binding.textViewCurrency.setText(prefs.getString(DB_CURRENCY, "Add Currency"));



    }


    private void showAddCustomerDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        DialogueInfoInputBinding dialogBinding = DialogueInfoInputBinding.inflate(getLayoutInflater());
        View dialogView = dialogBinding.getRoot();
        dialogBuilder.setView(dialogView);


        dialogBuilder.setPositiveButton("Submit", (dialog, which) -> {
            SharedPreferences preferences = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(DB_NAME, dialogBinding.inputName.getText().toString());
            editor.putString(DB_ADDRESS, dialogBinding.inputAddress.getText().toString());
            editor.putString(DB_NUMBER, dialogBinding.inputNumber.getText().toString());
            editor.putString(DB_CURRENCY, dialogBinding.inputCureency.getText().toString());
            editor.apply();


        });

        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        setInfos();
    }


}