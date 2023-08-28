package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class homePage extends AppCompatActivity {
    private CardView buy;
    private CardView sell;
    private CardView buyBook;
    private CardView sellBook;
    private CardView dueBook;
    private CardView costBook;
    private CardView productList;
    private CardView stockManagement;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        buy = findViewById(R.id.buying);
        sell = findViewById(R.id.selling);
        buyBook = findViewById(R.id.buyBook);
        sellBook = findViewById(R.id.sellBoooks);
        dueBook = findViewById(R.id.dueBooks);
        costBook = findViewById(R.id.costBooks);
        productList = findViewById(R.id.productLists);
        stockManagement = findViewById(R.id.stockManage);


        productList.setOnClickListener(view -> startActivity(new Intent(homePage.this, productList.class)));
        sell.setOnClickListener(view -> startActivity(new Intent(homePage.this, Sell.class)));
        stockManagement.setOnClickListener(view -> startActivity(new Intent(homePage.this, StockProduct.class)));


        if(autoload.data.isEmpty()){
            autoload.getProductData();
        }
    }

}