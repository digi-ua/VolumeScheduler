package com.example.volumescheduler;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
//import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;

public class MainActivity extends Activity {

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

		// onDebilClick();

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
		
		rule.setID(getIntent().getIntExtra("ID", 0));
		rule.setStartTime(getIntent().getIntExtra("S_HOUR", 0), getIntent().getIntExtra("S_MIN", 0));
		rule.setEndTime(getIntent().getIntExtra("E_HOUR", 0), getIntent().getIntExtra("E_MIN", 0));
		rule.setDays(getIntent().getStringExtra("Days"));
		rule.setVibrate(getIntent().getIntExtra("Vibrate", 0));
		rule.setActive(getIntent().getIntExtra("Active", 0));

	}

	/****** Function to set data in ArrayList *************/
	public void setListData() {

		for (int i = 0; i < 3; i++) {

			final RuleModel rule = new RuleModel();

			/******* Firstly take data in model object ******/
			rule.setStartTime(i, i + 28);
			rule.setEndTime(i + 1, i + 41);
			rule.setDays(rule_days[i]);

			/******** Take Model Object in ArrayList **********/
			CustomListViewValuesArr.add(rule);
		}

	}

	public void onItemClick(int mPosition) {
		RuleModel tempValues = (RuleModel) CustomListViewValuesArr
				.get(mPosition);

		// SHOW ALERT

		Toast.makeText(
				mainActivity,
				"" + tempValues.getStartTime() + " min " + "-"
						+ tempValues.getEndTime() + " min " + "--Days: "
						+ tempValues.getDays(), Toast.LENGTH_LONG).show();
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
		tt.setID(0);
		tt.setStartTime(10, 8);
		tt.setEndTime(11, 43);
		tt.setDays("Sun Mon Tue Wed Thu Fri Sat");
		tt.setVibrate(1);
		tt.setActive(1);

		// DBHelper db = new DBHelper(this);

		Log.d(LOG_TAG, "DB init");

		// db.Save(tt);

		// startService(new Intent(this, MainService.class));
	}

	/*
	 * public void onClickStop(View v) { stopService(new Intent(this,
	 * MainService.class)); }
	 */

}
