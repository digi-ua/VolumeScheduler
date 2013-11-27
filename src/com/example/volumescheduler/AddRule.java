package com.example.volumescheduler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.ToggleButton;

public class AddRule extends Activity {
	final String LOG_TAG = "myLogs";

	int MODE; // 1 - add, 2 - edit, ...

	ListView list;
	ToggleButton vibrate;
	Button add_rule;
	Button btn_addTime;
	Button btn_addDays;
	TextView tgl;
	TextView time;
	TextView tbx_days;

	private int ID;
	private int S_HOUR = 0;
	private int S_MIN = 0;
	private int E_HOUR = 0;
	private int E_MIN = 0;
	private int Vibrate;
	private String Days = "";
	private int s_hour, s_min, e_hour, e_min;
	
	private String[] days_name = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_rule);

		
		MODE = getIntent().getIntExtra("MODE", 1);

		btn_addDays = (Button) findViewById(R.id.btn_addDays);
		btn_addTime = (Button) findViewById(R.id.btn_addTime);
		add_rule = (Button) findViewById(R.id.btn_add);
		vibrate = (ToggleButton) findViewById(R.id.tgl_vibrate);
		tgl = (TextView) findViewById(R.id.tbx_vibrate);
		time = (TextView) findViewById(R.id.tbx_time);
		tbx_days = (TextView) findViewById(R.id.tbx_rule_days);

		if (MODE == 2) {
			getParametrs();
			if (Vibrate == 1)
				tgl.setText("Checked");
			else
				tgl.setText("Unchecked");

			btn_addDays.setText(R.string.btn_days_edit);
			btn_addTime.setText(R.string.btn_time_edit);

			time.setText(S_HOUR + ":" + S_MIN + " - " + E_HOUR + ":" + E_MIN);
			tbx_days.setText(Days);
		}
		vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton vibrate,
					boolean isChecked) {

				if (isChecked) {
					tgl.setText("Checked");

				} else {
					tgl.setText("Unckecked");
				}
			}
		});
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_addTime:
			showDlgTime();
			break;
		case R.id.btn_addDays:
			showDlgDays();
			break;
		case R.id.btn_submit:
			Intent intent = new Intent(v.getContext(), MainActivity.class);

			/******************
			 * Тут будемо викликати перевірку на накладання правил і якщо усе
			 * ГУД, то створюємо правило. Його в БД, сюда АЙДІШКУ нову і СТАН
			 * 
			 * Поправка: Тіки в БД! На майнАктівіті - рефреш.
			 * 
			 * Далі виводимо його в лістВЮ
			 ******************/
			int id = 0;
			int active = 1;
			intent.putExtra("ID", id);
			intent.putExtra("S_HOUR", S_HOUR);
			intent.putExtra("S_MIN", S_MIN);
			intent.putExtra("E_HOUR", E_HOUR);
			intent.putExtra("E_MIN", E_MIN);
			intent.putExtra("Vibrate", Vibrate);
			intent.putExtra("Active", active);
			intent.putExtra("Days", Days);

			// показываем новое Activity
			startActivity(intent);
			
			break;

		default:
			break;
		}
	}

	public void showDlgDays(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Set days");

		LayoutInflater inflator = LayoutInflater.from(this);
		View content = inflator.inflate(R.layout.activity_add_days, null);

		final ListView lv = (ListView) content.findViewById(R.id.list);
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice,
				days_name));
		if(Days != "" && Days != null)
		{
			String[] d = Days.split(" ");
			for (int i = 0; i < d.length; i ++) {
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

	    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int whichButton)
	        {
	        	Days = "";
	        	SparseBooleanArray array =  lv.getCheckedItemPositions();
	    		//int count = lv.getCheckedItemCount();
	    		
	    		for(int i = 0; i < 7; i ++)
	    		{
	    			boolean val = array.valueAt(i);
	    			if(array.valueAt(i))
	    				Days += days_name[array.keyAt(i)] + " ";
	    		}
	    		String str = Days;
	    		tbx_days.setText(Days);
	        }
	    });
	    
	    builder.create().show();
	    }

	public void showDlgTime(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Set time:");

		LayoutInflater inflator = LayoutInflater.from(this);
		View content = inflator.inflate(R.layout.activity_add_time, null);
		
		TimePicker timeS = (TimePicker) content.findViewById(R.id.timePickerS);
		TimePicker timeE = (TimePicker) content.findViewById(R.id.timePickerE);
		
		timeS.setCurrentHour(S_HOUR);
		timeS.setCurrentMinute(S_MIN);
		
		timeE.setCurrentHour(E_HOUR);
		timeE.setCurrentMinute(E_MIN);
		
		timeS.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				s_hour = hourOfDay;
				s_min = minute;

			}
		});
		
		timeE.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				e_hour = hourOfDay;
				e_min = minute;

			}
		});
		
		builder.setView(content).setNegativeButton("Cancel", null);

	    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int whichButton)
	        {
	        	S_HOUR = s_hour;
	        	S_MIN = s_min;
	        	E_HOUR = e_hour;
	        	E_MIN = e_min;
	        	time.setText(s_hour + ":" + s_min + " - " + e_hour + ":" + e_min);
	        }
	    });
	    
	    builder.create().show();
	    }

	public void getParametrs() {
		ID = getIntent().getIntExtra("ID", 0);
		S_HOUR = getIntent().getIntExtra("S_TIME", 0) / 60;
		S_MIN = getIntent().getIntExtra("S_TIME", 0) % 60;
		E_HOUR = getIntent().getIntExtra("E_TIME", 0) / 60;
		E_MIN = getIntent().getIntExtra("E_TIME", 0) % 60;
		Vibrate = getIntent().getIntExtra("Vibrate", 0);
		Days = getIntent().getStringExtra("Days");
	}
}
