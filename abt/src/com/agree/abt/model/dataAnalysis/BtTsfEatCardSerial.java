package com.agree.abt.model.dataAnalysis;

import java.io.Serializable;

/**
 * 吞卡交易流水信息表实体类
 * @ClassName: BtTsfEatCardSerial.java
 * @company 赞同科技
 * @author 王明哲
 * @date 2017-1-11
 */
public class BtTsfEatCardSerial implements Serializable
{

	private static final long serialVersionUID = 1L;
	/**
	 * 序号	
	 */
	private String serial_number;
	/**
	 * 机构号
	 */
	private String branch;
	/**
	 * 机构名称	
	 */
	private String branch_name;
	/**
	 * 客户名称
	 */
	private String cust_name;
	/**
	 * 虚拟柜员号
	 */
	private String teller_name;
	/**
	 * 日期
	 */
	private String date;
	/**
	 * 时间
	 */
	private String time;
	/**
	 * 卡号	
	 */
	private String cardnumber;
	/**
	 * 吞卡原因
	 */
	private String reason;
	/**
	 * 安装地址	
	 */
	private String address;
	/**
	 * 返回码	
	 */
	private String return_code;
	/**
	 * 前端流水号	
	 */
	private String webserno;
	/**
	 * AFA流水号	
	 */
	private String afaserno;
	public String getSerial_number() 
	{
		return serial_number;
	}
	public void setSerial_number(String serial_number) 
	{
		this.serial_number = serial_number;
	}
	public String getBranch()
	{
		return branch;
	}
	public void setBranch(String branch) 
	{
		this.branch = branch;
	}
	public String getBranch_name() 
	{
		return branch_name;
	}
	public void setBranch_name(String branch_name) 
	{
		this.branch_name = branch_name;
	}
	public String getCust_name() 
	{
		return cust_name;
	}
	public void setCust_name(String cust_name) 
	{
		this.cust_name = cust_name;
	}
	public String getTeller_name() 
	{
		return teller_name;
	}
	public void setTeller_name(String teller_name) 
	{
		this.teller_name = teller_name;
	}
	public String getDate() 
	{
		return date;
	}
	public void setDate(String date) 
	{
		this.date = date;
	}
	public String getTime() 
	{
		return time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public String getCardnumber() 
	{
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) 
	{
		this.cardnumber = cardnumber;
	}
	public String getReason() 
	{
		return reason;
	}
	public void setReason(String reason) 
	{
		this.reason = reason;
	}
	public String getAddress() 
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getReturn_code() 
	{
		return return_code;
	}
	public void setReturn_code(String return_code) 
	{
		this.return_code = return_code;
	}
	public String getWebserno() 
	{
		return webserno;
	}
	public void setWebserno(String webserno) 
	{
		this.webserno = webserno;
	}
	public String getAfaserno() 
	{
		return afaserno;
	}
	public void setAfaserno(String afaserno) 
	{
		this.afaserno = afaserno;
	}
}
