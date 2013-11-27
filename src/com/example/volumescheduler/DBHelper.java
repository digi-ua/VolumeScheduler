package com.example.volumescheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

class DBHelper extends SQLiteOpenHelper {	
	private final String T_NAME = "ruletable";
	private final String T_ID = "id";
	private final String T_STIME = "stime";
	private final String T_ETIME = "etime";	
	private final String T_DAYS = "days";
	private final String T_RULE = "rule";
	private final String T_STATE = "state";
	private final String T_RUNNING = "running";
	private final String T_ACTIVE = "active";
	
	private SQLiteDatabase db;
	
    public DBHelper(Context context) {
      super(context, "volumeDB.db", null, 3);
      db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL("create table " + T_NAME + "("
          + T_ID + " integer primary key autoincrement," 
          + T_STIME + " integer,"
          + T_ETIME + " integer,"
          + T_DAYS + " text,"
          + T_STATE + " integer,"
          + T_RULE + " integer,"
          + T_RUNNING + " integer,"
          + T_ACTIVE + " integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	db.execSQL("DROP TABLE IF EXISTS " + T_NAME);
        onCreate(db);
    }
    
    private long Insert(RuleModel tt) {    	   	
    	ContentValues cv = new ContentValues();
        cv.put(T_STIME, tt.StartTime);
        cv.put(T_ETIME, tt.EndTime);
        cv.put(T_DAYS, tt.Days);
        cv.put(T_STATE, tt.State);
        cv.put(T_RULE, tt.Rule);
        cv.put(T_RUNNING, tt.IsRunning);
        cv.put(T_ACTIVE, tt.Active);
        long rowID = db.insert(T_NAME, null, cv);
        return rowID;
    }
    
    private long Update(RuleModel tt) {
    	ContentValues cv = new ContentValues();
        cv.put(T_STIME, tt.StartTime);
        cv.put(T_ETIME, tt.EndTime);
        cv.put(T_DAYS, tt.Days);
        cv.put(T_STATE, tt.State);
        cv.put(T_RULE, tt.Rule);
        cv.put(T_RUNNING, tt.IsRunning);
        cv.put(T_ACTIVE, tt.Active);
        db.update(T_NAME, cv, T_ID + "=" + tt.ID, null);
        return tt.ID;
    }
    
    public long Save(RuleModel tt) {
    	if (tt.ID == 0) {
    		return Insert(tt);
    	}
    	else {
    		return Update(tt);
    	}
    }
    
    public void Delete(int rowId) {
    	db.delete(T_NAME, T_ID + "=" + rowId, null);
    }
    
    public List<RuleModel> getAll() {
    	List<RuleModel> ttList = new ArrayList<RuleModel>();
	    Cursor c = db.query(T_NAME, null, null, null, null, null, null);
	    if (c.moveToFirst()) {
	      do {
	    	  RuleModel tt = new RuleModel();	    	  
	    	  tt.ID = c.getInt(c.getColumnIndex(T_ID));
	    	  tt.StartTime = c.getInt(c.getColumnIndex(T_STIME));
	    	  tt.EndTime = c.getInt(c.getColumnIndex(T_ETIME));
	    	  tt.Days = c.getString(c.getColumnIndex(T_DAYS));
	    	  tt.State = c.getInt(c.getColumnIndex(T_STATE));
	    	  tt.Rule = c.getInt(c.getColumnIndex(T_RULE));
	    	  tt.IsRunning = c.getInt(c.getColumnIndex(T_RUNNING));
	    	  tt.Active = c.getInt(c.getColumnIndex(T_ACTIVE));
	    	  ttList.add(tt);
	      } while (c.moveToNext());
	    } 
	    c.close();
	    return ttList;
    }   
  }