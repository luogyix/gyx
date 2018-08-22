package com.agree.abt.model.confManager;

import java.io.Serializable;

public class BtDevPeripheralTypeState implements Serializable{
	private static final long serialVersionUID = 1L;
/**
	 * 外设类型ID
 */
	private String p_state_key;
	/**
	 * 外设状态ID
	 */
	private String p_type_key;
	public String getP_state_key() {
		return p_state_key;
	}
	public void setP_state_key(String p_state_key) {
		this.p_state_key = p_state_key;
	}
	public String getP_type_key() {
		return p_type_key;
	}
	public void setP_type_key(String p_type_key) {
		this.p_type_key = p_type_key;
	}
	public BtDevPeripheralTypeState(String p_state_key, String p_type_key) {
		super();
		this.p_state_key = p_state_key;
		this.p_type_key = p_type_key;
	}
	public BtDevPeripheralTypeState() {
		super();
	}
}
