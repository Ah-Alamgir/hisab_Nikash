package com.hanifsapp.hisabee.recyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.autoload;


import java.util.List;
import java.util.Map;
import java.util.Objects;

public class denapaonaAdapter extends RecyclerView.Adapter<denapaonaAdapter.ViewHolder> {
    Context mcontext;
    String tags;
    private final List<Map<String, Object>> mData;

    public denapaonaAdapter(List<Map<String, Object>> data , Context context, String tag) {
        mData = data;
        mcontext = context;
        tags = tag;
    }


    @NonNull
    @Override
    public denapaonaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_dena_paona, parent, false);
        return new denapaonaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull denapaonaAdapter.ViewHolder holder, int position) {

        Map<String, Object> item = mData.get(position);
        holder.price.setText("মোটঃ "+ String.valueOf(item.get("price")));
        holder.details.setText(Objects.requireNonNull(item.get("details")).toString());
        holder.dates.setText(Objects.requireNonNull(item.get("date")).toString());
        holder.cardViews.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim1));


        holder.deleteButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
            builder.setMessage("Are you sure you want to delete this item?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        String itemId = (String) item.get("date");
                        autoload.deleteFragmentData(itemId, tags);
                        int positions = mData.indexOf(item);
                        if (positions != -1) {
                            removeItem(positions);
                            if (tags.equals("todaySell")) {
                                autoload.todaysell.remove(positions);
                            }else if (tags.equals("todayDue")){
                                autoload.todaydue.remove(positions);
                            }else if (tags.equals("todaySpend")){
                                autoload.todayspend.remove(positions);
                            }

                        }
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
            details = itemView.findViewById(R.id.details_denapaona_text_view);
            dates = itemView.findViewById(R.id.date_denapaona_text_view);

            cardViews = itemView.findViewById(R.id.cardDenapaona);
            deleteButton = itemView.findViewById(R.id.deleteBtnDenapaona);

        }


    }



}