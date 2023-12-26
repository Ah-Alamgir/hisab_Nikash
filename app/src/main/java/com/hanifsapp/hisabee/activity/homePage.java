package com.hanifsapp.hisabee.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.StockProduct;
import com.hanifsapp.hisabee.autoload;
import com.hanifsapp.hisabee.databinding.ActivityHomePageBinding;
import com.hanifsapp.hisabee.databinding.DialogAddCustomerBinding;
import com.hanifsapp.hisabee.denaPawna;
import com.hanifsapp.hisabee.profile;
import com.hanifsapp.hisabee.localDb.localStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ir.mahozad.android.PieChart;


public class homePage extends AppCompatActivity {


    private ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("মাহি এন্টারপ্রাইজ");


        autoload.getCurrentMonthName();
        localStore.getDatas(this);
        binding.sellBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, Sell.class)));
        binding.contactBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, profile.class)));
        binding.stockManageBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, StockProduct.class)));
        binding.costBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, costCalculation.class)));
        binding.dueBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, denaPawna.class)));
        binding.wishBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, wish_printer.class)));


        autoload.cardItem_list.clear();
        autoload.cardItem.clear();
        if (autoload.productLists.isEmpty()) {
            autoload.getData();
        }


        binding.EditInfo.setOnClickListener(view -> showAddCustomerDialog());
        updateBusinessInfo();
        autoload.isNetworkAvailable(this);
    }











    DialogAddCustomerBinding dialogBinding;

    private void showAddCustomerDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        dialogBinding = DialogAddCustomerBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(dialogBinding.getRoot());
        bottomSheetDialog.show();


        dialogBinding.addInfoButton.setOnClickListener(v -> {
            String name = Objects.requireNonNull(dialogBinding.nam.getText()).toString();
            String address = Objects.requireNonNull(dialogBinding.thikana.getText().toString());
            String phoneNumber = Objects.requireNonNull(dialogBinding.editTextPhoneNumber.getText().toString());

            if (!name.isEmpty() && !address.isEmpty() && !phoneNumber.isEmpty()) {

                boolean done = localStore.putAddress(name, address, phoneNumber, this);
                if (done) {
                    localStore.getDatas(this);
                    updateBusinessInfo();
                    bottomSheetDialog.dismiss();
                }


            }
        });
    }


    private void updateBusinessInfo() {
        binding.businessnameTextView.setText(localStore.settings.get(4));
        binding.addressTextView.setText(localStore.settings.get(2));
        binding.phoneNumberTextView.setText(localStore.settings.get(3));
    }


}