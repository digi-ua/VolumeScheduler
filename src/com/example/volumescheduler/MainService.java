package com.example.volumescheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
	    int curr_rule;
	    
	    DBHelper db = null;
	    List<TimeTable> ttList = null;

	    public MainRun() {
	    	db = new DBHelper(MainService.this);
	    	curr_time = GetCurrentTime();
	    	
	      //curr_volume = GetCurrentVolume(curr_time);
    	  SetRule(curr_rule);
	      //next time = ReadNextTime(curr_time);
	      //взяти найближчий час(початок нового правила чи кінець поточного)	      
	    }
	    
	    public void run() {
	      try {
	    	  while (true)
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
	    
	    private void SetRule(int rule)
	    {
	    	AudioManager aManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        	switch (rule) {
        	case AudioManager.RINGER_MODE_NORMAL:
        		aManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        		break;
        	case AudioManager.RINGER_MODE_VIBRATE:
        		aManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        		break;
        	case AudioManager.RINGER_MODE_SILENT:
        		aManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        		break;
        	}
	    }
	    
	    private int GetVolumeOnDevice()
	    {
	    	AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);	    
	    	return audio.getStreamVolume(AudioManager.STREAM_RING);
	    }
	  
	    private int GetCurrentVolume(Calendar cl)
	    {
	    	ttList = db.getAll();
	    	int minOfDay = cl.get(Calendar.MINUTE) + cl.get(Calendar.HOUR_OF_DAY) * 60;
	    	int day = cl.get(Calendar.DAY_OF_WEEK) -1;
	    	int id = -1;
	    	int min = 32000;	    	
	    	for (final TimeTable tt : ttList) 
	    	{
	    		if(tt.day == day)
	    			if((minOfDay - tt.getMinOfDay())+((day + 7 - tt.day)%7)*1440 < min)
	    			{
	    				min = minOfDay - tt.getMinOfDay();
	    				id = tt.id;
	    			}
	            
	    	}
	    	return ttList.;//ttList.	    	
	    }    
	  }
}
