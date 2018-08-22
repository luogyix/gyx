package com.agree.abt.model.confManager;
/**
 * 设备通用参数表实体类
 * @author linyuedong
 */
public class BtDevParam {
	private String dev_param_id;
	private String dev_param_name;
	private String dev_param_data;
	private String param_type;
	private String exp1;
	private String exp2;
	private String parameter_id;
	public String getParameter_id() {
		return parameter_id;
	}
	public void setParameter_id(String parameter_id) {
		this.parameter_id = parameter_id;
	}
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
	public String getExp1() {
		return exp1;
	}
	public void setExp1(String exp1) {
		this.exp1 = exp1;
	}
	public String getExp2() {
		return exp2;
	}
	public void setExp2(String exp2) {
		this.exp2 = exp2;
	}
	public BtDevParam(String dev_param_id, String dev_param_name,
			String dev_param_data, String param_type, String exp1, String exp2,String parameter_id) {
		super();
		this.dev_param_id = dev_param_id;
		this.dev_param_name = dev_param_name;
		this.dev_param_data = dev_param_data;
		this.param_type = param_type;
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.parameter_id =parameter_id;
	}
	public BtDevParam() {
		super();
	}
	
}
