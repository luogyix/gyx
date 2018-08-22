package com.agree.framework.struts2.interceptors;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.exception.AppException;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
/**
 * 
 * @ClassName: ExceptionHandlerInterceptor 
 * @Description: 异常过滤器，用于将action抛出的异常信息显示在页面上
 * @company agree   
 * @author haoruibing   
 * @date 2011-7-29 下午03:16:52 
 *
 */
public class ExceptionHandlerInterceptor extends AbstractInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerInterceptor.class);

	private static final long serialVersionUID = 1L;
	
	private static final String APPEXCEPTION = "APPEXCEPTION";
	/**
	 * @Description:过滤器方法,过滤异常
	 * @param arg0
	 * @return String
	 * @throws Exception
	 */
	@Override
	public String intercept(ActionInvocation arg0)  {
		String result = null;
		try {
			result = arg0.invoke();
		} catch (Exception e){
			logger.error(e.getMessage(),e);
			result = processException(arg0, e);
		}
		return result;
	}

	/**
	 * 
	 * @Title: processException 
	 * @Description: 异常处理方法
	 * @param @param arg0
	 * @param @param e
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	private String processException(ActionInvocation arg0, Exception e) {
		String property_key = "exception.default";
		if(AppException.class.isInstance(e)){
			property_key = e.getMessage();
		}else{
			property_key = e.getClass().getName();
		}
		logger.error("异常信息：", e);
		ActionSupport actionSupport = (ActionSupport)arg0.getAction();
		String error_msg = actionSupport.getText(property_key);//异常信息
		
		if(e.getCause()!=null){
			Object err=e.getCause().getCause();
			String msg=err==null?"":e.getCause().getCause().getMessage();
			error_msg=msg.equals("")?e.getCause().toString():msg;
			
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		ValueStack stack = ServletActionContext.getValueStack(request);
		String requesttype = request.getParameter(ApplicationConstants.AJAXPARAMETER);//得到请求类型
		if(requesttype == null){//非Ajax提交
			stack.setValue(ApplicationConstants.ACTIONRESULT, error_msg);
			stack.setValue(ApplicationConstants.EXCEPTION_MSG, error_msg);
			return APPEXCEPTION;
		}else{//Ajax或非Ajax提交
			if(requesttype.equals(ApplicationConstants.AJAXPARAMETERVALUE)){//Ajax提交
				ServiceReturn sRet = new ServiceReturn(ServiceReturn.FAILURE, error_msg);
				JSONObject jsonObj;
				try {
					jsonObj = BaseAction.convertServiceReturnToJson(sRet);
					stack.setValue(ApplicationConstants.ACTIONRESULT, jsonObj.toString());
				} catch (Exception ex) {
					// TODO: handle exception
					logger.error("转换异常信息失败",ex);
				}
				return BaseAction.AJAX_SUCCESS;
			}else{//非Ajax提交
				stack.setValue(ApplicationConstants.ACTIONRESULT, error_msg);
				stack.setValue(ApplicationConstants.EXCEPTION_MSG, error_msg);
				return APPEXCEPTION;
			}
		}
	}
}
