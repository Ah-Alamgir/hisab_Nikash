package com.hanifsapp.hisabee.recyclerView.adapters;

import androidx.recyclerview.widget.DiffUtil;

import com.hanifsapp.hisabee.model.ProductList;

import java.util.ArrayList;

public class DiffCallback extends DiffUtil.Callback {
    ArrayList<ProductList> oldList;
    ArrayList<ProductList> newList;

    public DiffCallback(ArrayList<ProductList> oldList, ArrayList<ProductList> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }
}
