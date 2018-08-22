package com.agree.abt.model.dataAnalysis;

import java.io.Serializable;

/**
 * 零售客户等候时间统计
 * @author XiWang
 * @date 2014-1-16
 */
public class AnalyseWaitingTime implements Serializable {
	
	private static final long serialVersionUID = 5026488096652971063L;
	
	private String branch;
	private String branch_name;
	private String work_date;
	private String queue_bus_type;
	private String bus_type_name;
	private String custtype_i;
	private String custtype_name;
	private String ticket_g;
	private String ticket_v;
	private String ticket_l;
	private String ticket_l_rate;
	private String ticket_s;
	private String standard_rate;
	
	public AnalyseWaitingTime() {
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

	public String getWork_date() {
		return work_date;
	}

	public void setWork_date(String work_date) {
		this.work_date = work_date;
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

	public String getTicket_g() {
		return ticket_g;
	}

	public void setTicket_g(String ticket_g) {
		this.ticket_g = ticket_g;
	}

	public String getTicket_v() {
		return ticket_v;
	}

	public void setTicket_v(String ticket_v) {
		this.ticket_v = ticket_v;
	}

	public String getTicket_l() {
		return ticket_l;
	}

	public void setTicket_l(String ticket_l) {
		this.ticket_l = ticket_l;
	}

	public String getTicket_l_rate() {
		return ticket_l_rate;
	}

	public void setTicket_l_rate(String ticket_l_rate) {
		this.ticket_l_rate = ticket_l_rate;
	}

	public String getTicket_s() {
		return ticket_s;
	}

	public void setTicket_s(String ticket_s) {
		this.ticket_s = ticket_s;
	}

	public String getStandard_rate() {
		return standard_rate;
	}

	public void setStandard_rate(String standard_rate) {
		this.standard_rate = standard_rate;
	}
}
