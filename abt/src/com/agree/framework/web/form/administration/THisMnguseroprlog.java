package com.agree.framework.web.form.administration;

/**
 * THisMnguseroprlog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class THisMnguseroprlog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer unitid;
	private String logdate;
	private String logtime;
	private String username;
	private String hostip;
	private String operation;
	private String result;
	private String usercode;
	private String unitname;
	private String operdetail;

	// Constructors

	/** default constructor */
	public THisMnguseroprlog() {
	}

	/** full constructor */
	public THisMnguseroprlog(Integer unitid, String logdate, String logtime,
			String username, String hostip, String operation, String result,
			String usercode, String unitname, String operdetail) {
		this.unitid = unitid;
		this.logdate = logdate;
		this.logtime = logtime;
		this.username = username;
		this.hostip = hostip;
		this.operation = operation;
		this.result = result;
		this.usercode = usercode;
		this.unitname = unitname;
		this.operdetail = operdetail;
	}
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof THisMnguseroprlog))
			return false;
		THisMnguseroprlog castOther = (THisMnguseroprlog) other;

		return ((this.getUnitid() == castOther.getUnitid()) || (this
				.getUnitid() != null
				&& castOther.getUnitid() != null && this.getUnitid().equals(
				castOther.getUnitid())))
				&& ((this.getLogdate() == castOther.getLogdate()) || (this
						.getLogdate() != null
						&& castOther.getLogdate() != null && this.getLogdate()
						.equals(castOther.getLogdate())))
				&& ((this.getLogtime() == castOther.getLogtime()) || (this
						.getLogtime() != null
						&& castOther.getLogtime() != null && this.getLogtime()
						.equals(castOther.getLogtime())))
				&& ((this.getUsername() == castOther.getUsername()) || (this
						.getUsername() != null
						&& castOther.getUsername() != null && this
						.getUsername().equals(castOther.getUsername())))
				&& ((this.getHostip() == castOther.getHostip()) || (this
						.getHostip() != null
						&& castOther.getHostip() != null && this.getHostip()
						.equals(castOther.getHostip())))
				&& ((this.getOperation() == castOther.getOperation()) || (this
						.getOperation() != null
						&& castOther.getOperation() != null && this
						.getOperation().equals(castOther.getOperation())))
				&& ((this.getResult() == castOther.getResult()) || (this
						.getResult() != null
						&& castOther.getResult() != null && this.getResult()
						.equals(castOther.getResult())))
				&& ((this.getUsercode() == castOther.getUsercode()) || (this
						.getUsercode() != null
						&& castOther.getUsercode() != null && this
						.getUsercode().equals(castOther.getUsercode())))
				&& ((this.getUnitname() == castOther.getUnitname()) || (this
						.getUnitname() != null
						&& castOther.getUnitname() != null && this
						.getUnitname().equals(castOther.getUnitname())))
				&& ((this.getOperdetail() == castOther.getOperdetail()) || (this
						.getOperdetail() != null
						&& castOther.getOperdetail() != null && this
						.getOperdetail().equals(castOther.getOperdetail())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUnitid() == null ? 0 : this.getUnitid().hashCode());
		result = 37 * result
				+ (getLogdate() == null ? 0 : this.getLogdate().hashCode());
		result = 37 * result
				+ (getLogtime() == null ? 0 : this.getLogtime().hashCode());
		result = 37 * result
				+ (getUsername() == null ? 0 : this.getUsername().hashCode());
		result = 37 * result
				+ (getHostip() == null ? 0 : this.getHostip().hashCode());
		result = 37 * result
				+ (getOperation() == null ? 0 : this.getOperation().hashCode());
		result = 37 * result
				+ (getResult() == null ? 0 : this.getResult().hashCode());
		result = 37 * result
				+ (getUsercode() == null ? 0 : this.getUsercode().hashCode());
		result = 37 * result
				+ (getUnitname() == null ? 0 : this.getUnitname().hashCode());
		result = 37
				* result
				+ (getOperdetail() == null ? 0 : this.getOperdetail()
						.hashCode());
		return result;
	}

	public Integer getUnitid() {
		return unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}

	public String getLogdate() {
		return logdate;
	}

	public void setLogdate(String logdate) {
		this.logdate = logdate;
	}

	public String getLogtime() {
		return logtime;
	}

	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHostip() {
		return hostip;
	}

	public void setHostip(String hostip) {
		this.hostip = hostip;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public String getOperdetail() {
		return operdetail;
	}

	public void setOperdetail(String operdetail) {
		this.operdetail = operdetail;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}