package com.example.volumescheduler;

public class TimeTable {
	  public int id;
	  public int hour;
	  public int min;		  
	  public int day;
	  public int state;
	  public int rule;
	  public int enable;
	  
	  public int getMinOfDay() {
		  return min + hour * 60;
	  }
}
