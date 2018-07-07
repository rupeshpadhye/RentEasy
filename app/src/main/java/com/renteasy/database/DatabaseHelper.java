package com.renteasy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RUPESH on 8/14/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "RentEasy.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CreateAddressTable = "CREATE TABLE " + DatabaseContract.ADDRESS_TABLE + " ("
                + DatabaseContract.address_table._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DatabaseContract.address_table.LINE1 + " TEXT NOT NULL,"
                + DatabaseContract.address_table.LINE2 + " TEXT ,"
                + DatabaseContract.address_table.LINE3 + " TEXT ,"
                + DatabaseContract.address_table.LINE4 + " TEXT NOT NULL ,"
                + DatabaseContract.address_table.PRIMARY_ADDRESS_FLAG + " TEXT DEFAULT 'N'"
                + " );"
                ;

        db.execSQL(CreateAddressTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ADDRESS_TABLE);
    }
}
