package com.example.hisabee;

import androidx.appcompat.app.AlertDialog;
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
    public EditText name,sellPrice,buyPrice,Stock,discount, vat;

    private Button submit, updateBtn;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("denaPaona");
    public static String id;
    public static boolean editProduct= false;

    public static int edit_position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        name = findViewById(R.id.editTextName);
        Stock = findViewById(R.id.stock);
        buyPrice = findViewById(R.id.sell_price_text_view);
        sellPrice = findViewById(R.id.sellingPrice);
        discount = findViewById(R.id.discount);
        vat = findViewById(R.id.vatAvai);
        submit = findViewById(R.id.submit);
        updateBtn = findViewById(R.id.update);


        submit.setOnClickListener(view -> {
            updateProduct();
        });



        if (editProduct){
            setTitle("তথ্য আপডেট করুন");
            submit.setVisibility(View.GONE);
            updateBtn.setVisibility(View.VISIBLE);
            Map<String, Object> item = autoload.productLists.get(edit_position);


            name.setText((String) item.get("name"));
            id= item.get("id").toString();
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
            updateProduct();
        });




    }


    private void updateProduct(){
        if(name.getText().toString().isEmpty()){
            name.setError("Enter product name");
        } else if (sellPrice.getText().toString().isEmpty()) {
            sellPrice.setError("Enter selling price");
        } else if (Stock.getText().toString().isEmpty()) {
            Stock.setError("Add Stock information");

        } else if (buyPrice.getText().toString().isEmpty()) {
            buyPrice.setError("Enter buying price");
        }else {

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

            if (editProduct){
                myRef.child("ProductList").child(id).updateChildren(userData);
            }else {
                myRef.child("ProductList").push().setValue(userData);
            }

            alartDIalogue();

        }
    }




    public void alartDIalogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (editProduct){
            builder.setTitle("Product Updated")
                    .setMessage("Your product has been successfully updated.");
        }else {
            builder.setTitle("Product Added")
                    .setMessage("Your product has been successfully added.");
        }
        builder.setPositiveButton("OK", (dialog, which) -> {

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}