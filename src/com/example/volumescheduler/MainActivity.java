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
        cv.put("name", "name1");
        cv.put("email", "email1");
        long rowID = db.insert("mytable", null, cv);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);
        dbHelper.close();
    }
    
    public void onClick_read(View v) {    	
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        
	    Cursor c = db.query("mytable", null, null, null, null, null, null);
	
	    // ставим позицию курсора на первую строку выборки
	    // если в выборке нет строк, вернетс€ false
	    if (c.moveToFirst()) {
	
	      // определ€ем номера столбцов по имени в выборке
	      int idColIndex = c.getColumnIndex("id");
	      int nameColIndex = c.getColumnIndex("name");
	      int emailColIndex = c.getColumnIndex("email");
	
	      do {
	        // получаем значени€ по номерам столбцов и пишем все в лог
	        Log.d(LOG_TAG,
	            "ID = " + c.getInt(idColIndex) + 
	            ", name = " + c.getString(nameColIndex) + 
	            ", email = " + c.getString(emailColIndex));
	        // переход на следующую строку 
	        // а если следующей нет (текуща€ - последн€€), то false - выходим из цикла
	      } while (c.moveToNext());
	    } else
	      Log.d(LOG_TAG, "0 rows");
	    c.close();
	    dbHelper.close();
    }
    
    public void cleanDB() {    	
        SQLiteDatabase db = dbHelper.getWritableDatabase();       
        int clearCount = db.delete("mytable", null, null);
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
