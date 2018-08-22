package com.agree.abt.action.configManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.agree.abt.model.confManager.WhiteCustInfo;
import com.agree.abt.service.confmanager.IWhiteCustInfoService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.ExcelUtils;

public class WhiteCustInfoAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(WhiteCustInfoAction.class);
	private IWhiteCustInfoService whiteCustInfoService;
	private File upload;
	private String uploadFileName;

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public IWhiteCustInfoService getWhiteCustInfoService() {
		return whiteCustInfoService;
	}

	public void setWhiteCustInfoService(IWhiteCustInfoService whiteCustInfoService) {
		this.whiteCustInfoService = whiteCustInfoService;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	/**
	 * 跳转页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1,super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	public String queryWhiteCustInfo() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject json = JSONObject.fromObject(jsonString);
		Page page = getPage(json);
		List<WhiteCustInfo> list = whiteCustInfoService.queryWhiteCustInfo(json, page);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(page, list, ret);
		return AJAX_SUCCESS;
	}

	public String importWhiteCustInfo() throws Exception {
		String jsonString = super.getJsonString();
		ServiceReturn sRet = whiteCustInfoService.importWhiteCustInfo(jsonString);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	//public void exportWhiteCustInfo() throws Exception {
	//	String jsonString = super.getJsonString();
//		JSONObject jsonObj;
//		if("".equals(jsonString)){
//			jsonObj = new JSONObject();
//		}else {
//			jsonObj = JSONObject.fromObject(jsonString);
//		}
//		
	//	List<WhiteCustInfo> list = WhiteCustInfoService.exportWhiteCustInfo(jsonString);
	//	String path="华润公司VIP客户名单.xls";
		//String file = "华润公司VIP客户名单";
		//doExcel(path, list, jsonObj, file);
		//导出excel返回
//	}

	public String delWhiteCustInfo() throws Exception {
		String jsonString = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String custmomer_nums = "";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
			Object obj[] = jsonObj.values().toArray();
			custmomer_nums = custmomer_nums + "," + obj[0].toString();
		}
		ServiceReturn ret = whiteCustInfoService.delWhiteCustInfo(custmomer_nums);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}

	public void upLoadFile() throws IOException {
		JSONObject jObject = new JSONObject();
		HttpServletResponse rsp = getResponse();
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute(ApplicationConstants.LOGONUSER);// 返回登录用户信息
		rsp.setContentType("text/html; charset=utf-8");  
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		String filePath = path + File.separator + "assets" + File.separator + "whitecustinfo"+ File.separator + uploadFileName;
		logger.info("文件上传路径:" + filePath);
		FileInputStream fis = new FileInputStream(upload);
		//生成父文件夹
		File file = new File(filePath);
		File parent = file.getParentFile();//父文件夹
		if(!parent.exists()){
			parent.mkdirs();
		}
		try {
			//读取文件头判断此文件格式是否合格
			//POIFSFileSystem fs = new POIFSFileSystem(in);
			Workbook wb;
			if(uploadFileName.endsWith(".xls")){//读取文件的设备
				wb = new HSSFWorkbook(fis);
			}else{
				wb = new XSSFWorkbook(fis);
			}
			fis = new FileInputStream(upload);//再次读取这个数据...
			Sheet sheet = wb.getSheetAt(0);
			//标题验证
			Row row = sheet.getRow(0);
			if(row!=null){
				int colNum = row.getPhysicalNumberOfCells();
				boolean title_flag = true;
		        for (int i=0; i<colNum; i++) {  
		            String title = row.getCell((int)i).getStringCellValue();
		            //[客户号, 接收信息人手机号码] 此格式题头的excel才是合格excel
		            switch (i) {
					case 0:
						if(!"客户号".equals(title)){
							title_flag = false;
						}
						break;
					case 1:
						if(!"所在网点".equals(title)){
							title_flag = false;
						}
						break;
					case 2:
						if(!"接收信息人手机号码".equals(title)){
							title_flag = false;
						}
						break;
					}
		        }
		        if(!title_flag){
		        	logger.error("Excel标题格式与要求不符,请确认后再次上传!");
					jObject.put("result", "Excel标题格式与要求不符,请确认后再次上传!");
		        	jObject.put("success", "false");
		        	rsp.getWriter().print(jObject.toString());
					return;
		        }
			}else{
				logger.error("Excel文件为空文件,请确认后再次上传!");
				jObject.put("result", "Excel文件为空文件,请确认后再次上传!");
	        	jObject.put("success", "false");
	        	rsp.getWriter().print(jObject.toString());
				return;
			}
			
			//得到总行数
	        int rowNum = sheet.getLastRowNum();
	        //正文内容应该从第二行开始,第一行为表头的标题
	        List<WhiteCustInfo> list = new ArrayList<WhiteCustInfo>();//这个list用于保存之前的数据
	        for (int i = 1; i <= rowNum; i++) {
	            row = sheet.getRow(i);
	            if(row!=null){
		            String custmomer_num = ExcelUtils.getStringCellValue(row.getCell(0)).trim();
		            String branch;
		            if(row.getCell(1)==null){
		            	 branch = user.getUnitid();
		            }else{
		            	branch = ExcelUtils.getStringCellValue(row.getCell(1)).trim();
		            }
		            String recman_tel = ExcelUtils.getStringCellValue(row.getCell(2)).trim();
	               
	                //判断数据内容是否正确
	                boolean flag = true;
	                if("".equals(custmomer_num)||"".equals(recman_tel)){
	    				jObject.put("result", "数据内容不允许存在空值,请确认后再次上传!");
	    	        	flag=false;
	                }else if(custmomer_num.length()>80){
	    				jObject.put("result", custmomer_num + "的客户号长度不能超过80,请确认后再次上传!");
	    	        	flag=false;
		            }else if(recman_tel.length()>30){
	                	jObject.put("result", custmomer_num + "的接收信息人手机号码长度不能超过15,请确认后再次上传!");
	                	flag=false;
	                }else if(branch.length()>60){
	                	jObject.put("result", custmomer_num + "的所属网点长度不能超过60,请确认后再次上传!");
	                	flag=false;
	                }
	                for (WhiteCustInfo whiteCustInfo : list) {
						if(custmomer_num.equals(whiteCustInfo.getCustmomer_num())){
							jObject.put("result", "客户名称为[" + custmomer_num + "]的客户数据重复,请确认后再次上传!");
		                	flag=false;
		                	break;
						}
					}
	                if(!flag){
	    	        	jObject.put("success", "false");
	    	        	rsp.getWriter().print(jObject.toString());
	    	        	return;
	    			}
	                WhiteCustInfo info = new WhiteCustInfo();
	                info.setCustmomer_num(custmomer_num);
	                info.setBranch(branch);
	                info.setRecman_tel(recman_tel);
	                list.add(info);
	            }
	        }
			byte[] buffer = new byte[1024];
			int len = 0;
			FileOutputStream fos = new FileOutputStream(file);
			while((len = fis.read(buffer))>0){
				fos.write(buffer, 0, len);
			}
			fos.close();
		} catch (Exception e) {
			logger.error("上传文件出错", e);
        	jObject.put("success", "false");
        	jObject.put("result", "上传文件出错");
        	rsp.getWriter().print(jObject.toString());
			return;
		}finally{
			fis.close();
		}
		logger.info("白名单客户信息Excel文件上传成功");
    	jObject.put("success", "true");
    	jObject.put("result", "文件上传成功");
    	rsp.getWriter().print(jObject.toString());
	}
	/**
	 *  修改白名单客户信息
	 * @return
	 * @throws Exception
	 */
	public String editWhiteCustInfo() throws Exception{
		String json = getJsonString();
		ServiceReturn ret = whiteCustInfoService.editWhiteCustInfo(json);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	/**
	 * 增加白名单客户信息
	 * @return
	 * @throws Exception
	 */
	public String addWhiteCustInfo() throws Exception{
		String json = getJsonString();
		ServiceReturn ret = whiteCustInfoService.addWhiteCustInfo(json);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
}
