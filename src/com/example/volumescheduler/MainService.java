package com.example.volumescheduler;

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

                Time next_time;        
                Time curr_time;
                int curr_rule;
                
                DBHelper db = null;
                List<TimeTable> ttList = null;

            public MainRun() {
                    db = new DBHelper(MainService.this);
                    ttList = db.getAll();
                    curr_time = GetCurrentTime();                    
                    curr_rule = GetCurrentMode(curr_time);                    
                    SetRule(curr_rule);
                    next_time = GetNextTime(curr_time);     
            }
            
            public void run() {
              try {
                      while (true)
                      {
                              curr_time = GetCurrentTime();
                              int sub = Time.compare(curr_time, next_time);
                              if(sub > 0)
                              {
                                      curr_rule = GetCurrentMode(curr_time);
                                      SetRule(curr_rule);
                                      next_time = GetNextTime(curr_time);
                              }
                              TimeUnit.SECONDS.sleep(20);                      
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
            
            private int GetRingerMode() {
                    AudioManager aManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);            
                    return aManager.getRingerMode();
            }
          
            private int GetCurrentMode(Time t) {
                    int minOfDay = t.minute + t.hour * 60;
                    int day = t.weekDay;
                    int min = Integer.MAX_VALUE;
                    TimeTable res = null;
                    for (final TimeTable tt : ttList)
                    {                            
                                    if(tt.day == day) {
                                            if((minOfDay - tt.getMinOfDay())+((day + 7 - tt.day)%7)*1440 < min) {
                                                    min = minOfDay - tt.getMinOfDay();
                                                    res = tt;
                                            }
                                    }                            
                    }
                    if(res.state == 0) {
                            return res.rule;
                    } else {
                            return -1;
                    }
            } 
            
            private Time GetNextTime(Time t) {
                    int minOfDay = t.minute + t.hour * 60;
                    int day = t.weekDay;
                    int min = Integer.MAX_VALUE;
                    TimeTable res = null;
                    for (final TimeTable tt : ttList)
                    {                            
                            if(tt.day == day) {
                                    if((tt.getMinOfDay() - minOfDay)+((tt.day + 7 - day)%7)*1440 < min) {
                                            min = tt.getMinOfDay() - minOfDay;
                                            res = tt;
                                    }
                            }                            
                    }            
                    //Time t1;
                    t.weekDay = res.day;
                    t.hour = res.hour;
                    t.minute = res.min;
                    return t;
            }
            
            
            
            
         }
}