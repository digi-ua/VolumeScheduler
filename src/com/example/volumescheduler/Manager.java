package com.example.volumescheduler;

import java.util.List;
import android.content.Context;

public class Manager {
	
	public static boolean ChekedRules(RuleModel ruleModel, Context context)
	{
		DBHelper dBHelper = new DBHelper(context);
		List<RuleModel> ruleList = dBHelper.getAll();
		for (RuleModel rm : ruleList) {
			if(rm.ID != ruleModel.ID)
			{
				dBHelper.Delete(rm.ID);
			}
		}
		ruleList.clear();
		ruleList = dBHelper.getAll();
		int[] newRuleDayList = ruleModel.parseDaysBool();
		for (RuleModel rm : ruleList) {			
			int[] tmpDayList = rm.parseDaysBool();
			for (int i = 0; i < 7; i++) {
				if((tmpDayList[i] == newRuleDayList[i]) && (tmpDayList[i] != 0 || newRuleDayList[i] != 0) )
				{
					if((rm.StartTime <= ruleModel.StartTime) && ((rm.EndTime >= ruleModel.StartTime) || (rm.EndTime <= ruleModel.EndTime)))
						{
							return false;
						}
					if((rm.EndTime >= ruleModel.StartTime) && ((rm.EndTime >= ruleModel.StartTime) || (rm.EndTime <= ruleModel.EndTime)))
						{
							return false;
						}
					return true;
				}
			}
		}
		return true;
	}
	
	public List<RuleModel> GetList(Context context)
	{
		DBHelper dBHelper = new DBHelper(context);
		return dBHelper.getAll();
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
	public boolean DeleteRule(RuleModel ruleModel, Context context)
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
