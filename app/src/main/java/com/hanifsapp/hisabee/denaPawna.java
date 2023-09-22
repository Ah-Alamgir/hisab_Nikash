package com.hanifsapp.hisabee;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Map;

public class denaPawna extends AppCompatActivity {

    Button newDue;
    public static String titleText, fragmentName;
    private EditText editText, detailsText;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchButtonGive, switchButtonDue;

    TabLayout tabLayout;
    private ViewPager2 viewPager;

    int tabPosition = 0;
    public static TextView dateText,totalText;
    ImageButton datePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dena_pawna);

        newDue = findViewById(R.id.newDue);
        newDue.setOnClickListener(view -> showTextInputDialog());


        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tabPosition = 0;
                        tab.setText("বাকি");
                    } else if (position == 1) {
                        tabPosition = 1;
                        tab.setText("বিক্রি");
                    } else if (position == 2) {
                        tabPosition = 2;
                        tab.setText("ব্যায় ");
                    }
                }
        ).attach();





        datePicker = findViewById(R.id.imageButton2);
        dateText = findViewById(R.id.date_TextView);
        totalText = findViewById(R.id.hisab_textView);
        dateText.setText(autoload.dates);

        datePicker.setOnClickListener(v -> showDatePickerDialog());

    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            showDatePickerDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }












    private void showTextInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("দেনা পাওনার হিসাব");

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_text_input, null);

        editText = view.findViewById(R.id.editTextName);
        detailsText = view.findViewById(R.id.editTextPhoneNumber);
        switchButtonGive = view.findViewById(R.id.switchButtonGive);
        switchButtonDue = view.findViewById(R.id.switchButtonDue);

        builder.setView(view);

        builder.setPositiveButton("যোগ করুন", (dialog, which) -> {
            addData();
        });

        builder.setNegativeButton("বাদ দিন", null);
        builder.show();

        
    }


    private void addData() {
        if (!editText.getText().toString().isEmpty() && !detailsText.getText().toString().isEmpty()) {

            if (switchButtonGive.isChecked()) {
                autoload.getDataToUpdate("todaySpend", Integer.parseInt(editText.getText().toString()), detailsText.getText().toString());
            } else if (switchButtonDue.isChecked()) {
                autoload.getDataToUpdate("todayDue", Integer.parseInt(editText.getText().toString()), detailsText.getText().toString());
            }

        } else {
            if (editText.getText().toString().isEmpty()) {
                editText.setError("দাম লিখুন");
            } else {
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
                    fragmentName = "baki";
                    return baki.newInstance();
                case 1:
                    fragmentName = "bikri";
                    return bikri.newInstance();
                case 2:
                    fragmentName = "bay";
                    return Fragment_bay.newInstance();
                default:
                    fragmentName = "baki";
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }






    private void showDatePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_date_picker, null);
        builder.setView(dialogView);

        final DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch switchMonth = dialogView.findViewById(R.id.switch_month);
        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch switchYear = dialogView.findViewById(R.id.switch_year);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String selectedYear = String.valueOf(datePicker.getYear());
            int selectedMonth = datePicker.getMonth();
            String selectedDay = String.valueOf(datePicker.getDayOfMonth());

            if (switchYear.isChecked()) {
                titleText = selectedYear;
                dateText.setText(titleText);
            } else if (switchMonth.isChecked()) {
                titleText = getMonthName(selectedMonth);
                dateText.setText(titleText);
            } else {
                titleText = selectedDay + " " + getMonthName(selectedMonth) + " " + selectedYear;
                dateText.setText(titleText);
            }


            switch (fragmentName){
                case "baki":
                    baki.Update(Update(titleText , autoload.todaydue));
                    totalText.setText(String.valueOf(amount));
                    break;
                case "bikri":
                    bikri.Update(Update(titleText , autoload.todaysell));
                    totalText.setText(String.valueOf(amount));
                    break;
                case "bay":
                    Fragment_bay.Update(Update(titleText , autoload.todayspend));
                    totalText.setText(String.valueOf(amount));
                    break;
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }



    static int amount = 0;

    static ArrayList<Map<String, Object>> Update(String date , ArrayList<Map<String, Object>> getFromArray){
        amount = 0;
        ArrayList<Map<String, Object>> list = new ArrayList<>();

        for (Map<String, Object> entry: getFromArray){
            if (entry.get("date").toString().contains(date)){
                list.add(entry);
                amount = Integer.parseInt(entry.get("price").toString()) + amount;
            }
        }
        return list;
    }








    private String getMonthName(int month) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String[] monthNames = symbols.getMonths();
        return monthNames[month];
    }

}


