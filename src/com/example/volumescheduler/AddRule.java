package com.example.volumescheduler;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import com.example.volumescheduler.AddTime.onSubmitListener;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddRule extends Activity implements onSubmitListener, com.example.volumescheduler.AddDays.onSubmitListener {
	final String LOG_TAG = "myLogs";
	
	ToggleButton vibrate;
	Button add_rule;
	Button btn_addDays;
	TextView tgl;
	TextView time;
	TextView tbx_days;

	private int S_HOUR;
	private int S_MIN;
	private int E_HOUR;
	private int E_MIN;
	private Map<String, Boolean> days_ = new HashMap<String, Boolean>();
	private boolean[] days = {false, false, false, false, false, false, false};
	private String[] days_name = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

	private int T_DAY;
	private int T_RULE;
	private int T_ENABLE;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_rule);

		btn_addDays = (Button) findViewById(R.id.btn_addDays);
		add_rule = (Button) findViewById(R.id.btn_add);
		vibrate = (ToggleButton) findViewById(R.id.tgl_vibrate);
		tgl = (TextView) findViewById(R.id.tbx_tgl);
		tgl.setText("Unchecked");
		
		time = (TextView) findViewById(R.id.tbx_time);
		time.setText("Nothing");
		
		tbx_days = (TextView) findViewById(R.id.tbx_rule_days);
		tbx_days.setText("Nothing");

		vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton vibrate,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					tgl.setText("Checked");

				} else {
					tgl.setText("Unckecked");
				}
			}
		});
		/*
		add_rule.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg = new AddTime();
				dlg.mListener = AddRule.this;
				dlg.show(getFragmentManager(), "dlg1");
			}
		});
		*/
	}

	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_addTime:
			AddTime add_time_dlg = new AddTime();
			add_time_dlg.mListener = AddRule.this;
			add_time_dlg.show(getFragmentManager(), "add_time_dlg");
			break;
		case R.id.btn_addDays:
			AddDays add_days_dlg = new AddDays();
			add_days_dlg.mListener = AddRule.this;
			add_days_dlg.show(getFragmentManager(), "add_days_dlg");
		default:
			break;
		}
	}
	

	
	public void setOnSubmitListener(int s_hour, int s_min, int e_hour, int e_min) {
		S_HOUR = s_hour;
		S_MIN = s_min;
		E_HOUR = e_hour;
		E_MIN = e_min;
		time.setText(s_hour + ":" + s_min + " - " + e_hour + ":" + e_min);
	}
	
	
	public void setOnSubmitListener(boolean[] days) {
		//this.days = days;
		
		for (int i = 0; i < days.length; i ++)
		{
			days_.put(days_name[i], days[i]);
		}
		String str = "";
		for (int i = 0; i < days_.size(); i ++)
		{
			if(days_.get(days_name[i]))
				str += days_name[i] + " ";
		}
		tbx_days.setText(str);
	}

}
