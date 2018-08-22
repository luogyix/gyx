package com.agree.abt.model.dataAnalysis;

import java.io.Serializable;

public class ClientAssess implements Serializable{
	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 6641155552067459114L;
	
	private String branch;
	private String branch_name;
	private String date;
	private String teller;
	private String teller_name;
	private String total_call_num;
	private String total_service_num;
	private String total_assess_num;
	private String very_satisfy;
	private String satisfy;
	private String genl_satisfy;
	private String not_satisfy;
	private String not_evaluate;
	private String not_sed_evaluate;
	private String assess_rate;
	private String very_satisfy_rate;
	private String satisfy_rate;
	private String genl_satisfy_rate;
	private String not_satisfy_rate;
	private String not_evaluate_rate;
	private String not_sed_evaluate_rate;
	private String serviceratings;
	private String serviceratings_avg;
	
	public ClientAssess() {
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
	public String getTotal_call_num() {
		return total_call_num;
	}
	public void setTotal_call_num(String total_call_num) {
		this.total_call_num = total_call_num;
	}
	public String getTotal_service_num() {
		return total_service_num;
	}
	public void setTotal_service_num(String total_service_num) {
		this.total_service_num = total_service_num;
	}
	public String getTotal_assess_num() {
		return total_assess_num;
	}
	public void setTotal_assess_num(String total_assess_num) {
		this.total_assess_num = total_assess_num;
	}
	public String getVery_satisfy() {
		return very_satisfy;
	}
	public void setVery_satisfy(String very_satisfy) {
		this.very_satisfy = very_satisfy;
	}
	public String getSatisfy() {
		return satisfy;
	}
	public void setSatisfy(String satisfy) {
		this.satisfy = satisfy;
	}
	public String getNot_satisfy() {
		return not_satisfy;
	}
	public void setNot_satisfy(String not_satisfy) {
		this.not_satisfy = not_satisfy;
	}
	public String getAssess_rate() {
		return assess_rate;
	}
	public void setAssess_rate(String assess_rate) {
		this.assess_rate = assess_rate;
	}
	public String getVery_satisfy_rate() {
		return very_satisfy_rate;
	}
	public void setVery_satisfy_rate(String very_satisfy_rate) {
		this.very_satisfy_rate = very_satisfy_rate;
	}
	public String getSatisfy_rate() {
		return satisfy_rate;
	}
	public void setSatisfy_rate(String satisfy_rate) {
		this.satisfy_rate = satisfy_rate;
	}
	public String getNot_satisfy_rate() {
		return not_satisfy_rate;
	}
	public void setNot_satisfy_rate(String not_satisfy_rate) {
		this.not_satisfy_rate = not_satisfy_rate;
	}

	public String getTeller() {
		return teller;
	}

	public void setTeller(String teller) {
		this.teller = teller;
	}

	public String getTeller_name() {
		return teller_name;
	}

	public void setTeller_name(String teller_name) {
		this.teller_name = teller_name;
	}

	public String getServiceratings() {
		return serviceratings;
	}

	public void setServiceratings(String serviceratings) {
		this.serviceratings = serviceratings;
	}

	public String getGenl_satisfy() {
		return genl_satisfy;
	}

	public void setGenl_satisfy(String genl_satisfy) {
		this.genl_satisfy = genl_satisfy;
	}

	public String getNot_evaluate() {
		return not_evaluate;
	}

	public void setNot_evaluate(String not_evaluate) {
		this.not_evaluate = not_evaluate;
	}

	public String getNot_sed_evaluate() {
		return not_sed_evaluate;
	}

	public void setNot_sed_evaluate(String not_sed_evaluate) {
		this.not_sed_evaluate = not_sed_evaluate;
	}

	public String getGenl_satisfy_rate() {
		return genl_satisfy_rate;
	}

	public void setGenl_satisfy_rate(String genl_satisfy_rate) {
		this.genl_satisfy_rate = genl_satisfy_rate;
	}

	public String getNot_evaluate_rate() {
		return not_evaluate_rate;
	}

	public void setNot_evaluate_rate(String not_evaluate_rate) {
		this.not_evaluate_rate = not_evaluate_rate;
	}

	public String getNot_sed_evaluate_rate() {
		return not_sed_evaluate_rate;
	}

	public void setNot_sed_evaluate_rate(String not_sed_evaluate_rate) {
		this.not_sed_evaluate_rate = not_sed_evaluate_rate;
	}

	public String getServiceratings_avg() {
		return serviceratings_avg;
	}

	public void setServiceratings_avg(String serviceratings_avg) {
		this.serviceratings_avg = serviceratings_avg;
	}
}
