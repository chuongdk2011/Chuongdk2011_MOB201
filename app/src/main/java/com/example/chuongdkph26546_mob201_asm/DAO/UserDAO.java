package com.example.chuongdkph26546_mob201_asm.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chuongdkph26546_mob201_asm.DTO.UserDTO;
import com.example.chuongdkph26546_mob201_asm.DbHelper.MyDbHelper;

public class UserDAO {
    SQLiteDatabase db;
    MyDbHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }



    public  long insert(UserDTO userDTO) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", userDTO.getUsername());
        contentValues.put("password", userDTO.getPassword());
        contentValues.put("music", "");
        contentValues.put("file", "");

        long res = db.insert("tb_user", null, contentValues);
        return res;
    }

    public boolean checkuser(String username){
        Cursor cursor = db.rawQuery("Select * from tb_user where username = ?",new String[]{username});
        if (cursor.getCount()>0){
            return true;
        }else
            return  false;
    }

    public boolean checkuserpass(String username,String password){
        Cursor cursor = db.rawQuery("Select * from tb_user where username = ? and password = ?",new String[]{username,password});
        if (cursor.getCount()>0){
            return true;
        }else
            return  false;
    }

    public  boolean update(UserDTO userDTO){
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", userDTO.getUsername());
        contentValues.put("password", userDTO.getPassword());
        contentValues.put("music",userDTO.getFvtMusic());
        contentValues.put("file",userDTO.getFile_path());


        long res = db.update("tb_user",contentValues,"username=?",new String[]{String.valueOf(userDTO.getUsername())});

        if (res<=0){
            return  false;
        }
        return true;
    }
}
