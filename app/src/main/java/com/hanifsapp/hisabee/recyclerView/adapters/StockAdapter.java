package com.hanifsapp.hisabee.recyclerView.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.activity.addProductActivity;
import com.hanifsapp.hisabee.databinding.RecyclerViewStockBinding;
import com.hanifsapp.hisabee.model.ProductList;
import com.hanifsapp.hisabee.recyclerView.interFaces.onStockclickListner;

import java.util.ArrayList;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.viewHolder> {
    private final Context context;
    private final ArrayList<ProductList> getItem;
    private onStockclickListner listner;


    public StockAdapter(Context context, ArrayList<ProductList> items, onStockclickListner listen) {
        this.context = context;
        this.getItem = items;
        this.listner = listen;

    }




    public void updateList(ArrayList<ProductList> newList) {
        DiffCallback diffUtill = new DiffCallback(getItem, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffUtill);
        getItem.clear();
        getItem.addAll(newList);
        result.dispatchUpdatesTo(this);



    }

    @NonNull
    @Override
    public StockAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(RecyclerViewStockBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StockAdapter.viewHolder holder, int position) {
        ProductList item = this.getItem.get(position);
        holder.bd.nameTextView.setText(item.getName());
        holder.bd.sellPriceTextView.setText(String.valueOf(item.getSellPrice()));
        holder.bd.buyPriceTextView.setText(String.valueOf(item.getBuyPrice()));
        holder.bd.stockTextView.setText("স্টকঃ " + item.getStock());

        holder.bd.deleteBtn.setOnClickListener(view -> listner.onDeleteClick(item.getId()));
        holder.bd.editBtn.setOnClickListener(view -> {
            listner.onEditClick(position);
            addProductActivity.item = item;

        });
    }

    @Override
    public int getItemCount() {

        return getItem.size();
    }


    public static class viewHolder extends RecyclerView.ViewHolder {
        RecyclerViewStockBinding bd;

        public viewHolder(@NonNull RecyclerViewStockBinding view) {
            super(view.getRoot());
            bd = view;
        }
    }


}
