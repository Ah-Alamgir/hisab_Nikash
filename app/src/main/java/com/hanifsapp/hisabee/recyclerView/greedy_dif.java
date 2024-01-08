package com.hanifsapp.hisabee.recyclerView;

import androidx.recyclerview.widget.DiffUtil;

import com.hanifsapp.hisabee.model.ProductList;

import java.util.ArrayList;

public class greedy_dif extends DiffUtil.Callback {
    ArrayList<ProductList> oldList;
    ArrayList<ProductList> newList;
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
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
