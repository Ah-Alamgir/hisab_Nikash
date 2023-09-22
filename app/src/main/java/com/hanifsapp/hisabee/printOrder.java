package com.hanifsapp.hisabee;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.hanifsapp.hisabee.recyclerView.SqopenHelper;

import java.util.ArrayList;
import java.util.Map;

public class printOrder extends AppCompatActivity {

    TextView pricedetails, businessDetails, customerdetails, nameTextview, amountTextview, dorTextview, totalTextview;
    public int priceTopay = 0;
    private boolean printed = false;
    SqopenHelper sqopenHelper;

    Button startPrint;
    private DrawerLayout drawerLayout;
    private ListView listView;
    private ActionBarDrawerToggle drawerToggle;
    ArrayList<String> arrayListCustomerInfos;

    public final int PERMISSION_BLUETOOTH = 1,PERMISSION_BLUETOOTH_ADMIN = 2,PERMISSION_BLUETOOTH_CONNECT = 3,PERMISSION_BLUETOOTH_SCAN = 4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
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
        customerdetails = findViewById(R.id.customerDetails);
        getPermissions();
        arrayListCustomerInfos = sqopenHelper.getDataList();



        readyText();

        startPrint.setOnClickListener(view -> {
            try {
                startPrint();
            } catch (EscPosEncodingException | EscPosConnectionException | EscPosParserException |
                     EscPosBarcodeException e) {
                throw new RuntimeException(e);
            }
        });
        setDrawerLayout();


    }




    private void setDrawerLayout() {

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.customerList);


        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(this,R.layout.recycler_list_extview, arrayListCustomerInfos);
        listView.setAdapter(userAdapter);
        listView.setDivider(this.getDrawable(R.drawable.divider_white));
        listView.setDividerHeight(1);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItemText = "<b>গ্রাহকঃ </b>" + parent.getItemAtPosition(position).toString().replace("\n", "<br>");
            customerdetails.setText(HtmlCompat.fromHtml(selectedItemText, HtmlCompat.FROM_HTML_MODE_LEGACY));
            drawerLayout.closeDrawer(GravityCompat.START, true);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        text ="<b>"+ homePage.sharedPreferences.getString("name", "প্রতিষ্ঠানের  নাম ")+ "</b> <br>"+
                homePage.sharedPreferences.getString("address", "প্রতিষ্ঠানের ঠিকানা")
                + "<br>" + homePage.sharedPreferences.getString("phoneNumber", "ফোন নাম্বার")+
                "<br><b>============================</b><br><br>";



//        doing something like recycler view for printing



        StringBuilder dor = new StringBuilder();
        StringBuilder amount = new StringBuilder();
        StringBuilder dam = new StringBuilder();
        name.append("<b> নাম </b><br><br>");
        dor.append("<b> দর  </b><br><br>");
        amount.append("<b> পিছ </b><br><br>");
        dam.append("<b> মোট </b><br><br>");
        for(Map<String, Object> entry: autoload.cardItem){
            totalPrices = totalPrices + Integer.valueOf(entry.get("Order").toString()) * Integer.valueOf(entry.get("sellPrice").toString());

            name.append("<b>").append(entry.get("name").toString()).append("</b><br>");
            dor.append(entry.get("sellPrice")).append("<br>");
            dam.append(Integer.valueOf(entry.get("Order").toString()) * Integer.valueOf(entry.get("sellPrice").toString())).append("<br>");
            amount.append(entry.get("Order").toString()).append(" পিছ").append("<br>");

            totdisc = totdisc + (Integer.valueOf(entry.get("Discount").toString()) * Integer.valueOf(entry.get("Order").toString()));
            totvat = totvat + (Integer.valueOf(entry.get("vat").toString()) * Integer.valueOf(entry.get("Order").toString()));


        }
//
        String pricedetail =

                "<b> সর্বমোটঃ "+ totalPrices +"</b><br>" +
                "<b>ডিস্কাউন্টঃ  <b>"+ totdisc +"<br>" +
                "<b>ভ্যাটঃ <b>"+totvat+"<br>" +
                "-------------------------<br>"+
                "<b>মোট প্রদেয়ঃ <b>"+ (totalPrices - totdisc - totvat) + "<br>";


        String customer =
                "<b>গ্রাহকঃ </b><br>" +
                "Raymond DUPONT <br>" +
                "5 rue des girafes <br>" +
                "Tel : +33801201456 <br>";

        businessDetails.setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY));
        pricedetails.setText(HtmlCompat.fromHtml(pricedetail, HtmlCompat.FROM_HTML_MODE_LEGACY));
        nameTextview.setText(HtmlCompat.fromHtml(String.valueOf(name), HtmlCompat.FROM_HTML_MODE_LEGACY));
        dorTextview.setText(HtmlCompat.fromHtml(String.valueOf(dor), HtmlCompat.FROM_HTML_MODE_LEGACY));
        amountTextview.setText(HtmlCompat.fromHtml(String.valueOf(amount), HtmlCompat.FROM_HTML_MODE_LEGACY));
        totalTextview.setText(HtmlCompat.fromHtml(String.valueOf(dam), HtmlCompat.FROM_HTML_MODE_LEGACY));
        totalTextview.setText(HtmlCompat.fromHtml(String.valueOf(dam), HtmlCompat.FROM_HTML_MODE_LEGACY));
        customerdetails.setText(HtmlCompat.fromHtml(customer, HtmlCompat.FROM_HTML_MODE_LEGACY));
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

                "[R] সর্বমোটঃ [R]"+ priceTopay +"\n" +
                "[R]ডিস্কাউন্টঃ  [R]"+ totalDiscount +"\n" +
                "[R]ভ্যাটঃ [R]"+totalVat+"\n" +
                "[R]মোট প্রদেয়ঃ [R]"+ (priceTopay - totalDiscount - totalVat) + "\n" +
                "[L]\n" +
                "[C]================================\n" +
                "[L]\n" +
                "[L]<font size='tall'>গ্রাহকঃ </font>\n" +
                "[L]Raymond DUPONT\n" +
                "[L]5 rue des girafes\n" +
                "[L]31547 PERPETES\n" +
                "[L]Tel : +33801201456\n" +
                "[L]\n" ;



            printed = autoload.getStockToUpdat();
        autoload.getDataToUpdate("todaySell", priceTopay, String.valueOf(name));

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

