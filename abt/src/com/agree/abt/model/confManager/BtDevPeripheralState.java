package com.agree.abt.model.confManager;


/**
 *
 * @classname:
 * @author wangguoqing
 * @company 赞同科技
 * 2015-4-2
 */

public class BtDevPeripheralState {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	/**
	 * 外设状态ID
	 */
	private String p_state_key;
	/**
	 * 外设状态名
	 */
	private String p_state_value;
	/**
	 * 外设类型ID
	 */
	private String p_type_key;
	
	public String getP_type_key() {
		return p_type_key;
	}
	public void setP_type_key(String p_type_key) {
		this.p_type_key = p_type_key;
	}
	public String getP_state_key() {
		return p_state_key;
	}
	public void setP_state_key(String p_state_key) {
		this.p_state_key = p_state_key;
	}
	public String getP_state_value() {
		return p_state_value;
	}
	public void setP_state_value(String p_state_value) {
		this.p_state_value = p_state_value;
	}
	public BtDevPeripheralState(String p_state_key, String p_state_value) {
		super();
		this.p_state_key = p_state_key;
		this.p_state_value = p_state_value;
	}
	public BtDevPeripheralState() {
		super();
	}
	
	
}
