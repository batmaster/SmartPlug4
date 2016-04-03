package com.kmitl.smartplug4;

import java.util.ArrayList;
import java.util.Date;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			context.startService(new Intent(context, SwitchService.class));
		}
		else if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
			checkGPS(context, false);
			
            context.sendBroadcast(new Intent("com.kmitl.smartplug.refreshSwitch"));
            context.sendBroadcast(new Intent("com.kmitl.smartplug.refreshAlarm"));
		}
	}
	
	public final static double AVERAGE_RADIUS_OF_EARTH = 6371000;
	public int calculateDistance(double userLat, double userLng,
	  double venueLat, double venueLng) {

	    double latDistance = Math.toRadians(userLat - venueLat);
	    double lngDistance = Math.toRadians(userLng - venueLng);

	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	      + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
	      * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	    return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH * c));
	}
	
	private void checkGPS(Context context, final boolean forceClose) {
		GPSTracker gpsTracker = new GPSTracker(context);
		if (gpsTracker.canGetLocation()) {
			boolean enable = SharedValues.getEnableLocation(context);
			double offRange = SharedValues.getOffRange(context);
			double lat = SharedValues.getLat(context);
			double lng = SharedValues.getLng(context);
			
			Log.d("locationn", "setting is: " + enable + " offRange: " + offRange + " lat: " + lat + " lng: " + lng);
			if (enable && offRange != 0 && lat != 0 && lng != 0) {
				double distance = calculateDistance(lat, lng, gpsTracker.getLatitude(), gpsTracker.getLongitude());
				Log.d("locationn", "getting lat: " + gpsTracker.getLatitude() + " lng: " + gpsTracker.getLongitude());
				Log.d("locationn", "getting distance: " + distance);
				
				if (distance >= offRange) {
					Log.d("locationn", "distance >= offRange");
					SwitchCloseTask task = new SwitchCloseTask(context);
					task.execute();
				}
			}	
		}
	}
	
	private class SwitchCloseTask extends AsyncTask<Void, Void, String> {
		
		private Context context;
		private String switchPin;
		
		public SwitchCloseTask(Context context) {
			this.context = context;
			this.switchPin = String.valueOf(10);
		}
		
		@Override
		protected void onPreExecute() {
			Log.d("p", "SwitchTask: Pre");
		}

		@Override
		protected String doInBackground(Void... params) {
			return Service.sendHttpRequest(context, switchPin, Service.SOCKET_TIMEOUT_TRYING);
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d("p", "SwitchTask: Post");
			Toast.makeText(context, "Close switch remotely!", Toast.LENGTH_SHORT).show();
		}
	}
	
}