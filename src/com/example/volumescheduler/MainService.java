package com.example.volumescheduler;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

public class MainService extends Service {
	
	  final String LOG_TAG = "myLogs";
	  ExecutorService es;	  
	  
	  public void onCreate() {
	    super.onCreate();
	    Log.d(LOG_TAG, "MainService onCreate");
	    es = Executors.newFixedThreadPool(1);
	  }
	  
	  public void onDestroy() {
	    super.onDestroy();
	    Log.d(LOG_TAG, "MainService onDestroy");	    
	  }

	  public int onStartCommand(Intent intent, int flags, int startId) {
	    Log.d(LOG_TAG, "MainService onStartCommand");	    	    
	    MainRun mr = new MainRun();
	    es.execute(mr);
	    return super.onStartCommand(intent, flags, startId);
	  }

	  public IBinder onBind(Intent arg0) {
	    return null;
	  }
	  
	  class MainRun implements Runnable {
		  
	    long next_time;
	    long curr_time;
	    int curr_volume;
	    
	    public MainRun() {
	      
	      GetCurrentTime(curr_time);
	      //curr_volume = GetCurrentVolume(curr_time);//from xml
	      SetVolume(curr_volume);
	      //next time = ReadNextTime(curr_time);//from xml	      
	    }
	    
	    public void run() {
	      
	      try {
	    	  for(int i = 0;;i++)
	    	  {
		    	  GetCurrentTime(curr_time);
		    	  //if(curr_time >= next_time)
		    	  //{
		    	  //	curr_volume = GetCurrentVolume(curr_time);//from xml
			      //	SetVolume(curr_volume);
			      //	next time = ReadNextTime(curr_time);//from xml
		    	  //}
	    		  Log.d(LOG_TAG, "MainRun cicle #" + i);
		    	  TimeUnit.SECONDS.sleep(20);		    	  
			  }	      
	      }catch (InterruptedException e) {e.printStackTrace();}
	    }
	    
	    /*
	    void stop() {
	      Log.d(LOG_TAG, "MyRun# stopSelf");
	      stopSelf();
	    }
	    */
	    
	    private void GetCurrentTime(long cl)
	    {
	    	cl = Calendar.getInstance().getTimeInMillis(); 
	    	Log.d(LOG_TAG, "GetCurrentTime in millis:" + cl);
	    	
	    	//cl.set(2013, 10, 17, 20, 16);  //mounth -= 1; !!
	    	//Log.d(LOG_TAG, "GetCurrentTime in millis:" + cl.getTimeInMillis());
	    	    
	    	/*
	    	cl.set(year, month, day, hourOfDay, minute);
	    	long millis = cl.getTimeInMillis();
	    	Calendar cl1 = null;
	    	cl1.setTimeInMillis(millis);
	    	
	    	int millisecond = cl.get(Calendar.MILLISECOND);
	    	int second = cl.get(Calendar.SECOND);
	    	int minute = cl.get(Calendar.MINUTE);	    	      
	    	int hour = cl.get(Calendar.HOUR);	//12 hour format    	     
	    	int hourofday = cl.get(Calendar.HOUR_OF_DAY);//24 hour format
	    	int dayofyear = cl.get(Calendar.DAY_OF_YEAR);
	    	int year = cl.get(Calendar.YEAR);
	    	int dayofweek = cl.get(Calendar.DAY_OF_WEEK);
	    	int dayofmonth = cl.get(Calendar.DAY_OF_MONTH);
	    	*/
	    }
	    
	    private void SetVolume(int volume)
	    {
	    	AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
	    	audio.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
	    }
	    
	    private int GetCurrentVolume()
	    {
	    	AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);	    
	    	return audio.getStreamVolume(AudioManager.STREAM_MUSIC);
	    }
	  
	  }	
}
