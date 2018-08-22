package com.agree.abt.action.configManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.natp.NatpConfigBean;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;


/**
 * 卡Bin配置
 * @ClassName: CardBinAction.java
 * @company 赞同科技
 * @author XiWang
 * @date 2013-7-25
 */
@SuppressWarnings({ "rawtypes" })
public class CardBinConfigAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private ABTComunicateNatp cona;
	
	/**
	 * 加载页面
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		//Set<String> priv=this.getUserInpageFunc();
		//sRet.put(ServiceReturn.FIELD2,priv);//获取登录用户信息 也就是利用这个获取到此部分功能
		
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: queryCard
	 * @Description: 查询卡Bin信息
	 * @return String
	 * @throws Exception
	 */
	public String queryCard() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		 
		cona.setBMSHeader("ibp.bms.b101_2.01", user);
		cona.set("branch", user.getUnitid());
		//判断afa的返回,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("cardchecksize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			//机构号
			hld.put("branch", map.get("branch").get(i));
			//卡bin类型id
			hld.put("check_id", map.get("check_id").get(i));
			//卡类型名称
			hld.put("card_name", map.get("card_name").get(i));
			//内部客户类型
			hld.put("custtype_i", map.get("custtype_i").get(i));
			//卡Bin验证数据
			hld.put("bin_check_no", map.get("bin_check_no").get(i));
			//是否进行客户识别
			hld.put("iscustdiscern", map.get("iscustdiscern").get(i));
			//制定时间
			hld.put("ordertime", map.get("ordertime").get(i));
			//是否启用
			hld.put("isuseing", map.get("isuseing").get(i));
			//备注
			hld.put("remarks", map.get("remarks").get(i));
			list.add(hld);
		}
		pageInfo.setTotal(num);
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 
	 * @Title: queryCard
	 * @Description: 查询卡Bin信息
	 * @return String
	 * @throws Exception
	 */
	public String queryCardPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		 
		cona.setBMSHeader("ibp.bms.b101_2.01", user);
		cona.set("branch", user.getUnitid());
		//判断afa的返回,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("cardchecksize").get(0);
		int num = Integer.parseInt(loopNum);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		Page pageInfo = this.getPage(obj);
		pageInfo.setTotal(num);//总条数		
		Integer pageNo = (pageInfo.getTotal() % pageInfo.getLimit() == 0) ? pageInfo.getTotal() / pageInfo.getLimit() : pageInfo.getTotal()
				/ pageInfo.getLimit() + 1;// 得到总页数
		if (pageInfo.getStart() == -1) {// 查询最后一页
			pageInfo.setRowStart((pageNo - 1) * pageInfo.getLimit());
			pageInfo.setRowEnd(pageInfo.getRowStart()
					+ (pageInfo.getTotal() % pageInfo.getLimit() == 0 ? pageInfo.getLimit() : pageInfo.getTotal() % pageInfo.getLimit()));
			pageInfo.setPage(pageNo);
			pageInfo.setTotal(pageInfo.getTotal());
		} else {
			pageInfo.setRowStart((pageInfo.getStart() - 1) * pageInfo.getLimit() );
			pageInfo.setRowEnd( (pageInfo.getRowStart() + pageInfo.getLimit()) <= pageInfo.getTotal() ? (pageInfo.getRowStart() + pageInfo.getLimit())
					: pageInfo.getTotal() );
			pageInfo.setPage(pageNo);
			pageInfo.setTotal(pageInfo.getTotal());
		}
		
		for (int i=pageInfo.getRowStart();i<pageInfo.getRowEnd();i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			//机构号
			hld.put("branch", map.get("branch").get(i));
			//卡bin类型id
			hld.put("check_id", map.get("check_id").get(i));
			//卡类型名称
			hld.put("card_name", map.get("card_name").get(i));
			//内部客户类型
			hld.put("custtype_i", map.get("custtype_i").get(i));
			//卡Bin验证数据
			hld.put("bin_check_no", map.get("bin_check_no").get(i));
			//是否进行客户识别
			hld.put("iscustdiscern", map.get("iscustdiscern").get(i));
			//制定时间
			hld.put("ordertime", map.get("ordertime").get(i));
			//是否启用
			hld.put("isuseing", map.get("isuseing").get(i));
			//备注
			hld.put("remarks", map.get("remarks").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}

	/**
	 * 
	 * @Title: addCard
	 * @Description: 添加卡Bin信息
	 * @return String
	 * @throws Exception
	 */
	public String addCard() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b101_1.01", user);
		
		cona.set("branch", user.getUnitid());
		cona.set("check_id", "1");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-hhmmss");
		cona.set("ordertime", sdf.format(new Date()));
		Iterator keys = obj.keys();
		while(keys.hasNext()){
			String key = keys.next().toString();
			if(obj.getString(key).equals("on")){
				cona.set(key, "1");
			}else if(obj.getString(key).equals("off")){
				cona.set(key, "0");
			}else if(obj.getString(key)==null){
				cona.set(key, "");
			}else{
				cona.set(key, obj.getString(key));
			}
		}
		
		cona.exchange();
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 
	 * @Title: editCard
	 * @Description: 修改卡Bin信息
	 * @return String
	 * @throws Exception
	 */
	public String editCard() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b101_3.01", user);
		//机构号
		cona.set("branch", user.getUnitid());
		
		Iterator keys = obj.keys();
		while(keys.hasNext()){
			String key = keys.next().toString();
			if(obj.getString(key).equals("on")){
				cona.set(key, "1");
			}else if(obj.getString(key).equals("off")){
				cona.set(key, "0");
			}else{
				cona.set(key, obj.getString(key));
			}
			
		}
		
		
		cona.exchange();
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 
	 * @Title: delCard
	 * @Description: 删除卡Bin信息
	 * @return String
	 * @throws Exception
	 */

	public String delCard() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		
		//TODO 群体删除修改
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
			Object obj[] = jsonObj.values().toArray();
			 
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b101_4.01", user);
			cona.set("branch", obj[0].toString());
			cona.set("check_id", obj[1].toString());
			cona.exchange();
		}
	
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 获取客户级别
	 * @throws Exception
	 */
	public void getCustLevel() throws Exception{
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String flag=req.getParameter("flag");
		List<Map<String,Object>> retn=new ArrayList<Map<String,Object>> ();
		if(null!=flag&&flag.equalsIgnoreCase("1")){
			Map<String,Object> tem=new HashMap<String,Object>();
			tem.put("custLevel", "-1");
			tem.put("queuePrefix", "所有级别");
			retn.add(tem);
		}
		NatpConfigBean configBean= (NatpConfigBean) ServletActionContext.getServletContext().getAttribute(ApplicationConstants.NATPCONFIGBEAN);
		cona.setNatpConfig(configBean);
		//User user=this.getLogonUser(false);
		
		cona.setBMSHeader("B101", user);
		cona.set("funcCode", "5");
		Map<String,List<String>> map =  cona.exchange();
		if(map.get("loopNum")!=null){
			String loop=(String) map.get("loopNum").get(0);
			for(int i=0;i<Integer.parseInt(loop);i++){
				Map<String,Object> ret=new HashMap<String,Object>();
				ret.put("custLevel", map.get("custLevel").get(i));
				ret.put("queuePrefix",map.get("custLevel").get(i)+"-"+ map.get("queuePrefix").get(i));
				retn.add(ret);
			}
			
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, retn);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
}
