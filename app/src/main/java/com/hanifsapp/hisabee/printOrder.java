package com.hanifsapp.hisabee;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.hanifsapp.hisabee.activity.Sell;
import com.hanifsapp.hisabee.localDb.localStore;
import com.hanifsapp.hisabee.recyclerView.SqopenHelper;

import java.util.ArrayList;
import java.util.Map;


public class printOrder extends AppCompatActivity {

    TextView pricedetails, businessDetails, nameTextview, amountTextview, dorTextview, totalTextview;

    private boolean printed = false;
    SqopenHelper sqopenHelper;

    Button startPrint;

    ArrayList<String> arrayListCustomerInfos;

    public final int PERMISSION_BLUETOOTH = 1,PERMISSION_BLUETOOTH_ADMIN = 2,PERMISSION_BLUETOOTH_CONNECT = 3,PERMISSION_BLUETOOTH_SCAN = 4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        printed = false; //handling for backpress;
        setTitle("");
        sqopenHelper = new SqopenHelper(this);


        startPrint = findViewById(R.id.startPrinting);
        pricedetails = findViewById(R.id.priceDetails);
        businessDetails = findViewById(R.id.businessDetails);
        nameTextview = findViewById(R.id.orderName);
        amountTextview = findViewById(R.id.orderAmount);
        dorTextview = findViewById(R.id.orderDor);
        totalTextview = findViewById(R.id.orderDam);

        getPermissions();
        arrayListCustomerInfos = sqopenHelper.getDataList();


        LinearLayout layout_tobePrint = (LinearLayout) findViewById(R.id.printLayout);



        readyText();

        startPrint.setOnClickListener(view -> {
            try {
                printEpos.generatePdf(layout_tobePrint, this);
            } catch (EscPosEncodingException | EscPosConnectionException | EscPosParserException |
                     EscPosBarcodeException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        OnBackPressedCallback callback = new OnBackPressedCallback(true){

            @Override
            public void handleOnBackPressed() {
                if(printed){
                    autoload.cardItem_list.clear();
                    autoload.cardItem.clear();
                    Intent intent = new Intent(printOrder.this, Sell.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    setEnabled(false);

                }
            }
        };
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







    int totdisc=0;
    int totvat=0;
    int totalPrices=0;
    StringBuilder name = new StringBuilder();


    String text = "";

    public void readyText(){

        text =  localStore.settings.get(2)+ ("\n")+
                localStore.settings.get(3)
                + "\n" + localStore.settings.get(4)+
                "\n----------------------";



//        doing something like recycler view for printing



        StringBuilder dor = new StringBuilder();
        StringBuilder amount = new StringBuilder();
        StringBuilder dam = new StringBuilder();
        name.append(" নাম \n");
        dor.append("দর  \n");
        amount.append("পিছ \n");
        dam.append("মোট \n");
        for(Map<String, Object> entry: autoload.cardItem){
            totalPrices = totalPrices + Integer.parseInt(String.valueOf(entry.get("Order"))) * Integer.parseInt(String.valueOf(entry.get("sellPrice")));

            name.append(entry.get("name")).append("\n");
            dor.append(entry.get("sellPrice")).append("\n");
            dam.append(Integer.parseInt(String.valueOf(entry.get("Order"))) * Integer.parseInt(String.valueOf(entry.get("sellPrice")))).append("\n");
            amount.append(entry.get("Order")).append(" পিছ").append("\n");

            totdisc = totdisc + (Integer.parseInt(String.valueOf(entry.get("Discount"))) * Integer.parseInt(String.valueOf(entry.get("Order"))));
            totvat = totvat + (Integer.parseInt(String.valueOf(entry.get("vat"))) * Integer.parseInt(String.valueOf(entry.get("Order"))));


        }
//
        String pricedetail =

                "সর্বমোটঃ "+ totalPrices +"\n" +
                "ডিস্কাউন্টঃ  "+ totdisc +"\n" +
                "ভ্যাটঃ "+totvat+"\n" +
                "-----------------------\n"+
                "মোট প্রদেয়ঃ "+ (totalPrices - totdisc - totvat) ;


        businessDetails.setText(text);
        pricedetails.setText(pricedetail);
        nameTextview.setText(name);
        dorTextview.setText(dor);
        amountTextview.setText(amount);
        totalTextview.setText(dam);
    }

}

