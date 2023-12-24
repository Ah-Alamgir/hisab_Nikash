package com.hanifsapp.hisabee.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanifsapp.hisabee.autoload;
import com.hanifsapp.hisabee.databinding.ActivityAddProductBinding;

import java.util.HashMap;
import java.util.Map;

public class addProduct extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("denaPaona");
    public static String id;
    public static boolean editProduct= false;

    public static int edit_position;

    private ActivityAddProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.submit.setOnClickListener(view -> {
            updateProduct();
        });



        if (editProduct){
            setTitle("তথ্য আপডেট করুন");
            binding.submit.setVisibility(View.GONE);
            binding.update.setVisibility(View.VISIBLE);
            Map<String, Object> item = autoload.productLists.get(edit_position);


            binding.editTextName.setText((String) item.get("name"));
            id= item.get("id").toString();
            binding.sellingPrice.setText(item.get("sellPrice").toString());
            binding.buyPrice.setText( item.get("buyPrice").toString());
            binding.stock.setText( item.get("Stock").toString());
            if(!item.get("Discount").toString().isEmpty()){
                binding.discount.setText(item.get("Discount").toString());

            }
            if(!item.get("vat").toString().isEmpty()){
                binding.discount.setText(item.get("vat").toString());

            }

        }else{
            setTitle("পণ্য যোগ করুন");

            binding.update.setVisibility(View.GONE);
            binding.submit.setVisibility(View.VISIBLE);

        }

        binding.update.setOnClickListener(view -> {
            updateProduct();
        });




    }


    private void updateProduct(){
        if(binding.editTextName.getText().toString().isEmpty()){
            binding.editTextName.setError("Enter product name");
        } else if (binding.sellingPrice.getText().toString().isEmpty()) {
            binding.sellingPrice.setError("Enter selling price");
        } else if (binding.stock.getText().toString().isEmpty()) {
            binding.stock.setError("Add Stock information");

        } else if (binding.buyPrice.getText().toString().isEmpty()) {
            binding.buyPrice.setError("Enter buying price");
        }else {

            Map<String, Object> userData = new HashMap<>();
            userData.put("name", binding.editTextName.getText().toString());
            userData.put("sellPrice", Integer.valueOf(String.valueOf(binding.sellingPrice.getText())));
            userData.put("buyPrice", Integer.valueOf(String.valueOf(binding.buyPrice.getText())));
            userData.put("Stock", Integer.valueOf(String.valueOf(binding.stock.getText())));
            if(!binding.discount.getText().toString().isEmpty()){
                userData.put("Discount", Integer.valueOf(binding.discount.getText().toString()));
            }
            if (!binding.vatAvai.getText().toString().isEmpty()){
                userData.put("vat", Integer.valueOf(binding.vatAvai.getText().toString()));
            }

            if (editProduct){
                myRef.child("ProductList").child(id).updateChildren(userData);
            }else {
                myRef.child("ProductList").push().setValue(userData);
            }

            alartDIalogue();

        }
    }




    private void alartDIalogue(){
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


}