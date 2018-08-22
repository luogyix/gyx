package com.agree.abt.action.configManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/**
 * 法人配置Actioni
 * <p>配置发卡机和填单机的短信通知费用，和发卡机授权等级</>
 * @author 高艺祥
 *
 */
public class CorporationParamAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6858304102313084181L;
	
	private ABTComunicateNatp cona;


	public ABTComunicateNatp getCona() {
		return cona;
	}


	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}

	/**
	 * 加载法人配置界面
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		//sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());//获取部门集合
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		
		return SUCCESS;
	}
	
	public void loadConf() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		cona.setBMSHeader("ibp.bms.b218_2.01", user);
		cona.set("branch", user.getUnitid());
		//授权等级
		cona.set("param_key1","accreditrank");
		//发卡机短信通知费用
		cona.set("param_key2", "messageOutlay-card");
		//填单机短信通知费用
		cona.set("param_key3", "messageOutlay-pfs");
		Map<String, List<String>> map = cona.exchange();
		String DataSizeS = map.get("DataSize").get(0);
		Integer DataSize = Integer.valueOf(DataSizeS);
		
		ArrayList<Map<String,String>> list = new ArrayList<Map<String, String>>();
		for(int i = 0;i <DataSize ;i++){
			Map<String, String> hld = new HashMap<String, String>();
			hld.put("param_key", map.get("param_key").get(i));
			hld.put("param_value", map.get("param_value").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	@SuppressWarnings("rawtypes")
	public String saveParam() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String jsonString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(jsonString);
		Iterator keys = obj.keys();
		while(keys.hasNext()){
			String key = keys.next().toString();
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b218_1.01", user);
			//机构号
			cona.set("branch", user.getUnitid());
			//参数key
			cona.set("param_key", key);
			//参数value
			cona.set("param_value", obj.getString(key));
			//最后操作时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-hhmmss");
			String editTime = sdf.format(new Date());
			cona.set("creat_time", editTime);
			cona.exchange();
		}
		
		ServiceReturn tet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject resultJson = super.convertServiceReturnToJson(tet);
		super.setActionresult(resultJson.toString());
		
		return AJAX_SUCCESS;
	}
}
