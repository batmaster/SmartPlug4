package com.kmitl.smartplug4;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.kmitl.smartplug4.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlarmActivity extends FragmentActivity {

	private TextView textView0;
	private Button buttonAdd;
	private ListView listViewDateTime;
	//private ListView listViewTime;
	
	private Date date;
	
	private BroadcastReceiver refreshAlarm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		
		refreshAlarm = new BroadcastReceiver(){
			@Override
		    public void onReceive(Context context, Intent intent) {
		    	listViewDateTime.setAdapter(new ListViewRowAdapter(getApplicationContext(), SharedValues.getDateTimeList(getApplicationContext(), SharedValues.KEY_ONETIME)));
		    }
		};
		
		registerReceiver(refreshAlarm, new IntentFilter("com.kmitl.smartplug.refreshUnit"));
		
		textView0 = (TextView) findViewById(R.id.textView0);
		textView0.setText((SharedValues.getModePref(getApplicationContext()).equals("direct") ? "Direct Mode" : "Global Mode") + " Alarm Setting");

		buttonAdd = (Button) findViewById(R.id.buttonAdd);
		buttonAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				final Dialog dialog = new Dialog(AlarmActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog_alarm);
                dialog.setCancelable(true);
                
                final EditText editTextDateTime = (EditText) dialog.findViewById(R.id.editTextDateTime);
        		Button buttonClear = (Button) dialog.findViewById(R.id.buttonClear);
            	//final Switch switchEveryday = (Switch) dialog.findViewById(R.id.switchEveryday);
                

        		final Switch switch1 = (Switch) dialog.findViewById(R.id.switch1);
                final Switch switch2 = (Switch) dialog.findViewById(R.id.switch2);
                final Switch switch3 = (Switch) dialog.findViewById(R.id.switch3);
                final Switch switch4 = (Switch) dialog.findViewById(R.id.switch4);
        		
        		final CheckBox checkBox1 = (CheckBox) dialog.findViewById(R.id.checkBox1);
                checkBox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if (isChecked) {
							switch1.setVisibility(View.VISIBLE);
						}
						else {
							switch1.setVisibility(View.INVISIBLE);
						}
					}
				});
                
                final CheckBox checkBox2 = (CheckBox) dialog.findViewById(R.id.checkBox2);
                checkBox2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if (isChecked) {
							switch2.setVisibility(View.VISIBLE);
						}
						else {
							switch2.setVisibility(View.INVISIBLE);
						}
					}
				});
                
                final CheckBox checkBox3 = (CheckBox) dialog.findViewById(R.id.checkBox3);
                checkBox3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if (isChecked) {
							switch3.setVisibility(View.VISIBLE);
						}
						else {
							switch3.setVisibility(View.INVISIBLE);
						}
					}
				});
                
                final CheckBox checkBox4 = (CheckBox) dialog.findViewById(R.id.checkBox4);
                checkBox4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if (isChecked) {
							switch4.setVisibility(View.VISIBLE);
						}
						else {
							switch4.setVisibility(View.INVISIBLE);
						}
					}
				});
        		
        		final Button buttonAdd = (Button) dialog.findViewById(R.id.buttonAdd);
                
				final SlideDateTimeListener listener = new SlideDateTimeListener() {

					@Override
					public void onDateTimeSet(Date d) {
						AlarmActivity.this.date = d;
						//editTextDateTime.setText(switchEveryday.isChecked() ? SharedValues.sdf_everyday.format(date) : SharedValues.sdf.format(date));
						editTextDateTime.setText(SharedValues.sdf.format(date));
						
					}
				};
                
        		/*switchEveryday.setOnCheckedChangeListener(new OnCheckedChangeListener() {

        			@Override
        			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        				if (date != null)
        					editTextDateTime.setText(isChecked ? SharedValues.sdf_everyday.format(date) : SharedValues.sdf.format(date));
        				
        			}
        		});*/
        		
        		editTextDateTime.setOnClickListener(new OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				new SlideDateTimePicker.Builder(getSupportFragmentManager()).setIs24HourTime(true).setListener(listener)
        						.setInitialDate(new Date()).build().show();
        			}
        		});
        		editTextDateTime.setInputType(InputType.TYPE_NULL);
        		editTextDateTime.addTextChangedListener(new TextWatcher() {
        			
        			@Override
        			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        				// TODO Auto-generated method stub
        				
        			}
        			
        			@Override
        			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        				// TODO Auto-generated method stub
        				
        			}
        			
        			@Override
        			public void afterTextChanged(Editable arg0) {
        				if (editTextDateTime.getText().toString().equals("")) {
        					buttonAdd.setVisibility(View.GONE);
        					//switchEveryday.setVisibility(View.GONE);
        				}
        				else {
        					buttonAdd.setVisibility(View.VISIBLE);
        					//switchEveryday.setVisibility(View.VISIBLE);
        				}
        			}
        		});
        		
        		buttonClear.setOnClickListener(new OnClickListener() {
        			
        			@Override
        			public void onClick(View v) {
        				editTextDateTime.setText("");
        			}
        		});
        		
        		buttonAdd.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						int a = !checkBox1.isChecked() ? 2 : (switch1.isChecked() ? 1 : 0);
						int b = !checkBox2.isChecked() ? 2 : (switch2.isChecked() ? 1 : 0);
						int c = !checkBox3.isChecked() ? 2 : (switch3.isChecked() ? 1 : 0);
						int d = !checkBox4.isChecked() ? 2 : (switch4.isChecked() ? 1 : 0);
						String datetime = editTextDateTime.getText().toString();
						
						SendAlarmTask task = new SendAlarmTask(getApplicationContext(), a, b, c, d, datetime);
						task.execute();
						editTextDateTime.setText("");
						dialog.dismiss();
					}
				});
        		
        		buttonAdd.setVisibility(View.GONE);
        		switch1.setVisibility(View.GONE);
        		//switchEveryday.setVisibility(View.GONE);

                dialog.show();
			}
		});
		
		listViewDateTime = (ListView) findViewById(R.id.listViewDateTime);
		listViewDateTime.setAdapter(new ListViewRowAdapter(getApplicationContext(), SharedValues.getDateTimeList(getApplicationContext(), SharedValues.KEY_ONETIME)));
		
		//listViewTime = (ListView) findViewById(R.id.listViewTime);
		//listViewTime.setAdapter(new ListViewRowAdapter(getApplicationContext(), SharedValues.getDateTimeList(getApplicationContext(), SharedValues.KEY_EVERYDAY)));
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(refreshAlarm);
		super.onDestroy();
	}

	private class ListViewRowAdapter extends BaseAdapter {

		private Context context;
		private ArrayList<DateTimeItem> datetime;

		public ListViewRowAdapter(Context context, ArrayList<DateTimeItem> datetime) {
			this.context = context;
			this.datetime = datetime;
		}

		@Override
		public int getCount() {
			return datetime.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null)
				convertView = mInflater.inflate(R.layout.listview_row, parent, false);
			
			final Switch switch1 = (Switch) convertView.findViewById(R.id.switch1);
			switch1.setChecked((datetime.get(position).getState()));
			switch1.setClickable(false);
			
			TextView textViewTime = (TextView) convertView.findViewById(R.id.textViewTime);
			textViewTime.setText(datetime.get(position).getTime());
			
			TextView textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);
			textViewDate.setText(datetime.get(position).getDate());
			
			final String KEY = datetime.get(position).getDate().equals("") ? SharedValues.KEY_EVERYDAY : SharedValues.KEY_ONETIME;
			final DateTimeItem ITEM = datetime.get(position);
			
			Button buttonDelete = (Button) convertView.findViewById(R.id.buttonDelete);
			buttonDelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					SharedValues.removeDateTime(getApplicationContext(), KEY, ITEM);
					/*if (KEY.equals(SharedValues.KEY_EVERYDAY))
						listViewTime.setAdapter(new ListViewRowAdapter(getApplicationContext(), SharedValues.getDateTimeList(getApplicationContext(), SharedValues.KEY_EVERYDAY)));
					else*/
						listViewDateTime.setAdapter(new ListViewRowAdapter(getApplicationContext(), SharedValues.getDateTimeList(getApplicationContext(), SharedValues.KEY_ONETIME)));
				
				}
			});

			return convertView;
		}

	}
	
	private class SendAlarmTask extends AsyncTask<Void, Void, String> {
		
		private Context context;
		private ProgressDialog dialog;
		
		private String param;
		
		public SendAlarmTask(Context context, int a, int b, int c, int d, String datetime) {
			this.context = context;
			this.param = String.valueOf(a) + String.valueOf(b) + String.valueOf(c) + String.valueOf(d) + "_" + datetime.replace(' ', '_');
			
			dialog = new ProgressDialog(AlarmActivity.this);
			dialog.setCancelable(false);
		}
		
		@Override
		protected void onPreExecute() {
			Log.d("p", "TryToConnectTask: Pre");
			dialog.setMessage("Trying to connect...");
			
			if (!dialog.isShowing()) {
				dialog.show();
            }
		}

		@Override
		protected String doInBackground(Void... params) {
			Log.d("p", "TryToConnectTask: In");
			return Service.sendHttpRequest(context, "2" + param, Service.SOCKET_TIMEOUT_TRYING);
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d("p", "TryToConnectTask: Post");
			if (dialog.isShowing()) {
				dialog.dismiss();
            }
			
			
		}
	}
}
