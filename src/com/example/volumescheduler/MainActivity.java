package com.example.volumescheduler;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.Toast;
//import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class MainActivity extends Activity {

	// DBHelper dbHelper;
	final String LOG_TAG = "myLogs";
	public static final String ACTION_RULELIST_UPDATE = "com.example.volumesheduler.RULELIST_UPDATE";

	Manager mng = new Manager();;

	ListView list;
	CustomAdapter adapter;
	public MainActivity mainActivity = null;
	public ArrayList<RuleModel> CustomListViewValuesArr = new ArrayList<RuleModel>();
	private ImageButton btn_add;
	private String[] rule_days = { "Mon Tue Fri", "Tue Wed", "Fri Sun" };

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			Log.d("Receiver", "onReceive");
			FromDBtoList();

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_RULELIST_UPDATE);
		registerReceiver(broadcastReceiver, intentFilter);

		mainActivity = this;
		// onDebilClick(); // test service function

		/******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/

		list = (ListView) findViewById(R.id.list);

		btn_add = (ImageButton) findViewById(R.id.btn_add);

		FromDBtoList();

		/**************** Create Custom Adapter *********/
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}

	public void FromDBtoList() {

		List<RuleModel> rules = mng.GetList(this);

		CustomListViewValuesArr.clear();

		for (RuleModel model : rules)
			CustomListViewValuesArr.add(model);
		
		adapter = new CustomAdapter(mainActivity, CustomListViewValuesArr);

		list.setAdapter(adapter);

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

	public void onDebilClick() {
		RuleModel tt = new RuleModel();
		tt.ID = 0;
		tt.StartTime = 275;
		tt.EndTime = 278;
		tt.Days = "Sun Mon Tue Wed Thu Fri Sat";
		tt.State = 0;
		tt.Rule = 1;
		tt.IsRunning = 0;
		tt.Active = 1;

		// DBHelper db = new DBHelper(this);

		// db.Save(tt);
		// db.Save(tt1);

		stopService(new Intent(this, MainService.class));
		// Log.d(LOG_TAG, "stop service");

		startService(new Intent(this, MainService.class));

		// db.Save(tt);

		// List<RuleModel> ttList = db.getAll();
		// db.close();
		// for (final RuleModel rm : ttList){
		// Log.d(LOG_TAG, "id=" + rm.ID + " sTime=" + rm.StartTime + " eTime=" +
		// rm.EndTime + " rule=" + rm.Rule);
		// }

		// stopService(new Intent(this, MainService.class));
		// startService(new Intent(this, MainService.class));
	}

}
