package com.example.volumescheduler;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
//import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;

public class MainActivity extends Activity  {

	// DBHelper dbHelper;
	final String LOG_TAG = "myLogs";

	ListView list;
	CustomAdapter adapter;
	public MainActivity mainActivity = null;
	public ArrayList<RuleModel> CustomListViewValuesArr = new ArrayList<RuleModel>();
	private ImageButton btn_add;
	private TextView emptyView;
	private String[] s_time = { "17:10", "18:20" };
	private String[] e_time = { "18:00", "21:34" };
	private String[] rule_days = { "Mon Tue Fri", "Tue Wed", "Fri Sun" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mainActivity = this;
		onDebilClick();	// test service function

		/******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
		setListData();

		list = (ListView) findViewById(R.id.list);

		btn_add = (ImageButton) findViewById(R.id.btn_add);

		/**************** Create Custom Adapter *********/
		adapter = new CustomAdapter(mainActivity, CustomListViewValuesArr);
		list.setAdapter(adapter);

	}
	
	public void FromDBtoList()
	{
		//Тут заповняєм лістВЮ при запуску . Через метод з манаджера.
		/******
		  
		 List<RuleModel> rules = Manager.getRuleList();
		 
		 for(int i = 0; i < list.lenght; i ++)
		 {
		 	CustomListViewValuesArr.add(rules[i]);
		 }
		  
		 
		 ******/
	}
	
	
	///мабуть не треба цього методу
	public void getIntents() {
		
		final RuleModel rule = new RuleModel();
		
		rule.ID = getIntent().getIntExtra("ID", 0);
		rule.setStartTime(getIntent().getIntExtra("S_HOUR", 0), getIntent().getIntExtra("S_MIN", 0));
		rule.setEndTime(getIntent().getIntExtra("E_HOUR", 0), getIntent().getIntExtra("E_MIN", 0));
		rule.Days = getIntent().getStringExtra("Days");
		rule.Rule = getIntent().getIntExtra("Vibrate", 0);
		rule.Active = getIntent().getIntExtra("Active", 0);

	}

	/****** Function to set data in ArrayList *************/
	public void setListData() {

		for (int i = 0; i < 3; i++) {

			final RuleModel rule = new RuleModel();

			/******* Firstly take data in model object ******/
			rule.setStartTime(i, i + 28);
			rule.setEndTime(i + 1, i + 41);
			rule.Days = rule_days[i];

			/******** Take Model Object in ArrayList **********/
			CustomListViewValuesArr.add(rule);
		}

	}

	public void onItemClick(int mPosition) {
		RuleModel tempValues = (RuleModel) CustomListViewValuesArr
				.get(mPosition);

		// SHOW ALERT
		/*
		Toast.makeText(
				mainActivity,
				"" + tempValues.StartTime + " min " + "-"
						+ tempValues.EndTime + " min " + "--Days: "
						+ tempValues.Days, Toast.LENGTH_LONG).show();
						*/
		Intent intent = new Intent(this, AddRule.class);
		intent.putExtra("MODE", 2);
		intent.putExtra("ID", tempValues.ID);
        intent.putExtra("S_TIME", tempValues.StartTime);
        intent.putExtra("E_TIME", tempValues.EndTime);
        intent.putExtra("Vibrate", tempValues.Rule);
        intent.putExtra("Days", tempValues.Days);
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

	public void onDebilClick() {
		RuleModel tt = new RuleModel();
		tt.ID = 0;
		tt.StartTime = 100;
		tt.EndTime = 200;
		tt.Days = "Sun Mon Tue Wed Thu Fri Sat";
		tt.State = 0;
		tt.Rule = 1;
		tt.IsRunning = 0;
		tt.Active = 1;

		DBHelper db = new DBHelper(this);
		//db.Save(tt);		

		stopService(new Intent(this, MainService.class));		
		Log.d(LOG_TAG, "stop service");
		
		startService(new Intent(this, MainService.class));
		
		
	}

}
