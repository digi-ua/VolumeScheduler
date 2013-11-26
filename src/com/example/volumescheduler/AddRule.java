package com.example.volumescheduler;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import com.example.volumescheduler.AddTime.onSubmitListener;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddRule extends Activity implements onSubmitListener,
		AddDays.onSubmitListener {
	final String LOG_TAG = "myLogs";

	int MODE; // 1 - add, 2 - edit, ...

	ToggleButton vibrate;
	Button add_rule;
	Button btn_addTime;
	Button btn_addDays;
	TextView tgl;
	TextView time;
	TextView tbx_days;

	private int ID;
	private int S_HOUR;
	private int S_MIN;
	private int E_HOUR;
	private int E_MIN;
	private int Vibrate;
	private String Days = "";

	private Map<String, Boolean> days_ = new HashMap<String, Boolean>();
	private boolean[] days = { false, false, false, false, false, false, false };
	private String[] days_name = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
			"Sun" };

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_rule);

		// Intent intent = new Intent(this, MainActivity.class);
		MODE = getIntent().getIntExtra("MODE", 1);

		btn_addDays = (Button) findViewById(R.id.btn_addDays);
		btn_addTime = (Button) findViewById(R.id.btn_addTime);
		add_rule = (Button) findViewById(R.id.btn_add);
		vibrate = (ToggleButton) findViewById(R.id.tgl_vibrate);
		tgl = (TextView) findViewById(R.id.tbx_tgl);
		time = (TextView) findViewById(R.id.tbx_time);
		tbx_days = (TextView) findViewById(R.id.tbx_rule_days);

		if (MODE == 2) {
			getParametrs();
			if(Vibrate == 1)
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
			AddTime add_time_dlg = new AddTime();
			add_time_dlg.mListener = AddRule.this;
			add_time_dlg.show(getFragmentManager(), "add_time_dlg");
			break;
		case R.id.btn_addDays:
			AddDays add_days_dlg = new AddDays();
			add_days_dlg.mListener = AddRule.this;
			add_days_dlg.show(getFragmentManager(), "add_days_dlg");
			break;
		case R.id.btnYes:
			Intent intent = new Intent(this, MainActivity.class);

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

	public void getParametrs() {
		ID = getIntent().getIntExtra("ID", 0);
		S_HOUR = getIntent().getIntExtra("S_TIME", 0) / 60;
		S_MIN = getIntent().getIntExtra("S_TIME", 0) % 60;
		E_HOUR = getIntent().getIntExtra("E_TIME", 0) / 60;
		S_MIN = getIntent().getIntExtra("E_TIME", 0) % 60;
		Vibrate = getIntent().getIntExtra("Vibrate", 0);
		Days = getIntent().getStringExtra("Days");
	}

	public void setOnSubmitListener(int s_hour, int s_min, int e_hour, int e_min) {
		S_HOUR = s_hour;
		S_MIN = s_min;
		E_HOUR = e_hour;
		E_MIN = e_min;
		time.setText(s_hour + ":" + s_min + " - " + e_hour + ":" + e_min);
	}

	public void setOnSubmitListener(boolean[] days) {
		// this.days = days;

		for (int i = 0; i < days.length; i++) {
			days_.put(days_name[i], days[i]);
		}
		Days = "";
		for (int i = 0; i < days_.size(); i++) {
			if (days_.get(days_name[i]))
				Days += days_name[i] + " ";
		}
		tbx_days.setText(Days);
	}

}
