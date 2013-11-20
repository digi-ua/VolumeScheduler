package com.example.volumescheduler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
      // конструктор суперкласса
      super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      //Log.d(LOG_TAG, "--- onCreate database ---");
      // создаем таблицу с полями
      db.execSQL("create table mytable ("
          + "id integer primary key autoincrement," 
          + "name text,"
          + "email text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
  }
