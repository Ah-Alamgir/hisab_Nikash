package com.hanifsapp.hisabee;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class costCalculation extends AppCompatActivity {

    Button salaryBtn, boughtBtn, billBtn, RentBtn;
    public static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    TextView totalCost_textView, date_TextView, salary_textView, bought_TextView, bill_textView, rent_textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_calculation);

        setTitle("খরচ হিসাব ");

        salaryBtn = findViewById(R.id.salary_btn);
        boughtBtn = findViewById(R.id.buy_btn);
        billBtn = findViewById(R.id.bill_btn);
        RentBtn = findViewById(R.id.rent_btn);
        totalCost_textView = findViewById(R.id.totalcost_textView);
        date_TextView = findViewById(R.id.date_textVIew);
        salary_textView = findViewById(R.id.salary_Text_view);
        bought_TextView = findViewById(R.id.buy_Text_view);
        bill_textView = findViewById(R.id.bill_Text_view);
        rent_textView = findViewById(R.id.vara_Text_view);




        date_TextView.setText(autoload.dates);
        salaryBtn.setOnClickListener(view -> {
            showTextInputDialog("বেতন বাবদ খরচ ", "salary");
        });
        boughtBtn.setOnClickListener(view -> {
            showTextInputDialog("ক্রয় খরচ ", "buy");
        });
        billBtn.setOnClickListener(view -> {
            showTextInputDialog("বিল বাবদ খরচ ", "bill");
        });
        RentBtn.setOnClickListener(view -> {
            showTextInputDialog("ভারা বাবদ খরচ ", "rent");
        });


    }


    private void showTextInputDialog(String title, String tag) {
        EditText editText, detailsText;
        Switch switchButtonGive, switchButtonDue;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_text_input, null);

        editText = view.findViewById(R.id.editTextName);
        detailsText = view.findViewById(R.id.editTextPhoneNumber);
        switchButtonGive = view.findViewById(R.id.switchButtonGive);
        switchButtonDue = view.findViewById(R.id.switchButtonDue);

        switchButtonGive.setVisibility(View.GONE);
        switchButtonDue.setVisibility(View.GONE);

        builder.setView(view);

        builder.setPositiveButton("যোগ করুন", (dialog, which) -> {
            if(!editText.getText().toString().isEmpty() && !detailsText.getText().toString().isEmpty()){
                Map<String, Object> costHisab = new HashMap<>();
                costHisab.put("Cost", editText.getText().toString());
                costHisab.put("details", detailsText.getText().toString());
                rootRef.child("denaPaona").child("singleValues").child(autoload.dates).push().setValue(costHisab);

            }else {
                if (editText.getText().toString().isEmpty()){
                    editText.setError("দাম লিখুন");
                }else{
                    detailsText.setError("বিবরণ লিখুন ");
                }
            }
        });

        builder.setNegativeButton("বাদ দিন", null);
        builder.show();
    }




}