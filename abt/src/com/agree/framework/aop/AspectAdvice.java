/**   
 * @Title: AspectAdvice.java 
 * @Package com.agree.framework.aop 
 * @Description: TODO 
 * @company agree   
 * @author haoruibing   
 * @date 2011-8-19 下午02:45:50 
 * @version V1.0   
 */ 

package com.agree.framework.aop;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.exception.AppException;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.BsmsMnguseroprlog;
import com.agree.framework.web.form.administration.Module;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.administration.ILogService;
import com.agree.util.StringUtil;
import com.opensymphony.xwork2.util.ValueStack;

/** 
 * @ClassName: AspectAdvice 
 * @Description: 统一处理切面类
 * @company 赞同科技   
 * @author haoruibing   
 * @date 2011-8-19 下午02:45:50 
 *  
 */

public class AspectAdvice {
	private static final Logger logger = LoggerFactory.getLogger(AspectAdvice.class);
	private ILogService logService;
	private static final SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
	
	/** 
	 * @Title: afterMethod 
	 * @Description: 记录方法后日志
	 * @param @throws Exception    参数 
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void afterMethod(JoinPoint joinpoint) throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String actionresult=(String) request.getAttribute("result");
		JSONObject jsonObj = JSONObject.fromObject(actionresult);
		Boolean returnresult=true;
		
		if(actionresult!=null&&jsonObj.get("operaterResult")!=null&&jsonObj.get("operaterResult") instanceof Boolean){
			returnresult=(Boolean)jsonObj.get("operaterResult");
		}
		String actiondetail=(String) request.getAttribute("actiondetail");
		HttpSession session = request.getSession();
		if(session==null){
			throw new AppException("会话已超时，请重新登录");
		}
		User logonUser = (User)session.getAttribute(ApplicationConstants.LOGONUSER);
		
		String servletPath = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		if(queryString!=null){
			
			queryString=queryString.replace("&requesttype=ajax", "");
			queryString = queryString.replace("requesttype=ajax", "");
			//queryString=queryString.replace("requesttype=ajax&", "");
			
			if(queryString.trim().equals("?")){
				queryString="";
			}else if(StringUtil.isNotEmpty(queryString)){
				servletPath+="?"+queryString;
			}
		}
		servletPath=servletPath.trim();
		int flag = 0;
		String modulename = "";
		if(logonUser != null){
			Object logflag = request.getAttribute("logFlag");
			if (logflag != null && !logflag.equals("")) {// 如果前面拦截器已经判断了记录日志标志则直接使用
				flag = (Short) logflag;
				modulename = (String) request.getAttribute("modulename");
			} else {
				Set<Module> modules = logonUser.getCatalog_and_privileges();
				for (Module module : modules) {// 查看该用户是否有该动作权限以及是否记录该日志
					String action = module.getModuleaction()==null?"null":module.getModuleaction().trim();
					if (!action.equals("")&&servletPath.trim().endsWith(action)&&servletPath.contains("_"+joinpoint.getSignature().getName())
							&& module.getLogflag().equals((short) 1)) {
						modulename = module.getModulename();
						flag++;
						break;
					}
				}
			}
			if(flag > 0){//如果记录该日志......
				BsmsMnguseroprlog useroprlog = new BsmsMnguseroprlog();
				String now = df.format(Calendar.getInstance().getTime());
				String result="成功";
				useroprlog.setUnitid(logonUser.getUnit().getUnitid());
				useroprlog.setUnitname(logonUser.getUnit().getUnitname().replace("珠海华润银行", "").replace("股份有限公司", ""));
				useroprlog.setLogdate(now.substring(0, 8));
				useroprlog.setLogtime(now.substring(8, 14));
				useroprlog.setUsercode(logonUser.getUsercode());
				useroprlog.setUsername(logonUser.getUsername());
				useroprlog.setOperation(modulename);
				String ip = ServletActionContext.getRequest().getRemoteAddr() + ":"
						+ Integer.toString(ServletActionContext.getRequest().getRemotePort());
				useroprlog.setHostip(ip);
				String requesttype = request.getParameter("requesttype");
				ValueStack stack = ServletActionContext.getValueStack(request);
				if(returnresult.equals(ServiceReturn.FAILURE)
						||(StringUtil.isStringEmpty(actionresult)&&ApplicationConstants.AJAXPARAMETERVALUE.equals(requesttype))
						||StringUtil.isNotEmpty(stack.findString(ApplicationConstants.EXCEPTION_MSG))){
					//如果返回结果为空则不用记录日志,
					//1.如果返回的result为false，则操作失败
					//2.如果请求方式为ajax，并且返回结果为空则失败
					//3.//3.如果请求方式为正常同步请求，则不用判断actionresult，还需要判断是否有异常信息抛出，如果没有则成功
					result="失败";
				}
				if(StringUtil.isNotEmpty(actiondetail)){
					if(actiondetail.getBytes("GB2312").length>1024){
						actiondetail=StringUtil.cutMultibyte(actiondetail, 1024);//如果返回信息长度大于100，则只截取前100字节
					}
						useroprlog.setOperdetail(actiondetail);
				}
				useroprlog.setResult(result);
				logService.insertUserOprLog_itransc(useroprlog);
			}
		}else{
			throw new AppException("会话已失效，请重新登录");
		}
		}
	/** 
	 * @Title: beforeMethod 
	 * @Description: 切面处理-方法前调用,用于保存页面传递参数，来给方法后切面方法记录日志
	 * @throws Exception    
	 */ 
	public void beforeMethod(JoinPoint joinpoint) throws Exception{
		String jsonString = BaseAction.getJsonStringBeforMethod();
		HttpServletRequest request = ServletActionContext.getRequest();
		String modulename = (String) request.getAttribute("modulename");
		logger.info(modulename==null?"":modulename+"，调用方法："+joinpoint.getSignature().toShortString()+"，页面参数："+jsonString);
		if(!jsonString.equals("")){
			ServletActionContext.getRequest().setAttribute("actiondetail", jsonString);
		}
		
	}
	
	
	public ILogService getLogService() {
		return logService;
	}
	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

}
