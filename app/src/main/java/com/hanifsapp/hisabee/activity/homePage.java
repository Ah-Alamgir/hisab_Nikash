package com.hanifsapp.hisabee.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.databinding.ActivityHomePageBinding;
import com.hanifsapp.hisabee.firebase_Db.ChekNet;
import com.hanifsapp.hisabee.firebase_Db.Constant;
import com.hanifsapp.hisabee.firebase_Db.GetproductList;
import com.hanifsapp.hisabee.fragments.CostFragment;
import com.hanifsapp.hisabee.fragments.HomeFragment;
import com.hanifsapp.hisabee.fragments.ProfileFragment;
import com.hanifsapp.hisabee.fragments.printFragment;


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
            } else if (id == R.id.CostActivity) {
                selectedFragment = new CostFragment();
            } else if (id == R.id.ProfileActivity) {
                selectedFragment = new ProfileFragment();
            }


            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.fragmentHolder, selectedFragment).commit();
                return true;
            }else {
                return false;
            }

        });







    }










    @Override
    protected void onStart() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(homePage.this, SignUp.class));
        }
        super.onStart();



    }



}