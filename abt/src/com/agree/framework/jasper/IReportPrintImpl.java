package com.agree.framework.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表打印的实现 User: JiangMian Date: 2005-9-26 Time: 17:27:27 To change this
 * template use File | Settings | File Templates.
 */
public class IReportPrintImpl implements IReportPrint {
	public static String reportPath;// = "/WEB-INF/report";
	public static boolean complie;// = false;
	private ServletContext application;
	private HttpServletResponse response;
	private String reportName;
	private Map<String,Object> parameter = new HashMap<String,Object>();
	private JRDataSource jRDataSource = new JRIBatisDataSource();
	private String description;

	/**
	 * 设置下载文件的文件名
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 设置主报表的数据源
	 * 
	 * @param list
	 */
	@SuppressWarnings("rawtypes")
	public void setJRDataSource(List list) {
		this.jRDataSource = new JRIBatisDataSource(list);
	}

	/**
	 * 设置主报表的数据源
	 * 
	 * @param object
	 */
	public void setJRDataSource(Object object) {
		this.jRDataSource = new JRIBatisDataSource(object);
	}

	/**
	 * 设置子报表的数据源
	 * 
	 * @param parameterName
	 * @param list
	 */
	@SuppressWarnings({ "rawtypes" })
	public void setSubJRDataSource(String parameterName, List list) {
		this.parameter.put(parameterName, new JRIBatisDataSource(list));
	}

	/**
	 * 设置子报表的数据源
	 * 
	 * @param parameterName
	 * @param object
	 */
	public void setSubJRDataSource(String parameterName, Object object) {
		this.parameter.put(parameterName, new JRIBatisDataSource(object));
	}

	/**
	 * 设置主报表名
	 * 
	 * @param reportName
	 * @throws FileNotFoundException
	 */
	public void setReport(String reportName) throws FileNotFoundException {
		if (complie) {
			if (!existFile(reportName, "jrxml"))
				throw new FileNotFoundException("File " + reportName
						+ ".jrxml not found.");
		} else {
			if (!existFile(reportName, "jasper"))
				throw new FileNotFoundException(
						"File "
								+ reportName
								+ ".jasper not found. The report design must be compiled first.");
		}
		this.reportName = reportName;
	}

	/**
	 * 设置子报表
	 * 
	 * @param param
	 * @param reportName
	 * @throws JRException
	 */
	public void setSubReport(String param, String reportName)
			throws JRException, FileNotFoundException {
		if (complie) {
			if (!existFile(reportName, "jrxml"))
				throw new FileNotFoundException("File " + reportName
						+ ".jrxml not found.");
		} else {
			if (!existFile(reportName, "jasper"))
				throw new FileNotFoundException(
						"File "
								+ reportName
								+ ".jasper not found. The report design must be compiled first.");
		}
		this.parameter.put(param, getJasperReport(reportName));
	}

	/**
	 * 设置报表需要的参数
	 * 
	 * @param parameter
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setParameter(Map parameter) {
		this.parameter.putAll(parameter);
	}

	/**
	 * 设置报表需要的参数
	 * 
	 * @param key
	 * @param value
	 */
	public void setParameter(String key, Object value) {
		this.parameter.put(key, value);
	}

	/**
	 * 构造函数
	 * 
	 * @param application
	 * @param response
	 */
	public IReportPrintImpl(ServletContext application,
			HttpServletResponse response) {
		this.application = application;
		this.response = response;
		// if (complie) {
		// JRProperties.setProperty(JRProperties.COMPILER_CLASSPATH,
		// getRealPath("/WEB-INF/lib/jasperreports-1.0.2.jar") +
		// System.getProperty("path.separator") +
		// getRealPath("/WEB-INF/lib/iReport.jar") +
		// System.getProperty("path.separator") +
		// getRealPath("/WEB-INF/classes/")
		// );
		// JRProperties.setProperty(
		// JRProperties.COMPILER_TEMP_DIR,
		// getRealPath(reportPath)
		// );
		// }
	}

