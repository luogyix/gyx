package com.agree.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 
 * @ClassName: DateUtil 
 * @company agree   
 * @author 	lilei
 * @date 2011-8-15 下午03:56:53 
 *	
 */
public class DateUtil {

	/**
	 * 接受YYYY-MM-DD的日期字符串参数,返回两个日期相差的天数
	 * @Title:getDistDates
	 * @param start	String类型	起始日期
	 * @param end	String类型	结束日期
	 * @return	long
	 * @throws ParseException
	 */
	public long getDistDates(String start, String end) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = sdf.parse(start);
		Date endDate = sdf.parse(end);
		return getDistDates(startDate, endDate);
	}

	/**
	 * 返回两个日期相差的天数
	 * @Title:getDistDates
	 * @param startDate 起始日期
	 * @param endDate	结束日期
	 * @return			long 天数
	 * @throws ParseException
	 */
	public long getDistDates(Date startDate, Date endDate) {
		long totalDate = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		long timestart = calendar.getTimeInMillis();
		calendar.setTime(endDate);
		long timeend = calendar.getTimeInMillis();
		totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
		return totalDate;
	}

	/**
	 * 获取与_fromdate相差_monthCount个月的日期
	 * @Title：getDistMonths
	 * @param 	_fromdate	:YYYY-MM-DD           
	 * @param 	_monthCount
	 * @return	resultDate	:YYYY-MM-DD	
	 * @throws ParseException
	 */
	public String getDistMonths(String _fromdate, int _monthCount)
			throws ParseException {
		String resultDate = "";
		int year, month, date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(_fromdate));
		year = calendar.get(Calendar.YEAR);
		date = calendar.get(Calendar.DAY_OF_MONTH);
		month = calendar.get(Calendar.MONTH) + 1 + _monthCount;
		int c = new Integer((month - 1) / 12);
		month = month % 12;
		if (month == 0)
			month = 12;
		year += c;
		resultDate = year + "-" + month + "-" + date;
		return resultDate;
	}

	/**
	 * 计算每个月的天数，以数组返回
	 * @Title：countMonthDates
	 * @param 	months 	int		月份数，即数组长度
	 * @param 	date	Date	起始日期
	 * @return	String[]	
	 * @throws ParseException
	 */
	public String[] countMonthDates(int months, Date date)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String _date = date == null ? sdf.format(new Date()) : sdf.format(date);// 如果不给定起算时间则从今天算起

		return countMonthDates(months, _date);
	}

	/**
	 * 计算每个月的天数，以数组返回
	 * @Title：countMonthDates
	 * @param 	months 	int		月份数，即数组长度
	 * @param 	date	String	起始日期
	 * @return	String[]	
	 * @throws ParseException
	 */
	public String[] countMonthDates(int months, String date)
			throws ParseException {
		String[] results = null;// 结果
		String _today = date == null ? new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()) : date;// 如果不给定起算时间则从今天算起
		int _months = months > 0 ? months : 24;// 如果不给定计算的月数则计算未来两年里面的24月
		String startDate = getDistMonths(_today, 0);// 获得起算日期的YYYY-MM-DD格式的字符串日期
		results = new String[_months];
		for (int i = 1; i <= _months; i++) {
			String nextMonthDate = getDistMonths(_today, i);// 每个月的截至日期
			String desc = startDate + " 至 " + nextMonthDate;
			long dates = getDistDates(startDate, nextMonthDate);// 返回天数
			results[i - 1] = desc + " ：共 " + dates + " 天！";
			startDate = nextMonthDate;// 当前月的结束日期作为下一个月的起始日期
		}
		return results;
	}
	
	/**
	 * 返回当日日期
	 * @Title: getDateTime 
	 * @param @return    当天日期，以"yyyy-MM-dd HH:mm:ss"格式输出
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getDateTime() {
		return getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	  /**
	   * 将当前时间的显示格式用给定的格式显示
	   * @Title: getCurrentDateByFormat 
	   * @param @param formatStr	String	日期格式
	   * @param @return    返回当日日期
	   * @return String    返回类型 
	   * @throws
	   */ 
	public static String getCurrentDateByFormat(String formatStr){
		  long currentTime = System.currentTimeMillis();
		  java.util.Date date = new java.util.Date(currentTime);
		  java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(formatStr);
		  return formatter.format(date);  
	}
	
	/**
	 * 返回给定时间毫秒数指定的时间，起始时间为：1970-01-01 08:00:00。
	 * @Title: getDateTime 
	 * @param @param al_datetime	long类型		时间的毫秒数
	 * @param @return    对应毫秒数的日期
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getDateTime(long al_datetime) {
	    java.util.Date date = new java.util.Date(al_datetime);
	    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
	        "yyyy-MM-dd HH:mm:ss");
	    return formatter.format(date);
	}
	
	/**
	 * 将当前日期以字符串形式返回
	 * @Title: getDateString 
	 * @param @param inDate	Date类型
	 * @param @return    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getDateString(java.util.Date inDate) {
	    return inDate.toString();
	}
	
	  /**
	   * 返回给定日期后数days天的日期
	   * @Title: getDateNDays 
	   * @param @param date	Date类型	给定时间
	   * @param @param days	int类型	天数
	   * @param @return    日期
	   * @return java.util.Date    返回类型 
	   * @throws
	   */
	public static java.util.Date getDateNDays(java.util.Date date, int days) {
	    if (days > 36500 || days < -36500) {
	      return null;
	    }
	    long l1 = 24, l2 = 60, l3 = 1000, l4 = days;
	    long lDays = l1 * l2 * l2 * l3 * l4; 
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    long lCurrentDate = calendar.getTimeInMillis(); 
	    long lUpdatedDate = lCurrentDate + lDays; 
	    java.util.Date dateNew = new java.util.Date(lUpdatedDate); 
	    return dateNew;
	}
	
	  /**
	   * 返回给定日期后数months月那天的日期
	   * @Title: getDateNMonths 
	   * @param @param date	Date类型，给定日期
	   * @param @param months	int类型，月份数
	   * @param @return    参数 
	   * @return Date    返回类型 
	   * @throws
	   */
	public static Date getDateNMonths(java.util.Date date, int months) {
	    if (months == 0) { 
	      return date;
	    }
	    if (months > 1200 || months < -1200) { 
	      return null;
	    }
	    GregorianCalendar gc = new GregorianCalendar();
	    gc.setTime(date);
	    gc.add(Calendar.MONTH, months);
	    java.util.Date date2 = gc.getTime();
	    return date2;
	}

	  /**
	   * 将给定的日期以给定的格式输出
	   * 如果给定格式为空，则日期格式以"yyyy-MM-dd HH:mm:ss"返回
	   * @Title: formatDate 
	   * @param @param date	Date类型
	   * @param @param format	String类型，日期格式
	   * @param @return    参数 
	   * @return String    返回类型 
	   * @throws
	   */
	public static String formatDate(Date date,String format){
	    if (date == null){
	      return "";
	    }
	    if (format == null || format.trim().equals("")){
	      format = "yyyy-MM-dd HH:mm:ss";
	    }
	    Locale locale = new Locale("en","US");
	    SimpleDateFormat formatter = new SimpleDateFormat(format.trim(),locale);
	    return formatter.format(date);
	}
	
	
	/** 
	 * @Title: diffTime 
	 * @Description: 返回当天的当前时间减给定当天某一时间的时间差，单位：秒
	 * @param @param time 给定的当天的时间，String类型，格式为 ("HHmmss")
	 * @param @return    参数 
	 * @return long    返回类型 
	 * @throws 
	 */ 
	public static long diffTime(String time){
		String curr = formatDate(new Date(),"HHmmss");
		int ch = Integer.valueOf(curr.substring(0,2));//当前 时
		int cm = Integer.valueOf(curr.substring(2,4));//当前 分
		int cc = Integer.valueOf(curr.substring(4,6));//当前 秒
		
		int th = Integer.valueOf(time.substring(0,2));//给定 时
		int tm = Integer.valueOf(time.substring(2,4));//给定 分
		int tc = Integer.valueOf(time.substring(4,6));//给定 秒
		
		long currs = ch*60*60+cm*60+cc;
		long times = th*60*60+tm*60+tc;
		
		if( currs < times ){
			return -1;
		}else{
			return currs - times;
		}
	}
	
	/**
	 * 给出两个时间的时间差,前一个时间是时间段靠前的
	 * @param fTime 前一时间
	 * @param nTime 后一时间
	 * @return
	 */
	public static long diffTime2(String fTime,String nTime){
		int ch = Integer.valueOf(nTime.substring(0,2));
		int cm = Integer.valueOf(nTime.substring(2,4));
		int cc = Integer.valueOf(nTime.substring(4,6));
		
		int th = Integer.valueOf(fTime.substring(0,2));
		int tm = Integer.valueOf(fTime.substring(2,4));
		int tc = Integer.valueOf(fTime.substring(4,6));
		
		long currs = ch*60*60+cm*60+cc;
		long times = th*60*60+tm*60+tc;
		
		if( currs < times ){
			return -1;
		}else{
			return currs - times;
		}
	}
	
	/** 
	 * @Title: isTime 
	 * @Description: 将给定的times秒的时间以 时:分:秒 的格式返回字符串
	 * @param @param times 时间，单位:秒
	 * @param @return    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public static String isTime(long times){
		long s = times % 60;
		long m = (times/60) % 60;
		long H = times/60/60;
		
		String ss = "00"+String.valueOf(s);
		ss = ss.substring(ss.length()-2,ss.length());
		
		String mm = "00"+String.valueOf(m);
		mm = mm.substring(mm.length()-2,mm.length());
		
		String HH = "00"+String.valueOf(H);
		HH = HH.substring(HH.length()-2,HH.length());
		
		return HH+":"+mm+":"+ss;
	}

	  /**
	   * 返回当前日期,格式为yyyy-MM-dd
	   * @Title: getDate 
	   * @param @return    参数 
	   * @return java.sql.Date    返回类型 
	   * @throws
	   */	
	public static java.sql.Date getDate() {
	    Calendar oneCalendar = Calendar.getInstance();
	    return getDate(oneCalendar.get(Calendar.YEAR),
	                   oneCalendar.get(Calendar.MONTH) + 1,
	                   oneCalendar.get(Calendar.DATE));
	}
	  /**
	   * 返回给定的两个日期相差的天数
	   * @Title: getIntervalDay 
	   * @param @param startDate
	   * @param @param endDate
	   * @param @return    参数 
	   * @return int    返回类型 
	   * @throws
	   */
	public static int getIntervalDay(java.sql.Date startDate,java.sql.Date endDate) {
	    long startdate = startDate.getTime();    
	    long enddate = endDate.getTime();
	    long interval = enddate - startdate;
	    int intervalday = (int) interval / (1000 * 60 * 60 * 24);
	    return intervalday;
	}
	  /**
	   * 
	   * @Title: getDate 
	   * @param @param yyyy
	   * @param @param MM
	   * @param @param dd
	   * @param @return    参数 
	   * @return java.sql.Date    返回类型 
	   * @throws
	   */
	public static java.sql.Date getDate(int yyyy, int MM, int dd) {
	    if (!verityDate(yyyy, MM, dd)) {
	      throw new IllegalArgumentException("This is illegimate date!");
	    }

	    Calendar oneCalendar = Calendar.getInstance();
	    oneCalendar.clear();
	    oneCalendar.set(yyyy, MM - 1, dd);
	    return new java.sql.Date(oneCalendar.getTime().getTime());
	}
	  /**
	   * 判断年，月，日对应的数值是否是正确的
	   * @Title: verityDate 
	   * @param @param yyyy	年的数值是否是正确的
	   * @param @param MM	月的数值是否是正确的
	   * @param @param dd	日的数值是否是正确的
	   * @param @return    参数 
	   * @return boolean    返回类型 
	   * @throws
	   */
	public static boolean verityDate(int yyyy, int MM, int dd) {
	    boolean flag = false;

	    if (MM >= 1 && MM <= 12 && dd >= 1 && dd <= 31) {
	      if (MM == 4 || MM == 6 || MM == 9 || MM == 11) {
	        if (dd <= 30) {
	          flag = true;
	        }
	      }  else if (MM == 2) {
	        if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
	          if (dd <= 29) {
	            flag = true;
	          }
	        } else if (dd <= 28) {
	          flag = true;
	        }
	      } else {
	        flag = true;
	      }
	    }
	    return flag;
	}
	  /**
	   * 返回给定日期的下一天的日期
	   * @Title: nextDate 
	   * @param @param date
	   * @param @return    参数 
	   * @return String    返回类型 
	   * @throws
	   */	  
	public static String  nextDate(String date) {
		  
		  	int yyyy = Integer.valueOf(date.substring(0, 4));
			int MM = Integer.valueOf(date.substring(4,6));
			int dd = Integer.valueOf(date.substring(6,8));

	        if (MM == 4 || MM == 6 || MM == 9 || MM == 11) {
		        if (dd < 30) {
		          dd++;
		        }else{
		          if(MM < 12){
		        	  MM++;
		        	  dd = 1;
		          }else{
		        	  yyyy++;
		        	  MM = 1;
		        	  dd = 1;
		          }
		        }
	      }else if (MM == 2) {
		        if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
		        	if (dd < 29) {
		  	          dd++;
		  	        }else{
		  	          if(MM < 12){
		  	        	  MM++;
		  	        	  dd = 1;
		  	          }else{
		  	        	  yyyy++;
		  	        	  MM = 1;
		  	        	  dd = 1;
		  	          }
		  	        }
		        }else{
		        	if (dd < 28) {
		  	          dd++;
		  	        }else{
		  	          if(MM < 12){
		  	        	  MM++;
		  	        	  dd = 1;
		  	          }else{
		  	        	  yyyy++;
		  	        	  MM = 1;
		  	        	  dd = 1;
		  	          }
		  	        }
		        }
	      }else{
	    	  if (dd < 31) {
	  	          dd++;
	  	        }else{
	  	          if(MM < 12){
	  	        	  MM++;
	  	        	  dd = 1;
	  	          }else{
	  	        	  yyyy++;
	  	        	  MM = 1;
	  	        	  dd = 1;
	  	          }
	  	        }
	      }
	      StringBuffer sb = new StringBuffer("");
	  	  sb.append(yyyy);
	  	  if(MM <10){
	  		  sb.append("0"+MM); 
	  	  }else{
	  		  sb.append(MM); 
	  	  }
	  	  if(dd < 10){
	  		  sb.append("0"+dd);
	  	  }else{
	  		  sb.append(dd);
	  	  }
	    
	    return sb.toString();
	}
	  /**
	   * date的格式为yyyyMMdd,返回给定日期的上一天的日期
	   * @Title: preDate 
	   * @param @param date
	   * @param @return    参数 
	   * @return String    返回类型 
	   * @throws
	   */	  
	public static String  preDate(String date) {//
		  int yyyy = Integer.valueOf(date.substring(0, 4));
		  int MM = Integer.valueOf(date.substring(4,6));
		  int dd = Integer.valueOf(date.substring(6,8));
		  
		  if(dd > 1){
			  dd--;
		  }else{
			  --MM;
			  if(MM > 0){
				  if(MM == 4 || MM == 6 || MM == 9 || MM == 11){
					  dd = 30;
				  }else if(MM == 2){
					  if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
						  dd = 29;
					  }else{
						  dd = 28;
					  }
				  }else{
					  dd = 31;
				  }
		  	  }else{
		  		  --yyyy;
		  		  MM = 12;
		  		  dd = 31;
		  	  }
		  }
		  StringBuffer sb = new StringBuffer("");
		  sb.append(yyyy);
		  if(MM <10){
			  sb.append("0"+MM); 
		  }else{
			  sb.append(MM); 
		  }
		  if(dd < 10){
			  sb.append("0"+dd);
		  }else{
			  sb.append(dd);
		  }
	  
		  return sb.toString();
	}

	  /**
	   * 返回给定两个日期之间相差多少个月
	   * @Title: getIntervalMonth 
	   * @param @param startDate
	   * @param @param endDate
	   * @param @return    参数 
	   * @return int    返回类型 
	   * @throws
	   */
	public static int getIntervalMonth(java.util.Date startDate, java.util.Date endDate) {
	    java.text.SimpleDateFormat mmformatter = new java.text.SimpleDateFormat("MM");
	    SimpleDateFormat ddformatter = new SimpleDateFormat("dd");
	    int daystart = Integer.parseInt(ddformatter.format(startDate));
	    int dayend = Integer.parseInt(ddformatter.format(endDate));
	    int monthstart = Integer.parseInt(mmformatter.format(startDate));
	    int monthend = Integer.parseInt(mmformatter.format(endDate));
	    java.text.SimpleDateFormat yyyyformatter = new java.text.SimpleDateFormat("yyyy");
	    int yearstart = Integer.parseInt(yyyyformatter.format(startDate));
	    int yearend = Integer.parseInt(yyyyformatter.format(endDate));
	    if(daystart > dayend ){
	    	return (yearend - yearstart) * 12 + (monthend - monthstart)-1;
	    }
	    return (yearend - yearstart) * 12 + (monthend - monthstart);
	}
	
	/**
	 * 将HHmmss格式的String类型的time格式化成HH:mm:ss格式
	 * @param time
	 * @return
	 */
	public static String formatTime(String time){
		return time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4);
	}

	
}
