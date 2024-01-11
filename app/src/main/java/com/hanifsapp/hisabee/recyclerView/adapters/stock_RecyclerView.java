package com.hanifsapp.hisabee.recyclerView.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.activity.addProduct;
import com.hanifsapp.hisabee.databinding.RecyclerViewStockBinding;
import com.hanifsapp.hisabee.model.ProductList;
import com.hanifsapp.hisabee.recyclerView.interFaces.onDeleteClickListener;

public class stock_RecyclerView extends ListAdapter<ProductList, stock_RecyclerView.viewHolder>{
    //    Context context;
    private final onDeleteClickListener deleteCLickListener;


    protected stock_RecyclerView(@NonNull DiffUtil.ItemCallback<ProductList> diffCallback, onDeleteClickListener delete) {
        super(diffCallback);
        this.deleteCLickListener = delete;
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
        holder.bd.stockTextView.setText("স্টকঃ " + String.valueOf(item.getStock()));
        holder.bd.deleteBtn.setOnClickListener(view -> {
            this.deleteCLickListener.onDeleteClick(item.getId());
        });

        holder.bd.editBtn.setOnClickListener(view -> {
            addProduct.edit_position = holder.getAdapterPosition();
            addProduct.editProduct = true;

        });
    }


    public static class viewHolder extends RecyclerView.ViewHolder {
        RecyclerViewStockBinding bd;

        public viewHolder(@NonNull RecyclerViewStockBinding view) {
            super(view.getRoot());
            bd = view;
        }
    }


}
