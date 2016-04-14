package com.kmitl.smartplug4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class SharedValues {
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy H:m");
	
	public static String KEY_EVERYDAY = "KEY_EVERYDAY";
	public static String KEY_ONETIME = "KEY_ONETIME";
	
	private SharedValues () {
		
	}
	
	public static void setModePref(Context context, String value) {
		SharedPreferences sp = context.getSharedPreferences("kmitl", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("mode", value);
		editor.commit();
	}

	public static String getModePref(Context context) {
		SharedPreferences sp = context.getSharedPreferences("kmitl", Context.MODE_PRIVATE);
		return sp.getString("mode", null);
	}
	
	public static void showDialog(Context context, String message) {
		new AlertDialog.Builder(context)
        .setMessage(message)
        .setCancelable(true)
        .setPositiveButton("ok", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
	}
	
	
	
	//// location
	
	public static void setLat(Context context, double lat) {
		SharedPreferences sp = context.getSharedPreferences("kmitl", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("lat", String.valueOf(lat));
		editor.commit();
	}
	
	public static double getLat(Context context) {
		SharedPreferences sp = context.getSharedPreferences("kmitl", Context.MODE_PRIVATE);
		return Double.parseDouble(sp.getString("lat", "0"));
	}
	
	public static void setLng(Context context, double lng) {
		SharedPreferences sp = context.getSharedPreferences("kmitl", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("lng", String.valueOf(lng));
		editor.commit();
	}
	
	public static double getLng(Context context) {
		SharedPreferences sp = context.getSharedPreferences("kmitl", Context.MODE_PRIVATE);
		return Double.parseDouble(sp.getString("lng", "0"));
	}
	
	public static void setEnableLocation(Context context, boolean enable) {
		SharedPreferences sp = context.getSharedPreferences("kmitl", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean("enable", enable);
		editor.commit();
	}
	
	public static boolean getEnableLocation(Context context) {
		SharedPreferences sp = context.getSharedPreferences("kmitl", Context.MODE_PRIVATE);
		return sp.getBoolean("enable", false);
	}
	
	public static void setOffRange(Context context, double range) {
		SharedPreferences sp = context.getSharedPreferences("kmitl", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("range", String.valueOf(range));
		editor.commit();
	}
	
	public static double getOffRange(Context context) {
		SharedPreferences sp = context.getSharedPreferences("kmitl", Context.MODE_PRIVATE);
		return Double.parseDouble(sp.getString("range", "0"));
	}
}
