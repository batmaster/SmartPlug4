package com.kmitl.smartplug4;

import java.text.ParseException;
import java.util.Date;

public class DateTimeItem implements Comparable<DateTimeItem> {
	
	private String date;
	private String time;
	
	private Date d;
	
	private String states;
	
	public DateTimeItem(String datetime, String states) {
		setDateTime(datetime);
		try {
			d = SharedValues.sdf.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.states = states;
	}

	public Date getDateTime() {
		return d;
	}

	public void setDateTime(String datetime) {
		String[] dt = datetime.split(" ");
		if (dt.length == 2) {
			this.date = dt[0];
			this.time = dt[1];
		}
		else {
			this.date = "";
			this.time = dt[0];
		}
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}
	
	public int get1() {
		return states.charAt(0) - 48;
	}
	
	public int get2() {
		return states.charAt(1) - 48;
	}
	
	public int get3() {
		return states.charAt(2) - 48;
	}
	
	public int get4() {
		return states.charAt(3) - 48;
	}
	
	public String getStates() {
		return states;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof DateTimeItem))
			return false;
		DateTimeItem dt = (DateTimeItem) o;
		return toString().equals(dt.toString());
	}

	@Override
	public int compareTo(DateTimeItem arg0) {
		return d.compareTo(arg0.getDateTime());
	}

}
