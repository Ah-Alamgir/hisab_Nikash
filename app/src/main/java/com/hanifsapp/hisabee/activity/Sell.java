package com.hanifsapp.hisabee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.firebase_Db.getProductList;
import com.hanifsapp.hisabee.recyclerView.sell_recyclerView;


public class Sell extends AppCompatActivity {

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


}