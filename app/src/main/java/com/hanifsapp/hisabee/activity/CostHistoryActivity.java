package com.hanifsapp.hisabee.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hanifsapp.hisabee.databinding.ActivityCosthistoryBinding;
import com.hanifsapp.hisabee.firebase_Db.GetHistory;
import com.hanifsapp.hisabee.model.SoldHistory;
import com.hanifsapp.hisabee.utility.CustomChart;
import com.hanifsapp.hisabee.utility.GetDate;

import java.util.ArrayList;

public class CostHistoryActivity extends AppCompatActivity {

    private ActivityCosthistoryBinding binding;
    private ArrayList<SoldHistory> history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityCosthistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Cost calculation");

        binding.textViewDate.setText(GetDate.getDate());
        history = GetHistory.getSoldHistory();
        calculateCost(binding.textViewDate.getText().toString());


        binding.btnBack.setOnClickListener(v -> calculateCost(GetDate.getDate(-1)));
        binding.btnForword.setOnClickListener(v -> calculateCost(GetDate.getDate(+1)));

    }




private int shoppingCostvar=0;
private int travelCostvar=0;
private int foodCostvar=0;
private int otherCostvar=0;

    private void calculateCost(String date){
        travelCostvar = 0;
        foodCostvar = 0;
        shoppingCostvar = 0;
        otherCostvar = 0;
       history.forEach(items->{
           if (items.getDate().equals(date)){
               if (items.getType()==0) {
                    shoppingCostvar+= items.getPrice();
               } else if (items.getType()==1) {
                   travelCostvar+= items.getPrice();
               } else if (items.getType() == 2) {
                   foodCostvar+= items.getPrice();
               }else if (items.getType() == 3) {
                   otherCostvar+= items.getPrice();
               }
           }
       });

       binding.shoppingCost.setText(String.valueOf(shoppingCostvar));
       binding.travelCost.setText(String.valueOf(travelCostvar));
       binding.foodCost.setText(String.valueOf(foodCostvar));
       binding.otherCost.setText(String.valueOf(otherCostvar));
       String[] costList = {String.valueOf(shoppingCostvar), String.valueOf(foodCostvar), String.valueOf(travelCostvar), String.valueOf(otherCostvar)};
       CustomChart.showChart(costList, new String[]{"Shopping", "Travel", "Food", "Others"});

    }




//    private void showTextInputDialog(String title, String tag) {
//        EditText editText, detailsText;
//        Switch switchButtonGive, switchButtonDue;
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(title);
//
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View view = inflater.inflate(R.layout.dialog_text_input, null);
//
//        editText = view.findViewById(R.id.nam);
//        detailsText = view.findViewById(R.id.editTextPhoneNumber);
//        switchButtonGive = view.findViewById(R.id.switchButtonGive);
//        switchButtonDue = view.findViewById(R.id.switchButtonDue);
//
//        switchButtonGive.setVisibility(View.GONE);
//        switchButtonDue.setVisibility(View.GONE);
//
//        builder.setView(view);
//
//        builder.setPositiveButton("যোগ করুন", (dialog, which) -> {
//            if(!editText.getText().toString().isEmpty() && !detailsText.getText().toString().isEmpty()){
//                Map<String, Object> costHisab = new HashMap<>();
//                costHisab.put("Cost", editText.getText().toString());
//                costHisab.put("details", detailsText.getText().toString());
//                rootRef.child("denaPaona").child("singleValues").child(autoload.dates).push().setValue(costHisab);
//
//            }else {
//                if (editText.getText().toString().isEmpty()){
//                    editText.setError("দাম লিখুন");
//                }else{
//                    detailsText.setError("বিবরণ লিখুন ");
//                }
//            }
//        });
//
//        builder.setNegativeButton("বাদ দিন", null);
//        builder.show();
//    }
//
//


}