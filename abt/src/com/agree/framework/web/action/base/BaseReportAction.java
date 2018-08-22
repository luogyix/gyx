package com.agree.framework.web.action.base;

import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.agree.framework.web.action.base.BaseAction;

public class BaseReportAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String REPORT_RESULT = "reportresult";
	public static final String EXPORT_RESULT = "exportresult";
	
	private String querycondition_str;
	private String query_form_name;
	private String query_sql;
	private String report_template_name;
	private String report_parameter_str;
	
	public final void setReportParameterMap(Map<String, Object> map) throws Exception{
		JsonConfig config = new JsonConfig();
		config.setClassMap(map);
		JSONObject json_param = JSONObject.fromObject(map, config);
		this.report_parameter_str = json_param.toString();
	}
	
	public String getQuerycondition_str() {
		return querycondition_str;
	}
	public void setQuerycondition_str(String querycondition_str) {
		this.querycondition_str = querycondition_str;
	}
	public String getQuery_form_name() {
		return query_form_name;
	}
	public void setQuery_form_name(String query_form_name) {
		this.query_form_name = query_form_name;
	}
	public String getQuery_sql() {
		return query_sql;
	}
	public void setQuery_sql(String query_sql) {
		this.query_sql = query_sql;
	}
	public String getReport_template_name() {
		return report_template_name;
	}
	public void setReport_template_name(String report_template_name) {
		this.report_template_name = report_template_name;
	}
	public String getReport_parameter_str() {
		return report_parameter_str;
	}
	public void setReport_parameter_str(String reportParameterStr) {
		report_parameter_str = reportParameterStr;
	}
	public String queryResult() throws Exception{
		return REPORT_RESULT;
	}
	
	public String exportXlsResult() throws Exception{
		return EXPORT_RESULT;
	}
}
