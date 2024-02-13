package com.hanifsapp.hisabee.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.hanifsapp.hisabee.Autoload;
import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.databinding.InvoiceBinding;
import com.hanifsapp.hisabee.firebase_Db.Constant;
import com.hanifsapp.hisabee.firebase_Db.GetproductList;
import com.hanifsapp.hisabee.firebase_Db.localStore;
import com.hanifsapp.hisabee.model.ProductList;
import com.hanifsapp.hisabee.utility.printEpos;

public class invoiceActivity extends AppCompatActivity {
    public final int PERMISSION_BLUETOOTH = 1, PERMISSION_BLUETOOTH_ADMIN = 2, PERMISSION_BLUETOOTH_CONNECT = 3, PERMISSION_BLUETOOTH_SCAN = 4;
    LinearLayout layout_tobePrint;
    private boolean printed;

    InvoiceBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InvoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        printed = false;


        layout_tobePrint = (LinearLayout) findViewById(R.id.layout_print);
        getPermissions();
        readyText();


    }


    @Override
    protected void onStart() {
        super.onStart();

        binding.buttonPrint.setOnClickListener(view -> {
            try {
                printEpos.generatePdf(layout_tobePrint, this);
            } catch (EscPosEncodingException | EscPosConnectionException | EscPosParserException |
                     EscPosBarcodeException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            printed = true;

            String date = Autoload.getCurrentDate();
            Constant.todaySellHistory.child(date).setValue(totalPrices);
            Constant.todaySell.child(date.substring(0, 9)).get().addOnCompleteListener(task -> {
                Integer totalSold;
                if (task.isSuccessful()) {
                    totalSold = task.getResult().getValue(Integer.class) + totalPrices;
                } else {
                    totalSold = totalPrices;
                }

                Constant.todaySell.child(date.substring(0, 9)).setValue(totalSold);
            });

        });
    }


    public void getPermissions() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.BLUETOOTH}, PERMISSION_BLUETOOTH);
        } else if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.BLUETOOTH_ADMIN}, PERMISSION_BLUETOOTH_ADMIN);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, PERMISSION_BLUETOOTH_CONNECT);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.BLUETOOTH_SCAN}, PERMISSION_BLUETOOTH_SCAN);
        }
    }


    String text;
    int totdisc = 0;
    int totvat = 0;
    int totalPrices = 0;


    public void readyText() {

        try {
            text = localStore.settings.get(2) + ("\n")
                    + localStore.settings.get(3) + "\n" +
                    localStore.settings.get(4) +
                    "\n----------------------";

        } catch (Exception e) {
            text = "Your Company Name" + ("\n")
                    + "Company Address" + "\n" +
                    "Phone Number" +
                    "\n----------------------";

        }


        StringBuilder dorString = new StringBuilder(), damString = new StringBuilder(), amountString = new StringBuilder(), nameString = new StringBuilder();

//        doing something like recycler view for printing


        nameString.append(" নাম \n");
        dorString.append("দর  \n");
        amountString.append("পিছ \n");
        damString.append("মোট \n");
        for (ProductList entry : GetproductList.card_list) {
            totalPrices = totalPrices + entry.getOrder() * entry.getSellPrice();

            nameString.append(entry.getName()).append("\n");
            dorString.append(entry.getSellPrice()).append("\n");
            damString.append(entry.getOrder() * entry.getSellPrice()).append("\n");
            amountString.append(entry.getOrder()).append("\n");

            totdisc = totdisc + entry.getDiscount() * entry.getOrder();
            totvat = totvat + entry.getVat() * entry.getOrder();


        }

        String pricedetail =

                "সর্বমোটঃ  " + totalPrices + "\n" +
                        "ডিস্কাউন্টঃ  " + totdisc + "\n" +
                        "ভ্যাটঃ " + totvat + "\n" + "-----------------------\n" +
                        "মোট প্রদেয়ঃ " + (totalPrices - totdisc - totvat);


        binding.textViewHeader.setText(text);
        binding.textViewName.setText(nameString);
        binding.textViewPrice.setText(dorString);
        binding.textViewAmount.setText(amountString);
        binding.textViewTotal.setText(damString);
        binding.textViewFooter.setText(pricedetail);

    }

}
