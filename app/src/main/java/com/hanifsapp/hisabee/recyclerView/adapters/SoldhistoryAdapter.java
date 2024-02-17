package com.hanifsapp.hisabee.recyclerView.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.model.SoldHistory;

import java.util.ArrayList;

public class SoldhistoryAdapter extends RecyclerView.Adapter<SoldhistoryAdapter.ViewHolder> {
    Context mcontext;
    private final ArrayList<SoldHistory> mData;

    public SoldhistoryAdapter(ArrayList<SoldHistory> data, Context context) {
        mData = data;
        mcontext = context;
    }


    @NonNull
    @Override
    public SoldhistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_dena_paona, parent, false);
        return new SoldhistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoldhistoryAdapter.ViewHolder holder, int position) {

        SoldHistory item = mData.get(position);
        holder.price.setText("মোটঃ "+ item.getPrice());
        holder.dates.setText(item.getDate());


        holder.deleteButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
            builder.setMessage("Do you want to delete this item?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        String itemId = item.getDate();

                    })
                    .setNegativeButton("No", (dialog, id) -> dialog.dismiss());
            AlertDialog alert = builder.create();
            alert.show();
        });


    }


    private void removeItem(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView price, details, dates;
        ImageButton deleteButton;
        CardView cardViews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price_denapaona_textview);
            dates = itemView.findViewById(R.id.date_denapaona_text_view);

            cardViews = itemView.findViewById(R.id.cardDenapaona);
            deleteButton = itemView.findViewById(R.id.deleteBtnDenapaona);

        }


    }



}