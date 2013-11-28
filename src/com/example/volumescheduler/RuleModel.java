package com.example.volumescheduler;

import java.util.ArrayList;
import java.util.List;

public class RuleModel {
	public int ID;
	public int StartTime;
	public int EndTime;
	public String Days;
	public int State;
	public int Rule;
	public int IsRunning;
	public int Active;

	/*********** Set Methods ******************/

	public void setStartTime(int s_hour, int s_min) {
		this.StartTime = s_hour * 60 + s_min;
	}

	public void setEndTime(int e_hour, int e_min) {
		this.EndTime = e_hour * 60 + e_min;
	}

	/*********** Get Methods ****************/

	/***** String *****/
	public String getStartTimeString() {
		int hour = StartTime / 60;
		int min = StartTime % 60;
		return Integer.toString(hour) + ":" + Integer.toString(min);
	}

	public String getEndTimeString() {
		int hour = EndTime / 60;
		int min = EndTime % 60;
		return Integer.toString(hour) + ":" + Integer.toString(min);
	}

	/*********** Different Methods ****************/

	public int[] parseDaysBool()
	{
		int[] b = {0, 0, 0, 0, 0, 0, 0};
		String[] d = Days.split(" ");
		String[] days = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",	"Sun" };
		
		for(int i = 0; i < days.length; i++)
		{
			for(String d_ : d)
				if (d_.equals(days[i]))
					b[i] = i + 1;
			
			/*
			for(String d_ : d)
			{
				if(d_.equals(days[i]))
					b[i] = true;
			}
			*/
		}
		return b;
	}
	
	public List<Integer> parseDays() {
		List<Integer> res = new ArrayList<Integer>();
		String[] d = Days.split(" ");
	
		for (String day : d) {
			if (day.equals("Sun"))
				res.add(0);
			else if (day.equals("Mon"))
				res.add(1);
			else if (day.equals("Tue"))
				res.add(2);
			else if (day.equals("Wed"))
				res.add(3);
			else if (day.equals("Thu"))
				res.add(4);
			else if (day.equals("Fri"))
				res.add(5);
			else if (day.equals("Sat"))
				res.add(6);
		}

		return res;
	}
}
