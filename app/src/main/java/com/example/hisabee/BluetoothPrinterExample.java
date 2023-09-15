package com.example.hisabee;


import java.io.IOException;
import java.io.OutputStream;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Set;


public class BluetoothPrinterExample extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_printer);

        // Get the Bluetooth address of the connected printer

        button = findViewById(R.id.discoverButton);


        button.setOnClickListener(view -> print());



    }





    public void print(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!arePermissionsGranted()) {
                requestPermissions();
            } else {
                String printerAddress = getConnectedPrinterAddress();
                setTitle(printerAddress.toString());
                Log.d("datam", printerAddress.toString());

                if (printerAddress != null) {
                    // Connect to the printer
                    BluetoothDevice printerDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(printerAddress);
                    BluetoothSocket socket = null;
                    try {
                        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        socket = printerDevice.createRfcommSocketToServiceRecord(printerDevice.getUuids()[0].getUuid());
                        socket.connect();

                        // Print the activity data
                        String[] activityData = getActivityData(); // Replace with your activity data
                        OutputStream outputStream = socket.getOutputStream();
                        for (String data : activityData) {
                            outputStream.write(data.getBytes());
                            outputStream.write("\n".getBytes()); // Add a new line after each data item
                        }
                        outputStream.flush();

                        // Close the connection
                        outputStream.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }







    private String getConnectedPrinterAddress() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

            return "TODO";
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (!pairedDevices.isEmpty()) {
            BluetoothDevice device = pairedDevices.iterator().next();
            Log.d("datam" , device.getName() + "\n" + device.getName());
            return device.getAddress();
        }
        return null;
    }

    private String[] getActivityData() {
        // Replace this method with your logic to retrieve the activity data
        String[] activityData = {
                "মাহি এন্টারপ্রাইজ",
                "মহামায়া,চাঁদপুর",
                "০১৮৭২৪৭২৭৮৭"
        };
        return activityData;
    }


    @RequiresApi(api = Build.VERSION_CODES.S)
    private boolean arePermissionsGranted() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[] {
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.BLUETOOTH_ADMIN,
                android.Manifest.permission.BLUETOOTH_CONNECT
        }, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allPermissionsGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
            } else {
                requestPermissions();
            }
        }
    }













    private void printText(String text) {
        try {
            byte[] data = text.getBytes("UTF-8");
            mOutputStream.write(data);
            mOutputStream.flush();
            Toast.makeText(this, "Text printed successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // Handle write errors
            e.printStackTrace();
            Toast.makeText(this, "Failed to print text", Toast.LENGTH_SHORT).show();
        }
    }


}