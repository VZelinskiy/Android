package com.example.vladimir.smartpass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLTransactionRollbackException;

public class SmartPassDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PassBase";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "myAccounts";
    public static final String SITE_ADDRESS = "siteAddress";
    public static final String SITE_NAME = "siteName";
    public static final String DESCRIPTION = "description";
    public static final String LOGIN = "login";
    public static final String PASS = "pass";

    public static final String DATABASE_CREATE = "create table " + TABLE_NAME
            + " (_id integer primary key autoincrement, "
            + SITE_ADDRESS + " text, " + SITE_NAME + " text not null, "
            + DESCRIPTION + " text, " + LOGIN + " text, "
            + PASS + " text, ";

    public SmartPassDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SmartPassDBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
