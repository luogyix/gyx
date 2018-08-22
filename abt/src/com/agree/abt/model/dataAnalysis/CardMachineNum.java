package com.agree.abt.model.dataAnalysis;

/**
 * 发卡机交易流水报表统计
 * @author 高艺祥<bt>
 * 			最后修改日期：201701012
 *
 */
public class CardMachineNum {
	/**
	 * 机构号
	 */
	private String branch;
	/**
	 * 设备编号
	 */
	private String deviceno;
	/**
	 * 交易时间
	 */
	private String serial_date;
	/**
	 * 业务编码
	 */
	private String menu_code;
	/**
	 * 业务名称
	 */
	private String menu_name;
	/**
	 * 交易笔数
	 */
	private String executeNum;
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getDeviceno() {
		return deviceno;
	}
	public void setDeviceno(String deviceno) {
		this.deviceno = deviceno;
	}
	public String getSerial_date() {
		return serial_date;
	}
	public void setSerial_date(String serial_date) {
		this.serial_date = serial_date;
	}
	public String getMenu_code() {
		return menu_code;
	}
	public void setMenu_code(String menu_code) {
		this.menu_code = menu_code;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getExecuteNum() {
		return executeNum;
	}
	public void setExecuteNum(String executeNum) {
		this.executeNum = executeNum;
	}
}
