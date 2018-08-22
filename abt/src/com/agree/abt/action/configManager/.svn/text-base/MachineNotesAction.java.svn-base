package com.agree.abt.action.configManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/**
 * 发卡机交易流水查询
 * @author 高艺祥<br>
 * 			2017-01-15
 *@version 1.0
 */
public class MachineNotesAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private ABTComunicateNatp cona;
	
	

	public ABTComunicateNatp getCona() {
		return cona;
	}



	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}



	/**
	 * 初始化页面
	 * @author 高艺祥<bt>
	 * 					最后修改日期：2016-12-30-10:24
	 * @return /webpages/abt/devmanager/MachineNotes.jsp
	 * @throws Exception
	 * @version 1.0
	 */
	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		//Set<String> priv=this.getUserInpageFunc();
		//sRet.put(ServiceReturn.FIELD2,priv);//获取登录用户信息 也就是利用这个获取到此部分功能
		sRet.put(ServiceReturn.FIELD2,super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 发卡机交易流水查询
	 * <p>
	 * 		可通过机构号、交易种类、交易时间（开始到结束）查询流水
	 * </P
	 * @author 高艺祥<br>
	 * 					最后修改日期：2016-12-30-14:23
	 * @version 1.0
	 * @throws Exception
	 */
	public String queryMachineNotes() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String jsonString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(jsonString);
		cona.setBMSHeader("ibp.bms.b406_1.01", user);
		//用户选择的开始日期
		String startDate = obj.getString("workdate_start").replace("-", "");
		//用户选择的结束日期
		String endDate   = obj.getString("workdate_end").replace("-", "");
		String cardmachine_tellerno = obj.getString("cardmachine_tellerno");
		
		cona.set("branch", obj.getString("branch"));
		cona.set("start_date", startDate);
		cona.set("end_date", endDate);
		if(cardmachine_tellerno != null && !cardmachine_tellerno.equals("")){
			cona.set("cardmachine_tellerno", cardmachine_tellerno);
		}
		
		Map<String, List<String>> map = cona.exchange();
		
		String loopNum = (String) map.get("machineNotesNum").get(0);
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
			hld.put("serial_date", map.get("serial_date").get(i) + "-" + map.get("serial_time").get(i));
			// 发卡机柜员号
			hld.put("cardmachine_tellerno", map.get("cardmachine_tellerno").get(i));
			//交易名称
			hld.put("menu_name", map.get("menu_name").get(i));
			// 卡号
			hld.put("account_num", map.get("account_num").get(i));
			// 户名
			hld.put("custinfo_name", map.get("custinfo_name").get(i));
			//金额
			hld.put("amount", map.get("amount").get(i));
			//交易状态
			hld.put("trade_status", map.get("trade_status").get(i));
			//核心流水号
			hld.put("esb_channelserno", map.get("esb_channelserno").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		// 想页面返回结果
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
}
