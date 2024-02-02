package com.hanifsapp.hisabee.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hanifsapp.hisabee.databinding.ActivityStockProductBinding;
import com.hanifsapp.hisabee.firebase_Db.GetproductList;
import com.hanifsapp.hisabee.model.ProductList;
import com.hanifsapp.hisabee.recyclerView.adapters.StockAdapter;
import com.hanifsapp.hisabee.recyclerView.interFaces.onEditclickListner;

import java.util.ArrayList;

public class StockProduct extends AppCompatActivity implements onEditclickListner {


    private ArrayList<ProductList> currentList = new ArrayList<ProductList>();
    private ActivityStockProductBinding binding;
    private StockAdapter stockAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStockProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.addProductStock.setOnClickListener(view -> {
            addProduct.editProduct = false;
            startActivity(new Intent(this, addProduct.class));
        });

        currentList = GetproductList.product_list.getValue();
        stockAdapter = new StockAdapter(this, currentList);
        binding.stockRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.stockRecycler.setAdapter(stockAdapter);

        GetproductList.product_list.observe(this, arrayList -> {
            stockAdapter.updateList(arrayList);
//            setTitle("মোট পণ্য সংখ্যাঃ " + arrayList.size());
        });


    }


    @Override
    protected void onStart() {
        super.onStart();


    }


//    private void count() {
//        int totalStock_var = 0, totalStock_valueVar = 0;
//
//        for (ProductList map : getProductList.product_Lists) {
//            totalStock_var = map.getStock() + totalStock_var;
//            totalStock_valueVar = map.getBuyPrice() * map.getStock() + totalStock_valueVar;
//        }
//        totoalStock_textview.setText(String.valueOf(totalStock_var));
//        totalStock_value_textView.setText(String.valueOf(totalStock_valueVar));
//
//    }


    @Override
    public void onEditClick(int position) {
        startActivity(new Intent(this, addProduct.class));
    }


}