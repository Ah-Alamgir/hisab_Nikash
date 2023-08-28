package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Sell extends AppCompatActivity {

    public Integer itemCountVar=0;
    public Integer totalPriceVar=0;
    public TextView totalPrice_textView;
    public Button totalItemBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);



        RecyclerView recyclerView = findViewById(R.id.pdRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(autoload.data));


        totalItemBtn = findViewById(R.id.orderPage);
        totalPrice_textView = findViewById(R.id.totalPrice);

        totalItemBtn.setOnClickListener(view -> startActivity(new Intent(Sell.this, OrderPage.class)));
        setTitle("প্রোডাক্ট নির্বাচন করুন ");


    }



    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<Map<String, Object>> mData;
        public MyAdapter(List<Map<String, Object>> data) {
            mData = data;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sellrecycler, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Map<String, Object> item = mData.get(position);
            holder.textName.setText((String) item.get("name"));
            holder.textSellPrice.setText(Objects.requireNonNull(item.get("sellPrice")).toString());

            holder.cardView.setOnClickListener(view -> {
                totalPriceVar = totalPriceVar+ Integer.parseInt((String) holder.textSellPrice.getText());
                itemCountVar = itemCountVar+1;
                totalPrice_textView.setText(totalPriceVar.toString());
                totalItemBtn.setText(itemCountVar.toString());

                addtoCard((String) item.get("id"), position);

            });
        }



        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textName;
            TextView textSellPrice;
            CardView cardView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textName = itemView.findViewById(R.id.name_text);
                textSellPrice = itemView.findViewById(R.id.sell_price_text);
                cardView = itemView.findViewById(R.id.sellCardView);

            }


        }

    }


    public void addtoCard(String id, int position){
        if(!autoload.cardItem_list.contains(id)){
            autoload.cardItem_list.add(id);
            Map<String, Object> map = new HashMap<>();
            map.put("Order", 1 );
            map.put("name",autoload.data.get(position).get("name"));
            map.put("sellPrice", autoload.data.get(position).get("sellPrice"));
            map.put("Discount", autoload.data.get(position).get("Discount"));
            map.put("vat", autoload.data.get(position).get("vat"));
            autoload.cardIem.add(map);
        }else{
            int order = (int) autoload.cardIem.get(autoload.cardItem_list.indexOf(id)).get("Order");
            order = order+1;
            autoload.cardIem.get(autoload.cardItem_list.indexOf(id)).put("Order", order);
        }

    }


}