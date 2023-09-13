package com.example.hisabee;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class baki extends Fragment {

    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public baki() {
    }

    public static baki newInstance() {
        baki fragment = new baki();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baki, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.baki_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new baki.MyAdapter(denaPawna.filterItemsByWeek(getTime("month"),"todaySell")));
        return view;
    }







    private String getTime(String timeZone){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat;
        String dates="";
        if(timeZone == "month"){
            dateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
            dates= dateFormat.format(calendar.getTime());

        }else if(timeZone == "year"){
            dateFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
            dates= dateFormat.format(calendar.getTime());
        }
        return dates;
    }












    private class MyAdapter extends RecyclerView.Adapter<baki.MyAdapter.ViewHolder> {

        private final List<Map<String, Object>> mData;

        public MyAdapter(List<Map<String, Object>> data) {
            mData = data;
        }


        @NonNull
        @Override
        public baki.MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dena_paona_recyclerview, parent, false);
            return new baki.MyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull baki.MyAdapter.ViewHolder holder, int position) {

            Map<String, Object> item = mData.get(position);
            holder.price.setText((String) item.get("price"));
            holder.details.setText(Objects.requireNonNull(item.get("details")).toString());
            holder.dates.setText(Objects.requireNonNull(item.get("date")).toString());
            holder.cardViews.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim1));

            holder.deleteButton.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this item?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialog, id) -> {
                            String itemId = (String) item.get("id");
                            autoload.deleteData(itemId);
                            int positions = mData.indexOf(item);
                            if (positions != -1) {
                                removeItem(positions);
                                autoload.productLists.remove(positions);
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

}




