package com.agree.abt.action.configManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

public class ErrorMessageAction extends BaseAction {
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorMessageAction.class);
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	
	public void loadMessage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b123.01", user);
		cona.set("branch", user.getUnitid()); //查询条件.待确认
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		logger.info("查询排队机错误配置");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		//{primsg=[], qidmsg=[无窗口可以办理1号排队机配置的信息变更业务所对应的【普通队列】;无窗口可以办理1号排队机配置的信息变更业务所对应的【黄金队列】;无窗口可以办理1号排队机配置的个人开户销户业务所对应的【普通队列】;], H_ret_desc=[成功], bsidmsg=[], H_ret_code=[000000], H_ret_status=[S]}
		ServiceReturn ret = new ServiceReturn(true, "");
		
		String bsidmsg = (String) map.get("bsidmsg").get(0);
		if(!"".equals(bsidmsg)){
			String[] bsidmsgs = bsidmsg.split(";");
			logger.info("错误配置业务数:" + bsidmsgs.length);
			for (int i = 0; i < bsidmsgs.length; i++) {
				Map<String, String> m = new HashMap<String, String>();
				m.put("message",bsidmsgs[i]);
				list.add(m);
			}
		}
		String qidmsg = (String) map.get("qidmsg").get(0);
		if(!"".equals(qidmsg)){
			String[] qidmsgs = qidmsg.split(";");
			logger.info("错误配置队列数:" + qidmsgs.length);
			for (int i = 0; i < qidmsgs.length; i++) {
				Map<String, String> m = new HashMap<String, String>();
				m.put("message",qidmsgs[i]);
				list.add(m);
			}
		}
//		if(!"0".equals(map.get("bssize").get(0))){//bs操作
//			String loopNum = map.get("bssize").get(0);
//			int num = Integer.parseInt(loopNum);
//			for (int i = 0; i < num; i++) {
//				Map<String, String> m = new HashMap<String, String>();
//				m.put("type", "1");
//				m.put("id", map.get("bs_id").get(i));
//				//[map.get("bs_name_ch").get(i)(编号:map.get("bs_id").get(i))]尚未在叫号规则中配置,请正确配置,否则将会导致取号后无窗口办理.
//				m.put("name", map.get("bs_name_ch").get(i) + "(编号:" + map.get("bs_id").get(i) + ")尚未在叫号规则中配置,请正确配置");
//				list.add(m);
//			}
//		}
//		if(!"0".equals(map.get("queuetypesize").get(0))){//qt操作
//			String loopNum = map.get("queuetypesize").get(0);
//			int num = Integer.parseInt(loopNum);
//			for (int i = 0; i < num; i++) {
//				Map<String, String> m = new HashMap<String, String>();
//				m.put("type", "2");
//				m.put("id", map.get("queuetype_id").get(i));
//				m.put("name", map.get("queuetype_name").get(i) + "(编号:" + map.get("queuetype_id").get(i) + ")尚未在叫号规则中配置,请正确配置");
//				list.add(m);
//			}
//		}
	
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	public ABTComunicateNatp getCona() {
		return cona;
	}
	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
}
