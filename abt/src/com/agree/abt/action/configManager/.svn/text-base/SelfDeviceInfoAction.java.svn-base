package com.agree.abt.action.configManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.confManager.BtDevDeviceInfo;
import com.agree.abt.model.confManager.DevParamData;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;
/**
 * 自助设备信息
 * @author linyuedong
 *
 */
@SuppressWarnings("unused")
public class SelfDeviceInfoAction extends BaseAction {
	
	private static final long serialVersionUID = 7150247048713737606L;
	private ABTComunicateNatp cona;
	
	public ABTComunicateNatp getCona() {
		return cona;
	}
    
	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
	/**
	 * 加载主页面
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	/**
	 * 查询自助设备信息
	 * @return
	 * @throws Exception
	 */
	public void queryDeviceInfo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo = new Page();
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b131_2.01", user);
		cona.set(IDictABT.BRANCH, req.getParameter("branch"));
		cona.set(IDictABT.DEVICETYPE,req.getParameter("devicetype"));
		//分页专用
		cona.set("pageflag","0");//分页标识
		
		cona.set("currentpage","1");//当前页码
		cona.set("count","50");//每页记录数
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("devicesize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String,Object> hld = new HashMap<String,Object>();
			Set<String> keyset  = map.keySet();
			Iterator<String> it = keyset.iterator();
			while(it.hasNext()){
				String key = it.next();
				List<String> tmep = (List<String>) map.get(key);
				if(tmep.size()<num){
					continue;
				}else{
				 hld.put(key,tmep.get(i));
				}
			}
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	/**
	 * 查询自助设备信息
	 * @return
	 * @throws Exception
	 */
	public String queryDeviceInfoConfPage() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		Page pageInfo = this.getPage(obj);
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.dev.d003.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("deviceadm_num", obj.getString("deviceadm_num"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		String loopNum = "";
		if(map.containsKey("devnum")){
			loopNum = (String) map.get("devnum").get(0);
		}else{
			throw new AppException("查询数据出错,传输失败");
		}
		pageInfo.setTotal(Integer.parseInt(loopNum));
		int num = Integer.parseInt(loopNum);
		pageInfo.setTotal(num);//总条数		
		Integer pageNo = (pageInfo.getTotal() % pageInfo.getLimit() == 0) ? pageInfo.getTotal() / pageInfo.getLimit() : pageInfo.getTotal()
				/ pageInfo.getLimit() + 1;// 得到总页数
		if (pageInfo.getStart() == -1) {// 查询最后一页
			pageInfo.setRowStart((pageNo - 1) * pageInfo.getLimit());
			pageInfo.setRowEnd(pageInfo.getRowStart()
					+ (pageInfo.getTotal() % pageInfo.getLimit() == 0 ? pageInfo.getLimit() : pageInfo.getTotal() % pageInfo.getLimit()));
			pageInfo.setPage(pageNo);
			pageInfo.setTotal(pageInfo.getTotal());
		} else {
			pageInfo.setRowStart((pageInfo.getStart() - 1) * pageInfo.getLimit() );
			pageInfo.setRowEnd( (pageInfo.getRowStart() + pageInfo.getLimit()) <= pageInfo.getTotal() ? (pageInfo.getRowStart() + pageInfo.getLimit())
					: pageInfo.getTotal() );
			pageInfo.setPage(pageNo);
			pageInfo.setTotal(pageInfo.getTotal());
		}

		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for (int i = pageInfo.getRowStart(); i < pageInfo.getRowEnd(); i++) {
			Set<String> keyset  = map.keySet();
			Iterator<String> it = keyset.iterator();
			Map<String, String> hld = new HashMap<String, String>();
			while(it.hasNext()){
				String key = it.next();
				if("dev_param_data".equals(key)){
					JSONObject devobj = JSONObject.fromObject(map.get(key).get(i));
					DevParamData DevParamData = (DevParamData) JSONObject.toBean(devobj, DevParamData.class);
					hld.put("laying_mode", DevParamData.getLaying_mode());
					hld.put("buydate", DevParamData.getBuydate());
					hld.put("installdate", DevParamData.getInstalldate());
					hld.put("device_address", DevParamData.getDevice_address());
					hld.put("device_assessor", DevParamData.getDevice_assessor());
					hld.put("card_admin", DevParamData.getCard_admin());
				}else{
					List<String> tmep=(List<String>) map.get(key);
					if (tmep.size() < pageInfo.getRowEnd()-pageInfo.getRowStart()) {
						continue;
					}else
					hld.put(key, tmep.get(i));
				}
			} 
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
     	super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
		
	}
	/**
	 * 修改自助设备
	 * @return
	 * @throws Exception
	 */
	public String editDeviceInfoConf() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		DevParamData DevParamData = (DevParamData) JSONObject.toBean(obj, DevParamData.class);
		JSONObject devobj = JSONObject.fromObject(DevParamData);
		BtDevDeviceInfo deviceinfo = (BtDevDeviceInfo) JSONObject.toBean(obj, BtDevDeviceInfo.class);
		 
		
		cona.setBMSHeader("ibp.dev.d002.01", user);
		Field[] fields=deviceinfo.getClass().getDeclaredFields();
		for(Field field : fields){
			String key = field.getName();
			if("device_oid".equals(key)||"qm_num".equals(key)||"qm_ip".equals(key)){
				continue;
			}
			if(obj.getString(key).equalsIgnoreCase("on")){
				String a=obj.getString(key).replaceAll("on","1");
				cona.set(key, a);
			}else if(obj.getString(key).equalsIgnoreCase("off")){
				String a=obj.getString(key).replaceAll("off","0");
				cona.set(key, a);
			}else{
				cona.set(key, obj.getString(key));
			}
		}
		cona.set("dev_param_data",devobj.toString());
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
		
	}
	/**
	 * 添加自助设备
	 * @return
	 * @throws Exception
	 */
	public String addDeviceInfoConf() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		DevParamData DevParamData = (DevParamData) JSONObject.toBean(obj, DevParamData.class);
		JSONObject devobj = JSONObject.fromObject(DevParamData);
		BtDevDeviceInfo deviceinfo = (BtDevDeviceInfo) JSONObject.toBean(obj, BtDevDeviceInfo.class);
		 
		
		cona.setBMSHeader("ibp.dev.d001.01", user);
		Field[] fields=deviceinfo.getClass().getDeclaredFields();
		for(Field field : fields){
			String key = field.getName();
			if("device_oid".equals(key)||"qm_num".equals(key)||"qm_ip".equals(key)){
				continue;
			}
			if(obj.getString(key).equalsIgnoreCase("on")){
				String a=obj.getString(key).replaceAll("on","1");
				cona.set(key, a);
			}else if(obj.getString(key).equalsIgnoreCase("off")){
				String a=obj.getString(key).replaceAll("off","0");
				cona.set(key, a);
			}else{
				cona.set(key, obj.getString(key));
			}
		}
		String dev_param_data = devobj.toString();
		cona.set("dev_param_data",dev_param_data);
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 自助设备迁移
	 * @return
	 * @throws Exception
	 */
	public String moveDeviceInfoConf() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		DevParamData DevParamData = (DevParamData) JSONObject.toBean(obj, DevParamData.class);
		JSONObject devobj = JSONObject.fromObject(DevParamData);
		 
		
		cona.setBMSHeader("ibp.dev.d004.01", user);
		cona.set("device_num",obj.getString("device_num"));
		cona.set("device_ip",obj.getString("device_ip_new"));
		cona.set("deviceadm_num",obj.getString("deviceadm_num_new"));
		cona.set("branch",obj.getString("branch"));
		cona.set("branch_new",obj.getString("branch_new"));
		cona.set("devicetype",obj.getString("devicetype"));
		cona.set("dev_param_data",devobj.toString());
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	/**
	 *自助设备初始化 、停用与启用、报废
	 * @return
	 * @throws Exception
	 */
	public String changeDeviceStatus() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		DevParamData DevParamData = (DevParamData) JSONObject.toBean(obj, DevParamData.class);
		JSONObject devobj = JSONObject.fromObject(DevParamData);
		 
		cona.set("branch",obj.getString("branch"));
		
		cona.setBMSHeader("ibp.dev.d005.01", user);
		cona.set("device_num",obj.getString("device_num"));
		cona.set("devicetype",obj.getString("devicetype"));
		cona.set("device_status",obj.getString("device_status"));
		//判断afa的返回结果,是否成功
		cona.exchange();
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
}   
    