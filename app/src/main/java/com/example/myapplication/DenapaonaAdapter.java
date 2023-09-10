package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class DenapaonaAdapter extends RecyclerView.Adapter<DenapaonaAdapter.ViewHolder> {

    private List<Map<String, Object>> dataList;
    public DenapaonaAdapter(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dena_paona_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<String, Object> data = dataList.get(position);
        // Bind your data to the ViewHolder views
        holder.dateTextView.setText((String) data.get("date"));
        holder.priceTextView.setText("টাকার পরিমানঃ  "+ data.get("price"));
        holder.detailsTextView.setText("বিবরনঃ "+ data.get("details"));

        // Set click listener for delete button if needed
        holder.deleteButton.setOnClickListener(v -> {
            dataList.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView priceTextView;
        TextView detailsTextView;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.date_denapaona_text_view);
            priceTextView = itemView.findViewById(R.id.price_denapaona_textview);
            detailsTextView = itemView.findViewById(R.id.details_denapaona_text_view);
            deleteButton = itemView.findViewById(R.id.deleteBtnDenapaona);
        }
    }

    public void setData(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }


}