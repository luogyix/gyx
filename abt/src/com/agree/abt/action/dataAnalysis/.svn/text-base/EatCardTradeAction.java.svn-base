package com.agree.abt.action.dataAnalysis;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.agree.abt.service.dataAnalysis.IEatCardTradeService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;

/**
 * 吞卡交易-明细查询
 * @ClassName: EatCardTradeAction.java
 * @company 赞同科技
 * @author 王明哲
 * @date 2017-1-7
 */
public class EatCardTradeAction extends BaseAction 
{
	private static final long serialVersionUID = 1L;
	private IEatCardTradeService eatCardTradeService;
	
	public IEatCardTradeService getEatCardTradeService() 
	{
		return eatCardTradeService;
	}

	public void setEatCardTradeService(IEatCardTradeService eatCardTradeService) 
	{
		this.eatCardTradeService = eatCardTradeService;
	}
	/**
	 * 加载页面
	 * @return
	 * @throws Exception
	 */			  
	public String loadPage() throws Exception
	{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		//获取部门集合
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	/**
	 * 查询吞卡交易明细信息
	 * @return
	 * @throws Exception
	 */
	public String queryEatCardTrade() throws Exception
	{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		//得到Page分页对象	
		Page pageInfo = super.getPage(jsonObj);
		//调用接口实现类的查询方法
		List <Object> list=this.eatCardTradeService.queryEatCardInfo(jsonObj, pageInfo);
		super.setActionResult(pageInfo, list, ret);
		//返回结果
		return AJAX_SUCCESS;
	}
	/**
	 * 吞卡交易明细统计Excel报表
	 * @return
	 * @throws Exception
	 */
	public String exportEatCardTrade() throws Exception
	{
		//得到页面传递值
		String jsonString = ServletActionContext.getRequest().getParameter("querycondition_str");
		//定义json对象
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		//得到Page分页对象
		Page pageInfo = super.getPage(jsonObj);
		//查询吞卡交易明细数据
		List <Object> list=this.eatCardTradeService.queryEatCardInfo(jsonObj,pageInfo);
		//定义文件路径
		String path="EatCardSerial.xls";		
		//定义文件名称
		String file = "吞卡交易数据";
		//调用父类中的导出Excel方法
		super.doExcel(path, list, jsonObj, file);
		//返回结果
		return null;
	}
}


