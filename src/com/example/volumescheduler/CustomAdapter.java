package com.example.volumescheduler;

import java.util.List;
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
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter implements OnClickListener {

	/*********** Declare Used Variables *********/
	private Activity activity;
	private List<RuleModel> data;
	private static LayoutInflater inflater = null;
	RuleModel tempValues = null;
	Manager mng = new Manager();

	/************* CustomAdapter Constructor *****************/
	public CustomAdapter(Activity a, List<RuleModel> d) {

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

	public Object getRule(int position) {
		return data.get(position);
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
		public TextView tbx_rule_vibrate;
		public TextView tbx_rule_silent;
		public CheckBox cbx_rule_active;
	}

	/****** Depends upon data size called for each row , Create each ListView row *****/

	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			convertView = inflater.inflate(R.layout.rule, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.tbx_rule_start_time = (TextView) convertView
					.findViewById(R.id.tbx_time_start);
			holder.tbx_rule_end_time = (TextView) convertView
					.findViewById(R.id.tbx_time_end);
			holder.tbx_rule_days = (TextView) convertView
					.findViewById(R.id.tbx_rule_days);
			holder.cbx_rule_active = (CheckBox) convertView
					.findViewById(R.id.cbx_rule_active);
			holder.tbx_rule_vibrate = (TextView) convertView
					.findViewById(R.id.tbx_ruleVibrate);
			holder.tbx_rule_silent = (TextView) convertView
					.findViewById(R.id.tbx_ruleSilent);

			/************ Set holder with LayoutInflater ************/
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (data.size() <= 0) {
			holder.tbx_rule_days.setText("No Data");
			holder.tbx_rule_start_time.setText("None");
			holder.tbx_rule_end_time.setText("None");
			holder.tbx_rule_vibrate.setText("None");
			holder.tbx_rule_silent.setText("None");
			holder.cbx_rule_active.setChecked(false);
			holder.cbx_rule_active.setEnabled(false);

		} else {
			/***** Get each Model object from Arraylist ********/
			tempValues = null;

			tempValues = (RuleModel) data.get(position);

			/************ Set Model values in Holder elements ***********/
			LogRule(tempValues, position);
			holder.tbx_rule_start_time.setText(tempValues.getStartTimeString());
			holder.tbx_rule_end_time.setText(tempValues.getEndTimeString());
			holder.tbx_rule_days.setText(tempValues.Days);

			if (true)
				if (tempValues.Active == 1) {
					Log.d("Rule", "Checked: " + Integer.toString(position));
					Log.d("------", "-------");
					Log.d("------", "-------");

					holder.cbx_rule_active.setChecked(true);
				}

			switch (tempValues.Rule) {
			case 0:
				holder.tbx_rule_vibrate.setText("On");
				holder.tbx_rule_silent.setText("Off");
				break;
			case 1:
				holder.tbx_rule_vibrate.setText("Off");
				holder.tbx_rule_silent.setText("On");
				break;
			case 2:
				holder.tbx_rule_vibrate.setText("On");
				holder.tbx_rule_silent.setText("On");
				break;
			default:
				break;
			}

			/******** Set Item Click Listner for LayoutInflater for each row *******/

			convertView.setOnClickListener(new OnItemClickListener(position));

			holder.cbx_rule_active
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							Log.d("OnCheckedChanged",
									"Listener: " + Integer.toString(position)
											+ "-- Active: " + tempValues.Active);
							Log.d("------", "-------");
							Log.d("------", "-------");
							RuleModel model = (RuleModel) data.get(position);

							Log.d("OnCheckedChanged",
									"Listener: isActive" + model.Active
											+ "-- Pos: "
											+ Integer.toString(position));
							Log.d("------", "-------");
							Log.d("------", "-------");
							
							if (isChecked) {

								model.Active = 1;
								data.set(position, model);
								Toast.makeText(activity, "Checked",
										Toast.LENGTH_SHORT).show();
								Log.d("RuleChecked",
										"Checked: "
												+ Integer.toString(position));
								Log.d("------", "-------");
								Log.d("------", "-------");
							} else {
								model.Active = 0;
								data.set(position, model);
								Log.d("RuleChecked",
										"UnChecked: "
												+ Integer.toString(position));
								Log.d("------", "-------");
								Log.d("------", "-------");
							}

							mng.CreateOrUdateRule(model, activity);
						}
					});

		}
		return convertView;
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
			sct.onItemClick(mPosition);
		}
	}

	public void LogRule(RuleModel model, int position) {
		String rule = "Rule " + position;
		Log.d(rule, "ID:" + model.ID);
		Log.d(rule, "S_TIME:" + model.StartTime);
		Log.d(rule, "E_TIME:" + model.EndTime);
		Log.d(rule, "Days:" + model.Days);
		Log.d(rule, "Vibrate:" + model.Rule);
		Log.d(rule, "State:" + model.State);
		Log.d(rule, "IsRunning:" + model.IsRunning);
		Log.d(rule, "Active:" + model.Active);
		Log.d("------", "-------");
		Log.d("------", "-------");
	}
}
