package com.agree.abt.action.pfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.pfs.PfsMobinterfaceTreeNode;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;

/**
 * 预填单界面配置
 * @ClassName: MachineViewConfAction.java
 * @company 赞同科技
 * @date 2013-9-11
 */
public class PfsMobInterfaceAction extends BaseAction {
	
	private ABTComunicateNatp cona;
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(PfsMobInterfaceAction.class);	
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 通过填单机编号查询填单机界面树的结构
	 * @throws Exception
	 */
	public void queryViewByMobNum() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//String jsonString = super.getJsonString();
		//JSONObject obj = JSONObject.fromObject(jsonString);
		String mobdevice_num = req.getParameter("mobdevice_num");
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b134.01", user);
		cona.set("branch", user.getUnitid());
		cona.set("mobdevice_num", mobdevice_num);
		cona.set("mobtype", "09");
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<PfsMobinterfaceTreeNode> list = new ArrayList<PfsMobinterfaceTreeNode>();
		String loopNum = map.get("mvsize").get(0);
		int num = Integer.parseInt(loopNum);
		//创建根节点
		for (int i = 0; i < num; i++) {
			PfsMobinterfaceTreeNode mvt = new PfsMobinterfaceTreeNode();
			mvt.setMobdevice_num(map.get("mobdevice_num").get(i));
			mvt.setBranch(map.get("branch").get(i));
			mvt.setNode_id(map.get("node_id").get(i));
			mvt.setNode_level(map.get("node_level").get(i));
			mvt.setNode_location(map.get("node_location").get(i));
			mvt.setChild_node_order(map.get("child_node_order").get(i));
			mvt.setNode_type(map.get("node_type").get(i));
			mvt.setDir_name(map.get("dir_name").get(i));
			mvt.setTrade_id(map.get("trade_id").get(i));
			mvt.setIs_show_msg(map.get("is_show_msg").get(i));
			mvt.setPrompt_msg(map.get("prompt_msg").get(i));
			mvt.setNode_index(map.get("node_index").get(i));

			//判断是目录还是节点
			if("1".equals(mvt.getNode_type())){
				mvt.setNode_name(mvt.getTrade_id());
			}else {
				mvt.setNode_name(mvt.getDir_name());
			}
			list.add(mvt);
		}
		//根据现有数据,把节点类型改变一下(0-根节点,1-目录,2-叶子(业务和预约激活)),并导入父节点ID
		for (PfsMobinterfaceTreeNode mvt : list) {
			switch (Integer.parseInt(mvt.getNode_type())) {
			case 0:
				//目录
				mvt.setNode_type("1");
				break;
			case 1:
				//业务
				mvt.setNode_type("2");
				break;
			}
			String nodeId = mvt.getNode_id();
			String childNodes = mvt.getChild_node_order();
			//101,102
			String[] childs = childNodes.split(",");
			
			//取出子节点,遍历
			for (int k = 0; k < childs.length; k++) {
				String childNode = childs[k];
				//与list里的node_id对比,符合就加到parent_id里并跳出循环
				for (PfsMobinterfaceTreeNode tempMvt : list) {
					if(childNode.equals(tempMvt.getNode_id())){
						tempMvt.setParent_id(nodeId);
						break;
					}
				}
			}
		}
		
		//将数据库里取到的节点数据与根节点关联
		for (PfsMobinterfaceTreeNode mvt2 : list) {
			if("".equals(mvt2.getParent_id())||null==mvt2.getParent_id()){
				mvt2.setParent_id("root");
			}
		}
		
