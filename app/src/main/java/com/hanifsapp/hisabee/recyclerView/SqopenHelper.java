package com.hanifsapp.hisabee.recyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SqopenHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "customer.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "customers",COLUMN_ID = "_id",COLUMN_NAME = "name",COLUMN_ADDRESS = "address",
            COLUMN_PHONE_NUMBER = "phone_number";


    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_ADDRESS + " TEXT, " +
            COLUMN_PHONE_NUMBER + " TEXT" +
            ")";

    public SqopenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }




    public void addtoDatabase(String name, String address, String phoneNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_ADDRESS , address);
        contentValues.put(COLUMN_PHONE_NUMBER, phoneNumber);


        db.insert(TABLE_NAME, null, contentValues);

    }





    public ArrayList<String> getDataList(){
        ArrayList<String> customerInfo = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        while (cursor.moveToNext()){
            String dataset ="\n"+ "নামঃ   " + cursor.getString(1)+"\n" + "ঠিকানাঃ   " +cursor.getString(2)+"\n"+
                    "নাম্বারঃ " + cursor.getString(3);
            customerInfo.add(dataset);
        }
        cursor.close();


        return customerInfo;
    }





    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,COLUMN_ID + "=" + id, null);
    }

}

