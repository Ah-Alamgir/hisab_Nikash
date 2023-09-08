package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class OrderPage extends AppCompatActivity {

    TextView priceSholPay;
    public int priceTopay = 0;
    private static final UUID UUID_SERIAL_PORT_PROFILE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice printerDevice;
    private BluetoothSocket printerSocket;
    private OutputStream outputStream;

    private ActivityResultLauncher<Intent> enableBluetoothLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        setTitle("বিক্রয় বিবরণী");

        RecyclerView recyclerView = findViewById(R.id.recyclerViewOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new OrderPage.MyAdapter(autoload.cardItem));

        priceSholPay = findViewById(R.id.priceShouldPay);

        bluetoothSetup();


    }

    private class MyAdapter extends RecyclerView.Adapter<OrderPage.MyAdapter.ViewHolder> {

        private List<Map<String, Object>> mData;

        public MyAdapter(List<Map<String, Object>> data) {
            mData = data;
        }


        @NonNull
        @Override
        public OrderPage.MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_page_recycler, parent, false);
            return new OrderPage.MyAdapter.ViewHolder(view);

        }

        int sellPrice = 0;
        int discountPrice = 0;

        @Override
        public void onBindViewHolder(@NonNull OrderPage.MyAdapter.ViewHolder holder, int position) {
            Map<String, Object> item = mData.get(position);

            sellPrice = Integer.valueOf(item.get("sellPrice").toString()) * Integer.valueOf(item.get("Order").toString());
            discountPrice = sellPrice - (sellPrice * Integer.valueOf(item.get("Discount").toString()) / 100);

            holder.textName.setText("নামঃ " + item.get("name"));
            holder.textDiscount.setText(item.get("Discount").toString() + "%  ডিসকাউন্ট");
            holder.textAmount.setText(item.get("Order").toString() + "  পিছ");
            holder.textSellPrice.setText(discountPrice + " ৳");

            priceTopay = priceTopay + discountPrice;
            priceSholPay.setText(String.valueOf(priceTopay));


        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textName, textSellPrice, textDiscount, textAmount;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textName = itemView.findViewById(R.id.name_order);
                textSellPrice = itemView.findViewById(R.id.totalPrice_order);
                textDiscount = itemView.findViewById(R.id.discount_order);
                textAmount = itemView.findViewById(R.id.amount_order);


            }


        }

    }


    private void bluetoothSetup() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported on this device", Toast.LENGTH_SHORT).show();
            return;
        }

        enableBluetoothLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        discoverDevices();
                    } else {
                        Toast.makeText(OrderPage.this, "Bluetooth is required to print", Toast.LENGTH_SHORT).show();
                    }
                });

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBluetoothLauncher.launch(enableBluetoothIntent);
        } else {
            discoverDevices();
        }
    }


    private void discoverDevices() {
        // Discover Bluetooth devices and select the printer
        // Set the selected printer device to the printerDevice variable
        // Call establishBluetoothConnection() to establish the Bluetooth connection
    }

    private void establishBluetoothConnection() {
        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            printerSocket = printerDevice.createRfcommSocketToServiceRecord(UUID_SERIAL_PORT_PROFILE);
            printerSocket.connect();
            outputStream = printerSocket.getOutputStream();

            // Send print commands using the outputStream
            String message = "Hello, World!";
            outputStream.write(message.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeBluetoothConnection() {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (printerSocket != null) {
                printerSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeBluetoothConnection();
    }
}

