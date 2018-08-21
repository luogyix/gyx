/**
 * 
 */
package com.gyx.administration;

import java.util.Set;


public class Role{

	private String roleid;

	private String rolename;

	private String roledescribe;
	
	private String roletype;

	//private Long enabled;
	private String enabled;
	
	private String statusdesc;

	private Set<User> users;

	private Set<Module> modules;
	
	/**
	 * 角色级别  -- 2016-1-10
	 */
	private String rolelevel;
	
	//private Long createuser;
	private String createuser;
	
	private String createdate;
	
	//private Long lastmoduser;
	private String lastmoduser;
	
	private String lastmoddate;
	private String default_flag;//角色标识
	
	public String getDefault_flag() {
		return default_flag;
	}

	public void setDefault_flag(String default_flag) {
		this.default_flag = default_flag;
	}

	private String remark1;//客户化字段 add 2012-03-09
	private String remark2;//客户化字段 add 2012-03-09
	private String remark3;//客户化字段 add 2012-03-09

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRoledescribe() {
		return roledescribe;
	}

	public void setRoledescribe(String roledescribe) {
		this.roledescribe = roledescribe;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getStatusdesc() {
		return statusdesc;
	}

	public void setStatusdesc(String statusdesc) {
		this.statusdesc = statusdesc;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
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

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}

	public String getRolelevel() {
		return rolelevel;
	}

	public void setRolelevel(String rolelevel) {
		this.rolelevel = rolelevel;
	}

	
}
