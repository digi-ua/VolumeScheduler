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
          + "days text,"				   //0123456 string of days(ex: 015 - sun,mon,tri)
          + "state integer,"               //0 - start rule 1 - end rule
          + "volume integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
  }
