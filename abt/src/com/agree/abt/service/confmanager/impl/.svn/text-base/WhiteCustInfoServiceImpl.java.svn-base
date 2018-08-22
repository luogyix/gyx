package com.agree.abt.service.confmanager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.agree.abt.model.confManager.WhiteCustInfo;
import com.agree.abt.service.confmanager.IWhiteCustInfoService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.base.BaseService;
import com.agree.util.ExcelUtils;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class WhiteCustInfoServiceImpl extends BaseService implements IWhiteCustInfoService {
	private static final Logger logger = LoggerFactory.getLogger(WhiteCustInfoServiceImpl.class);
	@Transactional
	public List<WhiteCustInfo> queryWhiteCustInfo(JSONObject json, Page page) throws Exception{
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute(ApplicationConstants.LOGONUSER);
		String hql = "from WhiteCustInfo where branch =:branch or branch in (select unitid from Unit where unitlist like :unitlist)";
		Map<String, String> map = new HashMap<String, String>();
		map.put("branch", user.getUnitid());
		map.put("unitlist", "%|"+user.getUnitid()+"|%");
		List list = sqlDao_h.queryPage(hql , map, page, false);
		return list;
	}
	@Transactional
	public ServiceReturn importWhiteCustInfo(String jsonString) throws Exception{
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute(ApplicationConstants.LOGONUSER);// 返回登录用户信息
		JSONObject json = JSONObject.fromObject(jsonString);
		String filename = json.getString("filename");
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		String filePath = path + File.separator + "assets" + File.separator + "whitecustinfo"+ File.separator + filename;
		InputStream is = new FileInputStream(filePath);
		Map<String,String> map1 = new HashMap<String, String>();
		Workbook wb;
		if(filename.endsWith(".xls")){//读取文件的设备
			wb = new HSSFWorkbook(is);
		}else{
			wb = new XSSFWorkbook(is);
		}
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(0);
        int rowNum = sheet.getLastRowNum();
        StringBuffer sbf = new StringBuffer();
        for (int i = 1; i <= rowNum; i++) {  
            row = sheet.getRow(i);  
            if(row!=null){
            	WhiteCustInfo info = new WhiteCustInfo();
                String custmomer_num = ExcelUtils.getStringCellValue(row.getCell(0)).trim();
                if(custmomer_num.indexOf(".")!=-1){
                	custmomer_num = custmomer_num.substring(0, custmomer_num.indexOf("."));
                }
                info.setCustmomer_num(custmomer_num);
                String branch =user.getUnitid();
                String branch1 = row.getCell(1).toString();
	            if(branch1!=null&&!"".equals(branch1)&&branch1.length()>0){
	            	branch = ExcelUtils.getStringCellValue(row.getCell(1)).trim();
	            }
                if(branch.indexOf(".")!=-1){
                	branch = branch.substring(0, branch.indexOf("."));
                }
                info.setBranch(branch);
                String recman_tel = ExcelUtils.getStringCellValue(row.getCell(2)).trim();
                if(recman_tel.indexOf(".")!=-1){
                	recman_tel = recman_tel.substring(0, recman_tel.indexOf("."));
                }
                info.setRecman_tel(recman_tel);
                try {
                	Map<String, String> map = new HashMap<String, String> ();
                	map.put("custmomer_num",info.getCustmomer_num());
                	String hql = "from WhiteCustInfo where custmomer_num = :custmomer_num";
                	List<WhiteCustInfo> list = sqlDao_h.getRecordList(hql,map,false);
                	if(list.size()>0){
                		if(info.getBranch().equals(list.get(0).getBranch())){
                			String[] param ={info.getRecman_tel(),info.getCustmomer_num()};
                			String sql ="update T_BSMS_WHITECUSTINFO set RECMAN_TEL=? where CUSTMOMER_NUM=?";
                			sqlDao_h.excuteSql(sql, param);
                		}else{
                			map1.put(info.getCustmomer_num(), list.get(0).getBranch());
                		}
                	}else{
                		sqlDao_h.saveRecord(info);
                	}
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
            }
        }
        //删除文件
        File file = new File(filePath);  
        if(file.exists()){
        	file.delete();
        }
        ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "1");
        String custmuns="";
        String branchs="";
        if(map1.size()>0){
        	Set set = map1.keySet();
        	int i=0;
        	for (Object key : set) {
        		custmuns += ","+key;
    			branchs +=  ","+map1.get(key);
    			i++;
    			if(i>=11){
    				break;
    			}
    		}
        	if(i>10){
        		sbf.append("客户号["+custmuns.substring(1)+"]已存在,\n机构号:["+branchs.substring(1)+"];......");
        	}else{
        		sbf.append("客户号["+custmuns.substring(1)+"]已存在,\n机构号:["+branchs.substring(1)+"];");
        	}
        	sRet = new ServiceReturn(ServiceReturn.SUCCESS, sbf.toString());
        }
        return sRet;
	}
	@Transactional
	public ServiceReturn delWhiteCustInfo(String custmomer_nums) throws Exception {
		String[] custmomer_num_list = custmomer_nums.substring(1).split(",");
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		for (int i = 0; i < custmomer_num_list.length; i++) {
			sqlDao_h.deleteById(WhiteCustInfo.class, custmomer_num_list[i]);
		}
		return ret;
	}
	@Transactional
	public ServiceReturn addWhiteCustInfo(String jsonString) throws Exception {
		JSONObject obj = JSONObject.fromObject(jsonString);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		WhiteCustInfo custInfo = (WhiteCustInfo) JSONObject.toBean(obj, WhiteCustInfo.class);
		String sql ="insert into T_BSMS_WHITECUSTINFO (BRANCH, RECMAN_TEL, CUSTMOMER_NUM) values (?, ?, ?)";
		String[] param ={custInfo.getBranch(),custInfo.getRecman_tel(),custInfo.getCustmomer_num()};
		try{
			sqlDao_h.excuteSql(sql, param);
		}catch(Exception e){
			ret = new ServiceReturn(ServiceReturn.SUCCESS, "客户号["+custInfo.getCustmomer_num()+"]已存在,请重新输入客户号");
		}
		return ret;
	}
	@Transactional
	public ServiceReturn editWhiteCustInfo(String jsonString) throws Exception {
		JSONObject obj = JSONObject.fromObject(jsonString);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		WhiteCustInfo custInfo = (WhiteCustInfo) JSONObject.toBean(obj, WhiteCustInfo.class);
		try{
			String[] param ={custInfo.getRecman_tel(),custInfo.getCustmomer_num()};
			String sql = "update T_BSMS_WHITECUSTINFO set RECMAN_TEL=? where CUSTMOMER_NUM=?";
			sqlDao_h.excuteSql(sql, param);
		}catch(Exception e){
			 ret = new ServiceReturn(ServiceReturn.SUCCESS, "客户号["+custInfo.getCustmomer_num()+"]已存在,请重新输入客户号");
		}
		return ret;
	}
}
