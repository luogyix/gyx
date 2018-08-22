package com.agree.abt.action.pfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.abt.model.pfs.BtPfsUpdatepackage;
import com.agree.abt.service.pfs.IBtPfsUpdatepackageService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.util.DateUtil;


public class BtPfsUpdatepackageAction extends BaseAction {
    /**
	 * TODO
     * @author zhaoming,nanzheng
     * @date 2014-1-20 下午01:51:55
     * @copyright (c) by 赞同科技 2014
     * 对手持端上传更新下载的逻辑
	 */
	private static final Logger logger = LoggerFactory.getLogger(BtPfsUpdatepackageAction.class);
	private static final long serialVersionUID = 1L;
    private IBtPfsUpdatepackageService updatePackageService;
    private FileInputStream fileInputStream;
    private String downloadFileName;
    
    private File upload;
    
  /*
   * 加载jsp
   */
    public String loadPage() throws Exception {
 		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
 		JSONObject retObj = super.convertServiceReturnToJson(sRet);
 		ServletActionContext.getRequest().setAttribute(
 				ApplicationConstants.ACTIONRESULT, retObj.toString());
 		return SUCCESS;
 	}
    
    /**
     * 上传文件，添加包信息到数据库
     * @return
     * @throws Exception
     */
	public String uploadPackage() throws Exception {
		String packagename = null; 
		HttpServletResponse response = ServletActionContext.getResponse();
		String packagetype = super.getRequest().getParameter("packagetype");
		packagetype = packagetype.substring(packagetype.length() - 3).toLowerCase();
		String versionid = super.getRequest().getParameter("versionid");
		String remark = super.getRequest().getParameter("remark");
		String updatedDateTime = DateUtil.getCurrentDateByFormat("yyyyMMddHHmmss");
		response.setContentType("text/html; charset=utf-8");
		String retdata = "{'success':false, msg:'上传失败'}";
		try {
			synchronized (ApplicationConstants.PACKAGECREATE_LOCK) {
		    retdata = this.updatePackageService.addPackage(packagetype,versionid,remark,updatedDateTime,packagename,getUpload());	
			}
		} catch (Exception e) {
			e.printStackTrace();
			retdata = "{'success':false, msg:'上传失败'}";
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			PrintWriter pw = response.getWriter();
			pw.println(retdata);
			pw.flush();
			pw.close();
		}
		return null;
	}   
    
    /*
     * 获取数据里的版本号和版本标识放在下拉框中
     */
    public String getSystemDictionaryItem() throws Exception{
    	List<BtPfsUpdatepackage> list = this.updatePackageService.query_all();
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
		return null;
	}
    
