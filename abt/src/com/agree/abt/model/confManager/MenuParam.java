package com.agree.abt.model.confManager;
/**
 * 设备菜单私有参数
 * @author linyuedong
 */
public class MenuParam {
	private String menutype;//是否需要授权
	private String busitype;//所属业务类别
	private String menustatus;//
	private String disablestatus;//
	private String deleteflag;//
	private String menu_serv_time;
	public String getMenu_serv_time() {
		return menu_serv_time;
	}
	public void setMenu_serv_time(String menu_serv_time) {
		this.menu_serv_time = menu_serv_time;
	}
	public String getMenutype() {
		return menutype;
	}
	public void setMenutype(String menutype) {
		this.menutype = menutype;
	}
	public String getBusitype() {
		return busitype;
	}
	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}
	public String getMenustatus() {
		return menustatus;
	}
	public void setMenustatus(String menustatus) {
		this.menustatus = menustatus;
	}
	public String getDisablestatus() {
		return disablestatus;
	}
	public void setDisablestatus(String disablestatus) {
		this.disablestatus = disablestatus;
	}
	public String getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}
	public MenuParam(String menutype, String busitype, String menustatus,
			String disablestatus, String deleteflag,String menu_serv_time) {
		super();
		this.menutype = menutype;
		this.busitype = busitype;
		this.menustatus = menustatus;
		this.disablestatus = disablestatus;
		this.deleteflag = deleteflag;
		this.menu_serv_time = menu_serv_time;
	}
	public MenuParam() {
		super();
	}
	
}
