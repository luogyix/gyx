package com.agree.abt.service.dataAnalysis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import com.agree.abt.service.dataAnalysis.IEatCardTradeService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.web.service.base.BaseService;
/**
 * 吞卡交易明细统计接口实现类
 * @class  EatCardTradeServiceImpl.java
 * @author Administrator
 * @data   2017-1-7
 */
public class EatCardTradeServiceImpl extends BaseService implements IEatCardTradeService 
{
	/**
	 * 吞卡交易明细分页查询
	 */
	@Override
	public List<Object> queryEatCardInfo(JSONObject jsonObj, Page pageInfo) throws Exception 
	{
		//页面传递的当前机构号
		String branch=jsonObj.getString("branch");
		//页面传递的开始时间
		String startdate=jsonObj.getString("startdate").replace("-", "");
		//页面传递的结束时间
		String enddate=jsonObj.getString("enddate").replace("-", "");
		HashMap<String,String> paramaHashMap = new HashMap<String,String>();
		List<Object> list=new ArrayList<Object>();
		//执行hql
		String hql="select new map(t.branch as branch,t.branch_name as branch_name,t.cust_name as cust_name,t.teller_name as teller_name,"
					+" t.date as date,t.time as time,t.cardnumber as cardnumber,t.reason as reason,t.return_code as return_code,t.address as address)"
					+"from BtTsfEatCardSerial t where t.branch=:branch and t.date>=:startdate and t.date<=:enddate";
		//机构号
		paramaHashMap.put("branch", branch);
		//开始日期
		paramaHashMap.put("startdate", startdate);
		//结束日期
		paramaHashMap.put("enddate", enddate);
		//拼接hql语句
		hql+=" order by date ,time desc";
		/**
		 * 调用分页查询方法，执行查询操作
		 */
		list = sqlDao_h.queryPage(hql, paramaHashMap, pageInfo, false);
		return list;
	}
}
