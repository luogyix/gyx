/**   
 * @Title: TBsmsSysNotice.java 
 * @Package com.agree.bsms.service.common 
 * @Description: TODO 
 * @company agree   
 * @author authorname   
 * @date 2012-10-30 上午11:17:54 
 * @version V1.0   
 */ 

package com.agree.framework.web.form;

/** 
 * @ClassName: TBsmsSysNotice 
 * @Description: TODO
 * @company agree   
 * @author haoruibing   
 * @date 2012-10-30 上午11:17:54 
 *  
 */

public class TBsmsSysNotice implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	private String modifyuser;
	private String modifytime;
	private Long id;
	private String unitid;
	private String infoex1;
	private String infoex2;
	private String infoex3;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getModifyuser() {
		return modifyuser;
	}
	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}
	public String getModifytime() {
		return modifytime;
	}
	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
