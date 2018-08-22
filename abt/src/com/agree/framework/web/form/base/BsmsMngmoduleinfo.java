package com.agree.framework.web.form.base;

import java.util.Date;

/**
 * BsmsMngmoduleinfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class BsmsMngmoduleinfo implements java.io.Serializable {

	private static final long serialVersionUID = 2885380602437501467L;
	private String moduleid;
	private String modulename;
	private String parentmoduleid;
	private Long moduletype;
	private String moduleaction;
	private Long moduleorder;
	private Long modulelevel;
	private Long logflag;
	private Long privilegetype;
	private Long createuser;
	private Date createdate;
	private Long lastmoduser;
	private Date lastmoddate;

	// Constructors

	/** default constructor */
	public BsmsMngmoduleinfo() {
	}

	/** minimal constructor */
	public BsmsMngmoduleinfo(String moduleid) {
		this.moduleid = moduleid;
	}

	/** full constructor */
	public BsmsMngmoduleinfo(String moduleid, String modulename,
			String parentmoduleid, Long moduletype, String moduleaction,
			Long moduleorder, Long modulelevel, Long logflag,
			Long privilegetype, Long createuser, Date createdate,
			Long lastmoduser, Date lastmoddate) {
		this.moduleid = moduleid;
		this.modulename = modulename;
		this.parentmoduleid = parentmoduleid;
		this.moduletype = moduletype;
		this.moduleaction = moduleaction;
		this.moduleorder = moduleorder;
		this.modulelevel = modulelevel;
		this.logflag = logflag;
		this.privilegetype = privilegetype;
		this.createuser = createuser;
		this.createdate = createdate;
		this.lastmoduser = lastmoduser;
		this.lastmoddate = lastmoddate;
	}

	// Property accessors

	public String getModuleid() {
		return this.moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	public String getModulename() {
		return this.modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String getParentmoduleid() {
		return this.parentmoduleid;
	}

	public void setParentmoduleid(String parentmoduleid) {
		this.parentmoduleid = parentmoduleid;
	}

	public Long getModuletype() {
		return this.moduletype;
	}

	public void setModuletype(Long moduletype) {
		this.moduletype = moduletype;
	}

	public String getModuleaction() {
		return this.moduleaction;
	}

	public void setModuleaction(String moduleaction) {
		this.moduleaction = moduleaction;
	}

	public Long getModuleorder() {
		return this.moduleorder;
	}

	public void setModuleorder(Long moduleorder) {
		this.moduleorder = moduleorder;
	}

	public Long getModulelevel() {
		return this.modulelevel;
	}

	public void setModulelevel(Long modulelevel) {
		this.modulelevel = modulelevel;
	}

	public Long getLogflag() {
		return this.logflag;
	}

	public void setLogflag(Long logflag) {
		this.logflag = logflag;
	}

	public Long getPrivilegetype() {
		return this.privilegetype;
	}

	public void setPrivilegetype(Long privilegetype) {
		this.privilegetype = privilegetype;
	}

	public Long getCreateuser() {
		return this.createuser;
	}

	public void setCreateuser(Long createuser) {
		this.createuser = createuser;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Long getLastmoduser() {
		return this.lastmoduser;
	}

	public void setLastmoduser(Long lastmoduser) {
		this.lastmoduser = lastmoduser;
	}

	public Date getLastmoddate() {
		return this.lastmoddate;
	}

	public void setLastmoddate(Date lastmoddate) {
		this.lastmoddate = lastmoddate;
	}

}