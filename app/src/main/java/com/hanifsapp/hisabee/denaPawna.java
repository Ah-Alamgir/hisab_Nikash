package com.hanifsapp.hisabee;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hanifsapp.hisabee.databinding.ActivityDenaPawnaBinding;
import com.hanifsapp.hisabee.databinding.DialogTextInputBinding;
import com.hanifsapp.hisabee.fragments.Fragment_bay;
import com.hanifsapp.hisabee.fragments.baki;
import com.hanifsapp.hisabee.fragments.bikri;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Map;

public class denaPawna extends AppCompatActivity {

    private ActivityDenaPawnaBinding binding;
    int tabPosition = 0;


    public static String titleText, fragmentName;
//    public static TextView dateText,totalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDenaPawnaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.newDueBtn.setOnClickListener(view -> showTextInputDialog());





    }


    private void showTextInputDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("দেনা পাওনার হিসাব");

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_text_input, null);


        builder.setView(view);

        builder.setPositiveButton("যোগ করুন", (dialog, which) -> {
            addData();
        });

        builder.setNegativeButton("বাদ দিন", null);
        builder.show();
    }


    private void addData() {
        DialogTextInputBinding binding1 = DialogTextInputBinding.inflate(LayoutInflater.from(this));
        String amountText = binding1.denapawnaAmount.getText().toString(), details =  binding1.denapawnaDetails.getText().toString();

        if (!amountText.isEmpty() && !details.isEmpty()) {
            if (binding1.switchButtonGive.isChecked()) {
               Autoload.getDataToUpdate("todaySpend", Integer.parseInt(amountText), details);
            } else if (binding1.switchButtonDue.isChecked()) {
                Autoload.getDataToUpdate("todayDue", Integer.parseInt(amountText), details);
            }

        } else {
            if (amountText.isEmpty()) {
                binding1.denapawnaAmount.setError("দাম লিখুন");
            } else {
                binding1.denapawnaDetails.setError("বিবরণ লিখুন ");
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

            } else if (switchMonth.isChecked()) {
                titleText = getMonthName(selectedMonth);

            } else {
                titleText = selectedDay + " " + getMonthName(selectedMonth) + " " + selectedYear;
            }


//            switch (fragmentName){
//                case "baki":
//                    baki.Update(Update(titleText , Autoload.todaydue));
//                    binding.hisabTextView.setText(String.valueOf(amount));
//                    break;
//                case "bikri":
//                    bikri.Update(Update(titleText , autoload.todaysell));
//                    binding.hisabTextView.setText(String.valueOf(amount));
//                    break;
//                case "bay":
//                    Fragment_bay.Update(Update(titleText , autoload.todayspend));
//                    binding.hisabTextView.setText(String.valueOf(amount));
//                    break;
//            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }



    static int amount = 0;

    static ArrayList<Map<String, Object>> Update(String date , ArrayList<Map<String, Object>> getFromArray){
        amount = 0;
        ArrayList<Map<String, Object>> list = new ArrayList<>();

        for (Map<String, Object> entry: getFromArray){
            if (String.valueOf(entry.get("date")).contains(date)){
                list.add(entry);
                amount = Integer.parseInt(String.valueOf(entry.get("price"))) + amount;
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



