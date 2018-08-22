package com.agree.abt.model.confManager;

public class BtDevPeripheralType {

	private String p_type_key;

	private String p_type_value;

	private String p_type_devicetype;

	private String exp1;
	
	
	
	public String getP_type_key() {
		return p_type_key;
	}
	public void setP_type_key(String p_type_key) {
		this.p_type_key = p_type_key;
	}
	public String getP_type_value() {
		return p_type_value;
	}
	public void setP_type_value(String p_type_value) {
		this.p_type_value = p_type_value;
	}
	public String getP_type_devicetype() {
		return p_type_devicetype;
	}
	public void setP_type_devicetype(String p_type_devicetype) {
		this.p_type_devicetype = p_type_devicetype;
	}
	public String getExp1() {
		return exp1;
	}
	public void setExp1(String exp1) {
		this.exp1 = exp1;
	}
	public BtDevPeripheralType(String p_type_key, String p_type_value,
			String p_type_devicetype, String exp1) {
		super();
		this.p_type_key = p_type_key;
		this.p_type_value = p_type_value;
		this.p_type_devicetype = p_type_devicetype;
		this.exp1 = exp1;
	}
	public BtDevPeripheralType() {
		super();
	}
	
}
