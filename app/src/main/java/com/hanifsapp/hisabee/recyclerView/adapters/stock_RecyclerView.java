package com.hanifsapp.hisabee.recyclerView.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.hanifsapp.hisabee.activity.addProduct;
import com.hanifsapp.hisabee.databinding.RecyclerViewStockBinding;
import com.hanifsapp.hisabee.model.ProductList;

public class stock_RecyclerView extends ListAdapter<ProductList, stock_RecyclerView.viewHolder> {
    Context context;

    protected stock_RecyclerView(@NonNull DiffUtil.ItemCallback<ProductList> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public stock_RecyclerView.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewStockBinding binding = RecyclerViewStockBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull stock_RecyclerView.viewHolder holder, int position) {
        ProductList item = getItem(position);
        holder.bd.nameTextView.setText(item.getName());
        holder.bd.sellPriceTextView.setText(String.valueOf(item.getSellPrice()));
        holder.bd.buyPriceTextView.setText(String.valueOf(item.getBuyPrice()));
        holder.bd.stockTextView.setText("স্টকঃ "+ String.valueOf(item.getStock()));
        holder.bd.deleteBtn.setOnClickListener(view -> {

        });

        holder.bd.editBtn.setOnClickListener(view -> {
            addProduct.edit_position = holder.getAdapterPosition();
            addProduct.editProduct = true;
            context.startActivity(new Intent(context, addProduct.class));

        });    }



    public static class viewHolder extends RecyclerView.ViewHolder {
        RecyclerViewStockBinding bd;
        public viewHolder(@NonNull RecyclerViewStockBinding view) {
            super(view.getRoot());
            bd = view;
        }
    }



}
