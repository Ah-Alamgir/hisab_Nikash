package com.hanifsapp.hisabee.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.autoload;
import com.hanifsapp.hisabee.localDb.localStore;
import com.hanifsapp.hisabee.printEpos;

import java.util.Map;

public class invoice extends AppCompatActivity {
    private TextView header, footer, name, price, amount, total;
    private Button print;
    public final int PERMISSION_BLUETOOTH = 1,PERMISSION_BLUETOOTH_ADMIN = 2,PERMISSION_BLUETOOTH_CONNECT = 3,PERMISSION_BLUETOOTH_SCAN = 4;
    LinearLayout layout_tobePrint;
    private boolean printed;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice);
        printed = false;

        header = findViewById(R.id.textView_Header);
        footer = findViewById(R.id.textView_footer);
        name = findViewById(R.id.textView_name);
        price = findViewById(R.id.textView_price);
        amount = findViewById(R.id.textView_amount);
        total = findViewById(R.id.textView_total);
        print = findViewById(R.id.buttonPrint);
        layout_tobePrint = (LinearLayout) findViewById(R.id.layout_print);
        getPermissions();
        readyText();



        OnBackPressedCallback callback = new OnBackPressedCallback(true){

            @Override
            public void handleOnBackPressed() {
                if(printed){
                    autoload.cardItem_list.clear();
                    autoload.cardItem.clear();
                    Intent intent = new Intent(invoice.this, Sell.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    setEnabled(false);

                }
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();

        print.setOnClickListener(view -> {
            try {
                printEpos.generatePdf(layout_tobePrint, this);
            } catch (EscPosEncodingException | EscPosConnectionException | EscPosParserException |
                     EscPosBarcodeException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            printed = true;
        });
    }




    public void getPermissions(){
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
    int totdisc=0;
    int totvat=0;
    int totalPrices=0;



    public void readyText(){
        Log.d("entry", String.valueOf(autoload.cardItem));

        text =  localStore.settings.get(2)+ ("\n")+
                localStore.settings.get(3)
                + "\n" + localStore.settings.get(4)+
                "\n----------------------";


        StringBuilder dorString = new StringBuilder(), damString = new StringBuilder(), amountString = new StringBuilder(), nameString = new StringBuilder();

//        doing something like recycler view for printing


        nameString.append(" নাম \n");
        dorString.append("দর  \n");
        amountString.append("পিছ \n");
        damString.append("মোট \n");
        for(Map<String, Object> entry: autoload.cardItem){
            totalPrices = totalPrices + Integer.parseInt(String.valueOf(entry.get("Order"))) * Integer.parseInt(String.valueOf(entry.get("sellPrice")));

            nameString.append(entry.get("name")).append("\n");
            dorString.append(entry.get("sellPrice")).append("\n");
            damString.append(Integer.parseInt(String.valueOf(entry.get("Order"))) * Integer.parseInt(String.valueOf(entry.get("sellPrice")))).append("\n");
            amountString.append(entry.get("Order")).append("\n");

            totdisc = totdisc + (Integer.parseInt(String.valueOf(entry.get("Discount"))) * Integer.parseInt(String.valueOf(entry.get("Order"))));
            totvat = totvat + (Integer.parseInt(String.valueOf(entry.get("vat"))) * Integer.parseInt(String.valueOf(entry.get("Order"))));


        }

        String pricedetail =

                "সর্বমোটঃ  "+ totalPrices +"\n" +
                        "ডিস্কাউন্টঃ  "+ totdisc +"\n" +
                        "ভ্যাটঃ "+totvat+"\n" +
                        "-----------------------\n"+
                        "মোট প্রদেয়ঃ "+ (totalPrices - totdisc - totvat) ;


        header.setText(text);
        name.setText(nameString);
        price.setText(dorString);
        amount.setText(amountString);
        total.setText(damString);
        footer.setText(pricedetail);


    }

}
