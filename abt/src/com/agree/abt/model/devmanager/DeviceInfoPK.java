package com.agree.abt.model.devmanager;

import java.io.Serializable;

public class DeviceInfoPK implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 机构号
	 */
	private String branch;
	/**
	 * 设备类型
	 */
	private String devicetype;
	/**
	 * 设备编号
	 */
	private String device_num;
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public String getDevice_num() {
		return device_num;
	}
	public void setDevice_num(String device_num) {
		this.device_num = device_num;
	}
}
