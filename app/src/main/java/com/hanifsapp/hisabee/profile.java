package com.hanifsapp.hisabee;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hanifsapp.hisabee.recyclerView.SqopenHelper;

import java.util.ArrayList;


public class profile extends AppCompatActivity {
    private RecyclerView recyclerView;
    Button addButton;
    static SqopenHelper sqopenHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addButton = findViewById(R.id.addButton);

        sqopenHelper = new SqopenHelper(this);

        refreshRecyclerView();


        setTitle("কাস্টমারের তথ্য ");

        addButton.setOnClickListener(v -> {
            showAddCustomerDialog();
        });
    }


    private void showAddCustomerDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_customer, null);
        dialogBuilder.setView(dialogView);

        EditText editTextName = dialogView.findViewById(R.id.editTextName);
        EditText editTextAddress = dialogView.findViewById(R.id.editTextAddress);
        EditText editTextPhoneNumber = dialogView.findViewById(R.id.editTextPhoneNumber);

        dialogBuilder.setPositiveButton("যোগ করুন", (dialog, which) -> {
            String name = editTextName.getText().toString().trim();
            String address = editTextAddress.getText().toString().trim();
            String phoneNumber = editTextPhoneNumber.getText().toString().trim();

            if (!name.isEmpty() && !address.isEmpty() && !phoneNumber.isEmpty()) {

                sqopenHelper.addtoDatabase(name, address, phoneNumber);
                refreshRecyclerView();
                dialog.dismiss();
            } else {
                // Show an error message or handle empty input fields

            }
        });

        dialogBuilder.setNegativeButton("বাদ দিন", null);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
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