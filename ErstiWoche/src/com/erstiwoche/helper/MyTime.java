package com.erstiwoche.helper;

import java.util.concurrent.TimeUnit;

public class MyTime implements Comparable<MyTime>{

	private long time;
	
	private long days;
	private long hours;
	private long minutes;
	private long seconds;
	
	public MyTime(long time){
		if(time>0){
		this.time = time;
		}
		else{
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }
		
		long millis = time;
        
        days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
	}
	
	public long getDays(){
		return days;
	}
	
	public long getHours(){
		return hours+2;	//Whyever zwei Stunden fehlen
	}
	
	public long getMinutes(){
		return minutes;
	}
	
	public long getSeconds(){
		return seconds;
	}
	
	public String getTimeInHoursMinutesFormat(){
		return getHours()+":"+getMinutes();
	}

	@Override
	public int compareTo(MyTime o) {
		if(time>o.time) return 1;
		if(time<o.time) return -1;
		return 0;
	}

}