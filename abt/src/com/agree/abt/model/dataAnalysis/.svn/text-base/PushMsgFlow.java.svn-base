package com.agree.abt.model.dataAnalysis;

import java.io.Serializable;

import com.agree.framework.web.form.administration.Unit;

/**
 * 
 * 消息流水实体
 * @ClassName: PushMsgFlow 
 * @company 赞同科技   
 * @author  zhaojianyong
 * @date 2015-6-12 下午03:42:02 
 *
 */
@SuppressWarnings("all")
public class PushMsgFlow implements Serializable {

	private String msgId ;       //消息流水id
	private String branch ;      //网点号
	private String pdNo;         //设备号
	private String msgDate ;     //日期
	private String msgTime ;     //时间
	private String msgType ;     //消息类型
	private String deviceType ;  //设备类型
	private String msgContent ;  //消息内容
	private String isNew ;       //是否是新消息 0-否 1-是
	private Unit unit ;    //多对一
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getPdNo() {
		return pdNo;
	}
	public void setPdNo(String pdNo) {
		this.pdNo = pdNo;
	}
	public String getMsgDate() {
		return msgDate;
	}
	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}
	public String getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public PushMsgFlow(String msgId, String branch, String pdNo,
			String msgDate, String msgTime, String msgType, String deviceType,
			String msgContent, String isNew, Unit unit) {
		super();
		this.msgId = msgId;
		this.branch = branch;
		this.pdNo = pdNo;
		this.msgDate = msgDate;
		this.msgTime = msgTime;
		this.msgType = msgType;
		this.deviceType = deviceType;
		this.msgContent = msgContent;
		this.isNew = isNew;
		this.unit = unit;
	}
	public PushMsgFlow() {
		super();
	}
}
