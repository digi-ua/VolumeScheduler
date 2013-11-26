package com.example.volumescheduler;

import java.util.ArrayList;
import java.util.List;

public class TimeTable {
	  public int id;
	  public int s_time;
	  public int e_time;		  
	  public String days;
	  public int state;
	  public int rule;
	  public int activ;
	  
	  public static int getMin(int time) {
		  return time % 60 + 1;
	  }
	  
	  public static int getHour(int time) {
		  return time / 60;
	  }
	  	  
	  public List<Integer> parseDays() {
		  List<Integer> res = new ArrayList<Integer>(); 
		  String[] d = days.split(" ");
		  for (String day : d) {
			  if (day == "Sun") res.add(0);
			  else if (day == "Mon") res.add(1);
			  else if (day == "Tue") res.add(2);
			  else if (day == "Wed") res.add(3);
			  else if (day == "Thu") res.add(4);
			  else if (day == "Fri") res.add(5);
			  else if (day == "Sat") res.add(6);
		  }
		  return res;
	  }
}
