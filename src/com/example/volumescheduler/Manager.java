package com.example.volumescheduler;

import java.util.List;
import android.content.Context;
import android.util.Log;

public class Manager {

	public static boolean ChekedRules(RuleModel ruleModel, Context context) {

		DBHelper dBHelper = new DBHelper(context);
		List<RuleModel> ruleList = dBHelper.getAll();
		if (ruleModel.StartTime >= ruleModel.EndTime || ruleModel.Days == "")
			return false;
		int[] newRuleDayList = ruleModel.parseDaysBool();
		for (RuleModel rm : ruleList) {
			int[] tmpDayList = rm.parseDaysBool();
			for (int i = 0; i < 7; i++) {
				if ((tmpDayList[i] == newRuleDayList[i])
						&& (tmpDayList[i] != 0 || newRuleDayList[i] != 0)) {
					if (!(ruleModel.ID == rm.ID)) {
						Log.d("Mode", "Create -- Day:" + i);
						Log.d("------", "-------");
						Log.d("------", "-------");
						if ((ruleModel.StartTime >= rm.StartTime && ruleModel.StartTime < rm.EndTime)
								|| (ruleModel.EndTime > rm.StartTime && ruleModel.EndTime <= rm.EndTime)
								|| (ruleModel.StartTime <= rm.StartTime && ruleModel.EndTime > rm.StartTime))
							return false;
					} else {
						Log.d("Mode", "Edited -- Day: " + i);
						Log.d("Mode", "Edited -- ActiveOld: " + rm.Active);
						Log.d("Mode", "Edited -- ActiveUpdated: " + ruleModel.Active);
						Log.d("------", "-------");
						Log.d("------", "-------");
					}
				}
			}
		}

		return true;

	}

	public List<RuleModel> GetList(Context context) {
		return new DBHelper(context).getAll();
	}

	public boolean CreateOrUdateRule(RuleModel ruleModel, Context context) {
		DBHelper dBHelper = new DBHelper(context);
		boolean isCheked = ChekedRules(ruleModel, context);
		if (isCheked) {
			try {
				Log.d("CreateOrUpdate", "isChecke = true --> Rule is good");
				Log.d("------", "-------");
				Log.d("------", "-------");
				dBHelper.Save(ruleModel);
			} catch (Exception e) {
				Log.d("CreateOrUpdate", "Exeption");
				Log.d("------", "-------");
				Log.d("------", "-------");
				return false;
			}
			return true;
		}
		Log.d("CreateOrUpdate", "isChecked = false --> Rule is invald");
		Log.d("------", "-------");
		Log.d("------", "-------");
		return false;
	}

	public boolean DeleteRule(RuleModel ruleModel, Context context) {
		DBHelper dBHelper = new DBHelper(context);
		try {
			dBHelper.Delete(ruleModel.ID);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
