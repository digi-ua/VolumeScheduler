package com.example.volumescheduler;

public class ListModel {
    
	private  int ID;
    private  int StartTime = 0;
    private  int EndTime = 0;
    private  boolean Vibrate = false;
    private  String Days = "";
    private  boolean Active;
     
    /*********** Set Methods ******************/
    
    public void setID(int id)
    {
        this.ID = id;
    }
    
    public void setStartTime(int s_hour, int s_min)
    {
        this.StartTime = s_hour*60 + s_min;
    }
     
    public void setEndTime(int e_hour, int e_min)
    {
        this.EndTime = e_hour*60 + e_min;
    }
     
    public void setDays(String Days)
    {
        this.Days = Days;
    }
    
    public void setVibrate(int vibrate)
    {
        if (vibrate == 1)
        	this.Vibrate = true;
        else
        	this.Vibrate = false;
    }
    
    public void setActive(int active)
    {
        if (active == 1)
        	this.Active = true;
        else
        	this.Active = false;
    }
    
     
    /*********** Get Methods ****************/
    
    public int getID()
    {
        return this.ID;
    }
    
    public int getStartTime()
    {
        return this.StartTime;
    }
     
    public int getEndTime()
    {
        return this.EndTime;
    }
    
    public String getStartTimeString()
    {
    	int hour = StartTime / 60;
    	int min = StartTime % 60;
        return Integer.toString(hour)+":"+Integer.toString(min);
    }
     
    public String getEndTimeString()
    {
    	int hour = EndTime / 60;
    	int min = EndTime % 60;
        return Integer.toString(hour)+":"+Integer.toString(min);
    }
 
    public String getDays()
    {
        return this.Days;
    }
    
    public boolean getVibrate()
    {
        return this.Vibrate;
    }
    
    public boolean getActive()
    {
    	return this.Active;
    }
}
