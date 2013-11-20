package com.example.volumescheduler;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

public class MainService extends Service {
		  
	  ExecutorService es;	 
	  final String LOG_TAG = "myLogs";
	  
	  public void onCreate() {
	    super.onCreate();	    
	    es = Executors.newFixedThreadPool(1);
	  }
	  
	  public void onDestroy() {
	    super.onDestroy();	    	    
	  }

	  public int onStartCommand(Intent intent, int flags, int startId) {
	    MainRun mr = new MainRun();
	    es.execute(mr);
	    return super.onStartCommand(intent, flags, startId);
	  }

	  public IBinder onBind(Intent arg0) {
	    return null;
	  }
	  
	  class MainRun implements Runnable {

	    //long next_time;
	    Calendar curr_time;
	    int curr_volume;
	    DBHelper dbHelper;

	    public MainRun() {
	    	curr_time = GetCurrentTime();
	      //curr_volume = GetCurrentVolume(curr_time);
    	  SetVolume(curr_volume);
	      //next time = ReadNextTime(curr_time);
	      //взяти найближчий час(початок нового правила чи кінець поточного)	      
	    }
	    
	    public void run() {
	      
	      try {
	    	  for(int i = 0;;i++)
	    	  {
	    		  curr_time = GetCurrentTime();
		    	  //if(curr_time >= next_time) //якщо настав час зміни правила
		    	  //{
		    	  //	curr_volume = GetCurrentVolume(curr_time);
		    	  //	SetVolume(curr_volume);
			      //	next time = ReadNextTime(curr_time);
		    	  //}	    		  
		    	  TimeUnit.SECONDS.sleep(20);		    	  
			  }	      
	      }catch (InterruptedException e) {e.printStackTrace();}
	    }
	    
	    private Calendar GetCurrentTime()
	    {
	    	return Calendar.getInstance();
	    }
	    
	    private void SetVolume(int volume)
	    {
	    	AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
	    	audio.setStreamVolume(AudioManager.STREAM_RING, volume, 0);
	    }
	    
	    private int GetVolumeOnDevice()
	    {
	    	AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);	    
	    	return audio.getStreamVolume(AudioManager.STREAM_RING);
	    }
	  
	    private void GetCurrentVolume(Calendar cl)
	    {	    	
	    	int min = cl.get(Calendar.MINUTE) + cl.get(Calendar.HOUR_OF_DAY) * 60;
	    	int day = cl.get(Calendar.DAY_OF_WEEK) -1;
	    	
	    	
	    }

	    private void GetNearTime()
	    {
	    	dbHelper = new DBHelper(MainService.this);
	    	SQLiteDatabase db = dbHelper.getWritableDatabase();	        
		    Cursor c = db.query("timetable", null, null, null, null, null, null);
		    if (c.moveToFirst()) {
		      int idColIndex = c.getColumnIndex("id");
		      int hourColIndex = c.getColumnIndex("hour");
		      int minColIndex = c.getColumnIndex("min");
		      int daysColIndex = c.getColumnIndex("days");
		      int stateColIndex = c.getColumnIndex("state");
		      int volumeColIndex = c.getColumnIndex("volume");
		      do {
		    	 
		    	  //add to list
		    	 // c.getInt(idColIndex)

		      } while (c.moveToNext());
		    } 
		    c.close();
		    dbHelper.close();
	    }
	    
	    
	  }	
}
