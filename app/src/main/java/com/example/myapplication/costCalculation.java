package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class costCalculation extends AppCompatActivity {

    Button salaryBtn, boughtBtn, billBtn, RentBtn;
    TextView totalCost_textView, date_TextView, salary_textView, bought_TextView, bill_textView, rent_textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_calculation);

        setTitle("খরচ হিসাব ");
    }
}