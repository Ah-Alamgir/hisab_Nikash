package com.hanifsapp.hisabee;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;

import java.util.Map;

public class printOrder extends AppCompatActivity {

    TextView pricedetails, businessDetails;
    public int priceTopay = 0;
    public int totalPrice = 0;
    public int vatPrice= 0;
    private boolean printed= false;

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
        printed = false; //handling for backpress;

        startPrint = findViewById(R.id.startPrinting);
        pricedetails = findViewById(R.id.priceDetails);
        businessDetails = findViewById(R.id.businessDetails);
        getPermissions();


        readyText();

        startPrint.setOnClickListener(view -> {

            autoload.getDataToUpdate("todaySell", priceTopay, customerName);
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



    int totdisc=0;
    int totvat=0;
    int totalPrices=0;


    String text = "";
    String customerInfo = "";
    public void readyText(){
        text ="<b>"+ homePage.sharedPreferences.getString("name", "প্রতিষ্ঠানের  নাম ")+ "</b> <br>"+
                homePage.sharedPreferences.getString("address", "প্রতিষ্ঠানের ঠিকানা")
                + "<br>" + homePage.sharedPreferences.getString("phoneNumber", "ফোন নাম্বার")+
                "<br><b>================================</b><br><br>";



//        doing something like recycler view for printing


        for(Map<String, Object> entry: autoload.cardItem){
            totalPrices = totalPrices + Integer.valueOf(entry.get("Order").toString()) * Integer.valueOf(entry.get("sellPrice").toString());
//            printtext = printtext + "[L]<b>"+entry.get("name")+"</b>" +"[C]"+ entry.get("sellPrice") + "\n"+
//                    "[R]"+ totalPrices+
//                    "[L] "+entry.get("Order")+" পিছ"+"\n";
            totdisc = totdisc + (Integer.valueOf(entry.get("Discount").toString()) * Integer.valueOf(entry.get("Order").toString()));
            totvat = totvat + (Integer.valueOf(entry.get("vat").toString()) * Integer.valueOf(entry.get("Order").toString()));
            Log.d("datam", String.valueOf(totalPrices));

        }
//
        String pricedetail =

                "<b> সর্বমোটঃ "+ String.valueOf(totalPrices) +"</b><br>" +
                "<b>ডিস্কাউন্টঃ  <b>"+ String.valueOf(totdisc)+"<br>" +
                "<b>ভ্যাটঃ <b>"+totvat+"<br>" +
                "-------------------------<br>"+
                "<b>মোট প্রদেয়ঃ <b>"+ String.valueOf(totalPrices-totdisc-totvat) + "<br>" +



                "[L]<font size='tall'>গ্রাহকঃ </font>\n" +
                "[L]Raymond DUPONT\n" +
                "[L]5 rue des girafes\n" +
                "[L]31547 PERPETES\n" +
                "[L]Tel : +33801201456\n" +
                "[L]\n" ;

        Spanned styledTextBusiness = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
        Spanned styledTextPrices = HtmlCompat.fromHtml(pricedetail, HtmlCompat.FROM_HTML_MODE_LEGACY);
        businessDetails.setText(styledTextBusiness);
        pricedetails.setText(styledTextPrices);
    }

















    String printtext ="";

    public void startPrint() throws EscPosEncodingException, EscPosBarcodeException, EscPosParserException, EscPosConnectionException {
        int totalDiscount = 0;
        int totalVat = 0;
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
                    totalPrices = Integer.valueOf(entry.get("Order").toString()) * Integer.valueOf(entry.get("sellPrice").toString());
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

                autoload.getStockToUpdat();

            printed = autoload.getStockToUpdat();

//        EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32);
//        printer
//                .printFormattedText(
//                        printtext
//                );

    }


    @Override
    public void onBackPressed() {
        if(printed){
            startActivity(new Intent(this, homePage.class));
        }else {
            super.onBackPressed();
        }
    }
}

