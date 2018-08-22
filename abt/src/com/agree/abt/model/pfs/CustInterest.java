package com.agree.abt.model.pfs;

/**
 * 利率实体类
 * @author 高艺祥
 *
 */
public class CustInterest {
	/**
	 * 机构号
	 */
	private String branch;
	/**
	 * 利率说明
	 */
	private String intds;
	/**
	 * 货币代码
	 */
	private String ccy;
	/**
	 * 利率
	 */
	private String intV;
	/**
	 * 生效日期
	 */
	private String vlddat;
	/**
	 * 涨幅
	 */
	private String amount;
	/**
	 * 执行利率
	 */
	private String ex_interest;
	/**
	 * 修改时间
	 */
	private String date_time;
	/**
	 * 添加时间
	 */
	private String add_time;
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getIntds() {
		return intds;
	}
	public void setIntds(String intds) {
		this.intds = intds;
	}
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
	public String getIntV() {
		return intV;
	}
	public void setIntV(String intV) {
		this.intV = intV;
	}
	public String getVlddat() {
		return vlddat;
	}
	public void setVlddat(String vlddat) {
		this.vlddat = vlddat;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getEx_interest() {
		return ex_interest;
	}
	public void setEx_interest(String ex_interest) {
		this.ex_interest = ex_interest;
	}
	public String getDate_time() {
		return date_time;
	}
	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
}
