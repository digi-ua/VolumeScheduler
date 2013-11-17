package com.example.volumescheduler;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Intent;
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
		  
	    Calendar next_time;
	    Calendar curr_time;
	    int curr_volume;//type
	    
	    public MainRun() {
	    	Log.d(LOG_TAG, "MainRun create");
	      GetCurrentTime(curr_time);
	      //curr_volume = ReadCurrentVolume(curr_time);
	      //SetCurrentProp(curr_volume);
	      //next time = ReadNextTime();
	      
	    }
	    
	    public void run() {
	      Log.d(LOG_TAG, "MainRun start");
	      try {
	    	  for(int i = 0;;i++)
	    	  {
		    	  GetCurrentTime(curr_time);
		    	  //if(curr_time >= next_time)
		    	  //{
		    	  //	curr_volume = ReadCurrentProp();
			      //	SetCurrentProp(curr_volume); 
			      //	next time = ReadNextTime();
		    	  //}
	    		  Log.d(LOG_TAG, "MainRun cicle #" + i);
		    	  TimeUnit.SECONDS.sleep(60);		    	  
			  }	      
	      }catch (InterruptedException e) {e.printStackTrace();}
	    }
	    
	    /*
	    void stop() {
	      Log.d(LOG_TAG, "MyRun# stopSelf");
	      stopSelf();
	    }
	    */
	    
	    private void GetCurrentTime(Calendar cl)
	    {
	    	cl = Calendar.getInstance(); 
	    	/*
	    	int millisecond = cl.get(Calendar.MILLISECOND);
	    	int second = cl.get(Calendar.SECOND);
	    	int minute = cl.get(Calendar.MINUTE);//12 hour format	    	      
	    	int hour = cl.get(Calendar.HOUR);	    	     
	    	int hourofday = cl.get(Calendar.HOUR_OF_DAY);//24 hour format
	    	int dayofyear = cl.get(Calendar.DAY_OF_YEAR);
	    	int year = cl.get(Calendar.YEAR);
	    	int dayofweek = cl.get(Calendar.DAY_OF_WEEK);
	    	int dayofmonth = cl.get(Calendar.DAY_OF_MONTH);
	    	*/
	    }
	    
	  }	
}
