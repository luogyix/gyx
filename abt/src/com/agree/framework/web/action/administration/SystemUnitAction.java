package com.agree.framework.web.action.administration;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.struts2.webserver.IStartupObject;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.Unit;
import com.agree.framework.web.form.administration.User;
import com.agree.framework.web.service.administration.IUnitinfoService;
import com.agree.util.ActionUtil;
import com.agree.util.IDictABT;
/**
 * 
 * @ClassName: SystemUnitAction 
 * @Description: 部门信息管理
 * @company agree   
 * @author
 * @date 2011-10-12 下午03:52:12 
 *
 */
@SuppressWarnings("unchecked")
public class SystemUnitAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;

	private IUnitinfoService unitinfoService;
	private IStartupObject initService;
	private ABTComunicateNatp cona;
	
	public void setUnitinfoService(IUnitinfoService unitinfoService) {
		this.unitinfoService = unitinfoService;
	}
	public void setInitService(IStartupObject initService) {
		this.initService = initService;
	}
	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
	/**
	 * 
	 * @Title: loadPage 
	 * @Description: 进入界面
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String loadPage() throws Exception{
		//创建了结果返回类的对象
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		//获得ServletContext域
		ServletContext context = ServletActionContext.getServletContext();	
		//将ServletContext作为参数，调用initService的setUnits函数
		    //勾践出机构的树状结构，并将此机构的树状结构lisi 和  无结构的机构列表  保存到ServletContext域中
		initService.setUnits(context);
		
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		sRet.put(ServiceReturn.FIELD2, super.getLogonUser(true));
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}

	/**
	 * @Title: queryUnit 
	 * @Description:查询部门信息
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 * @edit lilei 2011-10-13
	 */
	public String queryUnit() throws Exception{
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Page pageInfo = this.getPage(jsonObj); 
		
		ActionUtil.setDimPara(jsonObj, "unitname");
		if(StringUtils.isEmpty((String)jsonObj.get("unitid"))){
			String curunitid=""+super.getLogonUser(false).getUnitid();
			jsonObj.put("unitid", curunitid);
		}
		List<Unit> unitList = this.unitinfoService.findUnitinfoAll(jsonObj,pageInfo);
		super.setActionResult(pageInfo, unitList, ret);
		
		return AJAX_SUCCESS;
	}
	/**
	 * 
	 * @Title: addUnit 
	 * @Description: 添加部门信息
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String addUnit() throws Exception{
		User user = getLogonUser(true);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		String createflag = obj.getString("default");
		Unit unit = (Unit)JSONObject.toBean(obj, Unit.class);
		unit.setCreateuser(String.valueOf(user.getUserid()));
		unit.setLastmoduser(String.valueOf(user.getUserid()));
		int checkcode = Integer.parseInt(unit.getUnitid())^102478;
		unit.setCheckcode(String.valueOf(checkcode));
		short unitorder = 1;
		unit.setUnitorder(String.valueOf(unitorder));
		ServiceReturn sRet = unitinfoService.addUnit(unit,createflag);
		
		//重新初始化部门内存信息
		ServletContext context = ServletActionContext.getServletContext();		
		initService.setUnits(context);
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 
	 * @Title: editUnit 
	 * @Description: 修改部门信息
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 *  @edit lilei 2011-10-13
	 */
	public String editUnit() throws Exception{
		User user = getLogonUser(true);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		Unit unit = (Unit)JSONObject.toBean(obj, Unit.class);
		unit.setLastmoduser(String.valueOf(user.getUserid()));
		short unitorder = 1;
		unit.setUnitorder(String.valueOf(unitorder));
		String parentid = (String)obj.get("parentunitid");//将原先的(String)obj.get("parentid")改成(String)obj.get("parentunitid")
		
		//		if(StringUtil.isEmpty(parentid)){
        //		parentid=(String)obj.get("parentunitid");			
		//		}
		if(obj.get("unitid_old").equals(obj.get("parentunitid"))){//将原先的(String)obj.get("parentid")改成(String)obj.get("parentunitid")
			throw new AppException("本部门不能成为本部门的上级部门！！");
		}
		ServiceReturn sRet = unitinfoService.editUnit(unit,parentid);
		//重新初始化部门内存信息
		ServletContext context = ServletActionContext.getServletContext();
		initService.setUnits(context);
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	/**
	 * 
	 * @Title: delUnit 
	 * @Description: 删除部门信息
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public String delUnit() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(Unit.class);
		List<Unit> units = (List<Unit>) JSONArray.toCollection(jsonArray,config);
		for(Unit unit : units){
			//修改用户状态
			unitinfoService.changeUnitUserState(unit);
			//调afa删除机构
			HttpServletRequest req = ServletActionContext.getRequest();
			User user = (User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
			//初始化NATP通讯
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b201_4.01", user);
			cona.set(IDictABT.BRANCH, unit.getUnitid());
			//{presbranch=[{'755004': '\xcd\xf2\xcf\xf3\xb3\xc7\xcd\xf8\xb5\xe3'}], H_ret_desc=[成功], succbranch=[{}], H_ret_code=[000000], H_ret_status=[S]}
			//判断afa的返回结果,是否成功
			Map<String,List<String>> map = cona.exchange();
			//List<String> succBranch = map.get("succbranch");//[{'123123123': '1', '22222': '22'}]
			List<String> presBranch = (List<String>) map.get("presbranch");//[{}]
			JSONObject obj = JSONObject.fromObject(presBranch.get(0));
			if(obj.size()!=0){
				throw new AppException("所删机构或子机构拥有正在使用的排队机,删除失败:");
			}
		}
		//ServiceReturn sRet = unitinfoService.delUnit(units);
		ServletContext context = ServletActionContext.getServletContext();
		initService.setUnits(context);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
}
