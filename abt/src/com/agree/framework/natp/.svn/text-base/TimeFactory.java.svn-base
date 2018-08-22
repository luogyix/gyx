package com.agree.framework.natp;
import java.text.SimpleDateFormat;


public class TimeFactory {
	public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日 HH:mm:ss ");
        java.util.Date dateTime = new java.util.Date();
        String time = dateFormat.format(dateTime);
        return time;
	}
	public static String getDateWithFormat(String format){
		 SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	        java.util.Date dateTime = new java.util.Date();
	        String time = dateFormat.format(dateTime);
	        return time;
	}
	public static long getTime(){
		long time=0 ;
		java.util.Date dateTime = new java.util.Date();
		time=dateTime.getTime();
		time=time/2;
		return time ;
	}
}
