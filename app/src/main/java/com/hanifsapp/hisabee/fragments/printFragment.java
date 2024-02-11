package com.hanifsapp.hisabee.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.databinding.BottomNavigationWishPrinterBinding;
import com.hanifsapp.hisabee.firebase_Db.localStore;
import com.hanifsapp.hisabee.utility.printEpos;


public class printFragment extends Fragment {
    ArrayAdapter<String> adapter;
    EditText editText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_print, container, false);


        Button btn =  view.findViewById(R.id.button_wish);
        editText = view.findViewById(R.id.editText_wish);
        String[] fontSize=  new String[]{"12","14","16","18","20","22","24"};
        ImageButton setting = view.findViewById(R.id.setting_wish);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, fontSize);






        setEditText();
        setting.setOnClickListener(v -> showSetting());

        btn.setOnClickListener(v -> {
            try {
                printEpos.generatePdf(editText, getContext());
            } catch (EscPosEncodingException | EscPosParserException | EscPosBarcodeException |
                     EscPosConnectionException e) {
                throw new RuntimeException(e);
            }

        });
        return view;

    }



    private void setEditText(){

        editText.setTextSize(22);
        try {
            if (Boolean.parseBoolean(localStore.settings.get(1))){
                editText.setTypeface(null, Typeface.BOLD);

            }
        }catch (Exception e){}


    }

    private void showSetting() {

        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        BottomNavigationWishPrinterBinding wishDialouge = BottomNavigationWishPrinterBinding.inflate(getLayoutInflater());

        dialog.setContentView(wishDialouge.getRoot());
        dialog.show();


        wishDialouge.spinnerWish.setAdapter(adapter);
        wishDialouge.spinnerWish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = parent.getItemAtPosition(position).toString();
                editText.setTextSize(Integer.parseInt(selectedText));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        wishDialouge.saveButton.setOnClickListener(v -> {
            localStore.putSettings(getContext(), String.valueOf(editText.getTextSize()), String.valueOf(wishDialouge.radioButton.isChecked()));
        });


        wishDialouge.radioButton.setOnClickListener(v -> {
            if (wishDialouge.radioButton.isChecked()) {
                editText.setTypeface(null, Typeface.BOLD);
            } else {
                editText.setTypeface(null, Typeface.NORMAL);
            }
        });
    }
}