	/**
	 * 以Xls方式显示报表
	 * 
	 * @throws JRException
	 * @throws IOException
	 */
	public void viewXlsReport() throws JRException, IOException {
		String downloadName = (this.description != null) ? this.description
				: this.reportName;
		downloadName = URLEncoder.encode(downloadName, "utf-8");
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				getJasperReport(), this.parameter, this.jRDataSource);
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=GBK");
		// response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ downloadName + ".xls\"");
		JRExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
				response.getOutputStream());
		// exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
		// Boolean.TRUE);
		// exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
		// Boolean.FALSE);
		// exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
		// Boolean.FALSE);
		exporter.exportReport();
	}

	/**
	 * 以Pdf方式显示报表
	 * 
	 * @throws JRException
	 * @throws IOException
	 */
	public void viewPdfReport() throws JRException, IOException {
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				getJasperReport(), this.parameter, this.jRDataSource);
		String downloadName = (this.description != null) ? this.description
				: this.reportName;
		downloadName = URLEncoder.encode(downloadName, "utf-8");
		response.reset();
		response.setContentType("application/pdf;charset=GBK");
		// response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ downloadName + ".pdf\"");
		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
				response.getOutputStream());
		exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
		exporter.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY,
				Boolean.TRUE);
		// exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD,
		// "jasper");
		// exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD,
		// "reports");
		// exporter.setParameter(JRPdfExporterParameter.PERMISSIONS, new
		// Integer(PdfWriter.AllowCopy | PdfWriter.AllowPrinting));
		exporter.exportReport();
	}

	/**
	 * 以Applet方式显示报表
	 * 
	 * @throws JRException
	 * @throws IOException
	 */
	public void viewAppletReport() throws JRException, IOException {
		ObjectOutputStream objectOutputStream = null;
		OutputStream ouputStream = null;
		try {
			JasperPrint jasperPrint = getJasperPrint();
			response.reset();
			response.setContentType("application/octet-stream");
			ouputStream = response.getOutputStream();
			objectOutputStream = new ObjectOutputStream(ouputStream);
			objectOutputStream.writeObject(jasperPrint);
			objectOutputStream.flush();
		} finally {
			if (objectOutputStream != null)
				objectOutputStream.close();
			if (ouputStream != null)
				ouputStream.close();
		}
	}

	/**
	 * 获得JasperPrint对象
	 * 
	 * @return JasperPrint
	 * @throws JRException
	 */
	private JasperPrint getJasperPrint() throws JRException {
		return JasperFillManager.fillReport(getJasperReport(), this.parameter,
				this.jRDataSource);
	}

	/**
	 * 获得 JasperReport对象
	 * 
	 * @param reportName
	 * @return JasperReport
	 * @throws JRException
	 */
	private JasperReport getJasperReport(String reportName) throws JRException {
		String fileRealPath;
		if (complie) {
			fileRealPath = getRealPath(reportPath + "/" + reportName + ".jrxml");
			JasperCompileManager.compileReportToFile(fileRealPath);
		}
		fileRealPath = getRealPath(reportPath + "/" + reportName + ".jasper");
		File file = new File(fileRealPath);
		return (JasperReport) JRLoader.loadObject(file.getPath());
	}

	/**
	 * 获得 JasperReport对象
	 * 
	 * @return JasperReport
	 * @throws JRException
	 */
	private JasperReport getJasperReport() throws JRException {
		return getJasperReport(this.reportName);
	}

	/**
	 * 获得文件的实际路径
	 * 
	 * @param fileName
	 * @return String
	 */
	private String getRealPath(String fileName) {
		return this.application.getRealPath(fileName);
	}

	/**
	 * 判断指定的文件是否存在
	 * 
	 * @param reportName
	 *            报表名
	 * @param filePostfix
	 *            报表后缀名
	 * @return boolean
	 */
	private boolean existFile(String reportName, String filePostfix) {
		String fileRealPath = getRealPath(reportPath + "/" + reportName + "."
				+ filePostfix);
		File file = new File(fileRealPath);
		return file.exists();
	}
}
