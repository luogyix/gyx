package com.agree.abt.model.devmanager;

import java.io.Serializable;

/**
 * 设备信息实体类
 * @company 赞同科技
 */
public class DeviceInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 设备编号
	 */
	private DeviceInfoPK devicePK;
	/**
	 * 设备名称
	 */
	private String device_name;
	/**
	 * 设备OID唯一标识
	 */
	private String device_oid;
	/**
	 * 设备型号
	 */
	private String device_model;
	/**
	 * 设备IP
	 */
	private String device_ip;
	/**
	 * 设备主管柜员号
	 */
	private String deviceadm_num;
	/**
	 * 设备主管姓名
	 */
	private String deviceadm_name;
	/**
	 * 设备状态
	 */
	private String device_status;
	/**
	 * 设备参数编号
	 */
	private String device_param_id;
	/**
	 * 设备校验类型
	 */
	private String device_chk_type;
	/**
	 * 设备校验信息
	 */
	private String device_chk_info;
	/**
	 * 关联排队机号
	 */
	private String qm_num;
	/**
	 * 关联排队机IP
	 */
	private String qm_ip;
	

	public DeviceInfo(DeviceInfoPK devicePK, String device_name,
			String device_oid, String device_model, String device_ip,
			String deviceadm_num, String deviceadm_name, String device_status,
			String device_param_id, String device_chk_type,
			String device_chk_info, String qm_num, String qm_ip) {
		super();
		this.devicePK = devicePK;
		this.device_name = device_name;
		this.device_oid = device_oid;
		this.device_model = device_model;
		this.device_ip = device_ip;
		this.deviceadm_num = deviceadm_num;
		this.deviceadm_name = deviceadm_name;
		this.device_status = device_status;
		this.device_param_id = device_param_id;
		this.device_chk_type = device_chk_type;
		this.device_chk_info = device_chk_info;
		this.qm_num = qm_num;
		this.qm_ip = qm_ip;
	}
	public DeviceInfo() {
		super();
	}
	
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getDevice_oid() {
		return device_oid;
	}
	public void setDevice_oid(String device_oid) {
		this.device_oid = device_oid;
	}
	public String getDevice_model() {
		return device_model;
	}
	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}
	public String getDevice_ip() {
		return device_ip;
	}
	public void setDevice_ip(String device_ip) {
		this.device_ip = device_ip;
	}
	public String getDeviceadm_num() {
		return deviceadm_num;
	}
	public void setDeviceadm_num(String deviceadm_num) {
		this.deviceadm_num = deviceadm_num;
	}
	public String getDeviceadm_name() {
		return deviceadm_name;
	}
	public void setDeviceadm_name(String deviceadm_name) {
		this.deviceadm_name = deviceadm_name;
	}
	public String getDevice_status() {
		return device_status;
	}
	public void setDevice_status(String device_status) {
		this.device_status = device_status;
	}
	public String getDevice_param_id() {
		return device_param_id;
	}
	public void setDevice_param_id(String device_param_id) {
		this.device_param_id = device_param_id;
	}
	public String getDevice_chk_type() {
		return device_chk_type;
	}
	public void setDevice_chk_type(String device_chk_type) {
		this.device_chk_type = device_chk_type;
	}
	public String getDevice_chk_info() {
		return device_chk_info;
	}
	public void setDevice_chk_info(String device_chk_info) {
		this.device_chk_info = device_chk_info;
	}
	public String getQm_num() {
		return qm_num;
	}
	public void setQm_num(String qm_num) {
		this.qm_num = qm_num;
	}
	public String getQm_ip() {
		return qm_ip;
	}
	public void setQm_ip(String qm_ip) {
		this.qm_ip = qm_ip;
	}
	public DeviceInfoPK getDevicePK() {
		return devicePK;
	}
	public void setDevicePK(DeviceInfoPK devicePK) {
		this.devicePK = devicePK;
	}
}
