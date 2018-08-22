package com.agree.abt.service.pfs;

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
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import com.agree.abt.model.pfs.BtCustRate;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.base.BaseService;
import com.agree.util.DateUtil;
import com.agree.util.ExcelUtils;

public class CustRateServiceImpl extends BaseService implements ICustRateService
{
	private static final Logger logger = LoggerFactory.getLogger(CustRateServiceImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public ServiceReturn importCustRate(String jsonString) throws Exception 
	{
		// 返回登录用户信息
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		JSONObject json = JSONObject.fromObject(jsonString);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		//得到当前文件名称
		String filename = json.getString("filename");
		//文件当前路径
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		//文件上传路径
		String filePath = path + File.separator + "assets" + File.separator + "btcustrate"+ File.separator + filename;
		InputStream is = new FileInputStream(filePath);
		//Excel对象
		Workbook wb;
		/**
		 * 判断文件格式，
		 * HSSFWorkbook是操作Excel2003以前的（包括2003）的版本，扩展名是.xls
		 * XSSFWorkbook是操作Excel2007的版本，扩展名是.xlsx
		 */
		if(filename.endsWith(".xls"))
		{
			wb = new HSSFWorkbook(is);
		}else{
			wb = new XSSFWorkbook(is);
		}
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(0);
        //得到总行数  
        int rowNum = sheet.getLastRowNum()+1;
        //读取文件
        for(int i = 1; i < rowNum; i++ )
        {
        	row = sheet.getRow(i);  
        	if( row != null)
        	{
        		//费率信息实体类
        		BtCustRate bcr = new  BtCustRate();
        		//机构号
        		bcr.setBranch(user.getUnitid());
        		//产品名称
        		String produce_name = ExcelUtils.getStringCellValue(row.getCell(0)).trim();
        		bcr.setProduce_name(produce_name);
        		//收费项目
        		String charge_project = ExcelUtils.getStringCellValue(row.getCell(1)).trim();
        		bcr.setCharge_project(charge_project);
        		//收费标准
        		String charge_criterion = ExcelUtils.getStringCellValue(row.getCell(2)).trim();
        		bcr.setCharge_criterion(charge_criterion);
        		//收费依据
        		String charge_reason = ExcelUtils.getStringCellValue(row.getCell(3)).trim();
        		bcr.setCharge_reason(charge_reason);
        		//创建时间
        		String create_date = DateUtil.getDateTime();
        		bcr.setCreate_date(create_date);
        		//备注
        		String cust_note = ExcelUtils.getStringCellValue(row.getCell(4)).trim();
        		bcr.setCust_note(cust_note);
        		try {
	                	Map<String, String> map = new HashMap<String, String>();
	                	map.put("branch", bcr.getBranch());
	                	map.put("produce_name",bcr.getProduce_name());
	                	map.put("charge_project", bcr.getCharge_project());
	                	/**
	                	 * 执行查询语句
	                	 * 查询条件：机构号，产品名称，收费项目
	                	 */
	                	String hql = "from BtCustRate where produce_name = :produce_name and charge_project =:charge_project and branch =:branch";
	                	//查询结果
	                	List<BtCustRate> list = sqlDao_h.getRecordList(hql,map,false);
	                	int size = list.size();
	                	logger.debug("查询到的记录是"+size);
	                	/**
	                	 * 如果size=0，说明数据库里没有相同的记录，直接执行添加
	                	 * 如果size>0，说明数据库里有相同的记录，修改收费标准
	                	 */
	                	if(list.size() > 0)
	                	{
                			/**
                			 * 调用修改方法，根据产品名称和收费项目修改收费标准
                			 */
                			String[] param ={bcr.getCharge_criterion(),bcr.getCharge_reason(),bcr.getProduce_name(),bcr.getCharge_project()};
                			String sql ="update BT_CUST_RATE set CHARGE_CRITERION=?,CHARGE_REASON=? where PRODUCE_NAME=? and CHARGE_PROJECT=?";
                			sqlDao_h.excuteSql(sql, param);
                		
	                	}else{
	                		/**
	                		 * 执行添加操作
	                		 * 执行操作时，出现主键冲突的问题，设置联合主键，确定数据的唯一性
	                		 */
	                		sqlDao_h.saveRecord(bcr);
	                	}
				}catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
        	}
        }
        //删除文件
        File file = new File(filePath);  
        if(file.exists())
        {
        	file.delete();
        }
        return ret;
	}
}
