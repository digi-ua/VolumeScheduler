package com.example.volumescheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class broRecAutoRun extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("Autorun: ", "onReceive " + intent.getAction());
	    context.startService(new Intent(context, MainService.class));
	}

}
