package com.example.volumescheduler;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter implements OnClickListener {

	/*********** Declare Used Variables *********/
	private Activity activity;
	private ArrayList data;
	private static LayoutInflater inflater = null;
	RuleModel tempValues = null;
	int i = 0;

	/************* CustomAdapter Constructor *****************/
	public CustomAdapter(Activity a, ArrayList d) {

		/********** Take passed values **********/
		activity = a;
		data = d;

		/*********** Layout inflator to call external xml layout () ***********/
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/******** What is the size of Passed Arraylist Size ************/
	public int getCount() {

		if (data.size() <= 0)
			return 1;
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder {

		public TextView tbx_rule_start_time;
		public TextView tbx_rule_end_time;
		public TextView tbx_rule_days;
		public CheckBox cbx_rule_active;
	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		final ViewHolder holder;

		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.rule, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.tbx_rule_start_time = (TextView) vi
					.findViewById(R.id.tbx_time_start);
			holder.tbx_rule_end_time = (TextView) vi
					.findViewById(R.id.tbx_time_end);
			holder.tbx_rule_days = (TextView) vi
					.findViewById(R.id.tbx_rule_days);
			holder.cbx_rule_active = (CheckBox) vi
					.findViewById(R.id.cbx_rule_active);
			
			

			
			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
			holder.cbx_rule_active.setTag(tempValues);

		} else
		{
			//holder = (ViewHolder) vi.getTag();
			vi = convertView;
			((ViewHolder) vi.getTag()).cbx_rule_active.setTag(tempValues);
		}
		
		ViewHolder holder1 = (ViewHolder) vi.getTag();
		if (data.size() <= 0) {
			holder1.tbx_rule_days.setText("No Data");
			holder1.tbx_rule_start_time.setText("None");
			holder1.tbx_rule_end_time.setText("None");
			holder1.cbx_rule_active.setChecked(false);

		} else {
			/***** Get each Model object from Arraylist ********/
			tempValues = null;
			tempValues = (RuleModel) data.get(position);

			/************ Set Model values in Holder elements ***********/

			holder1.tbx_rule_start_time.setText(tempValues.getStartTimeString());
			holder1.tbx_rule_end_time.setText(tempValues.getEndTimeString());
			holder1.tbx_rule_days.setText(tempValues.Days);
			if (tempValues.Active == 1)
				holder1.cbx_rule_active.setChecked(true);

			/******** Set Item Click Listner for LayoutInflater for each row *******/

			vi.setOnClickListener(new OnItemClickListener(position));
			
			holder1.cbx_rule_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

	            @Override
	            public void onCheckedChanged(CompoundButton buttonView,
	                boolean isChecked) {
	              if(isChecked)
	              {
	            	  tempValues.Active = 1;
	            	  Toast.makeText(activity, "Checked", Toast.LENGTH_SHORT).show();
	              }
	              else tempValues.Active = 0;

	            }
	          });
		}
		return vi;
	}

	@Override
	public void onClick(View v) {
		Log.v("CustomAdapter", "=====Row button clicked=====");
	}

	/********* Called when Item click in ListView ************/
	private class OnItemClickListener implements OnClickListener {
		private int mPosition;

		OnItemClickListener(int position) {
			mPosition = position;
		}

		@Override
		public void onClick(View arg0) {

			MainActivity sct = (MainActivity) activity;

			/****
			 * Call onItemClick Method inside CustomListViewAndroidExample Class
			 * ( See Below )
			 ****/

			sct.onItemClick(mPosition);
		}
	}
}
