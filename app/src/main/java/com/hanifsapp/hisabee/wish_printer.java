package com.hanifsapp.hisabee;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;

public class wish_printer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_printer);



        Button btn =  findViewById(R.id.button_wish);
        EditText editText = findViewById(R.id.editText_wish);

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


}