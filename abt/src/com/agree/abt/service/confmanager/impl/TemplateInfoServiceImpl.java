package com.agree.abt.service.confmanager.impl;

import java.util.List;
import java.util.UUID;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.confManager.BtSysTemplateInfo;
import com.agree.abt.service.confmanager.ITemplateInfoService;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.base.BaseService;

public class TemplateInfoServiceImpl extends BaseService implements ITemplateInfoService {

	public ServiceReturn queryTemplateInfo(String branch) {
		List<BtSysTemplateInfo> list = sqlDao_h.getRecordList("from BtSysTemplateInfo");
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, list);
		return sRet;
	}

	public ServiceReturn addTemplateInfo(String strJson) {
		JSONArray array = JSONArray.fromObject(strJson);
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//["模板2","测试一下","qm,window,machineView,queueingRules,managerCallNum,branchParam"]
		BtSysTemplateInfo t = new BtSysTemplateInfo();
		t.setTemplate_id(UUID.randomUUID().toString().substring(0, 8));
		t.setBranch(user.getUnitid());
		t.setTemplate_name(array.get(0).toString());
		t.setTemplate_note(array.get(1).toString());
		t.setTemplate_flag(array.get(2).toString());
		sqlDao_h.saveRecord(t);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		return sRet;
	}
	
//-------------------------------------------无用----------------------------------------------
//	public void clearHisLogData() throws Exception {
//	String sql ="insert into t_his_mnguseroprlog(UNITID,LOGDATE,LOGTIME,USERNAME,HOSTIP,OPERATION,RESULT,USERCODE,UNITNAME,OPERDETAIL) " +
//			"select UNITID,LOGDATE,LOGTIME,USERNAME,HOSTIP,OPERATION,RESULT,USERCODE,UNITNAME,OPERDETAIL from t_bsms_mnguseroprlog";
//	String[] test={};
//	sqlDao_h.excuteSql(sql,test);
//	String delsql = "delete from t_bsms_mnguseroprlog where logdate < to_char(sysdate - 30, 'YYYYMMDD')";
//	sqlDao_h.excuteSql(delsql,test);
//	String delmsg = "delete from t_bsms_msgbook where 1=1";
//	sqlDao_h.excuteSql(delmsg,test);
//	
//	String delbak = "delete from T_BAK_UPPTABLE where 1=1";
//	sqlDao_h.excuteSql(delbak,test);



//List reaultList=new ArrayList();
//if(list != null && list.size() != 0){
//	for(Module module:list){//取出菜单信息
//		boolean isleaf=true;
//		for(Module module1:list){//循环判断，是否有该菜单的子节点，有的话则修改标志为false，
//			if(module1.getParentmoduleid().equals(module.getModuleid())){
//				isleaf=false;
//				break;
//			}
//		}
//		module.setIsleaf(isleaf);
//		reaultList.add(module);
//	}
//}
//}
}
