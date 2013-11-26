package com.example.volumescheduler;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;


public class Manager {
	
	/*в статус≥ початку записане ≥д статусу к≥нц€, в статус≥ к≥нц€ записаний нуль*/
	private boolean CreateUpdateNewRule(TimeTable tt)
	{
		DBHelper dBHelper = new DBHelper(null);
		List<TimeTable> ttList = dBHelper.getAll();
		
		try {
				dbHelper.Save(tt);	
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	private boolean DeleteRule(TimeTable tt)
	{
		try {
				dBHelper(tt.id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
