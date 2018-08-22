package com.agree.framework.web.action.administration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.PasswordEntity;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.administration.IAdministrationService;
import com.agree.util.MD5;

public class UpdateUserPassAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;

	private IAdministrationService administrationService;
	public void setAdministrationService(
			IAdministrationService administrationService) {
		this.administrationService = administrationService;
	}

	public String loadPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 用户密码修改
	 */
	public String updatePass() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		obj.remove("keys");
		obj.remove("data");
		PasswordEntity passwordEntity = (PasswordEntity)JSONObject.toBean(obj, PasswordEntity.class);
		User user=(User)ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);//返回登录用户信息
		passwordEntity.setUserId(user.getUserid().toString());
		MD5 md5 = new MD5();
		passwordEntity.setNowPassword(md5.getMD5String(passwordEntity.getNowPassword()));//现用密码加密
		passwordEntity.setUpdatePassword(md5.getMD5String(passwordEntity.getUpdatePassword()));//新密码加密
		ServiceReturn sRet1 = this.administrationService.getUserChecked(passwordEntity);
		if(sRet1.getSuccess()){//返回true
			ServiceReturn sRet = this.administrationService.updatePassword_itransc(passwordEntity);
			JSONObject returnJson = super.convertServiceReturnToJson(sRet);
			super.setActionresult(returnJson.toString());
		}else{//返回false
			JSONObject robj = new JSONObject();
			robj.put("message", "您输入的密码不正确,请重新输入！");
			robj.put("res", true);
			robj.put("result", true);
			super.setActionresult(robj.toString());
		}
		return AJAX_SUCCESS;
	}
	

	/**
	 * 用户密码修改
	 */
	public String updatePass4Mobile() throws Exception{
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		String msgStr = request.getParameter("msg");
		JSONObject obj = JSONObject.fromObject(msgStr);
		
		PasswordEntity passwordEntity = (PasswordEntity)JSONObject.toBean(obj, PasswordEntity.class);
		User user=(User)ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);//返回登录用户信息
		passwordEntity.setUserId(user.getUserid().toString());
		MD5 md5 = new MD5();
		passwordEntity.setNowPassword(md5.getMD5String(passwordEntity.getNowPassword()));//现用密码加密
		passwordEntity.setUpdatePassword(md5.getMD5String(passwordEntity.getUpdatePassword()));//新密码加密
		ServiceReturn sRet1 = this.administrationService.getUserChecked(passwordEntity);
		if(sRet1.getSuccess()){//返回true
			ServiceReturn sRet = this.administrationService.updatePassword_itransc(passwordEntity);
			JSONObject returnJson = super.convertServiceReturnToJson(sRet);
			super.setActionresult(returnJson.toString());
		}else{//返回false
			JSONObject robj = new JSONObject();
			robj.put("message", "您输入的密码不正确,请重新输入！");
			robj.put("res", true);
			robj.put("result", true);
			super.setActionresult(robj.toString());
		}
		return AJAX_SUCCESS;
	}
}
