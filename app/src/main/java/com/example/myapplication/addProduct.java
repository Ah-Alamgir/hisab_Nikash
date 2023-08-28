package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class addProduct extends AppCompatActivity {
    private EditText name;
    private EditText sellPrice;
    private EditText buyPrice;
    private EditText Stock;
    private EditText discount;
    private EditText vat;

    private Button submit, updateBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static boolean editProduct= false;
    public static String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        name = findViewById(R.id.pName);
        Stock = findViewById(R.id.stock);
        buyPrice = findViewById(R.id.sell_price_text_view);
        sellPrice = findViewById(R.id.sellingPrice);
        discount = findViewById(R.id.discount);
        vat = findViewById(R.id.vatAvai);
        submit = findViewById(R.id.submit);
        updateBtn = findViewById(R.id.update);


        submit.setOnClickListener(view -> {
            if(name.getText().toString().isEmpty()){
                name.setError("Enter product name");
            } else if (sellPrice.getText().toString().isEmpty()) {
                sellPrice.setError("Enter selling price");
            } else if (Stock.getText().toString().isEmpty()) {
                Stock.setError("Add Stock information");

            } else if (buyPrice.getText().toString().isEmpty()) {
                buyPrice.setError("Enter buying price");
            }else {
                DatabaseReference myRef = database.getReference("ProductList");

                Map<String, Object> userData = new HashMap<>();
                userData.put("name", name.getText().toString());
                userData.put("sellPrice", Integer.valueOf(sellPrice.getText().toString()));
                userData.put("buyPrice", Integer.valueOf(buyPrice.getText().toString()));
                userData.put("Stock", Integer.valueOf(Stock.getText().toString()));
                if(!discount.getText().toString().isEmpty()){
                    userData.put("Discount", Integer.valueOf(discount.getText().toString()));
                }
                if (!vat.getText().toString().isEmpty()){
                    userData.put("vat", Integer.valueOf(vat.getText().toString()));
                }
                myRef.push().setValue(userData);


            }
        });


        if (editProduct){
            submit.setVisibility(View.GONE);
        }else{
            updateBtn.setVisibility(View.GONE);
        }


        updateBtn.setOnClickListener(view -> {

        });

    }

}