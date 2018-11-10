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
import android.widget.SimpleCursorAdapter;

public class AccountActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    private AccountDB accountDB;
    private Button confirmButton;
    private TextInputEditText inputSiteName;
    private TextInputEditText inputSiteAddress;
    private TextInputEditText inputDescription;
    private TextInputEditText inputLogin;
    private TextInputEditText inputPass;
    private SimpleCursorAdapter cursorAdapter;
    private static long accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        FindUIComponents();
        InitDB();

        initAccountID();
        getSupportLoaderManager().initLoader(0, null, this);

    }

    private void initAccountID() {
        Intent intent = getIntent();
        accountId = intent.getLongExtra("id", 0);
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
    }

    public void addButton(View view) {
        String siteAddress = inputSiteAddress.getText().toString();
        String siteName = inputSiteName.getText().toString();
        String desc = inputDescription.getText().toString();
        String login = inputLogin.getText().toString();
        String pass = inputPass.getText().toString();

        accountDB.addRec(siteName, siteAddress, desc, login, pass);
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
                Cursor cursor = accountDB.selectAccountById(accountId);

                return cursor;
            }

        }
    }
}
