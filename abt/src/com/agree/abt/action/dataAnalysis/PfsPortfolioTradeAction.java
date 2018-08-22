package com.agree.abt.action.dataAnalysis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.dataAnalysis.BtPfsPfsinfo;
import com.agree.abt.model.pfs.CustInterest;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/**
 * 填单业务量统计
 * @ClassName: PfsPortfolioTradeAction.java
 * @company 赞同科技
 * @author 王明哲
 * @date 2017-1-9上午10:24:58
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PfsPortfolioTradeAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	//定义转换格式
	DecimalFormat df = new DecimalFormat("#.##");
	//导出Excel
	private String STYLE_EXCEL = "excel";
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
	 */
	public String loadPage() throws Exception
	{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1,super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	/**
	 * 填单业务量查询
	 */
	public String queryPfsPortfolioTrade() throws Exception
	{
		HttpServletRequest req = ServletActionContext.getRequest();
		//返回用户登录信息
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//定义json对象
		String inputJsonStr = super.getJsonString();
		//得到页面传递值
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		//起始日期
		String stratdate = obj.getString("stratdate").trim().replace("-", "");
		//结束日期
		String enddate = obj.getString("enddate").trim().replace("-", "");
		//机具编号
		String devicenum = obj.getString("devicenum").trim();
		//业务名称
		String trade_name_ch = obj.getString("trade_name_ch").trim();
		Page pageInfo = this.getPage(obj);
		//查询交易接口
		cona.setBMSHeader("ibp.bms.b312.01", user);
		//上送字段
		cona.set("branch", user.getUnitid());
		cona.set("stratdate", stratdate);
		cona.set("enddate", enddate);
		cona.set("devicenum", devicenum);
		cona.set("trade_name_ch", trade_name_ch);
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
		if (pageInfo.getStart() == -1) 
		{
			pageInfo.setRowStart((pageNo - 1) * pageInfo.getLimit());
			pageInfo.setRowEnd(pageInfo.getRowStart()
					+ (pageInfo.getTotal() % pageInfo.getLimit() == 0 ? pageInfo
							.getLimit() : pageInfo.getTotal()
							% pageInfo.getLimit()));
			// 设置总页数
			pageInfo.setPage(pageNo);
			// 设置总条数
			pageInfo.setTotal(pageInfo.getTotal());
		} else{
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
			//序号
			hld.put("pfs_id", map.get("pfs_id").get(i).replace("L", ""));
			//日期
			hld.put("work_date", map.get("work_date").get(i));
			//机构号
			hld.put("branch", map.get("branch").get(i));
			//机具编号
			hld.put("devicenum", map.get("devicenum").get(i));
			//业务编号
			hld.put("trade_id", map.get("trade_id").get(i));
			//业务名称
			hld.put("trade_name_ch", map.get("trade_name_ch").get(i));
			//填单笔数
			hld.put("pfscount", map.get("pfscount").get(i));
			//平均填单时间
			String averagetime = map.get("averagetime").get(i);
			//平均时间秒数转化为分钟，保留两位小数
			double avr = Float.parseFloat(averagetime);
			double minute = avr/60;
			String averagetime_Min = df.format(minute);
			//传递至页面
			hld.put("averagetime", averagetime_Min);
			list.add(hld);
		}
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 填单业务量统计导出Excel
	 */
	public void exportPfsPortfolioTrade() throws Exception
	{
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
		
		String path="PfsPortfolioTrade.xls";		
		String file = "填单业务量统计表";
		super.doExcel(path, list, jsonObj, file);
	}
	
	public void natp( JSONObject jsonObj, List list, Page pageInfo, User user,  String selfStyle,  int totalSum) throws Exception
	{
		//调用查询交易，导出之前先做一笔查询
		cona.setBMSHeader("ibp.bms.b312.01", user);
		//上送字段
		cona.set("branch", jsonObj.getString("branch"));
		cona.set("stratdate", jsonObj.getString("stratdate").replace("-", ""));
		cona.set("enddate", jsonObj.getString("enddate").replace("-", ""));
		cona.set("devicenum", jsonObj.getString("devicenum"));
		cona.set("trade_name_ch", jsonObj.getString("trade_name_ch"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		//查询到记录总条数
		String loopNum = (String) map.get("infosize").get(0);
		int num = Integer.parseInt(loopNum);
		for(int i = 0;i < num;i++)
		{
			BtPfsPfsinfo  bps = new BtPfsPfsinfo();
			//序号
			bps.setPfs_id(map.get("pfs_id").get(i).replace("L", ""));
			//日期
			bps.setWork_date(map.get("work_date").get(i));
			//机构号
			bps.setBranch(map.get("branch").get(i));
			//机具编号
			bps.setDevicenum(map.get("devicenum").get(i));
			//业务编号
			bps.setTrade_id(map.get("trade_id").get(i));
			//业务名称
			bps.setTrade_name_ch(map.get("trade_name_ch").get(i));
			//填单笔数
			bps.setPfscount(map.get("pfscount").get(i));
			//平均填单时间
			String averagetime = map.get("averagetime").get(i);
			//平均时间秒数转化为分钟，保留两位小数
			double avr = Float.parseFloat(averagetime);
			double minute = avr/60;
			String averagetime_Min = df.format(minute);
			bps.setAveragetime(averagetime_Min);
			list.add(bps);
		}
		
	}
}
