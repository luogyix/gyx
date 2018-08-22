package com.agree.abt.action.configManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.abt.model.confManager.MachineViewTreeNode;
import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;

/**
 * 排队机界面配置
 * @ClassName: MachineViewConfAction.java
 * @company 赞同科技
 * @date 2013-9-11
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MachineViewConfAction extends BaseAction {
	
	private ABTComunicateNatp cona;
	private static final long serialVersionUID = 1L;
	
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		boolean machine_view_flag = (Boolean)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.MACHINEVIEWFLAG);
		if(machine_view_flag){
			return "success2";
		}else {
			return SUCCESS;
		}
	}
	
	/**
	 * 通过排队机编号查询排队机界面树的结构
	 * @throws Exception
	 */
	public void queryViewByQmNum() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//String jsonString = super.getJsonString();
		//JSONObject obj = JSONObject.fromObject(jsonString);
		String qm_num = req.getParameter("qm_num");
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b121.01", user);
		//传入的机构号
		cona.set("branch", user.getUnitid());
		cona.set("qm_num", qm_num);
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();

		List<MachineViewTreeNode> list = new ArrayList<MachineViewTreeNode>();
		String loopNum = (String) map.get("mvsize").get(0);
		int num = Integer.parseInt(loopNum);
		//创建根节点
		for (int i = 0; i < num; i++) {
			MachineViewTreeNode mvt = new MachineViewTreeNode();
			mvt.setQm_num((String) map.get("qm_num").get(i));
			mvt.setBranch((String) map.get("branch").get(i));
			mvt.setNode_id((String) map.get("node_id").get(i));
			mvt.setNode_level((String) map.get("node_level").get(i));
			mvt.setNode_location((String) map.get("node_location").get(i));
			mvt.setChild_node_order((String) map.get("child_node_order").get(i));
			mvt.setNode_type((String) map.get("node_type").get(i));
			mvt.setDir_name((String) map.get("dir_name").get(i));
			mvt.setBs_id((String) map.get("bs_id").get(i));
			mvt.setIs_show_msg((String) map.get("is_show_msg").get(i));
			mvt.setPrompt_msg((String) map.get("prompt_msg").get(i));
			mvt.setNode_index((String) map.get("node_index").get(i));
			mvt.setCard_visiable((String) map.get("card_visiable").get(i));
			mvt.setNeed_card((String) map.get("need_card").get(i));
			//判断是目录还是节点
			if("1".equals(mvt.getNode_type())){
				mvt.setNode_name(mvt.getBs_id());
			}else {
				mvt.setNode_name(mvt.getDir_name());
			}
			mvt.setDir_name_en((String) map.get("dir_name_en").get(i));
			list.add(mvt);
		}
		//根据现有数据,把节点类型改变一下(0-根节点,1-目录,2-叶子(业务和预约激活)),并导入父节点ID
		for (MachineViewTreeNode mvt : list) {
			switch (Integer.parseInt(mvt.getNode_type())) {
			case 0:
				//目录
				mvt.setNode_type("1");
				break;
			case 1:
				//业务
				mvt.setNode_type("2");
				break;
			case 2:
				//预约激活
				mvt.setNode_type("2");
				mvt.setIs_reserve(true);
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
				for (MachineViewTreeNode tempMvt : list) {
					if(childNode.equals(tempMvt.getNode_id())){
						tempMvt.setParent_id(nodeId);
						break;
					}
				}
			}
		}
		
		//将数据库里取到的节点数据与根节点关联
		for (MachineViewTreeNode mvt2 : list) {
			if("".equals(mvt2.getParent_id())||null==mvt2.getParent_id()){
				mvt2.setParent_id("root");
			}
		}
		
		MachineViewTreeNode root = new MachineViewTreeNode();
		root.setNode_id("root");
		root.setNode_name("排队机");
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
	 * 给排队机界面树添加目录
	 * @throws Exception
	 */
	public String addFolder() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b121_5.01", user);

		//传入的机构号
		cona.set("branch", user.getUnitid());
		cona.set(IDictABT.QM_NUM, obj.getString(IDictABT.QM_NUM));
		cona.set("parent_node_id", "root".equals(obj.getString("parent_node_id"))?"":obj.getString("parent_node_id"));
		cona.set(IDictABT.NODE_LEVEL, obj.getString(IDictABT.NODE_LEVEL));
		cona.set(IDictABT.NODE_LOCATION, obj.getString(IDictABT.NODE_LOCATION));
		cona.set(IDictABT.NODE_TYPE, obj.getString(IDictABT.NODE_TYPE));
		cona.set(IDictABT.DIR_NAME, obj.getString(IDictABT.DIR_NAME));
		cona.set("dir_name_en", obj.getString("dir_name_en"));
		cona.set("node_index", obj.getString("node_index"));
		//判断afa的返回结果,是否成功
		cona.exchange();

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 给排队机界面树修改目录
	 * @throws Exception
	 */
	public String editFolder() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b121_6.01", user);
		
		//传入的机构号
		cona.set("branch", user.getUnitid());
		cona.set(IDictABT.QM_NUM, obj.getString(IDictABT.QM_NUM));
		cona.set(IDictABT.DIR_NAME, obj.getString(IDictABT.DIR_NAME));
		cona.set("dir_name_en", obj.getString("dir_name_en"));
		cona.set(IDictABT.NODE_ID, obj.getString(IDictABT.NODE_ID));
		//判断afa的返回结果,是否成功
		cona.exchange();

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 点击排队机界面树查询业务节点
	 * @throws Exception
	 */
	public String queryLeafByViewNo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b121_2.01", user);
		//传入的机构号
		cona.set("branch", user.getUnitid());
		cona.set(IDictABT.NODE_ID, obj.getString("node_id"));
		cona.set(IDictABT.QM_NUM, obj.getString("qm_num"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		
		
		Map<String, Object> leafMap = new HashMap<String, Object>();
		leafMap.put(IDictABT.NODE_ID, map.get(IDictABT.NODE_ID).get(0));
		leafMap.put(IDictABT.NODE_LEVEL, map.get(IDictABT.NODE_LEVEL).get(0));
		leafMap.put(IDictABT.NODE_LOCATION, map.get(IDictABT.NODE_LOCATION).get(0));
		leafMap.put(IDictABT.CHILD_NODE_ORDER, map.get(IDictABT.CHILD_NODE_ORDER).get(0));
		leafMap.put(IDictABT.NODE_TYPE, map.get(IDictABT.NODE_TYPE).get(0));
		leafMap.put(IDictABT.BS_ID, map.get(IDictABT.BS_ID).get(0));
		leafMap.put(IDictABT.IS_SHOW_MSG, map.get(IDictABT.IS_SHOW_MSG).get(0));
		leafMap.put(IDictABT.PROMPT_MSG, map.get(IDictABT.PROMPT_MSG).get(0));
		leafMap.put("node_index", map.get("node_index").get(0));
		leafMap.put("need_reserv", map.get("need_reserv").get(0));
		leafMap.put("card_visiable", map.get("card_visiable").get(0));
		leafMap.put("need_card", map.get("need_card").get(0));
		//这里拆分成两个list,一个存预约的一个不存预约的
		List<Map<String,Object>> custtype_list = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> reserv_custtype_list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("cardqueuesize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put("bound", "1");
			hld.put(IDictABT.NODE_ID, map.get("node_id").get(i));
			hld.put(IDictABT.CUSTTYPE_I, map.get("custtype_i").get(i));
			hld.put(IDictABT.QUEUETYPE_ID, map.get("queuetype_id").get(i));
			hld.put(IDictABT.WAIT_AREA, map.get("wait_area").get(i));
			hld.put("prompt_msg", map.get("prompt_msgx").get(i));
			hld.put("nocard_default", "1".equals(map.get("nocard_default").get(i))?"1":"");
			hld.put("reserv_queuetype_id", "");
			//判断是否为预约客户类型
			if("1".equals(map.get("isreservqidflag").get(i))){//是预约,将预约标志赋值1
				hld.put("isreservqidflag", "true");
				reserv_custtype_list.add(hld);
			}else{//不是预约,将预约标志赋值1
				hld.put("isreservqidflag", "");
				custtype_list.add(hld);
			}
		}
		//将两个list合二为一
		for (int a = 0; a < custtype_list.size(); a++) {
			Map<String, Object> custtype_map = custtype_list.get(a);
			for (int b = 0; b < reserv_custtype_list.size(); b++) {
				Map<String, Object> reserv_custtype_map = reserv_custtype_list.get(b);
				//判断预约与非预约的两条数据是一个客户类型,将预约的队列ID赋予过去
				if (reserv_custtype_map.get("custtype_i").equals(custtype_map.get("custtype_i"))) {
					custtype_map.put("reserv_queuetype_id", reserv_custtype_map.get("queuetype_id"));
					custtype_map.put("isreservqidflag", "true");
					break;
				}
			}
		}
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		
		ret.put("leafMap", leafMap);
		ret.put("custtype_list", custtype_list);
		//ServiceReturn retn = new ServiceReturn(true, "");
		//ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		//ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
		super.setActionResult(pageInfo, null, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 给排队机界面树添加业务节点
	 * @throws Exception
	 */
	public String addLeaf() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b121_1.01", user);

		cona.set(IDictABT.QM_NUM, obj.getString(IDictABT.QM_NUM));
		cona.set("branch", user.getUnitid());
		cona.set("parent_node_id", "root".equals(obj.getString("parent_node_id"))?"":obj.getString("parent_node_id"));
		cona.set("node_level", obj.getString("node_level"));
		cona.set("node_location", obj.getString("node_location"));
		cona.set(IDictABT.NODE_TYPE, obj.getString(IDictABT.NODE_TYPE));
		cona.set("bs_id", obj.getString("bs_id"));
		cona.set("is_show_msg", "true".equals(obj.getString("is_show_msg"))?"1":"0");
		cona.set("prompt_msg", obj.getString("prompt_msg"));
		cona.set("node_index", obj.getString("node_index"));
		cona.set("need_reserv", "true".equals(obj.getString("need_reserv"))?"1":"0");
		cona.set("card_visiable", "true".equals(obj.getString("card_visiable"))?"1":"0");
		cona.set("need_card", "true".equals(obj.getString("need_card"))?"1":"0");
		JSONArray array = obj.getJSONArray("listData");
		boolean def = true;
		if(!"true".equals(obj.getString("need_card"))&&!"true".equals(obj.getString("card_visiable"))){//判断如果不必须卡
			def = false;
		}
		StringBuffer final_custtype_i = new StringBuffer("[");
		StringBuffer final_queuetype_id = new StringBuffer("[");
		StringBuffer final_wait_area = new StringBuffer("[");
		StringBuffer final_prompt_msg = new StringBuffer("[");
		StringBuffer final_nocard_default = new StringBuffer("[");
		
		StringBuffer final_isreservqidflag = new StringBuffer("[");
		int num = 0;
		for(int i = 0 ; i<array.size() ; i++){
			JSONObject map = array.getJSONObject(i);
			if("".equals(map.getString("queuetype_id"))){
				throw new AppException("需要为勾选的客户类型配置队列!");
			}
			if(map.getString("wait_area").length()>100){
				throw new AppException("自定义客户等待区域长度不能大于100位!");
			}
			if(map.getString("prompt_msg").length()>100){
				throw new AppException("提示信息字数长度不能大于100位!");
			}
			final_custtype_i.append("'" + map.getString("custtype_i") + "', ");
			final_queuetype_id.append("'" + map.getString("queuetype_id") + "', ");
			final_wait_area.append("'" + map.getString("wait_area") + "', ");
			final_prompt_msg.append("'" + map.getString("prompt_msg") + "', ");
			
			String nocard_default = map.getString("nocard_default");
			if("".equals(nocard_default)){
				nocard_default = "0";
			}else if("false".equals(nocard_default)){
				nocard_default = "0";
			}else {
				nocard_default = "1";
				def = true;
			}
			final_nocard_default.append("'" + nocard_default + "', ");
			final_isreservqidflag.append("'0', ");
			
			if("true".equals(map.getString("isreservqidflag"))){
				final_custtype_i.append("'" + map.getString("custtype_i") + "', ");
				final_queuetype_id.append("'" + map.getString("reserv_queuetype_id") + "', ");
				final_wait_area.append("'" + map.getString("wait_area") + "', ");
				final_prompt_msg.append("'" + map.getString("prompt_msg") + "', ");
				final_nocard_default.append("'" + nocard_default + "', ");
				final_isreservqidflag.append("'1', ");
				num++;
			}
		}
		if(!def){
			throw new AppException("配置错误,没有设定无卡时默认客户类型");
		}
		cona.set("custtype_i", final_custtype_i.substring(0, final_custtype_i.length()-2) + "]");
		cona.set("queuetype_id", final_queuetype_id.substring(0, final_queuetype_id.length()-2) + "]");
		cona.set("wait_area", final_wait_area.substring(0, final_wait_area.length()-2) + "]");
		cona.set("prompt_msgx", final_prompt_msg.substring(0, final_prompt_msg.length()-2) + "]");
		cona.set("nocard_default", final_nocard_default.substring(0, final_nocard_default.length()-2) + "]");
		cona.set("isreservqidflag", final_isreservqidflag.substring(0, final_isreservqidflag.length()-2) + "]");
		
		//传长度
		cona.set("cardqueuesize", String.valueOf(array.size() + num));
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 给排队机界面树修改业务节点
	 * @throws Exception
	 */
	public String editLeaf() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b121_3.01", user);
		
		cona.set(IDictABT.QM_NUM, obj.getString(IDictABT.QM_NUM));
		cona.set("branch", user.getUnitid());
		cona.set("node_id", obj.getString("node_id"));
		cona.set("bs_id", obj.getString("bs_id"));
		cona.set("is_show_msg", "true".equals(obj.getString("is_show_msg"))?"1":"0");//是否提示文字(checkbox)
		cona.set("prompt_msg", obj.getString("prompt_msg"));
		cona.set("need_reserv", "true".equals(obj.getString("need_reserv"))?"1":"0");//是否提示文字(checkbox)
		cona.set("card_visiable", "true".equals(obj.getString("card_visiable"))?"1":"0");//是否提示文字(checkbox)
		cona.set("need_card", "true".equals(obj.getString("need_card"))?"1":"0");//是否提示文字(checkbox)
		boolean def = true;
		if(!("true".equals(obj.getString("need_card")))&&!("true".equals(obj.getString("card_visiable")))){//判断如果不必须卡
			def = false;
		}
		JSONArray array = obj.getJSONArray("listData");
		
		StringBuffer final_custtype_i = new StringBuffer("[");
		StringBuffer final_queuetype_id = new StringBuffer("[");
		StringBuffer final_wait_area = new StringBuffer("[");
		StringBuffer final_prompt_msg = new StringBuffer("[");
		StringBuffer final_nocard_default = new StringBuffer("[");
		
		StringBuffer final_isreservqidflag = new StringBuffer("[");
		int num = 0;
		for(int i = 0 ; i<array.size() ; i++){
			JSONObject map = array.getJSONObject(i);
			if("".equals(map.getString("queuetype_id"))){
				throw new AppException("需要为勾选的客户类型配置队列!");
			}
			if(map.getString("wait_area").length()>100){
				throw new AppException("自定义客户等待区域长度不能大于100位!");
			}
			if(map.getString("prompt_msg").length()>100){
				throw new AppException("提示信息字数长度不能大于100位!");
			}
			final_custtype_i.append("'" + map.getString("custtype_i") + "', ");
			final_queuetype_id.append("'" + map.getString("queuetype_id") + "', ");
			final_wait_area.append("'" + map.getString("wait_area") + "', ");
			final_prompt_msg.append("'" + map.getString("prompt_msg") + "', ");
			
			String nocard_default = map.getString("nocard_default");
			if("".equals(nocard_default)){
				nocard_default = "0";
			}else if("false".equals(nocard_default)){
				nocard_default = "0";
			}else {
				nocard_default = "1";
				def = true;
			}
			final_nocard_default.append("'" + nocard_default + "', ");
			final_isreservqidflag.append("'0', ");
			
			//判断预约标志,向预约的list里添加数据
			if("true".equals(map.getString("isreservqidflag"))){
				final_custtype_i.append("'" + map.getString("custtype_i") + "', ");
				final_queuetype_id.append("'" + map.getString("reserv_queuetype_id") + "', ");
				final_wait_area.append("'" + map.getString("wait_area") + "', ");
				final_prompt_msg.append("'" + map.getString("prompt_msg") + "', ");
				final_nocard_default.append("'" + nocard_default + "', ");
				final_isreservqidflag.append("'1', ");
				num++;
			}
		}
		if(!def){
			throw new AppException("配置错误,没有设定无卡时默认客户类型");
		}
		
		cona.set("custtype_i", final_custtype_i.substring(0, final_custtype_i.length()-2) + "]");
		cona.set("queuetype_id", final_queuetype_id.substring(0, final_queuetype_id.length()-2) + "]");
		cona.set("wait_area", final_wait_area.substring(0, final_wait_area.length()-2) + "]");
		cona.set("prompt_msgx", final_prompt_msg.substring(0, final_prompt_msg.length()-2) + "]");
		cona.set("nocard_default", final_nocard_default.substring(0, final_nocard_default.length()-2) + "]");
		cona.set("isreservqidflag", final_isreservqidflag.substring(0, final_isreservqidflag.length()-2) + "]");
		//传长度
		cona.set("cardqueuesize", String.valueOf(array.size() + num));
		//判断afa的返回结果,是否成功
		cona.exchange();

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 点击排队机界面树删除节点
	 * @throws Exception
	 */
	public String delLeafByViewNo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b121_4.01", user);
		cona.set("branch", user.getUnitid());
		cona.set(IDictABT.QM_NUM, obj.getString(IDictABT.QM_NUM));
		cona.set("node_index", obj.getString("node_index"));
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
		cona.exchange();
		

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 通过参数ID查询排队机界面树的结构
	 * @throws Exception
	 */
	public void queryViewById() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//String jsonString = super.getJsonString();
		//JSONObject obj = JSONObject.fromObject(jsonString);
		String parameter_id = req.getParameter("parameter_id");
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b121.01", user);
		//传入的机构号
		cona.set("branch", user.getUnitid());
		cona.set("parameter_id", parameter_id);
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		if(map.get("H_ret_code") == null){
			throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
		}
		String H_ret_code = map.get("H_ret_code").get(0);
		if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
			throw new AppException("错误码:"+H_ret_code+","+"错误信息:"+map.get("H_ret_desc").get(0));
		}
		
		List<MachineViewTreeNode> list = new ArrayList<MachineViewTreeNode>();
		String loopNum = map.get("mvsize").get(0);
		int num = Integer.parseInt(loopNum);
		//创建根节点
		for (int i = 0; i < num; i++) {
			MachineViewTreeNode mvt = new MachineViewTreeNode();
			mvt.setBranch(map.get("branch").get(i));
			mvt.setNode_id(map.get("node_id").get(i));
			mvt.setNode_level(map.get("node_level").get(i));
			mvt.setNode_location(map.get("node_location").get(i));
			mvt.setChild_node_order(map.get("child_node_order").get(i));
			mvt.setNode_type(map.get("node_type").get(i));
			mvt.setDir_name(map.get("dir_name").get(i));
			mvt.setDir_name_en(map.get("dir_name_en").get(i));
			mvt.setBs_id(map.get("bs_id").get(i));
			mvt.setIs_show_msg(map.get("is_show_msg").get(i));
			mvt.setPrompt_msg(map.get("prompt_msg").get(i));
			mvt.setNode_index(map.get("node_index").get(i));
			mvt.setCard_visiable(map.get("card_visiable").get(i));
			mvt.setNeed_card(map.get("need_card").get(i));
			//判断是目录还是节点
			if("1".equals(mvt.getNode_type())){
				mvt.setNode_name(mvt.getBs_id());
			}else {
				mvt.setNode_name(mvt.getDir_name());
			}
			list.add(mvt);
		}
		//根据现有数据,把节点类型改变一下(0-根节点,1-目录,2-叶子(业务和预约激活)),并导入父节点ID
		for (MachineViewTreeNode mvt : list) {
			switch (Integer.parseInt(mvt.getNode_type())) {
			case 0:
				//目录
				mvt.setNode_type("1");
				break;
			case 1:
				//业务
				mvt.setNode_type("2");
				break;
			case 2:
				//预约激活
				mvt.setNode_type("2");
				mvt.setIs_reserve(true);
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
				for (MachineViewTreeNode tempMvt : list) {
					if(childNode.equals(tempMvt.getNode_id())){
						tempMvt.setParent_id(nodeId);
						break;
					}
				}
			}
		}
		
		//将数据库里取到的节点数据与根节点关联
		for (MachineViewTreeNode mvt2 : list) {
			if("".equals(mvt2.getParent_id())||null==mvt2.getParent_id()){
				mvt2.setParent_id("root");
			}
		}
		
		MachineViewTreeNode root = new MachineViewTreeNode();
		root.setNode_id("root");
		root.setNode_name("排队机");
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
	 * 给排队机界面树添加目录
	 * @throws Exception
	 */
	public String addFolderById() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(
				ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b121_5.01", user);

		//传入的机构号
		cona.set("branch", user.getUnitid());
		cona.set("parameter_id", obj.getString("parameter_id"));
		cona.set("parent_node_id", "root".equals(obj.getString("parent_node_id"))?"":obj.getString("parent_node_id"));
		cona.set(IDictABT.NODE_LEVEL, obj.getString(IDictABT.NODE_LEVEL));
		cona.set(IDictABT.NODE_LOCATION, obj.getString(IDictABT.NODE_LOCATION));
		cona.set(IDictABT.NODE_TYPE, obj.getString(IDictABT.NODE_TYPE));
		cona.set(IDictABT.DIR_NAME, obj.getString(IDictABT.DIR_NAME));
		cona.set("dir_name_en", obj.getString("dir_name_en"));
		cona.set("node_index", obj.getString("node_index"));
		//判断afa的返回结果,是否成功
		cona.exchange();
		

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 给排队机界面树修改目录
	 * @throws Exception
	 */
	public String editFolderById() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b121_6.01", user);

		//传入的机构号
		cona.set("branch", user.getUnitid());
		cona.set("parameter_id", obj.getString("parameter_id"));
		cona.set(IDictABT.DIR_NAME, obj.getString(IDictABT.DIR_NAME));
		cona.set("dir_name_en", obj.getString("dir_name_en"));
		cona.set(IDictABT.NODE_ID, obj.getString(IDictABT.NODE_ID));
		//判断afa的返回结果,是否成功
		cona.exchange();

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 点击排队机界面树查询业务节点
	 * @throws Exception
	 */
	public String queryLeafByViewNoAndId() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b121_2.01", user);
		//传入的机构号
		cona.set("branch", user.getUnitid());
		cona.set(IDictABT.NODE_ID, obj.getString("node_id"));
		cona.set("parameter_id", obj.getString("parameter_id"));
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		if(map.get("H_ret_code") == null){
			throw new AppException("与后台服务通讯失败,没有返回状态码(H_ret_code)和状态信息");
		}
		String H_ret_code = (String) map.get("H_ret_code").get(0);
		if(!H_ret_code.equals(IDictABT.AFARESULTSTATUS_SUCC)){
			throw new AppException("错误码:"+H_ret_code+","+"错误信息:"+map.get("H_ret_desc").get(0));
		}
		
		Map<String, String> leafMap = new HashMap<String, String>();
		leafMap.put(IDictABT.NODE_ID, map.get(IDictABT.NODE_ID).get(0));
		leafMap.put(IDictABT.NODE_LEVEL, map.get(IDictABT.NODE_LEVEL).get(0));
		leafMap.put(IDictABT.NODE_LOCATION, map.get(IDictABT.NODE_LOCATION).get(0));
		leafMap.put(IDictABT.CHILD_NODE_ORDER, map.get(IDictABT.CHILD_NODE_ORDER).get(0));
		leafMap.put(IDictABT.NODE_TYPE, map.get(IDictABT.NODE_TYPE).get(0));
		leafMap.put(IDictABT.BS_ID, map.get(IDictABT.BS_ID).get(0));
		leafMap.put(IDictABT.IS_SHOW_MSG, map.get(IDictABT.IS_SHOW_MSG).get(0));
		leafMap.put(IDictABT.PROMPT_MSG, map.get(IDictABT.PROMPT_MSG).get(0));
		leafMap.put("node_index", map.get("node_index").get(0));
		leafMap.put("need_reserv", map.get("need_reserv").get(0));
		leafMap.put("card_visiable", map.get("card_visiable").get(0));
		leafMap.put("need_card", map.get("need_card").get(0));
		//这里拆分成两个list,一个存预约的一个不存预约的
		List<Map> custtype_list = new ArrayList<Map>();
		List<Map> reserv_custtype_list = new ArrayList<Map>();
		String loopNum = map.get("cardqueuesize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, String> hld = new HashMap<String, String>();
			hld.put("bound", "1");
			hld.put(IDictABT.NODE_ID, map.get("node_id").get(i));
			hld.put(IDictABT.CUSTTYPE_I, map.get("custtype_i").get(i));
			hld.put(IDictABT.QUEUETYPE_ID, map.get("queuetype_id").get(i));
			hld.put(IDictABT.WAIT_AREA, map.get("wait_area").get(i));
			hld.put("prompt_msg", map.get("prompt_msgx").get(i));
			hld.put("nocard_default", "1".equals(map.get("nocard_default").get(i))?"1":"");
			hld.put("reserv_queuetype_id", "");
			//判断是否为预约客户类型
			if("1".equals(map.get("isreservqidflag").get(i))){//是预约,将预约标志赋值1
				hld.put("isreservqidflag", "true");
				reserv_custtype_list.add(hld);
			}else{//不是预约,将预约标志赋值1
				hld.put("isreservqidflag", "");
				custtype_list.add(hld);
			}
		}
		//将两个list合二为一
		for (int a = 0; a < custtype_list.size(); a++) {
			Map<String, String> custtype_map = custtype_list.get(a);
			for (int b = 0; b < reserv_custtype_list.size(); b++) {
				Map<String, String> reserv_custtype_map = reserv_custtype_list.get(b);
				//判断预约与非预约的两条数据是一个客户类型,将预约的队列ID赋予过去
				if (reserv_custtype_map.get("custtype_i").equals(custtype_map.get("custtype_i"))) {
					custtype_map.put("reserv_queuetype_id", reserv_custtype_map.get("queuetype_id"));
					custtype_map.put("isreservqidflag", "true");
					break;
				}
			}
		}
		
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		
		ret.put("leafMap", leafMap);
		ret.put("custtype_list", custtype_list);
		//ServiceReturn retn = new ServiceReturn(true, "");
		//ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		//ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
		super.setActionResult(pageInfo, new ArrayList(), ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 给排队机界面树添加业务节点
	 * @throws Exception
	 */
	public String addLeafById() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b121_1.01", user);

		cona.set("parameter_id", obj.getString("parameter_id"));
		cona.set("branch", user.getUnitid());
		cona.set("parent_node_id", "root".equals(obj.getString("parent_node_id"))?"":obj.getString("parent_node_id"));
		cona.set("node_level", obj.getString("node_level"));
		cona.set("node_location", obj.getString("node_location"));
		cona.set(IDictABT.NODE_TYPE, obj.getString(IDictABT.NODE_TYPE));
		cona.set("bs_id", obj.getString("bs_id"));
		cona.set("is_show_msg", "true".equals(obj.getString("is_show_msg"))?"1":"0");
		cona.set("prompt_msg", obj.getString("prompt_msg"));
		cona.set("node_index", obj.getString("node_index"));
		cona.set("need_reserv", "true".equals(obj.getString("need_reserv"))?"1":"0");
		cona.set("card_visiable", "true".equals(obj.getString("card_visiable"))?"1":"0");
		cona.set("need_card", "true".equals(obj.getString("need_card"))?"1":"0");
		JSONArray array = obj.getJSONArray("listData");
		boolean def = true;
		if(!"true".equals(obj.getString("need_card"))&&!"true".equals(obj.getString("card_visiable"))){//判断如果不必须卡
			def = false;
		}
		StringBuffer final_custtype_i = new StringBuffer("[");
		StringBuffer final_queuetype_id = new StringBuffer("[");
		StringBuffer final_wait_area = new StringBuffer("[");
		StringBuffer final_prompt_msg = new StringBuffer("[");
		StringBuffer final_nocard_default = new StringBuffer("[");
		
		StringBuffer final_isreservqidflag = new StringBuffer("[");
		int num = 0;
		for(int i = 0 ; i<array.size() ; i++){
			JSONObject map = array.getJSONObject(i);
			if("".equals(map.getString("queuetype_id"))){
				throw new AppException("需要为勾选的客户类型配置队列!");
			}
			if(map.getString("wait_area").length()>100){
				throw new AppException("自定义客户等待区域长度不能大于100位!");
			}
			if(map.getString("prompt_msg").length()>100){
				throw new AppException("提示信息字数长度不能大于100位!");
			}
			final_custtype_i.append("'" + map.getString("custtype_i") + "', ");
			final_queuetype_id.append("'" + map.getString("queuetype_id") + "', ");
			final_wait_area.append("'" + map.getString("wait_area") + "', ");
			final_prompt_msg.append("'" + map.getString("prompt_msg") + "', ");
			
			String nocard_default = map.getString("nocard_default");
			if("".equals(nocard_default)){
				nocard_default = "0";
			}else if("false".equals(nocard_default)){
				nocard_default = "0";
			}else {
				nocard_default = "1";
				def = true;
			}
			final_nocard_default.append("'" + nocard_default + "', ");
			final_isreservqidflag.append("'0', ");
			
			if("true".equals(map.getString("isreservqidflag"))){
				final_custtype_i.append("'" + map.getString("custtype_i") + "', ");
				final_queuetype_id.append("'" + map.getString("reserv_queuetype_id") + "', ");
				final_wait_area.append("'" + map.getString("wait_area") + "', ");
				final_prompt_msg.append("'" + map.getString("prompt_msg") + "', ");
				final_nocard_default.append("'" + nocard_default + "', ");
				final_isreservqidflag.append("'1', ");
				num++;
			}
		}
		if(!def){
			throw new AppException("配置错误,没有设定无卡时默认客户类型");
		}
		cona.set("custtype_i", final_custtype_i.substring(0, final_custtype_i.length()-2) + "]");
		cona.set("queuetype_id", final_queuetype_id.substring(0, final_queuetype_id.length()-2) + "]");
		cona.set("wait_area", final_wait_area.substring(0, final_wait_area.length()-2) + "]");
		cona.set("prompt_msgx", final_prompt_msg.substring(0, final_prompt_msg.length()-2) + "]");
		cona.set("nocard_default", final_nocard_default.substring(0, final_nocard_default.length()-2) + "]");
		cona.set("isreservqidflag", final_isreservqidflag.substring(0, final_isreservqidflag.length()-2) + "]");
		
		//传长度
		cona.set("cardqueuesize", String.valueOf(array.size() + num));
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 给排队机界面树修改业务节点
	 * @throws Exception
	 */
	public String editLeafById() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b121_3.01", user);
		
		cona.set("parameter_id", obj.getString("parameter_id"));
		cona.set("branch", user.getUnitid());
		cona.set("node_id", obj.getString("node_id"));
		cona.set("bs_id", obj.getString("bs_id"));
		cona.set("is_show_msg", "true".equals(obj.getString("is_show_msg"))?"1":"0");//是否提示文字(checkbox)
		cona.set("prompt_msg", obj.getString("prompt_msg"));
		cona.set("need_reserv", "true".equals(obj.getString("need_reserv"))?"1":"0");//是否提示文字(checkbox)
		cona.set("card_visiable", "true".equals(obj.getString("card_visiable"))?"1":"0");//是否提示文字(checkbox)
		cona.set("need_card", "true".equals(obj.getString("need_card"))?"1":"0");//是否提示文字(checkbox)
		boolean def = true;
		if(!("true".equals(obj.getString("need_card")))&&!("true".equals(obj.getString("card_visiable")))){//判断如果不必须卡
			def = false;
		}
		JSONArray array = obj.getJSONArray("listData");
		
		StringBuffer final_custtype_i = new StringBuffer("[");
		StringBuffer final_queuetype_id = new StringBuffer("[");
		StringBuffer final_wait_area = new StringBuffer("[");
		StringBuffer final_prompt_msg = new StringBuffer("[");
		StringBuffer final_nocard_default = new StringBuffer("[");
		
		StringBuffer final_isreservqidflag = new StringBuffer("[");
		int num = 0;
		for(int i = 0 ; i<array.size() ; i++){
			JSONObject map = array.getJSONObject(i);
			if("".equals(map.getString("queuetype_id"))){
				throw new AppException("需要为勾选的客户类型配置队列!");
			}
			if(map.getString("wait_area").length()>100){
				throw new AppException("自定义客户等待区域长度不能大于100位!");
			}
			if(map.getString("prompt_msg").length()>100){
				throw new AppException("提示信息字数长度不能大于100位!");
			}
			final_custtype_i.append("'" + map.getString("custtype_i") + "', ");
			final_queuetype_id.append("'" + map.getString("queuetype_id") + "', ");
			final_wait_area.append("'" + map.getString("wait_area") + "', ");
			final_prompt_msg.append("'" + map.getString("prompt_msg") + "', ");
			
			String nocard_default = map.getString("nocard_default");
			if("".equals(nocard_default)){
				nocard_default = "0";
			}else if("false".equals(nocard_default)){
				nocard_default = "0";
			}else {
				nocard_default = "1";
				def = true;
			}
			final_nocard_default.append("'" + nocard_default + "', ");
			final_isreservqidflag.append("'0', ");
			
			//判断预约标志,向预约的list里添加数据
			if("true".equals(map.getString("isreservqidflag"))){
				final_custtype_i.append("'" + map.getString("custtype_i") + "', ");
				final_queuetype_id.append("'" + map.getString("reserv_queuetype_id") + "', ");
				final_wait_area.append("'" + map.getString("wait_area") + "', ");
				final_prompt_msg.append("'" + map.getString("prompt_msg") + "', ");
				final_nocard_default.append("'" + nocard_default + "', ");
				final_isreservqidflag.append("'1', ");
				num++;
			}
		}
		if(!def){
			throw new AppException("配置错误,没有设定无卡时默认客户类型");
		}
		
		cona.set("custtype_i", final_custtype_i.substring(0, final_custtype_i.length()-2) + "]");
		cona.set("queuetype_id", final_queuetype_id.substring(0, final_queuetype_id.length()-2) + "]");
		cona.set("wait_area", final_wait_area.substring(0, final_wait_area.length()-2) + "]");
		cona.set("prompt_msgx", final_prompt_msg.substring(0, final_prompt_msg.length()-2) + "]");
		cona.set("nocard_default", final_nocard_default.substring(0, final_nocard_default.length()-2) + "]");
		cona.set("isreservqidflag", final_isreservqidflag.substring(0, final_isreservqidflag.length()-2) + "]");
		//传长度
		cona.set("cardqueuesize", String.valueOf(array.size() + num));
		//判断afa的返回结果,是否成功
		cona.exchange();

		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	/**
	 * 点击排队机界面树删除节点
	 * @throws Exception
	 */
	public String delLeafById() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user = (User) req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b121_4.01", user);
		cona.set("branch", user.getUnitid());
		cona.set("parameter_id", obj.getString("parameter_id"));
		cona.set("node_index", obj.getString("node_index"));
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
		cona.exchange();

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
