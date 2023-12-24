package com.hanifsapp.hisabee.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.printEpos;

public class wish_printer extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_printer);



        Button btn =  findViewById(R.id.button_wish);
        editText = findViewById(R.id.editText_wish);
        String[] fontSize=  new String[]{"12","14","16","18","20","22","24"};
        ImageButton setting = findViewById(R.id.setting_wish);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fontSize);





        setting.setOnClickListener(v -> showSetting());

        btn.setOnClickListener(v -> {
            try {
                printEpos.generatePdf(editText, wish_printer.this);
            } catch (EscPosEncodingException | EscPosParserException | EscPosBarcodeException |
                     EscPosConnectionException e) {
                throw new RuntimeException(e);
            }

        });


        OnBackPressedCallback callback = new OnBackPressedCallback(true){
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };




    }



    private void showSetting(){
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View views = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_navigation_wish_printer, null);

        dialog.setContentView(views);
        dialog.show();


        RadioButton boltButton = views.findViewById(R.id.radioButton);
        Spinner spinner = views.findViewById(R.id.spinner_wish);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = parent.getItemAtPosition(position).toString();
                editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Integer.parseInt(selectedText));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        CardView cardView  = views.findViewById(R.id.saveButton);
//        cardView.setOnClickListener(v -> {
//            SharedPreferences sp = new SharePre
//        });

        boltButton.setOnClickListener(v -> {
            if(boltButton.isChecked()) {
                editText.setTypeface(null, Typeface.BOLD);
            }else {
                editText.setTypeface(null, Typeface.NORMAL);
            }
        });
    }


}