package com.hanifsapp.hisabee.activity;

import static com.hanifsapp.hisabee.utility.CustomChart.showChart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.anychart.charts.CircularGauge;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.databinding.ActivityHomePageBinding;
import com.hanifsapp.hisabee.databinding.DialogAddCustomerBinding;
import com.hanifsapp.hisabee.firebase_Db.Constant;
import com.hanifsapp.hisabee.firebase_Db.GetproductList;
import com.hanifsapp.hisabee.firebase_Db.localStore;
import com.hanifsapp.hisabee.fragments.ContactFragment;
import com.hanifsapp.hisabee.fragments.HomeFragment;
import com.hanifsapp.hisabee.fragments.ProfileFragment;
import com.hanifsapp.hisabee.fragments.printFragment;

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
//        binding.sellBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, Sell.class)));
//        binding.contactBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, profile.class)));
//        binding.stockManageBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, StockActivity.class)));
//        binding.costBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, costCalculation.class)));
//        binding.dueBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, denaPawna.class)));
//        binding.wishBtn.setOnClickListener(view -> startActivity(new Intent(homePage.this, wish_printer.class)));


//        binding.EditInfo.setOnClickListener(view -> showAddCustomerDialog());


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
                selectedFragment = new ContactFragment();
            } else if (id == R.id.ProfileActivity) {
                selectedFragment = new ProfileFragment();
            }


            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, selectedFragment).commit();
            return true;
        });









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
        if(GetproductList.product_list.getValue() == null){
            Constant.getDbRef();
            GetproductList.getProduct_item();
        }

        GetproductList.product_list.observe(this, arrayList -> displayChart());

    }


    private void displayChart(){
        binding.anyChartView.setProgressBar(binding.progressBars);
        String[] values = {"500", "250", "1800", "5685"};
        String[] labels = {"Due", "Cost", "Sell", "le"};
        CircularGauge gauge = showChart(values, labels);
        binding.anyChartView.setChart(gauge);
    }
}