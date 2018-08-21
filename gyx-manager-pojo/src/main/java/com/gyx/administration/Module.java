
package com.gyx.administration;

import java.util.Set;

public class Module{
	
	private String      moduleid;
	
	private String    modulename;
	
	private String      parentmoduleid;
	
	private Short     moduletype;
	
	private String    moduleaction;
	
	private Integer   moduleorder;
	
	private Short   modulelevel;
	
	private Short   logflag;

	private Integer privilegeType;
	
	private Set<Role> roles;

	private Boolean isleaf = false;
	
	private String createuser;
	private String createdate;
	private String lastmoduser;
	private String lastmoddate;
	
	private String moduleImg;
	
	private String authflag;
	
	private String remark1;//客户化字段 add 2012-03-09
	private String remark2;//客户化字段 add 2012-03-09
	private String remark3;//客户化字段 add 2012-03-09
	

	public Module() {
		super();
	}

	public String getAuthflag() {
		return authflag;
	}

	public void setAuthflag(String authflag) {
		this.authflag = authflag;
	}

	public String getModuleImg() {
		return moduleImg;
	}

	public void setModuleImg(String moduleImg) {
		this.moduleImg = moduleImg;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getLastmoduser() {
		return lastmoduser;
	}

	public void setLastmoduser(String lastmoduser) {
		this.lastmoduser = lastmoduser;
	}

	public String getLastmoddate() {
		return lastmoddate;
	}

	public void setLastmoddate(String lastmoddate) {
		this.lastmoddate = lastmoddate;
	}

	public Integer getPrivilegeType() {
		return privilegeType;
	}

	public void setPrivilegeType(Integer privilegeType) {
		this.privilegeType = privilegeType;
	}

	public String getModuleid() {
		return moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String getParentmoduleid() {
		return parentmoduleid;
	}

	public void setParentmoduleid(String parentmoduleid) {
		this.parentmoduleid = parentmoduleid;
	}

	public Short getModuletype() {
		return moduletype;
	}

	public void setModuletype(Short moduletype) {
		this.moduletype = moduletype;
	}

	public String getModuleaction() {
		return moduleaction;
	}

	public void setModuleaction(String moduleaction) {
		this.moduleaction = moduleaction;
	}

	public Integer getModuleorder() {
		return moduleorder;
	}

	public void setModuleorder(Integer moduleorder) {
		this.moduleorder = moduleorder;
	}

	public Short getModulelevel() {
		return modulelevel;
	}

	public void setModulelevel(Short modulelevel) {
		this.modulelevel = modulelevel;
	}
	
	public Short getLogflag() {
		return logflag;
	}

	public void setLogflag(Short logflag) {
		this.logflag = logflag;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Boolean getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(Boolean isleaf) {
		this.isleaf = isleaf;
	}

	public int compareTo(Object o) {
//		Module mo=(Module)o;
//		if(mo.getModulelevel()>this.getModulelevel()){
//			return 1;
//		}else{
//			return -1;
//		}
		return 1;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authflag == null) ? 0 : authflag.hashCode());
		result = prime * result
				+ ((createdate == null) ? 0 : createdate.hashCode());
		result = prime * result
				+ ((createuser == null) ? 0 : createuser.hashCode());
		result = prime * result + ((isleaf == null) ? 0 : isleaf.hashCode());
		result = prime * result
				+ ((lastmoddate == null) ? 0 : lastmoddate.hashCode());
		result = prime * result
				+ ((lastmoduser == null) ? 0 : lastmoduser.hashCode());
		result = prime * result + ((logflag == null) ? 0 : logflag.hashCode());
		result = prime * result
				+ ((moduleImg == null) ? 0 : moduleImg.hashCode());
		result = prime * result
				+ ((moduleaction == null) ? 0 : moduleaction.hashCode());
		result = prime * result
				+ ((moduleid == null) ? 0 : moduleid.hashCode());
		result = prime * result
				+ ((modulelevel == null) ? 0 : modulelevel.hashCode());
		result = prime * result
				+ ((modulename == null) ? 0 : modulename.hashCode());
		result = prime * result
				+ ((moduleorder == null) ? 0 : moduleorder.hashCode());
		result = prime * result
				+ ((moduletype == null) ? 0 : moduletype.hashCode());
		result = prime * result
				+ ((parentmoduleid == null) ? 0 : parentmoduleid.hashCode());
		result = prime * result
				+ ((privilegeType == null) ? 0 : privilegeType.hashCode());
		result = prime * result + ((remark1 == null) ? 0 : remark1.hashCode());
		result = prime * result + ((remark2 == null) ? 0 : remark2.hashCode());
		result = prime * result + ((remark3 == null) ? 0 : remark3.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Module other = (Module) obj;
		if (authflag == null) {
			if (other.authflag != null)
				return false;
		} else if (!authflag.equals(other.authflag))
			return false;
		if (createdate == null) {
			if (other.createdate != null)
				return false;
		} else if (!createdate.equals(other.createdate))
			return false;
		if (createuser == null) {
			if (other.createuser != null)
				return false;
		} else if (!createuser.equals(other.createuser))
			return false;
		if (isleaf == null) {
			if (other.isleaf != null)
				return false;
		} else if (!isleaf.equals(other.isleaf))
			return false;
		if (lastmoddate == null) {
			if (other.lastmoddate != null)
				return false;
		} else if (!lastmoddate.equals(other.lastmoddate))
			return false;
		if (lastmoduser == null) {
			if (other.lastmoduser != null)
				return false;
		} else if (!lastmoduser.equals(other.lastmoduser))
			return false;
		if (logflag == null) {
			if (other.logflag != null)
				return false;
		} else if (!logflag.equals(other.logflag))
			return false;
		if (moduleImg == null) {
			if (other.moduleImg != null)
				return false;
		} else if (!moduleImg.equals(other.moduleImg))
			return false;
		if (moduleaction == null) {
			if (other.moduleaction != null)
				return false;
		} else if (!moduleaction.equals(other.moduleaction))
			return false;
		if (moduleid == null) {
			if (other.moduleid != null)
				return false;
		} else if (!moduleid.equals(other.moduleid))
			return false;
		if (modulelevel == null) {
			if (other.modulelevel != null)
				return false;
		} else if (!modulelevel.equals(other.modulelevel))
			return false;
		if (modulename == null) {
			if (other.modulename != null)
				return false;
		} else if (!modulename.equals(other.modulename))
			return false;
		if (moduleorder == null) {
			if (other.moduleorder != null)
				return false;
		} else if (!moduleorder.equals(other.moduleorder))
			return false;
		if (moduletype == null) {
			if (other.moduletype != null)
				return false;
		} else if (!moduletype.equals(other.moduletype))
			return false;
		if (parentmoduleid == null) {
			if (other.parentmoduleid != null)
				return false;
		} else if (!parentmoduleid.equals(other.parentmoduleid))
			return false;
		if (privilegeType == null) {
			if (other.privilegeType != null)
				return false;
		} else if (!privilegeType.equals(other.privilegeType))
			return false;
		if (remark1 == null) {
			if (other.remark1 != null)
				return false;
		} else if (!remark1.equals(other.remark1))
			return false;
		if (remark2 == null) {
			if (other.remark2 != null)
				return false;
		} else if (!remark2.equals(other.remark2))
			return false;
		if (remark3 == null) {
			if (other.remark3 != null)
				return false;
		} else if (!remark3.equals(other.remark3))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		return true;
	}

	public Module(String moduleid, String modulename, String parentmoduleid,
			Short moduletype, String moduleaction, Integer moduleorder,
			Short modulelevel, Short logflag, Integer privilegeType,
			String createuser, String createdate,
			String lastmoduser, String lastmoddate, String moduleImg,
			String authflag, String remark1, String remark2, String remark3) {
		super();
		this.moduleid = moduleid;
		this.modulename = modulename;
		this.parentmoduleid = parentmoduleid;
		this.moduletype = moduletype;
		this.moduleaction = moduleaction;
		this.moduleorder = moduleorder;
		this.modulelevel = modulelevel;
		this.logflag = logflag;
		this.privilegeType = privilegeType;
		this.createuser = createuser;
		this.createdate = createdate;
		this.lastmoduser = lastmoduser;
		this.lastmoddate = lastmoddate;
		this.moduleImg = moduleImg;
		this.authflag = authflag;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.remark3 = remark3;
	}


	

}
