package com.agree.abt.action.pfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

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
import com.agree.abt.model.pfs.CustInterest;
import com.agree.abt.service.pfs.ICustRateService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.DateUtil;
import com.agree.util.ExcelUtils;
import com.agree.util.IDictABT;


/**
 * 费率信息
 * @ClassName: CustRateAction.java
 * @company 赞同科技
 * @author 王明哲
 * @date 2016-12-26
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CustRateAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(BtCustRate.class);
	ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
	private String STYLE_EXCEL = "excel";//导出Excel
	private ABTComunicateNatp cona;
	private File upload;
	private ICustRateService  custrateservice;
	public ICustRateService getCustrateservice()
	{
		return custrateservice;
	}

	public void setCustrateservice(ICustRateService custrateservice) 
	{
		this.custrateservice = custrateservice;
	}

	public File getUpload() 
	{
		return upload;
	}

	public void setUpload(File upload) 
	{
		this.upload = upload;
	}


	public String getUploadFileName() 
	{
		return uploadFileName;
	}


	public void setUploadFileName(String uploadFileName) 
	{
		this.uploadFileName = uploadFileName;
	}

	private String uploadFileName;
	
	
	
	public ABTComunicateNatp getCona() 
	{
		return cona;
	}


	public void setCona(ABTComunicateNatp cona) 
	{
		this.cona = cona;
	}

	/**
	 * 加载主页面
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception
	{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD2,super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	/**
	 * 费率信息分页查询
	 * @return
	 * @throws Exception
	 */
	public String queryCustRatePage() throws Exception
	{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		String produceName = obj.getString("produce_name").trim();
		Page pageInfo = this.getPage(obj);
		//查询交易接口
		cona.setBMSHeader("ibp.bms.b217_1.01", user);
		//上送字段
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.PRODUCE_NAME,produceName);
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		//分页逻辑
		String loopNum = (String) map.get("infosize").get(0);
		int num = Integer.parseInt(loopNum);
		//总条数		
		pageInfo.setTotal(num);
		// 得到总页数
		// 获取总页数
		Integer pageNo = (pageInfo.getTotal() % pageInfo.getLimit() == 0) ? pageInfo
				.getTotal() / pageInfo.getLimit()
				: pageInfo.getTotal() / pageInfo.getLimit() + 1;
		// 查询最后一页
		if (pageInfo.getStart() == -1) {
			pageInfo.setRowStart((pageNo - 1) * pageInfo.getLimit());
			pageInfo.setRowEnd(pageInfo.getRowStart()
					+ (pageInfo.getTotal() % pageInfo.getLimit() == 0 ? pageInfo
							.getLimit() : pageInfo.getTotal()
							% pageInfo.getLimit()));
			// 设置总页数
			pageInfo.setPage(pageNo);
			// 设置总条数
			pageInfo.setTotal(pageInfo.getTotal());
		} else {
				pageInfo.setRowStart((pageInfo.getStart() - 1)
						* pageInfo.getLimit());
				// 每页显示条数
				pageInfo.setRowEnd((pageInfo.getRowStart() + pageInfo.getLimit()) <= pageInfo
						.getTotal() ? (pageInfo.getRowStart() + pageInfo.getLimit())
						: pageInfo.getTotal());
				// 当前页
				pageInfo.setPage(pageNo);
				// 总条数
				pageInfo.setTotal(pageInfo.getTotal());
		 	}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(int i = pageInfo.getRowStart(); i < pageInfo.getRowEnd(); i++)
		{
			Map<String,Object> hld = new HashMap<String,Object>();
			//机构号
			hld.put(IDictABT.BRANCH, user.getUnitid());
			//产品名称
			hld.put(IDictABT.PRODUCE_NAME, map.get("produce_name").get(i));
			//收费项目
			hld.put(IDictABT.CHARGE_PROJECT, map.get("charge_project").get(i));
			//收费标注
			hld.put(IDictABT.CHARGE_CRITERION, map.get("charge_criterion").get(i));
			//收费依据
			hld.put(IDictABT.CHARGE_REASON, map.get("charge_reason").get(i));
			//创建时间
			hld.put(IDictABT.CREATE_DATE, map.get("create_date").get(i));
			//备注
			hld.put(IDictABT.CUST_NOTE, map.get("cust_note").get(i));
			//修改时间
			hld.put(IDictABT.UPDATE_TIME, map.get("update_time").get(i));
			//查询结果添加至集合
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 费率信息新增
	 * @return
	 * @throws Exception
	 */
	public String addCustRate() throws Exception
	{
		HttpServletRequest req = ServletActionContext.getRequest();
		//用户登录信息
		User user = (User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//定义JSON字符串
		String inputJsonStr = super.getJsonString();
		//得到页面传递值
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		/**
		 * 执行新增操作
		 */
		//查询交易码，与后台afa通讯
		cona.setBMSHeader("ibp.bms.b217_2.01", user);
		/**
		 * 上送修改字段
		 */
		//机构号
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//产品名称
		cona.set(IDictABT.PRODUCE_NAME, obj.getString("produce_name"));
		//收费项目
		cona.set(IDictABT.CHARGE_PROJECT,obj.getString("charge_project") );
		//收费标准
		cona.set(IDictABT.CHARGE_CRITERION,obj.getString("charge_criterion"));
		//收费依据
		cona.set(IDictABT.CHARGE_REASON,obj.getString("charge_reason"));
		//创建时间（获取当前系统时间为创建时间）
		cona.set("create_date",DateUtil.getDateTime());
		//备注
		cona.set(IDictABT.CUST_NOTE,obj.getString("cust_note"));
		//判断afa返回结果
		cona.exchange();
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(retJson.toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 费率信息修改
	 * @return
	 * @throws Exception
	 */
	public String editCustRate() throws Exception
	{
		HttpServletRequest req = ServletActionContext.getRequest();
		//用户登录信息
		User user = (User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		//得到页面传递值
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		/**
		 * 执行修改操作
		 */
		//查询交易码，与后台afa通讯
		cona.setBMSHeader("ibp.bms.b217_3.01", user);
		/**
		 * 上送修改条件字段
		 */
		//得到页面传递的修改之前的值
		JSONObject old = (JSONObject) obj.get("old");
		//机构号
		cona.set("oldbranch", old.getString("branch"));
		//产品名称
		cona.set("oldproduce_name", old.getString("produce_name"));
		//收费项目
		cona.set("oldcharge_project", old.getString("charge_project"));
		//收费标准
		cona.set("oldcharge_criterion", old.getString("charge_criterion"));
		/**
		 * 上送需要修改的字段
		 */
		//机构号
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//产品名称
		cona.set(IDictABT.PRODUCE_NAME, obj.getString("produce_name"));
		//收费项目
		cona.set(IDictABT.CHARGE_PROJECT,obj.getString("charge_project") );
		//收费标准
		cona.set(IDictABT.CHARGE_CRITERION,obj.getString("charge_criterion"));
		//收费依据
		cona.set(IDictABT.CHARGE_REASON, obj.getString("charge_reason"));
		//修改时间（获取系统当前时间作为修改日期）
		cona.set("update_time",DateUtil.getDateTime());
		//备注
		cona.set(IDictABT.CUST_NOTE,obj.getString("cust_note"));
		//判断afa返回结果
		cona.exchange();
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(ret);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
		
	}
	/**
	 * 费率信息删除
	 * @return
	 * @throws Exception
	 */
	public String deleteCusRate() throws Exception
	{
		HttpServletRequest req = ServletActionContext.getRequest();
		//用户登录信息
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		//批量删除
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		/**
		 * 执行删除操作
		 */
		for (int i = 0; i < jsonArray.size(); i++) 
		{
			//得到页面传递值
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
			Object obj[] = jsonObj.values().toArray();
			//重置通讯
			cona.reInit();
			//查询交易码，与后台afa通讯
			cona.setBMSHeader("ibp.bms.b217_4.01", user);
			//机构号
			cona.set(IDictABT.BRANCH,obj[0].toString());
			//产品名称
			cona.set(IDictABT.PRODUCE_NAME, obj[1].toString());
			//收费项目
			cona.set(IDictABT.CHARGE_PROJECT, obj[2].toString());
			//收费标准
			cona.set(IDictABT.CHARGE_CRITERION, obj[3].toString());
			//判断afa的返回结果
			cona.exchange();
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	/**
	 * 导出Excel
	 * @return
	 * @throws Exception
	 */
	public void exportExcel()throws Exception{
		int totalSum = 0;
		String jsonString = ServletActionContext.getRequest().getParameter("querycondition_str");
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		List<CustInterest> list = new ArrayList<CustInterest>();
		Page pageInfo = new Page();
		pageInfo.setStart(1);
		pageInfo.setPageflag(4);
		pageInfo.setLimit(totalSum);
		User user = super.getLogonUser(false);
		
		natp(jsonObj, list, pageInfo, user, this.STYLE_EXCEL, totalSum);
		
		String path="CustRateBook.xls";		
		String file = "机构费率信息";
		super.doExcel(path, list, jsonObj, file);
	}
	/** 
	 * @Title: natp 
	 * @Description: 报文发送并接收
	 * @param @param jsonObj 参数
	 * @param @param list 返回的list数据集合
	 * @param @param pageInfo 分页类
	 * @param @param user 登陆用户
	 * @param @throws Exception    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void natp( JSONObject jsonObj, List list, Page pageInfo, User user,  String selfStyle,  int totalSum) throws Exception
	{
		//调用查询交易，导出Excel之前先做一次查询
		cona.setBMSHeader("ibp.bms.b217_1.01", user);
		cona.set("branch", jsonObj.getString("branch"));
		cona.set("produce_name", jsonObj.getString("produce_name"));
		
		Map<String,List<String>> map = cona.exchange();
		//查询到记录总条数
		String loopNum = (String) map.get("infosize").get(0);
		int num = Integer.parseInt(loopNum);
		
		for(int i = 0;i < num;i++)
		{
			//机构费率信息实体类
			BtCustRate bcr = new BtCustRate();
			//机构号
			bcr.setBranch(map.get("branch").get(i));
			//产品名称
			bcr.setProduce_name(map.get("produce_name").get(i));
			//收费项目
			bcr.setCharge_project(map.get("charge_project").get(i));
			//收费标准
			bcr.setCharge_criterion(map.get("charge_criterion").get(i));
			//收费依据
			bcr.setCharge_reason(map.get("charge_reason").get(i));
			//创建日期
			bcr.setCreate_date(map.get("create_date").get(i));
			//修改日期
			bcr.setUpdate_time(map.get("update_time").get(i));
			//备注
			bcr.setCust_note(map.get("cust_note").get(i));
			list.add(bcr);
		}
	}
	
	/**
	 * 上传Excel文件
	 * @return
	 * @throws Exception
	 *
	 */
	public void upLoadFile() throws Exception
	{
		JSONObject jObject = new JSONObject();
		HttpServletResponse rsp = getResponse();
		//设置编码格式
		rsp.setContentType("text/html; charset=utf-8");  
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		//文件上传路径
		String filePath = path + File.separator + "assets" + File.separator + "btcustrate"+ File.separator + uploadFileName;
		logger.info("文件上传路径:" + filePath);
		FileInputStream fis = new FileInputStream(upload);
		//生成父文件夹
		File file = new File(filePath);
		//父文件夹
		File parent = file.getParentFile();
		/**
		 * 判断父文件夹是否存在，如果不存在，自动创建
		 */
		if(!parent.exists())
		{
			parent.mkdirs();
		}
		try {
			//读取文件头判断此文件格式是否合格
			Workbook wb;
			/**
			 * 判断文件格式，
			 * HSSFWorkbook是操作Excel2003以前的（包括2003）的版本，扩展名是.xls
			 * XSSFWorkbook是操作Excel2007的版本，扩展名是.xlsx
			 */
			if(uploadFileName.endsWith(".xls")){
				wb = new HSSFWorkbook(fis);
			}else{
				wb = new XSSFWorkbook(fis);
			}
			//再次读取这个数据
			fis = new FileInputStream(upload);
			Sheet sheet = wb.getSheetAt(0);
			//标题验证
			Row row = sheet.getRow(0);
			if(row!=null){
				int colNum = row.getPhysicalNumberOfCells();
				boolean title_flag = true;
		        for (int i = 0; i < colNum; i++) 
		        {  
		            String title = row.getCell((int)i).getStringCellValue();
		            //[产品名称,收费项目,收费标准]此格式题头的excel才是合格excel
		            switch (i) 
		            {
						case 0:
							if(!"产品名称".equals(title))
							{
								title_flag = false;
							}
							break;
						case 1:
							if(!"收费项目".equals(title))
							{
								title_flag = false;
							}
							break;
						case 2:
							if(!"收费标准".equals(title))
							{
								title_flag = false;
							}
							break;
						case 3:
							if(!"收费依据".equals(title))
							{
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
	        //正文内容应该从第二行开始,第一行为表头的标题，list用于保存之前的数据
	        List<BtCustRate> list = new ArrayList<BtCustRate>();
	        for (int i = 1; i <= rowNum; i++) 
	        {
	            row = sheet.getRow(i);
	            //得到文件中对应的值，并去掉空格
	            if(row!=null)
	            {
	            	String produce_name = ExcelUtils.getStringCellValue(row.getCell(0)).trim();
	                String charge_project = ExcelUtils.getStringCellValue(row.getCell(1)).trim();
	                String charge_criterion = ExcelUtils.getStringCellValue(row.getCell(2)).trim();
	                String charge_reason = ExcelUtils.getStringCellValue(row.getCell(3)).trim();
	                //判断数据内容是否正确
	                boolean flag = true;
	                //判断必须数据是否有空
	                if(" ".equals(produce_name)||" ".equals(charge_project)||
	                		" ".equals(charge_criterion) || " ".equals(charge_reason))
	                {
	    				jObject.put("result", "数据内容不允许存在空值,请确认后再次上传!");
	    	        	flag=false;
	                }else if(produce_name.length()>32){
	    				//判断产品名称是否标准
	                	jObject.put("result", produce_name + "的产品名称长度不能超过32,请确认后再次上传!");
	    	        	flag=false;
	                }
	                if(!flag){
	    	        	jObject.put("success", "false");
	    	        	rsp.getWriter().print(jObject.toString());
	    	        	return;
	    			}
	                //费率信息实体类
	                BtCustRate bct = new BtCustRate();
	                //产品名称
	                bct.setProduce_name(produce_name);
	                //收费标准
	                bct.setCharge_criterion(charge_criterion);
	                //收费项目
	                bct.setCharge_project(charge_project);
	                //收费依据
	                bct.setCharge_reason(charge_reason);
	                list.add(bct);
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
			logger.info("机构费率信息上传成功！");
	    	jObject.put("success", "true");
	    	jObject.put("result", "文件上传成功");
	    	rsp.getWriter().print(jObject.toString());
	}
	/**
	 * 导入数据
	 * @return
	 * @throws Exception
	 */
	public String importCustRate() throws Exception
	{
		String jsonString = super.getJsonString();
		//调用导入方法
		ServiceReturn sRet = custrateservice.importCustRate(jsonString);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}	
}
