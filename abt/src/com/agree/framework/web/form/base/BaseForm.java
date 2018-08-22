/**
 * 
 */
package com.agree.framework.web.form.base;

import com.agree.framework.web.form.administration.User;

/**
 * @author David
 * @name BaseForm
 * @describe：所有页面Form的基类,其属性不参与Hibernate的对象关系映射
 */
public class BaseForm {
	public BaseForm(){
	}
	
	public BaseForm(BaseForm form){
		this.start = form.getStart();
		this.limit = form.getLimit();
		this.page = form.getPage();
		this.total = form.getTotal();
		this.rowStart = form.getRowStart();
		this.rowEnd = form.getRowEnd();
		this.sortString = form.getSortString();
	}
	
	/**
	 *	在很多sqlmap组装的sql语句中需要使用的登录用户信息
	 */
	private User    logonUser = null;
	
	/**
	 *	分页时用到的变量
	 *	start       : 当前页页码，与前台SelfPagingToolbar组件的start属性对应。当查询最后一页的数据时，前台组件相对应的该值设为-1
	 *	limit       : 每页显示的记录数，与前台SelfPagingToolbar组件的limit属性对应
	 *	total       : 总页数，只有当用户查询最后一页数据时由系统自己填入。查询其它页时，total的值应为0
	 *	rowStart    : 起始行号
	 *	rowEnd      : 结束行号 
	 */
	private Integer start = 1;
	private Integer limit = 20;
	private Integer total = 0;
	private Integer page=0;
	private Integer rowStart;
	private Integer rowEnd;
	private String datelimit1;
	private String datelimit2;
	private String datelimit3;
	private String datelimit4;
	private String locale;
	
	private String sortString;
	private String pageRecordSql;
	private String filename;			//表头
	private String updaterowsStr;
	
	
	public String getUpdaterowsStr() {
		return updaterowsStr;
	}

	public void setUpdaterowsStr(String updaterowsStr) {
		this.updaterowsStr = updaterowsStr;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDatelimit3() {
		return datelimit3;
	}

	public void setDatelimit3(String datelimit3) {
		this.datelimit3 = datelimit3;
	}

	public String getDatelimit4() {
		return datelimit4;
	}

	public void setDatelimit4(String datelimit4) {
		this.datelimit4 = datelimit4;
	}

	public String getPageRecordSql() {
		return pageRecordSql;
	}

	public void setPageRecordSql(String pageRecordSql) {
		this.pageRecordSql = pageRecordSql;
	}

	public String getDatelimit1() {
		return datelimit1;
	}

	public void setDatelimit1(String datelimit1) {
		this.datelimit1 = datelimit1;
	}

	public String getDatelimit2() {
		return datelimit2;
	}

	public void setDatelimit2(String datelimit2) {
		this.datelimit2 = datelimit2;
	}

	public User getLogonUser() {
		return logonUser;
	}
	public void setLogonUser(User logonUser) {
		this.logonUser = logonUser;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getRowStart() {
		return rowStart;
	}
	public void setRowStart(Integer rowStart) {
		this.rowStart = rowStart;
	}
	public Integer getRowEnd() {
		return rowEnd;
	}
	public void setRowEnd(Integer rowEnd) {
		this.rowEnd = rowEnd;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getSortString() {
		return sortString;
	}
	public void setSortString(String sortString) {
		this.sortString = sortString;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
}
