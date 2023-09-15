package com.example.hisabee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class homePage extends AppCompatActivity {
    CardView printerConnect,sell,buyBook,sprofile,dueBook,costBook,productList,stockManagement;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    static TextView sellToday, dueToday, spendToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

















        autoload.getCurrentMonthName();

        sell.setOnClickListener(view -> startActivity(new Intent(homePage.this, Sell.class)));
        sprofile.setOnClickListener(view -> startActivity(new Intent(homePage.this, profile.class)));
        stockManagement.setOnClickListener(view -> startActivity(new Intent(homePage.this, StockProduct.class)));
        costBook.setOnClickListener(view -> startActivity(new Intent(homePage.this, costCalculation.class)));
        dueBook.setOnClickListener(view -> startActivity(new Intent(homePage.this, denaPawna.class)));
        printerConnect.setOnClickListener(view -> startActivity(new Intent(homePage.this, BluetoothPrinterActivity.class)));

        autoload.cardItem_list.clear();
        autoload.cardItem.clear();
        if(autoload.productLists.isEmpty()){
            autoload.getData();
        }

        setText();

    }

    public static void setText(){
        if(autoload.singleValues.size()>0){
            try {

                Map<String, Object> todayDueMap = (Map<String, Object>) autoload.singleValues.get("todayDue");
                Map<String, Object> todaysellMap = (Map<String, Object>) autoload.singleValues.get("todaySell");
                Map<String, Object> todaySpendMap = (Map<String, Object>) autoload.singleValues.get("todaySpend");
                if (todayDueMap != null) {
                    sellToday.setText(todaysellMap.get(autoload.dates).toString());
                    spendToday.setText(todaySpendMap.get(autoload.dates).toString());
                    dueToday.setText(todayDueMap.get(autoload.dates).toString());
                }




            }catch (Exception e){

            }

        }
    }

}