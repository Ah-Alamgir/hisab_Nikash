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
    private EditText name,sellPrice,buyPrice,Stock,discount, vat;

    private Button submit, updateBtn;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static boolean editProduct= false;
    public static String id;
    public static int edit_position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        name = findViewById(R.id.denaPawana_editText);
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
                DatabaseReference myRef = database.getReference("denaPaona");

                Map<String, Object> userData = new HashMap<>();
                userData.put("name", name.getText().toString());
                userData.put("sellPrice", Integer.valueOf(String.valueOf(sellPrice.getText())));
                userData.put("buyPrice", Integer.valueOf(String.valueOf(buyPrice.getText())));
                userData.put("Stock", Integer.valueOf(String.valueOf(Stock.getText())));
                if(!discount.getText().toString().isEmpty()){
                    userData.put("Discount", Integer.valueOf(discount.getText().toString()));
                }
                if (!vat.getText().toString().isEmpty()){
                    userData.put("vat", Integer.valueOf(vat.getText().toString()));
                }
                myRef.child("ProductList").push().setValue(userData);


            }
        });



        if (editProduct){
            setTitle("তথ্য আপডেট করুন");
            submit.setVisibility(View.GONE);
            updateBtn.setVisibility(View.VISIBLE);
            Map<String, Object> item = autoload.data.get(edit_position);


            name.setText((String) item.get("name"));
            sellPrice.setText(item.get("sellPrice").toString());
            buyPrice.setText( item.get("buyPrice").toString());
            Stock.setText( item.get("Stock").toString());
            if(!item.get("Discount").toString().isEmpty()){
                discount.setText(item.get("Discount").toString());

            }
            if(!item.get("vat").toString().isEmpty()){
                discount.setText(item.get("vat").toString());

            }

        }else{
            setTitle("পণ্য যোগ করুন");

            updateBtn.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);

        }


        updateBtn.setOnClickListener(view -> {

        });





        updateBtn.setOnClickListener(view -> {
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
                userData.put("sellPrice", Integer.valueOf(String.valueOf(sellPrice.getText())));
                userData.put("buyPrice", Integer.valueOf(String.valueOf(buyPrice.getText())));
                userData.put("Stock", Integer.valueOf(String.valueOf(Stock.getText())));
                if(!discount.getText().toString().isEmpty()){
                    userData.put("Discount", Integer.valueOf(discount.getText().toString()));
                }
                if (!vat.getText().toString().isEmpty()){
                    userData.put("vat", Integer.valueOf(vat.getText().toString()));
                }
                myRef.push().setValue(userData);


            }
        });

    }



}