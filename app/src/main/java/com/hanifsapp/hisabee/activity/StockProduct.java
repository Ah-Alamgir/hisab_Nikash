package com.hanifsapp.hisabee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.autoload;
import com.hanifsapp.hisabee.firebase_Db.getProductList;
import com.hanifsapp.hisabee.model.ProductList;
import com.hanifsapp.hisabee.recyclerView.interFaces.onDeleteClickListener;
import com.hanifsapp.hisabee.recyclerView.interFaces.onEditclickListner;

public class StockProduct extends AppCompatActivity implements onDeleteClickListener, onEditclickListner {


    public TextView totoalStock_textview, totalStock_value_textView;
    Button addNewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_product);


        totoalStock_textview = findViewById(R.id.total_stock_textView);
        totalStock_value_textView = findViewById(R.id.total_stock_value_text);
        addNewProduct = findViewById(R.id.addProductStock);


        addNewProduct.setOnClickListener(view -> {
            addProduct.editProduct = false;
            startActivity(new Intent(this, addProduct.class));

        });

        setTitle("মোট পণ্য সংখ্যাঃ " + getProductList.product_Lists.size());
        count();

    }


    @Override
    protected void onStart() {
        RecyclerView recyclerView = findViewById(R.id.stockRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new stock_RecyclerView(StockProduct.this));
        super.onStart();
    }


    private void count() {
        int totalStock_var = 0, totalStock_valueVar = 0;

        for (ProductList map : getProductList.product_Lists) {
            totalStock_var = map.getStock() + totalStock_var;
            totalStock_valueVar = map.getBuyPrice() * map.getStock() + totalStock_valueVar;
        }
        totoalStock_textview.setText(String.valueOf(totalStock_var));
        totalStock_value_textView.setText(String.valueOf(totalStock_valueVar));

    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }




    //Method that is called from recyclerView

    @Override
    public void onDeleteClick(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this item?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, ids) -> {
                    autoload.deleteData(id);
                })
                .setNegativeButton("No", (dialog, ids) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onEditClick(int position) {
        startActivity(new Intent(this, addProduct.class));
    }
}