package com.example.volumescheduler;

import java.util.ArrayList;
import java.util.List;

public class RuleModel {
	public int ID;
	public int StartTime = 0;
	public int EndTime = 0;
	public int Vibrate = 0;
	public String Days = "";
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
	
	public List<Integer> parseDays() {
		List<Integer> res = new ArrayList<Integer>();
		String[] d = Days.split(" ");
		for (String day : d) {
			if (day == "Sun")
				res.add(0);
			else if (day == "Mon")
				res.add(1);
			else if (day == "Tue")
				res.add(2);
			else if (day == "Wed")
				res.add(3);
			else if (day == "Thu")
				res.add(4);
			else if (day == "Fri")
				res.add(5);
			else if (day == "Sat")
				res.add(6);
		}
		return res;
	}
}