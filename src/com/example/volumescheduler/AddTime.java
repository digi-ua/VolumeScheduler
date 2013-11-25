package com.example.volumescheduler;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddTime extends DialogFragment implements OnClickListener {

	final String LOG_TAG = "myLogs";
	
	TimePicker timeS;
	TimePicker timeE;
	
	private int START_HOUR;
	private int START_MIN;
	private int END_HOUR;
	private int END_MIN;
	

	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    getDialog().setTitle("Add Time!");
	    View v = inflater.inflate(R.layout.activity_add_time, null);
	    v.findViewById(R.id.btnYes).setOnClickListener(this);
	    v.findViewById(R.id.btnNo).setOnClickListener(this);
	    v.findViewById(R.id.btnMaybe).setOnClickListener(this);
	    timeS = (TimePicker) v.findViewById(R.id.timePickerS);
	    timeE = (TimePicker) v.findViewById(R.id.timePickerE);

	    timeS.setOnTimeChangedListener(new OnTimeChangedListener(){

	     @Override
	     public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
	      START_HOUR = hourOfDay;
	      START_MIN = minute;
	      
	     }});
	    
	    timeE.setOnTimeChangedListener(new OnTimeChangedListener(){

		     @Override
		     public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		      END_HOUR = hourOfDay;
		      END_MIN = minute;
		      
		     }});

	    return v;
	  }
	  
	  public void onClick(View v) {
	    Log.d(LOG_TAG, "Dialog 1: " + ((Button) v).getText());
	    dismiss();
	  }

	  public void onDismiss(DialogInterface dialog) {
	    super.onDismiss(dialog);
	    Log.d(LOG_TAG, "Dialog 1: onDismiss");
	  }

	  public void onCancel(DialogInterface dialog) {
	    super.onCancel(dialog);
	    Log.d(LOG_TAG, "Dialog 1: onCancel");
	  }

	
}
