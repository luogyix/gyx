/**
 * 
 */
package com.gyx.administration;

import java.util.ArrayList;
import java.util.List;


public class Unit {
	
	/**
	 * 机构号
	 */
	private String unitid="";
	
	/**
	 * 机构名称
	 */
	private String unitname;
	
	/**
	 * 部门级次，即部门树深度
	 */
	private String unitlevel;
	
	/**
	 * 父节点ID 所属分行
	 */
	private String parentunitid;
	
	/**
	 * 父节点名
	 */
	private String parentName;
	
	/**
	 * 机构树List
	 */
	private List<Unit> unitTreeList;
	
	/**
	 * 创建者
	 */
	private String createuser;
	
	/**
	 * 创建日期
	 */
	private String createdate;
	
	/**
	 * 最后修改者
	 */
	private String lastmoduser;
	
	/**
	 * 最后修改时间
	 */
	private String lastmoddate;
	
	/**
	 * 父节点unit
	 */
	private Unit parentNode = null;
	
	/**
	 * 子节点列表(unit)
	 */
	private List<Unit> childNodes = null;
	
	/**
	 * 排序标示
	 */
	private String unitorder;
	/**
	 * 归属部门列表
	 */
	private String unitlist;
	/**
	 * 机构地址
	 */
	private String address;
	/**
	 * GPS经度
	 */
	private String longitude;
	/**
	 * GPS纬度
	 */
	private String latitude;
	/**
	 * 机构级别
	 */
	private String bank_level;
	/**
	 * 附近车站
	 */
	private String near_station;
	/**
	 * 网点电话
	 */
	private String bank_tel;
	/**
	 * 负责人
	 */
	private String manager_name;
	/**
	 * 负责人手机
	 */
	private String manager_phone;
	/**
	 * 负责人电话
	 */
	private String manager_tel;
	/**
	 * 所在城市
	 */
	private String city;
	/**
	 * 备注
	 */
	private String note;
	private String unitid_old;
	/**
	 * 机构专属校验码
	 */
	private String checkcode;

	public Unit(){
	}
	
	public Unit(Unit unit){
		this.unitid = unit.getUnitid();
		this.unitname = unit.getUnitname();
		this.unitlevel = unit.getUnitlevel();
		this.parentunitid = unit.getParentunitid();
		this.parentName = unit.getParentName();
		this.unitorder = unit.getUnitorder();
		this.unitlist = unit.getUnitlist();
		this.createuser = unit.getCreateuser();
		this.createdate = unit.getCreatedate();
		this.lastmoddate = unit.getLastmoddate();
		this.lastmoduser = unit.getLastmoduser();
		
		this.address = unit.getAddress();
		this.longitude = unit.getLongitude();
		this.latitude = unit.getLatitude();
		this.bank_level = unit.getBank_level();
		this.near_station = unit.getNear_station();
		this.bank_tel = unit.getBank_tel();
		this.manager_name = unit.getManager_name();
		this.manager_phone = unit.getManager_phone();
		this.manager_tel = unit.getManager_tel();
		this.city = unit.getCity();
		this.note = unit.getNote();
		this.checkcode = unit.getCheckcode();
	}
	
