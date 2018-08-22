package com.agree.abt.action.pfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.exception.AppException;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;
import com.agree.util.IDictABT;



/**
 * 客户信息处理
 * @ClassName: PfsCustInfoAction.java
 * @company 赞同科技
 * @author 赵明
 * @date 2014-4-2
 */
public class PfsCustInfoAction extends BaseAction {
	
	private static final Logger logger = LoggerFactory.getLogger(PfsCustInfoAction.class);
	private static final long serialVersionUID = 1L;
	private ABTComunicateNatp cona;

	/**
	 * 加载主页面
	 * @return
	 * @throws Exception
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 客户信息处理查询
	 * @return
	 * @throws Exception
	 */
	public String queryCustInfo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		Page pageInfo =new Page();
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b135_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		cona.set("pageflag","3");//分页标识
		cona.set("currentpage","0");//当前页码
		cona.set("count","20");//每页记录数
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("infosize").get(0);
		pageInfo.setTotal(Integer.parseInt(loopNum));
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.OCUSTTYPE_E, map.get("ocusttype_e").get(i));
			hld.put(IDictABT.NCUSTTYPE_E, map.get("ncusttype_e").get(i));
			hld.put(IDictABT.SERVETIME, map.get("servetime").get(i));		
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	/**
	 * 客户信息处理分页查询
	 * @return
	 * @throws Exception
	 */
	public String queryCustInfoPage() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//查询交易接口
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		Page pageInfo = this.getPage(obj);
		 
		
		cona.setBMSHeader("ibp.bms.b135_1.01", user);
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//分页专用
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String loopNum = (String) map.get("infosize").get(0);
		int num = Integer.parseInt(loopNum);
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
			hld.put(IDictABT.BRANCH, map.get("branch").get(i));
			hld.put(IDictABT.OCUSTTYPE_E, map.get("ocusttype_e").get(i));
			hld.put(IDictABT.NCUSTTYPE_E, map.get("ncusttype_e").get(i));
			hld.put(IDictABT.SERVETIME, map.get("servetime").get(i));	
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
	/**
	 * 客户类型查询
	 * @throws Exception
	 */
	public void queryCustType() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		//Page pageInfo =new Page();
		//查询交易接口
		 
		
		cona.setBMSHeader("ibp.bms.b108_2.01", user);
		cona.set("branch", user.getUnitid()); //查询条件.待确认
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map = cona.exchange();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		logger.info("---------------------------map : " + map);
		String loopNum = (String) map.get("custtypesize").get(0);
		int num = Integer.parseInt(loopNum);
		for (int i = 0; i < num; i++) {
			Map<String, Object> hld = new HashMap<String, Object>();
			hld.put(IDictABT.CUSTTYPE_I, map.get("custtype_i").get(i));
			hld.put(IDictABT.CUSTTYPE_E, map.get("custtype_e").get(i));
			hld.put(IDictABT.CUSTTYPE_NAME, map.get("custtype_name").get(i));
			hld.put("custtype_waittime", map.get("custtype_waittime").get(i));
			//hld.put("custreporttype", map.get("custreporttype").get(i));
			hld.put(IDictABT.ISVIP, map.get("isvip").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
	}
	
	/**
	 * 客户信息处理新增
	 * @return String
	 * @throws Exception
	 */
	public String addCustInfo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b135_2.01", user);
		cona.set(IDictABT.OCUSTTYPE_E, obj.getString("ocusttype_e"));
		cona.set(IDictABT.NCUSTTYPE_E, obj.getString("ncusttype_e"));
		cona.set(IDictABT.SERVETIME, obj.getString("servetime"));
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//判断afa的返回结果,是否成功
		cona.exchange();
		
		ServiceReturn tet=new ServiceReturn(ServiceReturn.SUCCESS,"");
		JSONObject returnJson=super.convertServiceReturnToJson(tet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

	/**
	 * 客户信息处理修改
	 * @return String
	 * @throws Exception
	 */
	public String editCustInfo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		 
		
		cona.setBMSHeader("ibp.bms.b135_3.01", user);
		cona.set(IDictABT.OCUSTTYPE_E, obj.getString("ocusttype_e"));
		cona.set(IDictABT.NCUSTTYPE_E, obj.getString("ncusttype_e"));
		cona.set(IDictABT.SERVETIME, obj.getString("servetime"));
		cona.set(IDictABT.BRANCH, user.getUnitid());
		//判断afa的返回结果,是否成功
		Map<String,List<String>> map =  cona.exchange();
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
	 * 客户信息处理删除
	 * @return String
	 * @throws Exception
	 */

	public String delCustInfo() throws Exception {
		HttpServletRequest req = ServletActionContext.getRequest();
		User user=(User)req.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.getString(i));
			Object obj[] = jsonObj.values().toArray();
			 
			//初始化NATP通讯
			cona.reInit();
			cona.setBMSHeader("ibp.bms.b135_4.01", user);
			cona.set(IDictABT.OCUSTTYPE_E, obj[0].toString());
			cona.set(IDictABT.BRANCH, obj[1].toString());
			cona.set(IDictABT.NCUSTTYPE_E, obj[2].toString());
			//判断afa的返回结果,是否成功
			cona.exchange();
		}
	
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		String string = super.convertServiceReturnToJson(ret).toString();
		super.setActionresult(string);
		return AJAX_SUCCESS;
	}

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}
	
}
