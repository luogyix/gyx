package com.agree.abt.model.dataAnalysis;

/**
 * 动态叫号规则号码POJO
 * @ClassName: DynamicRulesNum.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-7-7
 */
public class DynamicRulesNum {
	
	private String work_date;
	private String queue_num;
	private String branch;
	private String branch_name;
	private String bs_id;
	private String bs_name_ch;
	private String queuetype_id;
	private String queuetype_name;
	private String custtype_i;
	private String custtype_name;
	private String en_queue_time;
	private String wait_time;
	
	public DynamicRulesNum(String work_date, String queue_num, String branch,
			String branch_name, String bs_id, String bs_name_ch,
			String queuetype_id, String queuetype_name, String custtype_i,
			String custtype_name, String en_queue_time, String wait_time) {
		super();
		this.work_date = work_date;
		this.queue_num = queue_num;
		this.branch = branch;
		this.branch_name = branch_name;
		this.bs_id = bs_id;
		this.bs_name_ch = bs_name_ch;
		this.queuetype_id = queuetype_id;
		this.queuetype_name = queuetype_name;
		this.custtype_i = custtype_i;
		this.custtype_name = custtype_name;
		this.en_queue_time = en_queue_time;
		this.wait_time = wait_time;
	}
	
	public DynamicRulesNum() {
		super();
	}
	
	public String getWork_date() {
		return work_date;
	}
	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}
	public String getQueue_num() {
		return queue_num;
	}
	public void setQueue_num(String queue_num) {
		this.queue_num = queue_num;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getBs_id() {
		return bs_id;
	}
	public void setBs_id(String bs_id) {
		this.bs_id = bs_id;
	}
	public String getBs_name_ch() {
		return bs_name_ch;
	}
	public void setBs_name_ch(String bs_name_ch) {
		this.bs_name_ch = bs_name_ch;
	}
	public String getQueuetype_id() {
		return queuetype_id;
	}
	public void setQueuetype_id(String queuetype_id) {
		this.queuetype_id = queuetype_id;
	}
	public String getQueuetype_name() {
		return queuetype_name;
	}
	public void setQueuetype_name(String queuetype_name) {
		this.queuetype_name = queuetype_name;
	}
	public String getCusttype_i() {
		return custtype_i;
	}
	public void setCusttype_i(String custtype_i) {
		this.custtype_i = custtype_i;
	}
	public String getCusttype_name() {
		return custtype_name;
	}
	public void setCusttype_name(String custtype_name) {
		this.custtype_name = custtype_name;
	}
	public String getEn_queue_time() {
		return en_queue_time;
	}
	public void setEn_queue_time(String en_queue_time) {
		this.en_queue_time = en_queue_time;
	}

	public String getWait_time() {
		return wait_time;
	}

	public void setWait_time(String wait_time) {
		this.wait_time = wait_time;
	}
}