	public List<Unit> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<Unit> childNodes) {
		this.childNodes = childNodes;
	}

	public Unit findNodeById(String id){
		//判断用户的 机构id 和 本实例对象中的机构id是否相等
		if(this.unitid.equals(id)){
			Unit a = this;
			//相等时返回此实例对象
			return a;
		}else{//不想等时
			//获得 本实例对象的  子机构id 列表
			List<Unit> childList = this.childNodes;
			//判断此 机构实例对象的子机构id列表是否为null
			if(childList == null){
				//返回null
				return null;
			}else{//此 机构实例对象的子机构id列表不为null时
				for(Unit unit : childList){
					//如果子机构 id列表任何元素 和传递多来的机构id匹配时
					Unit temp_unit = unit.findNodeById(id);
					if(temp_unit != null){
						//返回此机构id对应的机构对象
						return temp_unit;
					}
				}
				return null;
			}
		}
	}
	
	public void addChildNode(Unit unit){
		if(this.childNodes == null){
			this.childNodes = new ArrayList<Unit>();
		}
		this.childNodes.add(unit);
		unit.parentNode = this;
	}
	
	public Unit getAncestorAtLevel(Integer level){
		if(this.parentNode.getUnitlevel().equals(level)){
			return this.parentNode;
		}else if(Integer.parseInt(this.parentNode.getUnitlevel())> level){
			return this.parentNode.getAncestorAtLevel(level);
		}else{
			return null;
		}
	}
	
	public List<Unit> getDescendantAtLevel(Integer level){
		List<Unit> temp_list = new ArrayList<Unit>();
		if(this.childNodes == null){
			return null;
		}else{
			if(this.childNodes.get(0).getUnitlevel().equals(level)){
				return this.childNodes;
			}else if(Integer.parseInt(this.childNodes.get(0).getUnitlevel()) < level){
				for(Unit unit : this.childNodes){
					List<Unit> child_temp_list = unit.getDescendantAtLevel(level);
					if(child_temp_list != null){
						temp_list.addAll(child_temp_list);
					}
				}
				return temp_list;
			}else{
				return null;
			}
		}
	}

	public void setUnitTreeList(List<Unit> unitTreeList) {
		this.unitTreeList = unitTreeList;
	}

	public List<Unit> getUnitTreeList() {
		return unitTreeList;
	}
	public List<Unit> getUnitTreeList(int root_space_number){
		List<Unit> outList = new ArrayList<Unit>();
		Unit unit = new Unit(this);
		
		/*
		 * 疑似无用的操作
		 * StringBuffer sb = new StringBuffer();
		for(int i=0;i<root_space_number;i++){
			//sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		sb.append(unit.getUnitname());
		unit.setUnitname(sb.toString());
		*/
		
		//将 此unit对象 添加 到unit列表中
		outList.add(unit);
		//判断此unit对象的 子机构列表是否为空
		if(this.childNodes != null){//不为空时
			for(Unit child_unit : this.childNodes){
				List<Unit> child_unit_list = child_unit.getUnitTreeList(root_space_number + 1);
				outList.addAll(child_unit_list);
			}
		}
		return outList;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}


	public String getLastmoduser() {
		return lastmoduser;
	}

	public void setLastmoduser(String lastmoduser) {
		this.lastmoduser = lastmoduser;
	}

	public Unit getParentNode() {
		return parentNode;
	}

	public void setParentNode(Unit parentNode) {
		this.parentNode = parentNode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public String getUnitlevel() {
		return unitlevel;
	}

	public void setUnitlevel(String unitlevel) {
		this.unitlevel = unitlevel;
	}

	public String getParentunitid() {
		return parentunitid;
	}

	public void setParentunitid(String parentunitid) {
		this.parentunitid = parentunitid;
	}

	public String getUnitorder() {
		return unitorder;
	}

	public void setUnitorder(String unitorder) {
		this.unitorder = unitorder;
	}

	public String getUnitlist() {
		return unitlist;
	}

	public void setUnitlist(String unitlist) {
		this.unitlist = unitlist;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getBank_level() {
		return bank_level;
	}

	public void setBank_level(String bank_level) {
		this.bank_level = bank_level;
	}

	public String getNear_station() {
		return near_station;
	}

	public void setNear_station(String near_station) {
		this.near_station = near_station;
	}

	public String getBank_tel() {
		return bank_tel;
	}

	public void setBank_tel(String bank_tel) {
		this.bank_tel = bank_tel;
	}

	public String getManager_name() {
		return manager_name;
	}

	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}

	public String getManager_phone() {
		return manager_phone;
	}

	public void setManager_phone(String manager_phone) {
		this.manager_phone = manager_phone;
	}

	public String getManager_tel() {
		return manager_tel;
	}

	public void setManager_tel(String manager_tel) {
		this.manager_tel = manager_tel;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCheckcode() {
		return checkcode;
	}

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public String getUnitid_old() {
		return unitid_old;
	}

	public void setUnitid_old(String unitid_old) {
		this.unitid_old = unitid_old;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getLastmoddate() {
		return lastmoddate;
	}

	public void setLastmoddate(String lastmoddate) {
		this.lastmoddate = lastmoddate;
	}
}
