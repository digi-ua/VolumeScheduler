package com.example.volumescheduler;

import com.example.volumescheduler.AddTime.onSubmitListener;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddDays extends DialogFragment {

	final String LOG_TAG = "myLogs";

	TimePicker timeS;
	TimePicker timeE;
	boolean[] days = {false, false, false, false, false, false, false};

	onSubmitListener mListener;
	Button mButton;
	CheckBox cbx_mon, cbx_tue, cbx_wed, cbx_thu, cbx_fri, cbx_sat, cbx_sun;
	
	

	interface onSubmitListener {
		void setOnSubmitListener(boolean[] days);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.activity_add_days);

		dialog.show();
		
		mButton = (Button) dialog.findViewById(R.id.btnYes);
		cbx_mon = (CheckBox) dialog.findViewById(R.id.cbx_mon);
		cbx_tue = (CheckBox) dialog.findViewById(R.id.cbx_tue);
		cbx_wed = (CheckBox) dialog.findViewById(R.id.cbx_wed);
		cbx_thu = (CheckBox) dialog.findViewById(R.id.cbx_thu);
		cbx_fri = (CheckBox) dialog.findViewById(R.id.cbx_fri);
		cbx_sat = (CheckBox) dialog.findViewById(R.id.cbx_sat);
		cbx_sun = (CheckBox) dialog.findViewById(R.id.cbx_sun);
		
		cbx_mon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton vibrate,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					days[0] = true;

				} else {
					days[0] = false;;
				}
			}
		});
		cbx_tue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton vibrate,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					days[1] = true;

				} else {
					days[1] = false;;
				}
			}
		});
		cbx_wed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton vibrate,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					days[2] = true;

				} else {
					days[2] = false;;
				}
			}
		});
		cbx_thu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton vibrate,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					days[3] = true;

				} else {
					days[3] = false;;
				}
			}
		});
		cbx_fri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton vibrate,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					days[4] = true;

				} else {
					days[4] = false;;
				}
			}
		});
		cbx_sat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton vibrate,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					days[5] = true;

				} else {
					days[5] = false;;
				}
			}
		});
		cbx_sun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton vibrate,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					days[6] = true;

				} else {
					days[6] = false;;
				}
			}
		});
		
		
		
		// mEditText = (EditText) dialog.findViewById(R.id.editText1);
		// mEditText.setText(text);
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.setOnSubmitListener (days);
				dismiss();
			}

		});
	
		return dialog;

	}
	

}
