package com.example.vladimir.smartpass;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
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
        loadAccountsList();

    }

    public void loadAccountsList() {
        accountDB = new AccountDB(this);
        cursor = accountDB.selectSiteNames();
        startManagingCursor(cursor);
        String[] from = new String[]{accountDB.SITE_NAME};
        int[] to = new int[] {R.id.accountList};
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.account, cursor, from, to);
        accountList.setAdapter(cursorAdapter);
    }

    public void findUIComponents(){
        accountList = findViewById(R.id.accountList);
        addAccount = findViewById(R.id.addAccount);
    }

    public void addButton(View view){
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        startActivity(intent);
    }
}
