package com.hanifsapp.hisabee.firebase_Db;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;


import java.util.ArrayList;



public class localStore {
    static SharedPreferences sp;;
    public  static ArrayList<String> settings = new ArrayList<String>();


    public static boolean putSettings(Context context,String size, String enabled){
        sp = context.getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor setting = sp.edit();
        setting.putString("textSize", size);
        setting.putString("isBold", enabled);
        setting.apply();
        return true;
    }

    public static boolean putAddress(String name, String address, String phoneNumber, Context context){
        sp = context.getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.putString("name", name);
        editor.putString("address", address);
        editor.putString("phoneNumber", phoneNumber);
        editor.apply();


        return true;
    }




    public static void getDatas(Context context){
        sp = context.getSharedPreferences("settings", MODE_PRIVATE);
        settings.add(0,sp.getString("textSize", "12"));
        settings.add(1,sp.getString("isBold", "false"));
        settings.add(2, sp.getString("address", "Business Address"));
        settings.add(3, sp.getString("phoneNumber", "Phone Number"));
        settings.add(4, sp.getString("name", "Business Name"));
    }

}



