package com.example.chuongdkph26546_mob201_asm.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "chuongdk";
    final static  int DB_VERSION = 11;

    public MyDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_create_tb = "CREATE TABLE tb_user ( username TEXT PRIMARY KEY NOT NULL, password TEXT NOT NULL,music TEXT NOT NULL,file BLOD NOT NULL)";
        db.execSQL(sql_create_tb);

        String sql_insert = "INSERT INTO tb_user(username,password,music,file) VALUES ('hh','hh','','')";
        db.execSQL(sql_insert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
