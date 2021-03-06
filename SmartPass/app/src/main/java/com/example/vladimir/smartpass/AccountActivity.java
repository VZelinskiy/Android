package com.example.vladimir.smartpass;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.widget.CheckBox;
import android.widget.SimpleCursorAdapter;

public class AccountActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    private AccountDB accountDB;
    private Button confirmButton;
    private static TextInputEditText inputSiteName;
    private static TextInputEditText inputSiteAddress;
    private static TextInputEditText inputDescription;
    private static TextInputEditText inputLogin;
    private static TextInputEditText inputPass;
    private static CheckBox isEncryptPass;
    private static SimpleCursorAdapter cursorAdapter;
    private static long accountId;
    private static Cursor cursor;
    private Encryptor encryptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        FindUIComponents();
        InitDB();
        encryptor = new Encryptor();

        getSupportLoaderManager().initLoader(0, null, this);
        initAccount();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (accountId != 0){
            setInputTextFields(cursor);
        }

    }

    private void initAccount() {
        Intent intent = getIntent();
        accountId = intent.getLongExtra("id", 0);

    }

    private void setInputTextFields(Cursor cursor) {
        if (cursor.moveToFirst()) {
            inputSiteName.setText(cursor.getString(cursor.getColumnIndex(accountDB.SITE_NAME)));
            inputSiteAddress.setText(cursor.getString(cursor.getColumnIndex(accountDB.SITE_ADDRESS)));
            inputDescription.setText(cursor.getString(cursor.getColumnIndex(accountDB.DESCRIPTION)));
            inputLogin.setText(cursor.getString(cursor.getColumnIndex(accountDB.LOGIN)));
            String passStr = cursor.getString(cursor.getColumnIndex(accountDB.PASS));

            if (isEncryptPass.isChecked()){
                inputPass.setText(encryptor.decrypt(passStr));
            }
            else {
                inputPass.setText(passStr);
            }
        }
    }

    private void InitDB() {
        accountDB = new AccountDB(this);
        accountDB.openToWrite();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accountDB.close();
    }

    private void FindUIComponents() {
        confirmButton = findViewById(R.id.confirmButton);
        inputSiteName = findViewById(R.id.inputSiteName);
        inputSiteAddress = findViewById(R.id.inputSiteAddress);
        inputDescription = findViewById(R.id.inputDescription);
        inputLogin = findViewById(R.id.inputLogin);
        inputPass = findViewById(R.id.inputPass);
        isEncryptPass = findViewById(R.id.isEncryptPass);
    }

    public boolean isSiteNameValid(String siteName){
        if (siteName.isEmpty()){
            inputSiteName.setError("Поле не может быть пустым");
            return false;
        }
        else {
            inputSiteName.setError(null);
            return true;
        }
    }

    public void addButton(View view) {
        String siteAddress = inputSiteAddress.getText().toString().trim();
        String siteName = inputSiteName.getText().toString().trim();
        String desc = inputDescription.getText().toString().trim();
        String login = inputLogin.getText().toString().trim();
        String pass = inputPass.getText().toString().trim();

        if (isSiteNameValid(siteName) == false){
            return;
        }

        if (isEncryptPass.isChecked()){
            String newpass = encryptor.encrypt(pass);
            pass = newpass;
        }

        if (accountId == 0){
            accountDB.addRec(siteName, siteAddress, desc, login, pass);
        }
        else {
            accountDB.updateRec(accountId, siteName, siteAddress, desc, login, pass);
        }

        finish();
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyCursorLoader(this, accountDB);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (cursorAdapter != null) {
            cursorAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    public void deleteButton(View view) {
        if (accountId == 0){
            return;
        }

        accountDB.delRec(accountId);

        finish();
    }

    static class MyCursorLoader extends CursorLoader {
        AccountDB accountDB;

        public MyCursorLoader(Context context, AccountDB accountDB) {
            super(context);
            this.accountDB = accountDB;
        }

        @Override
        public Cursor loadInBackground() {
            if (accountId == 0){
                return null;
            }
            else {
                cursor = accountDB.selectAccountById(accountId);
                return cursor;
            }

        }
    }
}
