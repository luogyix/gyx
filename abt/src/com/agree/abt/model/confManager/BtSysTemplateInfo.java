package com.agree.abt.model.confManager;

/**
 * 参数模板表实体POJO类.
 * @ClassName: BtSysTemplateInfo.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-3-25
 */
public class BtSysTemplateInfo {
	
	private String template_id;
	private String template_name;
	private String branch;
	private String template_note;
	private String template_flag;

	public BtSysTemplateInfo() {
		super();
	}

	public BtSysTemplateInfo(String template_id, String template_name,
			String branch, String template_note, String template_flag) {
		super();
		this.template_id = template_id;
		this.template_name = template_name;
		this.branch = branch;
		this.template_note = template_note;
		this.template_flag = template_flag;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getTemplate_note() {
		return template_note;
	}

	public void setTemplate_note(String template_note) {
		this.template_note = template_note;
	}

	public String getTemplate_flag() {
		return template_flag;
	}

	public void setTemplate_flag(String template_flag) {
		this.template_flag = template_flag;
	}
}
