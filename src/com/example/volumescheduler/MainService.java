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
import android.media.AudioManager;
import android.os.IBinder;
import android.text.format.Time;
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
            Log.d(LOG_TAG, "MainService onStart");
            return super.onStartCommand(intent, flags, startId);
            
          }

          public IBinder onBind(Intent arg0) {
            return null;
          }
          
          class MainRun implements Runnable {

                Time next_time;        
                Time curr_time;
                int curr_rule;
                RuleModel currentRuleModel;
                
                DBHelper db = null;
                List<RuleModel> ttList = null;

            public MainRun() {
                    db = new DBHelper(MainService.this);
                    ttList = db.getAll();
                    curr_time = GetCurrentTime();
                    
                    currentRuleModel = GetCurrentRule(curr_time);
                    if(currentRuleModel != null){
                    	SetRule(currentRuleModel.Rule);
                    }
                    
                    
                    next_time = GetNextRuleTime(curr_time);
                    
                    //Log.d(LOG_TAG, "MainService 6");
            }
            
            public void run() {
              try {
                      while (true)
                      {
                              curr_time = GetCurrentTime();
                              int sub = Time.compare(curr_time, next_time);
                              if(sub > 0)
                              {
                            	  	currentRuleModel = GetCurrentRule(curr_time);
                            	  	if(currentRuleModel != null){
                                    	SetRule(currentRuleModel.Rule);
                                    	next_time = GetEndRuleTime(currentRuleModel);
                                    }
                            	  	else{
                            	  		next_time = GetNextRuleTime(curr_time);
                            	  	}
                              }
                              TimeUnit.SECONDS.sleep(30);                      
                          }
              }
              catch (InterruptedException e) {
                      e.printStackTrace();
              }
            }
            
            private Time GetCurrentTime()
            {
                    Calendar cl = Calendar.getInstance();
                    Time t = new Time();
                    t.hour = cl.get(Calendar.HOUR_OF_DAY);
                    t.minute = cl.get(Calendar.MINUTE);
                    t.weekDay = cl.get(Calendar.DAY_OF_WEEK - 1);                    
                    return t;                    
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
            
            /*
            private int GetRingerMode() {
                    AudioManager aManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);            
                    return aManager.getRingerMode();
            }
           */
            
            private RuleModel GetCurrentRule(Time t) {
                    int minOfDay = t.minute + t.hour * 60;                    
                    int day = t.weekDay;
                    int min = Integer.MAX_VALUE;
                    
                    List<Integer> rdays = new ArrayList<Integer>();                    		
                    RuleModel res = null;
                    
                    for (final RuleModel tt : ttList){                   	
                    	
                        rdays = tt.parseDays();
                        int rday = rdays.get(day);
                    	
                    	if((tt.Active == 1)&&(rday == 1)){
	                    	if((minOfDay >= tt.StartTime)&&(minOfDay - tt.StartTime < min)){
	                    		min = minOfDay - tt.StartTime;
	                    		res = tt;
	                    	}                    	
                    	}
                    }
                    
                    if(min == Integer.MAX_VALUE){
                    	if(res.EndTime - minOfDay > 0){
                    		return res;
                    	}
                    } 
                    return null;
                    
            } 
            
            private Time GetNextRuleTime(Time t) {
            	int minOfDay = t.minute + t.hour * 60;                    
                int day = t.weekDay;
                int min = Integer.MAX_VALUE;
                
                List<Integer> rdays = new ArrayList<Integer>();                    		
                RuleModel res = null;
                
                for (final RuleModel tt : ttList){                   	
                	
                    rdays = tt.parseDays();
                    int rday = rdays.get(day);
                	
                	if((tt.Active == 1)&&(rday == 1)){
                    	if((tt.StartTime >= minOfDay)&&(tt.StartTime - minOfDay < min)){
                    		min = tt.StartTime - minOfDay;
                    		res = tt;
                    	}                    	
                	}
                }
                if(min != Integer.MAX_VALUE){
                	rdays = res.parseDays();                	
                    t.hour = res.StartTime / 60;
                    t.minute = res.StartTime % 60;
                    return t;
                }
                
                t.weekDay += 8;                
                t.weekDay = t.weekDay % 7;                
                return GetNextRuleTime(t);                
            }
            
            private Time GetEndRuleTime(RuleModel rm) {
            	Time t = GetCurrentTime();            	            	
                t.hour = rm.StartTime / 60;
                t.minute = rm.StartTime % 60;
                return t;              
            }
            
            
         }
}