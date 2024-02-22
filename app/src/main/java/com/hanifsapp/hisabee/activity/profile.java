package com.hanifsapp.hisabee.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.Autoload;
import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.recyclerView.adapters.SqopenHelper;

import java.util.ArrayList;
import java.util.Map;


public class profile extends AppCompatActivity {
    private RecyclerView recyclerView;
    Button addButton, FETCH_BUTTON;
    static SqopenHelper sqopenHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addButton = findViewById(R.id.addButton);
        FETCH_BUTTON = findViewById(R.id.GETFIREBASE);

        sqopenHelper = new SqopenHelper(this);

        refreshRecyclerView();





        FETCH_BUTTON.setOnClickListener(v -> {
            for (Map<String, Object> map : Autoload.CustomerInfo){

                sqopenHelper.addtoDatabase(String.valueOf(map.get("name")), String.valueOf(map.get("address")),String.valueOf(map.get("phoneNumber")));
            }
            refreshRecyclerView();
        });
    }




    private void refreshRecyclerView() {
        CustomerAdapter adapter = new CustomerAdapter(sqopenHelper.getDataList(), this);
        recyclerView.setAdapter(adapter);
    }















    public static class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
        private final ArrayList<String> customerList;
        public SqopenHelper sqopenHelper;


        public CustomerAdapter(ArrayList<String> customerList, Context context) {
            this.customerList = customerList;
            sqopenHelper = new SqopenHelper(context);
        }


        @NonNull
        @Override
        public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_customer, parent, false);
            return new CustomerViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
            holder.textViewName.setText(customerList.get(position).split("id")[0]);
            holder.buttonDelete.setOnClickListener(v -> {
                sqopenHelper.deleteData(Integer.parseInt(customerList.get(position).split("id:")[1]));
                customerList.remove(position);
                notifyItemRemoved(position);

            });
        }

        @Override
        public int getItemCount() {
            return customerList.size();
        }


        public class CustomerViewHolder extends RecyclerView.ViewHolder {
            public TextView textViewName;
            public ImageButton buttonDelete;

            public CustomerViewHolder(View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.textViewName);
                buttonDelete = itemView.findViewById(R.id.buttonDelete);

            }
        }
    }
}