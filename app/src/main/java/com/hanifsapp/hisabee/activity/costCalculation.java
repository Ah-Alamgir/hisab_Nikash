package com.hanifsapp.hisabee.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.autoload;
import com.hanifsapp.hisabee.databinding.ActivityCostCalculationBinding;

import java.util.HashMap;
import java.util.Map;

public class costCalculation extends AppCompatActivity {

    private ActivityCostCalculationBinding binding;

    public static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityCostCalculationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Cost calculation");





//        binding.dateTextVIew.setText(autoload.dates);
//        binding.salaryBtn.setOnClickListener(view -> {
//            showTextInputDialog("Salary", "salary");
//        });
//        binding.buyBtn.setOnClickListener(view -> {
//            showTextInputDialog("Buy", "buy");
//        });
//        binding.billBtn.setOnClickListener(view -> {
//            showTextInputDialog("Bill", "bill");
//        });
//        binding.rentBtn.setOnClickListener(view -> {
//            showTextInputDialog("Rent", "rent");
//        });


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