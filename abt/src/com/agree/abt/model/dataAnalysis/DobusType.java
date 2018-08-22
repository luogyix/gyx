package com.agree.abt.model.dataAnalysis;

import java.io.Serializable;

/** 
 * @ClassName: DobusType 
 * @Description: TODO 
 * @author lilei
 * @date 2013-10-11 下午01:39:23  
 */ 
public class DobusType implements Serializable {
	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 5026488096652971063L;
	
	private String branch;
	private String branch_name;
	private String date;
	private String queue_bus_type;
	private String bus_type_name;
	private String dobus_count;
	private String avgwaittime;
	private String total_wait_time;
	private String avgservtime;
	private String total_service_time;
	
	public DobusType() {
		super();
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getQueue_bus_type() {
		return queue_bus_type;
	}
	public void setQueue_bus_type(String queue_bus_type) {
		this.queue_bus_type = queue_bus_type;
	}
	public String getBus_type_name() {
		return bus_type_name;
	}
	public void setBus_type_name(String bus_type_name) {
		this.bus_type_name = bus_type_name;
	}
	public String getDobus_count() {
		return dobus_count;
	}
	public void setDobus_count(String dobus_count) {
		this.dobus_count = dobus_count;
	}

	public String getAvgservtime() {
		return avgservtime;
	}

	public void setAvgservtime(String avgservtime) {
		this.avgservtime = avgservtime;
	}

	public String getTotal_service_time() {
		return total_service_time;
	}

	public void setTotal_service_time(String total_service_time) {
		this.total_service_time = total_service_time;
	}

	public String getAvgwaittime() {
		return avgwaittime;
	}

	public void setAvgwaittime(String avgwaittime) {
		this.avgwaittime = avgwaittime;
	}

	public String getTotal_wait_time() {
		return total_wait_time;
	}

	public void setTotal_wait_time(String total_wait_time) {
		this.total_wait_time = total_wait_time;
	}
}
