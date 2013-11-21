package com.example.volumescheduler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {
    
	DBHelper dbHelper;
	final String LOG_TAG = "myLogs";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);      
    }
      
    
    
    public void onClickStart(View v) {
    	TimeTable tt = new TimeTable();
    	tt.id=0;
    	tt.hour=1;
    	tt.min=8;		  
    	tt.day=3;
    	tt.state=2;
    	tt.rule=1;
    	tt.enable=1;
  	  
    	DBHelper db = new DBHelper(this);
    	
    	Log.d(LOG_TAG, "DB init");
    	
    	db.Save(tt); 
    	
    	Log.d(LOG_TAG, "DB save");
    	
    	db.Save(tt); 
    	
    	Log.d(LOG_TAG, "DB compl");
    	
    	startService(new Intent(this, MainService.class));      
    }
    
    public void onClickStop(View v) {
    	stopService(new Intent(this, MainService.class));
    }

}
