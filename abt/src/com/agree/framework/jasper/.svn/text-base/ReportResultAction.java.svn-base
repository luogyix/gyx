package com.agree.framework.jasper;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts2.ServletActionContext;


import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONObject;
/**
 * 
 * @ClassName: ReportResultAction 
 * @Description: 报表再跳转Action，用于分离报表Action和报表Service的中间动作类
 * @company agree   
 * @author haoruibing   
 * @date 2011-7-29 下午02:59:13 
 *
 */
public class ReportResultAction extends BaseReportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IReportService reportService;
	private String uriEncoding;

	public void setUriEncoding(String uriEncoding) {
		this.uriEncoding = uriEncoding;
	}


	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}
	
	/**
	 * 
	 */
	public String queryResult() throws Exception{
		//JSONObject query_jsonObj = JSONObject.fromObject(super.getQuerycondition_str());
		String str = super.getReport_parameter_str();
		
		if(!(str == null || str.equals(""))){
			str=new String(str.getBytes(uriEncoding),"UTF-8");
			Map<String,Object> parameterMap = new HashMap<String,Object>();
			JSONObject obj = JSONObject.fromObject(str);
			//json转换成集合对象
			MorphDynaBean bean = (MorphDynaBean)JSONObject.toBean(obj);
			DynaProperty[] properties = bean.getDynaClass().getDynaProperties();
			for(DynaProperty property : properties){
				parameterMap.put(property.getName(), obj.get(property.getName()));
			}
			this.reportService.setParameter(parameterMap);
		}
		/*Class<?> query_class = Class.forName(super.getQuery_form_name());
	//	Object query_obj = JSONObject.toBean(query_jsonObj, query_class);
		Locale locale = ServletActionContext.getRequest().getLocale();
		Class<?> temp = query_class;
		while(temp != null){
			Method[] methods = temp.getDeclaredMethods();
			int flag = 0;
			for(Method method : methods){
				if(method.getName().toLowerCase().equals("set" + LOCALE_FIELD)){
					method.invoke(query_obj, locale.toString());
					flag ++;
					break;
				}
			}
			if(flag > 0){
				break;
			}
			temp = temp.getSuperclass();
		}
		List<?> results = this.reportService.getArrayBeanReportDataSource(super.getQuery_sql(), query_obj);*/
		this.reportService.setRequestAndResponse(ServletActionContext.getRequest(), ServletActionContext.getResponse());
		this.reportService.setReport(super.getReport_template_name());
		//this.reportService.createJRDataSource(results);
		this.reportService.viewHtmlReport_itransc();
		return null;
	}
	
	public String exportXlsResult() throws Exception{
	//	JSONObject query_jsonObj = JSONObject.fromObject(super.getQuerycondition_str());
		String str = super.getReport_parameter_str();
		if(!(str == null || str.equals(""))){
			str=new String(str.getBytes(uriEncoding),"UTF-8");
			Map<String,Object> parameterMap = new HashMap<String,Object>();
			JSONObject obj = JSONObject.fromObject(str);
			MorphDynaBean bean = (MorphDynaBean)JSONObject.toBean(obj);
			DynaProperty[] properties = bean.getDynaClass().getDynaProperties();
			for(DynaProperty property : properties){
				parameterMap.put(property.getName(), obj.get(property.getName()));
			}
			this.reportService.setParameter(parameterMap);
		}
		/*Class<?> query_class = Class.forName(super.getQuery_form_name());
		Object query_obj = JSONObject.toBean(query_jsonObj, query_class);
		Locale locale = ServletActionContext.getRequest().getLocale();
		Class<?> temp = query_class;
		while(temp != null){
			Method[] methods = temp.getDeclaredMethods();
			int flag = 0;
			for(Method method : methods){
				if(method.getName().toLowerCase().equals("set" + LOCALE_FIELD)){
					method.invoke(query_obj, locale.toString());
					flag ++;
					break;
				}
			}
			if(flag > 0){
				break;
			}
			temp = temp.getSuperclass();
		}
		List<?> results = this.reportService.getArrayBeanReportDataSource(super.getQuery_sql(), query_obj);*/
		this.reportService.setRequestAndResponse(ServletActionContext.getRequest(), ServletActionContext.getResponse());
		this.reportService.setReport(super.getReport_template_name());
	//	this.reportService.createJRDataSource();
		this.reportService.viewXlsReport_itransc();
		return null;
	}
	
	
	public String exportPdfResult() throws Exception{
	//	JSONObject query_jsonObj = JSONObject.fromObject(super.getQuerycondition_str());
		String str = super.getReport_parameter_str();
		if(!(str == null || str.equals(""))){
			str=new String(str.getBytes(uriEncoding),"UTF-8");
			Map<String,Object> parameterMap = new HashMap<String,Object>();
			JSONObject obj = JSONObject.fromObject(str);
			MorphDynaBean bean = (MorphDynaBean)JSONObject.toBean(obj);
			DynaProperty[] properties = bean.getDynaClass().getDynaProperties();
			for(DynaProperty property : properties){
				parameterMap.put(property.getName(), obj.get(property.getName()));
			}
			this.reportService.setParameter(parameterMap);
		}
		/*Class<?> query_class = Class.forName(super.getQuery_form_name());
		Object query_obj = JSONObject.toBean(query_jsonObj, query_class);
		Locale locale = ServletActionContext.getRequest().getLocale();
		Class<?> temp = query_class;
		while(temp != null){
			Method[] methods = temp.getDeclaredMethods();
			int flag = 0;
			for(Method method : methods){
				if(method.getName().toLowerCase().equals("set" + LOCALE_FIELD)){
					method.invoke(query_obj, locale.toString());
					flag ++;
					break;
				}
			}
			if(flag > 0){
				break;
			}
			temp = temp.getSuperclass();
		}
		List<?> results = this.reportService.getArrayBeanReportDataSource(super.getQuery_sql(), query_obj);*/
		this.reportService.setRequestAndResponse(ServletActionContext.getRequest(), ServletActionContext.getResponse());
		this.reportService.setReport(super.getReport_template_name());
	//	this.reportService.createJRDataSource();
		this.reportService.viewPdfReport_itransc();
		return null;
	}
}
