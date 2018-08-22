package com.agree.framework.web.action.administration;

import java.util.List;

import javax.servlet.ServletContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.struts2.webserver.IStartupObject;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.TBsmsDictinfo;
import com.agree.framework.web.service.administration.IDictinfoService;
import com.agree.util.ActionUtil;

/**
 * 
 * @ClassName: DictinfoAction 
 * @Description: 数据字典操作
 * @company agree   
 * @author dhs   
 * @date 2011-10-14 下午02:32:33 
 *
 */
@SuppressWarnings("unchecked")
public class DictinfoAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private IDictinfoService dictinfoService;
	private IStartupObject initService;

	public void setDictinfoService(IDictinfoService dictinfoService) {
		this.dictinfoService = dictinfoService;
	}
	public void setInitService(IStartupObject initService) {
		this.initService = initService;
	}
	
	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Title: queryDict 
	 * @Description: 查询字典信息
	 * @return String
	 * @throws Exception
	 */
	public String queryDict() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Page pageInfo = this.getPage(jsonObj); 
		ActionUtil.setDimPara(jsonObj, "dicttypedesc");
		List<TBsmsDictinfo> dictList = dictinfoService.findDictinfoAll(jsonObj, pageInfo);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		super.setActionResult(pageInfo, dictList, ret);
		
		return AJAX_SUCCESS;
	}
	/**
	 * 
	 * @Title: addDict 
	 * @Description: 添加字典信息
	 * @return String
	 * @throws Exception
	 */
	public String addDict()throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		TBsmsDictinfo dictinfo = (TBsmsDictinfo)JSONObject.toBean(obj, TBsmsDictinfo.class);
		ServiceReturn sRet = dictinfoService.addDictinfo(dictinfo);
		
		//初始化内存
		ServletContext context = ServletActionContext.getServletContext();
		initService.setSystemDict(context);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 *  
	 * @Title: editDict 
	 * @Description: 修改字典信息
	 * @return String
	 * @throws Exception
	 */
	public String editDict() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		TBsmsDictinfo dictinfo = (TBsmsDictinfo)JSONObject.toBean(obj, TBsmsDictinfo.class);
		ServiceReturn sRet = dictinfoService.editDictinfo(dictinfo);
		
		//初始化内存
		ServletContext context = ServletActionContext.getServletContext();
		initService.setSystemDict(context);
		
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 
	 * @Title: delDict 
	 * @Description: 删除字典信息
	 * @return String
	 * @throws Exception
	 */
	
	public String delDict() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(TBsmsDictinfo.class);
		List<TBsmsDictinfo> dictinfos = (List<TBsmsDictinfo>) JSONArray.toCollection(jsonArray,config);
		ServiceReturn ret = dictinfoService.delDictinfo(dictinfos);
		
		//初始化内存
		ServletContext context = ServletActionContext.getServletContext();
		initService.setSystemDict(context);
		
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
}
