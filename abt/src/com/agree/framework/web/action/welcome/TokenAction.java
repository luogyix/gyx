package com.agree.framework.web.action.welcome;


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
import com.agree.util.IDictABT;



/**
 * 柜员令牌验证
 * @ClassName: TokenAction.java
 * @company 赞同科技
 * @author 赵明
 * @date 2014-4-2
 */
public class TokenAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	
	/**
	 * 柜员令牌验证
	 * @return
	 * @throws Exception
	 */
	public JSONObject checktoken() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();//获取request域
		//从session域中获取用户信息
		User user = (User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String usercode = req.getParameter("userName");
		String token = req.getParameter("userPassword");
		String branch = req.getParameter("brchno");
		 
		//初始化NATP通讯
		cona.reInit();
		cona.setBMSHeader("ibp.glb.c001.01", user);
		cona.set("usercode", usercode);
		cona.set("branch", branch);
		cona.set("token", token);
		Map<String,List<String>> map = cona.exchange();
		String H_ret_code = map.get("H_ret_code").get(0);
		String validate="";
		validate=cona.validMap(map);
		if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
			//throw new AppException(validate);
			JSONObject jsonobj = new JSONObject();
			jsonobj.accumulate("fail", validate);
			return jsonobj;
		}
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, usercode);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		return returnJson;
	}
	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
	
}
