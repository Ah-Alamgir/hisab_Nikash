package com.example.myapplication;

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


public class Fragment_bay extends Fragment {

    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public Fragment_bay() {
    }

    public static Fragment_bay newInstance() {
        Fragment_bay fragment = new Fragment_bay();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bay, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.Fragment_bay_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new Fragment_bay.MyAdapter(denaPawna.filterItemsByWeek(getTime("month"),"give")));

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












    private class MyAdapter extends RecyclerView.Adapter<Fragment_bay.MyAdapter.ViewHolder> {

        private final List<Map<String, Object>> mData;

        public MyAdapter(List<Map<String, Object>> data) {
            mData = data;
        }


        @NonNull
        @Override
        public Fragment_bay.MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dena_paona_recyclerview, parent, false);
            return new Fragment_bay.MyAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Fragment_bay.MyAdapter.ViewHolder holder, int position) {

            Map<String, Object> item = mData.get(position);
            holder.price.setText((String) item.get("details"));
            holder.details.setText(Objects.requireNonNull(item.get("price")).toString());
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




