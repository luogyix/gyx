package com.agree.abt.model.confManager;

/**
 * VIP客户实体类
 * @company 赞同科技
 * @author XiWang
 * @date 2014-8-19
 */
public class VipCustInfo {
	private String custinfo_name;
	private String vip_card;
	private String custtype;
	private String custinfo_tel;
	private String start_date;
	private String end_date;

	public VipCustInfo() {
		super();
	}

	public VipCustInfo(String custinfo_name, String vip_card, String custtype,
			String custinfo_tel, String start_date, String end_date) {
		super();
		this.custinfo_name = custinfo_name;
		this.vip_card = vip_card;
		this.custtype = custtype;
		this.custinfo_tel = custinfo_tel;
		this.start_date = start_date;
		this.end_date = end_date;
	}

	public String getCustinfo_name() {
		return custinfo_name;
	}

	public void setCustinfo_name(String custinfo_name) {
		this.custinfo_name = custinfo_name;
	}

	public String getVip_card() {
		return vip_card;
	}

	public void setVip_card(String vip_card) {
		this.vip_card = vip_card;
	}

	public String getCusttype() {
		return custtype;
	}

	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}

	public String getCustinfo_tel() {
		return custinfo_tel;
	}

	public void setCustinfo_tel(String custinfo_tel) {
		this.custinfo_tel = custinfo_tel;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
}
