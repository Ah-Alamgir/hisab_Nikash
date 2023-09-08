package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderPage extends AppCompatActivity {

    TextView priceSholPay;
    public int priceTopay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        setTitle("বিক্রয় বিবরণী");

        RecyclerView recyclerView = findViewById(R.id.recyclerViewOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new OrderPage.MyAdapter(autoload.cardItem));

        priceSholPay = findViewById(R.id.priceShouldPay);
    }

    private class MyAdapter extends RecyclerView.Adapter<OrderPage.MyAdapter.ViewHolder> {

        private List<Map<String, Object>> mData;
        public MyAdapter(List<Map<String, Object>> data) {
            mData = data;
        }


        @NonNull
        @Override
        public OrderPage.MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_page_recycler, parent, false);
            return new OrderPage.MyAdapter.ViewHolder(view);

        }
        int sellPrice=0;
        int discountPrice=0;
        @Override
        public void onBindViewHolder(@NonNull OrderPage.MyAdapter.ViewHolder holder, int position) {
            Map<String, Object> item = mData.get(position);

            sellPrice = Integer.valueOf(item.get("sellPrice").toString()) * Integer.valueOf(item.get("Order").toString());
            discountPrice =  sellPrice- (sellPrice* Integer.valueOf(item.get("Discount").toString())/100);

            holder.textName.setText("নামঃ "+ item.get("name"));
            holder.textDiscount.setText(item.get("Discount").toString() +"%  ডিসকাউন্ট");
            holder.textAmount.setText(item.get("Order").toString() +"  পিছ");
            holder.textSellPrice.setText( discountPrice +" ৳");

            priceTopay = priceTopay+ discountPrice;
            priceSholPay.setText(String.valueOf(priceTopay));


        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textName, textSellPrice, textDiscount, textAmount;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textName = itemView.findViewById(R.id.name_order);
                textSellPrice = itemView.findViewById(R.id.totalPrice_order);
                textDiscount = itemView.findViewById(R.id.discount_order);
                textAmount = itemView.findViewById(R.id.amount_order);


            }


        }

    }
}

