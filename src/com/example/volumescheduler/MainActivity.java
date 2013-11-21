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
      
    
    
    public void onClickStart(View v) {
    	startService(new Intent(this, MainService.class));      
    }
    
    public void onClickStop(View v) {
    	stopService(new Intent(this, MainService.class));
    }

}
