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
import android.widget.TextView;

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

	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;

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

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		if (data.size() <= 0) {
			holder.tbx_rule_days.setText("No Data");
			holder.tbx_rule_start_time.setText("None");
			holder.tbx_rule_end_time.setText("None");

		} else {
			/***** Get each Model object from Arraylist ********/
			tempValues = null;
			tempValues = (RuleModel) data.get(position);

			/************ Set Model values in Holder elements ***********/
			
			holder.tbx_rule_start_time.setText( tempValues.getStartTimeString() );
			holder.tbx_rule_end_time.setText( tempValues.getEndTimeString() );
			holder.tbx_rule_days.setText(tempValues.getDays());

			/******** Set Item Click Listner for LayoutInflater for each row *******/

			vi.setOnClickListener(new OnItemClickListener(position));
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
