package com.agree.framework.web.action.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.Dictionary;


/** 
 * @ClassName: CommonAction 
 * @Description: TODO
 * @company agree   
 * @author haoruibing   
 * @date 2011-8-25 下午09:39:20 
 *  
 */ 
@SuppressWarnings({"unchecked","rawtypes"})
public class CommonAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * @Title: getSystemDictionaryItem 
	 * @Description: 获得系统字典对应值
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public String getSystemDictionaryItem() throws Exception{
		String item_id = ServletActionContext.getRequest().getParameter("item_id");
		Map map=  (Map) ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMDICTIONARY);
		List dictList = (List) map.get(item_id);
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, dictList);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
		return null;
	}
	/** 
	 * @Title: getAppDictionaryItem 
	 * @Description: 获得应用字典的对应值
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public String getAppDictionaryItem() throws Exception{
		String item_id = ServletActionContext.getRequest().getParameter("item_id");
		Map map=  (Map) ServletActionContext.getServletContext().getAttribute(ApplicationConstants.APPDICTIONARY);
		List dictList = (List) map.get(item_id);
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, dictList);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
		return null;
	}
	
	/** 
	 * @Title: exportXls 
	 * @Description: 导出excel通用方法
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public  String exportXls() throws Exception{
		ServiceReturn sRet = new ServiceReturn(true, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/** 
	 * @Title: getSystemDictionaryItem 
	 * @Description: 获得系统字典对应值
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public static String getSystemDictionaryItem(String item_id) throws Exception{
		Map map=  (Map) ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMDICTIONARY);
		List<Dictionary> dictList = (List<Dictionary>) map.get(item_id);
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, dictList);
		return convertServiceReturnToJson(ret).toString();
	}
	
	
	/**
	 * 通过dicttype获得字典记录，再通过记录的Dictvalue做为dicttype查询字典获得字典集合
	 * @return
	 * @throws Exception
	 */
	public String findSystemDictionaryItemByDicttype() throws Exception{
		String dicttype = ServletActionContext.getRequest().getParameter("dicttype");
		Map map=  (Map) ApplicationConstants.getContext().getAttribute(ApplicationConstants.SYSTEMDICTIONARY);
		List dictList = (List) map.get(dicttype);
		
		List list = new ArrayList();
		for (Object object : dictList) {
			JSONObject jsonobj = JSONObject.fromObject(object);
			String dictvalue = jsonobj.getString("dictValue");
			List tempList = (List)map.get(dictvalue);
			if(tempList!=null){
				list.addAll(tempList);
			}
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
		return null;
	}
	
	/** 
	 * @Title: getAppDictionaryItem 
	 * @Description: 获得应用字典的对应值
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public static String getAppDictionaryItem(String item_id) throws Exception{
		Map map=  (Map) ServletActionContext.getServletContext().getAttribute(ApplicationConstants.APPDICTIONARY);
		List dictList = (List) map.get(item_id);
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, dictList);
		return convertServiceReturnToJson(ret).toString();
	}
	
	/** 
	 * @Title: getAppDictionaryItem 
	 * @Description: 获得应用字典的对应值(显示值不带key)
	 * @param @return
	 * @param @throws Exception    参数 
	 * @return String    返回类型 
	 * @throws 
	 */ 
	public static String getAppDictionaryItemWithnotkey(String item_id) throws Exception{
		Map map=  (Map) ServletActionContext.getServletContext().getAttribute(ApplicationConstants.APPDICTIONARY);
		List<Dictionary> dictList = (List) map.get(item_id);
		List<Dictionary> list=new ArrayList();
		for(Dictionary dic:dictList){
			Dictionary newdic=new Dictionary();
			newdic.setDictValue(dic.getDictValue());
			String[] desc = (dic.getDictValueDesc().split("-"));
			newdic.setDictValueDesc(desc[1]);
			list.add(newdic);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		return convertServiceReturnToJson(ret).toString();
	}
	
	
}
