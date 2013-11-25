package com.example.volumescheduler;

import com.example.volumescheduler.AddTime.onSubmitListener;

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

public class AddRule extends Activity implements onSubmitListener {
	final String LOG_TAG = "myLogs";
	AddTime dlg;
	ToggleButton vibrate;
	Button add_rule;
	TextView tgl;
	TextView time;

	private int S_HOUR;
	private int S_MIN;
	private int E_HOUR;
	private int E_MIN;

	private int T_DAY;
	private int T_RULE;
	private int T_ENABLE;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_rule);

		add_rule = (Button) findViewById(R.id.btn_add);
		vibrate = (ToggleButton) findViewById(R.id.tgl_vibrate);
		tgl = (TextView) findViewById(R.id.tbx_tgl);
		tgl.setText("Unchecked");
		
		time = (TextView) findViewById(R.id.tbx_time);
		time.setText("Nothing");

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
			dlg = new AddTime();
			dlg.mListener = AddRule.this;
			dlg.show(getFragmentManager(), "dlg1");
			break;
		default:
			break;
		}
	}
	

	@Override
	public void setOnSubmitListener(int s_hour, int s_min, int e_hour, int e_min) {
		S_HOUR = s_hour;
		S_MIN = s_min;
		E_HOUR = e_hour;
		E_MIN = e_min;
		time.setText(s_hour + ":" + s_min + " - " + e_hour + ":" + e_min);
	}

}
