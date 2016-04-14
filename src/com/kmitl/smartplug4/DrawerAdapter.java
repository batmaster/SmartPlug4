package com.kmitl.smartplug4;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class DrawerAdapter extends BaseAdapter {
	
	private Context context;
	private int[] imageRids;
	private int thirdState;
	
	public DrawerAdapter(Context context, int[] imageRids, int thirdState) {
		this.context = context;
		this.imageRids = imageRids;
		this.thirdState = thirdState;
	}

	@Override
	public int getCount() {
		return imageRids.length;
	}

	@Override
	public Object getItem(int position) {
		return imageRids[position];
	}

	@Override
	public long getItemId(int position) {
		return imageRids[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_row, null);
        }
          
        ImageView imageView1 = (ImageView) convertView.findViewById(R.id.imageView1);
        imageView1.setImageResource(imageRids[position]);
        
        if (position == 3)
        	convertView.setVisibility(thirdState);
        	
        
        return convertView;
	}

}
