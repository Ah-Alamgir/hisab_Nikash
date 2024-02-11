package com.hanifsapp.hisabee.recyclerView.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.databinding.RecyclerViewSellBinding;
import com.hanifsapp.hisabee.firebase_Db.GetproductList;
import com.hanifsapp.hisabee.model.ProductList;
import com.hanifsapp.hisabee.recyclerView.interFaces.invoiceListener;

import java.util.ArrayList;

public class SellAdapter extends RecyclerView.Adapter<SellAdapter.ViewHolder> {

    int totalPriceVar = 0;
    private ArrayList<ProductList> items;
    invoiceListener listener;

    public SellAdapter(Context context, ArrayList<ProductList> item, invoiceListener listen) {
        this.items = item;
        this.listener = listen;
    }


    @NonNull
    @Override
    public SellAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewSellBinding binding = RecyclerViewSellBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SellAdapter.ViewHolder holder, int position) {

        ProductList item = GetproductList.product_list.getValue().get(position);
        holder.binding.nameText.setText(item.getName());
        holder.binding.amountText.setText("00");
        holder.binding.sellPriceText.setText(String.valueOf(item.getSellPrice()));
        holder.binding.stockamountTextView.setText(String.valueOf(item.getStock()));


        holder.binding.plusBtn.setOnClickListener(view -> {
            int orderAmount = Integer.parseInt(holder.binding.amountText.getText().toString()) + 1;
            holder.binding.amountText.setText(String.valueOf(orderAmount));
            addtoCard(item.getId(), position, item.getSellPrice(), orderAmount);
            listener.setInvoice(item.getSellPrice(), 1);


        });
        holder.binding.minusBtn.setOnClickListener(view -> {

            if (!(Integer.parseInt(holder.binding.amountText.getText().toString()) <= 0)) {
                int orderAmount = Integer.parseInt(holder.binding.amountText.getText().toString()) - 1;
                holder.binding.amountText.setText(String.valueOf(orderAmount));
                addtoCard(item.getId(), position, -item.getSellPrice(), orderAmount);
                listener.setInvoice(item.getSellPrice() * -1, -1);
            }
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerViewSellBinding binding;

        public ViewHolder(RecyclerViewSellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public void addtoCard(String id, int position, int price, int orderAmount) {

        totalPriceVar = totalPriceVar + price;
        //        totalPrice_textView.setText(String.valueOf(totalPriceVar));
        //        totalItemBtn.setText(String.valueOf(itemCountVar));


        if (!GetproductList.added_tocard.contains(id)) {
            GetproductList.added_tocard.add(id);
            ProductList item = GetproductList.product_list.getValue().get(position);
            item.setOrder(1);
            GetproductList.card_list.add(item);
        } else {
            int location = GetproductList.added_tocard.indexOf(id);
            GetproductList.card_list.get(location).setOrder(orderAmount);
        }
    }

}
