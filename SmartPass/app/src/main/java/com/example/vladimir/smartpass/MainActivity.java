package com.example.vladimir.smartpass;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    private ListView accountList;
    private Button addAccount;
    private AccountDB accountDB;
    private Cursor cursor;
    private SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findUIComponents();
        initDB();
        loadAccountsList();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getSupportLoaderManager().getLoader(0).forceLoad();
    }

    private void initDB() {
        accountDB = new AccountDB(this);
        accountDB.openToRead();
    }


    public void loadAccountsList() {

        String[] from = new String[]{accountDB.SITE_NAME};
        int[] to = new int[] {R.id.itemText};
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
        accountList.setAdapter(cursorAdapter);

        accountList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
    }

    public void findUIComponents(){
        accountList = findViewById(R.id.accountList);
        addAccount = findViewById(R.id.addAccount);
    }

    public void addButton(View view){
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void onDestroy(){
        super.onDestroy();
        accountDB.close();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyCursorLoader(this, accountDB);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }


    static class MyCursorLoader extends CursorLoader {
        AccountDB accountDB;

        public MyCursorLoader(Context context, AccountDB accountDB) {
            super(context);
            this.accountDB = accountDB;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = accountDB.selectSiteNames();

            return cursor;
        }
    }
}
