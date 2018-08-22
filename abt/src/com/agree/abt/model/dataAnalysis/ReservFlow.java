package com.agree.abt.model.dataAnalysis;

/**
 * 预约流水-历史对象
 * @ClassName: ReservFlow.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-2-24
 */
public class ReservFlow {
	private String work_date;
	private String request_seq;
	private String request_channel;
	private String reserv_id;
	private String reserv_bs_id;
	private String reserv_record_date;
	private String reserv_record_time;
	private String reserv_begin_time;
	private String reserv_end_time;
	private String reserv_branch;
	private String reserv_status;
	private String reserv_modify_time;
	private String reserv_seq;
	private String queue_seq;
	private String service_seq;
	private String custinfo_type;
	private String custinfo_num;
	private String custinfo_name;
	private String account;
	private String sms_customer;
	private String phone_no;
	private String reserv_begin_date;
	private String reserv_end_date;
	private String reserv_zone;
	private String check_reserv_value;
	private String bs_name_ch;

	public ReservFlow() {
		super();
	}
	
	public ReservFlow(String work_date, String request_seq,
			String request_channel, String reserv_id, String reserv_bs_id,
			String reserv_record_date, String reserv_record_time,
			String reserv_begin_time, String reserv_end_time,
			String reserv_branch, String reserv_status,
			String reserv_modify_time, String reserv_seq, String queue_seq,
			String service_seq, String custinfo_type, String custinfo_num,
			String custinfo_name, String account, String sms_customer,
			String phone_no, String reserv_begin_date, String reserv_end_date,
			String reserv_zone, String check_reserv_value,String bs_name_ch) {
		super();
		this.work_date = work_date;
		this.request_seq = request_seq;
		this.request_channel = request_channel;
		this.reserv_id = reserv_id;
		this.reserv_bs_id = reserv_bs_id;
		this.reserv_record_date = reserv_record_date;
		this.reserv_record_time = reserv_record_time;
		this.reserv_begin_time = reserv_begin_time;
		this.reserv_end_time = reserv_end_time;
		this.reserv_branch = reserv_branch;
		this.reserv_status = reserv_status;
		this.reserv_modify_time = reserv_modify_time;
		this.reserv_seq = reserv_seq;
		this.queue_seq = queue_seq;
		this.service_seq = service_seq;
		this.custinfo_type = custinfo_type;
		this.custinfo_num = custinfo_num;
		this.custinfo_name = custinfo_name;
		this.account = account;
		this.sms_customer = sms_customer;
		this.phone_no = phone_no;
		this.reserv_begin_date = reserv_begin_date;
		this.reserv_end_date = reserv_end_date;
		this.reserv_zone = reserv_zone;
		this.check_reserv_value = check_reserv_value;
		this.bs_name_ch=bs_name_ch;
	}
	public String getWork_date() {
		return work_date;
	}
	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}
	public String getRequest_seq() {
		return request_seq;
	}
	public void setRequest_seq(String request_seq) {
		this.request_seq = request_seq;
	}
	public String getRequest_channel() {
		return request_channel;
	}
	public void setRequest_channel(String request_channel) {
		this.request_channel = request_channel;
	}
	public String getReserv_id() {
		return reserv_id;
	}
	public void setReserv_id(String reserv_id) {
		this.reserv_id = reserv_id;
	}
	public String getReserv_bs_id() {
		return reserv_bs_id;
	}
	public void setReserv_bs_id(String reserv_bs_id) {
		this.reserv_bs_id = reserv_bs_id;
	}
	public String getReserv_record_date() {
		return reserv_record_date;
	}
	public void setReserv_record_date(String reserv_record_date) {
		this.reserv_record_date = reserv_record_date;
	}
	public String getReserv_record_time() {
		return reserv_record_time;
	}
	public void setReserv_record_time(String reserv_record_time) {
		this.reserv_record_time = reserv_record_time;
	}
	public String getReserv_begin_time() {
		return reserv_begin_time;
	}
	public void setReserv_begin_time(String reserv_begin_time) {
		this.reserv_begin_time = reserv_begin_time;
	}
	public String getReserv_end_time() {
		return reserv_end_time;
	}
	public void setReserv_end_time(String reserv_end_time) {
		this.reserv_end_time = reserv_end_time;
	}
	public String getReserv_branch() {
		return reserv_branch;
	}
	public void setReserv_branch(String reserv_branch) {
		this.reserv_branch = reserv_branch;
	}
	public String getReserv_status() {
		return reserv_status;
	}
	public void setReserv_status(String reserv_status) {
		this.reserv_status = reserv_status;
	}
	public String getReserv_modify_time() {
		return reserv_modify_time;
	}
	public void setReserv_modify_time(String reserv_modify_time) {
		this.reserv_modify_time = reserv_modify_time;
	}
	public String getReserv_seq() {
		return reserv_seq;
	}
	public void setReserv_seq(String reserv_seq) {
		this.reserv_seq = reserv_seq;
	}
	public String getQueue_seq() {
		return queue_seq;
	}
	public void setQueue_seq(String queue_seq) {
		this.queue_seq = queue_seq;
	}
	public String getService_seq() {
		return service_seq;
	}
	public void setService_seq(String service_seq) {
		this.service_seq = service_seq;
	}
	public String getCustinfo_type() {
		return custinfo_type;
	}
	public void setCustinfo_type(String custinfo_type) {
		this.custinfo_type = custinfo_type;
	}
	public String getCustinfo_num() {
		return custinfo_num;
	}
	public void setCustinfo_num(String custinfo_num) {
		this.custinfo_num = custinfo_num;
	}
	public String getCustinfo_name() {
		return custinfo_name;
	}
	public void setCustinfo_name(String custinfo_name) {
		this.custinfo_name = custinfo_name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSms_customer() {
		return sms_customer;
	}
	public void setSms_customer(String sms_customer) {
		this.sms_customer = sms_customer;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getReserv_begin_date() {
		return reserv_begin_date;
	}
	public void setReserv_begin_date(String reserv_begin_date) {
		this.reserv_begin_date = reserv_begin_date;
	}
	public String getReserv_end_date() {
		return reserv_end_date;
	}
	public void setReserv_end_date(String reserv_end_date) {
		this.reserv_end_date = reserv_end_date;
	}
	public String getReserv_zone() {
		return reserv_zone;
	}
	public void setReserv_zone(String reserv_zone) {
		this.reserv_zone = reserv_zone;
	}
	public String getCheck_reserv_value() {
		return check_reserv_value;
	}
	public void setCheck_reserv_value(String check_reserv_value) {
		this.check_reserv_value = check_reserv_value;
	}

	public String getBs_name_ch() {
		return bs_name_ch;
	}

	public void setBs_name_ch(String bsNameCh) {
		bs_name_ch = bsNameCh;
	}
	
}
