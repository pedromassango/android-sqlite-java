package com.pedromassango.sqlitejava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Massango on 4/17/18.
 */

public class MyDB {

    private SQLiteDatabase db;

    public MyDB(Context context){
        MyDBHelper helper = new MyDBHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert(String product){

        ContentValues values = new ContentValues();
        values.put(ProductEntry.NAME_COLUMN, product);

        db.insert(ProductEntry.PRODUCTS_TABLE_NAME,null, values);
    }

    public List<String> get(){
        Cursor cursor = db.query(ProductEntry.PRODUCTS_TABLE_NAME, new String[]{ProductEntry.NAME_COLUMN},null,
                null,null,
                null, null);

        List<String> result = new ArrayList<>();

        // check if there is some result
        if(cursor.getCount() > 0){
            // while there is data in cursor...
            while(cursor.moveToNext()){
                String name = cursor.getString(0); // get the value column 0 (names)
                result.add(name); // add the name in list
            }

        }

        cursor.close();
        return result;
    }
}
