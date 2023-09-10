package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class denaPawna extends AppCompatActivity {

    Button newDue;
    private EditText editText, detailsText;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchButtonGive, switchButtonTake, switchButtonDue;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DenapaonaAdapter adapter = new DenapaonaAdapter(new ArrayList<>());
    String date;
    TabLayout tabLayout;
    private ViewPager2 viewPager;
    static List<Map<String, Object>> filteredItems = new ArrayList<>();
    static int totalPrice = 0;
    TextView giveTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dena_pawna);

        setTitle("পাবেনঃ "+autoload.singleValues.get("take").toString()  + "       দিবেনঃ " +autoload.singleValues.get("give").toString()  );

        giveTextView= findViewById(R.id.give_take_textView);
        giveTextView.setText(String.valueOf(Integer.valueOf(autoload.singleValues.get("take").toString())- Integer.valueOf(autoload.singleValues.get("give").toString())) );
        newDue = findViewById(R.id.newDue);

        newDue.setOnClickListener(view -> showTextInputDialog());
        date = autoload.dates;



        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), getLifecycle());

        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position==0) {
                        tab.setText("বাকি ");

                    }else if(position==1) {
                        tab.setText("বিক্রি");
                    }else if(position==2){
                        tab.setText("ব্যায় ");
                    }
                }
        ).attach();





    }

































    private void showTextInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("দেনা পাওনার হিসাব");

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_text_input, null);

        editText = view.findViewById(R.id.denaPawana_editText);
        detailsText = view.findViewById(R.id.biboron_editText);
        switchButtonGive = view.findViewById(R.id.switchButtonGive);
        switchButtonDue = view.findViewById(R.id.switchButtonDue);

        builder.setView(view);

        builder.setPositiveButton("যোগ করুন", (dialog, which) -> {
            addData();
        });

        builder.setNegativeButton("বাদ দিন", null);
        builder.show();
    }


    private void addData(){
        if (!editText.getText().toString().isEmpty() && !detailsText.getText().toString().isEmpty()){
            DatabaseReference myRef = database.getReference("denaPaona");
            Map<String, Object> hisab = new HashMap<>();
            hisab.put("price", editText.getText().toString());
            hisab.put("details", detailsText.getText().toString());
            hisab.put("date", date);
            if (switchButtonGive.isChecked()) {
                autoload.getDataToUpdate("todaySpend", Integer.valueOf(editText.getText().toString()));
                myRef.child("singleValues").child("give").push().setValue(hisab);
            } else if (switchButtonTake.isChecked()) {
                myRef.child("singleValues").child("take").push().setValue(hisab);
            } else if (switchButtonDue.isChecked()) {
                autoload.getDataToUpdate("todayDue",Integer.valueOf(editText.getText().toString()) );
                myRef.child("singleValues").child("Due").push().setValue(hisab);
            }

        }else {
            if (editText.getText().toString().isEmpty()){
                editText.setError("দাম লিখুন");
            } else if (!switchButtonTake.isChecked() || !switchButtonGive.isChecked()) {
                Toast.makeText(this, "দিবেন না পাবেন নিশ্চিত করুন", Toast.LENGTH_SHORT).show();
            }else{
                detailsText.setError("বিবরণ লিখুন ");
            }
        }
    }








    private static class MyPagerAdapter extends FragmentStateAdapter {
        private static final int NUM_PAGES = 3; // Replace with the number of tabs

        public MyPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @Override
        public Fragment createFragment(int position) {
            // Create and return a new Fragment instance based on the position
            switch (position) {
                case 0:
                    return baki.newInstance();
                case 1:
                    return bikri.newInstance();
                case 2:
                    return Fragment_bay.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }










     public static List<Map<String, Object>> filterItemsByWeek(String time, String id) {
        Map<String, Object> giveMap = (Map<String, Object>) autoload.singleValues.get(id);
        filteredItems.clear();
        try {
            for (Map.Entry<String, Object> entry : giveMap.entrySet()) {
                Map<String, Object> item = (Map<String, Object>) entry.getValue();
                String itemDateValue = (String) item.get("date");
                if (itemDateValue.contains(time)) {
                    int itemPrice = Integer.valueOf(item.get("price").toString()) ;
                    totalPrice += itemPrice;
                    filteredItems.add(item);

                }
            }
        }catch (Exception e) {

        }

        return filteredItems;
    }

}


