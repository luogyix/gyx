package com.agree.abt.action.configManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.abt.model.confManager.ThemeInfo;
import com.agree.abt.service.confmanager.IThemeManagerService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

public class ThemeManagerAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(ThemeManagerAction.class);
	private static final long serialVersionUID = 1L;  
	private ABTComunicateNatp cona;
	private IThemeManagerService themeManagerService;
	private File upload;  
	private String uploadContentType;
	private String uploadFileName;//fileName前面必須和upload一致,不然找不到文件  
	private static final int BUFFER_SIZE = 16 * 1024;
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		List<ThemeInfo> list = themeManagerService.queryThemeInfo(user.getUnitid());
		req.setAttribute("themeInfo", list);
		return SUCCESS;
	}
	
	/**
	 * 加载管理页面
	 */
	public String loadPage4Manager() throws Exception {
		return "success_manager";
	}
	/**
	 * 查询主题列表
	 */
	public String queryTheme() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		
		Page pageInfo = new Page();
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		List<ThemeInfo> list = themeManagerService.queryThemeInfo(user.getUnitid());
		try {
			super.setActionResult(pageInfo, list, ret);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return AJAX_SUCCESS;
	}
	/**
	 * 分页查询主题列表
	 */
	public String queryTheme4Page() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		
		Page pageInfo = this.getPage(jsonObj); //得到Page分页对象	
		List<ThemeInfo> list = themeManagerService.queryTheme4Page(jsonObj,pageInfo,user.getUnitid());//分页查询用户
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setDefault_flag("0".equals(list.get(i).getDefault_flag())?"1":"0");
		}
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 添加主题
	 */
	public String addTheme() throws Exception{
		String inputJsonStr = super.getJsonString();
		ServiceReturn ret = themeManagerService.addThemeInfo(inputJsonStr);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	/**
	 * 修改主题
	 */
	public String editTheme() throws Exception{
		String inputJsonStr = super.getJsonString();
		ServiceReturn ret = themeManagerService.editThemeInfo(inputJsonStr);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	/**
	 * 删除主题
	 */
	public String delTheme() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		String theme_ids = "";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
			Object obj[] = jsonObj.values().toArray();
			theme_ids = theme_ids + "," + obj[0].toString();
		}
		ServiceReturn ret = themeManagerService.delThemeInfo(theme_ids);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	
	
    // 上传文件的文件名  
    private String getFileExp(String name) {  
        int pos = name.lastIndexOf(".");  
        return name.substring(pos);  
    }
    public void saveFile() throws Exception{  
        Date d = new Date();  
        HttpServletRequest req = ServletActionContext.getRequest();
        String name = (String)req.getAttribute("imgName");
        logger.info("imgName   = " + name);
        //upload --  wapps 下面的文件夹,用来存放图片  
        String toSrc = ServletActionContext.getServletContext().getRealPath("themeUpload")+ "\\" +d.getTime()+getFileExp(this.uploadFileName);  //使用時間戳作為文件名  
        logger.info("uploadFileName = "+this.uploadFileName);  
        logger.info("toFile= "+toSrc);  
        File toFile = new File(toSrc);  
        writeFile(this.upload,toFile);  
        JSONObject jObject = new JSONObject(); 
        jObject.put("success", "true");
        ServletActionContext.getResponse().getWriter().print(jObject.toString());
    }  
  
    private static void writeFile(File src, File dst) {  
        try {  
            InputStream in = null;  
            OutputStream out = null;  
            try {  
                in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);  
                out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);  
                byte[] buffer = new byte[BUFFER_SIZE];  
                while (in.read(buffer) > 0) {
                    out.write(buffer);  
                }  
            } finally {  
                if (null != in) {  
                    in.close();  
                }  
                if (null != out) {  
                    out.close();  
                }  
            }  
        } catch (Exception e) {  
        	logger.error(e.getMessage(),e);
        }
        logger.info("写入成功！");  
    }

    public void setUpload(File upload) {  
        this.upload = upload;  
    }  
    public String getUploadContentType() {  
        return uploadContentType;  
    }  
    public void setUploadContentType(String uploadContentType) {  
        this.uploadContentType = uploadContentType;  
    }  
    public String getUploadFileName() {  
        return uploadFileName;  
    }  
    public void setUploadFileName(String uploadFileName) {  
        this.uploadFileName = uploadFileName;  
    }
	public ABTComunicateNatp getCona() {
		return cona;
	}
	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
	public IThemeManagerService getThemeManagerService() {
		return themeManagerService;
	}
	public void setThemeManagerService(IThemeManagerService themeManagerService) {
		this.themeManagerService = themeManagerService;
	}  
}
