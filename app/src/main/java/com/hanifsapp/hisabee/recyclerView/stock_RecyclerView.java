package com.hanifsapp.hisabee.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.activity.addProduct;
import com.hanifsapp.hisabee.autoload;
import com.hanifsapp.hisabee.databinding.RecyclerViewStockBinding;
import com.hanifsapp.hisabee.firebase_Db.getProductList;
import com.hanifsapp.hisabee.model.ProductList;

import java.util.ArrayList;

public class stock_RecyclerView extends RecyclerView.Adapter<stock_RecyclerView.viewHolder> {
    Context context;
    ArrayList<ProductList> mData;

    public stock_RecyclerView(Context context){
        this.context = context;
        mData = getProductList.product_Lists;
    }

    @NonNull
    @Override
    public stock_RecyclerView.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewStockBinding binding = RecyclerViewStockBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull stock_RecyclerView.viewHolder holder, int position) {
        ProductList item = mData.get(position);
        holder.bd.nameTextView.setText(item.getName());
        holder.bd.sellPriceTextView.setText(String.valueOf(item.getSellPrice()));
        holder.bd.buyPriceTextView.setText(String.valueOf(item.getBuyPrice()));
        holder.bd.stockTextView.setText("স্টকঃ "+ String.valueOf(item.getStock()));
        holder.bd.deleteBtn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
            builder.setMessage("Are you sure you want to delete this item?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        String itemId = item.getId();
                        autoload.deleteData(itemId);
                        int positions = mData.indexOf(item);
                        if (positions != -1) {
//                            removeItem(positions);
//                            autoload.productLists.remove(positions);
                        }
                    })
                    .setNegativeButton("No", (dialog, id) -> dialog.dismiss());
            AlertDialog alert = builder.create();
            alert.show();


        });

        holder.bd.editBtn.setOnClickListener(view -> {
            addProduct.edit_position = holder.getAdapterPosition();
            addProduct.editProduct = true;
            context.startActivity(new Intent(context, addProduct.class));

        });    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        RecyclerViewStockBinding bd;
        public viewHolder(@NonNull RecyclerViewStockBinding view) {
            super(view.getRoot());
            bd = view;
        }
    }
}
