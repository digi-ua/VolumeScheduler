package com.example.volumescheduler;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;

/*
public class Manager {
	public static Dictionary GetAllTime(TimeTable[] ttObjects)
	{
		int timeBegin, timeEnd;
		Dictionary<Integer, Integer> timesDictionary = new Hashtable();
		for (int i = 0; i < ttObjects.length; i++) {
			if(ttObjects[i].state != 0)
			{
				for (int j = 0; j < ttObjects.length; j++) {
					if((int)ttObjects[i].state == (int)ttObjects[j].id)
					{
						timesDictionary.put(ttObjects[i].id, ttObjects[j].id);
					}
				}
			}
		}
		return timesDictionary;
	}
	*/
	/*в статус≥ початку записане ≥д статусу к≥нц€б в статус≥ к≥нц€ записаний нуль*/
/*	
private boolean CreateUpdateNewRule(TimeTable[] ttObjects)
=======
	
	public static Dictionary GetAllTime(List<TimeTable> ttList)
	{
		int timeBegin, timeEnd;
		Dictionary<Integer, Integer> timesDictionary = new Hashtable();
		for (TimeTable timeTable : ttList) {
			if(timeTable.state != 0)
			{
				for (TimeTable tTable : ttList) {
					if((int)tTable.state == (int)tTable.id)
					{
						timesDictionary.put(tTable.id, tTable.id);
					}
				}
			}
		}
		return timesDictionary;
	}
	
	/*в статус≥ початку записане ≥д статусу к≥нц€, в статус≥ к≥нц€ записаний нуль*/
/*	
	private boolean CreateUpdateNewRule(TimeTable[] ttObjects)

	{
		DBHelper dBHelper = new DBHelper(null);
		List<TimeTable> ttList = dBHelper.getAll();
		Dictionary<Integer, Integer> ruleFromForm = GetAllTime(ttObjects);
		Dictionary<Integer, Integer> ruleFromDB = GetAllTime(ttList);
		
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
*/

