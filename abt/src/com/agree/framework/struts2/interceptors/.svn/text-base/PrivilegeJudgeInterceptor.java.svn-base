package com.agree.framework.struts2.interceptors;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.exception.AppErrorcode;
import com.agree.framework.exception.AppException;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.Module;
import com.agree.framework.web.form.administration.User;
import com.agree.util.StringUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;



/**
 * 
 * @ClassName: PrivilegeJudgeInterceptor
 * @Description: 权限验证拦截器
 * @company agree
 * @author haoruibing
 * @date 2011-7-29 下午03:20:09
 * 
 */
public class PrivilegeJudgeInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	/**
	 * @Description:权限过滤器，验证用户是否有该动作权限
	 * @param ActionInvocation
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String intercept(ActionInvocation arg0) throws Exception {
		// get request servlet path and servletcontext
		ServletContext context = ServletActionContext.getServletContext();
		String servletPath = ServletActionContext.getRequest().getRequestURL().toString();
		
		boolean hasPrivilege = false;

		// secondly, judge user privilege
		User logonUser = (User) ServletActionContext.getRequest().getSession()
				.getAttribute(ApplicationConstants.LOGONUSER);

		@SuppressWarnings("unchecked")
		List<Module> mouduleList = (List<Module>) context
				.getAttribute("mouduleList");

		Module module1 = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		String requesttype = request
				.getParameter(ApplicationConstants.AJAXPARAMETER);
		ValueStack stack = ServletActionContext.getValueStack(request);
		String queryString = request.getQueryString();
		if(queryString!=null){
			queryString=queryString.replace("&requesttype=ajax", "");
			queryString = queryString.replace("requesttype=ajax", "");
			//queryString=queryString.replace("requesttype=ajax&", "");
			
			if(queryString.equals("?")){
				queryString="";
			}else if(StringUtil.isNotEmpty(queryString)){
				servletPath+="?"+queryString;
			}
		}
		servletPath=servletPath.trim();
		
		
		if (servletPath.indexOf("/logon_logon")>=0 
				|| servletPath.indexOf("/logon_tellerLogon")>=0 
				|| servletPath.indexOf("/F5Check_check")>=0 
				//|| servletPath.indexOf("/logon_redirect")>=0 
				|| servletPath.indexOf("/logon_checkLogon")>=0 
				|| servletPath.indexOf("/logon_pdlogin")>=0 
				|| servletPath.indexOf("/logon_token")>=0 
				|| servletPath.indexOf("/logon_mobileLogin")>=0 
				|| servletPath.indexOf("/network_orderTimeQuery")>=0 
				|| servletPath.indexOf("/network_orderQuery")>=0 
				|| servletPath.indexOf("/network_orderTask")>=0 
				|| servletPath.indexOf("/network_cancelOrder")>=0 
				|| servletPath.indexOf("/mobInit_receiveJson") >=0 
				//|| servletPath.indexOf("/mobServe_receiveJson")>=0 
				//|| servletPath.indexOf("/qmInit_init")>=0 
				//|| servletPath.indexOf("/qmServer_receiveJson")>=0 
				|| servletPath.indexOf("/update_package_getNewRecordPage")>=0 
				|| servletPath.indexOf("/update_package_download")>=0  
			) {
			// 如果是登陆请求的话不验证,默认用户都是有登陆权限的
		} else {
			
			if (logonUser == null) {
				if(servletPath.indexOf("/qmServer_receiveJson2")>=0||servletPath.indexOf("/mobServe_receiveJson")>=0){
					ServiceReturn sRet = new ServiceReturn(
							ServiceReturn.FAILURE, "logonUserNull");
					JSONObject jsonObj = BaseAction
							.convertServiceReturnToJson(sRet);
					stack.setValue(ApplicationConstants.ACTIONRESULT,
							jsonObj.toString());
					return BaseAction.JSONP_SUCCESS;
				}
				if (requesttype == null) {// 非法提交
					return "nologin";
				} else {
					if (requesttype
							.equals(ApplicationConstants.AJAXPARAMETERVALUE)) {// Ajax提交
						ServiceReturn sRet = new ServiceReturn(
								ServiceReturn.FAILURE, "您已经超时或没有登录,页面将跳到登录界面");
						JSONObject jsonObj = BaseAction
								.convertServiceReturnToJson(sRet);
						stack.setValue(ApplicationConstants.ACTIONRESULT,
								jsonObj.toString());
						return BaseAction.AJAX_SUCCESS;
					} else {// 非Ajax提交
						return "nologin";
					}
				}
			} else {
				boolean isPrivilege = false;
				for (Module module : mouduleList) {// 判断该动作是否是数据库中记录的功能
					String moduleaction = module.getModuleaction();
					if (moduleaction != null) {
						String action = moduleaction==null?"":moduleaction.trim();
						if (!action.equals("")&&servletPath.trim().endsWith(action)) {
							isPrivilege = true;
							break;
						}
					}
				}
				if (!isPrivilege) {// 如果没有的话直接返回，不再判断用户权限
					return arg0.invoke();
				}
				Set<Module> userPrivileges = logonUser
						.getCatalog_and_privileges();
				for (Module module : userPrivileges) {
					String moduleaction = module.getModuleaction();
					if (moduleaction != null) {
						String action = moduleaction==null?"":moduleaction.trim();
						if (!action.equals("")&&servletPath.trim().endsWith(action)) {
							Short logFlag=module.getLogflag();
							request.setAttribute("logflag", logFlag);
							request.setAttribute("modulename", module.getModulename());
							hasPrivilege = true;
							module1 = module;
							break;
						}
					}
				}
				if (!hasPrivilege) {
					throw new AppException(AppErrorcode.NOPRIVILEGE);
				}
			}
			String authored = request.getParameter("authored");// 是否已经授权？true已授权
			if (module1 != null && "1".equals(module1.getAuthflag())
					&& authored == null) {
				ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
				ret.put(ServiceReturn.SERVICENAME, request.getContextPath());
				ret.put(ServiceReturn.AUTHOR, "true");// 原请求参数
				
				Map parameters=request.getParameterMap();
				Set keys=parameters.keySet();
				Iterator it=keys.iterator();
				String url = request.getRequestURL().toString()+"?authored=true";
				while(it.hasNext()){
					Object key = it.next();
					Object[] value=(Object[]) parameters.get(key);
					if(value.length==1){
						url+="&"+key+"="+value[0];
					}else if(value.length==0){
						url+="&"+key+"=";
					}else{//如果一个key对应多个值的话
						throw new AppException("参数"+key+"不能包含多个值，请检查请求的url！");
					}
				}
				
				ret.put(ServiceReturn.FIELD1, url);// 原请求路径
				ret.put(ServiceReturn.FIELD2, BaseAction.getJsonString());// 原请求参数
				JSONObject jsonObj = BaseAction.convertServiceReturnToJson(ret);
				stack.setValue(ApplicationConstants.ACTIONRESULT, jsonObj
						.toString());
				return BaseAction.AJAX_SUCCESS;
			}
		}
		return arg0.invoke();

	}

}
