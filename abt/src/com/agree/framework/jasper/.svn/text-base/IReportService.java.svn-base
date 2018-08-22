/**
 * 
 */
package com.agree.framework.jasper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.agree.framework.web.service.base.IBaseService;

/**
 * @author David
 * 
 */
public interface IReportService extends IBaseService {
	public void setRequestAndResponse(HttpServletRequest request,
			HttpServletResponse response);

	public void createJRDataSource(List<?> list);

	public void setSubJRDataSource(String parameterName, List<?> list);

	public void setSubJRDataSource(String parameterName, Object object);

	public void setReport(String reportName) throws Exception;

	public void setSubReport(String parameterName, String reportName)
			throws Exception;

	@SuppressWarnings("rawtypes")
	public void setParameter(Map parameter);

	@SuppressWarnings("rawtypes")
	public List getArrayBeanReportDataSource(String sqlMap, Object u);

	public void viewXlsReport_itransc() throws Exception;

	public void viewHtmlReport_itransc() throws Exception;

	public void setReportName(String reportName);

	@SuppressWarnings("rawtypes")
	public void setMaplist(List<Map> maplist);

	public void viewPdfReport_itransc() throws Exception;
	// void viewPdfReport() throws Exception;
}
