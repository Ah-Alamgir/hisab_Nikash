package com.hanifsapp.hisabee.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.autoload;
import com.hanifsapp.hisabee.databinding.RecyclerViewSellBinding;

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




        totalItemBtn = findViewById(R.id.orderPage);
        totalPrice_textView = findViewById(R.id.totalPrice);

        totalItemBtn.setOnClickListener(view ->{
                    startActivity(new Intent(Sell.this, invoice.class));
        });
        setTitle("প্রোডাক্ট নির্বাচন করুন ");

    }


    @Override
    protected void onStart() {

        super.onStart();
        RecyclerView recyclerView = findViewById(R.id.pdRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(autoload.productLists, LayoutInflater.from(this)));
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }


    //57-127, 151
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private final List<Map<String, Object>> mData;
        private final LayoutInflater mInflater;
        public MyAdapter(List<Map<String, Object>> data, LayoutInflater mInflater) {
            mData = data;
            this.mInflater = mInflater;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerViewSellBinding binding1 = RecyclerViewSellBinding.inflate(mInflater, parent, false);
            return new ViewHolder(binding1);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Map<String, Object> item = mData.get(position);
            holder.binding.nameText.setText( String.valueOf(item.get("name")) );
            holder.binding.pieceText.setText("00");
            holder.binding.sellPriceText.setText(Objects.requireNonNull(item.get("sellPrice")).toString());
            holder.binding.stockamountTextView.setText(Objects.requireNonNull(item.get("Stock")).toString());

            holder.binding.plusBtn.setOnClickListener(view -> {
                itemCountVar = itemCountVar+1;
                holder.binding.pieceText.setText(String.valueOf(Integer.parseInt(holder.binding.pieceText.getText().toString())+1));
                addtoCard(String.valueOf(item.get("id")), position, Integer.parseInt(String.valueOf(item.get("sellPrice"))) , Integer.parseInt(String.valueOf(holder.binding.pieceText.getText())));


            });
            holder.binding.minusBtn.setOnClickListener(view -> {
                if(!(Integer.parseInt(holder.binding.sellPriceText.getText().toString()) <=0)){
                    itemCountVar = itemCountVar-1;
                    holder.binding.pieceText.setText(String.valueOf(Integer.parseInt(holder.binding.pieceText.getText().toString())-1));
                    addtoCard(String.valueOf(item.get("id")), position, -Integer.parseInt(String.valueOf(item.get("sellPrice"))) , Integer.parseInt(String.valueOf(holder.binding.pieceText.getText()) ));

                }
            });
        }



        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            RecyclerViewSellBinding binding;

            public ViewHolder(RecyclerViewSellBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

            }
        }
    }





    public void addtoCard(String id, int position, int price,int orderAmount){

        totalPriceVar = totalPriceVar+ price;
        totalPrice_textView.setText(String.valueOf(totalPriceVar));
        totalItemBtn.setText(String.valueOf(itemCountVar));


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