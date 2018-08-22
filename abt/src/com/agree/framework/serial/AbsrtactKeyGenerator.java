package com.agree.framework.serial;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import com.agree.util.DateUtil;

/**
 * <DL>
 * <DT><B> 通讯前置抽象流水号类. </B></DT>
 * <p>
 * <DD>实现基本的流水号操作，如取流水号，存流水号</DD>
 * </DL>
 * <p>
 * 
 * <DL>
 * <DT><B>使用范例</B></DT>
 * <p>
 * <DD>使用范例说明</DD>
 * </DL>
 * <p>
 * 
 */
public abstract class AbsrtactKeyGenerator implements IKeyGenerator {

	/** */
	private static final long serialVersionUID = -3685930116934348258L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.agree.afe.resource.serial.AfeKeyGenerator#getKey()
	 */
	public synchronized String getKey() throws KeyException {
		// String ret = Long.toString(key);
		String ret = format.format(key).toString();
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.agree.afe.resource.serial.AfeKeyGenerator#getNextKey()
	 */
	public synchronized String getNextKey() throws KeyException {
		// String ret = Long.toString(key);
		if(changeFlag !=null && !changeFlag.equals("") && !changeFlag.equals("none") && date!=null){
			if(changeFlag.equals("day") && !DateUtil.formatDate(date, "yyyyMMdd").equals(DateUtil.formatDate(new Date(), "yyyyMMdd"))){
				reset();
			}else if(changeFlag.equals("month") && !DateUtil.formatDate(date, "yyyyMM").equals(DateUtil.formatDate(new Date(), "yyyyMM"))){
				reset();
			}else if(changeFlag.equals("year") && !DateUtil.formatDate(date, "yyyy").equals(DateUtil.formatDate(new Date(), "yyyy"))){
				reset();
			}else if(changeFlag.equals("week")){
				Calendar cal = Calendar.getInstance();
				cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 每周从周一开始
				cal.setTime(date);
		        int week = cal.get(Calendar.WEEK_OF_YEAR);
		        cal.setTime(new Date());
		        int nowWeek = cal.get(Calendar.WEEK_OF_YEAR);
		        if(week != nowWeek){
		        	reset();
		        }
			}
		}
		
		String ret = format.format(key).toString();
		if (key >= maxValue) {
			key = minValue;
			nextKey = key + interval; // added by wxchc
			save();
		} else if (key == nextKey) {
			nextKey += interval;
			if (nextKey > maxValue) { // added by wxchc
				nextKey = maxValue;
			}
			save();
		}
		key++;
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.agree.afe.resource.serial.AfeKeyGenerator#reset()
	 */
	public synchronized void reset() throws KeyException {
		key = minValue;
		nextKey = key + interval;
		save();
	}

	/** 保存间隔 */
	protected int interval = 20;

	/** 键值 */
	protected long key;

	/** 下一次键值保存 */
	protected long nextKey;

	/** 最大值 */
	protected long maxValue;

	/** 最小值 */
	protected long minValue;
	
	/** 流水号生成时间 **/
	protected Date date;
	
	/** 切换标志： 每日(day)、每周(week)、每月(month)、每年(year)切换或不切换(none) **/
	protected String changeFlag = "none";
	
	/** 流水号格式 */
	protected String formatStr = "'"+DateUtil.formatDate(new Date(), "yyyyMMdd")+"'00000000";

	/** 格式化类 */
	protected DecimalFormat format = new DecimalFormat(formatStr);
	
	/** 流水号格式 */
	public static String ITEM_FORMAT_STRING = "流水号格式";

	/** 最大值 */
	public static String ITEM_MAX_VALUE = "最大值";

	/** 最小值 */
	public static String ITEM_MIN_VALUE = "最小值";

	/** 保存间隔 */
	public static String ITEM_SAVE_INTERVAL = "保存间隔";

	/**
	 * @return Returns the format.
	 */
	public DecimalFormat getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            The format to set.
	 */
	public void setFormat(DecimalFormat format) {
		this.format = format;
	}

	/**
	 * @return Returns the interval.
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * @param interval
	 *            The interval to set.
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}

	/**
	 * @return Returns the maxValue.
	 */
	public long getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue
	 *            The maxValue to set.
	 */
	public void setMaxValue(long maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @return Returns the minValue.
	 */
	public long getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue
	 *            The minValue to set.
	 */
	public void setMinValue(long minValue) {
		this.minValue = minValue;
	}

	/**
	 * @param key
	 *            The key to set.
	 */
	public void setKey(String key) {
		this.key = Long.parseLong(key);
	}

	public String getFormatStr() {
		return formatStr;
	}

	public void setFormatStr(String formatStr) {
		this.formatStr = formatStr;
	}

	public Date getDate() {
		return date;
	}

	/**
	 * @return the changeFlag
	 */
	public String getChangeFlag() {
		return changeFlag;
	}

	/**
	 * @param changeFlag the changeFlag to set
	 */
	public void setChangeFlag(String changeFlag) {
		this.changeFlag = changeFlag;
	}

}