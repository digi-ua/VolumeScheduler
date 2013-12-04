package com.example.volumescheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;

public class MainService extends Service {
	ExecutorService es;
	final static String LOG_TAG = "MainService: ";
	final static int minOfDay = 1440;

	public void onCreate() {
		super.onCreate();
		es = Executors.newFixedThreadPool(1);
	}

	public void onDestroy() {
		Log.d(LOG_TAG, "MainService onDestroy");
		//es.shutdown();
		super.onDestroy();
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(LOG_TAG, "MainService 1 - onStart");
		Run service = new Run();
		es.execute(service);
		return super.onStartCommand(intent, flags, startId);
	}

	public IBinder onBind(Intent arg0) {
		return null;
	}

	class Run implements Runnable {

		DBHelper db = null;

		public Run() {}

		public void run() {
			try {
				db = new DBHelper(MainService.this);
				List<RuleModel> todayRules = null;
				while (true) {
					int indexOfLast = 0;
					todayRules = GetTodayRules(db.getAll());

					if (todayRules != null) {
						for (int i = 0; i < todayRules.size(); i++) {
							if(GetMinutes(GetCurrentTime()) >= todayRules.get(i).StartTime)
							{
								todayRules.get(i).State = 2;
								db.Save(todayRules.get(i));
							}
							else 
								{
									todayRules.get(i).State = GetRingerMode();
									db.Save(todayRules.get(i));
								}
							Log.d("Sleep before rule:", "Time: " + GetCurrentTimeString(GetCurrentTime()));
							TimeUnit.MILLISECONDS.sleep((todayRules.get(i).StartTime * 60000) - GetMiliseconds(GetCurrentTime()));
							
							Log.d("Set rule:", "Time:" + GetCurrentTimeString(GetCurrentTime()));
							SetRule(todayRules.get(i).Rule);
							Log.d("Sleep start:", "Time: " + GetCurrentTimeString(GetCurrentTime()));
							TimeUnit.MILLISECONDS.sleep((todayRules.get(i).EndTime * 60000) - GetMiliseconds(GetCurrentTime()));
							Log.d("Sleep end:", "Time: " + GetCurrentTimeString(GetCurrentTime()));
							Log.d("Set prev rule:", "Time: " + GetCurrentTimeString(GetCurrentTime()));
							SetRule(todayRules.get(i).State);

							indexOfLast = i;
						}
						Log.d("Next rule:", "Time: " + (minOfDay - todayRules.get(indexOfLast).EndTime));
						TimeUnit.MINUTES.sleep(minOfDay - todayRules.get(indexOfLast).EndTime);
					} else 
					{
						Log.d("Next day:", "Time: " + (minOfDay - GetMinutes(GetCurrentTime())));
						TimeUnit.MINUTES.sleep(minOfDay - GetMinutes(GetCurrentTime()));
					}
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private List<RuleModel> GetTodayRules(List<RuleModel> allRules) {
			Time currentTime = GetCurrentTime();
			List<RuleModel> todayRules = new ArrayList<RuleModel>();
			for (RuleModel rule : allRules) {
				List<Integer> days = rule.parseDays();
				for (int day : days) {
					int currTime = GetMinutes(currentTime);
					if (day == currentTime.weekDay && rule.EndTime > currTime && rule.Active == 1 ) {
						todayRules.add(rule);
					}
				}
			}
			return sortTodayRules(todayRules);

		}

		private List<RuleModel> sortTodayRules(List<RuleModel> todayRules) {

			if (todayRules.size() == 0)
				return null;
			RuleModel helperRule = todayRules.get(0);

			for (int i = 1; i < todayRules.size(); i++) {
				if (todayRules.get(i - 1).StartTime > todayRules.get(i).StartTime) {
					helperRule = todayRules.get(i);
					todayRules.set(i, todayRules.get(i - 1));
					todayRules.set(i - 1, helperRule);
				}
			}

			return todayRules;

		}

		private Time GetCurrentTime() {
			Calendar cl = Calendar.getInstance();
			Time t = new Time();
			t.hour = cl.get(Calendar.HOUR_OF_DAY);
			t.minute = cl.get(Calendar.MINUTE);
			t.second = cl.get(Calendar.SECOND);
			t.weekDay = cl.get(Calendar.DAY_OF_WEEK);
			return t;
		}
		
		private String GetCurrentTimeString(Time time)
		{
			long miliseconds = GetMiliseconds(time) % 1000;
			int seconds = time.second;
			int hours = time.hour;
			int minutes = time.minute;
			
			return "Hour: " + hours + " Min: " + minutes + " Sec: " + seconds + " Mili: " + miliseconds ;
		}

		private long GetMiliseconds(Time time){
			return (time.hour * 3600 + time.minute * 60 + time.second) * 1000;
		}
		
		private int GetMinutes(Time time){
			return time.hour * 60 + time.minute;
		}
		
		private void SetRule(int rule) {
			AudioManager aManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			switch (rule) {
			case AudioManager.RINGER_MODE_NORMAL:
				aManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				break;
			case AudioManager.RINGER_MODE_VIBRATE:
				aManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
				break;
			case AudioManager.RINGER_MODE_SILENT:
				aManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				break;

			}
		}

		private int GetRingerMode() {
			AudioManager aManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			return aManager.getRingerMode();
		}

	}
}
