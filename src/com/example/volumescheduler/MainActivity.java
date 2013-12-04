package com.example.volumescheduler;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class MainActivity extends Activity {

	public static final String ACTION_RULELIST_UPDATE = "com.example.volumesheduler.RULELIST_UPDATE";

	Manager mng = new Manager();

	ListView list;
	CustomAdapter adapter;
	public List<RuleModel> CustomListViewValuesArr = new ArrayList<RuleModel>();

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("------", "-------");
			Log.d("------", "-------");
			Log.d("Receiver", "onReceive");
			FromDBtoList();
			list.setAdapter(adapter);

		}
	};
	
	private BroadcastReceiver broRecAutoRun = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("Autorun: ", "onReceive " + intent.getAction());
		    context.startService(new Intent(context, MainService.class));
		}
		
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_RULELIST_UPDATE);
		registerReceiver(broadcastReceiver, intentFilter);

		list = (ListView) findViewById(R.id.list);
		/******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
		
		
		//add new object to database !  Troubles with service if no rules or nothing is no active
		//mng.CreateOrUdateRule(new RuleModel(0, 1, 2, "Sun", 1, 1), this); 
		
		FromDBtoList();
		/**************** Create Custom Adapter *********/
		
		adapter = new CustomAdapter(this, CustomListViewValuesArr);
		
		Log.d("MainActivity_OnCreate", "CustomAdaper is created -- Size: "
				+ adapter.getCount());
		Log.d("------", "-------");
		Log.d("------", "-------");

		list.setAdapter(adapter);
		
		Service();
		
	}

	@Override
	protected void onDestroy() {
		Log.d("MainActivity", "OnDestroy !!");
		Log.d("------", "-------");
		Log.d("------", "-------");
		unregisterReceiver(broadcastReceiver);
		
		super.onDestroy();
	}

	public void FromDBtoList() {
		
		CustomListViewValuesArr.clear();
		
		List<RuleModel> rules = mng.GetList(this);

		for (RuleModel model : rules)
			CustomListViewValuesArr.add(model);

		Log.d("MainActivity", "CustomList is created: Size: "
				+ CustomListViewValuesArr.size());
		Log.d("------", "-------");
		Log.d("------", "-------");

	}

	/****** Function to set data in ArrayList *************/

	public void onItemClick(int mPosition) {
		RuleModel tempValues = (RuleModel) CustomListViewValuesArr
				.get(mPosition);
		Intent intent = new Intent(this, AddRule.class);
		intent.putExtra("MODE", 2);
		intent.putExtra("ID", tempValues.ID);
		intent.putExtra("S_TIME", tempValues.StartTime);
		intent.putExtra("E_TIME", tempValues.EndTime);
		intent.putExtra("Vibrate", tempValues.Rule);
		intent.putExtra("Days", tempValues.Days);
		intent.putExtra("State", tempValues.State);
		intent.putExtra("IsRunning", tempValues.IsRunning);
		intent.putExtra("Active", tempValues.Active);
		startActivity(intent);

	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_add: {
			Intent intent = new Intent(v.getContext(), AddRule.class);
			startActivity(intent);
		}
			break;
		default:
			break;
		}

	}

	public void Service() {
		//stopService(new Intent(this, MainService.class));
		Log.d("MainActivity", "-- Service is started --");
		Log.d("------", "-------");
		Log.d("------", "-------");
		startService(new Intent(this, MainService.class));
	}

}
