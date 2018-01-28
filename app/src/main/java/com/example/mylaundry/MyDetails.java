package com.example.mylaundry;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;


/**
 * Created by shilk on 11/28/2017.
 */

public class MyDetails extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydetails);
        Bundle bundle = getIntent().getExtras();
        Integer userId = bundle.getInt("userId");

        SQLiteDatabase db =openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT FIRST_NAME, LAST_NAME, EMAIL_ID, PHONE_NO FROM USER WHERE USER_ID = " + userId  ,null);
        c.moveToFirst();
        String FIRST_NAME = c.getString(0);
        String LAST_NAME = c.getString(1);
        String EMAIL_ID = c.getString(2);
        String PHONE_NO = c.getString(3);
        c.close();


        setContentView(R.layout.mydetails);
        TextView textView = (TextView) findViewById(R.id.firstNameView);
        textView.setText(FIRST_NAME + " " + LAST_NAME);

        textView = (TextView) findViewById(R.id.lastNameView);
        textView.setText(EMAIL_ID);
    }
}
