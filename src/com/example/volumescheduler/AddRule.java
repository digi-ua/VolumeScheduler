package com.example.volumescheduler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddRule extends Activity {
	final String LOG_TAG = "myLogs";
	DialogFragment dlg;
	ToggleButton vibrate;
	TextView tgl;

	private int START_HOUR;
	private int START_MIN;
	private int END_HOUR;
	private int END_MIN;

	private int T_DAY;
	private int T_RULE;
	private int T_ENABLE;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_rule);

		dlg = new AddTime();
		vibrate = (ToggleButton) findViewById(R.id.tgl_vibrate);
		tgl = (TextView) findViewById(R.id.tbx_tgl);
		tgl.setText("Unchecked");
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
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_addTime:
			dlg.show(getFragmentManager(), "dlg1");
			break;
		default:
			break;
		}
	}

}
