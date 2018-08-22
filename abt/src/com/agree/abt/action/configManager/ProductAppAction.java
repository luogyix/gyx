package com.agree.abt.action.configManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.agree.abt.service.confmanager.IProductService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

@SuppressWarnings({ "rawtypes" })
public class ProductAppAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(ProductAppAction.class);// 日志

	private File upload;
	private File upload1;
	private File upload2;
	private File upload3;

	private String uploadFileName;
	private String upload1FileName;
	private String upload2FileName;
	private String upload3FileName;

	private File uploadFiles;
	private String upload_fileNames;
	private String upload_productid;
	private String uploaddetails_lableid;
	private static int uploadFileNum = 0;

	public String getUpload_fileNames() {
		return upload_fileNames;
	}

	public void setUpload_fileNames(String upload_fileNames) {
		this.upload_fileNames = upload_fileNames;
	}

	public String getUpload_productid() {
		return upload_productid;
	}

	public void setUpload_productid(String upload_productid) {
		this.upload_productid = upload_productid;
	}

	public String getUploaddetails_lableid() {
		return uploaddetails_lableid;
	}

	public void setUploaddetails_lableid(String uploaddetails_lableid) {
		this.uploaddetails_lableid = uploaddetails_lableid;
	}

	public File getUploadFiles() {
		return uploadFiles;
	}

	public void setUploadFiles(File uploadFiles) {
		this.uploadFiles = uploadFiles;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public File getUpload1() {
		return upload1;
	}

	public void setUpload1(File upload1) {
		this.upload1 = upload1;
	}

	public File getUpload2() {
		return upload2;
	}

	public void setUpload2(File upload2) {
		this.upload2 = upload2;
	}

	public File getUpload3() {
		return upload3;
	}

	public void setUpload3(File upload3) {
		this.upload3 = upload3;
	}

	public String getUpload1FileName() {
		return upload1FileName;
	}

	public void setUpload1FileName(String upload1FileName) {
		this.upload1FileName = upload1FileName;
	}

	public String getUpload2FileName() {
		return upload2FileName;
	}

	public void setUpload2FileName(String upload2FileName) {
		this.upload2FileName = upload2FileName;
	}

	public String getUpload3FileName() {
		return upload3FileName;
	}

	public void setUpload3FileName(String upload3FileName) {
		this.upload3FileName = upload3FileName;
	}

	public IProductService productService;

	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	/**
	 * 进入页面 将登录用户信息、部门集合、和创建用户的时候的默认密码传到页面
	 * 
	 * @Title: loadPage
	 * @param @return
	 * @param @throws Exception 参数
	 * @return String 返回类型
	 * @throws
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");

		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(
				ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}

	/**
	 * 查询产品
	 */
	public String queryProducts() throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String jsonString = super.getJsonString();
		List<Map> tlist = this.productService.getProducts(jsonString);

		Page pageInfo = new Page(); // 得到Page分页对象

		super.setActionResult(pageInfo, tlist, ret);
		return AJAX_SUCCESS;
	}

	/**
	 * 添加产品
	 * */
	public String addProduct() throws Exception {
		String jsonString = super.getJsonString();
		String info = this.productService.addProduct(jsonString);
		if (info != null && info.trim().length() != 0) {
			throw new AppException(info);
		}
		ServiceReturn tet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	public String editProduct() throws Exception {
		String jsonString = super.getJsonString();
		this.productService.editProduct(jsonString);
		
		ServiceReturn tet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject resultJson = super.convertServiceReturnToJson(tet);
		super.setActionresult(resultJson.toString());
		
		return AJAX_SUCCESS;
	}

	/**
	 * 
	 * 删除产品
	 * */
	public String deleteProducts() throws Exception {
		String jsonString = super.getJsonString();
		String info = this.productService.deleteProduct(jsonString);
		if (info != null && info.trim().length() > 0) {
			throw new AppException(info);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}

	/**
	 * 查询产品类别
	 * 
	 * @return
	 * @throws Exception
	 */
	public void queryProductClass() throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		HttpServletRequest request = ServletActionContext.getRequest();
		String branch = request.getParameter("branch");
		List<Map> tlist = productService.getProductClass(branch);
		ret.put(ServiceReturn.FIELD1, tlist);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter()
				.print(super.convertServiceReturnToJson(ret));

	}

	/**
	 * 添加产品类别
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addProductClass() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		String strJson = request.getParameter("strJson");
		String info = this.productService.addProductClass(strJson);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(info);
		return AJAX_SUCCESS;

	}

	/**
	 * 修改产品类别
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editproductClass() throws Exception {
		String jsonString = super.getJsonString();
		String info = this.productService.editProductClass(jsonString);
		if (info != null && info.trim().length() > 0) {
			throw new AppException(info);
		}
		return AJAX_SUCCESS;
	}

	/**
	 * 通过ID删除产品类别
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteProductClass() throws Exception {
		String jsonString = super.getJsonString();
		String info = this.productService.deleteProductClass(jsonString);
		if (info != null && info.trim().length() > 0) {
			throw new AppException(info);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "删除成功");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}

	/**
	 * 上传缩略图
	 * 
	 * @throws Exception
	 */
	public void upLoadFile() throws Exception {
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute(ApplicationConstants.LOGONUSER);
		/*
		 * FtpUtil ftp=new FtpUtil(); NatpConfigBean configBean=
		 * (NatpConfigBean)
		 * ServletActionContext.getServletContext().getAttribute
		 * (ApplicationConstants.NATPCONFIGBEAN); ftp.connectServer(configBean);
		 * try { logger.info("filesize:" +
		 * ftp.upload(upload.getAbsolutePath(),this.uploadFileName) + "字节");
		 * ftp.closeServer();
		 * 
		 * } catch (Exception e) { logger.error(e.getMessage(),e); }
		 */
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=utf-8");
		String upload_productid = super.getRequest().getParameter(
				"upload_productid");
		String upload_lableid = super.getRequest().getParameter(
				"upload_lableid");
		String branch = user.getUnitid();
		JSONObject jObject = new JSONObject();
		this.uploadFileName = branch + upload_productid + upload_lableid
				+ ".png";
		try {
			this.productService.upLoadFile(this.uploadFileName, this.upload);
		} catch (Exception e) {
			logger.error("上传文件出错", e);
			ServletActionContext.getResponse().setContentType("text/html");
			jObject.put("success", "false");
			ServletActionContext.getResponse().getWriter()
					.print(jObject.toString());
			return;
		}
		ServletActionContext.getResponse().setContentType("text/html");

		jObject.put("success", "false");
		ServletActionContext.getResponse().getWriter()
				.print(jObject.toString());
	}

	/**
	 * 上传详情图片
	 * 
	 * @throws Exception
	 */
	public void uploadDetailsPic() throws Exception {
		/*
		 * User user = (User)
		 * ServletActionContext.getRequest().getSession().getAttribute
		 * (ApplicationConstants.LOGONUSER); HttpServletRequest request =
		 * ServletActionContext.getRequest();
		 * request.setCharacterEncoding("utf-8"); String PRODUCTID =
		 * ServletActionContext
		 * .getRequest().getParameter("uploaddetails_productid"); String LABLEID
		 * =
		 * ServletActionContext.getRequest().getParameter("uploaddetails_lableid"
		 * ); String DETAILSPIC01 =
		 * ServletActionContext.getRequest().getParameter("upload01"); String
		 * DETAILSPIC02 =
		 * ServletActionContext.getRequest().getParameter("upload02"); String
		 * DETAILSPIC03 =
		 * ServletActionContext.getRequest().getParameter("upload03"); String
		 * branch = user.getUnitid(); JSONObject jObject = new JSONObject();
		 * String uploadFileName1 = branch+PRODUCTID+LABLEID+"01.png"; String
		 * uploadFileName2 = branch+PRODUCTID+LABLEID+"02.png"; String
		 * uploadFileName3 = branch+PRODUCTID+LABLEID+"03.png";
		 * this.upload1FileName = uploadFileName1; this.upload2FileName =
		 * uploadFileName2; this.upload3FileName = uploadFileName3; try {
		 * if(!"".equals(DETAILSPIC01)){
		 * this.productService.upLoadFile(this.upload1FileName, this.upload1); }
		 * } catch (Exception e) { logger.error("上传文件出错",e);
		 * ServletActionContext.getResponse().setContentType("text/html");
		 * ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		 * jObject.put("false1", "上传文件一出错");
		 * //ServletActionContext.getResponse()
		 * .getWriter().print(jObject.toString()); //return; }
		 * 
		 * 
		 * try { if(!"".equals(DETAILSPIC02)){
		 * this.productService.upLoadFile(this.upload2FileName, this.upload2); }
		 * } catch (Exception e) { logger.error("上传文件出错",e);
		 * ServletActionContext.getResponse().setContentType("text/html");
		 * ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		 * jObject.put("false2", "上传文件二出错");
		 * //ServletActionContext.getResponse()
		 * .getWriter().print(jObject.toString()); //return; }
		 * 
		 * try { if(!"".equals(DETAILSPIC03)){
		 * this.productService.upLoadFile(this.upload3FileName, this.upload3); }
		 * } catch (Exception e) { logger.error("上传文件出错",e);
		 * ServletActionContext.getResponse().setContentType("text/html");
		 * ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		 * jObject.put("false3", "上传文件三出错");
		 * //ServletActionContext.getResponse()
		 * .getWriter().print(jObject.toString()); //return; }
		 * if("{}".equals(jObject.toString()) ){
		 * ServletActionContext.getResponse().setContentType("text/html");
		 * jObject.put("success", "true");
		 * ServletActionContext.getResponse().getWriter
		 * ().print(jObject.toString()); }else{
		 * ServletActionContext.getResponse().setContentType("text/html");
		 * ServletActionContext
		 * .getResponse().getWriter().print(jObject.toString()); return; }
		 */
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute(ApplicationConstants.LOGONUSER);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		String branch = user.getUnitid();
		String result = "{'success':false,'message':'上传失败！'}";
		String[] fileNames = null;

		// DiskFileItemFactory factory = new DiskFileItemFactory();
		// ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			/*
			 * List items = upload.parseRequest(request); // 上传文件解析 Iterator itr
			 * = items.iterator(); // 枚举方法 while (itr.hasNext()) { FileItem item
			 * = (FileItem) itr.next(); if (item.getName() != null &&
			 * !item.getName().equals("")) {
			 * this.productService.upLoadFile(item.getName(), (File) item);
			 * result ="{'success':true,'message':'success!'}"; }else{ result
			 * ="{'success':false,'message':'failure！'}"; } }
			 */

			fileNames = this.upload_fileNames.split(",");
			String fileName = branch + this.upload_productid
					+ this.uploaddetails_lableid + uploadFileNum + ".png";
			productService.upLoadFile(fileName, uploadFiles);
			result = "{'success':true,'message':'上传成功！','filename':'"
					+ fileName + "'}";
		} catch (Exception e) {
			uploadFileNum = 0;
			logger.error(e.getMessage(), e);
			result = "{'success':false,'message':'上传失败！'}";
		}
		uploadFileNum++;// 记录上传文件个数
		if (uploadFileNum == fileNames.length) {
			uploadFileNum = 0;
		}
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(result);

	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public byte[] getFileBytes(File upload) {
		byte[] buffer = null;
		try {
			FileInputStream stream = new FileInputStream(upload);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			buffer = bos.toByteArray();
			bos.close();
			stream.close();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return buffer;
	}

}
