package com.agree.abt.action.dataAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.dataAnalysis.CardMachineNum;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
/**
 * 自主交易流水报表统计
 * <p>
 * 统计发卡机个业务交易量
 * </p>
 * @author 高艺祥<br>
 * 		       最后修改日期：2017--0-10
 * 
 * @version 0.1
 *
 */
public class CardMachineNumAction extends BaseAction {

	private static final long serialVersionUID = 2895581337216669276L;
	
	private ABTComunicateNatp cona;
	
	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}

	/**
	 * 加载页面
	 * @author 高艺祥<br>
	 * 		   最后修改日期：2017-01-12
	 * @return /webpages/abt/dataAnalysis/CardMachineNum.jsp
	 * @version 1.0
	 * @throws Exception
	 */
	public String loadPage() throws Exception {
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());//获取部门集合
		sRet.put(ServiceReturn.FIELD2, super.getLogonUser(true));//获取登录用户信息
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 获得所有发卡机菜单码
	 * @throws Exception
	 */
	public void queryMenucode() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		cona.setBMSHeader("ibp.bms.b312_2.01", user);
		Map<String, List<String>> map = cona.exchange();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		int num = Integer.parseInt(map.get("menuCode_Size").get(0));
		for (int i = 0; i < num; i++) {
			Map<String, String> hld = new HashMap<String, String>();
			hld.put("dictvalue", map.get("dictvalue").get(i));
			hld.put("dicttype", map.get("dicttype").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	/**
	 * 发卡机交易流水报表统计
	 * @author 高艺祥<br>
	 * 			最后修改日期：2017-01-12
	 * @version 1.0
	 */
	public String queryCardMachineNum() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String jsonString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(jsonString);
		
		//用户选择的开始日期
		String startDate = obj.getString("startDate").replace("-", "");
		//用户选择的结束日期
		String endDate   = obj.getString("endDate").replace("-", "");
		String deviceno  = obj.getString("deviceno");
		String menu_code = obj.getString("menu_code");
		
		cona.setBMSHeader("ibp.bms.b312_1.01", user);
		cona.set("branch", obj.getString("branch"));//机构号
		cona.set("start_date", startDate);//开始日期
		cona.set("end_date", endDate);//结束日期
		if(deviceno != null && !deviceno.equals("")){
			cona.set("deviceno", deviceno);//机具编号
		}
		if(menu_code != null && !menu_code.equals("")){
			cona.set("menu_code", menu_code);//机具编号
		}
		Map<String, List<String>> map = cona.exchange();
		
		String loopNum = (String) map.get("serialDateSize").get(0);
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
			// 交易时间
			hld.put("deviceno", map.get("deviceno").get(i));
			// 发卡机柜员号
			hld.put("serial_date", map.get("serial_date").get(i));
			//交易名称
			hld.put("menu_code", map.get("menu_code").get(i));
			// 卡号
			hld.put("menu_name", map.get("menu_name").get(i));
			// 户名
			hld.put("executeNum", map.get("executenum").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		// 想页面返回结果
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 导出excel
	 * @author 高艺祥<br>
	 * 			最后修改日期：2017-01-12
	 * @version 1.0
	 * @throws Exception 
	 * 
	 */
	public void exportExcel() throws Exception{
		int totalSum = 0;
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String parameter = request.getParameter("querycondition_str");
		JSONObject jsonObj = JSONObject.fromObject(parameter);
		List<CardMachineNum> list = new ArrayList<CardMachineNum>();
		Page pageInfo = new Page();
		pageInfo.setStart(1);
		pageInfo.setPageflag(4);
		pageInfo.setLimit(totalSum);
		natp(jsonObj, list , user, "excel", totalSum);
		
		String path="CardMachineNumBook.xls";		
		String file = "自助交易报表统计";
		super.doExcel(path, list, jsonObj, file);
		
	}
	
	public void natp( JSONObject jsonObj, List<CardMachineNum> list, User user,  String selfStyle,  int totalSum) throws Exception{
		
		//用户选择的开始日期
		String startDate = jsonObj.getString("startDate").replace("-", "");
		//用户选择的结束日期
		String endDate   = jsonObj.getString("endDate").replace("-", "");
		String deviceno  = jsonObj.getString("deviceno");
		String menu_code = jsonObj.getString("menu_code");
		
		cona.setBMSHeader("ibp.bms.b312_1.01", user);
		cona.set("branch", jsonObj.getString("branch"));//机构号
		cona.set("start_date", startDate);//开始日期
		cona.set("end_date", endDate);//结束日期
		if(deviceno != null && !deviceno.equals("")){
			cona.set("deviceno", deviceno);//机具编号
		}
		if(menu_code != null && !menu_code.equals("")){
			cona.set("menu_code", menu_code);//机具编号
		}
		Map<String, List<String>> map = cona.exchange();
		
		String loopNum = (String) map.get("serialDateSize").get(0);
		int num = Integer.parseInt(loopNum);
		

		// 循环得到的当前页所有的值
		for (int i = 0; i < num; i++) {
			CardMachineNum cardMachineNum = new CardMachineNum();
			// 机构号
			cardMachineNum.setBranch(map.get("branch").get(i));
			// 交易时间
			cardMachineNum.setDeviceno(map.get("deviceno").get(i));
			// 发卡机柜员号
			cardMachineNum.setSerial_date(map.get("serial_date").get(i));
			//交易名称
			cardMachineNum.setMenu_code(map.get("menu_code").get(i));
			// 卡号
			cardMachineNum.setMenu_name(map.get("menu_name").get(i));
			// 户名
			cardMachineNum.setExecuteNum(map.get("executenum").get(i));
			list.add(cardMachineNum);
		}
	}
}
