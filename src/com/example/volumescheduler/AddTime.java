package com.example.volumescheduler;

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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddTime extends DialogFragment { // implements OnClickListener {

	final String LOG_TAG = "myLogs";

	TimePicker timeS;
	TimePicker timeE;

	onSubmitListener mListener;
	Button mButton;

	private int S_HOUR;
	private int S_MIN;
	private int E_HOUR;
	private int E_MIN;

	interface onSubmitListener {
		void setOnSubmitListener(int s_hour, int s_min, int e_hour, int e_min);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.activity_add_time);

		dialog.show();
		mButton = (Button) dialog.findViewById(R.id.btnYes);

		timeS = (TimePicker) dialog.findViewById(R.id.timePickerS);
		timeE = (TimePicker) dialog.findViewById(R.id.timePickerE);

		timeS.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				S_HOUR = hourOfDay;
				S_MIN = minute;

			}
		});

		timeE.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				E_HOUR = hourOfDay;
				E_MIN = minute;

			}
		});

		// mEditText = (EditText) dialog.findViewById(R.id.editText1);
		// mEditText.setText(text);
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.setOnSubmitListener(S_HOUR, S_MIN, E_HOUR, E_MIN);
				dismiss();
			}

		});

		return dialog;

		/*
		 * public View onCreateView(LayoutInflater inflater, ViewGroup
		 * container, Bundle savedInstanceState) {
		 * getDialog().setTitle("Add Time!"); View v =
		 * inflater.inflate(R.layout.activity_add_time, null);
		 * v.findViewById(R.id.btnYes).setOnClickListener(this);
		 * v.findViewById(R.id.btnNo).setOnClickListener(this);
		 * v.findViewById(R.id.btnMaybe).setOnClickListener(this); timeS =
		 * (TimePicker) v.findViewById(R.id.timePickerS); timeE = (TimePicker)
		 * v.findViewById(R.id.timePickerE);
		 * 
		 * timeS.setOnTimeChangedListener(new OnTimeChangedListener(){
		 * 
		 * @Override public void onTimeChanged(TimePicker view, int hourOfDay,
		 * int minute) { START_HOUR = hourOfDay; START_MIN = minute;
		 * 
		 * }});
		 * 
		 * 
		 * timeE.setOnTimeChangedListener(new OnTimeChangedListener(){
		 * 
		 * @Override public void onTimeChanged(TimePicker view, int hourOfDay,
		 * int minute) { END_HOUR = hourOfDay; END_MIN = minute;
		 * 
		 * }});
		 * 
		 * return v;
		 */
	}
	/*
	 * public void onClick(View v) { Log.d(LOG_TAG, "Dialog 1: " + ((Button)
	 * v).getText()); dismiss(); }
	 * 
	 * public void onDismiss(DialogInterface dialog) { super.onDismiss(dialog);
	 * Log.d(LOG_TAG, "Dialog 1: onDismiss"); }
	 * 
	 * public void onCancel(DialogInterface dialog) { super.onCancel(dialog);
	 * Log.d(LOG_TAG, "Dialog 1: onCancel"); }
	 */

}
