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
	    int curr_volume;

	    public MainRun() {

	      GetCurrentTime(curr_time);
	      //curr_volume = GetCurrentVolume(curr_time);
	      // якщо існує правило на даний час - взяти опції звуку, в іншому випадку -1
	      if(curr_volume != -1)
	    	  SetVolume(curr_volume);
	      //next time = ReadNextTime(curr_time);
	      //взяти найближчий час(початок нового правила чи кінець поточного)	      
	    }
	    
	    public void run() {
	      
	      try {
	    	  for(int i = 0;;i++)
	    	  {
		    	  GetCurrentTime(curr_time);
		    	  //if(curr_time >= next_time) //якщо настав час зміни правила
		    	  //{
		    	  //	curr_volume = GetCurrentVolume(curr_time);
		    	  //	if(curr_volume != -1)
			      //		SetVolume(curr_volume);
			      //	next time = ReadNextTime(curr_time);
		    	  //}	    		  
		    	  TimeUnit.SECONDS.sleep(20);		    	  
			  }	      
	      }catch (InterruptedException e) {e.printStackTrace();}
	    }

	    
	    private void GetCurrentTime(Calendar cl)
	    {
	    	cl = Calendar.getInstance();
    	
	    	//cl.set(2013, 10, 17, 20, 16);  //mounth -= 1; !!
	    	/*
	    	cl.set(year, month, day, hourOfDay, minute);
	    	long millis = cl.getTimeInMillis();
	    	Calendar cl1 = null;
	    	cl1.setTimeInMillis(millis);	    	
	    	int millisecond = cl.get(Calendar.MILLISECOND);
	    	*/
	    }
	    
	    private void SetVolume(int volume)
	    {
	    	AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
	    	audio.setStreamVolume(AudioManager.STREAM_RING, volume, 0);
	    }
	    
	    private int GetCurrentVolume()
	    {
	    	AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);	    
	    	return audio.getStreamVolume(AudioManager.STREAM_RING);
	    }
	  
	  }	
}
