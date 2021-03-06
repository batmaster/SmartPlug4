package com.kmitl.smartplug4;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.kmitl.smartplug4.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SwitchActivity extends Activity {
	
	private static final int REQUEST_LOCATION_SETTING_FORCE_CLOSE = 12889;
    private static final int REQUEST_MOCK_SETTING_FORCE_CLOSE = 12890;
    private static final int REQUEST_LOCATION_SETTING = 12891;
    private static final int REQUEST_MOCK_SETTING = 12892;
    
    private ListView drawer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private static final int[] imageRids = {
    		R.drawable.locate,
    		R.drawable.alarm,
    		R.drawable.wifi,
    		R.drawable.costa
    };
 
    private DrawerAdapter adapter;
	
	private TextView textView0;
	private ImageView imageViewSwitch1;
	private ImageView imageViewBulb1;
	private ImageView imageViewSwitch2;
	private ImageView imageViewBulb2;
	private ImageView imageViewSwitch3;
	private ImageView imageViewBulb3;
	private ImageView imageViewSwitch4;
	private ImageView imageViewBulb4;
	
	public static SwitchActivity activity;
	private GPSTracker gpsTracker;
	
	private BroadcastReceiver refreshSwitch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_switch);
		
		refreshSwitch = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				CheckStateTask task = new CheckStateTask(getApplicationContext(), false);
		    	task.execute();
			}
		};
		
		registerReceiver(refreshSwitch, new IntentFilter("com.kmitl.smartplug.refreshSwitch"));
		
		gpsTracker = new GPSTracker(getApplicationContext());
		
		activity = SwitchActivity.this;
		
		textView0 = (TextView) findViewById(R.id.textView0);
		textView0.setText((SharedValues.getModePref(getApplicationContext()).equals("direct") ? "Direct Mode" : "Global Mode") + " ON/OFF");
		
		imageViewSwitch1 = (ImageView) findViewById(R.id.imageViewSwitch1);
		imageViewSwitch1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (ready) {
					SwitchTask task = new SwitchTask(getApplicationContext(), 10);
					task.execute();
				}
				else {
					Toast.makeText(getApplicationContext(), "Sending in progress, please wait and try again.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		imageViewBulb1 = (ImageView) findViewById(R.id.imageViewBulb1);
		
		imageViewSwitch2 = (ImageView) findViewById(R.id.imageViewSwitch2);
		imageViewSwitch2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (ready) {
					SwitchTask task = new SwitchTask(getApplicationContext(), 11);
					task.execute();
				}
				else {
					Toast.makeText(getApplicationContext(), "Sending in progress, please wait and try again.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		imageViewBulb2 = (ImageView) findViewById(R.id.imageViewBulb2);
		
		imageViewSwitch3 = (ImageView) findViewById(R.id.imageViewSwitch3);
		imageViewSwitch3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (ready) {
					SwitchTask task = new SwitchTask(getApplicationContext(), 12);
					task.execute();
				}
				else {
					Toast.makeText(getApplicationContext(), "Sending in progress, please wait and try again.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		imageViewBulb3 = (ImageView) findViewById(R.id.imageViewBulb3);
		
		imageViewSwitch4 = (ImageView) findViewById(R.id.imageViewSwitch4);
		imageViewSwitch4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (ready) {
					SwitchTask task = new SwitchTask(getApplicationContext(), 13);
					task.execute();
				}
				else {
					Toast.makeText(getApplicationContext(), "Sending in progress, please wait and try again.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		imageViewBulb4 = (ImageView) findViewById(R.id.imageViewBulb4);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer = (ListView) findViewById(R.id.list_slidermenu);
        adapter = new DrawerAdapter(getApplicationContext(), imageRids, SharedValues.getModePref(getApplicationContext()).equals("global") ? View.VISIBLE : View.GONE);
        drawer.setAdapter(adapter);
        drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(drawer);
                switch (position) {
	                case 0:
	                	final Dialog dialog = new Dialog(SwitchActivity.this);
	                    //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	                    dialog.setContentView(R.layout.custom_dialog_location);
	                    dialog.setCancelable(true);
	                    
	                    final Switch switchEnable = (Switch) dialog.findViewById(R.id.switchEnable);
	                    switchEnable.setChecked(SharedValues.getEnableLocation(getApplicationContext()));
	                    
	                    Button buttonSetCenter = (Button) dialog.findViewById(R.id.buttonSetCenter);
	                    buttonSetCenter.setOnClickListener(new OnClickListener() {
	        				
	        				@Override
	        				public void onClick(View v) {
	        					checkGPS(false);
	        				}
	        			});
	                    
	                    final EditText editTextRange = (EditText) dialog.findViewById(R.id.editTextRange);
	                    editTextRange.setText(String.valueOf(SharedValues.getOffRange(getApplicationContext())));
	                    
	                    Button buttonCheck = (Button) dialog.findViewById(R.id.buttonCheck);
	                    buttonCheck.setOnClickListener(new OnClickListener() {
	        				
	        				@Override
	        				public void onClick(View v) {
	        					double lat = SharedValues.getLat(getApplicationContext());
	        					double lng = SharedValues.getLng(getApplicationContext());
	        					if (lat != 0 && lng != 0) {
	        						Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + lat + "," + lng));
	        						intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
	        						startActivity(intent);
	        					}
	        					else {
	        						Toast.makeText(getApplicationContext(), "ยังไม่ได้ตั้งค่า", Toast.LENGTH_SHORT).show();
	        					}
	        				}
	        			});
	                    
	                    Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
	                    buttonCancel.setOnClickListener(new OnClickListener() {
	                        public void onClick(View v) {
	                        	
	                            dialog.cancel();
	                            
	                        }
	                    });
	                    
	                    Button buttonSet = (Button) dialog.findViewById(R.id.buttonSet);
	                    buttonSet.setOnClickListener(new OnClickListener() {
	                        public void onClick(View v) {
	                        	SharedValues.setEnableLocation(getApplicationContext(), switchEnable.isChecked());
	                        	SharedValues.setOffRange(getApplicationContext(), Double.parseDouble(editTextRange.getText().toString()));
	                        	Toast.makeText(getApplicationContext(), "Enable: " + SharedValues.getEnableLocation(getApplicationContext()) + " Range: " + SharedValues.getOffRange(getApplicationContext()), Toast.LENGTH_SHORT).show();
	                        	dialog.dismiss();
	                        }
	                    });
	
	                    dialog.show();
	                    break;
	                case 1:
	                	Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
	        			startActivity(intent);
	                    break;
	                case 2:
	                	AskWifiSSID task = new AskWifiSSID(getApplicationContext());
	        			task.execute();
	        			break;
	                case 3:
	                	GetITask task2 = new GetITask(getApplicationContext());
	        			task2.execute();
	        			break;
                }
            }

        });
        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
		
		refreshStatus(getIntent().getStringExtra("the8Digits"), false);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(refreshSwitch);
		super.onDestroy();
	}

	private void refreshStatus(String the8Digits, boolean isShowDialog) {
			imageViewSwitch1.setImageResource(the8Digits.charAt(4) == '0' ? R.drawable.switch_off : R.drawable.switch_on);
			imageViewBulb1.setImageResource(the8Digits.charAt(0) == '0' ? R.drawable.bulb_off : R.drawable.bulb_on);
			
			imageViewSwitch2.setImageResource(the8Digits.charAt(5) == '0' ? R.drawable.switch_off : R.drawable.switch_on);
			imageViewBulb2.setImageResource(the8Digits.charAt(1) == '0' ? R.drawable.bulb_off : R.drawable.bulb_on);
			
			imageViewSwitch3.setImageResource(the8Digits.charAt(6) == '0' ? R.drawable.switch_off : R.drawable.switch_on);
			imageViewBulb3.setImageResource(the8Digits.charAt(2) == '0' ? R.drawable.bulb_off : R.drawable.bulb_on);
			
			imageViewSwitch4.setImageResource(the8Digits.charAt(7) == '0' ? R.drawable.switch_off : R.drawable.switch_on);
			imageViewBulb4.setImageResource(the8Digits.charAt(3) == '0' ? R.drawable.bulb_off : R.drawable.bulb_on);
			
			if (isShowDialog) {
				if (the8Digits.charAt(0) != the8Digits.charAt(1)) {
					SharedValues.showDialog(SwitchActivity.this, "Appliance has problem!");
				}
			}
	}
	
	private boolean ready = true;
	private class CheckStateTask extends AsyncTask<Void, Void, String> {
		
		private Context context;
		private boolean showDialog;
		private ProgressDialog dialog;
		
		public CheckStateTask(Context context, Boolean showDialog) {
			this.context = context;
			this.showDialog = showDialog;
			
			dialog = new ProgressDialog(SwitchActivity.this);
			dialog.setCancelable(false);
		}
		
		@Override
		protected void onPreExecute() {
			Log.d("p", "CheckStateTask: Pre");
			ready = false;
			
			dialog.setMessage("Checking state...");
			if (!dialog.isShowing() && showDialog) {
				dialog.show();
            }
		}

		@Override
		protected String doInBackground(Void... params) {
			Log.d("p", "CheckStateTask: In");
			return Service.sendHttpRequest(context, "4", Service.SOCKET_TIMEOUT_TRYING);
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d("p", "CheckStateTask: Post");
			if (dialog.isShowing()) {
				dialog.dismiss();
            }
			
			if (result.length() == 8) {
				refreshStatus(result, showDialog);
			}
			else {
				AlertDialog d;
				AlertDialog.Builder alert = new AlertDialog.Builder(SwitchActivity.this);
				if (result.equals("ConnectTimeoutException"))
					alert.setMessage("ติดต่อบอร์ดไม่ได้");
				else if (result.equals("SocketTimeoutException"))
					alert.setMessage("ติดต่อบอร์ดไม่ได้");
				else
					alert.setMessage("Error: " + result);
				alert.setCancelable(true);
				d = alert.create();
				d.setCanceledOnTouchOutside(true);
				d.show();
			}
			ready = true;
		}
	}
	
	private class SwitchTask extends AsyncTask<Void, Void, String> {
		
		private Context context;
		private String switchPin;
		private int switchIndex;
		private ProgressDialog dialog;
		
		public SwitchTask(Context context, int pin) {
			this.context = context;
			this.switchPin = String.valueOf(pin);
			this.switchIndex = 1;
			
			dialog = new ProgressDialog(SwitchActivity.this);
			dialog.setCancelable(false);
		}
		
		@Override
		protected void onPreExecute() {
			Log.d("p", "SwitchTask: Pre");
			ready = false;
			
			dialog.setMessage("Sending command...");
			if (!dialog.isShowing()) {
				dialog.show();
            }
		}

		@Override
		protected String doInBackground(Void... params) {
			Log.d("p", "SwitchTask: In");
			return Service.sendHttpRequest(context, switchPin, Service.SOCKET_TIMEOUT_TRYING);
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d("p", "SwitchTask: Post");
			if (dialog.isShowing()) {
				dialog.dismiss();
            }
			
			if (result.length() == 8) {
				refreshStatus(result, false);
			}
			else {
				AlertDialog d;
				AlertDialog.Builder alert = new AlertDialog.Builder(SwitchActivity.this);
				if (result.equals("ConnectTimeoutException"))
					alert.setMessage("ติดต่อบอร์ดไม่ได้");
				else if (result.equals("SocketTimeoutException"))
					alert.setMessage("ติดต่อบอร์ดไม่ได้");
				else
					alert.setMessage("Error: " + result);
				alert.setCancelable(true);
				d = alert.create();
				d.setCanceledOnTouchOutside(true);
				d.show();
			}
			ready = true;
		}
	}
	
	private class AskWifiSSID extends AsyncTask<Void, Void, String> {
		
		private Context context;
		private String ssid;
		private String password;
		private Dialog outerDialog;
		
		private ProgressDialog dialog;
		
		public AskWifiSSID(Context context) {
			this.context = context;
			
			dialog = new ProgressDialog(SwitchActivity.this);
			dialog.setCancelable(true);
		}
		
		@Override
		protected void onPreExecute() {
			dialog.setMessage("Trying to connect...");
			
			if (!dialog.isShowing()) {
				dialog.show();
            }
		}

		@Override
		protected String doInBackground(Void... params) {
			return Service.sendHttpRequest(context, "7", Service.SOCKET_TIMEOUT_TRYING);
		}

		@Override
		protected void onPostExecute(String result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
            }
			String[] ssids = result.split(",");
			
			final Dialog dialog = new Dialog(SwitchActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_dialog_wifi);
            dialog.setCancelable(true);
            
            final Spinner spinnerSsid = (Spinner) dialog.findViewById(R.id.spinnerSsid);
            
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SwitchActivity.this, android.R.layout.simple_spinner_dropdown_item, ssids);
            spinnerSsid.setAdapter(adapter);
            
            final EditText editTextPassword = (EditText) dialog.findViewById(R.id.editTextPassword);
            
            Button buttonCancel = (Button)dialog.findViewById(R.id.buttonCancel);
            buttonCancel.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                	
                    dialog.cancel();
                    
                }
            });
            
            Button buttonSet = (Button)dialog.findViewById(R.id.buttonSet);
            buttonSet.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                	
                	SetWifiTask task = new SetWifiTask(getApplicationContext(), spinnerSsid.getSelectedItem().toString(), editTextPassword.getText().toString(), dialog);
                	task.execute();
                	
                }
            });

            dialog.show();
		}
	}
	
	private class SetWifiTask extends AsyncTask<Void, Void, String> {
		
		private Context context;
		private String ssid;
		private String password;
		private Dialog outerDialog;
		
		private ProgressDialog dialog;
		
		public SetWifiTask(Context context, String ssid, String password, Dialog outerDialog) {
			this.context = context;
			this.ssid = ssid;
			this.password = password;
			this.outerDialog = outerDialog;
			
			dialog = new ProgressDialog(SwitchActivity.this);
			dialog.setCancelable(true);
		}
		
		@Override
		protected void onPreExecute() {
			dialog.setMessage("Trying to connect...");
			
			if (!dialog.isShowing()) {
				dialog.show();
            }
		}

		@Override
		protected String doInBackground(Void... params) {
			return Service.sendHttpRequest(context, "8&" + ssid + "&" + password + "&", Service.SOCKET_TIMEOUT_TRYING);
		}

		@Override
		protected void onPostExecute(String result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
            }
			
			if (result.equals(ssid + "-" + password)) {
				outerDialog.dismiss();
				
				Intent intent = new Intent(getApplicationContext(), ConnectActivity.class);
				SwitchActivity.this.startActivity(intent);
				
				SwitchActivity.activity.finish();
				SwitchActivity.this.finish();
			}
		}
	}
	
	private class GetITask extends AsyncTask<Void, Void, String> {
		
		private Context context;
		private String ssid;
		private String password;
		private Dialog outerDialog;
		
		private ProgressDialog dialog;
		
		public GetITask(Context context) {
			this.context = context;
			
			dialog = new ProgressDialog(SwitchActivity.this);
			dialog.setCancelable(true);
		}
		
		@Override
		protected void onPreExecute() {
			dialog.setMessage("Trying to connect...");
			
			if (!dialog.isShowing()) {
				dialog.show();
            }
		}

		@Override
		protected String doInBackground(Void... params) {
			return Service.sendHttpRequest(context, "6", Service.SOCKET_TIMEOUT_TRYING);
		}

		@Override
		protected void onPostExecute(String result) {
			if (dialog.isShowing()) {
				dialog.dismiss();
            }
			ArrayList<Date> dates = new ArrayList<Date>();
			ArrayList<Double> is = new ArrayList<Double>();
			
			String[] splitsa = result.split("A");
			for (int i = 0; i < splitsa.length; i++) {
				String[] splitsc = splitsa[i].split(",");
				try {
					dates.add(SharedValues.sdf.parse(splitsc[0].replace("_", " ")));
					is.add(Double.parseDouble(splitsc[1]));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			double W = 0;
			Date now = new Date();
			if (dates.size() > 1) {
				for (int i = 1; i < dates.size(); i++) {
					if (dates.get(i).getMonth() == now.getMonth() && dates.get(i).getYear() == now.getYear()) {
						long diff = dates.get(i).getTime() - dates.get(i - 1).getTime();
						long diffMinutes = diff / (60 * 1000); 
						W += diffMinutes * is.get(i - 1);
					}
				}
			}
			
			final Dialog dialog = new Dialog(SwitchActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_dialog_unit);
            dialog.setCancelable(true);
            
            final double unit = W * 0.00383333;

            TextView textViewUnit = (TextView) dialog.findViewById(R.id.textViewUnit);
            textViewUnit.setText(String.format("%.15f", unit));
            final EditText editTextU = (EditText) dialog.findViewById(R.id.editTextU);
            final TextView textViewBaht = (TextView) dialog.findViewById(R.id.textViewBaht);
            
            editTextU.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					try {
						double baht = unit * Double.parseDouble(editTextU.getText().toString());
						textViewBaht.setText(String.format("%.2f ฿", baht));
					}
					catch (NumberFormatException e) {
						
					}
					return false;
				}
			});
            
            dialog.show();
		}
	}
	
	private boolean onBoot = true;
	
	@Override
	protected void onResume() {
		if (!onBoot) {
			CheckStateTask task = new CheckStateTask(getApplicationContext(), true);
	    	task.execute();
		}
		else {
			onBoot = false;
		}
		
		super.onResume();
	}
	
	private void checkGPS(final boolean forceClose) {
		Log.d("locc", "checkGPS");
		if (!gpsTracker.canGetLocation()) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
			dialog.setTitle("ระบบหาตำแหน่งถูกปิด");
			dialog.setMessage("เปิดการใช้งาน \"ตำแหน่ง\" หรือ \"Location\" เพื่อทำการเช็คอิน");
			dialog.setPositiveButton("ตั้งค่า", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					activity.startActivityForResult(intent, forceClose ? REQUEST_LOCATION_SETTING_FORCE_CLOSE : REQUEST_LOCATION_SETTING);
					dialog.dismiss();
				}
			});
			dialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			dialog.setIcon(android.R.drawable.ic_menu_manage);
			dialog.show();
		}
		else {
			checkMock(forceClose);
		}
	}
	
	private void checkMock(final boolean forceClose) {
		Log.d("locc", "checkMock");
		if (isMockSettingsON()) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(SwitchActivity.this);
			dialog.setTitle("ตรวจพบการเปิดระบบตำแหน่งจำลอง");
			dialog.setMessage("ปิด \"อนุญาตตำแหน่งจำลอง\" หรือ \"Allow mock locations\" เพื่อทำการเช็คอิน");
			dialog.setPositiveButton("ตั้งค่า", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
		        	SwitchActivity.this.startActivityForResult(intent, forceClose ? REQUEST_MOCK_SETTING_FORCE_CLOSE : REQUEST_MOCK_SETTING);
					dialog.dismiss();
		        }
		     });
			dialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        	dialog.dismiss();
		        }
			});
			dialog.setIcon(android.R.drawable.ic_menu_manage);
			dialog.show();
		}
		else {
			double lat = gpsTracker.getLatitude();
			double lng = gpsTracker.getLongitude();
			SharedValues.setLat(getApplicationContext(), lat);
			SharedValues.setLng(getApplicationContext(), lng);
			
			Log.d("locc", SharedValues.getLat(getApplicationContext()) + " " + SharedValues.getLng(getApplicationContext()));
		}
	}
	
	private boolean isMockSettingsON() {
		return !Settings.Secure.getString(activity.getApplicationContext().getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0");
	}
	
}
