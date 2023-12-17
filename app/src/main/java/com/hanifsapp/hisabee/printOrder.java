package com.hanifsapp.hisabee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.hanifsapp.hisabee.recyclerView.SqopenHelper;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

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


        ConstraintLayout layout_tobePrint = (ConstraintLayout) findViewById(R.id.printLayout_orderpage);



        readyText();

        startPrint.setOnClickListener(view -> {
            try {
                printEpos.generatePdf(layout_tobePrint, this);
            } catch (EscPosEncodingException | EscPosConnectionException | EscPosParserException |
                     EscPosBarcodeException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        setDrawerLayout();


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








String selectedItemText="";
    private void setDrawerLayout() {

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.customerList);


        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(this,R.layout.recycler_list_extview, arrayListCustomerInfos);
        listView.setAdapter(userAdapter);
        listView.setDivider(this.getDrawable(R.drawable.divider_white));
        listView.setDividerHeight(1);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedItemText = "<b>গ্রাহকঃ </b>" + parent.getItemAtPosition(position).toString().split("id:")[0];
            customerInfo = selectedItemText;
            customerdetails.setText(HtmlCompat.fromHtml(selectedItemText.replace("\n", "<br>"), HtmlCompat.FROM_HTML_MODE_LEGACY));
            drawerLayout.closeDrawer(GravityCompat.START, true);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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

        text =homePage.sharedPreferences.getString("name", "প্রতিষ্ঠানের  নাম ")+ ("\n")+
                homePage.sharedPreferences.getString("address", "প্রতিষ্ঠানের ঠিকানা")
                + "\n" + homePage.sharedPreferences.getString("phoneNumber", "ফোন নাম্বার")+
                "\n============================\n\n";



//        doing something like recycler view for printing



        StringBuilder dor = new StringBuilder();
        StringBuilder amount = new StringBuilder();
        StringBuilder dam = new StringBuilder();
        name.append(" নাম \n\n");
        dor.append("দর  \n\n");
        amount.append("পিছ \n\n");
        dam.append("মোট \n\n");
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
                "-------------------------\n"+
                "মোট প্রদেয়ঃ "+ (totalPrices - totdisc - totvat) + "\n";


        String customer =
                "গ্রাহকঃ \n" +
                "5 rue des girafes \n" +
                "Tel : +33801201456 \n";

        businessDetails.setText(text);
        pricedetails.setText(pricedetail);
        nameTextview.setText(name);
        dorTextview.setText(dor);
        amountTextview.setText(amount);
        totalTextview.setText(dam);
//        customerdetails.setText(customer);
    }


















    String customerInfo= "";

}

