package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static  String DATABASE_NAME = "student_db";
    private static final int DATABASE_VERSION = 1 ;
    private static final String TABLE_STUDENTS="students";
    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String KEY_ADDRESS="address";
    private static final String CREATE_TABLE_STUDENTS=
            "CREATE TABLE "+TABLE_STUDENTS
                    +"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +KEY_NAME+" TEXT,"
                    +KEY_ADDRESS+" TEXT)";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '"+TABLE_STUDENTS+"'");

    }
    public long addStudent(String name, String address){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_ADDRESS, address);
        long insert = db.insert(TABLE_STUDENTS,null,values);
        return insert;
    }

    public ArrayList<Map<String, String>> getAllStudents(){
        ArrayList<Map<String, String>> arrayList =  new ArrayList<>();
        String name = "";
        String address = "";
        int id  = 0;
        String selQuery  = "SELECT * FROM " + TABLE_STUDENTS;
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor c = db.rawQuery(selQuery,null);
        if (c.moveToFirst()){
            do {
                id = c.getInt(c.getColumnIndexOrThrow(KEY_ID));
                name = c.getString(c.getColumnIndexOrThrow(KEY_NAME));
                address = c.getString(c.getColumnIndexOrThrow(KEY_ADDRESS));
                Map<String,String> item = new HashMap<>();
                item.put(KEY_ID,id + "");
                item.put(KEY_NAME,name);
                item.put(KEY_ADDRESS,address);
                arrayList.add(item);
            }while (c.moveToNext());
        }
        return arrayList;
    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_STUDENTS+
                " WHERE " + KEY_ID+"='"+id+"'";
        db.execSQL(deleteQuery);
        db.close();
    }

}
