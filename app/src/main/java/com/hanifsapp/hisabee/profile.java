package com.hanifsapp.hisabee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {
    private RecyclerView recyclerView;
    Button addButton;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addButton = findViewById(R.id.addButton);

        databaseHelper = new DatabaseHelper(this);

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
                    Customer customer = new Customer(0, name, address, phoneNumber);
                    insertCustomer(customer);
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
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);

        List<Customer> customerList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            int columnIndexId = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
            int columnIndexName = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
            int columnIndexAddress = cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS);
            int columnIndexPhoneNumber = cursor.getColumnIndex(DatabaseHelper.COLUMN_PHONE_NUMBER);


            do {
                int id = cursor.getInt(columnIndexId);
                String name = cursor.getString(columnIndexName);
                String address = cursor.getString(columnIndexAddress);
                String phoneNumber = cursor.getString(columnIndexPhoneNumber);

                Customer customer = new Customer(id, name, address, phoneNumber);
                customerList.add(customer);
            } while (cursor.moveToNext());

            // Process the customerList as needed
        } else {
            // Handle empty cursor or no data found
        }

        cursor.close();
        db.close();

        CustomerAdapter adapter = new CustomerAdapter(customerList);
        adapter.setOnDeleteClickListener(customer -> deleteCustomer(customer));
        recyclerView.setAdapter(adapter);
    }

    private void insertCustomer(Customer customer) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, customer.getName());
        values.put(DatabaseHelper.COLUMN_ADDRESS, customer.getAddress());
        values.put(DatabaseHelper.COLUMN_PHONE_NUMBER, customer.getPhoneNumber());

        db.insert(DatabaseHelper.TABLE_NAME, null, values);
        db.close();

        refreshRecyclerView();
    }

    private void deleteCustomer(Customer customer) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String selection = DatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(customer.getId())};

        db.delete(DatabaseHelper.TABLE_NAME, selection, selectionArgs);
        db.close();

        refreshRecyclerView();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "customer.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME = "customers";
        private static final String COLUMN_ID = "_id";
        private static final String COLUMN_NAME = "name";
        private static final String COLUMN_ADDRESS = "address";
        private static final String COLUMN_PHONE_NUMBER = "phone_number";

        private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_PHONE_NUMBER + " TEXT" +
                ")";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_QUERY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public static class Customer {
        private int id;
        private String name;
        private String address;
        private String phoneNumber;

        public Customer(int id, String name, String address, String phoneNumber) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.phoneNumber = phoneNumber;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }
    }

    public static class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
        private List<Customer> customerList;
        private OnDeleteClickListener onDeleteClickListener;

        public CustomerAdapter(List<Customer> customerList) {
            this.customerList = customerList;
        }

        public void setOnDeleteClickListener(OnDeleteClickListener listener) {
            onDeleteClickListener = listener;
        }

        @NonNull
        @Override
        public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_customer, parent, false);
            return new CustomerViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
            Customer customer = customerList.get(position);
            holder.textViewName.setText("   নামঃ    "+customer.getName());
            holder.textViewAddress.setText("    ঠিকানাঃ     " + customer.getAddress());
            holder.textViewPhoneNumber.setText("    নাম্বারঃ    "+customer.getPhoneNumber());
        }

        @Override
        public int getItemCount() {
            return customerList.size();
        }

        public interface OnDeleteClickListener {
            void onDeleteClick(Customer customer);
        }

        public class CustomerViewHolder extends RecyclerView.ViewHolder {
            public TextView textViewName;
            public TextView textViewAddress;
            public TextView textViewPhoneNumber;
            public ImageButton buttonDelete;

            public CustomerViewHolder(View itemView) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.textViewName);
                textViewAddress = itemView.findViewById(R.id.textViewAddress);
                textViewPhoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
                buttonDelete = itemView.findViewById(R.id.buttonDelete);

                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && onDeleteClickListener != null) {
                            Customer customer = customerList.get(position);
                            onDeleteClickListener.onDeleteClick(customer);
                        }
                    }
                });
            }
        }
    }
}