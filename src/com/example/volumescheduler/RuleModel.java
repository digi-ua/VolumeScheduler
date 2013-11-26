package com.example.volumescheduler;

import java.util.ArrayList;
import java.util.List;

public class RuleModel {
	private int ID;
	private int StartTime = 0;
	private int EndTime = 0;
	private boolean Vibrate = false;
	private String Days = "";
	private boolean Active;

	/*********** Set Methods ******************/

	public void setID(int id) {
		this.ID = id;
	}

	public void setStartTime(int s_hour, int s_min) {
		this.StartTime = s_hour * 60 + s_min;
	}

	public void setEndTime(int e_hour, int e_min) {
		this.EndTime = e_hour * 60 + e_min;
	}

	public void setDays(String Days) {
		this.Days = Days;
	}

	public void setVibrate(int vibrate) {
		if (vibrate == 1)
			this.Vibrate = true;
		else
			this.Vibrate = false;
	}

	public void setActive(int active) {
		if (active == 1)
			this.Active = true;
		else
			this.Active = false;
	}

	/*********** Get Methods ****************/

	public int getID() {
		return this.ID;
	}
	
	/***** Integer *****/
	public int getStartTime() {
		return this.StartTime;
	}

	public int getEndTime() {
		return this.EndTime;
	}

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

	public String getDays() {
		return this.Days;
	}
	
	/***** Boolean *****/
	public boolean getVibrate() {
		return this.Vibrate;
	}

	public boolean getActive() {
		return this.Active;
	}
	
	/***** Integer *****/
	public int getVibrateInt() {
		if(this.Vibrate)
			return 1;
		else return 0;
	}

	public int getActiveInt() {
		if(this.Active)
			return 1;
		else return 0;
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
