package com.gyx.administration;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * 
 * @ClassName: User 
 * @company agree   
 * @author lilei
 * @date 2011-8-23 上午10:51:51 
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class User{
	private static final long serialVersionUID = 1L;
	
	private String userid;//用户id
	private String usercode;//用户登录号
	private String username;//用户名称
	private String password;//密码
	private String passwd;//密码
	private Long sex;//性别
	private String mailbox;//邮箱
	private String telphone;//电话
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	private String cellphone;//手机
	
	private String createuser;//创建用户
	private String createdate;//创建日期
	private String lastmoduser;//最后修改用户
	private String lastmoddate;//最后修改日期
	
	private String state;//状态
	private String operatorcardid;
	private String usertype;//类型
	private String sexdesc;
	private String statedesc;
	private String usertypedesc;
	private String unitid;
	private String unitname;
	private Set<Role> roles;
	private Unit unit;
	private Set<Module> catalog_and_privileges = new LinkedHashSet();
	
	private String infoex1;
	private String infoex2;
	private String infoex3;
	private String infoex4;
	private String infoex5;
	private String hostip;//最近登录客户端
	private String logintime;//最近登录时间
	private Long errcount;//密码错误次数
	private Integer logincnt;//登录次数
	private String tellerno; //交易柜员号
	//private String shorttellerno; //硬叫号柜员号
	
	//private String prcid;//登入工程id
	
	private String mdno;//排队机号
	private String orgno;//网点号
	private String pdno;//手持设备物理id
	private String channelID;//用户所在渠道
	
	/*public String getPrcid() {
		return prcid;
	}
	public void setPrcid(String prcid) {
		this.prcid = prcid;
	}*/
	
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public Long getSex() {
		return sex;
	}
	public void setSex(Long sex) {
		this.sex = sex;
	}
	public String getMailbox() {
		return mailbox;
	}
	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOperatorcardid() {
		return operatorcardid;
	}
	public void setOperatorcardid(String operatorcardid) {
		this.operatorcardid = operatorcardid;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getSexdesc() {
		return sexdesc;
	}
	public void setSexdesc(String sexdesc) {
		this.sexdesc = sexdesc;
	}
	public String getStatedesc() {
		return statedesc;
	}
	public void setStatedesc(String statedesc) {
		this.statedesc = statedesc;
	}
	public String getUsertypedesc() {
		return usertypedesc;
	}
	public void setUsertypedesc(String usertypedesc) {
		this.usertypedesc = usertypedesc;
	}
	
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public Set<Module> getCatalog_and_privileges() {
		return catalog_and_privileges;
	}
	public void setCatalog_and_privileges(Set<Module> catalogAndPrivileges) {
		catalog_and_privileges = catalogAndPrivileges;
	}
	public String getInfoex1() {
		return infoex1;
	}
	public void setInfoex1(String infoex1) {
		this.infoex1 = infoex1;
	}
	public String getInfoex2() {
		return infoex2;
	}
	public void setInfoex2(String infoex2) {
		this.infoex2 = infoex2;
	}
	public String getInfoex3() {
		return infoex3;
	}
	public void setInfoex3(String infoex3) {
		this.infoex3 = infoex3;
	}
	public String getInfoex4() {
		return infoex4;
	}
	public void setInfoex4(String infoex4) {
		this.infoex4 = infoex4;
	}
	public String getInfoex5() {
		return infoex5;
	}
	public void setInfoex5(String infoex5) {
		this.infoex5 = infoex5;
	}
	public String getHostip() {
		return hostip;
	}
	public void setHostip(String hostip) {
		this.hostip = hostip;
	}
	public Long getErrcount() {
		return errcount;
	}
	public void setErrcount(Long errcount) {
		this.errcount = errcount;
	}
	public Integer getLogincnt() {
		return logincnt;
	}
	public void setLogincnt(Integer logincnt) {
		this.logincnt = logincnt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getLogintime() {
		return logintime;
	}
	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}
	public String getTellerno() {
		return this.tellerno;
	}

	public void setTellerno(String tellerno) {
		this.tellerno = tellerno;
	}
	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}
/*	public String getShorttellerno() {
		return shorttellerno;
	}
	public void setShorttellerno(String shorttellerno) {
		this.shorttellerno = shorttellerno;
	}*/
	public String getMdno() {
		return mdno;
	}
	public void setMdno(String mdno) {
		this.mdno = mdno;
	}
	public String getOrgno() {
		return orgno;
	}
	public void setOrgno(String orgno) {
		this.orgno = orgno;
	}
	public String getPdno() {
		return pdno;
	}
	public void setPdno(String pdno) {
		this.pdno = pdno;
	}
	public String getChannelID() {
		return channelID;
	}
	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}
	
}