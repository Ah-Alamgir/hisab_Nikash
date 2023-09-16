package com.hanifsapp.hisabee;


import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Set;
import java.util.UUID;


public class BluetoothPrinterExample extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private UUID deviceUUID;

    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;

    private ActivityResultLauncher<Intent> enableBluetoothLauncher;
    public String deviceName= "MP-5804";

    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    EditText name, uuid;
    private static final int REQUEST_PERMISSION_BLUETOOTH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_printer);

        Button printButton = findViewById(R.id.discoverButton);
        name =findViewById(R.id.editTextText);
        uuid =findViewById(R.id.edittextuduid);




        printButton.setOnClickListener(v -> {
            if(!name.getText().toString().isEmpty()) {
                deviceName = name.getText().toString();
            } else if (uuid.getText().toString().isEmpty()) {
                deviceUUID = UUID.fromString(uuid.getText().toString());
                Toast.makeText(this, deviceUUID.toString(), Toast.LENGTH_SHORT).show();
            }
            printText("Hello, World!");
        });

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Bluetooth is not supported on this device
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            // Bluetooth is not enabled, prompt the user to enable it
            enableBluetooth();
        } else {
            initializeBluetooth();
        }
    }

    private void enableBluetooth() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.BLUETOOTH}, REQUEST_PERMISSION_BLUETOOTH);
        } else {
            enableBluetoothInternal();
        }
    }

    private void enableBluetoothInternal() {
        enableBluetoothLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Bluetooth is enabled, continue with the app initialization
                        initializeBluetooth();
                    } else {
                        // Bluetooth enable request is denied by the user
                        Toast.makeText(this, "Bluetooth is required to proceed", Toast.LENGTH_SHORT).show();
                    }
                });

        enableBluetoothLauncher.launch(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_BLUETOOTH) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableBluetoothInternal();
            } else {
                // Bluetooth permission request is denied by the user
                Toast.makeText(this, "Bluetooth permission is required to proceed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initializeBluetooth() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device.getName().equals(deviceName)) { // Replace with your printer's name
                bluetoothDevice = device;
                deviceUUID = fetchUuid(device);
                Toast.makeText(this, deviceUUID.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(this, "Printer MP-5804 found", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        if (bluetoothDevice == null) {
            // The printer with the specified name is not found
            Toast.makeText(this, "Printer not found", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(deviceUUID);
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            // Handle connection errors
            e.printStackTrace();
        }
    }

    private void printText(String text) {
        if (outputStream != null) {
            try {
                byte[] data = text.getBytes("UTF-8");
                outputStream.write(data);
                outputStream.flush();
                Toast.makeText(this, "Text printed successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                // Handle write errors
                e.printStackTrace();
                Toast.makeText(this, "Failed to print text", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Output stream is not initialized", Toast.LENGTH_SHORT).show();
        }
    }

    private UUID fetchUuid(BluetoothDevice device) {
        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                return null;
            }
            device.fetchUuidsWithSdp();
            ParcelUuid[] uuids = device.getUuids();
            if (uuids != null && uuids.length > 0) {
                return uuids[0].getUuid();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}