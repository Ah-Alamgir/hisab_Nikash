package com.hanifsapp.hisabee.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hanifsapp.hisabee.databinding.ActivityAddProductBinding;
import com.hanifsapp.hisabee.firebase_Db.getProductList;
import com.hanifsapp.hisabee.firebase_Db.variable;
import com.hanifsapp.hisabee.model.ProductList;

import java.util.Map;

public class addProduct extends AppCompatActivity {


    public static String id;
    public static boolean editProduct= false;
    public static int edit_position;
    private ActivityAddProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.submit.setOnClickListener(view -> updateProduct());
        binding.update.setOnClickListener(view -> {
            updateProduct();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (editProduct) {
//            setTitle("তথ্য আপডেট করুন");
            binding.submit.setVisibility(View.GONE);
            binding.update.setVisibility(View.VISIBLE);
            ProductList item = getProductList.product_Lists.get(edit_position);


            binding.editTextName.setText(item.getName());
            id = item.getId();
            binding.sellingPrice.setText(String.valueOf(item.getSellPrice()));
            binding.buyPrice.setText(String.valueOf(item.getBuyPrice()));
            binding.stock.setText(String.valueOf(item.getStock()));
            if (item.getDiscount()>0) {
                binding.discount.setText(String.valueOf(item.getDiscount()));

            }
            if (item.getVat()>0) {
                binding.discount.setText(String.valueOf(item.getVat()));
            }

        } else {
            setTitle("পণ্য যোগ করুন");

            binding.update.setVisibility(View.GONE);
            binding.submit.setVisibility(View.VISIBLE);

        }
    }

    private void updateProduct(){
        String name = String.valueOf(binding.editTextName.getText());
        String sellPrice = String.valueOf(binding.sellingPrice.getText());
        String buyPrice = String.valueOf(binding.buyPrice.getText());
        String Stock = String.valueOf(binding.stock.getText());
        String Discount = binding.discount.getText().toString();
        String vat= binding.vatAvai.getText().toString();


        if(name.isEmpty()){
            binding.editTextName.setError("Enter product name");
        } else if (sellPrice.isEmpty()) {
            binding.sellingPrice.setError("Enter selling price");
        } else if (Stock.isEmpty()) {
            binding.stock.setError("Add Stock information");
        } else if (buyPrice.isEmpty()) {
            binding.buyPrice.setError("Enter buying price");
        }else {

            ProductList productList = new ProductList( Integer.parseInt(buyPrice), name, Integer.parseInt(vat),
                    Integer.parseInt(Discount), Integer.parseInt(sellPrice), Integer.parseInt(Stock) );

            Map<String, Object> postValues = productList.toMap();


            if (editProduct){
                variable.productList_ref.child(id).updateChildren(postValues);
            }else {
                variable.productList_ref.push().setValue(postValues);
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