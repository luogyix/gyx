package com.agree.abt.action.devmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.agree.framework.dao.entity.Page;
import com.agree.framework.natp.ABTComunicateNatp;
import com.agree.framework.struts2.webserver.ApplicationConstants;
import com.agree.framework.web.action.base.BaseAction;
import com.agree.framework.web.common.ServiceReturn;
import com.agree.framework.web.form.administration.User;

/**
 * 机具外设监控Action
 * <P>
 * 外设出现异常时得到异常信息和设备信息
 * </p>
 * 异常信息：<br>
 * *******状态描述、状态码、异常发生时间<br>
 * 设备信息：<br>
 * *******设备所属机构、设备唯一编号、设备类型、外设类型、工作时间
 * @author 高艺祥<br>
 * 				最后修改时间：2016/12/22
 * @version 0.1
 *
 */
public class MachineMonitoringAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private ABTComunicateNatp cona;

	public ABTComunicateNatp getCona() {
		return cona;
	}

	public void setCona(ABTComunicateNatp cona) {
		this.cona = cona;
	}

	/**
	 * 加载机具外设监控页面
	 * @return jsp页面：/webpages/abt/devmanager/machineMonitoring.jsp
	 */
	public String loadPage() throws Exception {
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		//Set<String> priv=this.getUserInpageFunc();
		//sRet.put(ServiceReturn.FIELD2,priv);//获取登录用户信息 也就是利用这个获取到此部分功能
		sRet.put(ServiceReturn.FIELD2,super.getUnitTreeList());
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute(ApplicationConstants.ACTIONRESULT, retObj.toString());
		return SUCCESS;
	}
	
	/**
	 * 机具外设状态查询
	 * <p>
	 * 可通过机构号、异常发生日期、机具类型、外设类型查询机具外设状态
	 * </p>
	 * @author 高艺祥 
	 * @version 0.1
	 */
	public String queryMachineMonitoring() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute(ApplicationConstants.LOGONUSER);
		String jsonString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(jsonString);
		cona.setBMSHeader("ibp.bms.b405_1.01", user);
		//初始sql
		String sql = "select "+ 
				           "branch,"+
				           "workdate,"+
				           "devicetype,"+
				           "device_num,"+
				           "peripheral_type,"+
				           "status_code,"+
				           "desc,"+
				           "time"+
					 " from "+
					 	"bt_arsm_peripheral_info "+
					 "where "+
					 	"branch = ";
		//机构号条件
		if(obj.getString("branch")!=null && !obj.getString("branch").equals("")){
			sql = sql + "'" + obj.getString("branch") + "'";
		}else{
			sql = sql + "'" + user.getUnitid() + "'";
		}
		//日期条件
		if(obj.getString("workdate")!=null && !obj.getString("workdate").equals("")){
			sql = sql + " and workdate like" + "'%" + obj.getString("workdate") + "%'";
		}
		//设备类型条件
		if(obj.getString("devicetype")!= null && !obj.getString("devicetype").equals("")){
			sql = sql + " and devicetype = " + "'" + obj.getString("devicetype") + "'";
		}
		//外设类型
		if(obj.getString("peripheral_type") != null && !obj.getString("peripheral_type").equals("")){
			sql = sql + " and peripheral_type = " + "'" + obj.getString("peripheral_type") + "'";
		}
		cona.set("sql", sql);
		Map<String, List<String>> map = cona.exchange();
		
		String loopNum = (String) map.get("machine_size").get(0);
		int num = Integer.parseInt(loopNum);
		Page pageInfo = this.getPage(obj);

		// 总条数
		pageInfo.setTotal(num);
		// 分页逻辑
		// 获取总页数
		Integer pageNo = (pageInfo.getTotal() % pageInfo.getLimit() == 0) ? pageInfo
				.getTotal() / pageInfo.getLimit()
				: pageInfo.getTotal() / pageInfo.getLimit() + 1;
		// 查询最后一页
		if (pageInfo.getStart() == -1) {
			pageInfo.setRowStart((pageNo - 1) * pageInfo.getLimit());
			pageInfo.setRowEnd(pageInfo.getRowStart()
					+ (pageInfo.getTotal() % pageInfo.getLimit() == 0 ? pageInfo
							.getLimit() : pageInfo.getTotal()
							% pageInfo.getLimit()));
			// 设置总页数
			pageInfo.setPage(pageNo);
			// 设置总条数
			pageInfo.setTotal(pageInfo.getTotal());
		} else {
			pageInfo.setRowStart((pageInfo.getStart() - 1)
					* pageInfo.getLimit());
			// 每页显示条数
			pageInfo.setRowEnd((pageInfo.getRowStart() + pageInfo.getLimit()) <= pageInfo
					.getTotal() ? (pageInfo.getRowStart() + pageInfo.getLimit())
					: pageInfo.getTotal());
			// 当前页
			pageInfo.setPage(pageNo);
			// 总条树
			pageInfo.setTotal(pageInfo.getTotal());
		}

		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 循环得到的当前页所有的值
		for (int i = pageInfo.getRowStart(); i < pageInfo.getRowEnd(); i++) {
			HashMap<String, Object> hld = new HashMap<String, Object>();
			// 机构号
			hld.put("branch", map.get("branch").get(i));
			// 工作日期
			hld.put("workdate", map.get("workdate").get(i));
			// 设备类型
			hld.put("devicetype", map.get("devicetype").get(i));
			//设备编号
			hld.put("device_num", map.get("device_num").get(i));
			// 外设类型
			hld.put("peripheral_type", map.get("peripheral_type").get(i));
			// 状态码
			hld.put("status_code", map.get("status_code").get(i));
			//状态描述
			hld.put("desc", map.get("desc").get(i));
			//发生时间
			hld.put("time", map.get("time").get(i));
			list.add(hld);
		}
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		// 想页面返回结果
		super.setActionResult(pageInfo, list, ret);
		return AJAX_SUCCESS;
	}
	
}
