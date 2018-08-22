package com.agree.abt.service.confmanager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.agree.abt.model.confManager.VipCustInfo;
import com.agree.abt.service.confmanager.IVipCustInfoService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.service.base.BaseService;
import com.agree.util.ExcelUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class VipCustInfoServiceImpl extends BaseService implements IVipCustInfoService {
	private static final Logger logger = LoggerFactory.getLogger(VipCustInfoServiceImpl.class);
	@Transactional
	public List<VipCustInfo> queryVipCustInfo(JSONObject json, Page page) throws Exception{
		String hql = "from VipCustInfo";
		Map<String, String> map = new HashMap<String, String>();
		List list = sqlDao_h.queryPage(hql , map, page, false);
		return list;
	}
	@Transactional
	public ServiceReturn importVipCustInfo(String jsonString) throws Exception{
		JSONObject json = JSONObject.fromObject(jsonString);
		String filename = json.getString("filename");
		String start_date = json.getString("start_date").replace("-", "");
		//String end_date = json.getString("end_date").replace("-", "");
		//{"filename":"华润公司VIP客户名单.xls","start_date":"2014-08-21","end_date":"2014-08-22"}
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		String filePath = path + File.separator + "assets" + File.separator + "vipcustinfo"+ File.separator + filename;
		//String filePath = path + File.separator + "assets" + File.separator + "vipcustinfo"+ File.separator + uploadFileName;
		InputStream is = new FileInputStream(filePath);
        
        Workbook wb;
		if(filename.endsWith(".xls")){//读取文件的设备
			wb = new HSSFWorkbook(is);
		}else{
			wb = new XSSFWorkbook(is);
		}
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(0);
        //得到总行数  
        int rowNum = sheet.getLastRowNum();
        //删除表内已有数据
        Map map = new HashMap();
        sqlDao_h.excuteHql("delete from VipCustInfo",map);
        //正文内容应该从第二行开始,第一行为表头的标题  
        for (int i = 1; i <= rowNum; i++) {  
            row = sheet.getRow(i);  
            if(row!=null){
            	VipCustInfo info = new VipCustInfo();
            	info.setCustinfo_name(ExcelUtils.getStringCellValue(row.getCell(0)).trim());
                String vip_card = ExcelUtils.getStringCellValue(row.getCell(1)).trim();
                logger.info("切割前的vipcard:" + vip_card);
                if(vip_card.indexOf(".")!=-1){
                	vip_card = vip_card.substring(0, vip_card.indexOf("."));
                }
                info.setVip_card(vip_card);
                info.setCusttype(ExcelUtils.getStringCellValue(row.getCell(2)).trim());
                String custinfo_tel = ExcelUtils.getStringCellValue(row.getCell(3)).trim();
                logger.info("切割前的custinfo_tel:" + custinfo_tel);
                if(custinfo_tel.indexOf(".")!=-1){
                	custinfo_tel = custinfo_tel.substring(0, custinfo_tel.indexOf("."));
                }
                info.setCustinfo_tel(custinfo_tel);
                info.setStart_date(start_date);
                //info.setEnd_date(end_date);
                logger.info("name: " + info.getCustinfo_name() +",vipcard:" + info.getVip_card() + ",tel:" + info.getCustinfo_tel());
                try {
                	sqlDao_h.saveRecord(info);
				} catch (Exception e) {
					sqlDao_h.updateRecord(info);
				}
            }
        }
       
        //删除文件
        File file = new File(filePath);  
        if(file.exists()){
        	file.delete();
        }
        ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		return sRet;
	}
	@Transactional
	public List<VipCustInfo> exportVipCustInfo(String jsonString) throws Exception {
		List list = sqlDao_h.getAllRecords(VipCustInfo.class);
		return list;
	}
	
	@Transactional
	public ServiceReturn delVipCustInfo(String jsonString) throws Exception {
		return null;
	}
}
