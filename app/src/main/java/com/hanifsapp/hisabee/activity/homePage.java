package com.hanifsapp.hisabee.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.databinding.ActivityHomePageBinding;
import com.hanifsapp.hisabee.databinding.DialogAddCustomerBinding;
import com.hanifsapp.hisabee.firebase_Db.ChekNet;
import com.hanifsapp.hisabee.firebase_Db.Constant;
import com.hanifsapp.hisabee.firebase_Db.GetproductList;
import com.hanifsapp.hisabee.firebase_Db.localStore;
import com.hanifsapp.hisabee.fragments.HomeFragment;
import com.hanifsapp.hisabee.fragments.ProfileFragment;
import com.hanifsapp.hisabee.fragments.printFragment;

import java.util.Objects;


public class homePage extends AppCompatActivity {


    private ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(GetproductList.product_list.getValue() == null){
            Constant.getDbRef();
            GetproductList.getProduct_item();
        }
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ChekNet.isNetworkAvailable(this);


        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, new HomeFragment()).commit();
        }

        binding.bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if(id== R.id.homefrag){
                selectedFragment = new HomeFragment();
            } else if (id == R.id.printActivity) {
                selectedFragment = new printFragment();
            } else if (id == R.id.contactActivity) {
                startActivity(new Intent(this, Sell.class));
            } else if (id == R.id.ProfileActivity) {
                selectedFragment = new ProfileFragment();
            }


            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, selectedFragment).commit();
                return true;
            }else {
                return false;
            }

        });







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
                    bottomSheetDialog.dismiss();
                }
            }
        });
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
        super.onStart();



    }



}