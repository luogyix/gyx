package com.agree.abt.action.dataAnalysis;

import java.util.List;

import jxl.common.Logger;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.service.dataAnalysis.IPushMsgFlowService;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;

/**
 * 
 * 消息流水查询
 * @ClassName: PushMegFlowAction 
 * @company 赞同科技   
 * @author  zhaojianyong
 * @date 2015-6-12 上午11:18:26 
 *
 */
@SuppressWarnings("all")
public class PushMegFlowAction extends BaseAction {
	private Logger logger = Logger.getLogger(PushMegFlowAction.class);
	private static final long serialVersionUID = 1L;
	private IPushMsgFlowService pushMsgFlowService ;
	
	public void setPushMsgFlowService(IPushMsgFlowService pushMsgFlowService) {
		this.pushMsgFlowService = pushMsgFlowService;
	}

	/**
	 * 
	 * 加载主页
	 * @Title: loadPage 
	 * @param @return
	 * @param @throws Exception    参数     
	 * @return String    返回类型 
	 * @throws
	 */
	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());//获取部门集合
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 
	 * 查询消息流水列表
	 * @Title: queryPushMsgList 
	 * @param @return
	 * @param @throws Exception    参数     
	 * @return String    返回类型 
	 * @throws
	 */
	public String queryPushMsgList() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String jsonString = super.getJsonString();
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Page pageInfo = this.getPage(jsonObject);
		List<?> list  = this.pushMsgFlowService.queryPushMsgList(jsonObject,pageInfo);
		super.setActionResult(pageInfo, list, sRet);
		return AJAX_SUCCESS ;
		
	}
}
