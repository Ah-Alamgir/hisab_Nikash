package com.hanifsapp.hisabee.recyclerView.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.databinding.RecyclerViewSellBinding;
import com.hanifsapp.hisabee.firebase_Db.getProductList;
import com.hanifsapp.hisabee.model.ProductList;

public class sell_recyclerView extends RecyclerView.Adapter<sell_recyclerView.ViewHolder>{
    int itemCountVar=0;
    int totalPriceVar=0;

    public sell_recyclerView() {
    }

    @NonNull
    @Override
    public sell_recyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewSellBinding binding = RecyclerViewSellBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull sell_recyclerView.ViewHolder holder, int position) {

        ProductList item = getProductList.product_Lists.get(position);
        holder.binding.nameText.setText(item.getName());
        holder.binding.pieceText.setText("00");
        holder.binding.sellPriceText.setText( String.valueOf(item.getSellPrice()) );
        holder.binding.stockamountTextView.setText(String.valueOf(item.getStock()));


        holder.binding.plusBtn.setOnClickListener(view -> {
            itemCountVar = itemCountVar+1;
            holder.binding.pieceText.setText(String.valueOf(Integer.parseInt(holder.binding.pieceText.getText().toString())+1));
            addtoCard(item.getId(), position, item.getSellPrice() , Integer.parseInt(String.valueOf(holder.binding.pieceText.getText())));


        });
        holder.binding.minusBtn.setOnClickListener(view -> {
            if(!(Integer.parseInt(holder.binding.sellPriceText.getText().toString()) <=0)){
                itemCountVar = itemCountVar-1;
                holder.binding.pieceText.setText(String.valueOf(Integer.parseInt(holder.binding.pieceText.getText().toString())-1));
                addtoCard(item.getId(), position, - item.getSellPrice() , Integer.parseInt(String.valueOf(holder.binding.pieceText.getText()) ));
            }
        });

    }



    @Override
    public int getItemCount() {
        return getProductList.product_Lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerViewSellBinding binding;
        public ViewHolder(RecyclerViewSellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public void addtoCard(String id, int position, int price,int orderAmount){

        totalPriceVar = totalPriceVar+ price;
//        totalPrice_textView.setText(String.valueOf(totalPriceVar));
//        totalItemBtn.setText(String.valueOf(itemCountVar));


        if(!getProductList.added_tocard.contains(id)){
            getProductList.added_tocard.add(id);
            ProductList item = getProductList.product_Lists.get(position);
            item.setOrder(1);
            getProductList.card_list.add(item);
        }else{
            int location = getProductList.added_tocard.indexOf(id);
            getProductList.card_list.get(location).setOrder(orderAmount);
        }
    }

}
