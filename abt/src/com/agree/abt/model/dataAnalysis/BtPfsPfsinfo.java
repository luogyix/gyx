package com.agree.abt.model.dataAnalysis;

import java.io.Serializable;


/**
 * 预填单信息表实体类
 * @ClassName:BtPfsPfsinfo.java 
 * @company 赞同科技
 * @author 王明哲
 * @date 2017-1-9上午10:34:40
 */
public class BtPfsPfsinfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	/**
	 * 日期	WORK_DATE
	 */
	private String work_date;
	/**
	 * 网点号	BRANCH
	 */
	private String branch;
	/**
	 * 机具编号	DEVICENUM
	 */
	private String devicenum;
	/**
	 * 业务编号	TRADE_ID
	 */
	private String trade_id;
	/**
	 * 业务名称   TRADE_NAME_CH
	 */
	private String trade_name_ch;
	/**
	 * 填单笔数
	 */
	private String pfscount;
	/**
	 * 开始时间	START_TIME
	 */
	private String strat_time;
	/**
	 * 结束时间	END_TIME
	 */
	private String end_time;
	/**
	 * 平均填单时间
	 */
	private String averagetime;
	/**
	 * 序号
	 */
	private String pfs_id;

	public String getWork_date() 
	{
		return work_date;
	}
	public void setWork_date(String work_date) 
	{
		this.work_date = work_date;
	}
	public String getBranch() 
	{
		return branch;
	}
	public void setBranch(String branch) 
	{
		this.branch = branch;
	}
	public String getDevicenum() 
	{
		return devicenum;
	}
	public void setDevicenum(String devicenum) 
	{
		this.devicenum = devicenum;
	}
	public String getTrade_id() 
	{
		return trade_id;
	}
	public void setTrade_id(String trade_id) 
	{
		this.trade_id = trade_id;
	}
	public String getTrade_name_ch() 
	{
		return trade_name_ch;
	}
	public void setTrade_name_ch(String trade_name_ch) 
	{
		this.trade_name_ch = trade_name_ch;
	}
	public String getPfscount() 
	{
		return pfscount;
	}
	public void setPfscount(String pfscount) 
	{
		this.pfscount = pfscount;
	}
	public String getStrat_time() 
	{
		return strat_time;
	}
	public void setStrat_time(String strat_time) 
	{
		this.strat_time = strat_time;
	}
	public String getEnd_time() 
	{
		return end_time;
	}
	public void setEnd_time(String end_time) 
	{
		this.end_time = end_time;
	} 
	public String getAveragetime() 
	{
		return averagetime;
	}
	public void setAveragetime(String averagetime) 
	{
		this.averagetime = averagetime;
	}
	public String getPfs_id() 
	{
		return pfs_id;
	}
	public void setPfs_id(String pfs_id) 
	{
		this.pfs_id = pfs_id;
	}
}
