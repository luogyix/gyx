/**
 * 
 */
package com.agree.framework.jasper;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import com.agree.framework.web.service.base.BaseService;

/**
 * @author David
 * 
 */
public class ReportService extends BaseService implements IReportService {
	private String reportPath = "jasper";
	private boolean complie;// = false;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String reportName;
	@SuppressWarnings("rawtypes")
	private List<Map> Maplist;
	private Map<String, Object> parameter = new HashMap<String, Object>();
	private Connection jRDataSource;

	@SuppressWarnings("rawtypes")
	public void setMaplist(List<Map> maplist) {
		Maplist = maplist;
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public boolean isComplie() {
		return complie;
	}

	public void setComplie(boolean complie) {
		this.complie = complie;
	}

	public ReportService() {
		super();
	}

	public ReportService(boolean b_compile, String reportPath) {
		this.complie = b_compile;
		this.reportPath = reportPath;
	}

	public void setRequestAndResponse(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public void createJRDataSource(List<?> list) {
		// this.jRDataSource = sqlDao_h.getConn();
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setParameter(Map parameter) {
		this.parameter = parameter;
	}

	public void setReport(String reportName) throws Exception {

		this.reportName = reportName;

		if (this.complie) {
			if (!existFile(this.reportName, "jrxml"))
				throw new FileNotFoundException("File " + this.reportName
						+ ".jrxml not found.");
		} else {
			if (!existFile(this.reportName, "jasper"))
				throw new FileNotFoundException(
						"File "
								+ this.reportName
								+ ".jasper not found. The report design must be compiled first.");
		}

	}

	public void setSubJRDataSource(String parameterName, List<?> list) {

	}

	public void setSubJRDataSource(String parameterName, Object object) {

	}

	public void setSubReport(String parameterName, String reportName)
			throws Exception {

	}

	@SuppressWarnings("rawtypes")
	public List getArrayBeanReportDataSource(String sqlMap, Object u) {
		// List ls = super.sqlDao_h.getRecordList(sqlMap, u);
		// return ls;
		return null;
	}

	public void viewXlsReport_itransc() throws Exception {

		String downloadName = this.reportName + new Date().getTime();
		downloadName = URLEncoder.encode(downloadName, "utf-8");
		JRDataSource ds = new JRBeanCollectionDataSource(this.Maplist);
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				getJasperReport(this.reportName), this.parameter, ds);
		this.response.reset();
		this.response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		this.response.setCharacterEncoding("UTF-8");
		this.response.setHeader("Content-Disposition", "attachment;filename=\""
				+ downloadName + ".xls\"");
		javax.servlet.ServletOutputStream outputstream = this.response
				.getOutputStream();
		JRExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputstream);
		exporter.setParameter(
				JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
				Boolean.TRUE);
		exporter.setParameter(
				JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET,
				Boolean.FALSE);
		exporter.setParameter(
				JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND,
				Boolean.FALSE);
		exporter.exportReport();
		outputstream.flush();
		outputstream.close();
	}

	public void viewPdfReport_itransc() throws Exception {
		String downloadName = this.reportName + new Date().getTime();
		downloadName = URLEncoder.encode(downloadName, "utf-8");
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
				this.Maplist);
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				getJasperReport(this.reportName), this.parameter, ds);
		byte[] bReport = JasperExportManager.exportReportToPdf(jasperPrint);
		this.response.reset();
		response.setContentType("application/pdf");
		this.response.setCharacterEncoding("UTF-8");
		this.response.setHeader("Content-Disposition", "attachment;filename=\""
				+ downloadName + ".pdf\"");
		javax.servlet.ServletOutputStream outputstream = this.response
				.getOutputStream();
		outputstream.write(bReport);
		outputstream.flush();
		outputstream.close();

	}

	public void viewHtmlReport_itransc() throws Exception {
		// jRDataSource=sqlDao_h.getConn();
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				getJasperReport(this.reportName), this.parameter,
				this.jRDataSource);
		// request.getSession().setAttribute(
		// ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
		String imageURI = request.getContextPath() + "/report/images/";
		String imageDIR = this.request.getSession().getServletContext()
				.getRealPath(this.reportPath + "/images/");
		this.response.reset();
		this.response.setContentType("text/html");
		this.response.setCharacterEncoding("UTF-8");

		javax.servlet.ServletOutputStream outputstream = this.response
				.getOutputStream();
		JRHtmlExporter exporter = new JRHtmlExporter();

		exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING,
				"UTF-8");
		exporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM,
				outputstream);
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
				Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,
				Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, imageDIR);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, imageURI);

		exporter.exportReport();
		outputstream.flush();
		outputstream.close();
	}

	private boolean existFile(String reportName, String fileSuffix) {
		String fileRealPath = this.request
				.getSession()
				.getServletContext()
				.getRealPath(
						this.reportPath + "/" + reportName + "." + fileSuffix);
		File file = new File(fileRealPath);
		return file.exists();
	}

	private JasperReport getJasperReport(String reportName) throws Exception {
		String fileRealPath;
		if (this.complie) {
			fileRealPath = this.request.getSession().getServletContext()
					.getRealPath(this.reportPath + "/" + reportName + ".jrxml");
			JasperCompileManager.compileReportToFile(fileRealPath);
		}
		fileRealPath = this.request.getSession().getServletContext()
				.getRealPath(this.reportPath + "/" + reportName + ".jasper");
		File file = new File(fileRealPath);
		return (JasperReport) JRLoader.loadObject(file.getPath());
	}

	public void setReportName(String reportName) {

	}

}
