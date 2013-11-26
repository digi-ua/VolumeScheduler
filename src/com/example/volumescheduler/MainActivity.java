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
	public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
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

		//onDebilClick();

		/******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
		setListData();

		list = (ListView) findViewById(R.id.list);

		btn_add = (ImageButton) findViewById(R.id.btn_add);

		/**************** Create Custom Adapter *********/
		adapter = new CustomAdapter(mainActivity, CustomListViewValuesArr);
		list.setAdapter(adapter);

	}

	/****** Function to set data in ArrayList *************/
	public void setListData() {

		for (int i = 0; i < 3; i++) {

			final ListModel rules = new ListModel();

			/******* Firstly take data in model object ******/
			rules.setStartTime(i, i + 28);
			rules.setEndTime(i + 1, i + 41);
			rules.setDays(rule_days[i]);

			/******** Take Model Object in ArrayList **********/
			CustomListViewValuesArr.add(rules);
		}

	}

	public void onItemClick(int mPosition) {
		ListModel tempValues = ( ListModel ) CustomListViewValuesArr.get(mPosition);
		   
		 // SHOW ALERT                  

         Toast.makeText(mainActivity,
                 ""+tempValues.getStartTime()+" min "
                   +"-"+tempValues.getEndTime()+" min "
                   +"--Days: "+tempValues.getDays(),
                 Toast.LENGTH_LONG)
         .show();
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
		TimeTable tt = new TimeTable();
		tt.id = 0;
		tt.hour = 1;
		tt.min = 8;
		tt.day = 3;
		tt.state = 2;
		tt.rule = 1;
		tt.enable = 1;

		DBHelper db = new DBHelper(this);

		Log.d(LOG_TAG, "DB init");

		db.Save(tt);

		// startService(new Intent(this, MainService.class));
	}

	public void onClickStop(View v) {
		stopService(new Intent(this, MainService.class));
	}

}
