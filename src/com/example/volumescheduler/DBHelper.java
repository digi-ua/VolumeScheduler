package com.example.volumescheduler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
      // конструктор суперкласса
      super(context, "volumeDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      //Log.d(LOG_TAG, "--- onCreate database ---");
      // создаем таблицу с полями
      db.execSQL("create table timetable ("
          + "id integer primary key autoincrement," 
          + "hour integer,"
          + "min integer,"          
          + "day integer,"				   //0 - sunday 1 - monday...
          + "state integer,"               //0 - start rule 1 - end rule
          + "volume integer"
          + "enable integer"+ ");");
      
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
  }
