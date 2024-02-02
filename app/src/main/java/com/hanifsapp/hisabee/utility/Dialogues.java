package com.hanifsapp.hisabee.utility;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.hanifsapp.hisabee.autoload;

public class Dialogues {

    public static  void addProductDialogue(boolean editProduct, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (editProduct){
            builder.setTitle("Product Updated")
                    .setMessage("Your product has been successfully updated.");
        }else {
            builder.setTitle("Product Added")
                    .setMessage("Your product has been successfully added.");
        }
        builder.setPositiveButton("OK", (dialog, which) -> {

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public static void onDeleteClick(String id, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete this item?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, ids) -> {
                    autoload.deleteData(id);
                })
                .setNegativeButton("No", (dialog, ids) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

}