    /*
     * 根据条件查询信息
     */
    public String query_package() throws Exception{
     	 HttpServletRequest request = super.getRequest();
     	 HttpServletResponse response = super.getResponse();
     	 request.setCharacterEncoding("utf-8");
     	 response.setCharacterEncoding("UTF-8");
   	    JSONObject jsonObj = super.getInputJsonObject();
  	    Page pageInfo = this.getPage(jsonObj);
  	    List<?> list = this.updatePackageService.queryRecordPage(jsonObj,pageInfo);
   	  	ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
     	super.setActionResult(pageInfo,list,sRet); 	   
   	    return AJAX_SUCCESS;
      }
    /*
     * 删除数据和包
     */
    public String delete() throws Exception{
    	String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		String rcid=null;
		String packagename=null;
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
			Object obj[] = jsonObj.values().toArray();
			rcid = obj[0].toString();
			packagename = obj[1].toString();
		    this.updatePackageService.deleteBtPfsUpdatepackage(rcid,packagename);
		}
  	    ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sRet.put(ServiceReturn.FIELD3, "删除成功");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString()); 
  	    return AJAX_SUCCESS;
    }
    /*
     * 修改版本标识，包名称
     */
    public String update() throws Exception{  
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=utf-8");
		String packagetype = super.getRequest().getParameter("packagetype");
		String packagename = super.getRequest().getParameter("packagename");
		Long rcid = Long.parseLong(super.getRequest().getParameter("rcid"));
		Long version = Long.parseLong(super.getRequest().getParameter("version"));
		String versionid = super.getRequest().getParameter("versionid");
		String remark = super.getRequest().getParameter("remark");
		String updateDateTime = DateUtil.getCurrentDateByFormat("yyyyMMddHHmmss");
		String retdata = "{'success':false, msg:'更新失败'}";
		try {
			// 上传删除文件更新数据库数据
			this.updatePackageService.updateBtPfsUpdatepackage(packagename,
					getUpload(), rcid, packagetype, version, versionid, remark,
					updateDateTime);
			retdata = "{'success':true, msg: '更新成功 '}";
		} catch (Exception e) {
			retdata = "{'success':false, msg:'更新失败'}";
		} finally {
			PrintWriter pw = response.getWriter();
			pw.println(retdata);
			pw.flush();
			pw.close();
		}
		return null;
    }
    /*
     * 下载更新包
     */
    public String download() throws Exception{
		/**
		 * filepath 应该实践BtPfsUpdatepackage对象中存储的路径。
		 * 升级包分apk，zip两种，要下载哪种更新包需要前端给出信息。 目前前端没有给出这方面的信息，这点需要和前端确认。
		 * 
		 * 流程： 获得下载的更新包种类（apk/zip）->查询最新版本BtPfsUpdatepackage信息->将文件发送给前端
		 */
    	String rcid = super.getRequest().getParameter("rcid");
    	String packagename = super.getRequest().getParameter("packagename");
		String basePath = ApplicationConstants.getBasePath();
		String filepath = this.updatePackageService.filePath(rcid);
		File file = new File(basePath+filepath+packagename);
		if(!file.exists()){
			logger.info("所下载的更新包不存在!");
			throw new AppException("所下载的更新包不存在!");
		}
		FileInputStream fis = new FileInputStream(basePath+filepath+packagename);
		this.setFileInputStream(fis);
		this.setDownloadFileName(new String((packagename).getBytes(),"ISO8859-1"));
		return "downloadSuccess";
	}
    
    
	/** 
	 *获得数据库中最新版本信息仅仅给手持前端
	 */
	public String getNewRecordPage() throws Exception{
		HttpServletRequest request = super.getRequest();
	  	HttpServletResponse response = super.getResponse();
	    request.setCharacterEncoding("utf-8");
	   	response.setCharacterEncoding("UTF-8");
	    String packagename = request.getParameter("packagetype");
		BtPfsUpdatepackage updatepackage = this.updatePackageService.queryNewRecordPage(packagename);
		JSONObject jsonObject = JSONObject.fromObject(updatepackage);
		PrintWriter pw = super.getResponse().getWriter();
		pw.print(jsonObject);
		pw.flush();
		pw.close();		
		return null;
	}
	/**
	 * 修改前回显
	 * @throws Exception 
	 */
	public String queryBtpfsupdatepackage() throws Exception {
		JSONObject jsonObj = super.getInputJsonObject();
		BtPfsUpdatepackage btpfsupdatepackage =(BtPfsUpdatepackage)JSONObject.toBean(jsonObj,BtPfsUpdatepackage.class);
		long rcid = btpfsupdatepackage.getRcid();
	    btpfsupdatepackage = this.updatePackageService.queryBtpfsupdatepackage(rcid);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
	  	sRet.put(ServiceReturn.FIELD1, btpfsupdatepackage);
	  	JSONObject returnJson = super.convertServiceReturnToJson(sRet);
 		super.setActionresult(returnJson.toString()); 
    	return AJAX_SUCCESS;	
	}
	

	public IBtPfsUpdatepackageService getUpdatePackageService() {
		return updatePackageService;
	}
	
	public void setUpdatePackageService(IBtPfsUpdatepackageService updatePackageService) {
		this.updatePackageService = updatePackageService;
	}
	
	public FileInputStream getFileInputStream() {
		return fileInputStream;
	}


	public void setFileInputStream(FileInputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}


	public String getDownloadFileName() {
		return downloadFileName;
	}


	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
	public File getUpload() {
		return upload;
	}
	
	public void setUpload(File upload) {
		this.upload = upload;
	}
    
}
