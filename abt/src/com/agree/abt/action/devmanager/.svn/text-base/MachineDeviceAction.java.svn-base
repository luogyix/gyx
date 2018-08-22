package com.agree.abt.action.devmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;


public class MachineDeviceAction extends BaseAction {
	
	private ABTComunicateNatp cona;
	private static final long serialVersionUID = 1L;
	
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		//Set<String> priv=this.getUserInpageFunc();
		//sRet.put(ServiceReturn.FIELD2,priv);//获取登录用户信息 也就是利用这个获取到此部分功能
		sRet.put(ServiceReturn.FIELD2,super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: queryQue
	 * @Description: 查询机具外设状态
	 * @return String
	 * @throws Exception
	 */
	public String queryMachineDevice() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo = new Page();
		cona.reInit();
		cona.setBMSHeader("ibp.bms.b405_1.01", user);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		cona.set("branch", obj.getString("branch"));
		cona.set("machine_type", obj.getString("machine_type"));
		Map<String, List<String>> map = cona.exchange();
		if (map.get("H_ret_status") != null) {
			List<String> tt = map.get("H_ret_status");
			if (null != tt && tt.size() > 0) {
				if (((String) tt.get(0)).equalsIgnoreCase("F")) {
					tt = map.get("H_ret_code");
					String errcode = (String) tt.get(0);
					tt = map.get("H_ret_desc");
					String errreason = (String) tt.get(0);
					throw new AppException("错误代码:" + errcode + ":" + errreason);
				}
			}
		}
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String loopNum = map.get("devicetypesize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, String> hld = new HashMap<String, String>();
			hld.put("branch",map.get("branch").get(i));
			hld.put("branch_name",map.get("branch_name").get(i));
			hld.put("machine_type",map.get("machine_type").get(i));
			hld.put("oid",map.get("oid").get(i));
			hld.put("macaddress",map.get("macaddress").get(i));
			hld.put("machine_num",map.get("machine_num").get(i));
			hld.put("machine_code",map.get("machine_code").get(i));
			hld.put("maching_firm",map.get("maching_firm").get(i));
			hld.put("idcard_status",map.get("idcard_status").get(i));
			hld.put("cardsend_status",map.get("cardsend_status").get(i));
			hld.put("cardread_status",map.get("cardread_status").get(i));
			hld.put("iccr_status",map.get("iccr_status").get(i));
			hld.put("idcr_status",map.get("idcr_status").get(i));
			hld.put("ticket_status",map.get("ticket_status").get(i));
			hld.put("laser_status",map.get("laser_status").get(i));
			hld.put("carmar_status",map.get("carmar_status").get(i));
			hld.put("pwdkeybord_status",map.get("pwdkeybord_status").get(i));
			hld.put("fingerprt_status",map.get("fingerprt_status").get(i));
			hld.put("ukey_status",map.get("ukey_status").get(i));
			hld.put("siu_status",map.get("siu_status").get(i));
			hld.put("beer_status",map.get("beer_status").get(i));
			hld.put("comp_status",map.get("comp_status").get(i));
			hld.put("voice_status",map.get("voice_status").get(i));
			hld.put("appr_status",map.get("appr_status").get(i));
			list.add(hld);

		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}

}
