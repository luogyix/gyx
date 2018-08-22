package com.agree.framework.jasper;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 报表打印的接口 User: JiangMin Date: 2005-9-26 Time: 17:23:12 To change this template
 * use File | Settings | File Templates.
 */
public interface IReportPrint {

	void setDescription(String description);

	@SuppressWarnings("rawtypes")
	void setJRDataSource(List list);

	void setJRDataSource(Object object);

	@SuppressWarnings("rawtypes")
	void setSubJRDataSource(String parameterName, List list);

	void setSubJRDataSource(String parameterName, Object object);

	void setReport(String reportName) throws FileNotFoundException;

	void setSubReport(String parameterName, String reportName)
			throws FileNotFoundException, JRException;

	@SuppressWarnings("rawtypes")
	void setParameter(Map parameter);

	void setParameter(String key, Object value);

	void viewXlsReport() throws JRException, IOException;

	void viewPdfReport() throws JRException, IOException;

	void viewAppletReport() throws JRException, IOException;

	// Map getParameter();
	// String getDescription();
	// JRDataSource getJRDataSource();
	// JRDataSource getSubJRDataSource(String parameterName);
	// JasperPrint getJasperPrint() throws Exception;
	// JasperReport getJasperReport(String reportName) throws Exception;
	// JasperReport getJasperReport() throws Exception;

}
