package com.example.volumescheduler;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;

public class Manager {
	
	public static boolean ChekedRules(RuleModel ruleModel)
	{
		DBHelper dBHelper = new DBHelper(null);	
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
	
	public List<RuleModel> GetList(RuleModel ruleModel)
	{
		List<RuleModel> ruleList = new ArrayList<RuleModel>();
		ruleList.add(ruleModel);
		return ruleList;
	}
	
	public boolean CreateOrUdateRule(RuleModel ruleModel)
	{
		boolean isCheked = ChekedRules(ruleModel);
		DBHelper dBHelper = new DBHelper(null);	
		int ID = ruleModel.ID;
		List <RuleModel> oldRuleModelList = dBHelper.getAll();
		if(isCheked)
		{
			DBHelper dBHelper = new DBHelper(null);		
		try {
			for (RuleModel rule : oldRuleModelList) {
				if(rule.ID == ID)
				{
					dBHelper.Save(rule);
				}
			}		
				dbHelper.Save(ruleModel);	
		} catch (Exception e) {
			return false;
		}
		return true;
		}
		return false;		
	}
	private boolean DeleteRule(RuleModel ruleModel)
	{
		try {
				//dBHelper(ruleModel.ID);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}

