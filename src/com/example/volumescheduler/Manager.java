package com.example.volumescheduler;

import java.util.Dictionary;
import java.util.List;
import java.util.Comparator;


public class Manager {
	public static Dictionary GetAllTime(TimeTable[] ttObjects)
	{
		for (int i = 0; i < ttObjects.length; i++) {
			if(ttObjects[i].state != 0)
			{
				for (int j = 0; j < ttObjects.length; j++) {
					int isEqual = compare((int)ttObjects[i].state, (int)ttObjects[j].id);
					if(isEqual == 0)
				}
			}
		}
		return null;
	}
	/*в статус≥ початку записане ≥д статусу к≥нц€б в статус≥ к≥нц€ записаний нуль*/
	private boolean CreateUpdateNewRule(TimeTable[] ttObjects)
	{
		DBHelper dBHelper = new DBHelper();
		List<TimeTable> ttList = dBHelper.getAll();
		try {			
			for (int i = 0; i < ttObjects.length; i++) {
				dbHelper.Save(ttObjects[i]);
			}			
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	private boolean DeleteRule(TimeTable[] ttObjects)
	{
		try {
			for (int i = 0; i < ttObjects.length; i++) {
				dBHelper(ttObjects[i].id);
			}			
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
