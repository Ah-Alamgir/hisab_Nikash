package com.hanifsapp.hisabee.firebase_Db;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;

public class ChekNet {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        boolean isDataConnectionAvailable = activeNetworkInfo != null && activeNetworkInfo.isConnected() &&
                (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE ||
                        activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI);

        if(!isDataConnectionAvailable) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Network Unavailable")
                    .setMessage("Please check your internet connection.")
                    .setPositiveButton("OK", null)
                    .show();
        }else {
            return true;
        }
        return false;
    }

}
