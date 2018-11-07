package com.example.vladimir.smartpass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Account {
    private SmartPassDBHelper dbHelper;
    private SQLiteDatabase database;

    public Account(Context context){
        dbHelper = new SmartPassDBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long createRecords(String siteAddress, String siteName, String description, String login, String pass){
        ContentValues values = new ContentValues();
        values.put(dbHelper.SITE_ADDRESS, siteAddress);
        values.put(dbHelper.SITE_NAME, siteName);
        values.put(dbHelper.DESCRIPTION, description);
        values.put(dbHelper.LOGIN, login);
        values.put(dbHelper.PASS, pass);
        return database.insert(dbHelper.TABLE_NAME, null, values);
    }

    public Cursor selectSiteNames(){
        String[] cols = new String[] {dbHelper.SITE_NAME};
        Cursor cursor = database.query(true, dbHelper.TABLE_NAME, cols, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
