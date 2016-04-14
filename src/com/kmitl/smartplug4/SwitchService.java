package com.kmitl.smartplug4;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class SwitchService extends android.app.Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		registerReceiver(new Receiver(), new IntentFilter(Intent.ACTION_TIME_TICK));
	}
	
}
