package com.agree.abt.model.devmanager;

import java.io.Serializable;

/**
 * 设备通用参数实体类
 */
public class DeviceParam implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dev_param_id;
	private String dev_param_name;
	private String dev_param_data;
	private String param_type;
	private String channel_type;
	private String default_flag;
	private String branch;
	private String ext1;
	private String ext2;
	
	public String getDev_param_id() {
		return dev_param_id;
	}
	public void setDev_param_id(String dev_param_id) {
		this.dev_param_id = dev_param_id;
	}
	public String getDev_param_name() {
		return dev_param_name;
	}
	public void setDev_param_name(String dev_param_name) {
		this.dev_param_name = dev_param_name;
	}
	public String getDev_param_data() {
		return dev_param_data;
	}
	public void setDev_param_data(String dev_param_data) {
		this.dev_param_data = dev_param_data;
	}
	public String getParam_type() {
		return param_type;
	}
	public void setParam_type(String param_type) {
		this.param_type = param_type;
	}
	public String getChannel_type() {
		return channel_type;
	}
	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}
	public String getDefault_flag() {
		return default_flag;
	}
	public void setDefault_flag(String default_flag) {
		this.default_flag = default_flag;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public DeviceParam(String dev_param_id, String dev_param_name,
			String dev_param_data, String param_type, String channel_type,
			String default_flag, String branch, String ext1, String ext2) {
		super();
		this.dev_param_id = dev_param_id;
		this.dev_param_name = dev_param_name;
		this.dev_param_data = dev_param_data;
		this.param_type = param_type;
		this.channel_type = channel_type;
		this.default_flag = default_flag;
		this.branch = branch;
		this.ext1 = ext1;
		this.ext2 = ext2;
	}
	public DeviceParam() {
		super();
	}
}
