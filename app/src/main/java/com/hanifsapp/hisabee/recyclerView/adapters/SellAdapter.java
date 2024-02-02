package com.hanifsapp.hisabee.recyclerView.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.databinding.RecyclerViewSellBinding;
import com.hanifsapp.hisabee.firebase_Db.GetproductList;
import com.hanifsapp.hisabee.model.ProductList;

import java.util.ArrayList;

public class SellAdapter extends RecyclerView.Adapter<SellAdapter.ViewHolder> {
    int itemCountVar = 0;
    int totalPriceVar = 0;
    private ArrayList<ProductList> items;

    public SellAdapter(Context context, ArrayList<ProductList> item) {
        this.items = item;
    }



    public void updateView(ArrayList<ProductList> newList){
        DiffCallback diffCallback = new DiffCallback(items, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(diffCallback);
        result.dispatchUpdatesTo(this);
        items.clear();
        items = newList;
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
        holder.binding.pieceText.setText("00");
        holder.binding.sellPriceText.setText(String.valueOf(item.getSellPrice()));
        holder.binding.stockamountTextView.setText(String.valueOf(item.getStock()));


        holder.binding.plusBtn.setOnClickListener(view -> {
            itemCountVar = itemCountVar + 1;
            holder.binding.pieceText.setText(String.valueOf(Integer.parseInt(holder.binding.pieceText.getText().toString()) + 1));
            addtoCard(item.getId(), position, item.getSellPrice(), Integer.parseInt(String.valueOf(holder.binding.pieceText.getText())));


        });
        holder.binding.minusBtn.setOnClickListener(view -> {
            if (!(Integer.parseInt(holder.binding.sellPriceText.getText().toString()) <= 0)) {
                itemCountVar = itemCountVar - 1;
                holder.binding.pieceText.setText(String.valueOf(Integer.parseInt(holder.binding.pieceText.getText().toString()) - 1));
                addtoCard(item.getId(), position, -item.getSellPrice(), Integer.parseInt(String.valueOf(holder.binding.pieceText.getText())));
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
