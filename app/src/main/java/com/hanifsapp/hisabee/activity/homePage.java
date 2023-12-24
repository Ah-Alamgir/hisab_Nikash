package com.hanifsapp.hisabee.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.StockProduct;
import com.hanifsapp.hisabee.autoload;
import com.hanifsapp.hisabee.databinding.ActivityHomePageBinding;
import com.hanifsapp.hisabee.denaPawna;
import com.hanifsapp.hisabee.profile;

public class homePage extends AppCompatActivity {
    public static SharedPreferences sharedPreferences;





    private ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("মাহি এন্টারপ্রাইজ");




        autoload.getCurrentMonthName();

        binding.sellBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, Sell.class)));
        binding.contactBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, profile.class)));
        binding.stockManageBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, StockProduct.class)));
        binding.costBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, costCalculation.class)));
        binding.dueBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, denaPawna.class)));
        binding.wishBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, wish_printer.class)));

        autoload.cardItem_list.clear();
        autoload.cardItem.clear();
        if(autoload.productLists.isEmpty()){
            autoload.getData();
        }




        sharedPreferences = getSharedPreferences("businessInfo", Context.MODE_PRIVATE);
        binding.EditInfo.setOnClickListener(view -> showAddCustomerDialog());
        updateBusinessInfo();

        autoload.isNetworkAvailable(this);
    }








    public static void setText(){
//        dueToday.setText(autoload.todaydueamount);
//        spendToday.setText(autoload.todaycostamount);
//        sellToday.setText(autoload.todaysellamount);
    }










    private void showAddCustomerDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_customer, null);
        dialogBuilder.setView(dialogView);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextAddress = dialogView.findViewById(R.id.editTextAddress);
        EditText editTextPhoneNumber = dialogView.findViewById(R.id.editTextPhoneNumber);

        dialogBuilder.setPositiveButton("যোগ করুন", (dialog, which) -> {
            String name = editTextName.getText().toString().trim();
            String address = editTextAddress.getText().toString().trim();
            String phoneNumber = editTextPhoneNumber.getText().toString().trim();

            if (!name.isEmpty() && !address.isEmpty() && !phoneNumber.isEmpty()) {


                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.putString("name", name);
                editor.putString("address", address);
                editor.putString("phoneNumber", phoneNumber);
                editor.apply();

                dialog.dismiss();
                updateBusinessInfo();
            }
        });

        dialogBuilder.setNegativeButton("বাদ দিন", null);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }


    private void updateBusinessInfo(){
        binding.businessnameTextView.setText(sharedPreferences.getString("name", "প্রতিষ্ঠানের  নাম "));
        binding.addressTextView.setText(sharedPreferences.getString("address", "প্রতিষ্ঠানের ঠিকানা"));
        binding.phoneNumberTextView.setText(sharedPreferences.getString("phoneNumber", "ফোন নাম্বার"));
    }





}