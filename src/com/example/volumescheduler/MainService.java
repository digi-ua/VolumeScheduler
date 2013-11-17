package com.example.volumescheduler;

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
		  
	    int next_time;
	    int curr_time;
	    int curr_volume;
	    
	    public MainRun() {
	    	Log.d(LOG_TAG, "MainRun create");
	      //curr_time = GetCurrTime();
	      //curr_volume = ReadCurrentVolume(curr_time);
	      //SetCurrentProp(curr_volume);
	      //next time = ReadNextTime();
	      
	    }
	    
	    public void run() {
	      Log.d(LOG_TAG, "MainRun start");
	      try {
	    	  for(int i = 0;;i++)
	    	  {
		    	  //curr_time = GetCurrTime();
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
	  }	
}
