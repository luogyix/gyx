package com.agree.abt.model.dataAnalysis;

import java.io.Serializable;
import java.util.List;

/** 
 * @ClassName: BtQmQueuetype 
 * @Description: 队列类型表
 * @company agree   
 * @author lilei
 * @date 2013-9-26 下午04:28:23 
 *  
 */ 
public class BtQmQueuetype implements Serializable{

	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	
	private static final long serialVersionUID = -1401996654020667120L;
	
	private String queuetype_id;//队列ID
	private String branch;//网点号
	private String branch_name;//网点名称
	private String queuetype_name;//队列名称
	private String queuetype_prefix;//队列前缀字母
	private String queuetype_prefix_num;//队列前缀数字
	private String queuetype_level;//队列权重(优先级)
	private String status;//队列状态
	
	private List<BtQmQueue> btqmqueue;//等待人数集合
	private String btqmsum;//等待人数
	private String averagewaittime;//平均等待时间(单位：秒)
	private String queuedetails;//队列详情(0-无详情，1-有详情)
	
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getBtqmsum() {
		return btqmsum;
	}
	public void setBtqmsum(String btqmsum) {
		this.btqmsum = btqmsum;
	}
	public String getQueuedetails() {
		return queuedetails;
	}
	public void setQueuedetails(String queuedetails) {
		this.queuedetails = queuedetails;
	}
	public String getAveragewaittime() {
		return averagewaittime;
	}
	public void setAveragewaittime(String averagewaittime) {
		this.averagewaittime = averagewaittime;
	}
	public BtQmQueuetype() {
		super();
	}
	public String getQueuetype_id() {
		return queuetype_id;
	}
	public void setQueuetype_id(String queuetype_id) {
		this.queuetype_id = queuetype_id;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getQueuetype_name() {
		return queuetype_name;
	}
	public void setQueuetype_name(String queuetype_name) {
		this.queuetype_name = queuetype_name;
	}
	public String getQueuetype_prefix() {
		return queuetype_prefix;
	}
	public void setQueuetype_prefix(String queuetype_prefix) {
		this.queuetype_prefix = queuetype_prefix;
	}
	public String getQueuetype_prefix_num() {
		return queuetype_prefix_num;
	}
	public void setQueuetype_prefix_num(String queuetype_prefix_num) {
		this.queuetype_prefix_num = queuetype_prefix_num;
	}
	public String getQueuetype_level() {
		return queuetype_level;
	}
	public void setQueuetype_level(String queuetype_level) {
		this.queuetype_level = queuetype_level;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<BtQmQueue> getBtqmqueue() {
		return btqmqueue;
	}
	public void setBtqmqueue(List<BtQmQueue> btqmqueue) {
		this.btqmqueue = btqmqueue;
	}
	
	public void setBtQmQueueType123(){
		this.queuetype_id = "123";//队列ID
		this.branch = "001001";//网点号
		this.queuetype_name = "普通客户";//队列名称
		this.queuetype_prefix = "123";//队列前缀字母
		this.queuetype_prefix_num = "123";//队列前缀数字
		this.queuetype_level = "123";//队列权重(优先级)
		this.status = "123";//队列状态
	}
	
	public void setBtQmQueueType456(){
		this.queuetype_id = "456";//队列ID
		this.branch = "001001";//网点号
		this.queuetype_name = "VIP客户";//队列名称
		this.queuetype_prefix = "123";//队列前缀字母
		this.queuetype_prefix_num = "123";//队列前缀数字
		this.queuetype_level = "123";//队列权重(优先级)
		this.status = "123";//队列状态
	}

}
