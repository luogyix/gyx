package com.agree.abt.action.configManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.agree.abt.model.confManager.VipCustInfo;
import com.agree.abt.service.confmanager.IVipCustInfoService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.util.ExcelUtils;

public class VipCustInfoAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(VipCustInfoAction.class);
	private IVipCustInfoService vipCustInfoService;
	private File upload;
	private String uploadFileName;

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public IVipCustInfoService getVipCustInfoService() {
		return vipCustInfoService;
	}

	public void setVipCustInfoService(IVipCustInfoService vipCustInfoService) {
		this.vipCustInfoService = vipCustInfoService;
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
		return SUCCESS;
	}

	public String queryVipCustInfo() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject json = JSONObject.fromObject(jsonString);
		Page page = getPage(json);
		List<VipCustInfo> list = vipCustInfoService.queryVipCustInfo(json, page);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(page, list, ret);
		return AJAX_SUCCESS;
	}

	public String importVipCustInfo() throws Exception {
		String jsonString = super.getJsonString();
		ServiceReturn sRet = vipCustInfoService.importVipCustInfo(jsonString);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	public void exportVipCustInfo() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj;
		if("".equals(jsonString)){
			jsonObj = new JSONObject();
		}else {
			jsonObj = JSONObject.fromObject(jsonString);
		}
		
		List<VipCustInfo> list = vipCustInfoService.exportVipCustInfo(jsonString);
		String path="华润公司VIP客户名单.xls";
		String file = "华润公司VIP客户名单";
		doExcel(path, list, jsonObj, file);
		//导出excel返回
	}

	public String delVipCustInfo() throws Exception {
		//String jsonString = super.getJsonString();
		//ret返回
		return AJAX_SUCCESS;
	}

	public void upLoadFile() throws IOException {
		JSONObject jObject = new JSONObject();
		HttpServletResponse rsp = getResponse();
		rsp.setContentType("text/html; charset=utf-8");  
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		String filePath = path + File.separator + "assets" + File.separator + "vipcustinfo"+ File.separator + uploadFileName;
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
		            //[客户名称, VIP卡号, 客户类型, 客户经理联系电话] 此格式题头的excel才是合格excel
		            switch (i) {
					case 0:
						if(!"客户名称".equals(title)){
							title_flag = false;
						}
						break;
					case 1:
						if(!"VIP卡号".equals(title)){
							title_flag = false;
						}
						break;
					case 2:
						if(!"客户类型".equals(title)){
							title_flag = false;
						}
						break;
					case 3:
						if(!"客户经理联系电话".equals(title)){
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
	        List<VipCustInfo> list = new ArrayList<VipCustInfo>();//这个list用于保存之前的数据
	        for (int i = 1; i <= rowNum; i++) {
	            row = sheet.getRow(i);
	            if(row!=null){
		            String custinfo_name = ExcelUtils.getStringCellValue(row.getCell(0)).trim();
	                String vip_card = ExcelUtils.getStringCellValue(row.getCell(1)).trim();
	                if(vip_card.indexOf(".")!=-1){
	                	vip_card = vip_card.substring(0, vip_card.indexOf("."));
	                }
	                String custtype = ExcelUtils.getStringCellValue(row.getCell(2)).trim();
	                String custinfo_tel = ExcelUtils.getStringCellValue(row.getCell(3)).trim();
	                if(custinfo_tel.indexOf(".")!=-1){
	                	custinfo_tel = custinfo_tel.substring(0, custinfo_tel.indexOf("."));
	                }
	                //判断数据内容是否正确
	                boolean flag = true;
	                if("".equals(custinfo_name)||"".equals(vip_card)||//判断有空数据
	                		"".equals(custtype)||"".equals(custinfo_tel)){
	    				jObject.put("result", "数据内容不允许存在空值,请确认后再次上传!");
	    	        	flag=false;
	                }else if(custinfo_name.length()>60){
	    				jObject.put("result", custinfo_name + "的客户名称长度不能超过60,请确认后再次上传!");
	    	        	flag=false;
		            }else if(vip_card.length()>10){
	                	jObject.put("result", custinfo_name + "的VIP卡号长度不能超过10,请确认后再次上传!");
	                	flag=false;
	                }
	                for (VipCustInfo vipCustInfo : list) {
						if(custinfo_name.equals(vipCustInfo.getCustinfo_name())){
							jObject.put("result", "客户名称为[" + custinfo_name + "]的客户数据重复,请确认后再次上传!");
		                	flag=false;
		                	break;
						}
						if(vip_card.equals(vipCustInfo.getVip_card())){
							jObject.put("result", "卡号为[" + vip_card + "]的客户数据重复,请确认后再次上传!");
							flag=false;
							break;
						}
					}
	                if(!flag){
	    	        	jObject.put("success", "false");
	    	        	rsp.getWriter().print(jObject.toString());
	    	        	return;
	    			}
	                VipCustInfo info = new VipCustInfo();
	                info.setCustinfo_name(custinfo_name);
	                info.setVip_card(vip_card);
	                info.setCusttype(custtype);
	                info.setCustinfo_tel(custinfo_tel);
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
		logger.info("VIP客户信息Excel文件上传成功");
    	jObject.put("success", "true");
    	jObject.put("result", "文件上传成功");
    	rsp.getWriter().print(jObject.toString());
	}
}
