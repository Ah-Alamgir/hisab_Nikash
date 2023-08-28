
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class productList extends AppCompatActivity {


    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        add = findViewById(R.id.addProduct);
        add.setOnClickListener(view -> {
            startActivity(new Intent(this, addProduct.class));
        });




        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(autoload.data));
    }


    private class MyAdapter extends RecyclerView.Adapter<productList.MyAdapter.ViewHolder> {

        private List<Map<String, Object>> mData;
        public MyAdapter(List<Map<String, Object>> data) {
            mData = data;
        }


        @NonNull
        @Override
        public productList.MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
            return new productList.MyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull productList.MyAdapter.ViewHolder holder, int position) {

            Map<String, Object> item = mData.get(position);
            holder.textName.setText((String) item.get("name"));
            holder.textSellPrice.setText(Objects.requireNonNull(item.get("sellPrice")).toString());

            holder.editProduct.setOnClickListener(view -> {
            startActivity(new Intent());
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textName;
            TextView textSellPrice;
            ImageButton editProduct;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textName = itemView.findViewById(R.id.name_text_view);
                textSellPrice = itemView.findViewById(R.id.sell_price_text_view);
                editProduct = itemView.findViewById(R.id.edits);

            }


        }

    }
}