package com.example.testdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {

    final String dbName;

    public DB(@Nullable Context context) {
        super(context, "dbSV.sqlite", null, 1);
        dbName = "dbSV.sqlite";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    public void CreateTable() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("create table if not exists student (id INTEGER primary key AUTOINCREMENT, name nvarchar(50))" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void InsertStudent(Student student){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name",student.getName());

        sqLiteDatabase.insert("student",null, contentValues);
    }

    public void UpdateStudent(Student student) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",student.getName());

        sqLiteDatabase.update("student",contentValues,"id = " + student.getId(),null );
    }

    public void DeleteStudent(int studenid) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("student","id = " + studenid,null);
    }

    public Cursor getCursor(String tableName) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tableName,null);
        return cursor;
    }

    public ArrayList<Student> students() {
        Cursor cursor = getCursor("student");

        ArrayList<Student> students = new ArrayList<>();

        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);

                Student student = new Student(id,name);
                students.add(student);
                cursor.moveToNext();
            }
        }

        return  students;
    }
}
