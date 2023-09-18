package com.hanifsapp.hisabee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class homePage extends AppCompatActivity {
    CardView printerConnect,sell,sprofile,dueBook,costBook,stockManagement;
    public SharedPreferences sharedPreferences;

    static TextView sellToday, dueToday, spendToday, businessName, businessAddress, bussinessPhone;


    ImageButton editInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_page);
        setTitle("মাহি এন্টারপ্রাইজ");



        printerConnect = findViewById(R.id.buying);
        sell = findViewById(R.id.selling);
        sprofile = findViewById(R.id.profiles);
        dueBook = findViewById(R.id.dueBooks);
        costBook = findViewById(R.id.costBooks);
        stockManagement = findViewById(R.id.stockManage);
        sellToday = findViewById(R.id.todaySell);
        spendToday = findViewById(R.id.todayCost);
        dueToday = findViewById(R.id.today_due);
        editInfo= findViewById(R.id.EditInfo);
        businessName = findViewById(R.id.businessname);
        businessAddress = findViewById(R.id.address_home);
        bussinessPhone = findViewById(R.id.phoneNumber_home);




        autoload.getCurrentMonthName();

        sell.setOnClickListener(view -> startActivity(new Intent(homePage.this, Sell.class)));
        sprofile.setOnClickListener(view -> startActivity(new Intent(homePage.this, profile.class)));
        stockManagement.setOnClickListener(view -> startActivity(new Intent(homePage.this, StockProduct.class)));
        costBook.setOnClickListener(view -> startActivity(new Intent(homePage.this, costCalculation.class)));
        dueBook.setOnClickListener(view -> startActivity(new Intent(homePage.this, denaPawna.class)));
        printerConnect.setOnClickListener(view -> startActivity(new Intent(homePage.this, eps.class)));

        autoload.cardItem_list.clear();
        autoload.cardItem.clear();
        if(autoload.productLists.isEmpty()){
            autoload.getData();
        }




        sharedPreferences = getSharedPreferences("businessInfo", Context.MODE_PRIVATE);
        editInfo.setOnClickListener(view -> showAddCustomerDialog());
        updateBusinessInfo();
    }


    public static void setText(){
        dueToday.setText(autoload.todaydueamount);
        spendToday.setText(autoload.todaycostamount);
        sellToday.setText(autoload.todaysellamount);
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
        businessName.setText(sharedPreferences.getString("name", "প্রতিষ্ঠানের  নাম "));
        businessAddress.setText(sharedPreferences.getString("address", "প্রতিষ্ঠানের ঠিকানা"));
        bussinessPhone.setText(sharedPreferences.getString("phoneNumber", "ফোন নাম্বার"));
    }





}