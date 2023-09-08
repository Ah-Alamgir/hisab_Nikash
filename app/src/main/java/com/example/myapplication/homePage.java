package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class homePage extends AppCompatActivity {
    CardView buy,sell,buyBook,sellBook,dueBook,costBook,productList,stockManagement;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setTitle("মাহি এন্টারপ্রাইজ");



        buy = findViewById(R.id.buying);
        sell = findViewById(R.id.selling);
        buyBook = findViewById(R.id.buyBook);
//        sellBook = findViewById(R.id.sellBoooks);
        dueBook = findViewById(R.id.dueBooks);
        costBook = findViewById(R.id.costBooks);
        stockManagement = findViewById(R.id.stockManage);


        sell.setOnClickListener(view -> startActivity(new Intent(homePage.this, Sell.class)));
        stockManagement.setOnClickListener(view -> startActivity(new Intent(homePage.this, StockProduct.class)));
        costBook.setOnClickListener(view -> startActivity(new Intent(homePage.this, costCalculation.class)));
        dueBook.setOnClickListener(view -> startActivity(new Intent(homePage.this, denaPawna.class)));

        autoload.cardIem.clear();
        autoload.cardItem_list.clear();
        if(autoload.data.isEmpty()){
            autoload.getProductData();
        }
    }

}