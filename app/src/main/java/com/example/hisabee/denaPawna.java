package com.example.hisabee;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class denaPawna extends AppCompatActivity {

    Button newDue;
    private EditText editText, detailsText;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchButtonGive, switchButtonDue;


    TabLayout tabLayout;
    private ViewPager2 viewPager;

    private ArrayList<Map<String, Object>> itemsList = new ArrayList<>();
    static int totalPrice = 0;
    TextView giveTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dena_pawna);

        setTitle(autoload.dates);

        giveTextView = findViewById(R.id.give_take_textView);
        newDue = findViewById(R.id.newDue);

        newDue.setOnClickListener(view -> showTextInputDialog());


        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), getLifecycle());

        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("বাকি ");

                    } else if (position == 1) {
                        tab.setText("বিক্রি");
                    } else if (position == 2) {
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


    private void addData() {
        if (!editText.getText().toString().isEmpty() && !detailsText.getText().toString().isEmpty()) {

            if (switchButtonGive.isChecked()) {
                autoload.getDataToUpdate("todaySpend", Integer.valueOf(editText.getText().toString()), detailsText.getText().toString());
            } else if (switchButtonDue.isChecked()) {
                autoload.getDataToUpdate("todayDue", Integer.valueOf(editText.getText().toString()), detailsText.getText().toString());
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


    public static ArrayList<Map<String, Object>> filterItemsByWeek(String time, String id) {
        ArrayList<Map<String, Object>> filteredItems = new ArrayList<>();

        HashMap<String, Object> items = (HashMap<String, Object>) autoload.singleValues.get("todayDue");

        filteredItems.clear();
        items.forEach((key, value) -> {
            HashMap<String, Object> valueToArray = (HashMap<String, Object>) value;
            HashMap<String, Object> itemToArray = new HashMap<>(); // Create a new map instance

            itemToArray.put("date", String.valueOf(key));
            itemToArray.put("price", String.valueOf(valueToArray.get("price")));
            itemToArray.put("details", String.valueOf(valueToArray.get("details")));

            filteredItems.add(itemToArray);
        });
        return filteredItems;
    }


}