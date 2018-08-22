package com.agree.abt.action.pfs;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.pfs.CustInterest;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/**
 * 执行利率管理Action
 * <P>
 * 读取利率，配置涨幅，计算得到执行利率(执行利率 = 利率*(1+涨幅))
 * </p>
 * @author 高艺祥<br>
 * 				最后修改时间：2016/12/22
 * @version 0.1
 *
 */
public class CustInterestAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	
	private String STYLE_EXCEL = "excel";//导出Excel
	
	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
	
	/**
	 * 加载利率管理页面
	 * @return jsp页面：/webpages/abt/pfs/CustInterest.jsp
	 */
	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD2,super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 获得基础利率配置
	 * @author 高艺祥 <br>
 	 *		        最后修改时间：2016/12/22
	 * @version 0.1
	 */
	public void queryAddRequirement() throws Exception{
		//获得rquest域
		HttpServletRequest request = ServletActionContext.getRequest();
		//从session域中获取user
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		cona.setBMSHeader("ibp.bms.b216_5.01", user);
		Map<String, List<String>> map = cona.exchange();
		
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String loopNum = map.get("queryNum").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, String> hld = new HashMap<String, String>();
			hld.put("intds", map.get("intds").get(i));
			hld.put("vlddat", map.get("vlddat").get(i));
			hld.put("int", map.get("int").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	/**
	 * 查询执行利率
	 * <p>
	 * 通过机构号查询
	 * </p>
	 * @author 高艺祥<br>
 	 *		        最后修改时间：2016/12/22
	 * @version 1.0
	 */
	public String queryBisfmintBisPage() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String jsonString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(jsonString);
		
		cona.setBMSHeader("ibp.bms.b216_2.01", user);
		//根据机构号，查询利率配置
		cona.set("branch", obj.getString("branch"));
		Map<String, List<String>> map = cona.exchange();
		
		String loopNum = (String) map.get("interestSize").get(0);
		int num = Integer.parseInt(loopNum);
		Page pageInfo = this.getPage(obj);

		// 总条数
		pageInfo.setTotal(num);
		// 分页逻辑
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
			// 总条树
			pageInfo.setTotal(pageInfo.getTotal());
		}

		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 循环得到的当前页所有的值
		for (int i = pageInfo.getRowStart(); i < pageInfo.getRowEnd(); i++) {
			HashMap<String, Object> hld = new HashMap<String, Object>();
			// 机构号
			hld.put("branch", map.get("branch").get(i));
			// 利率说明
			hld.put("intds", map.get("intds").get(i));
			// 货币代码
			hld.put("ccy", map.get("ccy").get(i));
			//利率(xxx.xxxx,小数点前最多3位，小数点后必须有4位，不足的补0),
			hld.put("int", new DecimalFormat("#,##0.0000").format(Double.parseDouble(map.get("int").get(i))));
			// 生效日期
			hld.put("vlddat", map.get("vlddat").get(i));
			// 涨幅
			hld.put("amount", new DecimalFormat("#,##0.0000").format(Double.parseDouble(map.get("amount").get(i))));
			//执行利率
			hld.put("ex_interest", new DecimalFormat("#,##0.0000").format(Double.parseDouble(map.get("ex_interest").get(i))));
			//修改时间
			hld.put("date_time", map.get("date_time").get(i));
			//添加时间
			hld.put("add_time", map.get("add_time").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);

		return AJAX_SUCCESS;
	}
	
	/**
	 * 添加执行利率
	 * <p>
	 * 		执行利率 = 利率 * (1 + 涨幅),格式：小数点前最多3三,小数点后必须4位，不足补0
	 * </p>
	 * @author 高艺祥<br>
	 * 					最后修改日期：2016-12-26-21:00
	 * @version 1.0
	 */
	public String addBisfmintBisPage() throws Exception{
		//获得request域
		HttpServletRequest request = ServletActionContext.getRequest();
		//从session域中的到user信息
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//获得页面请求负载
		String jsonString = super.getJsonString();
		//转为JSONObject
		JSONObject obj = JSONObject.fromObject(jsonString);
		
		cona.setBMSHeader("ibp.bms.b216_1.01", user);
		//机构号
		cona.set("branch", user.getUnitid());
		//利率
		double intV = Double.parseDouble(obj.getString("int"));
		//涨幅
		double amount = Double.parseDouble(obj.getString("amount"));
		//执行利率(利率*(1+涨幅)),格式(xxx.xxxx)在数据库中控制
		String ex_interest = new DecimalFormat("#,##0.0000").format(intV * (1 + amount));
		
		
		//货币代码
		cona.set("ccy", obj.getString("ccy").substring(0, 2));
		//利率
		cona.set("int", String.valueOf(intV));
		//涨幅
		cona.set("amount",String.valueOf(amount));
		//执行利率
		cona.set("ex_interest",ex_interest);
		//利率说明
		String intds = obj.getString("intds");
		cona.set("intds",intds);
		//生效日期
		cona.set("vlddat",obj.getString("vlddat").substring(0, 8));
		//添加时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
		cona.set("add_time", sdf.format(new Date()));
		
		//是否成功，以及得到返回结果
		cona.exchange();
		
		ServiceReturn tet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject resultJson = super.convertServiceReturnToJson(tet);
		super.setActionresult(resultJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 修改执行利率
	 * <p>
	 * 		intds(利率说明)、branch(机构号)作为修改条件
	 * </p>
	 * @author 高艺祥<br>
	 * 					最后修改日期：2016-12-27-11:24
	 * @version 1.0
	 */
	public String editBisfmintBisPage() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String jsonString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(jsonString);
		
		cona.setBMSHeader("ibp.bms.b216_3.01", user);
		//机构号
		cona.set("branch", user.getUnitid());
		//利率
		double intV = Double.parseDouble(obj.getString("int"));
		//涨幅
		double amount = Double.parseDouble(obj.getString("amount"));
		//执行利率(利率*(1+涨幅)),格式(xxx.xxxx)在数据库中控制
		String ex_interest = new DecimalFormat("#,##0.0000").format(intV * (1 + amount));
		
		
		//货币代码
		cona.set("ccy", obj.getString("ccy").substring(0, 2));
		//涨幅
		cona.set("amount",new DecimalFormat("#,##0.0000").format(amount));
		//执行利率
		cona.set("ex_interest",ex_interest);
		//利率说明
		cona.set("intds",obj.getString("intds"));
		//修改时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
		cona.set("date_time", sdf.format(new Date()));
		
		cona.exchange();
		
		ServiceReturn tet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject resultJson = super.convertServiceReturnToJson(tet);
		super.setActionresult(resultJson.toString());
		
		return AJAX_SUCCESS;
	}
	
	/**
	 * 批量删除执行利率
	 * <p>
	 * 	通过机构号和利率说明删除执行利率，同一机构下执行利率不可重复
	 * </p>
	 * @author 高艺祥<br>
	 * 					最后修改日期：2016-12-29-15:30
	 * @version 1.0
	 * @throws Exception
	 */
	public String delBisfmintBisPage() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//批量删除
		String jsonString = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
			
			//机构号
			String branch = jsonObj.getString("branch");
			//利率说明
			String intds = jsonObj.getString("intds");
			
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b216_4.01", user);
			cona.set("branch", branch);
			cona.set("intds", intds);
			cona.exchange();
		}
		
		cona.reInit();
		
		ServiceReturn tet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject resultJson = super.convertServiceReturnToJson(tet);
		super.setActionresult(resultJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 导出excel
	 * <p>
	 * 		查询出用户选择机构下所有执行利率
	 * </p>
	 * @author 高艺祥<br>
	 * 					最后修改日期：2016-12-29-14:27
	 * @version 1.0
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
		
		//证件类型
		Map<String,String> map2 = new HashMap<String,String>();
		map2.put("01", "人民币");
		
		for(int i=0; i<list.size(); i++){//循环翻译要导出到excel中的需要翻译的字段
			if(!"".equals(((CustInterest)list.get(i)).getCcy())&&map2.containsKey(((CustInterest)list.get(i)).getCcy())){
				//货币代码
				((CustInterest) list.get(i)).setCcy( map2.get(((CustInterest)list.get(i)).getCcy()));
			}
		}
		
		String path="CustInterestBook.xls";		
		String file = "执行利率";
		super.doExcel(path, list, jsonObj, file);
	}
	
	/**
	 * 查询出需要导出excel的数据
	 * <p>
	 * 		查询出用户选择机构下所有执行利率
	 * </p>
	 * @author 高艺祥<br>
	 * 					最后修改日期：2016-12-29-14:27
	 * @version 1.0
	 */
	public void natp( JSONObject jsonObj, List<CustInterest> list, Page pageInfo, User user,  String selfStyle,  int totalSum) throws Exception{
		cona.setBMSHeader("ibp.bms.b216_2.01", user);
		//根据机构号，查询利率配置
		cona.set("branch", jsonObj.getString("branch"));
		Map<String, List<String>> map = cona.exchange();
		
		String loopNum = (String) map.get("interestSize").get(0);
		int num = Integer.parseInt(loopNum);

		

		// 循环得到的当前页所有的值
		for (int i = 0; i < num; i++) {
			
			//执行利率实体类
			CustInterest custInterest = new CustInterest();

			// 机构号
			custInterest.setBranch(map.get("branch").get(i));
			
			// 利率说明
			custInterest.setIntds(map.get("intds").get(i));
			
			// 货币代码
			custInterest.setCcy(map.get("ccy").get(i));
			
			//利率(xxx.xxxx,小数点前最多3位，小数点后必须有4位，不足的补0),
			custInterest.setIntV(new DecimalFormat("#,##0.0000").format(Double.parseDouble(map.get("int").get(i))));
			
			// 生效日期
			custInterest.setVlddat(map.get("vlddat").get(i));
			
			// 涨幅
			custInterest.setAmount(new DecimalFormat("#,##0.0000").format(Double.parseDouble(map.get("amount").get(i))));
			
			//执行利率
			custInterest.setEx_interest(new DecimalFormat("#,##0.0000").format(Double.parseDouble(map.get("ex_interest").get(i))));
			
			//修改时间
			custInterest.setDate_time(map.get("date_time").get(i));
			
			//添加时间
			custInterest.setAdd_time(map.get("add_time").get(i));
			
			list.add(custInterest);
		}
	}
}
