package com.agree.abt.model.confManager;

import java.io.Serializable;

/**
 * 界面配置表
 * @author hujuqiang
 * @date   2016-9-1
 */
@SuppressWarnings("serial")
public class BtSysInterface implements Serializable{
	private String interface_id;//界面id PK
	private String interface_name;//界面名称
	private String node_id;//节点id PK
	private String child_id;//子节点id,用@进行分隔
	private String parent_id;//父节点id
	private String node_type;//节点类型,0-系统，1-目录 2-业务
	private String node_level;//节点层级
	private String node_name_ch;//节点中文名
	private String node_name_en;//节点英文名
	private String node_location;//节点顺序
	private String metadata_id;//元数据id
	public String getInterface_name() {
		return interface_name;
	}
	public void setInterface_name(String interface_name) {
		this.interface_name = interface_name;
	}
	public String getInterface_id() {
		return interface_id;
	}
	public void setInterface_id(String interface_id) {
		this.interface_id = interface_id;
	}
	public String getNode_id() {
		return node_id;
	}
	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}
	public String getChild_id() {
		return child_id;
	}
	public void setChild_id(String child_id) {
		this.child_id = child_id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getNode_type() {
		return node_type;
	}
	public void setNode_type(String node_type) {
		this.node_type = node_type;
	}
	public String getNode_level() {
		return node_level;
	}
	public void setNode_level(String node_level) {
		this.node_level = node_level;
	}
	public String getNode_name_ch() {
		return node_name_ch;
	}
	public void setNode_name_ch(String node_name_ch) {
		this.node_name_ch = node_name_ch;
	}
	public String getNode_name_en() {
		return node_name_en;
	}
	public void setNode_name_en(String node_name_en) {
		this.node_name_en = node_name_en;
	}
	public String getNode_location() {
		return node_location;
	}
	public void setNode_location(String node_location) {
		this.node_location = node_location;
	}
	public String getMetadata_id() {
		return metadata_id;
	}
	public void setMetadata_id(String metadata_id) {
		this.metadata_id = metadata_id;
	}
}