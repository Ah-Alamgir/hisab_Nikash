package com.hanifsapp.hisabee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderPage extends AppCompatActivity {

    TextView priceSholPay;
    public int priceTopay = 0;
    Button startPrint;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("denaPaona");
    public String customerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        setTitle("বিক্রয় বিবরণী");

        RecyclerView recyclerView = findViewById(R.id.recyclerViewOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new OrderPage.MyAdapter(autoload.cardItem));
        startPrint = findViewById(R.id.startPrinting);
        priceSholPay = findViewById(R.id.priceShouldPay);



        startPrint.setOnClickListener(view -> {

            autoload.getDataToUpdate("todaySell", priceTopay, customerName);
            //update stock ammount in fireBase

            Map<String, Object> updateItem = new HashMap<String, Object>();
            for (Map<String, Object> item: autoload.cardItem){
                updateItem.put("Stock",Integer.valueOf(item.get("Stock").toString())- Integer.valueOf(item.get("Order").toString()) );
                myRef.child("ProductList").child(item.get("id").toString()).updateChildren(updateItem);
            }

        });

    }

    private class MyAdapter extends RecyclerView.Adapter<OrderPage.MyAdapter.ViewHolder> {

        private final List<Map<String, Object>> mData;

        public MyAdapter(List<Map<String, Object>> data) {
            mData = data;
        }


        @NonNull
        @Override
        public OrderPage.MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_order_page, parent, false);
            return new OrderPage.MyAdapter.ViewHolder(view);

        }

        int sellPrice = 0;
        int discountPrice = 0;

        @Override
        public void onBindViewHolder(@NonNull OrderPage.MyAdapter.ViewHolder holder, int position) {
            Map<String, Object> item = mData.get(position);

            sellPrice = Integer.valueOf(item.get("sellPrice").toString()) * Integer.valueOf(item.get("Order").toString());
            discountPrice = sellPrice - (sellPrice * Integer.valueOf(item.get("Discount").toString()) / 100);

            holder.textName.setText("নামঃ " + item.get("name"));
            holder.textDiscount.setText(item.get("Discount").toString() + "%  ডিসকাউন্ট");
            holder.textAmount.setText(item.get("Order").toString() + "  পিছ");
            holder.textSellPrice.setText(discountPrice + " ৳");

            priceTopay = priceTopay + discountPrice;
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

