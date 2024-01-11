package com.hanifsapp.hisabee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.recyclerView.adapters.sell_recyclerView;
import com.hanifsapp.hisabee.recyclerView.interFaces.invoiceListener;


public class Sell extends AppCompatActivity implements invoiceListener {

    public TextView totalPrice_textView;
    public Button totalItemBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);




        totalItemBtn = findViewById(R.id.orderPage);
        totalPrice_textView = findViewById(R.id.totalPrice);
        totalItemBtn.setOnClickListener(view -> startActivity(new Intent(Sell.this, invoice.class)));



    }


    @Override
    protected void onStart() {

        super.onStart();


        RecyclerView recyclerView = findViewById(R.id.pdRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new sell_recyclerView());


    }


    @Override
    public void setInvoice(int totalPrice, int totalAmount) {
        totalPrice_textView.setText(String.valueOf(totalPrice));
        totalItemBtn.setText(String.valueOf(totalAmount));
    }
}