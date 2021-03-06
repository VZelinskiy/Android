package com.example.vladimir.smartpass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;

public class AccountDB extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "database.sqlite";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "myAccounts";
    public static final String ID = "_id";
    public static final String SITE_ADDRESS = "siteAddress";
    public static final String SITE_NAME = "siteName";
    public static final String DESCRIPTION = "description";
    public static final String LOGIN = "login";
    public static final String PASS = "pass";

    public SQLiteDatabase database;

    public static final String DATABASE_CREATE = "create table " + TABLE_NAME
            + " (" + ID + " integer primary key autoincrement, "
            + SITE_ADDRESS + " text, " + SITE_NAME + " text not null, "
            + DESCRIPTION + " text, " + LOGIN + " text, "
            + PASS + " text)";

    public AccountDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(AccountDB.class.getName(), "Upgrading database from version " + oldVersion + " to "
        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void openToWrite(){
        database = getWritableDatabase();
    }

    public void openToRead(){
        database = getReadableDatabase();
    }

    public void addRec(String siteName, String siteAddress, String description, String login, String pass){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SITE_NAME, siteName);
        contentValues.put(SITE_ADDRESS, siteAddress);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(LOGIN, login);
        contentValues.put(PASS, pass);
        database.insert(TABLE_NAME, null, contentValues);
    }

    public void delRec(long id){
        database.delete(TABLE_NAME, ID + " = " + id, null);
    }

    public Cursor selectSiteNames(){
        String[] cols = new String[] {ID, SITE_NAME};
        Cursor cursor = database.query(true, TABLE_NAME, cols, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String temp = cursor.getString(cursor.getColumnIndex(ID));
            } while (cursor.moveToNext());
        }
        return cursor;
    }

    public Cursor selectAccountById(long id){
        String[] cols = new String[] {ID, SITE_NAME, SITE_ADDRESS, DESCRIPTION, LOGIN, PASS};
        String selection = ID + " = ?";
        String strId = Long.toString(id);
        String[] selectionArgs = new String[] {strId};
        Cursor cursor = database.query(true, TABLE_NAME, cols, selection, selectionArgs, null, null, null, null);

        return cursor;
    }

    public void updateRec(long id, String siteName, String siteAddress, String description, String login, String pass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SITE_NAME, siteName);
        contentValues.put(SITE_ADDRESS, siteAddress);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(LOGIN, login);
        contentValues.put(PASS, pass);
        database.update(TABLE_NAME, contentValues, ID + " = ?", new String[] { Long.toString(id) });
    }
}
