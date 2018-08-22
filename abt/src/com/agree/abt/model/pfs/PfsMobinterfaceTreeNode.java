package com.agree.abt.model.pfs;

/**
 * 叫号机界面实体类
 * @ClassName: QMinterFaceCfg.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-10-16
 */
public class PfsMobinterfaceTreeNode {
	
	private String mobdevice_num;
	private String branch;
	private String node_id;
	private String node_level;
	private String node_location;//同级节点位置 --node_order
	private String child_node_order;//子节点列表
	private String node_type;//节点类型
	private String dir_name;//目录名称
	private String trade_id;//业务ID
	private String is_show_msg;//是否显示提示
	private String prompt_msg;//提示文字
	private String node_index;

	
	private String parent_id;
	private String node_name;

	
	
	
	public PfsMobinterfaceTreeNode() {
		super();
	}
	
	public String getMobdevice_num() {
		return mobdevice_num;
	}

	public void setMobdevice_num(String mobdeviceNum) {
		mobdevice_num = mobdeviceNum;
	}

	public String getTrade_id() {
		return trade_id;
	}

	public void setTrade_id(String tradeId) {
		trade_id = tradeId;
	}

	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getNode_id() {
		return node_id;
	}
	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}
	public String getNode_level() {
		return node_level;
	}
	public void setNode_level(String node_level) {
		this.node_level = node_level;
	}
	public String getNode_location() {
		return node_location;
	}
	public void setNode_location(String node_location) {
		this.node_location = node_location;
	}
	public String getChild_node_order() {
		return child_node_order;
	}
	public void setChild_node_order(String child_node_order) {
		this.child_node_order = child_node_order;
	}
	public String getNode_type() {
		return node_type;
	}
	public void setNode_type(String node_type) {
		this.node_type = node_type;
	}
	public String getDir_name() {
		return dir_name;
	}
	public void setDir_name(String dir_name) {
		this.dir_name = dir_name;
	}
	public String getIs_show_msg() {
		return is_show_msg;
	}
	public void setIs_show_msg(String is_show_msg) {
		this.is_show_msg = is_show_msg;
	}
	public String getPrompt_msg() {
		return prompt_msg;
	}
	public void setPrompt_msg(String prompt_msg) {
		this.prompt_msg = prompt_msg;
	}
	public String getNode_index() {
		return node_index;
	}
	public void setNode_index(String node_index) {
		this.node_index = node_index;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getNode_name() {
		return node_name;
	}

	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}


}
