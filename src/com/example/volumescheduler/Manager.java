package com.example.volumescheduler;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;

import android.content.Context;

public class Manager {
	
	public static boolean ChekedRules(RuleModel ruleModel, Context context)
	{
		DBHelper dBHelper = new DBHelper(context);	
		List<RuleModel> ruleList = dBHelper.getAll();
		List<Integer> newRuleDayList = ruleModel.parseDays();
		for (RuleModel rm : ruleList) {
			List<Integer> tmpDayList = rm.parseDays();
			for (int i = 0; i < 6;) {
				if(tmpDayList.get(i) == newRuleDayList.get(i))
				{
					if((rm.StartTime <= ruleModel.StartTime) && (rm.EndTime > ruleModel.StartTime) && (rm.EndTime < ruleModel.EndTime))
					{
						return false;
					}
					
					if((rm.EndTime >= ruleModel.StartTime) && (rm.EndTime > ruleModel.StartTime) && (rm.EndTime < ruleModel.EndTime))
					{
						return false;
					}
				}
				else
					i++;
			}
		}
		return true;
	}
	
	public List<RuleModel> GetList(Context context)
	{
		DBHelper dBHelper = new DBHelper(context);	
		List<RuleModel> ruleList = dBHelper.getAll();
		return ruleList;
	}
	
	public boolean CreateOrUdateRule(RuleModel ruleModel, Context context)
	{
		DBHelper dBHelper = new DBHelper(context);	
		boolean isCheked = ChekedRules(ruleModel, context);
		if(isCheked)
		{	
			try {
				dBHelper.Save(ruleModel);
				}
			catch (Exception e) {
				return false;
			}	
			return true;
		}
		return false;		
	}
	private boolean DeleteRule(RuleModel ruleModel, Context context)
	{
		DBHelper dBHelper = new DBHelper(context);	
		try {
				dBHelper.Delete(ruleModel.ID);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}

