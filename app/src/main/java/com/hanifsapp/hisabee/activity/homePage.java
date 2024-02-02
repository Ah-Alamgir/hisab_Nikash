package com.hanifsapp.hisabee.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hanifsapp.hisabee.databinding.ActivityHomePageBinding;
import com.hanifsapp.hisabee.databinding.DialogAddCustomerBinding;
import com.hanifsapp.hisabee.denaPawna;
import com.hanifsapp.hisabee.firebase_Db.Constant;
import com.hanifsapp.hisabee.firebase_Db.GetproductList;
import com.hanifsapp.hisabee.firebase_Db.localStore;

import java.util.Objects;


public class homePage extends AppCompatActivity {


    private ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("মাহি এন্টারপ্রাইজ");




        //handle ui releted work
        binding.sellBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, Sell.class)));
        binding.contactBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, profile.class)));
        binding.stockManageBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, StockProduct.class)));
        binding.costBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, costCalculation.class)));
        binding.dueBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, denaPawna.class)));
        binding.wishBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, wish_printer.class)));
        binding.EditInfo.setOnClickListener(view -> showAddCustomerDialog());











//        //Handle data releted work
//        if(autoload.isNetworkAvailable(this)){
//
//            FirebaseAuth auth = FirebaseAuth.getInstance();
//            autoload.auth_User_id = auth.getUid();
//            autoload.getDbRef();
//            autoload.getCurrentMonthName();
//            localStore.getDatas(this);
//            autoload.cardItem_list.clear();
//            autoload.cardItem.clear();
//            if (autoload.productLists.isEmpty()) {
////            autoload.getData();
//            }
//            updateBusinessInfo();
//        }
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

    @Override
    protected void onStart() {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() == null) {
//            startActivity(new Intent(homePage.this, SignUp.class));
//        }else{
//            variable.getDbRef(auth.getUid());
//            if (Objects.requireNonNull(getProductList.product_list.getValue()).size() ==0){
//                getProductList.getProduct_item();
//            }
//        }

        if(GetproductList.product_list.getValue() == null){
            Constant.getDbRef();
            GetproductList.getProduct_item();
        }

        super.onStart();
    }
}