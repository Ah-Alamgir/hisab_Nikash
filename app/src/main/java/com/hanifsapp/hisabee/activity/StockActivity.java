package com.hanifsapp.hisabee.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hanifsapp.hisabee.databinding.ActivityStockProductBinding;
import com.hanifsapp.hisabee.firebase_Db.GetproductList;
import com.hanifsapp.hisabee.model.ProductList;
import com.hanifsapp.hisabee.recyclerView.adapters.StockAdapter;
import com.hanifsapp.hisabee.recyclerView.interFaces.onStockclickListner;
import com.hanifsapp.hisabee.utility.Dialogues;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class StockActivity extends AppCompatActivity implements onStockclickListner {


    private ArrayList<ProductList> currentList = new ArrayList<ProductList>();
    private ActivityStockProductBinding binding;
    private StockAdapter stockAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStockProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.addProductStock.setOnClickListener(view -> {
            addProductActivity.editProduct = false;
            startActivity(new Intent(this, addProductActivity.class));
            finish();
        });




        handleBackPress();
    }


    @Override
    protected void onStart() {
        super.onStart();

        setRecyclerView();
        AtomicBoolean firstTime = new AtomicBoolean(true);
        GetproductList.product_list.observe(this, arrayList -> {
            if (firstTime.get()){
                firstTime.set(false);
            }else {
                stockAdapter.updateList(arrayList);
            }

        });

    }



    private void setRecyclerView(){
        currentList = GetproductList.product_list.getValue();
        stockAdapter = new StockAdapter(this, currentList,this);
        binding.stockRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.stockRecycler.setAdapter(stockAdapter);
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
        startActivity(new Intent(this, addProductActivity.class));
    }

    @Override
    public void onDeleteClick(String id) {
        Dialogues.onDeleteClick(id, this);
    }


    private void handleBackPress(){
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }


}