		PfsMobinterfaceTreeNode root = new PfsMobinterfaceTreeNode();
		root.setNode_id("root");
		root.setNode_name("填单机");
		root.setNode_type("0");
		root.setNode_index("0");//第0个
		root.setParent_id("-1");
		root.setNode_level("1");
		root.setNode_location("1");
		list.add(0, root);
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	/**
	 * 给填单机界面树添加目录
	 * @throws Exception
	 */
	public String addFolder() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b134_5.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.MOBTYPE,"09");
		cona.set(IDictABT.MOBDEVICE_NUM, obj.getString(IDictABT.MOBDEVICE_NUM));
		cona.set("parent_node_id", "root".equals(obj.getString("parent_node_id"))?"":obj.getString("parent_node_id"));
		cona.set(IDictABT.NODE_LEVEL, obj.getString(IDictABT.NODE_LEVEL));
		cona.set(IDictABT.NODE_LOCATION, obj.getString(IDictABT.NODE_LOCATION));
		cona.set(IDictABT.NODE_TYPE, obj.getString(IDictABT.NODE_TYPE));
		cona.set(IDictABT.DIR_NAME, obj.getString(IDictABT.DIR_NAME));
		cona.set("node_index", obj.getString("node_index"));
		//判断afa的返回结果,是否成功
		cona.exchange();

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 给填单机界面树修改目录
	 * @throws Exception
	 */
	public String editFolder() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b134_6.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.MOBDEVICE_NUM, obj.getString(IDictABT.MOBDEVICE_NUM));
		cona.set(IDictABT.MOBTYPE,"09");
		cona.set(IDictABT.DIR_NAME, obj.getString(IDictABT.DIR_NAME));
		cona.set(IDictABT.NODE_ID, obj.getString(IDictABT.NODE_ID));
		//判断afa的返回结果,是否成功
		cona.exchange();

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 点击填单机界面树查询业务节点
	 * @throws Exception
	 */
	public String queryLeafByViewNo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b134_2.01", user);
		cona.set(IDictABT.MOBTYPE,"09");
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.NODE_ID, obj.getString("node_id"));
		cona.set(IDictABT.MOBDEVICE_NUM, obj.getString("mobdevice_num"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		logger.info("map:" + map);
		
		Map<String, Object> leafMap = new HashMap<String, Object>();
		leafMap.put(IDictABT.NODE_ID, map.get(IDictABT.NODE_ID).get(0));
		leafMap.put(IDictABT.NODE_LEVEL, map.get(IDictABT.NODE_LEVEL).get(0));
		leafMap.put(IDictABT.NODE_LOCATION, map.get(IDictABT.NODE_LOCATION).get(0));
		leafMap.put(IDictABT.CHILD_NODE_ORDER, map.get(IDictABT.CHILD_NODE_ORDER).get(0));
		leafMap.put(IDictABT.NODE_TYPE, map.get(IDictABT.NODE_TYPE).get(0));
		leafMap.put(IDictABT.TRADE_ID, map.get(IDictABT.TRADE_ID).get(0));
		leafMap.put(IDictABT.IS_SHOW_MSG,"1".equals( map.get(IDictABT.IS_SHOW_MSG).get(0))?"true":"false");
		leafMap.put(IDictABT.PROMPT_MSG, map.get(IDictABT.PROMPT_MSG).get(0));
		leafMap.put("node_index", map.get("node_index").get(0));
		leafMap.put("menu_name", map.get("dir_name").get(0));
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		
		ret.put("leafMap", leafMap);
		//ServiceReturn retn = new ServiceReturn(true, "");
		//ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		//ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
		super.setActionResult(pageInfo, null, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 给填单机界面树添加业务节点
	 * @throws Exception
	 */
	public String addLeaf() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b134_1.01", user);
		cona.set(IDictABT.MOBDEVICE_NUM, obj.getString(IDictABT.MOBDEVICE_NUM));
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.MOBTYPE,"09");
		cona.set("parent_node_id", "root".equals(obj.getString("parent_node_id"))?"":obj.getString("parent_node_id"));
		cona.set("node_level", obj.getString("node_level"));
		cona.set("node_location", obj.getString("node_location"));
		cona.set(IDictABT.NODE_TYPE, obj.getString(IDictABT.NODE_TYPE));
		cona.set("trade_id", obj.getString("trade_id"));
		cona.set("is_show_msg", "true".equals(obj.getString("is_show_msg"))?"1":"0");
		cona.set("prompt_msg", obj.getString("prompt_msg"));
		cona.set("node_index", obj.getString("node_index"));
		cona.set("dir_name", obj.getString("menu_name"));//在业务里面加个菜单目录menu_name存到数据库的dir_name里	
		StringBuffer final_prompt_msg = new StringBuffer("[");
		cona.set("prompt_msgx", final_prompt_msg.substring(0, final_prompt_msg.length()-1) + "]");
		
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		if(map.get("H_ret_code") == null){
			throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
		}
		String H_ret_code = (String) map.get("H_ret_code").get(0);
		if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
			throw new AppException("错误码:"+H_ret_code+","+"错误信息:"+map.get("H_ret_desc").get(0));
		}
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 给填单机界面树修改业务节点
	 * @throws Exception
	 */
	public String editLeaf() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b134_3.01", user);
		cona.set("is_show_msg", "true".equals(obj.getString("is_show_msg"))?"1":"0");
		cona.set(IDictABT.MOBTYPE,"09");
		cona.set(IDictABT.MOBDEVICE_NUM, obj.getString(IDictABT.MOBDEVICE_NUM));
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("node_id", obj.getString("node_id"));
		cona.set("trade_id", obj.getString("trade_id"));
		cona.set("prompt_msg", obj.getString("prompt_msg"));
		cona.set("dir_name", obj.getString("menu_name"));//在业务里面加个菜单目录menu_name存到数据库的dir_name里	
		StringBuffer final_prompt_msg = new StringBuffer("[");		
		cona.set("prompt_msgx", final_prompt_msg.substring(0, final_prompt_msg.length()-1) + "]");
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		if(map.get("H_ret_code") == null){
			throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
		}
		String H_ret_code = (String) map.get("H_ret_code").get(0);
		if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
			throw new AppException("错误码:"+H_ret_code+","+"错误信息:"+map.get("H_ret_desc").get(0));
		}

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	
	/**
	 * 查询填单机下拉框
	 * @throws Exception
	 */
	public void queryQMInfoSmall() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b131_2.01", user);
		cona.set("branch", user.getUnitid()); //查询条件.待确认
		//cona.set(IDictABT.DEVICETYPE, "04");//原来是09，华润需要设备初始化获得的所有参数，所以变更04获取设备编号
		cona.set(IDictABT.DEVICETYPE, "10");
		cona.set("pageflag","3");
		cona.set("currentpage","0");
		cona.set("count","20");
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		logger.info("---------------------------map : " + map);
		String loopNum = (String) map.get("devicesize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.MOBDEVICE_NUM, map.get("device_num").get(i));
			hld.put(IDictABT.MOBDEVICE_NAME, map.get("device_name").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	/**
	 * 点击填单机界面树删除节点
	 * @throws Exception
	 */
	public String delLeafByViewNo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		//初始化NATP通讯
		cona.reInit();
		cona.setBMSHeader("ibp.bms.b134_4.01", user);
		cona.set(IDictABT.MOBTYPE,"09");
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set(IDictABT.MOBDEVICE_NUM, obj.getString(IDictABT.MOBDEVICE_NUM));
		cona.set("node_index", obj.getString("node_index").substring(0, 2));
		cona.set("parent_node_id", obj.getString("parent_node_id"));
		String node_id = obj.getString(IDictABT.NODE_ID);
		cona.set(IDictABT.NODE_ID, node_id);
		String child_node_order = obj.getString("child_node_order");
		String child_node_order_final = "";
		String[] child_node_orders = child_node_order.split(",");
		for (int i = 0; i < child_node_orders.length; i++) {
			if(!node_id.equals(child_node_orders[i])){
				child_node_order_final = child_node_order_final +"," +  child_node_orders[i];
			}
		}
		cona.set("child_node_order", "".equals(child_node_order_final)?"":child_node_order_final.substring(1));
		
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		if(map.get("H_ret_code") == null){
			throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
		}
		String H_ret_code = (String) map.get("H_ret_code").get(0);
		if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
			throw new AppException("错误码:"+H_ret_code+","+"错误信息:"+map.get("H_ret_desc").get(0));
		}

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
	
	
}
