package com.hanifsapp.hisabee;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;

import java.util.Map;

public class printOrder extends AppCompatActivity {

    TextView priceSholPay, orderInfos;
    public int priceTopay = 0;
    public int totalPrice = 0;
    public int vatPrice= 0;

    Button startPrint;
    public String customerName;

    public final int PERMISSION_BLUETOOTH = 1;
    public final int PERMISSION_BLUETOOTH_ADMIN = 2;
    public final int PERMISSION_BLUETOOTH_CONNECT = 3;
    public final int PERMISSION_BLUETOOTH_SCAN = 4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        setTitle("বিক্রয় বিবরণী");


        startPrint = findViewById(R.id.startPrinting);
        priceSholPay = findViewById(R.id.priceShouldPay);
        orderInfos = findViewById(R.id.orderInfo);
        getPermissions();


        try {
            readyText();
        } catch (EscPosConnectionException | EscPosParserException | EscPosBarcodeException |
                 EscPosEncodingException e) {
            throw new RuntimeException(e);
        }









        startPrint.setOnClickListener(view -> {

            autoload.getDataToUpdate("todaySell", priceTopay, customerName);
            autoload.getStockToUpdat(autoload.cardItem);
            try {
                startPrint();
            } catch (EscPosEncodingException | EscPosConnectionException | EscPosParserException |
                     EscPosBarcodeException e) {
                throw new RuntimeException(e);
            }
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


    String text = "";
    String customerInfo = "";
    public void readyText() throws EscPosConnectionException, EscPosEncodingException, EscPosBarcodeException, EscPosParserException {
        text =
        "--------------------------------------------------------"+ "\n" +autoload.dates + "\n"+ "গ্রাহকঃ "+ customerInfo + "\n" +
        "--------------------------------------------------------"+ "\n\n\n\n\n\n\n"+
                "সর্বমোটঃ "+ totalPrice + "\n" +
                "(-)ডিসকাঊন্টঃ "+ discountPrice +"\n"+
                "ভ্যাট পরিমানঃ " + vatPrice + "\n"+
                "মোট প্রদেয়ঃ" +  priceTopay;




        text =  "[L]\n" +
                "[C]<u><font size='big'>"+homePage.sharedPreferences.getString("name", "প্রতিষ্ঠানের  নাম ") + "\n" +
                homePage.sharedPreferences.getString("address", "প্রতিষ্ঠানের ঠিকানা")
               + "\n" + homePage.sharedPreferences.getString("phoneNumber", "ফোন নাম্বার")+"\n" +"</font></u>\n" +
                "[L]\n" +
                "[C]================================\n" +
                "[L]\n" +
                "[L]<b>BEAUTIFUL SHIRT</b>[R]9.99e\n" +
                "[L]  + Size : S\n" +
                "[L]\n" +
                "[L]<b>AWESOME HAT</b>[R]24.99e\n" +
                "[L]  + Size : 57/58\n" +
                "[L]\n" +
                "[C]--------------------------------\n" +
                "[R]TOTAL PRICE :[R]34.98e\n" +
                "[R]TAX :[R]4.23e\n" +
                "[L]\n" +
                "[C]================================\n" +
                "[L]\n" +
                "[L]<font size='tall'>Customer :</font>\n" +
                "[L]Raymond DUPONT\n" +
                "[L]5 rue des girafes\n" +
                "[L]31547 PERPETES\n" +
                "[L]Tel : +33801201456\n" +
                "[L]\n" +
                "[C]<barcode type='ean13' height='10'>831254784551</barcode>\n" +
                "[C]<qrcode size='20'>https://dantsu.com/</qrcode>";
        orderInfos.setText(text);

//        EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32);
//        printer
//                .printFormattedText(
//                        text
//                );
    }

















    String printtext ="";

    public void startPrint() throws EscPosEncodingException, EscPosBarcodeException, EscPosParserException, EscPosConnectionException {
        int totalDiscount = 0;
        int totalVat = 0;
        int priceAfterCutting = 0;
        printtext = "";
        printtext = "[L]\n" +
                "[C]<u><font size='big'>"+homePage.sharedPreferences.getString("name", "প্রতিষ্ঠানের  নাম ") + "\n" +
                homePage.sharedPreferences.getString("address", "প্রতিষ্ঠানের ঠিকানা")
               + "\n" + homePage.sharedPreferences.getString("phoneNumber", "ফোন নাম্বার")+"\n" +"</font></u>\n" +
                "[L]\n" +
                "[C]================================\n" +
                "[L]\n" +
                "[L]<b> নাম <b>"+
                "[C]<b> দর <b>"+
                "[R]<b> মোট দাম <b>" + "\n";


//        doing something like recycler view for printing


                for(Map<String, Object> entry: autoload.cardItem){
                    int totalPrices = 0;
                    totalPrices = Integer.valueOf(entry.get("Order").toString()) * Integer.valueOf(entry.get("price").toString());
                    printtext = printtext + "[L]<b>"+entry.get("name")+"</b>" +"[C]"+ entry.get("sellPrice") + "\n"+
                            "[R]"+ totalPrices+
                    "[L] "+entry.get("Order")+" পিছ"+"\n";
                    totalDiscount = totalDiscount + (Integer.valueOf(entry.get("Discount").toString()) * Integer.valueOf(entry.get("Order").toString()));
                    totalVat = totalVat + (Integer.valueOf(entry.get("vat").toString()) * Integer.valueOf(entry.get("Order").toString()));
                }

                printtext = printtext +
                "[L]\n" +
                "[C]--------------------------------\n"+

                "[R] সর্বমোটঃ [R]"+ String.valueOf(priceTopay) +"\n" +
                "[R]ডিস্কাউন্টঃ  [R]"+ String.valueOf(totalDiscount)+"\n" +
                "[R]ভ্যাটঃ [R]"+totalVat+"\n" +
                "[R]মোট প্রদেয়ঃ [R]"+ String.valueOf(priceTopay-totalDiscount-totalVat) + "\n" +
                "[L]\n" +
                "[C]================================\n" +
                "[L]\n" +
                "[L]<font size='tall'>গ্রাহকঃ </font>\n" +
                "[L]Raymond DUPONT\n" +
                "[L]5 rue des girafes\n" +
                "[L]31547 PERPETES\n" +
                "[L]Tel : +33801201456\n" +
                "[L]\n" ;

        EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32);
        printer
                .printFormattedText(
                        printtext
                );

    }

}

