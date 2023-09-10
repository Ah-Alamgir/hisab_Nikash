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
import android.widget.ImageButton;
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
        recyclerView.setAdapter(new MyAdapter(autoload.productLists));


        totalItemBtn = findViewById(R.id.orderPage);
        totalPrice_textView = findViewById(R.id.totalPrice);

        totalItemBtn.setOnClickListener(view -> startActivity(new Intent(Sell.this, OrderPage.class)));
        setTitle("প্রোডাক্ট নির্বাচন করুন ");

    }



    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private final List<Map<String, Object>> mData;
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
            holder.textName.setText( String.valueOf(item.get("name")) );
            holder.pieceAmount.setText("0");
            holder.textSellPrice.setText(Objects.requireNonNull(item.get("sellPrice")).toString());
            holder.finalStock.setText(Objects.requireNonNull(item.get("Stock")).toString());

            holder.plusBtn.setOnClickListener(view -> {
                itemCountVar = itemCountVar+1;
                holder.pieceAmount.setText(String.valueOf(Integer.valueOf(holder.pieceAmount.getText().toString())+1));
                addtoCard(String.valueOf(item.get("id")), position, Integer.valueOf(item.get("sellPrice").toString()) , Integer.valueOf(holder.pieceAmount.getText().toString()));


            });
            holder.minusBtn.setOnClickListener(view -> {
                if(!(Integer.valueOf(holder.pieceAmount.getText().toString()) <=0)){
                    itemCountVar = itemCountVar-1;
                    holder.pieceAmount.setText(String.valueOf(Integer.valueOf(holder.pieceAmount.getText().toString())-1));
                    addtoCard(String.valueOf(item.get("id")), position, -Integer.valueOf(item.get("sellPrice").toString()) , Integer.valueOf(holder.pieceAmount.getText().toString()));

                }
            });
        }



        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textName, textSellPrice, pieceAmount, finalStock;
            ImageButton plusBtn, minusBtn;

            CardView cardView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textName = itemView.findViewById(R.id.name_text);
                textSellPrice = itemView.findViewById(R.id.sell_price_text);
                cardView = itemView.findViewById(R.id.sellCardView);
                plusBtn = itemView.findViewById(R.id.plusBtn);
                minusBtn = itemView.findViewById(R.id.minusBtn);
                pieceAmount = itemView.findViewById(R.id.pieceText);
                finalStock = itemView.findViewById(R.id.stockamount_text_view);

            }


        }

    }

    public void addtoCard(String id, int position, int price,int orderAmount){

        totalPriceVar = totalPriceVar+ price;
        totalPrice_textView.setText(totalPriceVar.toString());
        totalItemBtn.setText(itemCountVar.toString());


        if(!autoload.cardItem_list.contains(id)){
            autoload.cardItem_list.add(id);
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("Stock", autoload.productLists.get(position).get("Stock"));
            map.put("Order", 1 );
            map.put("name",autoload.productLists.get(position).get("name"));
            map.put("sellPrice", autoload.productLists.get(position).get("sellPrice"));
            map.put("Discount", autoload.productLists.get(position).get("Discount"));
            map.put("vat", autoload.productLists.get(position).get("vat"));
            autoload.cardItem.add(map);
        }else{
            autoload.cardItem.get(autoload.cardItem_list.indexOf(id)).put("Order", orderAmount);
        }
    }


}