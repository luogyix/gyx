package com.agree.framework.serial;

import java.io.Serializable;
import java.util.Date;

/**
 * <DL>
 * <DT><B> 流水号信息. </B></DT>
 * <p>
 * <DD>包括流水号</DD>
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
public class KeyInfo implements Serializable {

	/** */
	private static final long serialVersionUID = -2849165987149481493L;
	/** 流水号 */
	private String _key;
	
	/** 日期 **/
	private Date _date;

	/**
	 * 获取流水号
	 * 
	 * @return 流水号
	 */
	public String getKey() {
		return _key;
	}

	/**
	 * 设置流水号
	 * 
	 * @param key
	 *            流水号
	 */
	public void setKey(String key) {
		_key = key;
	}

	/**
	 * 获取日期
	 * 
	 * @return 日期
	 */
	public Date getDate() {
		return _date;
	}

	/**
	 * 设置日期
	 * 
	 * @param _date 日期
	 *            
	 */
	public void setDate(Date date) {
		this._date = date;
	}

}