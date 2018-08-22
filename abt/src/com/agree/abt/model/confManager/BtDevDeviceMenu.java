package com.agree.abt.model.confManager;

import java.io.Serializable;

/**
 * 自助机菜单实体类
 * @author linyuedong
 * @date 2015-4-2
 */
public class BtDevDeviceMenu implements Serializable{
	private static final long serialVersionUID = 8214929216613850170L;
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
	private String menu_param;//设备菜单私有参数
	private MenuParam menuparam;
	public MenuParam getMenuparam() {
		return menuparam;
	}
	public void setMenuparam(MenuParam menuparam) {
		this.menuparam = menuparam;
	}
	public String getMenu_param() {
		return menu_param;
	}
	public void setMenu_param(String menuparam) {
		this.menu_param = menuparam;
	}
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
	public BtDevDeviceMenu(String parameter_id, String menu_id,
			String menu_name_ch, String menu_name_en, String menu_level,
			String menu_location, String menu_type, String bs_id,
			String menu_parent_id, String ext1, String ext2,String menu_param,MenuParam menuParam) {
		super();
		this.parameter_id = parameter_id;
		this.menu_id = menu_id;
		this.menu_name_ch = menu_name_ch;
		this.menu_name_en = menu_name_en;
		this.menu_level = menu_level;
		this.menu_location = menu_location;
		this.menu_type = menu_type;
		this.bs_id = bs_id;
		this.menu_parent_id = menu_parent_id;
		this.menu_param = menu_param;
		this.ext1 = ext1;
		this.ext2 = ext2;
		this.menuparam = menuParam;
	}
	public BtDevDeviceMenu() {
		super();
	}
	
}
