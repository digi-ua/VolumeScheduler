package com.example.volumescheduler;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.text.format.Time;

public class Manager {
	
	//DBHelper db = new DBHelper(Manager.this);
	DBHelper dbHelper = new DBHelper();
	TimeTable tt = new TimeTable();
	List<TimeTable> ttList = dbHelper.getAll();
	int[] timeArray = new int[ttList.size()];
	int i = 0;
	Time time = new Time();
	public void Varfication(Time t)
		{
			for(TimeTable timeTable : ttList)
			{
				time.set(0, timeTable.min, timeTable.hour, 0, 0, 0);
				timeArray[i] = time - t;
			}
		}
}
