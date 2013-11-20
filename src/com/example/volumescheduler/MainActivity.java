package com.example.volumescheduler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends Activity {
    
	DBHelper dbHelper;
	final String LOG_TAG = "myLogs";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        
        
        dbHelper = new DBHelper(this);        
    }
    
    public void onClick_add(View v) {
    	ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        cv.put("hour", 16);
        cv.put("min", 34);
        cv.put("days", 0);
        cv.put("state", 0);
        cv.put("volume", 1);
        cv.put("enable", 1);
        long rowID = db.insert("timetable", null, cv);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);
        dbHelper.close();
    }
    
    public void onClick_read(View v) {    	
        SQLiteDatabase db = dbHelper.getWritableDatabase();
	    Cursor c = db.query("timetable", null, null, null, null, null, null);

	    if (c.moveToFirst()) {
	      int idColIndex = c.getColumnIndex("id");
	      int hourColIndex = c.getColumnIndex("hour");
	      int minColIndex = c.getColumnIndex("min");
	      int dayColIndex = c.getColumnIndex("day");
	      int stateColIndex = c.getColumnIndex("state");
	      int volumeColIndex = c.getColumnIndex("volume");
	      int enableColIndex = c.getColumnIndex("enable");
	
	      do {	        
	        Log.d(LOG_TAG,
	            "ID = " + c.getInt(idColIndex) + 
	            ", time:  " + c.getInt(hourColIndex) +":"+ c.getInt(minColIndex) + 
	            ", volume = " + c.getInt(volumeColIndex));	       
	      } while (c.moveToNext());
	    } else
	      Log.d(LOG_TAG, "0 rows");
	    c.close();
	    dbHelper.close();
    }
    
    public void cleanDB() {    	
        SQLiteDatabase db = dbHelper.getWritableDatabase();       
        int clearCount = db.delete("timetable", null, null);
        Log.d(LOG_TAG, "deleted rows count = " + clearCount);
        dbHelper.close();
    }   
    
    
    public void onClickStart(View v) {
    	startService(new Intent(this, MainService.class));      
    }
    
    public void onClickStop(View v) {
    	stopService(new Intent(this, MainService.class));
    }

}
