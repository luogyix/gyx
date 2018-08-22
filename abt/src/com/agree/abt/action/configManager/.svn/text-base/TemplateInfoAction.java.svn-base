package com.agree.abt.action.configManager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.abt.service.confmanager.ITemplateInfoService;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/**
 * 参数模板配置Action类
 * @ClassName: TemplateInfoAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2014-3-25
 */
public class TemplateInfoAction extends BaseAction {
	
	private static final Logger logger = LoggerFactory.getLogger(TemplateInfoAction.class);
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;
	private ITemplateInfoService templateInfoService;
	/**
	 * 查询参数模板表内信息-用于下拉框
	 * @throws Exception 
	 */
	public void queryTemplateInfo4ComboBox() throws Exception{
		//JSONObject obj = JSONObject.fromObject(super.getJsonString());//获取页面传值
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String branch = request.getParameter("branch");
		ServiceReturn ret = templateInfoService.queryTemplateInfo(branch);
		//List list = (List)ret.get("field1");
		//设置常规模式
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("template_id", "normal");
//		map.put("template_name", "常规配置模式");
//		map.put("branch", branch);
//		list.add(0, map);
		//这里判断是否有session?如果有,清空session内的模板
		HttpSession session = request.getSession();
		String templateSession = (String)session.getAttribute("template");
		if(templateSession!=null){
			session.removeAttribute("template");
		}
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	/**
	 * 控制开关模板模式
	 * @throws Exception 
	 */
	public void configTemplateModel() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String template_id = request.getParameter("template_id");
		String template_flag = request.getParameter("template_flag");
		String template_name = request.getParameter("template_name");
		
		//normal代表没开启模板模式,其他状态都是模板模式
		if ("normal".equals(template_id)) {
			//普通模式下,删除session的数据,启用正常配置状态
			String templateSession = (String)session.getAttribute("template");
			if(templateSession!=null){
				session.removeAttribute("template");
				session.removeAttribute("template_flag");
			}
			response.getWriter().print("切换正常配置模式");
		} else {
			//模板模式下,记录session的数据,启用模板配置状态
			session.setAttribute("template",template_id);
			String[] flags = template_flag.split(",");
			session.setAttribute("template_flag",flags);
			//String templateFlag = (String)session.getAttribute("template_flag");
			if(template_name.contains("<script>"))
			{
				response.getWriter().print("版本名字错误：包含非法内容！");
			}
			else
			{
				response.getWriter().print("切换<<" + template_name + ">>模板配置模式");
			}
			//response.getWriter().print("已开启模板配置模式:" + template_id);
		}
	}
	/**
	 * 查询参数模板表内信息列表
	 * @return
	 * @throws Exception 
	 */
	public String queryTemplateInfo() throws Exception{
		//JSONObject obj = JSONObject.fromObject(super.getJsonString());//获取页面传值
		//String branch = obj.getString("branch");
		return AJAX_SUCCESS;
	}
	/**
	 * 添加模板
	 * @return
	 * @throws Exception 
	 */
	public String addTemplateInfo() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String strJson = request.getParameter("strJson");
		ServiceReturn ret = templateInfoService.addTemplateInfo(strJson);
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 应用所选模板 
	 * @return
	 * @throws Exception 
	 */
	public String selTemplateById() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b206.01", user);
		cona.set("template_id", obj.getString("template_id"));
		if(obj.containsKey("chkflag")){
			cona.set("chkflag", obj.getString("chkflag"));
		}
		
		cona.set("branch", user.getUnitid());
		Map<String,List<String>> map = cona.exchange();
		logger.info("selTemplateById map:" + map);
		
		//{H_ret_desc=[无法应用模版，['\xc9\xee\xdb\xda\xb7\xd6\xd0\xd0\xd3\xaa\xd2\xb5\xb2\xbf', '\xc9\xee\xdb\xda\xb8\xa3\xcc\xef\xd6\xa7\xd0\xd0', '\xc9\xee\xdb\xda\xb8\xa3\xcc\xef\xd6\xa7\xd0\xd0']机构中的['1', '2', '1']号排队机，应用了本级机构排队机界面['755888\xbb\xf9\xb4\xa1\xbd\xe7\xc3\xe601', '755888\xbb\xf9\xb4\xa1\xbd\xe7\xc3\xe602', '755888\xbb\xf9\xb4\xa1\xbd\xe7\xc3\xe601']的参数配置], H_ret_code=[ABM019], H_ret_status=[F]}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		if("ABM019".equals((map.get("H_ret_code")).get(0))){
			//((ArrayList)map.get("H_ret_status")).add(0, "S");
			ret.setSuccess(true);
			ret.setOperaterResult(false);
			//((ArrayList)map.get("H_ret_desc")).get(0)
			ret.setErrmsg((String) map.get("H_ret_desc").get(0));
		}else {
			ret.setSuccess(true);
			ret.setOperaterResult(true);
		}
		
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 通过模板ID删除所选模板 
	 * @return
	 * @throws Exception 
	 */
	public String delTemplateById() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		String template_id = obj.getString("template_id");
		 
		
		cona.setBMSHeader("ibp.bms.b206_1.01", user);
		cona.set("template_id", template_id);
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	public ABTComunicateNatp getCona() {
		return cona;
	}
	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}

	public ITemplateInfoService getTemplateInfoService() {
		return templateInfoService;
	}

	public void setTemplateInfoService(ITemplateInfoService templateInfoService) {
		this.templateInfoService = templateInfoService;
	}
}
