package com.example.volumescheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class DBHelper extends SQLiteOpenHelper {	
	private final String T_NAME = "timetable";
	private final String T_ID = "id";
	private final String T_HOUR = "hour";
	private final String T_MIN = "min";
	private final String T_DAY = "day";
	private final String T_STATE = "state";
	private final String T_RULE = "rule";
	private final String T_ENABLE = "enable";
	
	private SQLiteDatabase db;
	
    public DBHelper(Context context) {
      super(context, "volumeDB", null, 1);
      db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL("create table " + T_NAME + "("
          + T_ID + " integer primary key autoincrement," 
          + T_HOUR + " integer,"
          + T_MIN + " integer,"          
          + T_DAY + " integer,"				   //0 - sunday 6 - saturday
          + T_STATE + " integer,"              //0 - start rule id - end rule
          + T_RULE + " integer"
          + T_ENABLE + " integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	
    }
    
    private long Insert(TimeTable tt) {
    	ContentValues cv = new ContentValues();
        cv.put(T_HOUR, tt.hour);
        cv.put(T_MIN, tt.min);
        cv.put(T_DAY, tt.day);
        cv.put(T_STATE, tt.state);
        cv.put(T_RULE, tt.rule);
        cv.put(T_ENABLE, tt.enable);
        long rowID = db.insert(T_NAME, null, cv);
        close();
        return rowID;
    }
    
    private long Update(TimeTable tt) {
    	ContentValues cv = new ContentValues();
        cv.put("hour", tt.hour);
        cv.put("min", tt.min);
        cv.put("day", tt.day);
        cv.put("state", tt.state);
        cv.put("rule", tt.rule);
        cv.put("enable", tt.enable);
        db.update(T_NAME, cv, T_ID + "=" + tt.id, null);
        close();
        return tt.id;
    }
    
    public long Save(TimeTable tt) {
    	if (tt.id == 0) {
    		return Insert(tt);
    	}
    	else {
    		return Update(tt);
    	}
    }
    
    public void Delete(int rowId) {
    	db.delete(T_NAME, T_ID + "=" + rowId, null);
    }
    
    public List<TimeTable> getAll() {
    	List<TimeTable> ttList = new ArrayList<TimeTable>();
	    Cursor c = db.query(T_NAME, null, null, null, null, null, null);
	    if (c.moveToFirst()) {
	      do {
	    	  TimeTable tt = new TimeTable();
	    	  tt.id = c.getInt(c.getColumnIndex(T_ID));
	    	  tt.hour = c.getInt(c.getColumnIndex(T_HOUR));
	    	  tt.min = c.getInt(c.getColumnIndex(T_MIN));
	    	  tt.day = c.getInt(c.getColumnIndex(T_DAY));
	    	  tt.state = c.getInt(c.getColumnIndex(T_STATE));
	    	  tt.rule = c.getInt(c.getColumnIndex(T_RULE));
	    	  tt.enable = c.getInt(c.getColumnIndex(T_ENABLE));
	    	  ttList.add(tt);
	      } while (c.moveToNext());
	    } 
	    c.close();
	    return ttList;
    }
    
    
  }
