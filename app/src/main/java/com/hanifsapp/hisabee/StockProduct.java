package com.hanifsapp.hisabee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StockProduct extends AppCompatActivity {


    public TextView totoalStock_textview, totalStock_value_textView;
    Button addNewProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_product);


        RecyclerView recyclerView = findViewById(R.id.stockRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StockProduct.MyAdapter(autoload.productLists));

        totoalStock_textview = findViewById(R.id.total_stock_textView);
        totalStock_value_textView = findViewById(R.id.total_stock_value_text);
        addNewProduct = findViewById(R.id.addProductStock);


        addNewProduct.setOnClickListener(view -> {
            addProduct.editProduct= false;
            startActivity(new Intent(this, addProduct.class));

        });

        setTitle("মোট পণ্য সংখ্যাঃ "+ autoload.productLists.size());
        count();


    }


    private void count(){
        int totalStock_var=0, totalStock_valueVar=0;

        for (Map<String, Object> map : autoload.productLists) {
            totalStock_var = Integer.parseInt(map.get("Stock").toString()) + totalStock_var;
            totalStock_valueVar = (Integer.parseInt(map.get("buyPrice").toString())*  Integer.parseInt(map.get("Stock").toString())) + totalStock_valueVar;
        }
        totoalStock_textview.setText(String.valueOf(totalStock_var));
        totalStock_value_textView.setText(String.valueOf(totalStock_valueVar));

    }


    private class MyAdapter extends RecyclerView.Adapter<StockProduct.MyAdapter.ViewHolder> {

        private final List<Map<String, Object>> mData;
        public MyAdapter(List<Map<String, Object>> data) {
            mData = data;
        }


        @NonNull
        @Override
        public StockProduct.MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_stock, parent, false);
            return new StockProduct.MyAdapter.ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull StockProduct.MyAdapter.ViewHolder holder, int position) {

            Map<String, Object> item = mData.get(position);
            holder.textName.setText((String) item.get("name"));
            holder.textSellPrice.setText(Objects.requireNonNull(item.get("sellPrice")).toString());
            holder.buyPrice.setText(Objects.requireNonNull(item.get("buyPrice")).toString());
            holder.cardViews.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim1));
            holder.stockAmount.setText("স্টকঃ "+ item.get("Stock").toString());
            holder.deleteButton.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(StockProduct.this);
                builder.setMessage("Are you sure you want to delete this item?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialog, id) -> {
                            String itemId = (String) item.get("id");
                            autoload.deleteData(itemId);
                            int positions = mData.indexOf(item);
                            if (positions != -1) {
                                removeItem(positions);
                                autoload.productLists.remove(positions);
                            }
                        })
                        .setNegativeButton("No", (dialog, id) -> dialog.dismiss());
                AlertDialog alert = builder.create();
                alert.show();


            });

            holder.editButton.setOnClickListener(view -> {
                addProduct.edit_position = holder.getAdapterPosition();
                addProduct.editProduct = true;
                startActivity(new Intent(StockProduct.this, addProduct.class));
                finish();
            });

        }


        private void removeItem(int position) {
            notifyItemRemoved(position);
        }
        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textName , stockAmount, textSellPrice, buyPrice;
            ImageButton editButton, deleteButton;
            CardView cardViews;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textName = itemView.findViewById(R.id.name_text_view);
                textSellPrice = itemView.findViewById(R.id.sell_price_text_view);
                stockAmount = itemView.findViewById(R.id.stock_text_view);
                buyPrice = itemView.findViewById(R.id.buy_price_text_view);
                editButton = itemView.findViewById(R.id.editBtn);
                deleteButton = itemView.findViewById(R.id.deleteBtn);
                cardViews = itemView.findViewById(R.id.cardDenapaona);
            }


        }



    }

    @Override
    public void onBackPressed() {
        finish();
    }
}