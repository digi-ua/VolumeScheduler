package com.example.volumescheduler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.ToggleButton;

public class AddRule extends Activity {
	final String LOG_TAG = "myLogs";

	Manager mng = new Manager();
	int MODE; // 1 - add, 2 - edit, ...

	ListView list;
	ToggleButton tgl_vibrate;
	ToggleButton tgl_active;
	Button add_rule;
	ImageButton delete_rule;
	Button btn_addSTime;
	Button btn_addETime;
	Button btn_addDays;
	TextView tbx_vibrate;
	TextView tbx_active;
	TextView time;
	TextView tbx_days;

	private int ID = 0;
	private int S_HOUR = 0;
	private int S_MIN = 0;
	private int E_HOUR = 0;
	private int E_MIN = 0;
	private int Rule;
	private int vRule = 0;
	private String Days = "";
	private int State;
	private int IsRunning;
	private int Active;
	private int s_hour, s_min, e_hour, e_min;

	private String[] days_name = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
			"Sun" };

	
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_rule);

		MODE = getIntent().getIntExtra("MODE", 1);

		btn_addDays = (Button) findViewById(R.id.btn_addDays);
		btn_addSTime = (Button) findViewById(R.id.btn_addSTime);
		btn_addETime = (Button) findViewById(R.id.btn_addETime);
		add_rule = (Button) findViewById(R.id.btn_add);
		delete_rule = (ImageButton) findViewById(R.id.btn_delete);
		delete_rule.setVisibility(View.INVISIBLE);
		tgl_vibrate = (ToggleButton) findViewById(R.id.tgl_vibrate);
		tgl_active = (ToggleButton) findViewById(R.id.tgl_active);
		tbx_vibrate = (TextView) findViewById(R.id.tbx_vibrate);
		tbx_active = (TextView) findViewById(R.id.tbx_active);
		time = (TextView) findViewById(R.id.tbx_time);
		tbx_days = (TextView) findViewById(R.id.tbx_rule_days);

		if (MODE == 2) {
			getParametrs();
			if(Rule == 1)
			{
				tgl_vibrate.setChecked(true);
				vRule = 1;
			}
			tgl_active.setChecked(Active == 1 ? true : false);
			btn_addDays.setText(R.string.btn_days_edit);
			btn_addSTime.setText(R.string.btn_Stime_edit);
			btn_addETime.setText(R.string.btn_Etime_edit);
			delete_rule.setVisibility(View.VISIBLE);
			tbxTimeUpdate();
			tbx_days.setText(Days);
		}
		tgl_vibrate
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton vibrate,
							boolean isChecked) {

						vRule = isChecked ? 1 : 0;
					}
				});
		tgl_active
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton silent,
							boolean isChecked) {

						Active = isChecked ? 1 : 0;
					}
				});
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_addSTime:
			showDlgSTime();
			break;
		case R.id.btn_addETime:
			showDlgETime();
			break;
		case R.id.btn_addDays:
			showDlgDays();
			break;
		case R.id.btn_submit:
			if (!mng.CreateOrUdateRule(ruleModel(), this))
				Toast.makeText(this, "Invalid data", Toast.LENGTH_SHORT).show();
			else {
				getApplicationContext().sendBroadcast(new Intent(MainActivity.ACTION_RULELIST_UPDATE));
				Service();
				this.finish();
			}
			break;
		case R.id.btn_delete:
			if (!mng.DeleteRule(ruleModel(), this))
				Toast.makeText(this, "Exeption", Toast.LENGTH_SHORT).show();
			else {
				getApplicationContext().sendBroadcast(new Intent(MainActivity.ACTION_RULELIST_UPDATE));
				Service();
				this.finish();
			}
			break;

		default:
			break;
		}
	}

	public void showDlgDays() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Set days");

		LayoutInflater inflator = LayoutInflater.from(this);
		View content = inflator.inflate(R.layout.activity_add_days, null);

		final ListView lv = (ListView) content.findViewById(R.id.list);
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, days_name));
		if (Days != "" && Days != null) {
			String[] d = Days.split(" ");
			for (int i = 0; i < d.length; i++) {
				if (d[i].equals("Mon"))
					lv.setItemChecked(0, true);
				else if (d[i].equals("Tue"))
					lv.setItemChecked(1, true);
				else if (d[i].equals("Wed"))
					lv.setItemChecked(2, true);
				else if (d[i].equals("Thu"))
					lv.setItemChecked(3, true);
				else if (d[i].equals("Fri"))
					lv.setItemChecked(4, true);
				else if (d[i].equals("Sat"))
					lv.setItemChecked(5, true);
				else if (d[i].equals("Sun"))
					lv.setItemChecked(6, true);
			}
		}
		builder.setView(content).setNegativeButton("Cancel", null);

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				Days = "";
				SparseBooleanArray array = lv.getCheckedItemPositions();
				for (int i = 0; i < 7; i++) {
					if (array.valueAt(i))
						Days += days_name[array.keyAt(i)] + " ";
				}
				tbx_days.setText(Days);
			}
		});

		builder.create().show();
	}

	public void showDlgETime()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Set end time:");

		LayoutInflater inflator = LayoutInflater.from(this);
		View content = inflator.inflate(R.layout.activity_add_time, null);
		
		TimePicker time = (TimePicker) content.findViewById(R.id.timePicker);
		
		time.setIs24HourView(DateFormat.is24HourFormat(this));
		
		time.setCurrentHour(e_hour = E_HOUR);
		time.setCurrentMinute(e_min = E_MIN);
		
		time.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				e_hour = hourOfDay;
				e_min = minute;
			}
		});
		
		builder.setView(content).setNegativeButton("Cancel", null);

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				E_HOUR = e_hour;
				E_MIN = e_min;
				tbxTimeUpdate();
			}
		});

		builder.create().show();
	}
	public void showDlgSTime() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Set start time:");

		LayoutInflater inflator = LayoutInflater.from(this);
		View content = inflator.inflate(R.layout.activity_add_time, null);

		TimePicker time = (TimePicker) content.findViewById(R.id.timePicker);
		
		time.setIs24HourView(DateFormat.is24HourFormat(this));
		
		time.setCurrentHour(s_hour = S_HOUR);
		time.setCurrentMinute(s_min = S_MIN);

		time.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				s_hour = hourOfDay;
				s_min = minute;

			}
		});

		builder.setView(content).setNegativeButton("Cancel", null);

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				S_HOUR = s_hour;
				S_MIN = s_min;
				tbxTimeUpdate();
			}
		});

		builder.create().show();
	}
	
	public void tbxTimeUpdate()
	{
		String s_h = S_HOUR/10 == 0 ? "0" + Integer.toString(S_HOUR) : Integer.toString(S_HOUR);
		String s_m = S_MIN/10 == 0 ? "0" + Integer.toString(S_MIN) : Integer.toString(S_MIN);
		String e_h = E_HOUR/10 == 0 ? "0" + Integer.toString(E_HOUR) : Integer.toString(E_HOUR);
		String e_m = E_MIN/10 == 0 ? "0" + Integer.toString(E_MIN) : Integer.toString(E_MIN);
		time.setText(s_h + ":" + s_m + " - " + e_h + ":" + e_m);
	}

	public void getParametrs() {

		ID = getIntent().getIntExtra("ID", 0);
		S_HOUR = getIntent().getIntExtra("S_TIME", 0) / 60;
		S_MIN = getIntent().getIntExtra("S_TIME", 0) % 60;
		E_HOUR = getIntent().getIntExtra("E_TIME", 0) / 60;
		E_MIN = getIntent().getIntExtra("E_TIME", 0) % 60;
		Rule = getIntent().getIntExtra("Vibrate", 0);
		Days = getIntent().getStringExtra("Days");
		State = getIntent().getIntExtra("State", 0);
		IsRunning = getIntent().getIntExtra("IsRunning", 0);
		Active = getIntent().getIntExtra("Active", 0);
	}

	public RuleModel ruleModel() {
		RuleModel rule = new RuleModel();
		rule.ID = ID;
		rule.StartTime = S_HOUR * 60 + S_MIN;
		rule.EndTime = E_HOUR * 60 + E_MIN;
		rule.Days = Days;
		rule.State = State;
		rule.IsRunning = IsRunning;
		rule.Active = Active;
		rule.Rule = vRule == 1 ? 1: 0;
		return rule;
	}
	
	public void Service() {
		stopService(new Intent(this, MainService.class));
		Log.d("MainActivity", "-- Service is started --");
		Log.d("------", "-------");
		Log.d("------", "-------");
		startService(new Intent(this, MainService.class));
	}
	
	
}
