package com.example.volumescheduler;

import java.util.List;


public class Manager {
	/*в статус≥ початку записане ≥д статусу к≥нц€б в статус≥ к≥нц€ записаний нуль*/
	private boolean CreateUpdateNewRule(TimeTable[] ttObjects)
	{
		DBHelper dBHelper = new DBHelper();
		List<TimeTable> ttList = dBHelper.getAll();
		try {
			int j = 0;
				int tmp = ttObjects[j].state;
				if(tmp != 0)
				{
					
					for (int i = 0; i < ttObjects.length; i++) {
						
					}
				}
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
