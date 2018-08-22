package com.agree.abt.model.devmanager;




public class AdvertManageInfo {

	/**
	 * 机构号
	 */
	private String branch;
	/**
	 * 设备类型
	 */
	private String device_type;
	/**
	 * 广告id
	 */
	private String content_id;
	/**
	 * 广告类型（扩展项：1-―图片，2―视频，3―文字）
	 */
	private String contentType;
	/**
	 * 广告文件路径
	 */
	private String content;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 创建日期
	 */
	private String create_date;
	/**
	 * 广告描述
	 */
	private String explain;
	
	
	public AdvertManageInfo() {
		super();
	}


	public AdvertManageInfo(String branch, String device_type,
			String content_id, String contentType,String creator, String create_date,
			String explain) {
		super();
		this.creator = creator;
		this.branch = branch;
		this.device_type = device_type;
		this.content_id = content_id;
		this.contentType = contentType;
		this.create_date = create_date;
		this.explain = explain;
	}


	public AdvertManageInfo(String branch, String device_type, String content_id,
			String contentType, String content, String creator,String create_date, String explain) {
		super();
		this.branch = branch;
		this.creator = creator;
		this.device_type = device_type;
		this.content_id = content_id;
		this.contentType = contentType;
		this.content = content;
		this.create_date = create_date;
		this.explain = explain;
	}


	public String getBranch() {
		return branch;
	}


	public void setBranch(String branch) {
		this.branch = branch;
	}


	public String getDevice_type() {
		return device_type;
	}


	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}


	public String getContent_id() {
		return content_id;
	}


	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}


	public String getContentType() {
		return contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
	
	public String getCreator() {
		return creator;
	}


	public void setCreator(String creator) {
		this.creator = creator;
	}

	
	public String getCreate_date() {
		return create_date;
	}


	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}


	public String getExplain() {
		return explain;
	}


	public void setExplain(String explain) {
		this.explain = explain;
	}


	@Override
	public String toString() {
		return "AdvertManageInfo [branch=" + branch + ", device_type="
				+ device_type + ", content_id=" + content_id + ", contentType="
				+ contentType + ", content=" + content
				+ ", creator=" + creator + ", create_date=" + create_date
				+ ", explain=" + explain + "]";
	}



}
