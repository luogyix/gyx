package com.agree.abt.model.devmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备菜单节点公共实体父类
 */
public class DeviceMenuNode implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String parameter_id;//参数ID
	private String menu_id;//菜单ID
	private String menu_name_ch;//菜单中文名
	private String menu_name_en;//菜单英文名
	private String menu_level;//菜单层级
	private String menu_location;//菜单位置
	private String menu_type;//菜单类型
	private String bs_id;//业务ID
	private String menu_parent_id;//父节点
	private String ext1;
	private String ext2;
	private List<DeviceMenuNode> childrens = new ArrayList<DeviceMenuNode>();
//	private MenuParam menuparam;
	public String getParameter_id() {
		return parameter_id;
	}
	public void setParameter_id(String parameter_id) {
		this.parameter_id = parameter_id;
	}
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public String getMenu_name_ch() {
		return menu_name_ch;
	}
	public void setMenu_name_ch(String menu_name_ch) {
		this.menu_name_ch = menu_name_ch;
	}
	public String getMenu_name_en() {
		return menu_name_en;
	}
	public void setMenu_name_en(String menu_name_en) {
		this.menu_name_en = menu_name_en;
	}
	public String getMenu_level() {
		return menu_level;
	}
	public void setMenu_level(String menu_level) {
		this.menu_level = menu_level;
	}
	public String getMenu_location() {
		return menu_location;
	}
	public void setMenu_location(String menu_location) {
		this.menu_location = menu_location;
	}
	public String getMenu_type() {
		return menu_type;
	}
	public void setMenu_type(String menu_type) {
		this.menu_type = menu_type;
	}
	public String getBs_id() {
		return bs_id;
	}
	public void setBs_id(String bs_id) {
		this.bs_id = bs_id;
	}
	public String getMenu_parent_id() {
		return menu_parent_id;
	}
	public void setMenu_parent_id(String menu_parent_id) {
		this.menu_parent_id = menu_parent_id;
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
	public List<DeviceMenuNode> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<DeviceMenuNode> childrens) {
		this.childrens = childrens;
	}
	
	/**
	 * 添加子节点
	 */
	public void setChildren(DeviceMenuNode child) {
		childrens.add(child);
	}
	
	/**
	 * 删除子节点
	 */
	public void removeChildren(DeviceMenuNode child) {
		childrens.remove(child);
	}
	
	
}
