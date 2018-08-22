package com.agree.framework.web.action.administration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.TBsmsSysNotice;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.administration.ISysNoticeService;

/**
 * 
 * @ClassName: SysNoticeAction 
 * @Description: 系统通知信息操作
 * @company agree   
 * @author haoruibing   
 * @date 2012-10-31 下午02:32:33 
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class SysNoticeAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private ISysNoticeService sysNoticeService;
	
	
	public void setSysNoticeService(ISysNoticeService sysNoticeService) {
		this.sysNoticeService = sysNoticeService;
	}

	/** 
	 * @Title: noticePage 
	 * @Description: 跳转到修改通知页面
	 * @return
	 * @throws Exception     
	 */ 
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sRet.put(ServiceReturn.FIELD2, super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	public String query() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Page pageInfo = this.getPage(jsonObj); 
		String myunitid=super.getLogonUser(false).getUnitid().toString();
		jsonObj.put("myunitid", myunitid);
		List list = sysNoticeService.queryNotice(jsonObj,pageInfo);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/** 
	 * @Title: updateNotice 
	 * @Description: 修改通知信息
	 * @return
	 * @throws Exception     
	 */ 
	public String updateNotice() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		TBsmsSysNotice notice = (TBsmsSysNotice)JSONObject.toBean(obj, TBsmsSysNotice.class);
		User logonUser = super.getLogonUser(false);
		notice.setModifyuser(logonUser.getUsername());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String modifytime=df.format(new Date());
		notice.setModifytime(modifytime);
		sysNoticeService.updateNotice(notice);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	/** 
	 * @Title: addNotice 
	 * @Description: 新增通知信息
	 * @return
	 * @throws Exception     
	 */ 
	public String addNotice() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		TBsmsSysNotice notice = (TBsmsSysNotice)JSONObject.toBean(obj, TBsmsSysNotice.class);
		User logonUser = super.getLogonUser(false);
		notice.setModifyuser(logonUser.getUsername());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String modifytime=df.format(new Date());
		notice.setModifytime(modifytime);
		sysNoticeService.addNotice(notice);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/** 
	 * @Title: delNotice 
	 * @Description: 删除通知
	 * @return
	 * @throws Exception     
	 */ 
	public String delNotice() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(TBsmsSysNotice.class);
		List<TBsmsSysNotice> notices =  (List<TBsmsSysNotice>) JSONArray.toCollection(jsonArray,config);
		sysNoticeService.delNotice(notices);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
}
