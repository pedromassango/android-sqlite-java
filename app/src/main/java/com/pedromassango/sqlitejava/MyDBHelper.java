package com.pedromassango.sqlitejava;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pedro Massango on 4/17/18.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "sqlite_sampple";
    private static final int DB_VERSION = 2;

    public MyDBHelper(Context ctx){
        super(ctx,DB_NAME, null, DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ ProductEntry.PRODUCTS_TABLE_NAME +"(" +
                ProductEntry.ID_COLUMN + " integer primary key autoincrement," +
                ProductEntry.NAME_COLUMN + " text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If upgrade is needed, just drop table
        db.execSQL("drop table "+ ProductEntry.PRODUCTS_TABLE_NAME+";");
        onCreate(db);

    }
}
