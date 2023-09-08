package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.recyclerView.MyAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class denaPawna extends AppCompatActivity {

    Button newDue;
    private EditText editText, detailsText;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchButtonGive, switchButtonTake;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DenapaonaAdapter adapter = new DenapaonaAdapter(new ArrayList<>());
    LocalDate date;
    TabLayout tabLayout;
    private ViewPager2 viewPager;

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
        date = LocalDate.now();


//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        DenapaonaAdapter adapter = new DenapaonaAdapter(new ArrayList<>());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter.setData(autoload.give);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPagerAdapter());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Check if Tab 1 is selected
                if (tab.getPosition() == 0) {
                    adapter.setData(autoload.give);
                } else if (tab.getPosition()==2) {
                    adapter.setData(autoload.take);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });

    }


    private void showTextInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("দেনা পাওনার হিসাব");

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_text_input, null);

        editText = view.findViewById(R.id.denaPawana_editText);
        detailsText = view.findViewById(R.id.biboron_editText);
        switchButtonGive = view.findViewById(R.id.switchButtonGive);
        switchButtonTake = view.findViewById(R.id.switchButtonTake);

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
            hisab.put("date", date.toString());
            if (switchButtonGive.isChecked()) {
                myRef.child("give").setValue(Integer.valueOf(editText.getText().toString()));
                myRef.child("history").child("give").push().setValue(hisab);
            } else if (switchButtonTake.isChecked()) {
                myRef.child("take").setValue(Integer.valueOf(editText.getText().toString()));
                myRef.child("history").child("take").push().setValue(hisab);
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





    private class MyPagerAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_take, parent, false);
            RecyclerView recyclerView = view.findViewById(R.id.giveRecyclerViewFrag);
            recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext()));
            return new MyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
            List<String> itemList;

            if (position == 0) {
                // Tab 1
                itemList = createTab1ItemList();
            } else {
                // Tab 2
                itemList = createTab2ItemList();
            }

            MyAdapter adapter = new MyAdapter(itemList);
//            holder.recyclerView.setAdapter(adapter);
        }

        @Override
        public int getItemCount() {
            return 2; // Two tab items
        }

        private List<String> createTab1ItemList() {
            List<String> itemList = new ArrayList<>();
            itemList.add("Tab 1 Item 1");
            itemList.add("Tab 1 Item 2");
            return itemList;
        }

        private List<String> createTab2ItemList() {
            List<String> itemList = new ArrayList<>();
            itemList.add("Tab 2 Item 1");
            itemList.add("Tab 2 Item 2");
            return itemList;
        }
    }
}
